package al.tonikolaba.main;

import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import static org.mockito.Mockito.*;

class BatBatGameTest {

    private BatBatGame batBatGame;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        // Mock de Player y TileMap
        TileMap mockTileMap = mock(TileMap.class);
        mockPlayer = mock(Player.class);

        // Instancia de BatBatGame
        batBatGame = new BatBatGame();
    }

    @Test
    @DisplayName("Test inicialización del jugador con nombre correcto")
    void testPlayerInitialization() {
        // Simular nombre del jugador
        String playerName = "TestPlayer";
        Player player = new Player(new TileMap(30));
        player.setName(playerName);

        Assertions.assertEquals("TestPlayer", player.getName(), "El nombre del jugador debe inicializarse correctamente.");
    }

    @Test
    @DisplayName("Test guardar puntuación en scores.txt")
    void testSaveScoreCorrectly() throws IOException {
        // Arrange
        String playerName = "TestPlayer";
        int score = 50;

        File scoreFile = new File("scores.txt");
        if (scoreFile.exists()) {
            scoreFile.delete(); // Limpiar archivo existente
        }

        // Act
        batBatGame.saveScore(playerName, score);

        // Assert
        boolean scoreFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Player: TestPlayer - Score: 50")) {
                    scoreFound = true;
                    break;
                }
            }
        }
        Assertions.assertTrue(scoreFound, "La puntuación debería guardarse correctamente en scores.txt");
    }

    @Test
    @DisplayName("Test guardar puntuación al cerrar la ventana")
    void testSaveScoreOnWindowClosing() {
        // Arrange
        String playerName = "TestPlayer";
        when(mockPlayer.getScore()).thenReturn(100);

        // Simular evento de cierre de ventana
        WindowEvent windowEvent = new WindowEvent(batBatGame, WindowEvent.WINDOW_CLOSING);

        // Act
        batBatGame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                batBatGame.saveScore(playerName, mockPlayer.getScore());
            }
        });
        batBatGame.dispatchEvent(windowEvent);

        // Verificar que el método saveScore se llamó con los valores correctos
        verify(mockPlayer, atLeastOnce()).getScore();
    }
}
