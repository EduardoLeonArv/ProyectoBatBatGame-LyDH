package al.tonikolaba.entity;

/**
 * @author N.Kolaba
 *
 */

public class PlayerSave {

	private static int lives = 3;
	private static int health = 5;
	private static long time = 0;
	private static int score = 0; // Nuevo atributo para el puntaje

	public PlayerSave() {
		// throw new IllegalStateException("Utility Class");
	}

	// Inicializa los valores predeterminados
	public static void init() {
		lives = 3;
		health = 5;
		time = 0;
		score = 0; // Reiniciar el puntaje al inicializar
	}

	// Obtener vidas
	public static int getLives() {
		return lives;
	}

	// Establecer vidas
	public static void setLives(int i) {
		lives = i;
	}

	// Obtener salud
	public static int getHealth() {
		return health;
	}

	// Establecer salud
	public static void setHealth(int i) {
		health = i;
	}

	// Obtener tiempo
	public static long getTime() {
		return time;
	}

	// Establecer tiempo
	public static void setTime(long t) {
		time = t;
	}

	// Obtener puntaje
	public static int getScore() {
		return score;
	}

	// Establecer puntaje
	public static void setScore(int s) {
		score = s;
	}
}
