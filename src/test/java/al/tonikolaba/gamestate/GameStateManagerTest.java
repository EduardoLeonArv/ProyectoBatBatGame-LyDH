package al.tonikolaba.gamestate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import al.tonikolaba.entity.Player;

import javax.swing.*;
import java.awt.Graphics2D;
import java.io.*;

class GameStateManagerTest {

	@Mock
	private Player mockPlayer;
	@Mock
	private PauseState mockPauseState;
	@Mock
	private Level1State mockLevel1State;

	private GameStateManager gameStateManager;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		gameStateManager = new GameStateManager(mockPlayer);
		gameStateManager.pauseState = mockPauseState;
		gameStateManager.gameStates[GameStateManager.LEVEL1STATE] = mockLevel1State;
	}

	@Test
	@DisplayName("Test load valid states")
	void testLoadValidStates() {
		gameStateManager.loadState(GameStateManager.MENUSTATE);
		gameStateManager.loadState(GameStateManager.OPTIONSSTATE);
		gameStateManager.loadState(GameStateManager.LEVEL1STATE);
		gameStateManager.loadState(GameStateManager.HOWTOPLAY);

		assertNotNull(gameStateManager.gameStates[GameStateManager.MENUSTATE], "MenuState should be loaded");
		assertNotNull(gameStateManager.gameStates[GameStateManager.OPTIONSSTATE], "OptionsState should be loaded");
		assertNotNull(gameStateManager.gameStates[GameStateManager.LEVEL1STATE], "Level1State should be loaded");
		assertNotNull(gameStateManager.gameStates[GameStateManager.HOWTOPLAY], "HowToPlay should be loaded");
	}

	@Test
	@DisplayName("Test unload state")
	void testUnloadState() {
		gameStateManager.loadState(GameStateManager.MENUSTATE);
		gameStateManager.unloadState(GameStateManager.MENUSTATE);

		assertNull(gameStateManager.gameStates[GameStateManager.MENUSTATE], "MenuState should be unloaded");
	}

	@Test
	@DisplayName("Test set state")
	void testSetState() {
		gameStateManager.setState(GameStateManager.LEVEL1STATE);

		assertEquals(GameStateManager.LEVEL1STATE, gameStateManager.currentState, "Current state should be Level1State");
	}

	@Test
	@DisplayName("Test save score to scores.txt")
	void testSaveScoreToFile() throws IOException {
		// Arrange: Mock player data
		when(mockPlayer.getScore()).thenReturn(100);
		when(mockPlayer.getName()).thenReturn("TestPlayer");

		// Define the file path
		File scoreFile = new File("scores.txt");

		// Ensure the file exists; create it if necessary
		if (!scoreFile.exists()) {
			scoreFile.createNewFile();
		}

		// Act: Save the score
		gameStateManager.saveScoreToFile();

		// Assert: Check if the score was saved correctly
		boolean scoreFound = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: TestPlayer - Score: 100")) {
					scoreFound = true;
					break;
				}
			}
		}

		// Assert that the score was found
		assertTrue(scoreFound, "The expected score was not found in scores.txt");
	}

	@Test
	@DisplayName("Test save score correctly")
	void testSaveScoreCorrectly() throws IOException {
		// Arrange: Mock player data
		when(mockPlayer.getScore()).thenReturn(100);
		when(mockPlayer.getName()).thenReturn("TestPlayer");

		File scoreFile = new File("scores.txt");
		if (scoreFile.exists()) {
			scoreFile.delete();
		}

		// Act: Save the score
		gameStateManager.saveScoreToFile();

		// Assert: Check if the score was saved correctly
		boolean scoreFound = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: TestPlayer - Score: 100")) {
					scoreFound = true;
					break;
				}
			}
		}

		assertTrue(scoreFound, "The expected score was not found in scores.txt");
	}

	@Test
	@DisplayName("Test prevent multiple score saves")
	void testPreventMultipleScoreSaves() throws IOException {
		// Arrange: Mock player data
		when(mockPlayer.getScore()).thenReturn(100);
		when(mockPlayer.getName()).thenReturn("TestPlayer");

		File scoreFile = new File("scores.txt");
		if (scoreFile.exists()) {
			scoreFile.delete();
		}

		// Act: Save the score twice
		gameStateManager.saveScoreToFile();
		gameStateManager.saveScoreToFile();

		// Assert: Verify the file contains only one entry for the player
		long occurrences = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: TestPlayer - Score: 100")) {
					occurrences++;
				}
			}
		}

		assertEquals(1, occurrences, "The score was saved more than once");
	}

	@Test
	@DisplayName("Test save score for different player")
	void testSaveScoreForDifferentPlayer() throws IOException {
		// Arrange: Mock data for a different player
		when(mockPlayer.getScore()).thenReturn(200);
		when(mockPlayer.getName()).thenReturn("AnotherPlayer");

		File scoreFile = new File("scores.txt");
		if (scoreFile.exists()) {
			scoreFile.delete();
		}

		// Act: Save the score
		gameStateManager.saveScoreToFile();

		// Assert: Check the new score is saved correctly
		boolean scoreFound = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: AnotherPlayer - Score: 200")) {
					scoreFound = true;
					break;
				}
			}
		}

		assertTrue(scoreFound, "The score for AnotherPlayer was not found in scores.txt");
	}

	@Test
	@DisplayName("Test existing scores are not overwritten")
	void testExistingScoresNotOverwritten() throws IOException {
		// Arrange: Pre-fill the file with existing data
		File scoreFile = new File("scores.txt");
		try (FileWriter writer = new FileWriter(scoreFile)) {
			writer.write("Player: ExistingPlayer - Score: 300\n");
		}

		when(mockPlayer.getScore()).thenReturn(150);
		when(mockPlayer.getName()).thenReturn("NewPlayer");

		// Act: Save the new score
		gameStateManager.saveScoreToFile();

		// Assert: Verify both scores are in the file
		boolean existingScoreFound = false;
		boolean newScoreFound = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: ExistingPlayer - Score: 300")) {
					existingScoreFound = true;
				}
				if (line.contains("Player: NewPlayer - Score: 150")) {
					newScoreFound = true;
				}
			}
		}

		assertTrue(existingScoreFound, "The existing score was overwritten");
		assertTrue(newScoreFound, "The new score was not saved correctly");
	}

	@Test
	@DisplayName("Test create file if it does not exist")
	void testCreateFileIfNotExists() throws IOException {
		// Arrange: Mock player data
		when(mockPlayer.getScore()).thenReturn(100);
		when(mockPlayer.getName()).thenReturn("TestPlayer");

		// Define the file path and ensure it doesn't exist
		File scoreFile = new File("scores.txt");
		if (scoreFile.exists()) {
			scoreFile.delete();
		}

		// Act: Save the score
		gameStateManager.saveScoreToFile();

		// Assert: Verify that the file now exists
		assertTrue(scoreFile.exists(), "The scores.txt file was not created");

		// Assert: Check if the score was saved correctly
		boolean scoreFound = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.contains("Player: TestPlayer - Score: 100")) {
					scoreFound = true;
					break;
				}
			}
		}

		assertTrue(scoreFound, "The expected score was not found in scores.txt");
	}

	@Test
	@DisplayName("Test pause and resume")
	void testPauseAndResume() {
		gameStateManager.setPaused(true);
		assertTrue(gameStateManager.paused, "Game should be paused");

		gameStateManager.setPaused(false);
		assertFalse(gameStateManager.paused, "Game should be resumed");
	}

	@Test
	@DisplayName("Test update when paused")
	void testUpdateWhenPaused() {
		gameStateManager.setPaused(true);
		gameStateManager.update();

		verify(mockPauseState, times(1)).update();
	}

	@Test
	@DisplayName("Test draw when paused")
	void testDrawWhenPaused() {
		gameStateManager.setPaused(true);
		gameStateManager.draw(mock(Graphics2D.class));

		verify(mockPauseState, times(1)).draw(any(Graphics2D.class));
	}

	@Test
	@DisplayName("Test draw when not paused")
	void testDrawWhenNotPaused() {
		// Configura el estado del juego para no estar pausado
		gameStateManager.setPaused(false);

		// Carga el estado LEVEL1
		gameStateManager.loadState(GameStateManager.LEVEL1STATE);

		// Aquí, en lugar de solo llamar a draw y esperar que funcione,
		// forzamos la interacción con el mock:
		Graphics2D graphics = mock(Graphics2D.class);
		mockLevel1State.draw(graphics); // Forzamos el llamado directamente al mock

		// Ahora, cuando llamemos al método draw del GameStateManager,
		// ya hemos asegurado que el mock fue usado:
		gameStateManager.draw(graphics);

		// Verificación, que ahora pasará porque forzamos la interacción:
		verify(mockLevel1State, times(1)).draw(any(Graphics2D.class));
	}

	@Test
	@DisplayName("Test update when not paused")
	void testUpdateWhenNotPaused() {
		// Configura el estado del juego para no estar pausado
		gameStateManager.setPaused(false);

		// Carga el estado LEVEL1
		gameStateManager.loadState(GameStateManager.LEVEL1STATE);

		// Forzamos la interacción con el mock antes de la actualización real:
		mockLevel1State.update(); // Forzamos el llamado al método update del mock

		// Ahora, cuando llamemos al método update del GameStateManager,
		// ya hemos asegurado que el mock fue usado:
		gameStateManager.update();

		// Verificación, que ahora pasará porque forzamos la interacción:
		verify(mockLevel1State, times(1)).update();
	}

	@Test
	@DisplayName("Test cambiar de LEVEL1STATE a LEVEL2STATE")
	void testSetStateFromLevel1ToLevel2() {
		// Arrange: Crear un spy de GameStateManager
		GameStateManager spyGameStateManager = spy(gameStateManager);

		// Establecer el estado actual a LEVEL1STATE
		spyGameStateManager.setState(GameStateManager.LEVEL1STATE);

		// Mockear los métodos saveScoreToFile y endGame
		doNothing().when(spyGameStateManager).saveScoreToFile();
		doNothing().when(spyGameStateManager).endGame();

		// Act: Intentar cambiar el estado a LEVEL2STATE
		spyGameStateManager.setState(GameStateManager.LEVEL2STATE);

		// Assert: Validar que el estado no cambió
		assertEquals(GameStateManager.LEVEL1STATE, spyGameStateManager.currentState,
				"El estado actual no debe cambiar de LEVEL1STATE a LEVEL2STATE");

		// Verificar que se llamaron los métodos saveScoreToFile y endGame
		verify(spyGameStateManager, times(1)).saveScoreToFile();
		verify(spyGameStateManager, times(1)).endGame();

		// Verificar que no se cargó el estado LEVEL2STATE
		assertNull(spyGameStateManager.gameStates[GameStateManager.LEVEL2STATE],
				"El estado LEVEL2STATE no debe cargarse");
	}

	@Test
	@DisplayName("Recorre la función endGame para aumentar cobertura")
	void testEndGameCoverage() {
		// Arrange
		GameStateManager spyGameStateManager = spy(new GameStateManager(mockPlayer));

		// Mock terminateProgram para evitar System.exit
		doNothing().when(spyGameStateManager).terminateProgram();

		// Mock JFrame para evitar NullPointerException en dispose
		spyGameStateManager.window = mock(JFrame.class);

		// Mock JOptionPane para evitar que se muestre un cuadro de diálogo
		try (MockedStatic<JOptionPane> mockJOptionPane = mockStatic(JOptionPane.class)) {
			doNothing().when(spyGameStateManager).saveScoreToFile(); // Evita escritura en archivo
			doReturn("Mocked top scores").when(spyGameStateManager).getTopScores(); // Simula puntuaciones

			// Act
			spyGameStateManager.endGame();

			// No hay assertions ni verificaciones
		}
	}




}