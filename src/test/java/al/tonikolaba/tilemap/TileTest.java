package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testConstructor() {
        BufferedImage image = null;
        int type = 1;
        Tile tile = new Tile(image, type);

        assertNotNull(tile);
    }

    @Test
    public void testGetType() {
        int type = 1;
        Tile tile = new Tile(null, type);

        assertEquals(type, tile.getType());
    }

    @Test
    public void testGetImage() {
        BufferedImage image = null;
        Tile tile = new Tile(image, 1);

        assertEquals(image, tile.getImage());
    }
}
