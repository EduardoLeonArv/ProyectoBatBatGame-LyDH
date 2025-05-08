package al.tonikolaba.audio;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import al.tonikolaba.handlers.LoggingHelper;

/**
 * Clase para gestionar el audio del juego. Proporciona funciones para cargar,
 * reproducir, detener, reanudar, y manipular clips de audio.
 */
public class JukeBox {

    /**
     * Mapa que almacena los clips de audio cargados, indexados por nombre.
     */
    private static final Map<String, Clip> clips = new HashMap<>();

    /**
     * Retardo predeterminado (gap) utilizado para reproducir clips desde un marco específico.
     */
    private static int gap;

    /**
     * Bandera que indica si el audio está silenciado.
     */
    private static boolean mute = false;

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private JukeBox() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Carga un archivo de audio y lo asocia con un nombre en el mapa de clips.
     *
     * @param s la ruta del archivo de audio.
     * @param n el nombre con el que se asociará el clip.
     */
    public static void load(String s, String n) {
        Clip clip;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(JukeBox.class.getResourceAsStream(s));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            clips.put(n, clip);
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Reproduce un clip de audio desde el principio.
     *
     * @param s el nombre del clip a reproducir.
     */
    public static void play(String s) {
        play(s, gap);
    }

    /**
     * Reproduce un clip de audio desde una posición específica.
     *
     * @param s el nombre del clip a reproducir.
     * @param i el marco desde el cual iniciar la reproducción.
     */
    public static void play(String s, int i) {
        if (mute)
            return;
        Clip c = clips.get(s);
        if (c == null)
            return;
        if (c.isRunning())
            c.stop();
        c.setFramePosition(i);
        while (!c.isRunning())
            c.start();
    }

    /**
     * Detiene la reproducción de un clip de audio.
     *
     * @param s el nombre del clip a detener.
     */
    public static void stop(String s) {
        if (clips.get(s) == null)
            return;
        if (clips.get(s).isRunning())
            clips.get(s).stop();
    }

    /**
     * Reanuda la reproducción de un clip de audio desde su posición actual.
     *
     * @param s el nombre del clip a reanudar.
     */
    public static void resume(String s) {
        if (mute)
            return;
        if (clips.get(s).isRunning())
            return;
        clips.get(s).start();
    }

    /**
     * Reproduce un clip de audio en bucle desde el principio hasta el final.
     *
     * @param s el nombre del clip a reproducir en bucle.
     */
    public static void loop(String s) {
        loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * Reproduce un clip de audio en bucle desde un marco específico hasta el final.
     *
     * @param s el nombre del clip a reproducir en bucle.
     * @param frame el marco desde el cual iniciar la reproducción.
     */
    public static void loop(String s, int frame) {
        loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
    }

    /**
     * Reproduce un clip de audio en bucle entre dos marcos específicos.
     *
     * @param s el nombre del clip a reproducir en bucle.
     * @param start el marco de inicio del bucle.
     * @param end el marco final del bucle.
     */
    public static void loop(String s, int start, int end) {
        loop(s, gap, start, end);
    }

    /**
     * Reproduce un clip de audio en bucle desde un marco específico, limitado entre dos marcos.
     *
     * @param s el nombre del clip a reproducir en bucle.
     * @param frame el marco desde el cual iniciar la reproducción.
     * @param start el marco de inicio del bucle.
     * @param end el marco final del bucle.
     */
    public static void loop(String s, int frame, int start, int end) {
        stop(s);
        if (mute)
            return;
        clips.get(s).setLoopPoints(start, end);
        clips.get(s).setFramePosition(frame);
        clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Establece la posición de reproducción de un clip de audio.
     *
     * @param s el nombre del clip.
     * @param frame el marco al que mover la posición de reproducción.
     */
    public static void setPosition(String s, int frame) {
        clips.get(s).setFramePosition(frame);
    }

    /**
     * Devuelve la cantidad total de marcos de un clip de audio.
     *
     * @param s el nombre del clip.
     * @return la cantidad total de marcos.
     */
    public static int getFrames(String s) {
        return clips.get(s).getFrameLength();
    }

    /**
     * Devuelve la posición actual de reproducción de un clip de audio.
     *
     * @param s el nombre del clip.
     * @return la posición actual en marcos.
     */
    public static int getPosition(String s) {
        return clips.get(s).getFramePosition();
    }

    /**
     * Cierra y libera los recursos asociados a un clip de audio.
     *
     * @param s el nombre del clip.
     */
    public static void close(String s) {
        stop(s);
        clips.get(s).close();
    }

    /**
     * Devuelve el mapa de clips cargados.
     *
     * @return el mapa de clips.
     */
    public static Map<String, Clip> getClips() {
        return clips;
    }

    /**
     * Metodo para inicializar.
     */
    public static void init() {
    }
}
