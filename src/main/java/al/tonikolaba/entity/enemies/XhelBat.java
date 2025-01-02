package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Content;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Representa al enemigo XhelBat en el juego, un tipo de Flyer que sigue al jugador
 * y cambia su comportamiento dependiendo de su proximidad.
 * Este enemigo tiene una animación y puede detectar el suelo para cambiar de dirección.
 *
 * @author ArtOfSoul
 */
public class XhelBat extends Flyer {

    /** Arreglo de sprites del XhelBat */
    private BufferedImage[] sprites;

    /** Referencia al jugador */
    private Player player;

    /** Indica si el enemigo está activo (si puede moverse y atacar) */
    private boolean active;

    /**
     * Constructor que inicializa el enemigo XhelBat.
     *
     * @param tm El mapa de tiles en el que el enemigo está ubicado
     * @param p El jugador que interactúa con el enemigo
     */
    public XhelBat(TileMap tm, Player p) {
        // Llama al constructor de la clase base Flyer
        super(tm, FlyerType.XHEL_BAT, p);
        this.player = p; // Asigna el jugador a la referencia local

        // Carga los sprites del XhelBat desde el contenido
        sprites = Content.getXhelbat()[0];

        // Establece la animación para el XhelBat
        animation.setFrames(sprites);
        animation.setDelay(4);

        left = true;
        facingRight = false;
    }

    /**
     * Actualiza la posición y el estado del enemigo XhelBat.
     *
     * Si el enemigo no está activo, se activará cuando el jugador se acerque lo suficiente.
     * Después, se actualiza su posición y dirección dependiendo de si hay suelo en los bordes.
     *
     * Si el enemigo está parpadeando (flinch), se cuenta el tiempo de parpadeo.
     * La animación del enemigo también se actualiza.
     */
    @Override
    public void update() {

        if (!active) {
            // Activa el enemigo si el jugador está cerca
            if (Math.abs(player.getx() - x) < GamePanel.WIDTH)
                active = true;
            return;
        }

        // Verifica si el enemigo está parpadeando (flinching)
        if (flinching) {
            flinchCount++;
            if (flinchCount == 6)
                flinching = false;
        }

        // Actualiza la posición y dirección del enemigo
        getNextPosition();
        checkTileMapCollision();
        calculateCorners(x, ydest + 1);

        // Cambia la dirección si no hay suelo en los bordes
        if (!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        if (!bottomRight) {
            left = true;
            right = facingRight = false;
        }
        setPosition(xtemp, ytemp);

        // Cambia de dirección si el movimiento en X se detiene
        if (dx == 0) {
            left = !left;
            right = !right;
            facingRight = !facingRight;
        }

        // Actualiza la animación
        animation.update();
    }

    /**
     * Dibuja el enemigo XhelBat en el gráfico.
     * Si el enemigo está parpadeando, no se dibuja en ciertos momentos.
     *
     * @param g El objeto Graphics2D usado para dibujar
     */
    @Override
    public void draw(Graphics2D g) {

        // Si el enemigo está parpadeando, no dibujarlo en ciertos momentos
        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }

        // Dibuja el enemigo
        super.draw(g);
    }

    /**
     * Verifica si el enemigo está activo.
     *
     * @return true si el enemigo está activo, false en caso contrario
     */
    public boolean isActive() {
        return active;
    }
}