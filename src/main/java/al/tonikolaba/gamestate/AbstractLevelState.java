package al.tonikolaba.gamestate;

import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.Background;

/**
 * Clase abstracta que representa el estado de un nivel en el juego.
 * Esta clase define los métodos genéricos para la configuración de los niveles,
 * pero deja los detalles específicos de cada nivel a las clases derivadas.
 *
 * @author ArtOfSoul
 */
public abstract class AbstractLevelState extends GameState {

    /**
     * Constructor de la clase AbstractLevelState.
     *
     * @param gsm El manejador de estados del juego
     * @param player El jugador que interactúa en el nivel
     */
    public AbstractLevelState(GameStateManager gsm, Player player) {
        super(gsm, player);
    }

    /**
     * Inicializa el estado del nivel, configurando los elementos del nivel
     * y los enemigos. Este método se llama cuando se cambia al siguiente nivel.
     *
     * @param nextLevel El siguiente nivel a cargar
     */
    @Override
    public void init(int nextLevel) {
        super.init(nextLevel);
        setupLevelElements();
        configureEnemies(getEnemyTypes(), getEnemyCoordinates());
    }

    /**
     * Método abstracto que obtiene las coordenadas de los enemigos para el nivel.
     * Este método debe ser implementado por las clases derivadas para especificar
     * las posiciones de los enemigos en el mapa.
     *
     * @return Un arreglo bidimensional con las coordenadas de los enemigos
     */
    protected abstract int[][] getEnemyCoordinates();

    /**
     * Método abstracto que obtiene los tipos de enemigos para el nivel.
     * Este método debe ser implementado por las clases derivadas para especificar
     * los tipos de enemigos que aparecerán en el nivel.
     *
     * @return Un arreglo con los tipos de enemigos para el nivel
     */
    protected abstract EnemyType[] getEnemyTypes();

    /**
     * Método abstracto que obtiene la ruta del mapa del nivel.
     * Este método debe ser implementado por las clases derivadas para especificar
     * el archivo del mapa para el nivel.
     *
     * @return La ruta del archivo del mapa
     */
    protected abstract String getMapPath();

    /**
     * Método abstracto que obtiene la ruta de la música para el nivel.
     * Este método debe ser implementado por las clases derivadas para especificar
     * el archivo de música que se reproducirá durante el nivel.
     *
     * @return La ruta del archivo de música
     */
    protected abstract String getMusicPath();

    /**
     * Configura los elementos del nivel, como el fondo, el mapa de tiles,
     * los objetos del juego y la música de fondo.
     */
    private void setupLevelElements() {
        // Configura el fondo del nivel
        temple = new Background("/Backgrounds/temple.gif", 0.5, 0);

        // Genera el mapa de tiles usando la ruta especificada
        generateTileMap(getMapPath(), 140, 0, false);

        // Configura los objetos del juego en el mapa
        setupGameObjects(300, 131, 2850, 371, false);

        // Configura la música del nivel
        setupMusic("levelMusic", getMusicPath(), true);
    }
}
