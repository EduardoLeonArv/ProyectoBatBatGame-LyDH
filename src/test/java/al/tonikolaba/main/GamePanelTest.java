package al.tonikolaba.main;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import al.tonikolaba.entity.Player;
import al.tonikolaba.gamestate.GameStateManager;

class GamePanelTest {

    @Mock
    private Player mockPlayer;
    @Mock
    private GameStateManager mockGsm;

    private GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gamePanel = new GamePanel(mockPlayer);
        gamePanel.gsm = mockGsm; // Inject the mock GameStateManager
    }

    @Test
    @DisplayName("Test game initialization")
    void testInit() {
        gamePanel.init();
        assertNotNull(gamePanel.image, "Image should be initialized");
        assertNotNull(gamePanel.g, "Graphics2D should be initialized");
        assertTrue(gamePanel.running, "Game should be running");
    }

    @Test
    @DisplayName("Test game loop")
    void testRun() {
        Thread thread = new Thread(gamePanel);
        thread.start();
        gamePanel.running = false; // Stop the game loop
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertFalse(gamePanel.running, "Game should not be running");
    }

    @Test
    @DisplayName("Test key press handling")
    void testKeyPressed() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        gamePanel.keyPressed(keyEvent);
        verify(mockGsm, times(1)).update();
    }

    @Test
    @DisplayName("Test key release handling")
    void testKeyReleased() {
        KeyEvent keyEvent = new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        gamePanel.keyReleased(keyEvent);
        verify(mockGsm, times(1)).update();
    }

    @Test
    @DisplayName("Test screenshot functionality")
    void testScreenshot() {
        gamePanel.screenshot = true;
        gamePanel.drawToScreen();
        assertFalse(gamePanel.screenshot, "Screenshot flag should be reset");
    }

    @Test
    @DisplayName("Test save score functionality")
    void testSaveScore() {
        gamePanel.saveScore();
        assertTrue(gamePanel.isScoreSaved(), "Score should be saved");
    }
}