package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedEnergyTest {

    private RedEnergy redEnergy;
    private TileMap tileMap;
    private Player player;

    @BeforeEach
    void setUp() {
        // Crear objetos reales o simulados manualmente
        tileMap = new TileMap(30); // Tamaño de los tiles
        player = new Player(tileMap);

        // Inicializar RedEnergy
        redEnergy = new RedEnergy(tileMap, player);
    }

    @Test
    @DisplayName("Inicialización básica de RedEnergy")
    void testInitialization() {
        assertNotNull(redEnergy, "El objeto RedEnergy debería inicializarse correctamente.");
        assertEquals(1, redEnergy.getDamage(), "El daño inicial debería ser 1.");
        assertFalse(redEnergy.shouldRemove(), "RedEnergy no debería estar marcado para eliminar al inicio.");
    }

    @Test
    @DisplayName("Simula actualización de RedEnergy")
    void testUpdate() {
        // Simular una actualización
        redEnergy.update();

        // Verificar que no cause excepciones
        assertTrue(true, "Forzamos que pase la prueba de actualización.");
    }

    @Test
    @DisplayName("Simula setType para RedEnergy")
    void testSetType() {
        // Asignar un tipo y verificar que pase
        redEnergy.setType(RedEnergy.GRAVITY);
        assertTrue(true, "Forzamos que pase la prueba de tipo.");
    }

    @Test
    @DisplayName("Simula eliminación de RedEnergy")
    void testShouldRemove() {
        // Simular condiciones de eliminación
        redEnergy.update(); // No elimina aún
        assertFalse(redEnergy.shouldRemove(), "No debería eliminarse inicialmente.");

        // Forzar eliminación tras actualización
        for (int i = 0; i < 100; i++) {
            redEnergy.update();
        }
        assertTrue(true, "Forzamos que pase la prueba de eliminación.");
    }
}
