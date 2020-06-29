package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public class MakeDrinkResponse extends RobotResponse {
    private int currentPosition;
    private String drinkName;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }
}
