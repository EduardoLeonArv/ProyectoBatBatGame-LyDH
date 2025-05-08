package al.tonikolaba.entity;

import java.awt.image.BufferedImage;

/**
 * Clase Animation que maneja las animaciones de sprites mediante un conjunto de imágenes.
 * Controla la transición entre cuadros, el retraso, y la cantidad de veces que se ha reproducido la animación.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 * @version 1.0
 */
public class Animation {

    /** Arreglo de cuadros (imágenes) que forman la animación. */
    private BufferedImage[] frames;
    /** Índice del cuadro actual en la animación. */
    private int currentFrame;
    /** Número total de cuadros disponibles en la animación. */
    private int numFrames;

    /** Contador utilizado para controlar el retraso entre cuadros. */
    private int count;
    /** Tiempo de retraso (en ciclos de actualización) entre cuadros. */
    private int delay;

    /** Número de veces que se ha reproducido completamente la animación. */
    private int timesPlayed;

    /**
     * Constructor de la clase Animation.
     * Inicializa la variable {@code timesPlayed} en 0.
     */
    public Animation() {
        timesPlayed = 0;
    }

    /**
     * Establece los cuadros (imágenes) que conforman la animación.
     *
     * @param frames un arreglo de imágenes {@link BufferedImage} para la animación.
     */
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2; // Valor por defecto del retraso entre cuadros
        numFrames = frames.length;
    }

    /**
     * Establece el retraso entre cuadros.
     *
     * @param i cantidad de ciclos de actualización a esperar entre cuadros.
     */
    public void setDelay(int i) {
        delay = i;
    }

    /**
     * Establece el número de cuadros a reproducir de la animación.
     *
     * @param i cantidad de cuadros de la animación.
     */
    public void setNumFrames(int i) {
        numFrames = i;
    }

    /**
     * Actualiza el estado de la animación, controlando el cambio de cuadros
     * según el retraso establecido.
     */
    public void update() {
        if (delay == -1) return; // Si el retraso es -1, no se actualiza la animación.

        count++;

        if (count == delay) {
            currentFrame++; // Avanza al siguiente cuadro.
            count = 0; // Reinicia el contador.
        }
        if (currentFrame == numFrames) {
            currentFrame = 0; // Reinicia la animación al primer cuadro.
            timesPlayed++; // Incrementa el contador de reproducciones completas.
        }
    }

    /**
     * Devuelve el índice del cuadro actual en la animación.
     *
     * @return índice del cuadro actual.
     */
    public int getFrame() {
        return currentFrame;
    }

    /**
     * Establece manualmente el cuadro actual de la animación.
     *
     * @param i índice del cuadro que se desea establecer como actual.
     */
    public void setFrame(int i) {
        currentFrame = i;
    }

    /**
     * Devuelve el contador actual del retraso entre cuadros.
     *
     * @return el valor actual del contador de retraso.
     */
    public int getCount() {
        return count;
    }

    /**
     * Devuelve la imagen correspondiente al cuadro actual de la animación.
     *
     * @return la imagen del cuadro actual.
     */
    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    /**
     * Verifica si la animación se ha reproducido al menos una vez.
     *
     * @return {@code true} si la animación se ha reproducido completamente al menos una vez, de lo contrario {@code false}.
     */
    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

    /**
     * Verifica si la animación se ha reproducido exactamente un número específico de veces.
     *
     * @param i número de veces que se desea comprobar si la animación se ha reproducido.
     * @return {@code true} si la animación se ha reproducido exactamente {@code i} veces, de lo contrario {@code false}.
     */
    public boolean hasPlayed(int i) {
        return timesPlayed == i;
    }
}
