package al.tonikolaba.main;

import static org.junit.jupiter.api.Assertions.*;
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
    private static final Logger LOGGER = Logger.getLogger(BatBatGameTest.class.getName());

    @BeforeEach
    public void setUp() {
        game = new BatBatGame();
    }

    @Test
    @DisplayName("Test Save Score")
    public void testSaveScore() {
        try {
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
                assertNotNull(line, "Score file should not be empty");
                assertTrue(line.contains("TestPlayer") && line.contains("100"), "Score file should contain the player's name and score");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error in testSaveScore", e);
            fail("Exception thrown during test: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test Player Initialization")
    public void testPlayerInitialization() {
        try {
            // Arrange
            TileMap tileMap = new TileMap(30);

            // Act
            Player player = new Player(tileMap);
            player.setName("TestPlayer");

            // Assert
            assertEquals("TestPlayer", player.getName(), "Player name should be set correctly");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in testPlayerInitialization", e);
            fail("Exception thrown during test: " + e.getMessage());
        }
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
            LOGGER.log(Level.SEVERE, "Error in testGameWindowInitialization", e);
            fail("Game window initialization failed: " + e.getMessage());
        } finally {
            System.setIn(sysInBackup); // Restore System.in
        }

        // Assert
        assertNotNull(game, "Game window should be initialized");
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

            // Invoke game lifecycle methods explicitly
            game.saveScore("TestPlayer", 200);
            SwingUtilities.invokeAndWait(() -> {
                game.dispose();
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in testFullGameLifecycle", e);
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
            LOGGER.log(Level.SEVERE, "Error in testLoggingDuringInitialization", e);
            fail("Game run failed: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }

        String logContent = logOutput.toString();
        assertTrue(logContent.contains("Enter your player name"), "Log should contain player name prompt");
    }

    @Test
    @DisplayName("Test Run Method Explicitly")
    public void testRunMethod() {
        try {
            // Act
            game.run();

            // Assert
            assertNotNull(game, "Game instance should not be null after run");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in testRunMethod", e);
            fail("Exception thrown during test: " + e.getMessage());
        }
    }
}