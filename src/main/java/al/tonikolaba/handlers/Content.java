package al.tonikolaba.handlers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * @author ArtOfSoul
 */

// this class loads resources on boot.
// spritesheets are taken from here.

public class Content {

    private static final BufferedImage[][] ENERGY_PARTICLE = load("/Sprites/Player/EnergyParticle.gif", 5, 5);
    private static final BufferedImage[][] EXPLOSIONS = load("/Sprites/Enemies/ExplosionRed.gif", 30, 30);

    private static final BufferedImage[][] ZOGU = load("/Sprites/Enemies/Zogu.gif", 39, 20);
    private static final BufferedImage[][] UFO = load("/Sprites/Enemies/Ufo.gif", 30, 30);
    private static final BufferedImage[][] XHELBAT = load("/Sprites/Enemies/XhelBat.gif", 25, 25);
    private static final BufferedImage[][] RED_ENERGY = load("/Sprites/Enemies/RedEnergy.gif", 20, 20);


    public Content() {
        throw new IllegalStateException("Utility Class");
    }

    public static BufferedImage[][] load(String s, int w, int h) {
        BufferedImage[][] ret;
        try {
            BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
            System.exit(0);
        }
        return new BufferedImage[0][0];
    }

    public static BufferedImage[][] getEnergyParticle() {
        return ENERGY_PARTICLE;
    }

    public static BufferedImage[][] getExplosions() {
        return EXPLOSIONS;
    }

    public static BufferedImage[][] getZogu() {
        return ZOGU;
    }

    public static BufferedImage[][] getUfo() {
        return UFO;
    }

    public static BufferedImage[][] getXhelbat() {
        return XHELBAT;
    }

    public static BufferedImage[][] getRedEnergy() {
        return RED_ENERGY;
    }

}
