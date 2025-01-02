package al.tonikolaba.entity;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Esta clase representa un título animado que se desplaza desde el lado izquierdo de la pantalla.
 * El título se muestra durante el inicio del juego o en una pantalla de transición.
 * Se anima deslizándose hasta llegar al centro de la pantalla, luego se mueve hacia la derecha
 * antes de ser eliminado.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class Title {

    /** Imagen que representa el título */
    private BufferedImage image;

    /** Contador para medir el tiempo que el título ha estado en pantalla */
    private int count;

    /** Indica si el título ha completado su animación */
    boolean done;

    /** Indica si el título debe ser removido de la pantalla */
    private boolean remove;

    /** Posición del título en el eje X */
    double x;

    /** Posición del título en el eje Y */
    private double y;

    /** Velocidad de desplazamiento en el eje X */
    private double dx;

    /** Ancho de la imagen del título */
    private int width;

    /**
     * Constructor que inicializa el título con una imagen cargada desde un archivo.
     *
     * @param s La ruta del archivo de imagen que representa el título
     */
    public Title(String s) {

        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            width = image.getWidth();
            x = -width;
            done = false;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    /**
     * Constructor que inicializa el título con una imagen ya proporcionada.
     *
     * @param image La imagen que representa el título
     */
    public Title(BufferedImage image) {
        this.image = image;
        width = image.getWidth();
        x = -width;
        done = false;
    }

    /**
     * Reinicia el estado del título para su reutilización, restableciendo la posición y el estado de la animación.
     */
    public void reset() {
        done = false;
        remove = false;
        x = -width;
    }

    /**
     * Establece la posición Y del título en la pantalla.
     *
     * @param y La posición Y del título
     */
    public void sety(double y) {
        this.y = y;
    }

    /**
     * Inicia la animación del título, estableciendo su velocidad de desplazamiento.
     */
    public void begin() {
        dx = 10;
    }

    /**
     * Verifica si el título debe ser removido de la pantalla.
     *
     * @return true si el título debe ser removido, false en caso contrario
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     * Actualiza la posición del título y maneja su animación.
     * El título se mueve hasta llegar al centro de la pantalla, luego continúa moviéndose
     * hacia la derecha antes de ser marcado para ser removido.
     */
    public void update() {
        if (!done) {
            if (x >= (GamePanel.WIDTH - width) / 2.0) {
                x = (GamePanel.WIDTH - width) / 2.0;
                count++;
                if (count >= 120)
                    done = true;
            } else {
                x += dx;  /** Mueve el título hacia la derecha */
            }
        } else {

            x += dx;
            if (x > GamePanel.WIDTH)
                remove = true;
        }
    }

    /**
     * Dibuja el título en la pantalla.
     *
     * @param g El objeto Graphics2D que se utiliza para dibujar el título
     */
    public void draw(Graphics2D g) {
        if (!remove)
            g.drawImage(image, (int) x, (int) y, null);
    }

}
