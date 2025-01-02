package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;

/**
 * Clase que representa un nivel de prueba en el juego.
 * Extiende de {@link AbstractLevelState} e implementa los detalles específicos del nivel,
 * como las coordenadas de los enemigos, tipos de enemigos, mapa y música.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class LevelTest extends AbstractLevelState {

    /**
     * Constructor que inicializa el nivel de prueba con el gestor de estados y el jugador.
     *
     * @param gsm    Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public LevelTest(GameStateManager gsm, Player player) {
        super(gsm, player);
    }

    /**
     * Devuelve las coordenadas de los enemigos en el nivel.
     *
     * @return Una matriz bidimensional de enteros, donde cada par representa
     *         las coordenadas X e Y de un enemigo.
     */
    @Override
    protected int[][] getEnemyCoordinates() {
        return new int[][]{{150, 100}};
    }

    /**
     * Devuelve los tipos de enemigos presentes en el nivel.
     *
     * @return Un arreglo de {@link EnemyType} que representa los tipos de enemigos.
     */
    @Override
    protected EnemyType[] getEnemyTypes() {
        return new EnemyType[]{EnemyType.SPIRIT};
    }

    /**
     * Devuelve la ruta del archivo del mapa del nivel.
     *
     * @return Una cadena de texto que representa la ruta al archivo del mapa.
     */
    @Override
    protected String getMapPath() {
        return "/Maps/level4.map";
    }

    /**
     * Devuelve la ruta del archivo de música para el nivel.
     *
     * @return Una cadena de texto que representa la ruta al archivo de música.
     */
    @Override
    protected String getMusicPath() {
        return "/Music/level1boss.mp3";
    }
}
