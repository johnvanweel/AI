package nl.johnvanweel.particlefilters.actor.sensor.data;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/13/11
 * Time: 10:39 AM
 *
 */
public interface SensorData {
    abstract double matchesWith(SensorData data);
}
