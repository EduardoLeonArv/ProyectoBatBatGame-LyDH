package al.tonikolaba.entity;

import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents an explosion effect in the game.
 * The explosion is animated and consists of multiple expanding points.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class Explosion extends MapObject {

    /** Array of sprites for the explosion animation. */
    private BufferedImage[] sprites;

    /** Flag indicating if the explosion should be removed from the game. */
    private boolean remove;

    /** Array of points representing the expanding parts of the explosion. */
    private Point[] points;

    /** Speed of the explosion's expansion in horizontal and vertical directions. */
    private int speed;

    /** Speed of the explosion's expansion in diagonal directions. */
    private double diagSpeed;

    /**
     * Constructor for Explosion.
     * Initializes the explosion's position, size, speed, and animation frames.
     *
     * @param tm the tile map associated with the explosion.
     * @param x the initial x-coordinate of the explosion's center.
     * @param y the initial y-coordinate of the explosion's center.
     */
    public Explosion(TileMap tm, int x, int y) {
        super(tm);

        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        speed = 2;
        diagSpeed = 1.41;

        sprites = Content.getExplosions()[0];

        animation.setFrames(sprites);
        animation.setDelay(6);

        points = new Point[8];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(x, y);
        }
    }

    /**
     * Updates the explosion's animation and position.
     * Marks the explosion for removal once the animation has completed.
     */
    public void update() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
        // Update the positions of the points for the expanding explosion.
        points[0].x += speed;
        points[1].x += diagSpeed;
        points[1].y += diagSpeed;
        points[2].y += speed;
        points[3].x -= diagSpeed;
        points[3].y += diagSpeed;
        points[4].x -= speed;
        points[5].x -= diagSpeed;
        points[5].y -= diagSpeed;
        points[6].y -= speed;
        points[7].x += diagSpeed;
        points[7].y -= diagSpeed;
    }

    /**
     * Checks if the explosion should be removed from the game.
     * @return true if the explosion should be removed, false otherwise.
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Draws the explosion on the screen.
     * The explosion consists of multiple animated points expanding outward.
     *
     * @param g the graphics context to draw the explosion.
     */
    @Override
    public void draw(Graphics2D g) {
        setMapPosition();
        for (int i = 0; i < points.length; i++) {
            g.drawImage(animation.getImage(),
                    (int) (points[i].x + xmap - width / 2.0),
                    (int) (points[i].y + ymap - height / 2.0), null);
        }
    }
}
