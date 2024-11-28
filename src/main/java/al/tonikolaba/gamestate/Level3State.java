/** Copyright to N.Kolaba
 All rights reserved ©.
 **/

package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

/**
 * Clase que representa el nivel 3 del juego.
 * Gestiona los enemigos, eventos y el estado del nivel.
 *
 * @author N.Kolaba
 */
public class Level3State extends GameState {

    private boolean eventQuake;
    private Player player; // Referencia al jugador

    /**
     * Constructor del estado del nivel 3.
     *
     * @param gsm    Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public Level3State(GameStateManager gsm, Player player) {
        super(gsm, player);
        this.player = player;
        init(GameStateManager.LEVEL4STATE);
    }

	/**
	 * Inicializa los elementos del nivel 3.
	 *
	 * @param nextLevel Estado del siguiente nivel.
	 */
	@Override
	public void init(int nextLevel) {
		super.init(nextLevel);
		temple = new Background("/Backgrounds/temple1.gif", 0.5, 0);
		generateTileMap("/Maps/level3.map", 140, 0, false);
		setupGameObjects(300, 131, 2850, 371, false);
		setupTitle(new int[]{0, 0, 178, 20}, new int[]{0, 33, 91, 13});
		setupMusic("level2", "/Music/level1v2.mp3", true);
		configureEnemies(
				new EnemyType[]{EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.UFO},
				new int[][]{{750, 100}, {900, 150}, {1320, 250}}
		);
	}

	/**
	 * Configura los enemigos del nivel.
	 */
	private void configureEnemies() {
		enemyTypesInLevel = new EnemyType[] {
				EnemyType.XHELBAT, EnemyType.ZOGU, EnemyType.UFO
		};

		coords = new int[][] {
				{ 750, 100 }, { 900, 150 }, { 1320, 250 }, { 1570, 160 }, { 1590, 160 },
				{ 2600, 370 }, { 2620, 370 }, { 2640, 370 }, { 904, 130 }, { 1080, 270 },
				{ 1200, 270 }, { 1704, 300 }, { 1900, 580 }, { 2330, 550 }, { 2400, 490 },
				{ 2457, 430 }
		};

		populateEnemies(enemyTypesInLevel, coords);
	}

	/**
	 * Actualiza el estado del nivel.
	 */
	@Override
	public void update() {
		super.update();
		handleEventQuake(2175, new int[]{60, 120, 150, 180, 300});
	}

	/**
	 * Gestiona el evento de terremoto.
	 */
	private void eventQuake() {
		eventCount++;
		switch (eventCount) {
			case 1:
				player.stop();
				player.setPosition(2175, player.gety());
				break;
			case 60:
				player.setEmote(Player.CONFUSED_EMOTE);
				break;
			case 120:
				player.setEmote(Player.NONE_EMOTE);
				break;
			case 150:
				tileMap.setShaking(true, 10);
				break;
			case 180:
				player.setEmote(Player.SURPRISED_EMOTE);
				break;
			case 300:
				player.setEmote(Player.NONE_EMOTE);
				eventQuake = blockInput = false;
				eventCount = 0;
				break;
			default:
				// No action for other counts
				break;
		}
	}
}
