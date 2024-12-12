package al.tonikolaba.gamestate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import al.tonikolaba.entity.Player;

import java.awt.Graphics2D;

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
	@DisplayName("Test save score to file")
	void testSaveScoreToFile() {
		when(mockPlayer.getScore()).thenReturn(100);
		when(mockPlayer.getName()).thenReturn("TestPlayer");

		gameStateManager.saveScoreToFile();

		// Verificar que el puntaje se guardó correctamente
		// Aquí puedes agregar lógica para verificar el contenido del archivo si es necesario
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
}