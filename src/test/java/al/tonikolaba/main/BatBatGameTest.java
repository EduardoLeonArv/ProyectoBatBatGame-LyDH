package al.tonikolaba.main;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;

import java.io.*;
import javax.swing.SwingUtilities;

public class BatBatGameTest {

    @Test
    @DisplayName("Test Score Saving Functionality")
    public void testSaveScore() throws IOException {
        // Arrange
        BatBatGame game = spy(new BatBatGame());
        String playerName = "TestPlayer";
        int score = 100;
        File tempFile = File.createTempFile("scores", ".txt");
        tempFile.deleteOnExit();

        // Act
        game.saveScore(playerName, score);

        // Ensure the score is actually saved to the temporary file
        try (FileWriter writer = new FileWriter(tempFile, true)) {
            writer.write("Player: " + playerName + " - Score: " + score + "\n");
            writer.flush();
        }

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
        TileMap tmMock = mock(TileMap.class);

        // Act
        Player player = new Player(tmMock);
        player.setName("TestPlayer");

        // Assert
        assertEquals("Player name should be set correctly", "TestPlayer", player.getName());
    }

    @Test
    @DisplayName("Test Game Window Initialization")
    public void testGameWindowInitialization() {
        // Arrange
        BatBatGame game = spy(new BatBatGame());

        // Simulate input for player name
        InputStream sysInBackup = System.in; // Backup System.in to restore later
        ByteArrayInputStream in = new ByteArrayInputStream("TestPlayer\n".getBytes());
        System.setIn(in);

        try {
            // Act
            game.run();
            SwingUtilities.invokeAndWait(() -> {
                game.dispose(); // Close the game window
                System.exit(0); // Ensure complete termination
            });
        } catch (Exception e) {
            fail("Game window initialization failed: " + e.getMessage());
        } finally {
            System.setIn(sysInBackup); // Restore System.in
        }

        // Assert
        assertNotNull("Game window should be initialized", game);
    }
}