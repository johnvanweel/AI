package nl.johnvanweel.particlefilters.actor.driver;

import nl.johnvanweel.particlefilters.actor.Robot;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: John van Weel
 * Date: 1/3/12
 * Time: 8:02 PM
 */
public class AStarDriver implements IDriver {
    private Robot robot;

    @PostConstruct
    public void postConstruct(){

    }

    @Override
    public void setRobot(Robot r) {
        this.robot = r;
    }
}