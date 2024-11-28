package al.tonikolaba.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.security.SecureRandom;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.*;
import al.tonikolaba.entity.batbat.Piece;
import al.tonikolaba.entity.enemies.RedEnergy;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.Background;

/**
 * @author N.Kolaba
 */
public class Level4State extends GameState {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
	private ArrayList<EnergyParticle> energyParticles;
	private Piece tlp, trp, blp, brp;
	private Spirit spirit;

	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart, eventFinish, eventDead, eventPortal, flash, eventBossDead;
	protected Portal portal;
	private ArrayList<Rectangle> tb;

	public Level4State(GameStateManager gsm, Player player) {
		super(gsm, player);
		init(GameStateManager.ACIDSTATE);
	}

	@Override
	public void init(int nextLevel) {
		super.init(nextLevel);

		// Backgrounds and Tilemap
		temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);
		generateTileMap("/Maps/level4.map", 0, 140, false);

		// Setup entities and player
		setupGameObjects(50, 190, 160, 154, true);
		setupMusic(LEVEL_BOSS_MUSIC_NAME, "/Music/level1boss.mp3", true);

		player = new Player(tileMap);
		player.setPosition(50, 190);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		explosions = new ArrayList<>();
		enemies = new ArrayList<>();
		populateEnemies();

		energyParticles = new ArrayList<>();
		player.init(enemies, energyParticles);

		hud = new HUD(player);

		portal = new Portal(tileMap);
		portal.setPosition(270, 395);
		teleport = new Teleport(tileMap);
		teleport.setPosition(480, 40);

		initPieces();

		eventStart = blockInput = true;
		tb = new ArrayList<>();
		eventStart();
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

	private void populateEnemies() {
		enemies.clear();
		spirit = new Spirit(tileMap, player, enemies, explosions);
		spirit.setPosition(-9000, 9000);
		enemies.add(spirit);
	}

	private void updateEventRectangles(Rectangle rect, int deltaX, int deltaY, int deltaWidth, int deltaHeight) {
		rect.x += deltaX;
		rect.y += deltaY;
		rect.width += deltaWidth;
		rect.height += deltaHeight;
	}

	private void manageEvent(Rectangle rect, int maxCount, Runnable onComplete) {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount <= maxCount) {
			updateEventRectangles(tb.get(0), -6, -4, 12, 8);
		}
		if (eventCount == maxCount) {
			onComplete.run();
			eventCount = 0;
		}
	}

	private void updateExplosions() {
		for (int i = 0; i < explosions.size(); i++) {
			Explosion explosion = explosions.get(i);
			explosion.update();
			if (explosion.shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}

	private void updateEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			enemy.update();
			if (enemy.isDead() || enemy.shouldRemove()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, enemy.getx(), enemy.gety()));
			}
		}
	}

	@Override
	public void update() {
		handleInput();

		if (!eventFinish && spirit.isDead()) eventBossDead = blockInput = true;
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) eventDead = blockInput = true;

		if (eventStart) eventStart();
		if (eventDead) manageEvent(tb.get(0), 120, this::reset);
		if (eventFinish) manageEvent(tb.get(0), 60, () -> gsm.setState(GameStateManager.ACIDSTATE));
		if (eventPortal) eventPortal();
		if (eventBossDead) eventBossDead();

		temple.setPosition(tileMap.getx(), tileMap.gety());
		player.update();
		tileMap.setPosition((double) GamePanel.WIDTH - player.getx(), (double) GamePanel.HEIGHT - player.gety());
		updateEnemies();
		updateExplosions();

		portal.update();
		tlp.update();
		trp.update();
		blp.update();
		brp.update();
	}

	@Override
	public void draw(Graphics2D g) {
		temple.draw(g);
		tileMap.draw(g);
		portal.draw(g);

		for (Enemy enemy : enemies) enemy.draw(g);
		for (Explosion explosion : explosions) explosion.draw(g);

		tlp.draw(g);
		trp.draw(g);
		blp.draw(g);
		brp.draw(g);

		player.draw(g);
		hud.draw(g);

		g.setColor(java.awt.Color.YELLOW);
		for (Rectangle rect : tb) g.fill(rect);

		if (flash) {
			g.setColor(java.awt.Color.WHITE);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if (blockInput || player.getHealth() == 0) return;

		player.setUp(Keys.getKeyState()[Keys.UP]);
		player.setLeft(Keys.getKeyState()[Keys.LEFT]);
		player.setDown(Keys.getKeyState()[Keys.DOWN]);
		player.setRight(Keys.getKeyState()[Keys.RIGHT]);
		player.setJumping(Keys.getKeyState()[Keys.BUTTON1]);
		player.setDashing(Keys.getKeyState()[Keys.BUTTON2]);
		if (Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if (Keys.isPressed(Keys.BUTTON4)) player.setCharging();
	}

	@Override
	protected void reset() {
		player.reset();
		player.setPosition(50, 190);
		populateEnemies();
		eventStart = blockInput = true;
		eventCount = 0;
		eventStart();
	}

	// level started
	private void eventStart() {
		eventCount++;
		if (eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH,
					GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2,
					GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2,
					GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0,
					GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			if (!portal.isOpened())
				tileMap.setShaking(true, 10);
			JukeBox.stop("level1");
		}
		if (eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 60) {
			eventStart = blockInput = false;
			eventCount = 0;
			eventPortal = blockInput = true;
			tb.clear();

		}
	}

	// player has died
	private void eventDead() {
		eventCount++;
		if (eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if (eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2,
					GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 120) {
			if (player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			} else {
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	private void eventPortal() {
		eventCount++;
		if (eventCount == 1) {
			if (portal.isOpened()) {
				eventCount = 360;
			}
		}
		if (eventCount > 60 && eventCount < 180) {
			energyParticles.add(new EnergyParticle(tileMap, 270, 353,
					(int) (SECURE_RANDOM.nextDouble() * 4)));
		}
		if (eventCount >= 160 && eventCount <= 180) {
			if (eventCount % 4 == 0 || eventCount % 4 == 1)
				flash = true;
			else
				flash = false;
		}
		if (eventCount == 181) {
			tileMap.setShaking(false, 0);
			flash = false;
			tlp.setVector(-0.3, -0.3);
			trp.setVector(0.3, -0.3);
			blp.setVector(-0.3, 0.3);
			brp.setVector(0.3, 0.3);
			player.setEmote(Player.SURPRISED_EMOTE);
		}
		if (eventCount == 240) {
			tlp.setVector(0, -5);
			trp.setVector(0, -5);
			blp.setVector(0, -5);
			brp.setVector(0, -5);
		}
		if (eventCount == 300) {
			player.setEmote(Player.NONE_EMOTE);
			portal.setOpening();
		}
		if (eventCount == 360) {
			flash = true;
			spirit.setPosition(270, 395);
			RedEnergy de;
			for (int i = 0; i < 20; i++) {
				de = new RedEnergy(tileMap, player);
				de.setPosition(270, 395);
				de.setVector(SECURE_RANDOM.nextDouble() * 10 - 5,
						SECURE_RANDOM.nextDouble() * -2 - 3);

				enemies.add(de);
			}
		}
		if (eventCount == 362) {
			flash = false;
			JukeBox.loop("level1boss", 0, 60000,
					JukeBox.getFrames("level1boss") - 4000);
		}
		if (eventCount == 420) {
			eventPortal = blockInput = false;
			eventCount = 0;
			spirit.setActive();
		}

	}

	public void eventBossDead() {
		eventCount++;
		if (eventCount == 1) {
			player.stop();
			JukeBox.stop("level1boss");
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
			eventBossDead = false;
			eventCount = 0;
			eventFinish = true;
		}
	}
}
