/**
 * @author N.Kolaba
 */
package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

public class LevelTest extends GameState {

    private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
    private boolean eventQuake;

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

        enemyTypesInLevel = new EnemyType[]{EnemyType.SPIRIT};
        coords = new int[][]{{150, 100}};

        populateEnemies(enemyTypesInLevel, coords);
    }

    @Override
    public void update() {
        super.update();

        if (player.getx() > 100 && !tileMap.isShaking()) {
            eventQuake = blockInput = true;
            eventCount++;
        }

        if (eventQuake) eventQuake();
    }

    private void eventQuake() {
        eventCount++;
        if (eventCount == 1) {
            player.stop();
            player.setPosition(120, player.gety());
            setPlayerEmote(Player.CONFUSED_EMOTE, 0);
            tileMap.setShaking(true, 10);
        } else if (eventCount == 60) {
            setPlayerEmote(Player.CONFUSED_EMOTE, 0);
        } else if (eventCount == 120) {
            setPlayerEmote(Player.NONE_EMOTE, 0);
        } else if (eventCount == 150) {
            tileMap.setShaking(true, 10);
        } else if (eventCount == 180) {
            setPlayerEmote(Player.SURPRISED_EMOTE, 0);
        } else if (eventCount == 300) {
            setPlayerEmote(Player.NONE_EMOTE, 0);
            eventQuake = blockInput = false;
            eventCount = 0;
        }
    }

    private void setPlayerEmote(int emote, int delay) {
        player.setEmote(emote);
        if (delay > 0) {
            eventCount += delay;
        }
    }
}
