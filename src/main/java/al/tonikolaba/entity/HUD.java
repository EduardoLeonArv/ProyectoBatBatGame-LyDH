package al.tonikolaba.entity;

import javax.imageio.ImageIO;

import al.tonikolaba.handlers.LoggingHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * HUD (Heads-Up Display) class used to render player information on the screen.
 * It displays the player's health and lives as images and the remaining time as a string.
 *
 * @author ArtOfSoul
 */
public class HUD {

    /** Player object associated with this HUD. */
    private Player player;
    /** Images to represent health and lives. */
    private BufferedImage heart;
    /** Images to represent health and life. */
    private BufferedImage life;

    /**
     * Constructor for the HUD class.
     * Initializes the HUD for the given player and loads the HUD images.
     *
     * @param p The player associated with this HUD
     */
    public HUD(Player p) {
        player = p;
        try {
            // Load the HUD images from the resources
            BufferedImage image = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/HUD/Hud.gif"
                    )
            );
            // Extract the specific parts of the image for heart and life icons
            heart = image.getSubimage(0, 0, 13, 12);
            life = image.getSubimage(0, 12, 12, 11);
        } catch (Exception e) {
            // Log any error that occurs while loading the images
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Draws the HUD on the screen, displaying the player's health, lives, and time.
     *
     * @param g The Graphics2D object used for drawing
     */
    public void draw(Graphics2D g) {
        // Draw health icons (hearts) based on the player's current health
        for (int i = 0; i < player.getHealth(); i++) {
            g.drawImage(heart, 10 + i * 15, 10, null);
        }

        // Draw life icons based on the player's remaining lives
        for (int i = 0; i < player.getLives(); i++) {
            g.drawImage(life, 10 + i * 15, 25, null);
        }

        // Draw the player's remaining time as a string
        g.setColor(java.awt.Color.WHITE);
        g.drawString(player.getTimeToString(), 290, 15);
    }

}
