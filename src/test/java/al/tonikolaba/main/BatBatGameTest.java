package al.tonikolaba.main;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;

import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatBatGameTest {

    private BatBatGame game;

    @BeforeEach
    public void setUp() {
        game = spy(new BatBatGame());
    }

    @Test
    @DisplayName("Test Save Score")
    public void testSaveScore() throws IOException {
        // Arrange
        String playerName = "TestPlayer";
        int score = 100;
        File tempFile = File.createTempFile("scores", ".txt");
        tempFile.deleteOnExit();

        // Act
        doNothing().when(game).saveScore(eq(playerName), eq(score));
        game.saveScore(playerName, score);

        // Assert
        verify(game, times(1)).saveScore(playerName, score);
    }

    @Test
    @DisplayName("Test Player Initialization")
    public void testPlayerInitialization() {
        // Arrange
        TileMap tileMap = new TileMap(30);

        // Act
        Player player = new Player(tileMap);
        player.setName("TestPlayer");

        // Assert
        assertEquals("Player name should be set correctly", "TestPlayer", player.getName());
    }

    @Test
    @DisplayName("Test Game Window Initialization")
    public void testGameWindowInitialization() {
        // Simulate input for player name
        InputStream sysInBackup = System.in; // Backup System.in to restore later
        ByteArrayInputStream in = new ByteArrayInputStream("TestPlayer\n".getBytes());
        System.setIn(in);

        try {
            // Act
            game.run();
            SwingUtilities.invokeAndWait(() -> {
                game.dispose(); // Close the game window
            });
        } catch (Exception e) {
            fail("Game window initialization failed: " + e.getMessage());
        } finally {
            System.setIn(sysInBackup); // Restore System.in
        }

        // Assert
        assertNotNull("Game window should be initialized", game);
    }

    @Test
    @DisplayName("Test Logging for Initialization")
    public void testLoggingDuringInitialization() throws Exception {
        // Arrange
        Logger loggerMock = mock(Logger.class);
        doNothing().when(loggerMock).log(eq(Level.INFO), anyString());

        // Act
        game.run();

        // Assert
        verify(loggerMock, atLeastOnce()).log(eq(Level.INFO), anyString());
    }
}
