package nl.johnvanweel.particlefilters.actor.sensor.data;

import nl.johnvanweel.particlefilters.model.TileType;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/13/11
 * Time: 10:40 AM
 */
public class LocalSensorData implements SensorData {
    private final TileType currentTileType;

    private final int dx;
    private final int dy;

    public LocalSensorData(TileType currentTileType, int dx, int dy) {
        this.currentTileType = currentTileType;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public double matchesWith(SensorData data) {
        if (data instanceof LocalSensorData) {
            LocalSensorData d = (LocalSensorData) data;
            if (d.getDx() == dx && d.getDy() == dy) {
                if (currentTileType == d.getCurrentTileType()) {
                    return 0.8D;
                } else {
                    return 0.2D;
                }
            }


        }

        return 0D;
    }

    public TileType getCurrentTileType() {
        return currentTileType;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
