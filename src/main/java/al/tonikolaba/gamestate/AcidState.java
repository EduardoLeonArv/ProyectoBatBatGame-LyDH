/** Copyright to N.Kolaba
All rights reserved ©.
**/

package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;

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

 public class AcidState extends BasicState {
    
    // Constructor to initialize AcidState with a GameStateManager and Player.
    public AcidState(GameStateManager gsm, Player player) {
        super(gsm);
        super.player = player; // Use the inherited field from BasicState
    }

    @Override
    public void update() {
        handleInput();
    }

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setFont(fontMenu);
		g.setColor(Color.WHITE);
		g.fillOval(185, 165, 250, 250); // draw a cycle OK
		g.setColor(Color.ORANGE);
		g.fillOval(180, 160, 260, 260); // draw a cycle
		g.drawRect(185, 165, 250, 250); // draw a square
		g.setColor(Color.WHITE);
		g.fillOval(190, 170, 240, 240); // Fills a square
		g.setColor(Color.YELLOW);
		g.fillOval(195, 175, 230, 230);
		g.setColor(Color.RED);
		g.drawString("Congratulation!", 240, 280);
		g.setFont(font);

		// Mostrar la puntuación del jugador si es relevante
		g.drawString("Your Score: " + player.getScore(), 230, 295);

		g.drawString("Press any key to Play Again", 215, 325);
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ENTER))
			select();
		if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
			JukeBox.play("menuoption", 0);
			gsm.setState(GameStateManager.MENUSTATE);
			currentChoice--;
		}
	}
}
