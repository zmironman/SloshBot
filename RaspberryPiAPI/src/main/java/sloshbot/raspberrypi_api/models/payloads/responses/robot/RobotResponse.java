package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public abstract class RobotResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
