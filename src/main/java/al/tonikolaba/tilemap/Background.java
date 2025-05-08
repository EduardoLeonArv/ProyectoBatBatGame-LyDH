package al.tonikolaba.tilemap;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;

/**
 * Clase que representa un fondo desplazable (Background) en el juego.
 * Permite cargar una imagen como fondo, escalarla, posicionarla y actualizar su desplazamiento.
 *
 * @author ArtOfSoul
 * @version 1.0
 */
public class Background {

    /** Imagen que representa el fondo. */
    private BufferedImage image;

    /** Posición X del fondo en píxeles. */
    private double x;
    /** Posición Y del fondo en píxeles. */
    private double y;
    /** Velocidad de desplazamiento en el eje X. */
    private double dx;
    /** Velocidad de desplazamiento en el eje Y. */
    private double dy;

    /** Ancho del fondo en píxeles. */
    private int width;
    /** Altura del fondo en píxeles. */
    private int height;

    /** Factor de escala en el eje X. */
    private double xscale;
    /** Factor de escala en el eje Y. */
    private double yscale;

    /**
     * Constructor que carga una imagen de fondo desde la ruta especificada.
     * Se aplica una escala por defecto de 0.1 en ambos ejes.
     *
     * @param s Ruta de la imagen del fondo.
     */
    public Background(String s) {
        this(s, 0.1);
    }

    /**
     * Constructor que carga una imagen de fondo con una escala uniforme.
     *
     * @param s Ruta de la imagen del fondo.
     * @param d Escala para ambos ejes (X e Y).
     */
    public Background(String s, double d) {
        this(s, d, d);
    }

    /**
     * Constructor que carga una imagen de fondo con escalas separadas para X e Y.
     *
     * @param s Ruta de la imagen del fondo.
     * @param d1 Escala para el eje X.
     * @param d2 Escala para el eje Y.
     */
    public Background(String s, double d1, double d2) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            width = image.getWidth();
            height = image.getHeight();
            xscale = d1;
            yscale = d2;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Constructor que carga una porción de una imagen como fondo.
     *
     * @param s Ruta de la imagen del fondo.
     * @param ms Escala uniforme para ambos ejes.
     * @param x Coordenada X inicial de la subimagen.
     * @param y Coordenada Y inicial de la subimagen.
     * @param w Ancho de la subimagen.
     * @param h Altura de la subimagen.
     */
    public Background(String s, double ms, int x, int y, int w, int h) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(s));
            image = image.getSubimage(x, y, w, h);
            width = image.getWidth();
            height = image.getHeight();
            xscale = ms;
            yscale = ms;
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Establece la posición del fondo en la pantalla.
     * La posición se calcula considerando los factores de escala.
     *
     * @param x Posición en el eje X.
     * @param y Posición en el eje Y.
     */
    public void setPosition(double x, double y) {
        this.x = (x * xscale) % width;
        this.y = (y * yscale) % height;
    }

    /**
     * Establece el vector de desplazamiento del fondo.
     *
     * @param dx Velocidad en el eje X.
     * @param dy Velocidad en el eje Y.
     */
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Establece los factores de escala para el fondo.
     *
     * @param xscale Factor de escala en el eje X.
     * @param yscale Factor de escala en el eje Y.
     */
    public void setScale(double xscale, double yscale) {
        this.xscale = xscale;
        this.yscale = yscale;
    }

    /**
     * Establece las dimensiones del fondo.
     *
     * @param i1 Ancho en píxeles.
     * @param i2 Altura en píxeles.
     */
    public void setDimensions(int i1, int i2) {
        width = i1;
        height = i2;
    }

    /**
     * Obtiene la posición actual del fondo en el eje X.
     *
     * @return Posición X del fondo.
     */
    public double getx() {
        return x;
    }

    /**
     * Obtiene la posición actual del fondo en el eje Y.
     *
     * @return Posición Y del fondo.
     */
    public double gety() {
        return y;
    }

    /**
     * Actualiza la posición del fondo en función de su vector de desplazamiento.
     * La posición se ajusta para mantener un efecto de desplazamiento continuo.
     */
    public void update() {
        x += dx;
        while (x <= -width) x += width;
        while (x >= width) x -= width;
        y += dy;
        while (y <= -height) y += height;
        while (y >= height) y -= height;
    }

    /**
     * Dibuja el fondo en pantalla, ajustando su posición para permitir un efecto
     * de desplazamiento continuo.
     *
     * @param g Objeto Graphics2D utilizado para dibujar la imagen.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(image, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - GamePanel.WIDTH, (int) y, null);
        }
        if (y < 0) {
            g.drawImage(image, (int) x, (int) y + GamePanel.HEIGHT, null);
        }
        if (y > 0) {
            g.drawImage(image, (int) x, (int) y - GamePanel.HEIGHT, null);
        }
    }
}
