package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.enemies.Flyer;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlyerTest {

    private TileMap mockTileMap;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        mockTileMap = mock(TileMap.class);
        mockPlayer = mock(Player.class);
    }

    @Test
    void testFlyerInitializationUFO() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.UFO, mockPlayer);

        assertEquals(4, flyer.getHealth(), "La salud del UFO debería ser 4.");
        assertEquals(30, flyer.getWidth(), "El ancho del UFO debería ser 30.");
        assertEquals(1, flyer.getDamage(), "El daño del UFO debería ser 1.");
        assertEquals(1.5, flyer.getMoveSpeed(), 0.01, "La velocidad de movimiento del UFO debería ser 1.5.");
    }

    @Test
    void testFlyerInitializationXhelBat() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.XHEL_BAT, mockPlayer);

        assertEquals(1, flyer.getHealth(), "La salud del Xhel Bat debería ser 1.");
        assertEquals(25, flyer.getWidth(), "El ancho del Xhel Bat debería ser 25.");
        assertEquals(0.8, flyer.getMoveSpeed(), 0.01, "La velocidad de movimiento del Xhel Bat debería ser 0.8.");
    }

    @Test
    void testGetNextPositionMovingLeft() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.UFO, mockPlayer);
        flyer.setLeft(true); // Simula movimiento a la izquierda

        flyer.getNextPosition();

        assertEquals(-1.5, flyer.getDx(), 0.01, "dx debería ser -1.5 cuando se mueve a la izquierda.");
    }

    @Test
    void testGetNextPositionFalling() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.UFO, mockPlayer);
        flyer.setFalling(true); // Simula caída

        flyer.getNextPosition();

        assertEquals(0.15, flyer.getDy(), 0.01, "dy debería ser 0.15 al caer.");
    }

    @Test
    void testGetNextPositionJumping() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.XHEL_BAT, mockPlayer);
        flyer.setJumping(true); // Simula salto

        flyer.getNextPosition();

        assertEquals(-5.0, flyer.getDy(), 0.01, "dy debería ser -5.0 al saltar.");
    }

    @Test
    void testFlyerTypeUFO() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.UFO, mockPlayer);

        assertEquals(4, flyer.getHealth(), "La salud inicial del UFO debería ser 4.");
        assertEquals(30, flyer.getWidth(), "El ancho del UFO debería ser 30.");
    }

    @Test
    void testFlyerTypeXhelBat() {
        Flyer flyer = new Flyer(mockTileMap, Flyer.FlyerType.XHEL_BAT, mockPlayer);

        assertEquals(1, flyer.getHealth(), "La salud inicial del Xhel Bat debería ser 1.");
        assertEquals(25, flyer.getWidth(), "El ancho del Xhel Bat debería ser 25.");
    }
}
