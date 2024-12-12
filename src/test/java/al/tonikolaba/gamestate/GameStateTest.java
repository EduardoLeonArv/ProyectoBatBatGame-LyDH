package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.EnemyProjectile;
import al.tonikolaba.entity.Explosion;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.enemies.Zogu;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameStateTest {

    @Test
    public void testInit() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        gameState.init(3);
        assertEquals(3, gameState.nextLevelState);
    }

    @Test
    public void testSetupGameObjects() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        Player player = mock(Player.class);
        TileMap tileMap = mock(TileMap.class);
        gameState.tileMap = tileMap;

        gameState.player = player;
        gameState.setupGameObjects(100, 100, 200, 200, false);

        verify(player).setPosition(100, 100);
        assertNotNull(gameState.enemies);
        assertNotNull(gameState.explosions);
        assertNotNull(gameState.hud);
        assertNotNull(gameState.teleport);
    }

    @Test
    public void testCreateEnemy() throws Exception {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        TileMap tileMap = mock(TileMap.class);
        Player player = mock(Player.class);
        gameState.tileMap = tileMap;
        gameState.player = player;

        // Acceder al método privado mediante reflexión
        Method createEnemyMethod = GameState.class.getDeclaredMethod("createEnemy", Enemy.EnemyType.class, int.class, int.class);
        createEnemyMethod.setAccessible(true);

        Enemy enemy = (Enemy) createEnemyMethod.invoke(gameState, Enemy.EnemyType.ZOGU, 100, 200);

        assertNotNull(enemy);
        assertTrue(enemy instanceof Zogu);
    }

    @Test
    void testEventStartFunc() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        gameState.eventStart = true;

        gameState.tb = new ArrayList<>();
        gameState.eventCount = 0;

        for (int i = 0; i < 60; i++) {
            gameState.eventStartFunc();
        }

        assertFalse(gameState.eventStart, "El evento de inicio debería terminar después de 60 actualizaciones.");
        assertEquals(0, gameState.tb.size(), "No deberían quedar cuadros de transición.");
    }
}
