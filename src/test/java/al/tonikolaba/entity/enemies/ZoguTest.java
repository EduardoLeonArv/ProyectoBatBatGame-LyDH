package al.tonikolaba.entity.enemies;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ZoguTest {

    @Test
    @DisplayName("Test Zogu Initialization")
    public void testZoguInitialization() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);

        Zogu zogu = new Zogu(tmMock, playerMock);

        assertNotNull("Zogu should be initialized", zogu);
        assertEquals("Zogu should have max health of 2", 2, zogu.getMaxHealth());
        assertEquals("Zogu should have initial health of 2", 2, zogu.getHealth());
    }

    @Test
    @DisplayName("Test Zogu Update Logic")
    public void testZoguUpdateLogic() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);

        Zogu zogu = new Zogu(tmMock, playerMock);

        // Initial position
        zogu.x = 0;
        zogu.y = 0;

        // Call update several times and verify movement
        for (int i = 0; i < 10; i++) {
            double prevX = zogu.x;
            double prevY = zogu.y;
            zogu.update();
            assertNotEquals("Zogu's X position should change", prevX, zogu.x);
            assertNotEquals("Zogu's Y position should change", prevY, zogu.y);
        }
    }

    @Test
    @DisplayName("Test Zogu Flinching Behavior")
    public void testZoguFlinching() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);

        Zogu zogu = new Zogu(tmMock, playerMock);

        // Simulate flinching
        zogu.hit(1); // Assume hit method exists to reduce health
        assertTrue("Zogu should be flinching", zogu.isFlinching());

        // Simulate several updates
        for (int i = 0; i < 6; i++) {
            zogu.update();
        }

        assertFalse("Zogu should stop flinching after 6 updates", zogu.isFlinching());
    }

    @Test
    @DisplayName("Test Zogu Drawing")
    public void testZoguDrawing() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);
        BufferedImage imageMock = mock(BufferedImage.class);

        Zogu zogu = new Zogu(tmMock, playerMock);

        Graphics2D gMock = mock(Graphics2D.class);
        zogu.draw(gMock);

        // Verify drawing logic
        verify(gMock, atLeastOnce()).drawImage(any(), anyInt(), anyInt(), isNull());
    }
}