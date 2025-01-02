package al.tonikolaba.handlers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Clase de utilidad que carga y administra los recursos gráficos del juego, como sprites y partículas.
 * Proporciona métodos para cargar y acceder a los sprites utilizados en el juego.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class Content {

    /** Partículas de energía del jugador. */
    private static final BufferedImage[][] ENERGY_PARTICLE = load("/Sprites/Player/EnergyParticle.gif", 5, 5);
    /** Animaciones de explosiones. */
    private static final BufferedImage[][] EXPLOSIONS = load("/Sprites/Enemies/ExplosionRed.gif", 30, 30);

    /** Sprites del enemigo Zogu. */
    private static final BufferedImage[][] ZOGU = load("/Sprites/Enemies/Zogu.gif", 39, 20);
    /** Sprites del enemigo UFO. */
    private static final BufferedImage[][] UFO = load("/Sprites/Enemies/Ufo.gif", 30, 30);
    /** Sprites del enemigo XhelBat. */
    private static final BufferedImage[][] XHELBAT = load("/Sprites/Enemies/XhelBat.gif", 25, 25);
    /** Sprites de energía roja de los enemigos. */
    private static final BufferedImage[][] RED_ENERGY = load("/Sprites/Enemies/RedEnergy.gif", 20, 20);

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     * Lanza una excepción si se intenta instanciar.
     */
    public Content() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Carga un spritesheet desde una ruta especificada y lo divide en subimágenes del tamaño dado.
     *
     * @param s Ruta del archivo de spritesheet.
     * @param w Ancho de cada subimagen en píxeles.
     * @param h Altura de cada subimagen en píxeles.
     * @return Una matriz bidimensional de {@link BufferedImage} que contiene las subimágenes cargadas.
     */
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

    /**
     * Obtiene las partículas de energía del jugador.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con las partículas de energía.
     */
    public static BufferedImage[][] getEnergyParticle() {
        return ENERGY_PARTICLE;
    }

    /**
     * Obtiene las animaciones de explosiones.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con las explosiones.
     */
    public static BufferedImage[][] getExplosions() {
        return EXPLOSIONS;
    }

    /**
     * Obtiene los sprites del enemigo Zogu.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con los sprites de Zogu.
     */
    public static BufferedImage[][] getZogu() {
        return ZOGU;
    }

    /**
     * Obtiene los sprites del enemigo UFO.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con los sprites de UFO.
     */
    public static BufferedImage[][] getUfo() {
        return UFO;
    }

    /**
     * Obtiene los sprites del enemigo XhelBat.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con los sprites de XhelBat.
     */
    public static BufferedImage[][] getXhelbat() {
        return XHELBAT;
    }

    /**
     * Obtiene los sprites de energía roja de los enemigos.
     *
     * @return Matriz bidimensional de {@link BufferedImage} con los sprites de energía roja.
     */
    public static BufferedImage[][] getRedEnergy() {
        return RED_ENERGY;
    }
}
