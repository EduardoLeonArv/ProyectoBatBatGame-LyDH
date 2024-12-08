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

import java.io.FileWriter; //AGRAGAR PUNTUACIONES
import java.io.IOException;
import java.util.Scanner;

@Component
public class BatBatGame extends JFrame implements CommandLineRunner {

	private static final long serialVersionUID = -437004379167511593L;

	private Player player; // Instancia del jugador

	@Override
	public void run(String... arg0) throws Exception {
		// Prompt for player name
		Scanner scanner = new Scanner(System.in);
		LoggingHelper.LOGGER.log(Level.INFO, "Enter your player name: ");
		String playerName = scanner.nextLine();
		LoggingHelper.LOGGER.log(Level.INFO, "Welcome, {0}!", playerName);

		// Inicializa el mapa y el jugador
		TileMap tileMap = new TileMap(30); // Configura según tu juego
		player = new Player(tileMap); // Crea el jugador
		player.setName(playerName); // Si tienes un método para guardar el nombre

		// Initialize game window
		EventQueue.invokeLater(() -> {
			try {
				JFrame window = new JFrame("BatBat Game \u2122");
				window.add(new GamePanel(player)); // Pasa el jugador al panel del juego
				window.setContentPane(new GamePanel(player));
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setResizable(true);
				window.pack();
				window.setLocationRelativeTo(null);
				window.setVisible(true);

				window.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						saveScore(playerName, player.getScore());
					}
				});
			} catch (Exception e) {
				LoggingHelper.LOGGER.log(Level.SEVERE, "Error initializing game window", e);
			}
		});
	}

	protected void saveScore(String playerName, int score) {
		// Save player name and score to scores.txt
		try (FileWriter writer = new FileWriter("scores.txt", true)) {
			writer.write("Player: " + playerName + " - Score: " + score + "\n");
			writer.flush();
			LoggingHelper.LOGGER.log(Level.INFO, "Player name and score saved to scores.txt");
		} catch (IOException e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, "Error saving score to file", e);
		}
	}
}
