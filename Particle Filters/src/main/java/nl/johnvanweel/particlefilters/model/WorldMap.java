package nl.johnvanweel.particlefilters.model;

import nl.johnvanweel.particlefilters.WorldReader;
import nl.johnvanweel.particlefilters.exception.TileNotFoundException;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:30 AM
 */
public class WorldMap {
    private final Tile[] tiles;
    private int amountXTyles = 22;
    private int amountYTyles = 9;


    public WorldMap(Tile[] tiles) {
        this.tiles = tiles;

        for (Tile t : tiles){
            if (amountXTyles < t.getxPos()){
                  amountXTyles = t.getxPos();
            }

            if (amountYTyles < t.getyPos()){
                  amountYTyles = t.getyPos();
            }
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getAmountXTyles() {
        return amountXTyles;
    }

    public int getAmountYTyles() {
        return amountYTyles;
    }

    public Rectangle getDimension(Tile tile, int screenWidth, int screenHeight) {
        int tileWidth = screenWidth / getAmountXTyles();
        int tileHeight = screenHeight / getAmountYTyles();

        int x = (screenWidth / getAmountXTyles()) * tile.getxPos();
        int y = (screenHeight / getAmountYTyles()) * tile.getyPos();

        return new Rectangle(x, y, tileWidth, tileHeight);
    }

    public Tile getTileAtCoords(int x, int y) {
        Rectangle findRect = new Rectangle(x, y, 10, 10);
        for (Tile t : tiles) {
            Rectangle possibleMatch = getDimension(t, 640, 460);
            if (findRect.intersects(possibleMatch)) {
                return t;
            }
        }

        return null;
    }

    public void render(Graphics g, int width, int height) {
        g.setColor(Color.GRAY);
        for (Tile tile :getTiles()) {
            if (tile.getType() == TileType.BLOCK) {
                Rectangle r = getDimension(tile, width, height);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }
        }
    }
}
