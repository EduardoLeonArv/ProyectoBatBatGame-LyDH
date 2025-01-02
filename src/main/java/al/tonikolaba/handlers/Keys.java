package al.tonikolaba.handlers;

import java.awt.event.KeyEvent;

/**
 * Clase que administra los estados actuales y previos de las teclas utilizadas en el juego.
 * Permite rastrear si una tecla está presionada, liberada o si cualquier tecla está en uso.
 */
public class Keys {

	/** Número total de teclas rastreadas. */
	public static final int NUM_KEYS = 16;

	/** Representa la tecla de movimiento hacia arriba. */
	public static final int UP = 0;

	/** Representa la tecla de movimiento hacia la izquierda. */
	public static final int LEFT = 1;

	/** Representa la tecla de movimiento hacia abajo. */
	public static final int DOWN = 2;

	/** Representa la tecla de movimiento hacia la derecha. */
	public static final int RIGHT = 3;

	/** Representa el botón de acción 1 (por ejemplo, ataque). */
	public static final int BUTTON1 = 4;

	/** Representa el botón de acción 2 (por ejemplo, salto). */
	public static final int BUTTON2 = 5;

	/** Representa el botón de acción 3 (por ejemplo, usar un objeto). */
	public static final int BUTTON3 = 6;

	/** Representa el botón de acción 4 (por ejemplo, abrir un menú). */
	public static final int BUTTON4 = 7;

	/** Representa la tecla Enter, utilizada para confirmar o seleccionar. */
	public static final int ENTER = 8;

	/** Representa la tecla Escape, utilizada para retroceder o pausar. */
	public static final int ESCAPE = 9;


	/** Estado actual de cada tecla (true si está presionada). */
	private static final boolean[] KEY_STATE = new boolean[NUM_KEYS];
	/** Estado previo de cada tecla (true si estaba presionada en el último fotograma). */
	public static boolean[] prevKeyState = new boolean[NUM_KEYS];

	/**
	 * Constructor vacío. La clase está diseñada como una clase de utilidad y no debería ser instanciada.
	 */
	public Keys() {
		// Constructor vacío intencionado
	}

	/**
	 * Actualiza el estado de una tecla específica.
	 *
	 * @param i Código de la tecla presionada.
	 * @param b {@code true} si la tecla está presionada, {@code false} si está liberada.
	 */
	public static void keySet(int i, boolean b) {
		if (i == KeyEvent.VK_UP)
			getKeyState()[UP] = b;
		else if (i == KeyEvent.VK_LEFT)
			getKeyState()[LEFT] = b;
		else if (i == KeyEvent.VK_DOWN)
			getKeyState()[DOWN] = b;
		else if (i == KeyEvent.VK_RIGHT)
			getKeyState()[RIGHT] = b;
		else if (i == KeyEvent.VK_W)
			getKeyState()[BUTTON1] = b;
		else if (i == KeyEvent.VK_E)
			getKeyState()[BUTTON2] = b;
		else if (i == KeyEvent.VK_R)
			getKeyState()[BUTTON3] = b;
		else if (i == KeyEvent.VK_F)
			getKeyState()[BUTTON4] = b;
		else if (i == KeyEvent.VK_ENTER)
			getKeyState()[ENTER] = b;
		else if (i == KeyEvent.VK_ESCAPE)
			getKeyState()[ESCAPE] = b;
	}

	/**
	 * Actualiza el estado previo de todas las teclas al estado actual.
	 * Debe ser llamado al final de cada ciclo de juego.
	 */
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = getKeyState()[i];
		}
	}

	/**
	 * Verifica si una tecla específica fue presionada (es decir, está activa en el estado actual,
	 * pero no estaba activa en el estado previo).
	 *
	 * @param i Índice de la tecla en cuestión.
	 * @return {@code true} si la tecla fue presionada, {@code false} en caso contrario.
	 */
	public static boolean isPressed(int i) {
		return getKeyState()[i] && !prevKeyState[i];
	}

	/**
	 * Verifica si cualquier tecla está presionada en el estado actual.
	 *
	 * @return {@code true} si al menos una tecla está presionada, {@code false} en caso contrario.
	 */
	public static boolean anyKeyPress() {
		for (int i = 0; i < NUM_KEYS; i++) {
			if (getKeyState()[i])
				return true;
		}
		return false;
	}

	/**
	 * Obtiene el arreglo que representa el estado actual de las teclas.
	 *
	 * @return Arreglo de estados de teclas (true si está presionada, false si no).
	 */
	public static boolean[] getKeyState() {
		return KEY_STATE;
	}

	/**
	 * Establece manualmente el estado de una tecla específica.
	 *
	 * @param key Código de la tecla.
	 * @param pressed {@code true} si la tecla está presionada, {@code false} si está liberada.
	 */
	public static void setPressed(int key, boolean pressed) {
		keySet(key, pressed);
	}
}
