package nl.johnvanweel.particlefilters.actor;

import nl.johnvanweel.particlefilters.actor.agent.IAgent;
import nl.johnvanweel.particlefilters.actor.sensor.ISensor;
import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 3:03 PM
 */
public class Robot {
    @Autowired
    private WorldMap map;

    private final IAgent agent;
    private final ISensor[] sensors;

    private int xLocation;
    private int yLocation;

    public Robot(int x, int y, IAgent agent, ISensor[] sensors) {
        xLocation = x;
        yLocation = y;
        this.agent = agent;
        this.sensors = sensors;
    }

    public void move(int xDiff, int yDiff) {
        xLocation += xDiff + (new Random().nextInt(2) - 1);
        yLocation += yDiff + (new Random().nextInt(2) - 1);

        agent.assessMovement(xDiff, yDiff, readSensor());
    }

    public SensorData[] readSensor() {
        SensorData[] result = new SensorData[sensors.length];

        for (int i = 0; i < sensors.length; i++) {
            result[i] = sensors[i].poll(xLocation, yLocation);
        }

        return result;
    }

    public int getXLocation() {
        return xLocation;
    }

    public int getYLocation() {
        return yLocation;
    }

    public void render(Graphics g) {
        agent.render(g);

        g.setColor(Color.RED);
        g.fillOval(getXLocation() - 10, getYLocation() - 10, 20, 20);
        g.setColor(Color.BLUE);
        g.drawLine(getXLocation(), getYLocation(), getXLocation() + 1, getYLocation() + 1);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "xLocation=" + xLocation +
                ", yLocation=" + yLocation +
                '}';
    }
}
