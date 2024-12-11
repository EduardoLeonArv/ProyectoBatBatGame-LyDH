package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.enemies.Zogu;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
    public void testHandleInput() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        Player player = mock(Player.class);
        gameState.player = player;
        gameState.blockInput = false;

        Keys.setPressed(Keys.ESCAPE, true);
        gameState.handleInput();

        Keys.setPressed(Keys.ESCAPE, false);
        Keys.setPressed(Keys.BUTTON1, true);
        gameState.handleInput();

        verify(player).setJumping(true);
    }

    @Test
    public void testReset() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);
        Player player = mock(Player.class);
        gameState.player = player;
        gameState.playerXStart = 50;
        gameState.playerYStart = 100;

        gameState.reset();

        verify(player).reset();
        verify(player).setPosition(50, 100);
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
}
