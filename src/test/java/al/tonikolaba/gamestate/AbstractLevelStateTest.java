package al.tonikolaba.gamestate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.enemies.Flyer.FlyerType;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

public class AbstractLevelStateTest {

    @Test
    @DisplayName("Test AbstractLevelState Initialization")
    public void testInit() {
        // Mock dependencies
        GameStateManager gsmMock = mock(GameStateManager.class);
        Player playerMock = mock(Player.class);

        // Create anonymous subclass to test abstract methods
        AbstractLevelState levelState = new AbstractLevelState(gsmMock, playerMock) {
            @Override
            protected int[][] getEnemyCoordinates() {
                return new int[][]{{100, 200}, {300, 400}};
            }

            @Override
            protected EnemyType[] getEnemyTypes() {
                return new EnemyType[]{EnemyType.RED_ENERGY, EnemyType.UFO};
            }

            @Override
            protected String getMapPath() {
                return "/maps/testmap.tmx";
            }

            @Override
            protected String getMusicPath() {
                return "/music/testmusic.mp3";
            }
        };

        // Call init and ensure no exceptions occur
        levelState.init(1);
        assertNotNull("Background should be initialized", levelState.temple);
    }
}