package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Content;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author ArtOfSoul
 */

/**
 * @author ArtOfSoul
 */
public class XhelBat extends Flyer {

    private BufferedImage[] sprites;
    private Player player; // Referencia al jugador
    private boolean active;

    public XhelBat(TileMap tm, Player p) {
        // Constructor modificado para usar el nuevo constructor de Flyer
        super(tm, FlyerType.XHEL_BAT, p);
        this.player = p; // Asigna el jugador a la referencia local

        sprites = Content.getXhelbat()[0];

        animation.setFrames(sprites);
        animation.setDelay(4);

        left = true;
        facingRight = false;
    }

    @Override
    public void update() {

        if (!active) {
            // Activa el enemigo si el jugador está cerca
            if (Math.abs(player.getx() - x) < GamePanel.WIDTH)
                active = true;
            return;
        }

        // Check if done flinching
        if (flinching) {
            flinchCount++;
            if (flinchCount == 6)
                flinching = false;
        }

        getNextPosition();
        checkTileMapCollision();
        calculateCorners(x, ydest + 1);

        // Cambia de dirección si no hay suelo en los bordes
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

    @Override
    public void draw(Graphics2D g) {

        if (flinching && (flinchCount == 0 || flinchCount == 2)) {
            return;
        }

        super.draw(g);
    }

    public boolean isActive() {
        return active;
    }
}
