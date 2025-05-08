package al.tonikolaba.entity;

import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;

import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * Represents an energy particle in the game.
 * Energy particles are visual effects that move in a specific direction
 * and are removed after a certain period.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class EnergyParticle extends MapObject {

    /** Random generator for particle movement variations. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /** Constant representing upward movement for the particle. */
    public static final int ENERGY_UP = 0;
    /** Constant representing leftward movement for the particle. */
    public static final int ENERGY_LEFT = 1;
    /** Constant representing downward movement for the particle. */
    public static final int ENERGY_DOWN = 2;
    /** Constant representing rightward movement for the particle. */
    public static final int ENERGY_RIGHT = 3;

    /** Counter for the particle's lifespan. */
    private int count;
    /** Flag indicating if the particle should be removed from the game. */
    private boolean remove;
    /** Array of sprites representing the particle's animation frames. */
    private BufferedImage[] sprites;

    /**
     * Constructor for EnergyParticle.
     * Initializes the particle's position, direction, and animation frames.
     *
     * @param tm the tile map associated with the particle.
     * @param x the initial x-coordinate of the particle.
     * @param y the initial y-coordinate of the particle.
     * @param dir the direction in which the particle moves.
     */
    public EnergyParticle(TileMap tm, double x, double y, int dir) {
        super(tm);
        this.x = x;
        this.y = y;

        // Generate random velocities based on direction.
        double d1 = SECURE_RANDOM.nextDouble() * 2.5 - 1.25;
        double d2 = -SECURE_RANDOM.nextDouble() - 0.8;

        if (dir == ENERGY_UP) {
            dx = d1;
            dy = d2;
        } else if (dir == ENERGY_LEFT) {
            dx = d2;
            dy = d1;
        } else if (dir == ENERGY_DOWN) {
            dx = d1;
            dy = -d2;
        } else {
            dx = -d2;
            dy = d1;
        }

        count = 0;
        sprites = Content.getEnergyParticle()[0];
        animation.setFrames(sprites);
        animation.setDelay(-1);
    }

    /**
     * Updates the particle's position and state.
     * Removes the particle after it has existed for a certain duration.
     */
    public void update() {
        x += dx;
        y += dy;
        count++;
        if (count == 60) {
            remove = true;
        }
    }

    /**
     * Checks if the particle should be removed from the game.
     * @return true if the particle should be removed, false otherwise.
     */
    public boolean shouldRemove() {
        return remove;
    }
}
