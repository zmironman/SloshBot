package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public class StartRobotResponse extends RobotResponse {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
