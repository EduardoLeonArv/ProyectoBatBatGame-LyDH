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
	javax.swing.JFrame window; // Referencia al JFrame principal

	public BasicState[] gameStates;
	int currentState;
	PauseState pauseState;
	boolean paused;

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

		// Añade un hook de cierre para guardar la puntuación al salir del juego
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			saveScoreToFile(); // Guarda la puntuación al cerrar
		}));
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
	}

	// Método para descargar un estado del juego
	void unloadState(int state) {
		gameStates[state] = null;
	}

	// Cambiar el estado del juego
	public void setState(int state) {
		// Verifica si se está intentando pasar de LEVEL1STATE a LEVEL2STATE
		if (currentState == LEVEL1STATE && state == LEVEL2STATE) {
			saveScoreToFile(); // Guarda la puntuación antes de finalizar
			endGame();
			return; // Evita cargar el nuevo estado
		}

		// Cambiar el estado del juego
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void endGame() {
		// Guarda la puntuación antes de cualquier otra acción
		saveScoreToFile();

		// Leer y mostrar el Top 3 puntuaciones
		String topScores = getTopScores();
		int playerScore = player.getScore(); // Obtén la puntuación actual del jugador

		// Mostrar un cuadro de diálogo con el Top 3 y la puntuación del jugador
		javax.swing.JOptionPane.showMessageDialog(
				window,
				String.format(
						"Fin del Juego. Gracias por jugar!\n\nTu puntuación: %d\n\nTop 3 Puntuaciones:\n%s",
						playerScore,
						topScores
				),
				"Fin del Juego",
				javax.swing.JOptionPane.INFORMATION_MESSAGE
		);

		// Cierra la ventana principal
		if (window != null) {
			window.dispose(); // Cierra el JFrame principal
		}

		// Finaliza todos los procesos del programa
		System.exit(0);
	}


	String getTopScores() {
		java.util.List<String> scores = new java.util.ArrayList<>();

		// Leer el archivo scores.txt
		try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("scores.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				scores.add(line);
			}
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE, "Error reading scores.txt", e);
		}

		// Ordenar las puntuaciones en orden descendente
		scores.sort((line1, line2) -> {
			try {
				int score1 = extractScore(line1);
				int score2 = extractScore(line2);
				return Integer.compare(score2, score1); // Orden descendente
			} catch (NumberFormatException e) {
				return 0; // Si hay líneas mal formateadas, no afectan el orden
			}
		});

		// Construir el Top 3 puntuaciones
		StringBuilder topScores = new StringBuilder();
		int topLimit = Math.min(3, scores.size());
		for (int i = 0; i < topLimit; i++) {
			topScores.append((i + 1)).append(". ").append(scores.get(i)).append("\n");
		}

		return topScores.toString();
	}

	// Método para extraer la puntuación de una línea en scores.txt
	private int extractScore(String line) {
		try {
			String[] parts = line.split(" - "); // Formato esperado: "Player: [Name] - Score: [Score]"
			String scorePart = parts[1].replace("Score: ", "").trim();
			return Integer.parseInt(scorePart);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failed to parse score from line: {0}", line);
			return 0;
		}
	}




	// Método para guardar el puntaje en un archivo
	public void saveScoreToFile() {
		if (scoreSaved) return; // Evita guardar múltiples veces

		int finalScore = player.getScore(); // Obtén la puntuación actual del jugador
		if (finalScore == 0) {
			LOGGER.log(Level.WARNING, "Score is 0, skipping save.");
			return; // No guarda si la puntuación es 0
		}

		scoreSaved = true; // Marca que el puntaje ya se guardó
		PlayerSave.setScore(finalScore); // Actualiza el puntaje en PlayerSave

		LOGGER.log(Level.INFO, "Saving score: Player = {0}, Score = {1}",
				new Object[]{player.getName(), finalScore});

		try (java.io.FileWriter writer = new java.io.FileWriter("scores.txt", true)) {
			writer.write("Player: " + player.getName() + " - Score: " + finalScore + "\n");
			writer.flush(); // Asegúrate de que los datos se escriban inmediatamente
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