package sloshbot.raspberrypi_api.models.payloads.responses.robot;

import org.jruby.ir.Tuple;
import sloshbot.raspberrypi_api.models.DAOs.Recipe;

import java.util.LinkedList;
import java.util.Queue;

public class RobotStatsResponse extends RobotResponse{
    private int successfulDrinksMade;
    private int failedDrinksMade;
    private int drinksLostInProgress;
    private Queue<Tuple<String, Recipe>> drinkQueue;

    public Queue<Tuple<String, Recipe>> getDrinkQueue() {
        return drinkQueue;
    }

    public void setDrinkQueue(Queue<Tuple<String, Recipe>> drinkQueue) {
        this.drinkQueue = drinkQueue;
    }

    public int getSuccessfulDrinksMade() {
        return successfulDrinksMade;
    }

    public void setSuccessfulDrinksMade(int successfulDrinksMade) {
        this.successfulDrinksMade = successfulDrinksMade;
    }

    public int getFailedDrinksMade() {
        return failedDrinksMade;
    }

    public void setFailedDrinksMade(int failedDrinksMade) {
        this.failedDrinksMade = failedDrinksMade;
    }

    public int getDrinksLostInProgress() {
        return drinksLostInProgress;
    }

    public void setDrinksLostInProgress(int drinksLostInProgress) {
        this.drinksLostInProgress = drinksLostInProgress;
    }
}
