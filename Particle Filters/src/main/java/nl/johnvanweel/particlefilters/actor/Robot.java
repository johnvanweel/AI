package nl.johnvanweel.particlefilters.actor;

import nl.johnvanweel.particlefilters.actor.agent.IAgent;
import nl.johnvanweel.particlefilters.actor.driver.IDriver;
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

    private IDriver driver;

    private final IAgent agent;
    private final ISensor[] sensors;
    private int reward = 0;

    public Robot( IAgent agent, ISensor[] sensors, IDriver driver) {
        this.agent = agent;
        this.sensors = sensors;
        this.driver = driver;

        driver.setRobot(this);
    }

    public void move(final int xDiff, final int yDiff) {
        final int[] xCorrected = {xDiff}; /*+ (new Random().nextInt(2) - 1);*/
        final int[] yCorrected = {yDiff}; /*+ (new Random().nextInt(2) - 1);*/

        new Thread(new Runnable() {
            @Override
            public void run() {
                int dx = 1;
                int dy = 1;
                if (xCorrected[0] < 0){
                    xCorrected[0] = -xCorrected[0];
                    dx = -1;
                }

                if (yCorrected[0] < 0){
                    yCorrected[0] = -yCorrected[0];
                    dy = -1;
                }

                for (int i = 0; i < xCorrected[0];i++){
                    world.moveRobot(Robot.this, dx, 0);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

                for (int i = 0; i < yCorrected[0];i++){
                    world.moveRobot(Robot.this, 0, dy);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

                agent.assessMovement(xDiff, yDiff, readSensor());
            }
        }).start();
//        world.moveRobot(this, xCorrected, yCorrected);
//        agent.assessMovement(xDiff, yDiff, readSensor());
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

    public void reward(int reward) {
        this.reward += reward;
    }
}
