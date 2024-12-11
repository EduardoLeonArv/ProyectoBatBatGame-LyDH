package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TileTest {
    @Test
    void testLoadMapInvalidFile() {
        TileMap tileMap = new TileMap(32);
        assertThrows(Exception.class, () -> tileMap.loadMap("/invalid/map.txt"), "Debería lanzar una excepción para archivos inválidos.");
    }

    @Test
    void testUpdateWhileShaking() {
        TileMap tileMap = new TileMap(32);
        tileMap.setShaking(true, 5);
        double previousX = tileMap.getx();
        tileMap.update();
        assertNotEquals(previousX, tileMap.getx(), "La posición debería cambiar cuando está en modo shaking.");
    }
    @Test
    void testTileInitialization() {
        BufferedImage image = mock(BufferedImage.class);
        Tile tile = new Tile(image, Tile.NORMAL);

        assertNotNull(tile.getImage(), "La imagen debería inicializarse correctamente.");
        assertEquals(Tile.NORMAL, tile.getType(), "El tipo del tile debería ser NORMAL.");
    }

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
