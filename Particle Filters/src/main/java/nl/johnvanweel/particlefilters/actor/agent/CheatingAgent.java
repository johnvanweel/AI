package nl.johnvanweel.particlefilters.actor.agent;

import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

/**
 * User: John van Weel
 * Date: 12/15/11
 * Time: 12:27 PM
 */
public class CheatingAgent implements IAgent {
    private Point location;

    public CheatingAgent(int x, int y) {
        location = new Point(x, y);
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void assessMovement(int dX, int dY, SensorData[] sensorData) {
        location.x += dX;
        location.y += dY;
    }

    @Override
    public Point getLocation() {
        return location;
    }
}
