package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileMapTest {

    @Test
    public void testConstructor() {
        TileMap tileMap = new TileMap(32);

        assertNotNull(tileMap);
        assertEquals(32, tileMap.getTileSize());
    }

    @Test
    public void testSetPosition() {
        TileMap tileMap = new TileMap(32);
        tileMap.setPosition(100, 150);

        assertEquals(100, tileMap.getx(), 0.01);
        assertEquals(150, tileMap.gety(), 0.01);
    }

    @Test
    public void testSetShaking() {
        TileMap tileMap = new TileMap(32);
        tileMap.setShaking(true, 10);

        assertTrue(tileMap.isShaking());
    }

    @Test
    public void testFixBounds() {
        TileMap tileMap = new TileMap(32);
        tileMap.setBounds(100, 100, 200, 200);
        tileMap.setPosition(-500, -500);
        tileMap.fixBounds();

        assertTrue(tileMap.getx() >= -100);
        assertTrue(tileMap.gety() >= -100);
    }

    @Test
    public void testLoadTilesHandlesExceptions() {
        TileMap tileMap = new TileMap(32);
        assertDoesNotThrow(() -> tileMap.loadTiles("/invalid/path.png"));
    }

    @Test
    public void testLoadMapHandlesExceptions() {
        TileMap tileMap = new TileMap(32);
        assertDoesNotThrow(() -> tileMap.loadMap("/invalid/map.txt"));
    }
}
