package al.tonikolaba.tilemap;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class BackgroundTest {

    private Background background;

    @BeforeEach
    void setUp() {
        // Mock image data
        String mockImagePath = "/mockImage.png";
        background = new Background(mockImagePath, 0.1);
    }

    @Test
    @DisplayName("Test Background Initialization with Default Scale")
    void testInitializationDefaultScale() {
        assertNotNull(background, "Background should be initialized");
    }

    @Test
    @DisplayName("Test Setting Position")
    void testSetPosition() {
        background.setPosition(50, 30);
        assertEquals(50 * 0.1 % background.getWidth(), background.getx(), 0.01, "x position should scale properly");
        assertEquals(30 * 0.1 % background.getHeight(), background.gety(), 0.01, "y position should scale properly");
    }

    @Test
    @DisplayName("Test Setting Vector")
    void testSetVector() {
        background.setVector(2, 3);
        background.update();
        assertEquals(2.0, background.getx(), 0.01, "x position should update properly");
        assertEquals(3.0, background.gety(), 0.01, "y position should update properly");
    }

    @Test
    @DisplayName("Test Update Wrap Around")
    void testUpdateWrapAround() {
        background.setDimensions(100, 100);
        background.setVector(-150, 150);
        background.update();
        assertTrue(background.getx() >= 0, "x position should wrap around properly");
        assertTrue(background.gety() >= 0, "y position should wrap around properly");
    }

    @Test
    @DisplayName("Test Draw Method")
    void testDraw() {
        BufferedImage mockImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = mockImage.createGraphics();

        background.draw(graphics);
        graphics.dispose();

        // Assert no exceptions occurred during drawing
        assertNotNull(mockImage, "Image should be drawn without exceptions");
    }

    @Test
    @DisplayName("Test Draw with Negative Coordinates")
    void testDrawNegativeCoordinates() {
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        background.setPosition(-100, -100);
        background.draw(graphics);
        graphics.dispose();
        assertNotNull(image, "Image should handle negative coordinates without errors");
    }

}
