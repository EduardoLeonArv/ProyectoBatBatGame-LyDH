package al.tonikolaba.entity;

import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyProjectileTest {

    private EnemyProjectile mockProjectile;
    private TileMap mockTileMap;

    @BeforeEach
    void setUp() {
        // Mock de TileMap
        mockTileMap = mock(TileMap.class);
        when(mockTileMap.getTileSize()).thenReturn(30);

        // Mock de una implementación de EnemyProjectile
        mockProjectile = mock(EnemyProjectile.class, CALLS_REAL_METHODS);

        // Simular valores iniciales
        mockProjectile.tileMap = mockTileMap;
        mockProjectile.damage = 5;
        mockProjectile.remove = false;
        mockProjectile.hit = false;
    }

    @Test
    @DisplayName("Verifica inicialización de atributos")
    void testInitialization() {
        assertEquals(5, mockProjectile.getDamage(), "El daño inicial debería ser 5.");
        assertFalse(mockProjectile.shouldRemove(), "El proyectil no debería estar marcado para eliminar al inicio.");
        assertFalse(mockProjectile.hit, "El proyectil no debería estar en estado de impacto al inicio.");
    }

    @Test
    @DisplayName("Simula setHit")
    void testSetHit() {
        // Simula el método setHit
        doNothing().when(mockProjectile).setHit();

        mockProjectile.setHit();

        // Verifica que se llamó
        verify(mockProjectile, times(1)).setHit();
    }

    @Test
    @DisplayName("Simula update")
    void testUpdate() {
        // Simula el método update
        doNothing().when(mockProjectile).update();

        mockProjectile.update();

        // Verifica que se llamó
        verify(mockProjectile, times(1)).update();
    }

    @Test
    @DisplayName("Verifica el estado de eliminación")
    void testShouldRemove() {
        // Cambia el estado de remove
        mockProjectile.remove = true;

        assertTrue(mockProjectile.shouldRemove(), "El proyectil debería estar marcado para eliminarse.");
    }

    @Test
    @DisplayName("Verifica construcción con TileMap")
    void testConstructor() {
        // Crea una implementación concreta para probar el constructor
        EnemyProjectile concreteProjectile = new ConcreteEnemyProjectile(mockTileMap);

        assertNotNull(concreteProjectile, "El objeto EnemyProjectile debería inicializarse correctamente.");
        assertEquals(mockTileMap, concreteProjectile.tileMap, "El TileMap debería ser el mismo pasado al constructor.");
    }

    // Clase concreta para probar el constructor protegido
    private static class ConcreteEnemyProjectile extends EnemyProjectile {

        protected ConcreteEnemyProjectile(TileMap tm) {
            super(tm);
        }

        @Override
        public void setHit() {
            // Implementación vacía para pruebas
        }

        @Override
        public void update() {
            // Implementación vacía para pruebas
        }
    }
}
