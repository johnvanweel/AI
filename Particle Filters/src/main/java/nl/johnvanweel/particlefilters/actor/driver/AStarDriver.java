package nl.johnvanweel.particlefilters.actor.driver;

import nl.johnvanweel.particlefilters.actor.Robot;
import nl.johnvanweel.particlefilters.model.Tile;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.reflect.generics.tree.Tree;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * User: John van Weel
 * Date: 1/3/12
 * Time: 8:02 PM
 */
public class AStarDriver implements IDriver {
    private Robot robot;

    @Autowired
    private WorldMap world;

    public void drive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (robot != null) {
                        try {
                            AStarDriver.this.executeAction();
                        } catch (InterruptedException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
        }).start();



        for (int i = 0; i < 1000; i++){
            Point p = robot.getLocation();
            Tile currentTile = world.getTileAtCoords(p.x, p.y);
        }
    }

    private final Queue<Action> actions = new LinkedList<>();

    private void executeAction() throws InterruptedException {
        synchronized (actions) {
            if (actions.isEmpty()) {
                Thread.sleep(50);
            } else {
                Action a = actions.poll();
                robot.move(a.x, a.y).join();
            }
        }
    }

    private class Action {
        private int x, y;

        private Action(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void setRobot(Robot r) {
        this.robot = r;
    }
}