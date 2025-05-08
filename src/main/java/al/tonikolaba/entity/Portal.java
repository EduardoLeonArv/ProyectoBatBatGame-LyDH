package al.tonikolaba.entity;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Esta clase representa un portal en el juego, que puede estar cerrado, abriéndose o abierto.
 * El portal tiene animaciones para cada estado y permite cambiar entre ellos.
 *
 * @author ArtOfSoul
 */
public class Portal extends MapObject {


    /** Sprites del portal cerrado. */
    private BufferedImage[] closedSprites;
    /** Sprites para la animación de apertura del portal. */
    private BufferedImage[] openingSprites;
    /** Sprites del portal abierto. */
    private BufferedImage[] openedSprites;

    /** Estado del portal: abierto o abriéndose */
    private boolean opened;
    /** Indica si el portal está abriéndose */
    private boolean opening;

    /**
     * Constructor que inicializa el portal con su animación y carga los sprites.
     *
     * @param tm El mapa de tiles en el que el portal está ubicado
     */
    public Portal(TileMap tm) {

        super(tm);

        width = 81;  // Ancho del portal
        height = 111;  // Altura del portal

        try {
            // Carga la hoja de sprites para el portal
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Other/Portal.gif"));

            // Inicializa los sprites para el portal cerrado
            closedSprites = new BufferedImage[1];
            closedSprites[0] = spritesheet.getSubimage(0, 0, width, height);

            // Inicializa los sprites para la animación de apertura
            openingSprites = new BufferedImage[6];
            for (int i = 0; i < openingSprites.length; i++) {
                openingSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
            }

            // Inicializa los sprites para el portal abierto
            openedSprites = new BufferedImage[3];
            for (int i = 0; i < openedSprites.length; i++) {
                openedSprites[i] = spritesheet.getSubimage(i * width, 2 * height, width, height);
            }

            // Establece el portal como cerrado por defecto
            animation.setFrames(closedSprites);
            animation.setDelay(-1);

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    /**
     * Establece el portal en el estado cerrado, usando la animación correspondiente.
     */
    public void setClosed() {
        animation.setFrames(closedSprites);
        animation.setDelay(-1);
        opened = false;
    }

    /**
     * Establece el portal en el estado de apertura, usando la animación de apertura.
     */
    public void setOpening() {
        opening = true;
        animation.setFrames(openingSprites);
        animation.setDelay(2);
    }

    /**
     * Establece el portal en el estado abierto, usando la animación de portal abierto.
     */
    public void setOpened() {
        if (opened)
            return;
        animation.setFrames(openedSprites);
        animation.setDelay(2);
        opened = true;
    }

    /**
     * Verifica si el portal está abierto.
     *
     * @return true si el portal está abierto, false en caso contrario
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * Actualiza la animación del portal. Si el portal está abriéndose y la animación se ha completado,
     * lo establece en estado abierto.
     */
    public void update() {
        animation.update();
        if (opening && animation.hasPlayedOnce()) {
            opened = true;
            animation.setFrames(openedSprites);
            animation.setDelay(2);
        }
    }
}
