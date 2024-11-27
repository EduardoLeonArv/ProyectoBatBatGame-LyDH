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
public class HowtoPlay extends BasicState {

	private Player player; // Referencia al jugador

	public HowtoPlay(GameStateManager gsm, Player player) {
		super(gsm);
		this.player = player; // Asignar el jugador
	}

	@Override
	public void draw(Graphics2D h) {
		super.draw(h);
		h.setFont(font);
		h.setColor(Color.YELLOW);
		h.fillRect(200, 160, 280, 200); // Fills a square
		h.drawRoundRect(190, 150, 300, 220, 50, 50);
		h.setColor(Color.RED);
		h.drawString("< >      -   MOVE LEFT OR RIGHT", 230, 200);
		h.drawString("W+R   -   JUMP AND HIT ", 230, 220);
		h.drawString("R         -   SINGLE HIT ", 230, 240);
		h.drawString("F         -   BIG HIT ", 230, 260);
		h.drawString("W        -   JUMP UP ", 230, 280);
		h.drawString("ESC   -   PAUSE ", 230, 300);
		h.setFont(font);
		h.drawString(" * Press any key to go Back ", 240, 330);
	}

	@Override
	protected void select() {
	}
	

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER)) {
			select();
		}
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play("menuoption", 0);
			gsm.setState(GameStateManager.MENUSTATE); // Regresar al menú principal con el jugador
			currentChoice--;
		}
	}
}
