package al.tonikolaba.entity;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Esta clase representa un teletransportador en el juego, que tiene una animación de teletransportación.
 * El teletransportador muestra una serie de imágenes (sprites) que se actualizan con el tiempo.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class Teleport extends MapObject {

    // Arreglo que contiene los sprites para la animación del teletransportador
    private BufferedImage[] sprites;

    /**
     * Constructor que inicializa el teletransportador, establece las dimensiones y carga los sprites.
     *
     * @param tm El mapa de tiles en el que el teletransportador está ubicado
     */
    public Teleport(TileMap tm) {
        super(tm);

        // Establece que el teletransportador está mirando hacia la derecha
        facingRight = true;

        // Establece las dimensiones del teletransportador
        width = height = 40;   // El teletransportador tiene un tamaño de 40x40 píxeles
        cwidth = 20;            // Ancho de la caja de colisión
        cheight = 40;           // Alto de la caja de colisión

        try {
            // Carga la hoja de sprites del teletransportador
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Teleport.gif"));

            // Inicializa el arreglo de sprites con las imágenes correspondientes
            sprites = new BufferedImage[9];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

            // Configura la animación con los sprites cargados
            animation.setFrames(sprites);
            animation.setDelay(1);  // Establece el retraso de la animación a 1 para una actualización rápida

        } catch (Exception e) {
            // Si ocurre un error al cargar los sprites, lo registra en el log
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Actualiza la animación del teletransportador. Este método se llama en cada ciclo de actualización.
     */
    public void update() {
        // Actualiza la animación del teletransportador
        animation.update();
    }
}
