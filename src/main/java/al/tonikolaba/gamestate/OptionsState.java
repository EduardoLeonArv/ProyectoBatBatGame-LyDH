/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.Keys;

/**
 * @author N.Kolaba
 */

/** Copyright to N.Kolaba
 All rights reserved ©.
 **/

import al.tonikolaba.entity.Player;

/**
 * @author N.Kolaba
 */
public class OptionsState extends BasicState {

	private Player player; // Referencia al jugador

	public OptionsState(GameStateManager gsm, Player player) {
		super(gsm);
		this.player = player; // Asignar el jugador
		options = new String[] { "HowTo Play", "Language", "Back" };
	}

	@Override
	public void update() {
		// check keys
		handleInput();
	}

	@Override
	public void draw(Graphics2D i) {
		super.draw(i);
		i.setFont(fontMenu);

		i.setColor(Color.RED);
		i.drawString("HowTo Play", 300, 223);
		i.drawString("Language", 300, 248);
		i.drawString("Back", 300, 273);
	}

	@Override
	protected void select() {
		switch (currentChoice) {
			case 0:
				JukeBox.play("menuselect");
				gsm.setState(GameStateManager.HOWTOPLAY); // Pasar el jugador
				break;
			case 1:
				JukeBox.play("menuselect");
				gsm.setState(GameStateManager.OPTIONSSTATE); // Pasar el jugador si hay subopciones
				break;
			case 2:
				JukeBox.play("menuselect");
				gsm.setState(GameStateManager.MENUSTATE); // Regresar al menú principal con el jugador
				break;
			default:
				gsm.setState(GameStateManager.MENUSTATE);
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
