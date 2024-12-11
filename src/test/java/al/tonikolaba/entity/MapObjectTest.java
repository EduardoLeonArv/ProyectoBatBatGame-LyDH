package al.tonikolaba.entity;

import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Rectangle;

public class MapObjectTest {

    @Test
    public void testConstructor() {
        TileMap tileMap = new TileMap(32);
        MapObject mapObject = new MapObject(tileMap);

        assertNotNull(mapObject);
        assertEquals(32, mapObject.tileSize);
    }

    @Test
    public void testSetPosition() {
        TileMap tileMap = new TileMap(32);
        MapObject mapObject = new MapObject(tileMap);
        mapObject.setPosition(50, 75);

        assertEquals(50, mapObject.getx());
        assertEquals(75, mapObject.gety());
    }

    @Test
    public void testIntersects() {
        TileMap tileMap = new TileMap(32);
        MapObject object1 = new MapObject(tileMap);
        MapObject object2 = new MapObject(tileMap);

        object1.setPosition(50, 50);
        object2.setPosition(55, 55);

        object1.cwidth = object1.cheight = 10;
        object2.cwidth = object2.cheight = 10;

        assertTrue(object1.intersects(object2));
    }

    @Test
    public void testNotOnScreen() {
        TileMap tileMap = new TileMap(32);
        MapObject mapObject = new MapObject(tileMap);
        mapObject.setPosition(-50, -50);

        assertTrue(mapObject.notOnScreen());
    }

    @Test
    public void testCalculateCorners() {
        TileMap tileMap = new TileMap(32);
        MapObject mapObject = new MapObject(tileMap);

        mapObject.cwidth = 32;
        mapObject.cheight = 32;

        // Assuming map has open space, no blocked tiles
        mapObject.calculateCorners(64, 64);
        assertFalse(mapObject.topLeft);
        assertFalse(mapObject.topRight);
        assertFalse(mapObject.bottomLeft);
        assertFalse(mapObject.bottomRight);
    }
}
