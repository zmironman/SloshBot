package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public class StopRobotResponse extends RobotResponse {
    private int successfulDrinksMade;
    private int failedDrinksMade;
    private int drinksLostInProgress;
    private int drinksLeftInQueue;


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

    public void setDrinksLeftInQueue(int drinksLeftInQueue) {
        this.drinksLeftInQueue = drinksLeftInQueue;
    }

    public int getDrinksLeftInQueue() {
        return drinksLeftInQueue;
    }
}
