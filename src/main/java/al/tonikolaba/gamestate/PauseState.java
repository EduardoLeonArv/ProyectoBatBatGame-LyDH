package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.handlers.Keys;
import al.tonikolaba.main.GamePanel;

/**
 * @author N. Kolaba
 */

import al.tonikolaba.entity.Player;

/**
 * @author N. Kolaba
 */

public class PauseState extends GameState {

	private Player player; // Referencia al jugador

	public PauseState(GameStateManager gsm, Player player) {
		super(gsm, player); // Llama al constructor de GameState con el jugador
		this.player = player;
	}

	@Override
	public void update() {
		handleInput();
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRoundRect(180, 130, 300, 220, 50, 50);
		g.setColor(Color.YELLOW);
		g.fillRect(190, 140, 280, 200); // Fills a square
		g.setColor(Color.RED);
		g.setFont(fontMenu);
		g.drawString("Game Paused", 280, 230);
		g.setFont(font);

		// Mostrar información del jugador (opcional)
		g.drawString("* press ESC to continue", 250, 255);
		g.drawString("Player Lives: " + player.getLives(), 250, 275);
		g.drawString("Score: " + player.getScore(), 250, 295);
	}

	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE))
			gsm.setPaused(false);
		if (Keys.isPressed(Keys.BUTTON1)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
}

