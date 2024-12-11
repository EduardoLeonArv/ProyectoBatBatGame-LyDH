package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TileMapTest {

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

    @Test
    public void testGetType() {
        TileMap tileMap = new TileMap(32);
        tileMap.loadMap("/path/to/map.txt"); // Asegúrate de que el archivo existe para la prueba.

        int tileType = tileMap.getType(0, 0); // Suponiendo un mapa válido.
        assertEquals(Tile.NORMAL, tileType); // Cambia según el tipo esperado.
    }
    @Test
    public void testUpdateShaking() {
        TileMap tileMap = new TileMap(32);
        tileMap.setShaking(true, 5);
        double initialX = tileMap.getx();
        double initialY = tileMap.gety();

        tileMap.update();

        assertNotEquals(initialX, tileMap.getx());
        assertNotEquals(initialY, tileMap.gety());
    }
    @Test
    public void testDraw() {
        TileMap tileMap = new TileMap(32);
        tileMap.loadMap("/path/to/map.txt"); // Asegúrate de tener un mapa válido.

        Graphics2D g = mock(Graphics2D.class); // Usa Mockito para simular.
        assertDoesNotThrow(() -> tileMap.draw(g));
    }
    @Test
    public void testLoadMapWithInvalidRows() {
        TileMap tileMap = new TileMap(32);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tileMap.loadMap("/path/to/invalid_map.txt"); // Mapa con filas incompletas.
        });
        assertTrue(exception.getMessage().contains("El archivo del mapa tiene menos filas"));
    }



}
