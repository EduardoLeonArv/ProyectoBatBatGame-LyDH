package al.tonikolaba.gamestate;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.main.GamePanel;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author tonikolaba
 */

/**
 * @author tonikolaba
 */
import al.tonikolaba.entity.PlayerSave;

/**
 * @author tonikolaba
 */

public class GameStateManager {

	private static final Logger LOGGER = Logger.getLogger(GameStateManager.class.getName());
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int OPTIONSSTATE = 1;
	public static final int LEVEL1STATE = 2;
	public static final int LEVEL2STATE = 3;
	public static final int LEVEL3STATE = 4;
	public static final int LEVEL4STATE = 5;
	public static final int HOWTOPLAY = 7;
	public static final int ACIDSTATE = 15;

	public BasicState[] gameStates;
	private int currentState;
	private PauseState pauseState;
	private boolean paused;

	private Player player; // Referencia al jugador
	private boolean scoreSaved = false; // Bandera para verificar si el puntaje ya se guardó

	// Constructor modificado para recibir el jugador
	public GameStateManager(Player player) {
		this.player = player; // Asignar el jugador
		JukeBox.init();

		gameStates = new BasicState[NUMGAMESTATES];

		// Pasa el jugador al constructor de PauseState
		pauseState = new PauseState(this, player);

		paused = false;

		currentState = MENUSTATE;
		loadState(currentState);
	}

	// Método para cargar un estado del juego
	public void loadState(int state) {
		if (state == MENUSTATE)
			gameStates[state] = new MenuState(this, player);
		else if (state == OPTIONSSTATE)
			gameStates[state] = new OptionsState(this, player);
		else if (state == HOWTOPLAY)
			gameStates[state] = new HowtoPlay(this, player);
		else if (state == LEVEL1STATE)
			gameStates[state] = new Level1State(this, player);
		else if (state == LEVEL2STATE)
			gameStates[state] = new Level2State(this, player);
		else if (state == LEVEL3STATE)
			gameStates[state] = new Level3State(this, player);
		else if (state == LEVEL4STATE)
			gameStates[state] = new Level4State(this, player);
		else if (state == ACIDSTATE)
			gameStates[state] = new AcidState(this, player);
	}

	// Método para descargar un estado del juego
	private void unloadState(int state) {
		gameStates[state] = null;
	}

	// Cambiar el estado del juego
	public void setState(int state) {
		// Guardar puntaje antes de cambiar de estado
		saveScoreToFile();

		// Cambiar el estado del juego
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	// Método para guardar el puntaje en un archivo
	public void saveScoreToFile() {
		if (scoreSaved) return; // Evita guardar múltiples veces

		scoreSaved = true; // Marca que el puntaje ya se guardó
		PlayerSave.setScore(player.getScore()); // Actualiza el puntaje en PlayerSave

		try (java.io.FileWriter writer = new java.io.FileWriter("scores.txt", true)) {
			writer.write("Player: " + player.getName() + " - Score: " + player.getScore() + "\n");
			writer.flush();
			LOGGER.log(Level.INFO, "Player name and score saved to scores.txt");
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE, "Error while saving score to file", e);
		}
	}

	// Pausar el juego
	public void setPaused(boolean b) {
		paused = b;
	}

	// Actualizar el estado actual
	public void update() {
		if (paused) {
			pauseState.update();
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	// Dibujar el estado actual
	public void draw(java.awt.Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}
		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		} else {
			g.setColor(java.awt.Color.YELLOW);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

	public boolean isStateLoaded(int state) {
		return state >= 0 && state < NUMGAMESTATES && gameStates[state] != null;
	}

}
