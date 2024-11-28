package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;

public class Level3State extends AbstractLevelState {

	public Level3State(GameStateManager gsm, Player player) {
		super(gsm, player);
	}

	@Override
	protected int[][] getEnemyCoordinates() {
		return new int[][]{{750, 100}, {900, 150}, {1320, 250}};
	}

	@Override
	protected EnemyType[] getEnemyTypes() {
		return new EnemyType[]{EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.UFO};
	}

	@Override
	protected String getMapPath() {
		return "/Maps/level3.map";
	}

	@Override
	protected String getMusicPath() {
		return "/Music/level1v2.mp3";
	}
}
