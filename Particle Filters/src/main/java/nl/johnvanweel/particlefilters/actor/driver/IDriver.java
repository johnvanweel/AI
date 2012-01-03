package nl.johnvanweel.particlefilters.actor.driver;

import nl.johnvanweel.particlefilters.actor.Robot;

/**
 * User: John van Weel
 * Date: 1/3/12
 * Time: 8:01 PM
 */
public interface IDriver {
    void setRobot(Robot r);
    void drive();
}
