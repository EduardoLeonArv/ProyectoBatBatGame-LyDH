package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Level1StateTest {

    private Level1State level1State;
    private Player player;
    private GameStateManager gsm;

    @BeforeEach
    void setUp() {
        // Crear un mock de TileMap
        TileMap mockTileMap = mock(TileMap.class);
        when(mockTileMap.getTileSize()).thenReturn(30);
        when(mockTileMap.getWidth()).thenReturn(800);
        when(mockTileMap.getHeight()).thenReturn(600);

        player = new Player(mockTileMap);
        gsm = new GameStateManager(player);

        level1State = new Level1State(gsm, player);
        level1State.tileMap = mockTileMap;

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
    void testUpdateLogic() {
        // Simular que el jugador pierde toda su salud
        player.setHealth(0);
        level1State.update();

        assertTrue(player.getHealth() <= 0, "La salud del jugador debería reflejar la muerte.");
        // Aquí puedes agregar más verificaciones relacionadas con la lógica tras la muerte.
    }



    @Test
    void testSetupTitle() {
        level1State.setupTitle(new int[] { 0, 0, 178, 19 }, new int[] { 0, 33, 93, 13 });

        assertNotNull(level1State.title, "El título debería configurarse correctamente.");
        assertNotNull(level1State.subtitle, "El subtítulo debería configurarse correctamente.");
        // Validar dimensiones y posiciones si es relevante.
    }

}