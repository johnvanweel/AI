package nl.johnvanweel.particlefilters.actor.agent;

import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/13/11
 * Time: 3:28 PM
 *
 */
public interface IAgent {
    void render(Graphics g);

    void assessMovement(int dX, int dY, SensorData[] sensorData);
}
