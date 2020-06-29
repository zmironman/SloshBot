package sloshbot.raspberrypi_api.controllers;

import org.jruby.ir.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.*;
import sloshbot.raspberrypi_api.models.payloads.requests.MakeDrinkRequest;
import sloshbot.raspberrypi_api.models.payloads.responses.robot.*;
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
        }else if (drinkQueue.isEmpty()) {
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
        while (!stopped) Thread.onSpinWait();
        StopRobotResponse response = new StopRobotResponse();
        response.setDrinksLostInProgress(drinksLostInProgress);
        response.setFailedDrinksMade(failedDrinksMade);
        response.setSuccessfulDrinksMade(successfulDrinksMade);
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
        }
        else if (makingDrink) {
            response.setMessage("Robot is currently making a drink.  Please wait.");
            return ResponseEntity.ok().body(response);
        }else if (drinkQueue.isEmpty()) {
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

    //region Private Methods
    private void initialize() {
        print("Initializing Optics");
//        for(Optic optic : opticRepository.findAll()){
//            GpioPinDigitalOutput opticPin = gpio.provisionDigitalOutputPin(RaspiPin.getPinByAddress(optic.getPinNumber()), optic.getIngredient().getName(), PinState.HIGH);
//            opticPins.add(opticPin);
//        }
        started = true;
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
        print("Going home");
        int distance = 0;
//        while (homePin.getState() != PinState.HIGH) {
//            step();
//            distance++;
//        }
        return distance;
    }

    private void pourLiquid(RecipeIngredient recipeIngredient, Optic optic) {
        print("Pouring " + recipeIngredient.getIngredient().getName() + " from pinNumber: " + optic.getPinNumber());
//        GpioPinDigitalOutput opticPin = opticPins.stream().filter(pin -> pinNumber == (pin.getPin().getAddress())).findAny().orElse(null);
//        opticPin.low();
//        for (int i = 0; i < amount; i++)
//            delayMillis(1000);
//        opticPin.high();
        try {
            delayMillis(recipeIngredient.getAmount() * 1000);
        } catch (Exception e) {
            print("SOMETHING FUCKED UP");
        }
    }

    private void movePosition(int to, int from) {
        int distance = to - from;
        if (distance < 0)
//        {
//            directionPin.low();
            distance = -distance;
//        } else {
//            directionPin.high();
//        }
//        for (int i = 0; i < distance; i++) {
//            step();
//        }
        print("Moving from position " + from + " to position " + to);
        try {
            delayMillis(distance * 50);
        } catch (Exception e) {
            print("Something fucked up");
        }
    }

    private void step() {
//        stepperPin.high();
//        motorDelay();
//        stepperPin.low();
//        motorDelay();
    }

    private void delayNanos(long time) {
        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + time >= end);
        print(end - start);
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

    private void print(Object s){
       System.out.println("--------" + s);
    }
    //endregion
}
