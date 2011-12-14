package nl.johnvanweel.particlefilters.actor.sensor;

import nl.johnvanweel.particlefilters.actor.sensor.data.LocalSensorData;
import nl.johnvanweel.particlefilters.actor.sensor.data.SensorData;
import nl.johnvanweel.particlefilters.model.Tile;
import nl.johnvanweel.particlefilters.model.WorldMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

/**
 * User: John van Weel
 * Date: 12/13/11
 * Time: 4:33 PM
 */
public class AdjecentTileSensor implements ISensor {
    @Autowired
    private WorldMap map;

    private final int dx;
    private final int dy;

    public AdjecentTileSensor(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }


    @Override
    public SensorData poll(int xLocation, int yLocation) {
        Tile t = map.getTileAtCoords(xLocation, yLocation);
        if (t != null) {
            Rectangle tileShape = map.getDimension(t, 640, 460);

            int otherX = (int) (xLocation + (dx * tileShape.getWidth()));
            int otherY = (int) (yLocation + (dy * tileShape.getHeight()));
            Tile otherTile = map.getTileAtCoords(otherX, otherY);
            if (otherTile == null){
                return new LocalSensorData(null, dx, dy);
            }

            return new LocalSensorData(otherTile.getType(), dx, dy);
        }

        return new LocalSensorData(null, dx, dy);
    }
}
