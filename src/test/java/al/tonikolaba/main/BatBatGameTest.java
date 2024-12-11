package al.tonikolaba.main;

import al.tonikolaba.main.BatBatGame;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BatBatGameTest {

    @Test
    void testRunInitialization() throws Exception {
        BatBatGame game = spy(BatBatGame.class);
        String playerName = "TestPlayer";

        // Simula la entrada del usuario
        InputStream in = new ByteArrayInputStream(playerName.getBytes());
        System.setIn(in);

        // Ejecuta el método run
        game.run();

        // Valida que el jugador se haya inicializado
        Player player = game.getPlayer();
        assertNotNull(player, "El jugador debería inicializarse correctamente.");
        assertEquals(playerName, player.getName(), "El nombre del jugador debería coincidir con la entrada.");
    }
    @Test
    void testSaveScore() throws IOException {
        BatBatGame game = new BatBatGame();
        String playerName = "TestPlayer";
        int score = 100;

        // Crea un archivo temporal para pruebas
        File tempFile = File.createTempFile("scores", ".txt");
        tempFile.deleteOnExit();

        // Cambia la implementación para usar el archivo temporal
        game.saveScore(playerName, score);

        // Valida el contenido del archivo
        String content = String.join("\n", Files.readAllLines(tempFile.toPath()));
        assertTrue(content.contains("Player: " + playerName + " - Score: " + score),
                "El archivo debería contener el nombre y la puntuación del jugador.");
    }

    @Test
    void testSaveScoreErrorHandling() {
        BatBatGame game = new BatBatGame();

        // Simula un error al guardar
        BatBatGame spyGame = spy(game);
        doThrow(IOException.class).when(spyGame).saveScore(anyString(), anyInt());

        assertDoesNotThrow(() -> spyGame.saveScore("TestPlayer", 100),
                "El método saveScore debería manejar excepciones sin lanzar errores.");
    }

    @Test
    void testScoreSavingOnWindowClosing() throws Exception {
        BatBatGame game = spy(new BatBatGame());
        game.run(); // Simula la ejecución del juego

        // Simula cerrar la ventana
        game.saveScore("TestPlayer", 100);

        verify(game, times(1)).saveScore("TestPlayer", 100);
    }

    @Test
    void testPlayerInitialization() throws Exception {
        BatBatGame game = new BatBatGame();
        game.run();

        assertNotNull(game.getPlayer(), "El jugador debería inicializarse correctamente.");
        assertEquals("TestPlayer", game.getPlayer().getName(), "El nombre del jugador debería ser 'TestPlayer'.");
    }

}
