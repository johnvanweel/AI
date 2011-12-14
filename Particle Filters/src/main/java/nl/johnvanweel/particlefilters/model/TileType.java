package nl.johnvanweel.particlefilters.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:33 AM
 *
 */
public enum TileType {
    SPACE(0), BLOCK(1);

    private final int intValue;

    private static final Map<Integer, TileType> typeMap = new HashMap<>();

    static {
        for (TileType type : TileType.values()) {
            typeMap.put(type.getIntValue(), type);
        }
    }

    TileType(int i) {
        this.intValue = i;
    }

    public static TileType findType(int t) {
        if (!typeMap.keySet().contains(t)) {
            throw new IllegalArgumentException("Invalid Tile Type specified!");
        }

        return typeMap.get(t);
    }

    public int getIntValue() {
        return intValue;
    }
}
