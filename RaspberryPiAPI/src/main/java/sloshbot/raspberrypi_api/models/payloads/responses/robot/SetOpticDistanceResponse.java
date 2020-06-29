package sloshbot.raspberrypi_api.models.payloads.responses.robot;

public class SetOpticDistanceResponse extends RobotResponse {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
