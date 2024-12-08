package al.tonikolaba.entity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import al.tonikolaba.tilemap.TileMap;

/**
 * Test for the Teleport class.
 */
public class TeleportTest {

    private Teleport teleport;
    private TileMap tileMap;

    @BeforeEach
    public void setUp() {
        // Create a mock or dummy TileMap
        tileMap = new TileMap(30);
        teleport = new Teleport(tileMap);
    }

    @Test
    public void testTeleportInitialization() {
        // Check if the Teleport object is initialized
        assertNotNull(teleport, "Teleport object should be initialized.");
    }

    @Test
    public void testAnimationFrames() {
        // Verify if animation frames are set up
        teleport.update(); // Call update to initialize animations
        assertNotNull(teleport.animation, "Animation should not be null.");
        
        // Verify that the frame value is valid
        assertTrue(teleport.animation.getFrame() >= 0, "Animation frame index should be non-negative.");
    }


}
