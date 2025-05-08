package al.tonikolaba.gamestate;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.main.GamePanel;
import java.util.logging.Level;
import java.util.logging.Logger;

import al.tonikolaba.entity.PlayerSave;

/**
 * Clase que gestiona los diferentes estados del juego, permitiendo cambiar entre niveles,
 * menú principal, opciones, y otros. También maneja la pausa del juego y la gestión
 * de la puntuación del jugador.
 */
public class GameStateManager {
	/** Logger para registrar eventos e información. */
	private static final Logger LOGGER = Logger.getLogger(GameStateManager.class.getName());

	/** Número total de estados posibles en el juego. */
	public static final int NUMGAMESTATES = 16;
	/** Identificador del estado del menú principal. */
	public static final int MENUSTATE = 0;
	/** Identificador del estado de opciones. */
	public static final int OPTIONSSTATE = 1;
	/** Identificador del nivel 1 del juego. */
	public static final int LEVEL1STATE = 2;
	/** Identificador del nivel 2 del juego. */
	public static final int LEVEL2STATE = 3;
	/** Identificador del nivel 3 del juego. */
	public static final int LEVEL3STATE = 4;
	/** Identificador del nivel 4 del juego. */
	public static final int LEVEL4STATE = 5;
	/** Identificador del estado "How to Play". */
	public static final int HOWTOPLAY = 7;
	/** Identificador del estado especial "Acid State". */
	public static final int ACIDSTATE = 15;

	/** Referencia al JFrame principal de la aplicación. */
	javax.swing.JFrame window;

	/** Array que contiene los estados actuales del juego. */
	public BasicState[] gameStates;
	/** Identificador del estado actual del juego. */
	int currentState;
	/** Estado de pausa del juego. */
	PauseState pauseState;
	/** Indica si el juego está pausado. */
	boolean paused;

	/** Referencia al jugador actual. */
	private Player player;
	/** Bandera para verificar si la puntuación ya se ha guardado. */
	private boolean scoreSaved = false;

	/**
	 * Constructor que inicializa el gestor de estados del juego.
	 *
	 * @param player Referencia al jugador actual.
	 */
	public GameStateManager(Player player) {
		this.player = player; // Asigna el jugador actual
		JukeBox.init(); // Inicializa el sistema de audio

		gameStates = new BasicState[NUMGAMESTATES];

		// Inicializa el estado de pausa
		pauseState = new PauseState(this, player);

		paused = false;

		// Establece el estado inicial como el menú principal
		currentState = MENUSTATE;
		loadState(currentState);

		// Hook para guardar puntuación al cerrar la aplicación
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			saveScoreToFile(); // Guarda la puntuación al salir
		}));
	}

	/**
	 * Carga un estado específico del juego en memoria.
	 *
	 * @param state Identificador del estado que se desea cargar.
	 */
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

	/**
	 * Descarga un estado del juego de la memoria.
	 *
	 * @param state Identificador del estado que se desea descargar.
	 */
	void unloadState(int state) {
		gameStates[state] = null;
	}

	/**
	 * Cambia el estado actual del juego a otro especificado.
	 *
	 * @param state Identificador del nuevo estado al que se desea cambiar.
	 */
	public void setState(int state) {
		// Guarda la puntuación antes de cambiar del nivel 1 al nivel 2
		if (currentState == LEVEL1STATE && state == LEVEL2STATE) {
			saveScoreToFile();
			endGame();
			return; // Evita cargar el nuevo estado después de finalizar
		}

		// Descarga el estado actual y carga el nuevo estado
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/**
	 * Termina el programa cerrando la aplicación.
	 */
	protected void terminateProgram() {
		System.exit(0);
	}

	/**
	 * Finaliza el juego mostrando las puntuaciones y cerrando la aplicación.
	 */
	public void endGame() {
		saveScoreToFile(); // Guarda la puntuación actual del jugador
		String topScores = getTopScores(); // Obtiene las mejores puntuaciones
		int playerScore = player.getScore(); // Obtiene la puntuación del jugador

		// Muestra un mensaje con la puntuación del jugador y las mejores puntuaciones
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

		// Cierra la ventana principal si está abierta
		if (window != null) {
			window.dispose();
		}
		terminateProgram(); // Finaliza el programa
	}


	/**
	 * Obtiene las puntuaciones más altas de los jugadores desde el archivo de puntuaciones.
	 *
	 * @return Una cadena que contiene las tres mejores puntuaciones formateadas.
	 */
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

	/**
	 * Extrae la puntuación de una línea en el archivo de puntuaciones.
	 *
	 * @param line Una línea del archivo que contiene el formato "Player: [Name] - Score: [Score]".
	 * @return La puntuación como un entero. Devuelve 0 si no se puede parsear.
	 */
	int extractScore(String line) {
		try {
			String[] parts = line.split(" - "); // Formato esperado: "Player: [Name] - Score: [Score]"
			String scorePart = parts[1].replace("Score: ", "").trim();
			return Integer.parseInt(scorePart);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Failed to parse score from line: {0}", line);
			return 0;
		}
	}

	/**
	 * Guarda el puntaje actual del jugador en el archivo de puntuaciones.
	 * Verifica si ya ha sido guardado previamente para evitar duplicados.
	 */
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

	/**
	 * Pausa o reanuda el juego.
	 *
	 * @param b {@code true} para pausar el juego, {@code false} para reanudarlo.
	 */
	public void setPaused(boolean b) {
		paused = b;
	}

	/**
	 * Actualiza el estado actual del juego.
	 * Si el juego está pausado, actualiza el estado de pausa.
	 */
	public void update() {
		if (paused) {
			pauseState.update();
			return;
		}
		if (gameStates[currentState] != null)
			gameStates[currentState].update();
	}

	/**
	 * Dibuja el estado actual del juego en la pantalla.
	 * Si el juego está pausado, dibuja el estado de pausa.
	 *
	 * @param g Contexto gráfico utilizado para renderizar.
	 */
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

	/**
	 * Verifica si un estado del juego está cargado en memoria.
	 *
	 * @param state Identificador del estado a verificar.
	 * @return {@code true} si el estado está cargado, {@code false} en caso contrario.
	 */
	public boolean isStateLoaded(int state) {
		return state >= 0 && state < NUMGAMESTATES && gameStates[state] != null;
	}


}