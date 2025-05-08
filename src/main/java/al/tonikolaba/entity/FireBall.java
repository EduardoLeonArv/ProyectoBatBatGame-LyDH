package al.tonikolaba.entity;

import javax.imageio.ImageIO;
import al.tonikolaba.tilemap.TileMap;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a fireball projectile in the game.
 * The fireball moves in a straight line, can hit targets, and has an animation for hitting.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class FireBall extends MapObject {

    /** Logger for handling errors during sprite loading. */
    private static final Logger LOGGER = Logger.getLogger(FireBall.class.getName());

    /** Flag indicating if the fireball has hit a target. */
    private boolean hit;

    /** Flag indicating if the fireball should be removed from the game. */
    private boolean remove;

    /** Array of sprites for the fireball's normal animation. */
    private BufferedImage[] sprites;

    /** Array of sprites for the fireball's hit animation. */
    private BufferedImage[] hitSprites;

    /**
     * Constructor for FireBall.
     * Initializes the fireball's properties, direction, and animation frames.
     *
     * @param tm the tile map associated with the fireball.
     * @param right true if the fireball is moving to the right, false otherwise.
     */
    public FireBall(TileMap tm, boolean right) {
        super(tm);

        facingRight = right;

        moveSpeed = 3.8;
        dx = right ? moveSpeed : -moveSpeed;

        width = 30;
        height = 30;
        cwidth = 14;
        cheight = 14;

        // Load sprites for the fireball.
        try {
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/fireball.gif")
            );

            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

            hitSprites = new BufferedImage[3];
            for (int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spritesheet.getSubimage(
                        i * width,
                        height,
                        width,
                        height
                );
            }

            animation.setFrames(sprites);
            animation.setDelay(4);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading fireball sprites", e);
        }
    }

    /**
     * Sets the fireball as having hit a target.
     * Changes the animation to the hit animation and stops movement.
     */
    public void setHit() {
        if (hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(4);
        dx = 0;
    }

    /**
     * Checks if the fireball has hit a target.
     * @return true if the fireball has hit, false otherwise.
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Checks if the fireball should be removed from the game.
     * @return true if the fireball should be removed, false otherwise.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Updates the fireball's position and state.
     * Handles collisions, transitions to hit animation if needed, and marks for removal when the animation finishes.
     */
    public void update() {
        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    // Draw method is inherited from MapObject.
}
