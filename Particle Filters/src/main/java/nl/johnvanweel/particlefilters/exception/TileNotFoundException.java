package nl.johnvanweel.particlefilters.exception;

/**
 * Created by IntelliJ IDEA.
 * User: John van Weel
 * Date: 12/13/11
 * Time: 9:37 AM
 *
 */
public class TileNotFoundException extends RuntimeException {
    public TileNotFoundException(String s) {
        super(s);
    }
}
