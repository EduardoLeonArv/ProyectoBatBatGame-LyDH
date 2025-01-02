package al.tonikolaba.tilemap;

import java.awt.image.BufferedImage;

/**
 * Clase que representa una baldosa (Tile) en el mapa del juego.
 * Cada baldosa tiene un tipo y una imagen asociada.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 * @version 1.0
 */
public class Tile {

    /** Constante que indica que la baldosa es transitable. */
    public static final int NORMAL = 0;

    /** Constante que indica que la baldosa está bloqueada. */
    public static final int BLOCKED = 1;

    /** Imagen que representa la baldosa. */
    private BufferedImage image;

    /** Tipo de la baldosa (NORMAL o BLOCKED). */
    private int type;

    /**
     * Constructor de la clase Tile.
     *
     * @param image Imagen asociada a la baldosa.
     * @param type Tipo de la baldosa (NORMAL o BLOCKED).
     */
    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.type = type;
    }

    /**
     * Obtiene la imagen de la baldosa.
     *
     * @return Imagen asociada a la baldosa.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Obtiene el tipo de la baldosa.
     *
     * @return Tipo de la baldosa (NORMAL o BLOCKED).
     */
    public int getType() {
        return type;
    }

}
