package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.entity.Player;

/**
 * Clase que representa el estado de pausa del juego.
 * Muestra un menú de pausa y permite al jugador reanudar el juego o regresar al menú principal.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class PauseState extends GameState {

	/** Referencia al jugador actual. */
	private Player player;

	/**
	 * Constructor que inicializa el estado de pausa con el gestor de estados y el jugador.
	 *
	 * @param gsm Gestor de estados del juego.
	 * @param player Referencia al jugador actual.
	 */
	public PauseState(GameStateManager gsm, Player player) {
		super(gsm, player); // Llama al constructor de GameState con el jugador
		this.player = player;
	}

	/**
	 * Actualiza el estado de pausa. Llama al manejo de entradas para procesar la interacción del jugador.
	 */
	@Override
	public void update() {
		handleInput();
	}

	/**
	 * Dibuja el menú de pausa en la pantalla.
	 * Muestra información del jugador como vidas y puntuación.
	 *
	 * @param g Objeto {@link Graphics2D} utilizado para realizar el dibujo.
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRoundRect(180, 130, 300, 220, 50, 50);
		g.setColor(Color.YELLOW);
		g.fillRect(190, 140, 280, 200); // Llena un rectángulo
		g.setColor(Color.RED);
		g.setFont(fontMenu);
		g.drawString("Game Paused", 280, 230);
		g.setFont(font);

		// Mostrar información del jugador (opcional)
		g.drawString("* press ESC to continue", 250, 255);
		g.drawString("Player Lives: " + player.getLives(), 250, 275);
		g.drawString("Score: " + player.getScore(), 250, 295);
	}

	/**
	 * Maneja la entrada del jugador durante el estado de pausa.
	 * Permite reanudar el juego o regresar al menú principal.
	 */
	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(false);
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
}
