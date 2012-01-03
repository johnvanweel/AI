package nl.johnvanweel.particlefilters.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 2:33 PM
 *
 */
public class Tile {
    private TileType type;

    private final int xPos;
    private final int yPos;

    public Tile(TileType type, int xPos, int yPos) {
        this.type = type;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public TileType getType() {
        return type;
    }
}
