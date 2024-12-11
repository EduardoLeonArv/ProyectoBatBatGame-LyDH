package al.tonikolaba.tilemap;

import al.tonikolaba.tilemap.Background;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BackgroundTest {

    @Test
    void testBackgroundInitialization() {
        Background background = new Background("/path/to/image.gif", 0.5);
        assertNotNull(background, "El fondo debería inicializarse correctamente.");
    }

    @Test
    void testSetPosition() {
        Background background = new Background("/path/to/image.gif", 0.5);
        background.setPosition(100, 150);

        assertEquals(50, background.getx(), "La posición x debería ser escalada.");
        assertEquals(75, background.gety(), "La posición y debería ser escalada.");
    }

    @Test
    void testUpdate() {
        Background background = new Background("/path/to/image.gif", 0.5);
        background.setVector(5, -3);
        background.update();

        assertEquals(5, background.getx(), "El vector x debería actualizar la posición.");
        assertEquals(-3, background.gety(), "El vector y debería actualizar la posición.");
    }

    @Test
    void testDraw() {
        Background background = new Background("/path/to/image.gif", 0.5);
        Graphics2D mockGraphics = mock(Graphics2D.class);
        assertDoesNotThrow(() -> background.draw(mockGraphics), "El método draw no debería lanzar excepciones.");
    }
}
