package al.tonikolaba.main;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

import javax.swing.JFrame;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Clase principal del juego BatBat, que extiende de {@link JFrame}.
 * Gestiona la configuración de la ventana, inicializa al jugador, el mapa,
 * y guarda las puntuaciones cuando el juego finaliza.
 * Implementa {@link CommandLineRunner} para integrarse con Spring Boot.
 */
@Component
public class BatBatGame extends JFrame implements CommandLineRunner {

	private static final long serialVersionUID = -437004379167511593L;

	/** Instancia del jugador en el juego. */
	private Player player;

	/**
	 * Método principal que se ejecuta al iniciar la aplicación Spring Boot.
	 * Inicializa el juego solicitando el nombre del jugador, configurando el mapa,
	 * y gestionando la ventana principal del juego.
	 *
	 * @param arg0 Argumentos pasados desde la línea de comandos.
	 * @throws Exception En caso de error durante la inicialización del juego.
	 */
	@Override
	public void run(String... arg0) throws Exception {
		// Solicita el nombre del jugador
		Scanner scanner = new Scanner(System.in);
		LoggingHelper.LOGGER.log(Level.INFO, "Enter your player name: ");
		String playerName = scanner.nextLine();
		LoggingHelper.LOGGER.log(Level.INFO, "Welcome, {0}!", playerName);

		// Inicializa el mapa y el jugador
		TileMap tileMap = new TileMap(30); // Configura según tu juego
		player = new Player(tileMap); // Crea el jugador
		player.setName(playerName); // Establece el nombre del jugador

		// Configura la ventana principal del juego
		setTitle("BatBat Game \u2122");
		setContentPane(new GamePanel(player)); // Agrega el panel principal del juego
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setLocationRelativeTo(null);

		// Agrega un listener para guardar la puntuación al cerrar la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveScore(playerName, player.getScore());
			}
		});
	}

	/**
	 * Guarda la puntuación del jugador en un archivo de texto.
	 *
	 * @param playerName Nombre del jugador.
	 * @param score Puntuación obtenida por el jugador.
	 */
	protected void saveScore(String playerName, int score) {
		try (FileWriter writer = new FileWriter("scores.txt", true)) {
			writer.write("Player: " + playerName + " - Score: " + score + "\n");
			writer.flush();
			LoggingHelper.LOGGER.log(Level.INFO, "Player name and score saved to scores.txt");
		} catch (IOException e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, "Error saving score to file", e);
		}
	}

	/**
	 * Obtiene la instancia actual del jugador.
	 *
	 * @return Instancia del jugador en el juego.
	 */
	public Player getPlayer() {
		return player;
	}
}
