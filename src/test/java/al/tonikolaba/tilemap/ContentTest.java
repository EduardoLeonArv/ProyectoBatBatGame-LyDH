package al.tonikolaba.tilemap;

import al.tonikolaba.handlers.Content;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ContentTest {
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
    public void testPartialResourceLoad() {
        // Simula una ruta válida pero con dimensiones incorrectas para el recurso.
        BufferedImage[][] result = Content.load("/Sprites/Enemies/Zogu.gif", 100, 100);
        assertNotNull(result);
        assertEquals(0, result.length);
    }

}
