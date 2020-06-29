package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public class StartRobotResponse extends RobotResponse {
    private int successfulDrinksMade;
    private int failedDrinksMade;
    private int drinksLostInProgress;

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
