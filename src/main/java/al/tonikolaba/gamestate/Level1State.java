package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;
import al.tonikolaba.entity.Player;

/**
 * Clase que representa el nivel 1 del juego.
 * Gestiona la inicialización, actualización y configuración de los elementos
 * específicos del nivel, como el mapa, enemigos y fondos.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class Level1State extends GameState {

	/** Referencia al jugador. */
	private Player player;

	/**
	 * Constructor que inicializa el estado del nivel 1.
	 *
	 * @param gsm    Gestor de estados del juego.
	 * @param player Referencia al jugador.
	 */
	public Level1State(GameStateManager gsm, Player player) {
		super(gsm, player);
		this.player = player; // Asignar el jugador
		init(GameStateManager.LEVEL2STATE);
	}

	/**
	 * Inicializa los elementos del nivel, como el mapa, enemigos, fondo y música.
	 *
	 * @param nextLevel Identificador del siguiente nivel al que se accederá al finalizar.
	 */
	@Override
	public void init(int nextLevel) {
		// Configuración inicial de elementos comunes
		super.init(nextLevel);

		// Configuración del mapa y objetos del juego
		generateTileMap("/Maps/level1.map", 0, 140, true);
		setupGameObjects(100, 191, 3700, 131, false);
		setupMusic("level1", "/Music/level1.mp3", true);

		// Configuración de fondos
		sky = new Background("/Backgrounds/qielli1.gif", 0);
		clouds = new Background("/Backgrounds/mali1.gif", 0.1);
		mountains = new Background("/Backgrounds/kodra.gif", 0.2);

		// Configuración de enemigos
		enemyTypesInLevel = new EnemyType[] {
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.XHELBAT,
				EnemyType.XHELBAT, EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.ZOGU
		};

		coords = new int[][] {
				new int[] { 1300, 100 }, new int[] { 1320, 100 }, new int[] { 1340, 100 },
				new int[] { 1660, 100 }, new int[] { 1680, 100 }, new int[] { 1700, 100 },
				new int[] { 2180, 100 }, new int[] { 2960, 100 }, new int[] { 2980, 100 },
				new int[] { 3000, 100 }, new int[] { 2300, 300 }, new int[] { 3500, 350 }
		};

		populateEnemies(enemyTypesInLevel, coords);

		// Configuración del título del nivel
		setupTitle(new int[] { 0, 0, 178, 19 }, new int[] { 0, 33, 93, 13 });
	}

	/**
	 * Actualiza los elementos del nivel, como la lógica del jugador y el entorno.
	 */
	@Override
	public void update() {
		super.update();

		// Lógica específica para el nivel 1
		if (player.getHealth() <= 0) {
			// Manejar la muerte del jugador (pendiente de implementación)
		}
	}
}
