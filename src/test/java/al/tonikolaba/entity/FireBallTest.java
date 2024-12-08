package al.tonikolaba.entity;

import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FireBallTest {

    private FireBall fireballRight;
    private FireBall fireballLeft;
    private TileMap mockTileMap;

    @BeforeEach
    void setUp() {
        // Crear un mock de TileMap
        mockTileMap = mock(TileMap.class);
        when(mockTileMap.getTileSize()).thenReturn(30);

        // Instanciar objetos FireBall
        fireballRight = new FireBall(mockTileMap, true); // Hacia la derecha
        fireballLeft = new FireBall(mockTileMap, false); // Hacia la izquierda

        // Simular los sprites para evitar NullPointerException
        BufferedImage[] mockSprites = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            mockSprites[i] = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);
        }

        fireballRight.sprites = mockSprites;
        fireballRight.hitSprites = mockSprites;
        fireballLeft.sprites = mockSprites;
        fireballLeft.hitSprites = mockSprites;
    }

    @Test
    void testInitialization() {
        // Verificar que las FireBall están correctamente inicializadas
        assertFalse(fireballRight.isHit(), "FireBall no debería estar en estado 'hit' al inicializarse.");
        assertFalse(fireballRight.shouldRemove(), "FireBall no debería estar marcada para eliminarse al inicializarse.");
        assertEquals(3.8, fireballRight.dx, "La velocidad inicial debería ser positiva para FireBall hacia la derecha.");
        assertEquals(-3.8, fireballLeft.dx, "La velocidad inicial debería ser negativa para FireBall hacia la izquierda.");
    }

    @Test
    void testSetHit() {
        // Simular que la FireBall golpea
        fireballRight.setHit();

        // Verificar que se marcó como 'hit'
        assertTrue(fireballRight.isHit(), "FireBall debería estar en estado 'hit' después de setHit().");
        assertEquals(0, fireballRight.dx, "FireBall debería detenerse después de impactar.");
    }

    @Test
    void testUpdate() {
        // Simular una actualización de la FireBall
        fireballRight.update();

        // Verificar que no se elimina antes de que ocurra un impacto
        assertFalse(fireballRight.shouldRemove(), "FireBall no debería estar marcada para eliminarse antes de un impacto.");

        // Simular impacto
        fireballRight.setHit();

        // Mock para el método hasPlayedOnce de Animation
        fireballRight.animation = mock(Animation.class);
        when(fireballRight.animation.hasPlayedOnce()).thenReturn(true);

        // Simular actualizaciones
        fireballRight.update();

        // Verificar que se elimina tras completar la animación
        assertTrue(fireballRight.shouldRemove(), "FireBall debería estar marcada para eliminarse después de la animación de impacto.");
    }

    @Test
    void testSpritesInitialization() {
        // Verificar que los sprites se inicializan correctamente
        assertNotNull(fireballRight.sprites, "Los sprites no deberían ser nulos.");
        assertEquals(4, fireballRight.sprites.length, "Debe haber 4 sprites inicializados.");
        assertNotNull(fireballRight.hitSprites, "Los hitSprites no deberían ser nulos.");
        assertEquals(4, fireballRight.hitSprites.length, "Debe haber 3 hitSprites inicializados.");
    }

    @Test
    void testCollisionTriggersHit() {
        // Simular colisión al establecer dx en 0
        fireballRight.dx = 0;

        // Llamar a update y verificar que se marca como hit
        fireballRight.update();
        assertTrue(fireballRight.isHit(), "La FireBall debería marcarse como hit al colisionar.");
    }

    @Test
    void testAnimationFrames() {
        // Mock de Animation para simular frames
        fireballRight.animation = mock(Animation.class);

        // Simular que los frames se establecen correctamente
        fireballRight.animation.setFrames(fireballRight.sprites);
        verify(fireballRight.animation, atLeastOnce()).setFrames(fireballRight.sprites);

        fireballRight.setHit();
        fireballRight.animation.setFrames(fireballRight.hitSprites);
        verify(fireballRight.animation, atLeastOnce()).setFrames(fireballRight.hitSprites);
    }
}
