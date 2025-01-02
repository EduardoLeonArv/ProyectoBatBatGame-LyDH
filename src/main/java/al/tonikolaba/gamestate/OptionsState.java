package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;

/**
 * Clase que representa el estado de opciones del menú.
 * Permite al jugador interactuar con submenús como "HowTo Play" y "Language".
 * Proporciona funcionalidades para navegar por las opciones y realizar selecciones.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class OptionsState extends BasicState {

    /** Constante de texto para la opción "HowTo Play". */
    private static final String HOW_TO_PLAY = "HowTo Play";
    /** Constante de texto para la opción "Language". */
    private static final String LANGUAGE = "Language";
    /** Constante de texto para la opción "Back". */
    private static final String BACK = "Back";

    /** Coordenada X para las opciones del menú. */
    private static final int MENU_X = 300;
    /** Coordenada Y para la opción 1. */
    private static final int OPTION_1_Y = 223;
    /** Coordenada Y para la opción 2. */
    private static final int OPTION_2_Y = 248;
    /** Coordenada Y para la opción 3. */
    private static final int OPTION_3_Y = 273;

    /** Referencia al jugador actual. */
    private Player player;

    /**
     * Constructor del estado de opciones.
     *
     * @param gsm    Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public OptionsState(GameStateManager gsm, Player player) {
        super(gsm);
        this.player = player; // Asignar el jugador
        options = new String[] { HOW_TO_PLAY, LANGUAGE, BACK };
    }

    /**
     * Actualiza el estado del menú comprobando la entrada del jugador.
     */
    @Override
    public void update() {
        handleInput();
    }

    /**
     * Dibuja las opciones del menú en la pantalla.
     *
     * @param g Contexto gráfico para renderizar.
     */
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setFont(fontMenu);

        g.setColor(Color.RED);
        g.drawString(HOW_TO_PLAY, MENU_X, OPTION_1_Y);
        g.drawString(LANGUAGE, MENU_X, OPTION_2_Y);
        g.drawString(BACK, MENU_X, OPTION_3_Y);
    }

    /**
     * Maneja la selección de opciones del menú y realiza las acciones correspondientes.
     */
    @Override
    protected void select() {
        JukeBox.play("menuselect");
        switch (currentChoice) {
            case 0:
                // Pasar al estado "HowTo Play"
                gsm.setState(GameStateManager.HOWTOPLAY);
                break;
            case 1:
                // Ir al submenú de opciones
                gsm.setState(GameStateManager.OPTIONSSTATE);
                break;
            case 2:
                // Regresar al menú principal
                gsm.setState(GameStateManager.MENUSTATE);
                break;
            default:
                gsm.setState(GameStateManager.MENUSTATE);
                break;
        }
    }

    /**
     * Controla las entradas del jugador para navegar por el menú.
     * Maneja teclas como ENTER, UP, y DOWN para realizar acciones.
     */
    @Override
    public void handleInput() {
        if (Keys.isPressed(Keys.ENTER)) {
            select();
        } else if (Keys.isPressed(Keys.UP) && currentChoice > 0) {
            navigateMenu(-1);
        } else if (Keys.isPressed(Keys.DOWN) && currentChoice < options.length - 1) {
            navigateMenu(1);
        }
    }

    /**
     * Cambia la opción seleccionada en el menú.
     *
     * @param direction Dirección de la navegación (-1 para arriba, 1 para abajo).
     */
    void navigateMenu(int direction) {
        int newChoice = currentChoice + direction;
        if (newChoice >= 0 && newChoice < options.length) {
            JukeBox.play("menuoption", 0);
            currentChoice = newChoice;
        }
    }
}
