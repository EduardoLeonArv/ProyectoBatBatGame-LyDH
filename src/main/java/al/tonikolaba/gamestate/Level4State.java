package al.tonikolaba.gamestate;

import java.security.SecureRandom;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.batbat.Piece;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.Spirit;

public class Level4State extends AbstractLevelState {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
	private Piece tlp, trp, blp, brp;
	private Spirit spirit;

	public Level4State(GameStateManager gsm, Player player) {
		super(gsm, player);
	}

	@Override
	protected int[][] getEnemyCoordinates() {
		return new int[][]{{-9000, 9000}};
	}

	@Override
	protected EnemyType[] getEnemyTypes() {
		return new EnemyType[]{EnemyType.SPIRIT};
	}

	@Override
	protected String getMapPath() {
		return "/Maps/level4.map";
	}

	@Override
	protected String getMusicPath() {
		return "/Music/level1boss.mp3";
	}

	private void initPieces() {
		tlp = createPiece(260, 345, new int[]{0, 0, 10, 10});
		trp = createPiece(270, 345, new int[]{10, 0, 10, 10});
		blp = createPiece(260, 355, new int[]{0, 10, 10, 10});
		brp = createPiece(270, 355, new int[]{10, 10, 10, 10});
	}

	private Piece createPiece(int x, int y, int[] coords) {
		Piece piece = new Piece(tileMap, coords);
		piece.setPosition(x, y);
		return piece;
	}
}
