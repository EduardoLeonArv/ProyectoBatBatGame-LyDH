package al.tonikolaba.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import al.tonikolaba.entity.enemies.RedEnergy;
import al.tonikolaba.tilemap.TileMap;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @author N. Kolaba
 */

/**
 * Spirit enemy class.
 * Handles unique behaviors and attacks of the Spirit boss.
 */
public class Spirit extends Enemy {

	/* Logger and secure random for security hotspots */
	private static final Logger LOGGER = Logger.getLogger(Spirit.class.getName());
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	public BufferedImage[] sprites;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;

	private boolean active;
	private boolean finalAttack;

	private int step;
	private int stepCount;

	// Attack pattern
	private int[] steps = { 0, 1, 0, 1, 2, 1, 0, 2, 1, 2 };

	//// Attacks:
	// Fly around throwing dark energy (0)
	// Floor sweep (1)
	// Crash down on floor to create shockwave (2)
	//// Special:
	// After half hp, create shield
	// After quarter hp, bullet hell

	private RedEnergy[] shield;
	private double ticks;

	public Spirit(TileMap tm, Player p, ArrayList<Enemy> enemies, ArrayList<Explosion> explosions) {

		super(tm, p);
		this.player = p; // Vinculación directa con el jugador
		this.enemies = enemies;
		this.explosions = explosions;

		width = 40;
		height = 40;
		cwidth = 30;
		cheight = 30;

		health = maxHealth = 80;

		moveSpeed = 1.4;

		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Enemies/Spirit.gif")
			);
			sprites = new BufferedImage[4];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
		} catch (Exception e) {
			LOGGER.log(
					Level.SEVERE,
					"Error loading sprites",
					e
			);
		}

		damage = 1;

		animation.setFrames(sprites);
		animation.setDelay(1);

		shield = new RedEnergy[2];

		step = 0;
		stepCount = 0;
	}

	public void setActive() {
		active = true;
	}

	@Override
	public void update() {

		if (health == 0)
			return;

		// Restart attack pattern
		if (step == steps.length) {
			step = 0;
		}

		ticks++;

		if (flinching) {
			flinchCount++;
			if (flinchCount == 8)
				flinching = false;
		}

		x += dx;
		y += dy;

		animation.update();

		if (!active)
			return;

		//////////////
		// Special
		//////////////
		if (health <= maxHealth / 2) {
			if (shield[0] == null) {
				shield[0] = new RedEnergy(tileMap, player); // Incluye al jugador
				shield[0].setPermanent(true);
				enemies.add(shield[0]);
			}
			if (shield[1] == null) {
				shield[1] = new RedEnergy(tileMap, player); // Incluye al jugador
				shield[1].setPermanent(true);
				enemies.add(shield[1]);
			}
			double pos = ticks / 32;
			shield[0].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
			pos += 3.1415;
			shield[1].setPosition(x + 30 * Math.sin(pos), y + 30 * Math.cos(pos));
		}

		if (!finalAttack && health <= maxHealth / 4) {
			stepCount = 0;
			finalAttack = true;
		}

		if (finalAttack) {
			stepCount++;
			if (stepCount == 1) {
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
				x = -9000;
				y = 9000;
				dx = dy = 0;
			}
			if (stepCount == 60) {
				x = tileMap.getWidth() / 2.0;
				y = tileMap.getHeight() / 2.0;
				explosions.add(new Explosion(tileMap, (int) x, (int) y));
			}
			if (stepCount >= 90 && stepCount % 30 == 0) {
				RedEnergy de = new RedEnergy(tileMap, player); // Incluye al jugador
				de.setPosition(x, y);
				de.setVector(3 * Math.sin((double) stepCount / 32), 3 * Math.cos((double) stepCount / 32));
				de.setType(RedEnergy.BOUNCE);
				enemies.add(de);
			}
			return;
		}

		//////////////
		// Attacks
		//////////////

		// Fly around dropping bombs
		if (steps[step] == 0) {
			stepCount++;
			if (y > 60) {
				dy = -4;
			}
			if (y < 60) {
				dy = 0;
				y = 60;
				dx = -1;
			}
			if (y == 60) {
				if (dx == -1 && x < 60) {
					dx = 1;
				}
				if (dx == 1 && x > tileMap.getWidth() - 60) {
					dx = -1;
				}
			}
			if (stepCount % 10 == 0) {
				RedEnergy de = new RedEnergy(tileMap, player); // Incluye al jugador
				de.setType(RedEnergy.GRAVITY);
				de.setPosition(x, y);
				int dir = SECURE_RANDOM.nextBoolean() ? 1 : -1;
				de.setVector(dir, 0);
				enemies.add(de);
			}
			if (stepCount == 559) {
				step++;
				stepCount = 0;
				right = left = false;
			}
		}
		// Floor sweep
		else if (steps[step] == 1) {
			// Sin cambios
		}
		// Shockwave
		else if (steps[step] == 2) {
			// Sin cambios
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (flinching) {
			if (flinchCount % 4 < 2)
				return;
		}
		super.draw(g);
	}
}
