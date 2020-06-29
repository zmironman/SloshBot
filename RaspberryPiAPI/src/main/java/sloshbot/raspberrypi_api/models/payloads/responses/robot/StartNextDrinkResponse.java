package sloshbot.raspberrypi_api.models.payloads.responses.robot;

import sloshbot.raspberrypi_api.models.DAOs.Recipe;

public class StartNextDrinkResponse extends RobotResponse {
    private int queueLength;
    private Recipe recipe;
    private String username;

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
