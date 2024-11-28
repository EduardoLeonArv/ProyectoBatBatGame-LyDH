package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

public abstract class AbstractLevelState extends GameState {

    public AbstractLevelState(GameStateManager gsm, Player player) {
        super(gsm, player);
    }

    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);
        configureLevel();
    }

    protected abstract int[][] getEnemyCoordinates();
    protected abstract EnemyType[] getEnemyTypes();
    protected abstract String getMapPath();
    protected abstract String getMusicPath();
    protected abstract int getNextLevelState();

    private void configureLevel() {
        // Configuración genérica para niveles
        temple = new Background("/Backgrounds/temple.gif", 0.5, 0);
        generateTileMap(getMapPath(), 140, 0, false);
        setupGameObjects(300, 131, 2850, 371, false);
        setupMusic("levelMusic", getMusicPath(), true);
        configureEnemies(getEnemyTypes(), getEnemyCoordinates());
    }
}
