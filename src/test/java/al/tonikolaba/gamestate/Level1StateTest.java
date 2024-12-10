package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Level1StateTest {

    private Level1State level1State;
    private Player player;
    private GameStateManager gsm;

    @BeforeEach
    void setUp() {
        TileMap tileMap = new TileMap(30);
        player = new Player(tileMap);
        gsm = new GameStateManager(player);

        level1State = new Level1State(gsm, player);

        // Simular la inicialización del estado manualmente
        level1State.enemyTypesInLevel = new Enemy.EnemyType[] {
                Enemy.EnemyType.XHELBAT, Enemy.EnemyType.XHELBAT, Enemy.EnemyType.ZOGU
        };
        level1State.coords = new int[][] {
                {1300, 100}, {1320, 100}, {1340, 100}
        };

        level1State.enemies = new ArrayList<>();
        level1State.populateEnemies(level1State.enemyTypesInLevel, level1State.coords);
    }

    @Test
    void testInitialization() {
        assertNotNull(level1State, "Level1State debería inicializarse correctamente.");
        assertNotNull(level1State.enemyTypesInLevel, "Los tipos de enemigos deberían inicializarse.");
        assertEquals(3, level1State.enemyTypesInLevel.length, "Debería haber 3 enemigos inicializados.");
    }

    @Test
    void testTileMapInitialization() {
        assertNotNull(level1State.tileMap, "El TileMap debería inicializarse correctamente.");
        assertEquals(30, level1State.tileMap.getTileSize(), "El tamaño de los tiles debería ser 30.");
    }

    @Test
    void testEnemyPopulation() {
        assertNotNull(level1State.enemies, "La lista de enemigos no debería ser nula.");
        assertEquals(3, level1State.enemies.size(), "Debería haber 3 enemigos poblados en el nivel.");
    }

    @Test
    void testUpdateMethod() {
        level1State.update();
        assertFalse(level1State.blockInput, "blockInput debería ser falso después de la actualización inicial.");
    }

    @Test
    void testSetupTitle() {
        assertNotNull(level1State.title, "El título debería configurarse correctamente.");
        assertNotNull(level1State.subtitle, "El subtítulo debería configurarse correctamente.");
    }

    @Test
    void testPlayerInitialization() {
        Player initializedPlayer = this.player; // Acceder directamente al jugador inicializado
        assertNotNull(initializedPlayer, "El jugador debería inicializarse correctamente.");
        assertEquals(100, initializedPlayer.getx(), "El jugador debería posicionarse en x=100 al inicio.");
        assertEquals(191, initializedPlayer.gety(), "El jugador debería posicionarse en y=191 al inicio.");
    }
}
