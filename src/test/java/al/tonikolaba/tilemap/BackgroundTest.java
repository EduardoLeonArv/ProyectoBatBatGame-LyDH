package al.tonikolaba.tilemap;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BackgroundTest {
    /*
    private static final String IMAGE_PATH = "/Image.png";
    
    @Test
    void testBackgroundInitialization() {
        Background background = new Background(IMAGE_PATH, 0.5);
        assertNotNull(background, "El fondo debería inicializarse correctamente.");
    }

    @Test
    void testSetPosition() {
        Background background = new Background(IMAGE_PATH, 0.5);
        background.setPosition(100, 150);

        assertEquals(50, background.getx(), "La posición x debería ser escalada.");
        assertEquals(75, background.gety(), "La posición y debería ser escalada.");
    }

    @Test
    void testUpdate() {
        Background background = new Background(IMAGE_PATH, 0.5);
        background.setVector(5, -3);
        background.update();

        assertEquals(5, background.getx(), "El vector x debería actualizar la posición.");
        assertEquals(-3, background.gety(), "El vector y debería actualizar la posición.");
    }

    @Test
    void testDraw() {
        Background background = new Background(IMAGE_PATH, 0.5);
        Graphics2D mockGraphics = mock(Graphics2D.class);
        assertDoesNotThrow(() -> background.draw(mockGraphics), "El método draw no debería lanzar excepciones.");
    }

    @Test
    void testInvalidImagePath() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Background("/invalid/path.gif", 0.5));
        assertTrue(exception.getMessage().contains("No se pudo cargar la imagen"), "Debería lanzar una excepción con un mensaje claro.");
    }

    @Test
    void testMultipleUpdates() {
        Background background = new Background(IMAGE_PATH, 0.5);
        background.setVector(2, 2);

        for (int i = 0; i < 10; i++) {
            background.update();
        }

        assertEquals(20, background.getx(), "La posición x debería ser consistente tras múltiples actualizaciones.");
        assertEquals(20, background.gety(), "La posición y debería ser consistente tras múltiples actualizaciones.");
    }

     */
}
