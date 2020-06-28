package sloshbot.raspberrypi_api.models.payloads.requests;

public class MakeDrinkRequest {
    private String username;
    private int recipeId;

    //region getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    //endregion

}
