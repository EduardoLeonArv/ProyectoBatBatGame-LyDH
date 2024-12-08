package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class Level1StateTest {

    private Level1State level1State;
    private Player player;
    private GameStateManager gsm;

    @BeforeEach
    void setUp() {
        // Crear instancias de objetos necesarios

        Player player = new Player(new TileMap(30));
        GameStateManager gms = new GameStateManager(player);
        player = new Player(new TileMap(30));
        level1State = new Level1State(gsm, player);
    }

    @Test
    void testInitialization() {
        // Verificar que el nivel inicializa correctamente
        assertNotNull(level1State, "Level1State debería inicializarse correctamente.");
        assertNotNull(level1State.enemyTypesInLevel, "Los tipos de enemigos deberían inicializarse.");
        assertEquals(12, level1State.enemyTypesInLevel.length, "Debería haber 12 enemigos inicializados.");
    }
}
