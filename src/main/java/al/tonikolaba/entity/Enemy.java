package al.tonikolaba.entity;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.tilemap.TileMap;
import java.util.logging.Logger;

/**
 * Enemy class representing an enemy in the game.
 * Handles health, damage, and interactions with the player.
 */
public class Enemy extends MapObject {

    /** Logger instance for logging enemy events. */
    private static final Logger logger = Logger.getLogger(Enemy.class.getName());

    /** Current health of the enemy. */
    public int health;
    /** Maximum health of the enemy. */
    protected int maxHealth;
    /** Flag indicating if the enemy is dead. */
    protected boolean dead;
    /** Reference to the player interacting with the enemy. */
    private Player player;
    /** Damage dealt by the enemy. */
    protected int damage;
    /** Flag indicating if the enemy should be removed from the game. */
    protected boolean remove;
    /** Flag indicating if the enemy is flinching after taking damage. */
    public boolean flinching;
    /** Counter for flinching duration. */
    public long flinchCount;

    /**
     * Constructor for the Enemy class.
     * @param tm the tile map associated with the enemy.
     * @param player the player interacting with the enemy.
     */
    public Enemy(TileMap tm, Player player) {
        super(tm);
        this.player = player;
        this.remove = false;
    }

    /**
     * Checks if the enemy is dead.
     * @return true if the enemy is dead, false otherwise.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Checks if the enemy should be removed from the game.
     * @return true if the enemy should be removed, false otherwise.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Gets the damage dealt by the enemy.
     * @return the damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Applies damage to the enemy and updates its state.
     * @param damage the amount of damage to apply.
     */
    public void hit(int damage) {
        if (dead || flinching) {
            return;
        }

        JukeBox.play("enemyhit");
        health -= damage;

        if (health < 0) {
            health = 0;
        }

        if (health == 0) {
            dead = true;

            if (player != null) {
                player.increaseScore(10);
                logger.info("Enemy defeated! Current score: " + player.getScore());
            }

            remove = true;
        }

        flinching = true;
        flinchCount = 0;
    }

    /**
     * Updates the enemy's state.
     * This method should be overridden by subclasses.
     */
    public void update() {
        throw new IllegalStateException("Needs to be overwritten");
    }

    /**
     * Checks if the enemy is currently flinching.
     * @return true if the enemy is flinching, false otherwise.
     */
    public boolean isFlinching() {
        return flinching;
    }

    /**
     * Gets the current health of the enemy.
     * @return the current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health of the enemy.
     * @return the maximum health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the movement speed of the enemy.
     * @return the movement speed.
     */
    public double getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Enumeration of enemy types for categorization.
     */
    public enum EnemyType {
        /** Variables */
        RED_ENERGY, UFO, XHELBAT, ZOGU, SPIRIT
    }
}
