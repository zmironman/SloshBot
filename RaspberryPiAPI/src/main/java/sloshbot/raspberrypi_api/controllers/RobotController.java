package sloshbot.raspberrypi_api.controllers;

import org.jruby.ir.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.*;
import sloshbot.raspberrypi_api.models.payloads.requests.MakeDrinkRequest;
import sloshbot.raspberrypi_api.models.payloads.responses.MakeDrinkResponse;
import sloshbot.raspberrypi_api.models.payloads.responses.SetOpticDistanceResponse;
import sloshbot.raspberrypi_api.models.payloads.responses.StartRobotResponse;
import sloshbot.raspberrypi_api.models.payloads.responses.StopRobotResponse;
import sloshbot.raspberrypi_api.repositories.OpticRepository;
import sloshbot.raspberrypi_api.repositories.RecipeRepository;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Controller
@RequestMapping("/api/v1/robot")
public class RobotController {
    @Autowired
    private OpticRepository opticRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    //    private final GpioController gpio = GpioFactory.getInstance();
//    @Value("${robot.pin.stepper}")
//    private int stepperPinNumber;
//    @Value("${robot.pin.direction}")
//    private int directionPinNumber;
//    @Value("${robot.pin.home}")
//    private int homePinNumber;
//    @Value("${robot.pin.enable}")
//    private int enablePinNumber;
//
//    final GpioPinDigitalOutput stepperPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(stepperPinNumber), "StepperPin", PinState.LOW);
//    final GpioPinDigitalOutput directionPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(directionPinNumber), "DirectionPin", PinState.LOW);
//    final GpioPinDigitalInput homePin = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(homePinNumber), "HomePin");
//    final GpioPinDigitalOutput enablePin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(enablePinNumber), "EnablePin", PinState.HIGH);
//    final List<GpioPinDigitalOutput> opticPins = new ArrayList<>();
    private boolean initialized = false;
    private boolean started = false;
    private boolean stopped = false;
    private int successfulDrinksMade = 0;
    private int failedDrinksMade = 0;
    private int drinksLostInProgress = 0;
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
        drinkQueue.add(new Tuple<String, Recipe>(request.getUsername(), recipe));
        response.setDrinkName(recipe.getName());
        response.setCurrentPosition(drinkQueue.size());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/drinkqueue")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Queue<Tuple<String, Recipe>>> GetDrinkQueue() {
        return ResponseEntity.ok().body(drinkQueue);
    }

    @GetMapping("/start")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<StartRobotResponse> RunBot() {
        initialize();
        while (started) {
            if (!drinkQueue.isEmpty()) {
                if(makeDrink())
                    successfulDrinksMade++;
                else
                    failedDrinksMade++;
            }
        }
        StartRobotResponse response = new StartRobotResponse();
        drinksLostInProgress = drinkQueue.size();
        response.setDrinksLostInProgress(drinksLostInProgress);
        response.setSuccessfulDrinksMade(successfulDrinksMade);
        response.setFailedDrinksMade(failedDrinksMade);
        drinkQueue = new LinkedList<>();
        stopped = true;
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/stop")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<StopRobotResponse> StopBot() {
        started = false;
        while (!stopped);
        StopRobotResponse response = new StopRobotResponse();
        response.setDrinksLostInProgress(drinksLostInProgress);
        response.setFailedDrinksMade(failedDrinksMade);
        response.setSuccessfulDrinksMade(successfulDrinksMade);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/setopticdistance")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<SetOpticDistanceResponse> setOpticDistance(@RequestBody Optic opticDetails){
        SetOpticDistanceResponse response = new SetOpticDistanceResponse();
        int distanceFromHome = goHome();
        Optic optic = opticRepository.findById(opticDetails.getId()).orElse(null);
        if(optic == null){
            response.setSuccess(false);
            response.setMessage("Could not find optic in database.  Please see admin to troubleshoot");
            return ResponseEntity.ok().body(response);
        }
        optic.setDistanceFromHome(distanceFromHome);
        opticRepository.save(optic);
        response.setSuccess(true);
        return ResponseEntity.ok().body(response);
    }
    
    //region Private Methods
    private void initialize() {
        System.out.println("Entering: initializeOpticPins");
//        for(Optic optic : opticRepository.findAll()){
//            GpioPinDigitalOutput opticPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(optic.getPinNumber()), optic.getIngredient().getName(), PinState.HIGH);
//            opticPins.add(opticPin);
//        }
        initialized = true;
        started = true;
        System.out.println("Exiting: initializeOpticPins");
    }

    private boolean makeDrink() {
        System.out.println("Entering: makeDrink");
        Recipe recipe = Objects.requireNonNull(drinkQueue.poll()).b;
        goHome();
        int previousDistanceFromHome = 0;
        try {
            for (RecipeIngredient recipeIngredient : recipe.getRecipeIngredients()) {
                Optic optic = opticRepository.FindOpticByIngredientId(recipeIngredient.getIngredient().getId());
                movePosition(optic.getDistanceFromHome(), previousDistanceFromHome);
                pourLiquid(recipeIngredient.getAmount(), optic.getPinNumber());
                previousDistanceFromHome = optic.getDistanceFromHome();
            }
            goHome();
            System.out.println("Exiting: makeDrink");
            return true;
        } catch (Exception e) {
            System.out.println("Exiting failed: makeDrink \n" + e.toString());
            return false;
        }
    }

    private int goHome() {
        System.out.println("Entering: goHome");
        int distance = 0;
//        while (homePin.getState() != PinState.HIGH) {
//            step();
//            distance++;
//        }
        System.out.println("Exiting: goHome");
        return distance;
    }

    private void pourLiquid(int amount, int pinNumber) {
        System.out.println("Entering: pourLiquid");
//        GpioPinDigitalOutput opticPin = opticPins.stream().filter(pin -> pinNumber == (pin.getPin().getAddress())).findAny().orElse(null);
//        opticPin.low();
//        for (int i = 0; i < amount; i++)
//            delayMillis(1000);
//        opticPin.high();
        try {
            delayMillis(2000);
        } catch (Exception e) {
            System.out.println("SOMETHING FUCKED UP");
        }
        System.out.println("Exiting: pourLiquid");
    }

    private void movePosition(int to, int from) {
        System.out.println("Entering: movePosition");
//        int distance = to - from;
//        if (distance < 0) {
//            directionPin.low();
//            distance = -distance;
//        } else {
//            directionPin.high();
//        }
//        for (int i = 0; i < distance; i++) {
//            step();
//        }
        System.out.println("Delaying a bit");
        try {
            delayMillis(2000);
        } catch (Exception e) {
            System.out.println("SOMETHING FUCKED UP");
        }
        System.out.println("Exiting: movePosition");
    }

    private void step() {
        System.out.println("Entering: step");
//        stepperPin.high();
//        motorDelay();
//        stepperPin.low();
//        motorDelay();
        System.out.println("Exiting: step");
    }

    private void delayNanos(long time) {
        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + time >= end);
        System.out.println(end - start);
    }

    private void delayMillis(long time) throws InterruptedException {
        Thread.sleep(time);
    }

    private void motorDelay() {
        long time = 500000;
        final long INTERVAL = 1000;
        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + INTERVAL >= end);
        System.out.println(end - start);
    }
    //endregion
}
