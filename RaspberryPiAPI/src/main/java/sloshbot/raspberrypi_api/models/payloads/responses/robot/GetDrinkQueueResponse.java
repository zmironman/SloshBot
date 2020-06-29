package sloshbot.raspberrypi_api.models.payloads.responses.robot;

import org.jruby.ir.Tuple;
import sloshbot.raspberrypi_api.models.DAOs.Recipe;

import java.util.Queue;

public class GetDrinkQueueResponse extends RobotResponse {
    private Queue<Tuple<String, Recipe>> queue;
    private int queueSize;

    public Queue<Tuple<String, Recipe>> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Tuple<String, Recipe>> queue) {
        this.queue = queue;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
