package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

public abstract class AbstractLevelState extends GameState {

    public AbstractLevelState(GameStateManager gsm, Player player) {
        super(gsm, player);
    }

    // Configuración genérica para inicializar niveles
    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);
        setupLevelElements();
        configureEnemies(getEnemyTypes(), getEnemyCoordinates());
    }

    // Métodos abstractos específicos para cada nivel
    protected abstract int[][] getEnemyCoordinates();
    protected abstract EnemyType[] getEnemyTypes();
    protected abstract String getMapPath();
    protected abstract String getMusicPath();

    // Método genérico para configurar elementos del nivel
    private void setupLevelElements() {
        temple = new Background("/Backgrounds/temple.gif", 0.5, 0);
        generateTileMap(getMapPath(), 140, 0, false);
        setupGameObjects(300, 131, 2850, 371, false);
        setupMusic("levelMusic", getMusicPath(), true);
    }
}
