package nl.johnvanweel.particlefilters.actor.agent;

import nl.johnvanweel.particlefilters.model.WorldMap;
import nl.johnvanweel.particlefilters.ui.WorldPanel;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 3:33 PM
 */
public class Particle {
    private final int x;
    private final int y;
    private double weight;

    public Particle(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "x=" + x +
                ", y=" + y +
                ", weight=" + weight +
                '}';
    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(getX() - 2, getY() - 2, 2, 2);
    }
}
