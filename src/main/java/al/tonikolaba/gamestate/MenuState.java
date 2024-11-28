/** Copyright to N.Kolaba
All rights reserved ©.
**/
package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.handlers.Keys;

/**
 * @author tonikolaba
 */
public class MenuState extends BasicState {

    private Player player; // Referencia al jugador

    public MenuState(GameStateManager gsm, Player player) {
        super(gsm);
        this.player = player; // Asignar el jugador
        options = new String[] { "Play", "Options", "Quit" };
    }

    @Override
    public void draw(Graphics2D i) {
        super.draw(i);
        // títulos y fuentes
        i.setFont(fontMenu);
        i.setColor(Color.RED);
        i.drawString("Play", 300, 223);
        i.drawString("Options", 300, 248);
        i.drawString("Quit", 300, 273);
    }

    @Override
    protected void select() {
        switch (currentChoice) {
            case 0:
                JukeBox.play("menuselect");
                PlayerSave.init();
                gsm.setState(GameStateManager.LEVEL1STATE); // Pasar el jugador al nuevo estado
                break;
            case 1:
                gsm.setState(GameStateManager.OPTIONSSTATE); // Pasar el jugador al menú de opciones
                break;
            default:
                System.exit(0);
                break;
        }
    }

    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER))
            select();
        if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            JukeBox.play("menuoption", 0);
            currentChoice--;
        }
        if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
            JukeBox.play("menuoption", 0);
            currentChoice++;
        }
    }
}
