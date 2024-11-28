package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

/**
 * Clase que representa el nivel de prueba en el juego.
 * Gestiona enemigos, eventos y el estado del nivel.
 */
public class LevelTest extends GameState {

    public LevelTest(GameStateManager gsm, Player player) {
        super(gsm, player);
        init(GameStateManager.ACIDSTATE);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);
        temple = new Background("/Backgrounds/temple.gif", 0.5, 0);
        generateTileMap("/Maps/level4.map", 140, 0, false);
        setupGameObjects(80, 131, 2850, 120, false);
        setupTitle(new int[]{0, 0, 178, 20}, new int[]{0, 33, 91, 13});
        setupMusic("level4", "/Music/level1boss.mp3", true);
        configureEnemies(
                new EnemyType[]{EnemyType.SPIRIT},
                new int[][]{{150, 100}}
        );
    }

    @Override
    public void update() {
        super.update();
        handleEventQuake(100, new int[]{60, 120, 150, 180, 300});
    }
}
