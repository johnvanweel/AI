package nl.johnvanweel.particlefilters.actor.sensor;

import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;

/**
 * User: John van Weel
 * Date: 12/13/11
 * Time: 4:31 PM
 */
public interface ISensor {
    SensorData poll(int xLocation, int yLocation);
}
