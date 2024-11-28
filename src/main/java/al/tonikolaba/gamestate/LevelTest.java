package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;


public class LevelTest extends AbstractLevelState {

    public LevelTest(GameStateManager gsm, Player player) {
        super(gsm, player);
    }

    @Override
    protected int[][] getEnemyCoordinates() {
        return new int[][]{{150, 100}};
    }

    @Override
    protected EnemyType[] getEnemyTypes() {
        return new EnemyType[]{EnemyType.SPIRIT};
    }

    @Override
    protected String getMapPath() {
        return "/Maps/level4.map";
    }

    @Override
    protected String getMusicPath() {
        return "/Music/level1boss.mp3";
    }
}
