package al.tonikolaba.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.swing.JPanel;

import al.tonikolaba.gamestate.GameStateManager;
//import al.tonikolaba.gamestate.GameStateManager;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;

import al.tonikolaba.entity.Player; // Importar la clase Player

import java.io.FileWriter;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable, KeyListener {

	// dimensions
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int SCALE = 1;
	private static final long serialVersionUID = 1275876853084636658L;

	// game thread
	private transient Thread thread;
	private boolean running;
	private int fps = 60;
	private long targetTime = 1000 / fps;

	// image
	private transient BufferedImage image;
	private transient Graphics2D g;

	// game state manager
	private transient GameStateManager gsm;

	// player reference
	private Player player; // Referencia al jugador

	// other
	private boolean recording = false;
	private int recordingCount = 0;
	private boolean screenshot;
	private boolean scoreSaved = false; // Nueva bandera para evitar guardar múltiples veces

	// Constructor modificado para aceptar el jugador
	public GamePanel(Player player) {
		super();
		this.player = player; // Asignar el jugador
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager(player); // Asegúrate de que GameStateManager reciba el mismo jugador
	}

	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// game loop
		while (running) {

			start = System.nanoTime();

			update();
			draw();
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			if (wait < 0)
				wait = 5;

			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt(); // Reestablece el estado de interrupción del hilo
				LoggingHelper.LOGGER.log(Level.SEVERE, "Thread interrupted: {0}", e.getMessage());
			}
		}

		// Guardar la puntuación solo si el juego termina
		saveScore();
	}

	private void update() {
		gsm.update(); // Se asegura de actualizar el estado del juego
		Keys.update();
	}

	private void draw() {
		gsm.draw(g); // Dibuja el estado actual del juego
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
		if (screenshot) {
			screenshot = false;
			try {
				java.io.File out = new java.io.File("screenshot " + System.nanoTime() + ".gif");
				javax.imageio.ImageIO.write(image, "gif", out);
			} catch (Exception e) {
				LoggingHelper.LOGGER.log(Level.SEVERE, "Error while taking screenshot", e);
			}
		}
		if (!recording)
			return;
		try {
			java.io.File out = new java.io.File("C:\\out\\frame" + recordingCount + ".gif");
			javax.imageio.ImageIO.write(image, "gif", out);
			recordingCount++;
		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, "Error while recording frame", e);
		}
	}

	private void saveScore() {
		if (scoreSaved) return; // Evita guardar más de una vez
		scoreSaved = true; // Marca que la puntuación ya se guardó

		try (FileWriter writer = new FileWriter("scores.txt", true)) {
			writer.write("Player: " + player.getName() + " - Score: " + player.getScore() + "\n");
			writer.flush();
			LoggingHelper.LOGGER.log(Level.INFO, "Player score saved to scores.txt");
		} catch (IOException e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, "Error saving score to file", e);
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.isControlDown()) {
			if (key.getKeyCode() == KeyEvent.VK_R) {
				recording = !recording;
				return;
			}
			if (key.getKeyCode() == KeyEvent.VK_S) {
				screenshot = true;
				return;
			}
		}
		Keys.keySet(key.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not necessary
	}
}
