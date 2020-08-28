package sloshbot.raspberrypi_api.controllers;

import com.pi4j.io.gpio.*;
import org.jruby.ir.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.*;
import sloshbot.raspberrypi_api.models.payloads.requests.MakeDrinkRequest;
import sloshbot.raspberrypi_api.models.payloads.responses.robot.*;
import sloshbot.raspberrypi_api.repositories.OpticRepository;
import sloshbot.raspberrypi_api.repositories.RecipeRepository;

import java.util.*;

@Controller
@RequestMapping("/api/v1/robot")
public class RobotController {
    @Autowired
    private OpticRepository opticRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @Value("${robot.pin.stepper}")
    private int stepperPinNumber;
    @Value("${robot.pin.direction}")
    private int directionPinNumber;
    @Value("${robot.pin.home}")
    private int homePinNumber;
    @Value("${robot.pin.enable}")
    private int enablePinNumber;
    @Value("${robot.testing}")
    private boolean testing;

    private GpioController gpio;
    private GpioPinDigitalOutput stepperPin;
    private GpioPinDigitalOutput directionPin;
    private GpioPinDigitalInput homePin;
    private GpioPinDigitalOutput enablePin;
    private List<GpioPinDigitalOutput> opticPins = new ArrayList<>();
    private Thread thread;

    private boolean started = false;
    private volatile boolean stopped = false;
    private volatile boolean startNextDrink = false;
    private volatile boolean makingDrink = false;
    private int successfulDrinksMade = 0;
    private int failedDrinksMade = 0;
    private int drinksLostInProgress = 0;
    private Recipe currentDrink = new Recipe();
    private String currentUsername = "";
    private Queue<Tuple<String, Recipe>> drinkQueue = new LinkedList<>();


    @PostMapping("/drinkqueue")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MakeDrinkResponse> AddDrinkToQueue(@RequestBody MakeDrinkRequest request) {
        MakeDrinkResponse response = new MakeDrinkResponse();
        if (!started) {
            response.setMessage("Robot not started, please talk to an admin.");
            return ResponseEntity.ok().body(response);
        }
        Recipe recipe = recipeRepository.findById(request.getRecipeId()).orElse(null);
        if (recipe == null) {
            response.setMessage("Recipe not found, please talk to an admin.");
            return ResponseEntity.ok().body(response);
        }
        drinkQueue.add(new Tuple<>(request.getUsername(), recipe));
        response.setDrinkName(recipe.getName());
        response.setCurrentPosition(drinkQueue.size());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/drinkqueue")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GetDrinkQueueResponse> GetDrinkQueue() {
        GetDrinkQueueResponse response = new GetDrinkQueueResponse();
        if (!started) {
            response.setMessage("Robot not started, please talk to an admin.");
            return ResponseEntity.ok().body(response);
        } else if (drinkQueue.isEmpty()) {
            response.setMessage("Robot does not have a drink to make.  Please wait.");
            return ResponseEntity.ok().body(response);
        }
        response.setQueueSize(drinkQueue.size());
        response.setQueue(drinkQueue);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/start")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<StartRobotResponse> RunBot() {
        StartRobotResponse response = new StartRobotResponse();
        if(started){
            response.setMessage("Robot was already started. Nothing has changed.");
            return ResponseEntity.ok().body(response);
        }
        started = true;
        thread = new Thread(() -> {
            initialize();
            startNextDrink = true;
            while (started) {
                if (!drinkQueue.isEmpty() && startNextDrink) {
                    print("Starting to make a drink");
                    makingDrink = true;
                    if (makeDrink()) {
                        successfulDrinksMade++;
                        print("Successfully finished making a drink");
                    } else {
                        failedDrinksMade++;
                        print("Failed making a drink");
                    }
                    makingDrink = false;
                }
            }
            stopped = true;
            return;
        });
        thread.start();
        response.setMessage("Robot has been started");
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/stop")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<StopRobotResponse> StopBot() {
        StopRobotResponse response = new StopRobotResponse();
        if(!started){
            response.setMessage("Robot is not turned on.");
            return ResponseEntity.ok().body(response);
        }
        started = false;
        while (!stopped) Thread.onSpinWait();
        response.setDrinksLostInProgress(drinksLostInProgress);
        response.setFailedDrinksMade(failedDrinksMade);
        response.setSuccessfulDrinksMade(successfulDrinksMade);
        response.setDrinksLeftInQueue(drinkQueue.size());
        response.setMessage("The bot has been stopped.");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/setopticdistance")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<SetOpticDistanceResponse> SetOpticDistance(@RequestBody Optic opticDetails) {
        SetOpticDistanceResponse response = new SetOpticDistanceResponse();
        int distanceFromHome = goHome();
        Optic optic = opticRepository.findById(opticDetails.getId()).orElse(null);
        return getSetOpticDistanceResponse(response, distanceFromHome, optic);
    }

    @PostMapping("/setopticdistancebyid")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<SetOpticDistanceResponse> SetOpticDistanceById(@RequestBody int opticId) {
        SetOpticDistanceResponse response = new SetOpticDistanceResponse();
        int distanceFromHome = goHome();
        Optic optic = opticRepository.findById(opticId).orElse(null);
        return getSetOpticDistanceResponse(response, distanceFromHome, optic);
    }

    @GetMapping("/startnextdrink")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<StartNextDrinkResponse> StartNextDrink() {
        StartNextDrinkResponse response = new StartNextDrinkResponse();
        if (!started) {
            response.setMessage("Robot not started, please talk to an admin.");
            return ResponseEntity.ok().body(response);
        } else if (makingDrink) {
            response.setMessage("Robot is currently making a drink.  Please wait.");
            return ResponseEntity.ok().body(response);
        } else if (drinkQueue.isEmpty()) {
            response.setMessage("Robot does not have a drink to make.  Please wait.");
            return ResponseEntity.ok().body(response);
        }
        startNextDrink = true;
        while (startNextDrink) Thread.onSpinWait();
        response.setQueueLength(drinkQueue.size());
        response.setRecipe(currentDrink);
        response.setUsername(currentUsername);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/currentstats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RobotStatsResponse> GetCurrentStats(){
        RobotStatsResponse response = new RobotStatsResponse();
        if(!started){
            response.setMessage("The robot has not been started, please talk to an admin.");
            return ResponseEntity.ok().body(response);
        }
        response.setDrinksLostInProgress(drinksLostInProgress);
        response.setFailedDrinksMade(failedDrinksMade);
        response.setSuccessfulDrinksMade(successfulDrinksMade);
        response.setDrinkQueue(drinkQueue);
        return ResponseEntity.ok().body(response);
    }

    //region Private Methods
    private void initialize() {
        if (!testing) {
            print("Initializing Pins");
            gpio = GpioFactory.getInstance();
            stepperPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(stepperPinNumber), "StepperPin", PinState.LOW);
            directionPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(directionPinNumber), "DirectionPin", PinState.LOW);
            homePin = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(homePinNumber), "HomePin");
            enablePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(enablePinNumber), "EnablePin", PinState.HIGH);
            print("Initializing Optics");
            for (Optic optic : opticRepository.findAll()) {
                GpioPinDigitalOutput opticPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(optic.getPinNumber()), optic.getIngredient().getName(), PinState.HIGH);
                opticPins.add(opticPin);
            }
        } else {
            printTest("Initializing Pins");
            printTest("Initializing Optics");
        }
        successfulDrinksMade = 0;
        failedDrinksMade = 0;
        drinksLostInProgress = 0;
    }

    private boolean makeDrink() {
        assert drinkQueue.peek() != null;
        currentUsername = drinkQueue.peek().a;
        Recipe recipe = Objects.requireNonNull(drinkQueue.poll()).b;
        currentDrink = recipe;
        startNextDrink = false;
        goHome();
        int previousDistanceFromHome = 0;
        try {
            for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
                Optic optic = opticRepository.FindOpticByIngredientId(recipeIngredient.getIngredient().getId());
                movePosition(optic.getDistanceFromHome(), previousDistanceFromHome);
                pourLiquid(recipeIngredient, optic);
                previousDistanceFromHome = optic.getDistanceFromHome();
            }
            goHome();
            return true;
        } catch (Exception e) {
            print("Exiting failed: makeDrink \n" + e.toString());
            return false;
        }
    }

    private int goHome() {
        int distance = 0;
        if (!testing) {
            print("Going home");
            while (homePin.getState() != PinState.HIGH) {
                step();
                distance++;
            }
        } else {
            printTest("Going home");
        }
        return distance;
    }

    private void pourLiquid(RecipeIngredient recipeIngredient, Optic optic) {
        if (!testing) {
            print("Pouring " + recipeIngredient.getIngredient().getName() + " from pinNumber: " + optic.getPinNumber());
            GpioPinDigitalOutput opticPin = opticPins.stream().filter(pin -> optic.getPinNumber() == (pin.getPin().getAddress())).findAny().orElse(null);
            opticPin.low();
            delayMillis(recipeIngredient.getAmount() * 1000);
            opticPin.high();
        } else {
            printTest("Pouring " + recipeIngredient.getIngredient().getName() + " from pinNumber: " + optic.getPinNumber());
            delayMillis(recipeIngredient.getAmount() * 1000);
        }
    }

    private void movePosition(int to, int from) {
        int distance = to - from;
        if (!testing) {
            print("Moving from position " + from + " to position " + to);
            if (distance < 0) {
                directionPin.low();
                distance = -distance;
            } else {
                directionPin.high();
            }
            for (int i = 0; i < distance; i++) {
                step();
            }
        }
        else{
            printTest("Moving from position " + from + " to position " + to);
            if(distance < 0){
                distance = -distance;
            }
            delayMillis(distance * 50);
        }
    }

    private void step() {
        if (!testing) {
            stepperPin.high();
            motorDelay();
            stepperPin.low();
            motorDelay();
        }
    }

    private void delayNanos(long time) {
        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + time >= end);
        print(end - start);
    }

    private void delayMillis(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void motorDelay() {
        long time = 500000;
        final long INTERVAL = 1000;
        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + INTERVAL >= end);
        print(end - start);
    }

    private ResponseEntity<SetOpticDistanceResponse> getSetOpticDistanceResponse(SetOpticDistanceResponse response, int distanceFromHome, Optic optic) {
        if (optic == null) {
            response.setSuccess(false);
            response.setMessage("Could not find optic in database.  Please see admin to troubleshoot");
            return ResponseEntity.ok().body(response);
        }
        optic.setDistanceFromHome(distanceFromHome);
        opticRepository.save(optic);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }

    private void print(Object s) {
        System.out.println("--------" + s);
    }

    private void printTest(Object s) {
        System.out.println("-------- TESTING: " + s);
    }
    //endregion
}
