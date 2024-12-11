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
    void testInit() {
        TileMap mockTileMap = mock(TileMap.class);
        Player mockPlayer = mock(Player.class);
        Level1State level1State = new Level1State(new GameStateManager(mockPlayer), mockPlayer);

        level1State.init(2);
        assertNotNull(level1State.enemyTypesInLevel, "Los tipos de enemigos deberían inicializarse.");
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
    void testPlayerInitialization() {
        Player initializedPlayer = level1State.getPlayer(); // Usar getter para player
        assertNotNull(initializedPlayer, "El jugador debería inicializarse correctamente.");
        assertEquals(100, initializedPlayer.getx(), "El jugador debería posicionarse en x=100 al inicio.");
        assertEquals(191, initializedPlayer.gety(), "El jugador debería posicionarse en y=191 al inicio.");
    }

    @Test
    void testCompleteInitialization() {
        level1State.init(2);

        assertNotNull(level1State.sky, "El fondo 'sky' debería inicializarse.");
        assertNotNull(level1State.clouds, "El fondo 'clouds' debería inicializarse.");
        assertNotNull(level1State.mountains, "El fondo 'mountains' debería inicializarse.");
        assertNotNull(level1State.enemyTypesInLevel, "Los tipos de enemigos deberían inicializarse.");
        assertEquals(12, level1State.enemyTypesInLevel.length, "Debería haber 12 enemigos en el nivel.");
        assertNotNull(level1State.coords, "Las coordenadas de los enemigos deberían inicializarse.");
        assertEquals(12, level1State.coords.length, "Debería haber 12 pares de coordenadas para los enemigos.");
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
    void testInvalidMapPath() {
        Exception exception = assertThrows(RuntimeException.class, () -> level1State.generateTileMap("/invalid/path.map", 0, 140, true));
        assertTrue(exception.getMessage().contains("No se pudo cargar el mapa"), "Debería lanzar una excepción al cargar un mapa inválido.");
    }

    @Test
    void testInvalidBackgroundPath() {
        Exception exception = assertThrows(RuntimeException.class, () -> new Background("/invalid/path.gif", 0.5));
        assertTrue(exception.getMessage().contains("No se pudo cargar la imagen"), "Debería lanzar una excepción al cargar un fondo inválido.");
    }

    @Test
    void testPopulateEnemies() {
        level1State.populateEnemies(level1State.enemyTypesInLevel, level1State.coords);

        assertNotNull(level1State.enemies, "La lista de enemigos no debería ser nula.");
        assertEquals(12, level1State.enemies.size(), "Debería haber 12 enemigos en el nivel.");
        // Aquí puedes agregar más validaciones para los tipos de enemigos.
    }

    @Test
    void testSetupTitle() {
        level1State.setupTitle(new int[] { 0, 0, 178, 19 }, new int[] { 0, 33, 93, 13 });

        assertNotNull(level1State.title, "El título debería configurarse correctamente.");
        assertNotNull(level1State.subtitle, "El subtítulo debería configurarse correctamente.");
        // Validar dimensiones y posiciones si es relevante.
    }

}