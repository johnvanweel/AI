package nl.johnvanweel.particlefilters.actor;

import nl.johnvanweel.particlefilters.actor.agent.IAgent;
import nl.johnvanweel.particlefilters.actor.sensor.ISensor;
import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.Random;

/**
 * User: John van Weel
 * Date: 12/12/11
 * Time: 3:03 PM
 */
public class Robot {
    @Autowired
    private WorldMap world;

    private final IAgent agent;
    private final ISensor[] sensors;

    public Robot( IAgent agent, ISensor[] sensors) {
        this.agent = agent;
        this.sensors = sensors;
    }

    public void move(int xDiff, int yDiff) {
        int xCorrected = xDiff + (new Random().nextInt(2) - 1);
        int yCorrected = yDiff + (new Random().nextInt(2) - 1);

        world.moveRobot(this, xCorrected, yCorrected);
        agent.assessMovement(xDiff, yDiff, readSensor());
    }

    public SensorData[] readSensor() {
        SensorData[] result = new SensorData[sensors.length];

        for (int i = 0; i < sensors.length; i++) {
            Point loc = world.getRobotLocation(this);
            result[i] = sensors[i].poll(loc.x, loc.y);
        }

        return result;
    }


    public void render(Graphics g) {
        agent.render(g);
    }

    public Point getLocation(){
        return agent.getLocation();
    }
}
