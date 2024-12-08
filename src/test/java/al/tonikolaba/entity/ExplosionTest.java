package al.tonikolaba.entity;

import al.tonikolaba.entity.Explosion;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ExplosionTest {

    private Explosion explosion;
    private TileMap mockTileMap;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp() {
        // Mock de TileMap
        mockTileMap = mock(TileMap.class);
        when(mockTileMap.getTileSize()).thenReturn(30);

        // Crear instancia de Explosion sin modificar el constructor
        explosion = new Explosion(mockTileMap, 100, 100);
    }

    @Test
    void testInitialization() {
        // Verificar inicialización
        assertFalse(explosion.shouldRemove(), "Explosion no debe estar marcada para ser eliminada al inicializarse");
    }

    @Test
    void testUpdate() {
        // Inicialmente, la explosión no debe estar marcada para eliminar
        assertFalse(explosion.shouldRemove(), "Explosion no debería estar marcada para eliminación al inicio");

        // Simular actualizaciones hasta que la animación se marque como completada
        while (!explosion.shouldRemove()) {
            explosion.update();
        }

        // Verificar que ahora se marca como eliminada
        assertTrue(explosion.shouldRemove(), "Explosion debería estar marcada para eliminación después de que termine la animación");
    }

    @Test
    void testDraw() {
        // Mock de Graphics2D
        mockGraphics = mock(Graphics2D.class);

        // Dibujar la explosión
        explosion.draw(mockGraphics);

        // Verificar que se intentó dibujar algo
        verify(mockGraphics, atLeastOnce()).drawImage(
                any(BufferedImage.class), anyInt(), anyInt(), isNull()
        );
    }
}
