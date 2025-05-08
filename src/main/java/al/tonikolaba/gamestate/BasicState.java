package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.TileMap;

/**
 * Esta clase representa un estado base para los menús del juego, proporcionando
 * la funcionalidad básica para manejar la entrada del usuario y la visualización de
 * las opciones del menú.
 * <p>
 * Contiene métodos genéricos para dibujar el menú y gestionar la entrada del jugador
 * mientras se navega por las opciones. Las clases derivadas deben sobrescribir
 * métodos como `select()` para definir acciones específicas cuando el jugador selecciona una opción.
 * </p>
 *
 * @author tonikolaba
 */
public abstract class BasicState {

	/** Nombre de la opción de menú */
	private static final String MENU_OPTION = "menuoption";

	/** Referencia al GameStateManager para gestionar los estados del juego */
	protected GameStateManager gsm;

	/** Referencia al jugador */
	protected Player player;

	/** Mapa de tiles del juego */
	protected TileMap tileMap;

	/** Flag para bloquear la entrada de usuario */
	protected boolean blockInput = false;

	/** Fondo del menú */
	protected BufferedImage bg;

	/** Cabeza flotante que se mueve con las opciones del menú */
	protected BufferedImage head;

	/** Opción seleccionada actualmente */
	protected int currentChoice = 0;

	/** Opciones de menú disponibles */
	protected String[] options;

	/** Fuentes para el texto en el menú */
	protected Font font;
	/** Fuentes para el texto en el menú */
	protected Font font2;
	/** Fuentes para el texto en el menú */
	protected Font fontMenu;

	/**
	 * Establece la opción seleccionada actualmente.
	 *
	 * @param choice La opción que se desea seleccionar
	 */
	public void setCurrentChoice(int choice) {
		this.currentChoice = choice;
	}

	/**
	 * Obtiene la opción seleccionada actualmente.
	 *
	 * @return La opción seleccionada actualmente
	 */
	public int getCurrentChoice() {
		return currentChoice;
	}

	/**
	 * Constructor de la clase `BasicState`. Inicializa el gestor de estados del juego,
	 * el jugador y otros recursos como imágenes y sonidos para el menú.
	 *
	 * @param gsm El `GameStateManager` que gestiona los estados del juego
	 */
	public BasicState(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			// Carga la imagen de fondo
			bg = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/sfondi1.gif"))
					.getSubimage(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

			// Carga la imagen de la cabeza flotante
			head = ImageIO.read(getClass().getResourceAsStream("/HUD/Hud.gif")).getSubimage(0, 12, 12, 11);

			// Inicializa las fuentes de texto
			fontMenu = new Font("Arial", Font.BOLD, 18);
			font = new Font("Arial", Font.BOLD, 15);
			font2 = new Font("Arial", Font.PLAIN, 9);

			// Carga los sonidos de las opciones del menú
			JukeBox.load("/SFX/menuoption.mp3", MENU_OPTION);
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");

		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Actualiza el estado del menú, verificando las entradas del jugador.
	 */
	public void update() {
		// Verifica la entrada del jugador
		handleInput();
	}

	/**
	 * Dibuja el menú en la pantalla, incluyendo el fondo, las opciones de menú
	 * y la cabeza flotante que sigue la selección actual.
	 *
	 * @param g El objeto `Graphics2D` usado para dibujar en la pantalla
	 */
	public void draw(Graphics2D g) {
		// Dibuja el fondo
		g.drawImage(bg, 0, 0, null);

		// Dibuja las opciones del menú
		g.setFont(font);
		g.setColor(Color.YELLOW);
		g.drawRoundRect(220, 170, 245, 140, 50, 50);
		g.fillRect(230, 180, 225, 120); // Rellena un rectángulo

		g.setColor(Color.WHITE);

		// Dibuja la cabeza flotante dependiendo de la opción seleccionada
		if (currentChoice == 0)
			g.drawImage(head, 270, 213, null);
		else if (currentChoice == 1)
			g.drawImage(head, 270, 238, null);
		else if (currentChoice == 2)
			g.drawImage(head, 270, 263, null);
		else if (currentChoice == 3)
			g.drawImage(head, 270, 281, null);
		else if (currentChoice == 4)
			g.drawImage(head, 270, 299, null);
		else if (currentChoice == 5)
			g.drawImage(head, 270, 317, null);

		// Dibuja otros elementos
		g.setFont(font2);
		g.drawString("tonikolaba \u00A9 \u00AE", 20, 468);
	}

	/**
	 * Método para manejar la selección de opciones. Este método debe ser sobrescrito
	 * por las clases derivadas para definir el comportamiento específico de la selección.
	 */
	protected void select() {
		throw new IllegalStateException("Needs to be overwritten");
	}

	/**
	 * Maneja la entrada del jugador para navegar por las opciones del menú.
	 */
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select(); // Realiza la selección si se presiona Enter
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play(MENU_OPTION, 0); // Reproduce el sonido al mover hacia arriba
			currentChoice--; // Mueve la selección hacia arriba
		}
		if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
			JukeBox.play(MENU_OPTION, 0); // Reproduce el sonido al mover hacia abajo
			currentChoice++; // Mueve la selección hacia abajo
		}
	}

	/**
	 * Obtiene la referencia del jugador en el estado actual.
	 *
	 * @return El objeto `Player` que representa al jugador en este estado
	 */
	public Player getPlayer() {
		return player;
	}
}