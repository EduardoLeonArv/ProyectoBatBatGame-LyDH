package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.handlers.Content;
import al.tonikolaba.tilemap.TileMap;
import java.security.SecureRandom;

import java.awt.*;
import java.awt.image.BufferedImage;

import al.tonikolaba.entity.Player;

/**
 * Representa al enemigo Zogu, que se mueve en un patrón oscilante.
 * Este enemigo tiene una animación y puede moverse de manera aleatoria
 * siguiendo una trayectoria basada en funciones seno.
 *
 * @author ArtOfSoul
 */
public class Zogu extends Enemy {

    /** Generador de números aleatorios seguros */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /** Arreglo de sprites para la animación en el estado de inactividad */
    private BufferedImage[] idleSprites;

    /** Contador de ticks para controlar el movimiento oscilante */
    private int tick;

    /** Parámetros para controlar el movimiento oscilante en el eje X */
    private double a;

    /** Parámetros para controlar el movimiento oscilante en el eje Y */
    private double b;

    /** Referencia al jugador */
    private Player player;

    /**
     * Constructor de la clase Zogu, que inicializa las propiedades del enemigo.
     *
     * @param tm El mapa de tiles en el que el enemigo está ubicado
     * @param player El jugador, que es referenciado por el enemigo para sus interacciones
     */
    public Zogu(TileMap tm, Player player) {
        super(tm, player); // Pasa el jugador al constructor de Enemy
        this.player = player;

        // Inicializa las propiedades del enemigo
        health = maxHealth = 2;
        width = 39;
        height = 20;
        cwidth = 25;
        cheight = 15;
        damage = 1;
        moveSpeed = 5;

        // Carga los sprites de inactividad
        idleSprites = Content.getZogu()[0];

        // Configura la animación del enemigo
        animation.setFrames(idleSprites);
        animation.setDelay(4);

        // Inicializa los parámetros del movimiento oscilante
        tick = 0;
        a = SECURE_RANDOM.nextDouble() * 0.06 + 0.07;
        b = SECURE_RANDOM.nextDouble() * 0.06 + 0.07;
    }

    /**
     * Actualiza la posición del enemigo Zogu según su patrón de movimiento oscilante.
     *
     * El movimiento se calcula utilizando funciones seno para alterar las posiciones
     * en los ejes X y Y de manera oscilante. Además, se gestiona la animación y el parpadeo.
     */
    @Override
    public void update() {

        // Verifica si el enemigo está parpadeando (flinching) y actualiza el contador
        if (flinching) {
            flinchCount++;
            if (flinchCount == 6)
                flinching = false;
        }

        // Actualiza la posición en base a un movimiento oscilante
        tick++;
        x = Math.sin(a * tick) + x;
        y = Math.sin(b * tick) + y;

        // Actualiza la animación
        animation.update();
    }

    /**
     * Dibuja al enemigo Zogu en la pantalla.
     *
     * Si el enemigo está parpadeando, no se dibuja en ciertos momentos.
     *
     * @param g El objeto Graphics2D utilizado para dibujar
     */
    @Override
    public void draw(Graphics2D g) {

        // Si el enemigo está parpadeando, no se dibuja en ciertos momentos
        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }

        // Dibuja al enemigo usando la animación
        super.draw(g);
    }
}
