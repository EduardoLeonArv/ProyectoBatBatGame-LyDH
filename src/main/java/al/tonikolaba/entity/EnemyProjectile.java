package al.tonikolaba.entity;

import al.tonikolaba.tilemap.TileMap;

/**
 * Abstract class representing a projectile fired by an enemy.
 * Manages the state of the projectile, including damage and removal.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public abstract class EnemyProjectile extends MapObject {

    /** Flag indicating if the projectile has hit its target. */
    protected boolean hit;
    /** Flag indicating if the projectile should be removed from the game. */
    protected boolean remove;
    /** Damage dealt by the projectile. */
    protected int damage;

    /**
     * Constructor for EnemyProjectile.
     * @param tm the tile map associated with the projectile.
     */
    protected EnemyProjectile(TileMap tm) {
        super(tm);
    }

    /**
     * Gets the damage dealt by the projectile.
     * @return the damage value.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Checks if the projectile should be removed from the game.
     * @return true if the projectile should be removed, false otherwise.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Abstract method to handle the state when the projectile hits its target.
     * Implementations should define specific behavior.
     */
    public abstract void setHit();

    /**
     * Abstract method to update the state of the projectile.
     * Implementations should define specific behavior.
     */
    public abstract void update();
}
