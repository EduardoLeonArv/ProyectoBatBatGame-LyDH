/**
 * Clase que representa el nivel de prueba en el juego.
 * Gestiona enemigos, eventos y el estado del nivel.
 *
 * @author N.Kolaba
 */
package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.tilemap.Background;

public class LevelTest extends GameState {

    private static final String LEVEL_BOSS_MUSIC_NAME = "level1boss";
    private static final int[][] ENEMY_COORDINATES = {{150, 100}};
    private static final EnemyType[] ENEMY_TYPES = {EnemyType.SPIRIT};

    private boolean eventQuake;

    /**
     * Constructor del estado de nivel de prueba.
     *
     * @param gsm    Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public LevelTest(GameStateManager gsm, Player player) {
        super(gsm, player);
        init(GameStateManager.ACIDSTATE);
    }

    /**
     * Inicializa los elementos del nivel.
     *
     * @param nextLevel Estado del siguiente nivel.
     */
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

    /**
     * Configura los enemigos del nivel.
     */
    private void configureEnemies() {
        populateEnemies(ENEMY_TYPES, ENEMY_COORDINATES);
    }

    /**
     * Actualiza el estado del nivel.
     */
    @Override
    public void update() {
        super.update();
        handleEventQuake(100, new int[]{60, 120, 150, 180, 300});
    }

    /**
     * Gestiona el evento de terremoto.
     */
    private void eventQuake() {
        eventCount++;
        switch (eventCount) {
            case 1:
                player.stop();
                player.setPosition(120, player.gety());
                setPlayerEmote(Player.CONFUSED_EMOTE, 0);
                tileMap.setShaking(true, 10);
                break;
            case 60:
                setPlayerEmote(Player.CONFUSED_EMOTE, 0);
                break;
            case 120:
                setPlayerEmote(Player.NONE_EMOTE, 0);
                break;
            case 150:
                tileMap.setShaking(true, 10);
                break;
            case 180:
                setPlayerEmote(Player.SURPRISED_EMOTE, 0);
                break;
            case 300:
                setPlayerEmote(Player.NONE_EMOTE, 0);
                eventQuake = blockInput = false;
                eventCount = 0;
                break;
            default:
                break;
        }
    }

    /**
     * Configura el emote del jugador y ajusta el contador del evento si es necesario.
     *
     * @param emote Emote a asignar al jugador.
     * @param delay Tiempo adicional para el evento.
     */
    private void setPlayerEmote(int emote, int delay) {
        if (isValidEmote(emote)) {
            player.setEmote(emote);
            if (delay > 0) {
                eventCount += delay;
            }
        }
    }

    /**
     * Valida si el emote es válido para el jugador.
     *
     * @param emote Emote a validar.
     * @return true si es válido, false en caso contrario.
     */
    private boolean isValidEmote(int emote) {
        return emote == Player.CONFUSED_EMOTE || emote == Player.NONE_EMOTE || emote == Player.SURPRISED_EMOTE;
    }
}
