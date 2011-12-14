package nl.johnvanweel.particlefilters;

import nl.johnvanweel.particlefilters.model.Tile;
import nl.johnvanweel.particlefilters.model.TileType;
import nl.johnvanweel.particlefilters.model.WorldMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:29 AM
 * <p/>
 * Factory class that can read a world from a text file. Results in a new instance of a WorldMap, representing the file content.
 */
public class WorldReader {
    private WorldReader() {
    }

    /**
     * Reads the world from the specified file.
     *
     * @param name
     * @return
     * @throws IOException
     */
    public static WorldMap readWorld(String name) throws IOException {
        try (InputStream is = WorldReader.class.getClassLoader().getResourceAsStream(name)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            List<TileType[]> allRows = extractRows(reader);
            List<Tile> tiles = extractTiles(allRows);

            return new WorldMap(tiles.toArray(new Tile[tiles.size()]));
        } catch (IOException e) {
            throw e;
        }
    }

    private static List<Tile> extractTiles(List<TileType[]> allRows) {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < allRows.size(); i++) {
            for (int j = 0; j < allRows.get(i).length; j++) {
                tiles.add(new Tile(allRows.get(i)[j], j, i));
            }
        }
        return tiles;
    }

    private static List<TileType[]> extractRows(BufferedReader reader) throws IOException {
        List<TileType[]> allRows = new ArrayList<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            TileType[] row = parseRow(line);
            allRows.add(row);
        }

        return allRows;
    }

    private static TileType[] parseRow(String line) {
        TileType[] row = new TileType[line.length()];
        for (int i = 0; i < line.length(); i++) {
            row[i] = TileType.findType(Integer.parseInt(Character.toString(line.charAt(i))));
        }
        return row;
    }
}
