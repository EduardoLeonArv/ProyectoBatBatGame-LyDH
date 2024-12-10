package al.tonikolaba.gamestate;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

public class AbstractLevelStateTest {

    @Test
    @DisplayName("Test LevelTest Initialization")
    public void testLevelTestInit() {
        // Mock dependencies
        GameStateManager gsmMock = mock(GameStateManager.class);
        Player playerMock = mock(Player.class);

        // Create instance of LevelTest
        LevelTest levelTest = new LevelTest(gsmMock, playerMock);

        // Call init and ensure no exceptions occur
        levelTest.init(1);
        assertNotNull("Background should be initialized", levelTest.temple);
    }
}
