package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.entity.Player;

/**
 * Clase que representa el estado "How to Play" en el juego.
 * Proporciona instrucciones al jugador sobre cómo interactuar con los controles del juego.
 * Permite regresar al menú principal o salir del estado al presionar una tecla.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class HowtoPlay extends BasicState {

	/** Referencia al jugador actual. */
	private Player player;

	/**
	 * Constructor que inicializa el estado "How to Play".
	 *
	 * @param gsm    Gestor de estados del juego.
	 * @param player Referencia al jugador actual.
	 */
	public HowtoPlay(GameStateManager gsm, Player player) {
		super(gsm);
		this.player = player;
		this.options = new String[] { "Option1", "Option2" }; // Opciones ficticias
	}

	/**
	 * Dibuja las instrucciones en pantalla.
	 *
	 * @param h Contexto gráfico utilizado para renderizar los elementos.
	 */
	@Override
	public void draw(Graphics2D h) {
		super.draw(h);
		h.setFont(font);
		h.setColor(Color.YELLOW);
		h.fillRect(200, 160, 280, 200); // Llena un rectángulo
		h.drawRoundRect(190, 150, 300, 220, 50, 50);
		h.setColor(Color.RED);

		// Dibuja las instrucciones en pantalla
		h.drawString("< >      -   MOVE LEFT OR RIGHT", 230, 200);
		h.drawString("W+R   -   JUMP AND HIT ", 230, 220);
		h.drawString("R         -   SINGLE HIT ", 230, 240);
		h.drawString("F         -   BIG HIT ", 230, 260);
		h.drawString("W        -   JUMP UP ", 230, 280);
		h.drawString("ESC   -   PAUSE ", 230, 300);
		h.setFont(font);
		h.drawString(" * Press any key to go Back ", 240, 330);
	}

	/**
	 * Selecciona una opción del menú.
	 * Este metodo no tiene implementación porque "How to Play" no tiene opciones seleccionables.
	 */
	@Override
	protected void select() {
		// No hay opciones seleccionables en este estado.
	}

	/**
	 * Maneja las entradas del jugador para interactuar con el estado.
	 * Permite regresar al menú principal o salir del estado al presionar las teclas correspondientes.
	 */
	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER)) {
			select();
		}
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play("menuoption", 0);
			gsm.setState(GameStateManager.MENUSTATE); // Regresar al menú principal
			currentChoice--;
		}
	}
}
