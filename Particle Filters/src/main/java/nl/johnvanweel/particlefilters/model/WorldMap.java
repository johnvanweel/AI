package nl.johnvanweel.particlefilters.model;

import nl.johnvanweel.particlefilters.actor.Robot;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/12/11
 * Time: 11:30 AM
 */
public class WorldMap {
    @Autowired
    private Robot[] robots;

    private final Tile[] tiles;
    private int amountXTyles = 22;
    private int amountYTyles = 9;

    private Map<Robot, Point> robotLocationMap = new HashMap<>();
    
    private Tile rewardTile;

    @PostConstruct
    public void initRobotLocations() {
        for (Robot r : robots) {
            robotLocationMap.put(r, new Point(35, 120));
        }

        generateRandomRewardTile();
    }

    public void moveRobot(Robot r, int xDiff, int yDiff) {
        Point robotLocation = robotLocationMap.get(r);
        Tile oldTile = getTileAtCoords(robotLocation.x, robotLocation.y);
        Tile newTile = getTileAtCoords(robotLocation.x + xDiff, robotLocation.y + yDiff);

        System.out.println(String.format("%s - %s", oldTile, getTileNorthOf(oldTile)));
        
        if (newTile.getType() != TileType.BLOCK) {
            robotLocation.x += xDiff;
            robotLocation.y += yDiff;
        }
        
        if (oldTile != newTile){
            switch (newTile.getType()){
                case SPACE:
                    r.reward(-2);
                    break;
                case REWARD:
                    r.reward(20);
                    newTile.setType(TileType.SPACE);
                    generateRandomRewardTile();
                    break;
                default:
                    r.reward(0);
            }
        }
        
    }
    
    public Tile getTileNorthOf(Tile oldTile){
        for (Tile tt : tiles){
            if (tt.getyPos() == oldTile.getyPos()-1 && tt.getxPos() == oldTile.getxPos()){
                return tt;
            }
        }

        return null;
    }

    public Tile getTileSouthOf(Tile oldTile){
        for (Tile tt : tiles){
            if (tt.getyPos() == oldTile.getyPos()+1 && tt.getxPos() == oldTile.getxPos()){
                return tt;
            }
        }

        return null;
    }

    public Tile getTileEastOf(Tile oldTile){
        for (Tile tt : tiles){
            if (tt.getyPos() == oldTile.getyPos() && tt.getxPos() == oldTile.getxPos()+1){
                return tt;
            }
        }

        return null;
    }

    public Tile getTileWestOf(Tile oldTile){
        for (Tile tt : tiles){
            if (tt.getyPos() == oldTile.getyPos() && tt.getxPos() == oldTile.getxPos()-1){
                return tt;
            }
        }

        return null;
    }

    private void generateRandomRewardTile() {
        Tile t = null;
        while (t == null || t.getType() != TileType.SPACE){
            t = tiles[new Random().nextInt(tiles.length)];
        }
        
        t.setType(TileType.REWARD);
        
        this.rewardTile = t;
    }

    public Point getRobotLocation(Robot r) {
        return robotLocationMap.get(r);
    }

    public WorldMap(Tile[] tiles) {
        this.tiles = tiles;

        for (Tile t : tiles) {
            if (amountXTyles < t.getxPos()) {
                amountXTyles = t.getxPos();
            }

            if (amountYTyles < t.getyPos()) {
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
        for (Tile tile : getTiles()) {
            if (tile.getType() == TileType.BLOCK) {
                g.setColor(Color.GRAY);
                Rectangle r = getDimension(tile, width, height);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            } else if (tile.getType() == TileType.REWARD){
                g.setColor(Color.RED);
                Rectangle r = getDimension(tile, width, height);
                g.fillRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
            }
        }

        for (int i = 0; i < robots.length; i++) {
            Point loc = getRobotLocation(robots[i]);
            g.setColor(Color.RED);
            g.fillOval(loc.x - 10, loc.y - 10, 20, 20);

            Point robotLocation = robots[i].getLocation();
            String robotText = String.format("Robot %s: %s,%s", robots[i], robotLocation.x, robotLocation.y);

            g.drawChars(robotText.toCharArray(), 0, robotText.length(), 10, 10 + 15 * (i+1));

            String actualText = String.format("Robot %s actual %s,%s", robots[i],(int) getRobotLocation(robots[i]).getX(), (int)getRobotLocation(robots[i]).getY());
            g.setColor(Color.GREEN);
            g.drawChars(actualText.toCharArray(), 0, actualText.length(), 10, 10+30*(i+1));
        }
    }

    public Tile getRewardTile() {
        return rewardTile;
    }
}
