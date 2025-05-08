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
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;

import al.tonikolaba.entity.Player;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que representa el panel principal del juego.
 * Administra el ciclo de vida del juego, los estados, el dibujo y las interacciones del teclado.
 *
 * @author ArtOfSoul
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

	/** Ancho de la ventana del juego en píxeles. */
	public static final int WIDTH = 640;
	/** Altura de la ventana del juego en píxeles. */
	public static final int HEIGHT = 480;
	/** Escala del juego. */
	public static final int SCALE = 1;
	private static final long serialVersionUID = 1275876853084636658L;

	// Game thread
	/** Hilo principal del juego. */
	private transient Thread thread;
	/** Indica si el juego está en ejecución. */
	boolean running;
	/** Fotogramas por segundo objetivo del juego. */
	private int fps = 60;
	/** Tiempo objetivo entre fotogramas en milisegundos. */
	private long targetTime = 1000 / fps;

	// Image
	/** Imagen utilizada como buffer para el dibujo en pantalla. */
	transient BufferedImage image;
	/** Gráficos asociados a la imagen del buffer. */
	transient Graphics2D g;

	// Game state manager
	/** Administrador de estados del juego. */
	transient GameStateManager gsm;

	// Player reference
	/** Referencia al jugador actual. */
	private Player player;

	// Other
	/** Indica si se está grabando la sesión de juego. */
	private boolean recording = false;
	/** Contador de fotogramas grabados. */
	private int recordingCount = 0;
	/** Indica si se está capturando una captura de pantalla. */
	boolean screenshot;
	/** Indica si la puntuación ya fue guardada. */
	private boolean scoreSaved = false;

	/**
	 * Constructor de la clase GamePanel.
	 *
	 * @param player Referencia al jugador actual.
	 */
	public GamePanel(Player player) {
		super();
		this.player = player; // Asignar el jugador
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}

	/**
	 * Método invocado automáticamente cuando el panel se añade al contenedor.
	 * Inicia el hilo principal del juego si no ha sido iniciado.
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	/**
	 * Inicializa el juego, creando el buffer de imagen y configurando el administrador de estados.
	 */
	void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager(player);
	}

	/**
	 * Método principal del juego. Controla el ciclo de vida del juego y los tiempos de actualización.
	 */
	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;

		// Ciclo principal del juego
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
				Thread.currentThread().interrupt(); // Restablecer el estado de interrupción del hilo
				LoggingHelper.LOGGER.log(Level.SEVERE, "Thread interrupted: {0}", e.getMessage());
			}
		}

		// Guardar la puntuación cuando el juego termine
		saveScore();
	}

	/**
	 * Actualiza el estado del juego.
	 */
	protected void update() {
		gsm.update();
		Keys.update();
	}

	/**
	 * Dibuja el estado actual del juego en el buffer de imagen.
	 */
	private void draw() {
		gsm.draw(g);
	}

	/**
	 * Dibuja el contenido del buffer de imagen en la pantalla.
	 */
	void drawToScreen() {
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

	/**
	 * Guarda la puntuación del jugador en un archivo.
	 * Evita guardar múltiples veces utilizando una bandera.
	 */
	public void saveScore() {
		if (scoreSaved) return;
		scoreSaved = true;

		try (FileWriter writer = new FileWriter("scores.txt", true)) {
			writer.write("Player: " + player.getName() + " - Score: " + player.getScore() + "\n");
			writer.flush();
			LoggingHelper.LOGGER.log(Level.INFO, "Player score saved to scores.txt");
		} catch (IOException e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, "Error saving score to file", e);
		}
	}

	/**
	 * Maneja los eventos de teclado cuando se presionan las teclas.
	 *
	 * @param key Evento de teclado presionado.
	 */
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

	/**
	 * Maneja los eventos de teclado cuando se sueltan las teclas.
	 *
	 * @param key Evento de teclado liberado.
	 */
	@Override
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

	/**
	 * Maneja los eventos de teclado cuando se escriben caracteres.
	 * (No se utiliza en este caso).
	 *
	 * @param e Evento de teclado tipeado.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// Not necessary
	}

	/**
	 * Verifica si se está grabando la sesión de juego.
	 *
	 * @return {@code true} si se está grabando, {@code false} en caso contrario.
	 */
	public boolean isRecording() {
		return recording;
	}

	/**
	 * Verifica si se está capturando una captura de pantalla.
	 *
	 * @return {@code true} si se está capturando una captura de pantalla, {@code false} en caso contrario.
	 */
	public boolean isScreenshot() {
		return screenshot;
	}

	/**
	 * Verifica si la puntuación ya ha sido guardada.
	 *
	 * @return {@code true} si la puntuación ya se guardó, {@code false} en caso contrario.
	 */
	public boolean isScoreSaved() {
		return scoreSaved;
	}
}
