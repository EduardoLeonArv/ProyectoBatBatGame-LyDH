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

public class BatBatGameTest {

    private BatBatGame game;

    @BeforeEach
    public void setUp() {
        game = new BatBatGame();
    }

    @Test
    @DisplayName("Test Save Score")
    public void testSaveScore() throws IOException {
        // Arrange
        String playerName = "TestPlayer";
        int score = 100;
        File tempFile = File.createTempFile("scores", ".txt");
        tempFile.deleteOnExit();

        // Redirect output to a temporary file
        System.setProperty("user.dir", tempFile.getParent());

        // Act
        game.saveScore(playerName, score);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line = reader.readLine();
            assertNotNull("Score file should not be empty", line);
            assertTrue("Score file should contain the player's name and score", line.contains("TestPlayer") && line.contains("100"));
        }
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
        } catch (Exception e) {
            fail("Game window initialization failed: " + e.getMessage());
        } finally {
            System.setIn(sysInBackup); // Restore System.in
        }

        // Assert
        assertNotNull("Game window should be initialized", game);
    }

    @Test
    @DisplayName("Test Full Game Lifecycle")
    public void testFullGameLifecycle() {
        // Simulate input for player name
        InputStream sysInBackup = System.in; // Backup System.in to restore later
        ByteArrayInputStream in = new ByteArrayInputStream("TestPlayer\n".getBytes());
        System.setIn(in);

        try {
            // Start game
            game.run();

            // Simulate gameplay actions here (expand if necessary)

            // Ensure game window can close without exceptions
            SwingUtilities.invokeAndWait(() -> {
                game.dispose();
            });
        } catch (Exception e) {
            fail("Game lifecycle failed: " + e.getMessage());
        } finally {
            System.setIn(sysInBackup);
        }
    }

    @Test
    @DisplayName("Test Logging During Initialization")
    public void testLoggingDuringInitialization() {
        // Capture log messages during run
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(logOutput));

        try {
            game.run();
        } catch (Exception e) {
            fail("Game run failed: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }

        String logContent = logOutput.toString();
        assertTrue("Log should contain player name prompt", logContent.contains("Enter your player name"));
    }
}