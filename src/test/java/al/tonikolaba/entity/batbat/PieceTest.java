package al.tonikolaba.entity.batbat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.tilemap.TileMap;

public class PieceTest {

    @Test
    @DisplayName("Test Piece Initialization")
    public void testPieceInitialization() {
        // Mock dependencies
        TileMap tmMock = mock(TileMap.class);

        // Coordinates for the sprite
        int[] mapCoords = {0, 0, 4, 4};

        // Create instance of Piece
        Piece piece = new Piece(tmMock, mapCoords);

        // Assert that the Piece was initialized correctly
        assertNotNull("Piece should have sprites initialized", piece);
        assertEquals("Width should match map coordinates", 4, piece.getWidth());
        assertEquals("Height should match map coordinates", 4, piece.getHeight());
    }

    @Test
    @DisplayName("Test Update Method")
    public void testUpdateMethod() {
        // Mock dependencies
        TileMap tmMock = mock(TileMap.class);

        // Coordinates for the sprite
        int[] mapCoords = {0, 0, 4, 4};

        // Create instance of Piece
        Piece piece = new Piece(tmMock, mapCoords);

        // Simulate movement
        piece.dx = 1;
        piece.dy = 1;
        piece.update();

        // Validate the position
        assertEquals("X position should update", 1, piece.x, 0.001);
        assertEquals("Y position should update", 1, piece.y, 0.001);
    }
}
