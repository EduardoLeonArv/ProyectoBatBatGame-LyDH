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
    void testHandleObjects() {
        GameState gameState = mock(GameState.class, CALLS_REAL_METHODS);

        TileMap tileMap = mock(TileMap.class);
        Player player = mock(Player.class);

        // Configurar enemigos
        Enemy enemy1 = mock(Enemy.class);
        when(enemy1.isDead()).thenReturn(false);
        Enemy enemy2 = mock(Enemy.class);
        when(enemy2.isDead()).thenReturn(true);
        when(enemy2.getx()).thenReturn(100);
        when(enemy2.gety()).thenReturn(200);

        // Configurar proyectiles
        EnemyProjectile projectile1 = mock(EnemyProjectile.class);
        when(projectile1.shouldRemove()).thenReturn(false);
        EnemyProjectile projectile2 = mock(EnemyProjectile.class);
        when(projectile2.shouldRemove()).thenReturn(true);

        // Configurar explosiones
        Explosion explosion = mock(Explosion.class);
        when(explosion.shouldRemove()).thenReturn(true);

        ArrayList<Enemy> enemies = new ArrayList<>(Arrays.asList(enemy1, enemy2));
        ArrayList<EnemyProjectile> projectiles = new ArrayList<>(Arrays.asList(projectile1, projectile2));
        ArrayList<Explosion> explosions = new ArrayList<>(Arrays.asList(explosion));

        gameState.handleObjects(tileMap, enemies, projectiles, explosions);

        // Validar que los enemigos y proyectiles eliminados son removidos
        assertEquals(1, enemies.size(), "Solo debería quedar un enemigo.");
        assertEquals(1, projectiles.size(), "Solo debería quedar un proyectil.");
        assertEquals(0, explosions.size(), "No deberían quedar explosiones.");
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
