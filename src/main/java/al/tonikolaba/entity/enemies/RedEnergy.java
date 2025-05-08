package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;

import java.awt.image.BufferedImage;

/**
 * Esta clase representa un enemigo de tipo "RedEnergy", que tiene diferentes tipos de movimiento
 * y comportamientos (vectorial, gravitacional, rebote). Los enemigos de este tipo tienen una animación
 * inicial y una animación de movimiento.
 *
 * @author ArtOfSoul
 */
public class RedEnergy extends Enemy {

    /** Tipo de movimiento: VECTOR, GRAVITY, BOUNCE */
    public static final int VECTOR = 0;
    /** Tipo de movimiento: GRAVITY */
    public static final int GRAVITY = 1;
    /** Tipo de movimiento: BOUNCE */
    public static final int BOUNCE = 2;

    /** Sprites para la animación de inicio */
    private BufferedImage[] startSprites;

    /** Sprites para la animación de movimiento */
    private BufferedImage[] sprites;

    /** Indica si la animación ha comenzado */
    private boolean start;

    /** Indica si el objeto es permanente (no se elimina) */
    private boolean permanent;

    /** Tipo de movimiento actual del enemigo */
    private int type = 0;

    /** Cuenta el número de rebotes para el tipo BOUNCE */
    private int bounceCount = 0;

    /**
     * Constructor que inicializa un objeto RedEnergy con sus propiedades y sprites.
     *
     * @param tm El mapa de tiles en el que el enemigo está ubicado
     * @param player El jugador que interactuará con el enemigo
     */
    public RedEnergy(TileMap tm, Player player) {
        super(tm, player);  // Llama al constructor de la clase base Enemy

        health = maxHealth = 1;  // Establece la salud máxima

        width = 20;  // Ancho del enemigo
        height = 20; // Altura del enemigo
        cwidth = 12; // Ancho de la colisión
        cheight = 12; // Altura de la colisión

        damage = 1;  // Daño del enemigo
        moveSpeed = 5;  // Velocidad de movimiento

        // Obtiene los sprites de la animación
        startSprites = Content.getRedEnergy()[0];
        sprites = Content.getRedEnergy()[1];

        animation.setFrames(startSprites);  // Establece los frames iniciales
        animation.setDelay(2);  // Establece el retraso de la animación

        start = true;  // La animación comienza
        flinching = true;  // Indica si el enemigo parpadea cuando es golpeado
        permanent = false;  // El enemigo no es permanente por defecto
    }

    /**
     * Establece el tipo de movimiento del enemigo.
     *
     * @param i El tipo de movimiento (VECTOR, GRAVITY, BOUNCE)
     */
    public void setType(int i) {
        type = i;
    }

    /**
     * Establece si el enemigo es permanente (no se elimina automáticamente).
     *
     * @param b Si el valor es true, el enemigo será permanente; si es false, se eliminará
     */
    public void setPermanent(boolean b) {
        permanent = b;
    }

    /**
     * Actualiza la posición del enemigo, maneja la animación y la eliminación
     * según su tipo de movimiento y su estado.
     */
    @Override
    public void update() {

        // Cambia los frames de animación una vez que la animación inicial haya terminado
        if (start && animation.hasPlayedOnce()) {
            animation.setFrames(sprites);
            animation.setNumFrames(3);
            animation.setDelay(2);
            start = false;
        }

        // Actualiza la posición según el tipo de movimiento
        if (type == VECTOR) {
            x += dx;
            y += dy;
        } else if (type == GRAVITY) {
            dy += 0.2;  // Aplica la gravedad
            x += dx;
            y += dy;
        } else if (type == BOUNCE) {
            double dx2 = dx;
            double dy2 = dy;
            checkTileMapCollision();  // Verifica las colisiones en el mapa de tiles
            if (dx == 0) {
                dx = -dx2;  // Rebote horizontal
                bounceCount++;  // Incrementa el contador de rebotes
            }
            if (dy == 0) {
                dy = -dy2;  // Rebote vertical
                bounceCount++;  // Incrementa el contador de rebotes
            }
            x += dx;
            y += dy;
        }

        // Actualiza la animación
        animation.update();

        // Elimina el enemigo si está fuera de la pantalla o ha rebotado 3 veces
        if (!permanent) {
            if (x < 0 || x > tileMap.getWidth() || y < 0 || y > tileMap.getHeight()) {
                remove = true;
            }
            if (bounceCount == 3) {
                remove = true;  // El enemigo se elimina después de 3 rebotes
            }
        }
    }
}
