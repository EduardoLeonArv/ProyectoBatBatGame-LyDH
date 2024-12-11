package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testConstructor() {
        BufferedImage image = null;
        int type = Tile.NORMAL;
        Tile tile = new Tile(image, type);

        assertNotNull(tile);
        assertEquals(Tile.NORMAL, tile.getType());
        int type = 1;
        Tile tile = new Tile(image, type);

        assertNotNull(tile);
    }

    @Test
    public void testGetType() {
        Tile tile = new Tile(null, Tile.BLOCKED);

        assertEquals(Tile.BLOCKED, tile.getType());
        int type = 1;
        Tile tile = new Tile(null, type);

        assertEquals(type, tile.getType());
    }

    @Test
    public void testGetImage() {
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Tile tile = new Tile(image, Tile.NORMAL);
        BufferedImage image = null;
        Tile tile = new Tile(image, 1);
        assertEquals(image, tile.getImage());
    }
}
