package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

/**
 * Clase que representa el nivel 3 del juego.
 * Gestiona los enemigos, eventos y el estado del nivel.
 */
public class Level3State extends GameState {

	public Level3State(GameStateManager gsm, Player player) {
		super(gsm, player);
		init(GameStateManager.LEVEL4STATE);
	}

	@Override
	public void init(int nextLevel) {
		super.init(nextLevel);
		temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);
		generateTileMap("/Maps/level3.map", 140, 0, false);
		setupGameObjects(300, 131, 2850, 371, false);
		setupTitle(new int[]{0, 0, 178, 20}, new int[]{0, 33, 91, 13});
		setupMusic("level2", "/Music/level1v2.mp3", true);
		configureEnemies(
				new EnemyType[]{EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.UFO},
				new int[][]{{750, 100}, {900, 150}, {1320, 250}}
		);
	}

	@Override
	public void update() {
		super.update();
		handleEventQuake(2175, new int[]{60, 120, 150, 180, 300});
	}
}
