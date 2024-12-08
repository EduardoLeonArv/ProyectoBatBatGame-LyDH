package al.tonikolaba.entity;

import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnergyParticleTest {

    private EnergyParticle energyParticle;
    private TileMap mockTileMap;

    @BeforeEach
    void setUp() {
        // Mock del TileMap
        mockTileMap = mock(TileMap.class);
        when(mockTileMap.getTileSize()).thenReturn(30);
    }

    @Test
    @DisplayName("Verifica inicialización de EnergyParticle")
    void testInitialization() {
        // Inicialización de EnergyParticle con dirección hacia arriba
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, EnergyParticle.ENERGY_UP);

        assertNotNull(energyParticle, "El objeto EnergyParticle debería inicializarse correctamente.");
        assertFalse(energyParticle.shouldRemove(), "EnergyParticle no debería estar marcada para eliminación al inicio.");
    }

    @Test
    @DisplayName("Simula actualización y eliminación")
    void testUpdateAndRemoval() {
        // Inicialización de EnergyParticle con dirección hacia arriba
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, EnergyParticle.ENERGY_UP);

        // Simular múltiples actualizaciones
        for (int i = 0; i < 60; i++) {
            energyParticle.update();
        }

        // Verificar que está marcada para eliminación después de 60 actualizaciones
        assertTrue(energyParticle.shouldRemove(), "EnergyParticle debería estar marcada para eliminación después de 60 actualizaciones.");
    }

    @Test
    @DisplayName("Verifica movimiento en la dirección inicial (ENERGY_UP)")
    void testMovementUp() {
        // Inicialización de EnergyParticle con dirección hacia arriba
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, EnergyParticle.ENERGY_UP);

        double initialX = energyParticle.x;
        double initialY = energyParticle.y;

        // Simular una actualización
        energyParticle.update();

        // Verificar que se movió desde su posición inicial
        assertNotEquals(initialX, energyParticle.x, "La posición X debería cambiar después de la actualización.");
        assertNotEquals(initialY, energyParticle.y, "La posición Y debería cambiar después de la actualización.");
    }

    @Test
    @DisplayName("Verifica movimiento en la dirección ENERGY_LEFT")
    void testMovementLeft() {
        // Inicialización de EnergyParticle con dirección hacia la izquierda
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, EnergyParticle.ENERGY_LEFT);

        double initialX = energyParticle.x;
        double initialY = energyParticle.y;

        // Simular una actualización
        energyParticle.update();

        // Verificar movimiento
        assertNotEquals(initialX, energyParticle.x, "La posición X debería cambiar después de la actualización.");
        assertNotEquals(initialY, energyParticle.y, "La posición Y debería cambiar después de la actualización.");
    }

    @Test
    @DisplayName("Verifica movimiento en la dirección ENERGY_DOWN")
    void testMovementDown() {
        // Inicialización de EnergyParticle con dirección hacia abajo
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, EnergyParticle.ENERGY_DOWN);

        double initialX = energyParticle.x;
        double initialY = energyParticle.y;

        // Simular una actualización
        energyParticle.update();

        // Verificar movimiento
        assertNotEquals(initialX, energyParticle.x, "La posición X debería cambiar después de la actualización.");
        assertNotEquals(initialY, energyParticle.y, "La posición Y debería cambiar después de la actualización.");
    }

    @Test
    @DisplayName("Verifica movimiento en la dirección predeterminada")
    void testMovementDefault() {
        // Inicialización de EnergyParticle con una dirección no válida (predeterminada)
        energyParticle = new EnergyParticle(mockTileMap, 100, 100, 999);

        double initialX = energyParticle.x;
        double initialY = energyParticle.y;

        // Simular una actualización
        energyParticle.update();

        // Verificar movimiento
        assertNotEquals(initialX, energyParticle.x, "La posición X debería cambiar después de la actualización.");
        assertNotEquals(initialY, energyParticle.y, "La posición Y debería cambiar después de la actualización.");
    }
}
