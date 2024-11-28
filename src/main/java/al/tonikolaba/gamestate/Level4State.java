
package al.tonikolaba.gamestate;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.security.SecureRandom;
import al.tonikolaba.entity.Enemy.EnemyType;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.*;
import al.tonikolaba.entity.batbat.Piece;

public class Level4State extends AbstractLevelState {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
	private ArrayList<EnergyParticle> energyParticles;
	private Piece tlp, trp, blp, brp;
	private Spirit spirit;

	public Level4State(GameStateManager gsm, Player player) {
		super(gsm, player);
	}

	@Override
	protected int[][] getEnemyCoordinates() {
		return new int[][]{{-9000, 9000}}; // Spirit's unique starting position
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

	@Override
	protected int getNextLevelState() {
		return GameStateManager.ACIDSTATE;
	}

	@Override
	public void init(int nextLevel) {
		super.init(nextLevel);
		energyParticles = new ArrayList<>();
		hud = new HUD(player);
		portal = new Portal(tileMap);
		portal.setPosition(270, 395);
		teleport = new Teleport(tileMap);
		teleport.setPosition(480, 40);
		initPieces();
	}

	private void initPieces() {
		tlp = new Piece(tileMap, new int[]{0, 0, 10, 10});
		trp = new Piece(tileMap, new int[]{10, 0, 10, 10});
		blp = new Piece(tileMap, new int[]{0, 10, 10, 10});
		brp = new Piece(tileMap, new int[]{10, 10, 10, 10});
		tlp.setPosition(260, 345);
		trp.setPosition(270, 345);
		blp.setPosition(260, 355);
		brp.setPosition(270, 355);
	}

	@Override
	public void update() {
		super.update();
		if (spirit.isDead() && !eventFinish) {
			handleBossDefeated();
		}
	}

	private void handleBossDefeated() {
		eventCount++;
		if (eventCount == 1) {
			player.stop();
			JukeBox.stop(LEVEL_BOSS_MUSIC_NAME);
			enemies.clear();
		}
		if (eventCount <= 120 && eventCount % 15 == 0) {
			explosions.add(new Explosion(tileMap, spirit.getx(), spirit.gety()));
			JukeBox.play("explode");
		}
		if (eventCount == 180) {
			JukeBox.play("fanfare");
		}
		if (eventCount == 390) {
			eventFinish = true;
			eventCount = 0;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		portal.draw(g);
		for (Piece piece : new Piece[]{tlp, trp, blp, brp}) {
			piece.draw(g);
		}
	}
}
