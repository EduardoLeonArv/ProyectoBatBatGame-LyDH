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

import java.io.FileWriter; // AGRAGAR PUNTUACIONES
import java.io.IOException;
import java.util.Scanner;

@Component
public class BatBatGame extends JFrame implements CommandLineRunner {

	private static final long serialVersionUID = -437004379167511593L;

	private Player player; // Instancia del jugador

	@Override
	public void run(String... arg0) throws Exception {
		// Inicializa el mapa y el jugador
		TileMap tileMap = new TileMap(30); // Configura según tu juego
		player = new Player(tileMap); // Crea el jugador

		// Configura la ventana actual (this)
		setTitle("BatBat Game \u2122");
		setContentPane(new GamePanel(player)); // Usa setContentPane directamente
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		pack();
		setLocationRelativeTo(null);



		// Mostrar la ventana del juego
		setVisible(true);
	}

	/**
	 * Método para guardar la puntuación del jugador en un archivo.
	 *
	 * @param playerName Nombre del jugador
	 * @param score      Puntuación del jugador
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

	public Player getPlayer() {
		return player;
	}
}
