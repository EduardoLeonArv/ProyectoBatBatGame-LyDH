package al.tonikolaba.tilemap;

import al.tonikolaba.handlers.Content;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ContentTest {

    @Test
    public void testLoadWithValidPath() {
        BufferedImage[][] result = Content.load("/Sprites/Player/EnergyParticle.gif", 5, 5);
        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    public void testLoadWithInvalidPath() {
        BufferedImage[][] result = Content.load("/invalid/path.gif", 5, 5);
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    public void testGetters() {
        assertNotNull(Content.getEnergyParticle());
        assertNotNull(Content.getExplosions());
        assertNotNull(Content.getZogu());
        assertNotNull(Content.getUfo());
        assertNotNull(Content.getXhelbat());
        assertNotNull(Content.getRedEnergy());
    }

    @Test
    public void testStaticResources() {
        // Acceder a los recursos estáticos explícitamente
        assertTrue(Content.getEnergyParticle().length > 0);
        assertTrue(Content.getExplosions().length > 0);
    }
}
