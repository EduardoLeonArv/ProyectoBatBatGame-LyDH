package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

/**
 * @author ArtOfSoul
 */

import al.tonikolaba.entity.Player;

/**
 * @author ArtOfSoul
 */
public class Level1State extends GameState {

	private Player player; // Referencia al jugador

	public Level1State(GameStateManager gsm, Player player) {
		super(gsm, player);
		this.player = player; // Asignar el jugador
		init(GameStateManager.LEVEL2STATE);
	}

	@Override
	public void init(int nextLevel) {
		// backgrounds
		super.init(nextLevel);

		generateTileMap("/Maps/level1.map", 0, 140, true);
		setupGameObjects(100, 191, 3700, 131, false);
		setupMusic("level1", "/Music/level1.mp3", true);

		sky = new Background("/Backgrounds/qielli1.gif", 0);
		clouds = new Background("/Backgrounds/mali1.gif", 0.1);
		mountains = new Background("/Backgrounds/kodra.gif", 0.2);

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
		setupTitle(new int[] { 0, 0, 178, 19 }, new int[] { 0, 33, 93, 13 });
	}

	@Override
	public void update() {
		super.update();

		// Actualización de lógica específica para el nivel 1
		if (player.getHealth() <= 0) {
			// Manejar muerte del jugador
		}
	}
}
