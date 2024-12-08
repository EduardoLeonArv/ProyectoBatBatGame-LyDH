package al.tonikolaba.entity;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.tilemap.TileMap;
import java.util.logging.Logger;

/**
 * Enemy class representing an enemy in the game.
 * Handles health, damage, and interactions with the player.
 */
public class Enemy extends MapObject {

    private static final Logger logger = Logger.getLogger(Enemy.class.getName()); // Logger agregado

    public int health;
    protected int maxHealth;
    protected boolean dead;
    private Player player; // Reference to the player
    protected int damage;
    protected boolean remove;
    public boolean flinching;
    public long flinchCount;

    // Constructor modificado
    public Enemy(TileMap tm, Player player) {
        super(tm);
        this.player = player; // Vincula el jugador con el enemigo
        this.remove = false;
    }

    // Check if the enemy is dead
    public boolean isDead() {
        return dead;
    }

    // Check if the enemy should be removed
    public boolean shouldRemove() {
        return remove;
    }

    // Get the damage this enemy deals
    public int getDamage() {
        return damage;
    }

    // Método hit modificado
    public void hit(int damage) {
        if (dead || flinching) return;

        JukeBox.play("enemyhit");
        health -= damage;

        if (health < 0) health = 0;

        if (health == 0) {
            dead = true;

            // Incrementa el puntaje del jugador
            if (player != null) {
                player.increaseScore(10);
                logger.info("Enemy defeated! Current score: " + player.getScore()); // Sustituye System.out.println
            }

            remove = true; // Marca al enemigo para ser eliminado
        }

        flinching = true;
        flinchCount = 0;
    }

    // Method to update the enemy (needs to be overridden by subclasses)
    public void update() {
        throw new IllegalStateException("Needs to be overwritten");
    }

    // Enemy types (for categorization)
    public enum EnemyType {
        RED_ENERGY, UFO, XHELBAT, ZOGU, SPIRIT
    }
}
