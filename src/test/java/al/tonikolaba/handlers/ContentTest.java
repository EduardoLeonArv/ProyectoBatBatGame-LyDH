package al.tonikolaba.handlers;

import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

public class ContentTest {

    @Test
    public void testConstructorThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, Content::new);
        assertEquals("Utility Class", exception.getMessage());
    }

    @Test
    public void testGetEnergyParticle() {
        BufferedImage[][] energyParticle = Content.getEnergyParticle();
        assertNotNull(energyParticle);
    }

    @Test
    public void testGetExplosions() {
        BufferedImage[][] explosions = Content.getExplosions();
        assertNotNull(explosions);
    }

    @Test
    public void testGetZogu() {
        BufferedImage[][] zogu = Content.getZogu();
        assertNotNull(zogu);
    }

    @Test
    public void testGetUfo() {
        BufferedImage[][] ufo = Content.getUfo();
        assertNotNull(ufo);
    }

    @Test
    public void testGetXhelbat() {
        BufferedImage[][] xhelbat = Content.getXhelbat();
        assertNotNull(xhelbat);
    }

    @Test
    public void testGetRedEnergy() {
        BufferedImage[][] redEnergy = Content.getRedEnergy();
        assertNotNull(redEnergy);
    }

    @Test
    public void testLoadHandlesExceptions() {
        BufferedImage[][] result = Content.load("/Invalid/Path.gif", 5, 5);
        assertEquals(0, result.length); // Should return an empty array
    }
}
