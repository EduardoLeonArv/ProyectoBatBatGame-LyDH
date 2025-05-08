package al.tonikolaba.entity;

/**
 * Esta clase maneja el almacenamiento de la información del jugador, como vidas, salud, tiempo y puntaje.
 * Proporciona métodos para obtener y establecer estos valores, así como inicializar los valores predeterminados.
 *
 * @author N.Kolaba
 */
public class PlayerSave {

	/** Número de vidas del jugador */
	private static int lives = 3;
	/** Salud del jugador */
	private static int health = 5;
	/** Tiempo transcurrido en el juego */
	private static long time = 0;
	/** Puntaje del jugador. */
	private static int score = 0;


	/**
	 * Constructor privado para evitar la creación de instancias de esta clase.
	 */
	public PlayerSave() {
		// throw new IllegalStateException("Utility Class");
	}

	/**
	 * Inicializa los valores predeterminados para las estadísticas del jugador.
	 * Establece las vidas, salud, tiempo y puntaje a sus valores iniciales.
	 */
	public static void init() {
		lives = 3;
		health = 5;
		time = 0;
		score = 0; // Reiniciar el puntaje al inicializar
	}

	/**
	 * Obtiene el número de vidas del jugador.
	 *
	 * @return El número de vidas del jugador
	 */
	public static int getLives() {
		return lives;
	}

	/**
	 * Establece el número de vidas del jugador.
	 *
	 * @param i El número de vidas a establecer
	 */
	public static void setLives(int i) {
		lives = i;
	}

	/**
	 * Obtiene la salud del jugador.
	 *
	 * @return La salud del jugador
	 */
	public static int getHealth() {
		return health;
	}

	/**
	 * Establece la salud del jugador.
	 *
	 * @param i La cantidad de salud a establecer
	 */
	public static void setHealth(int i) {
		health = i;
	}

	/**
	 * Obtiene el tiempo transcurrido en el juego.
	 *
	 * @return El tiempo transcurrido en el juego
	 */
	public static long getTime() {
		return time;
	}

	/**
	 * Establece el tiempo transcurrido en el juego.
	 *
	 * @param t El tiempo a establecer
	 */
	public static void setTime(long t) {
		time = t;
	}

	/**
	 * Obtiene el puntaje actual del jugador.
	 *
	 * @return El puntaje del jugador
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * Establece el puntaje del jugador.
	 *
	 * @param s El puntaje a establecer
	 */
	public static void setScore(int s) {
		score = s;
	}
}
