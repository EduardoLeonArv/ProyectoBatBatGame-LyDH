package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.handlers.Keys;

/**
 * Clase que representa el estado del menú principal.
 * Permite al jugador navegar por las opciones del menú principal,
 * como jugar, abrir el menú de opciones o salir del juego.
 *
 * @author Laboratorio y Desarrollo de herramientas-GrupoBatBat
 */
public class MenuState extends BasicState {

    /** Texto de la opción para iniciar el juego. */
    private static final String PLAY_OPTION = "Play";
    /** Texto de la opción para abrir el menú de opciones. */
    private static final String OPTIONS_OPTION = "Options";
    /** Texto de la opción para salir del juego. */
    private static final String QUIT_OPTION = "Quit";

    /** Referencia al jugador actual. */
    private Player player;

    /**
     * Constructor para inicializar el estado del menú principal.
     *
     * @param gsm    Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public MenuState(GameStateManager gsm, Player player) {
        super(gsm);
        this.player = player; // Asignar el jugador
        options = new String[] { PLAY_OPTION, OPTIONS_OPTION, QUIT_OPTION };
    }

    /**
     * Dibuja las opciones del menú en la pantalla.
     *
     * @param g Contexto gráfico para renderizar el menú.
     */
    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        // Títulos y fuentes
        g.setFont(fontMenu);
        g.setColor(Color.RED);
        g.drawString(PLAY_OPTION, 300, 223);
        g.drawString(OPTIONS_OPTION, 300, 248);
        g.drawString(QUIT_OPTION, 300, 273);
    }

    /**
     * Maneja la selección de opciones del menú y realiza las acciones correspondientes.
     */
    @Override
    protected void select() {
        JukeBox.play("menuselect");
        switch (currentChoice) {
            case 0:
                startGame();
                break;
            case 1:
                openOptions();
                break;
            case 2:
                exitGame();
                break;
            default:
                break;
        }
    }

    /**
     * Comienza el juego iniciando el estado de nivel 1.
     */
    private void startGame() {
        PlayerSave.init();
        gsm.setState(GameStateManager.LEVEL1STATE); // Pasar el jugador al nuevo estado
    }

    /**
     * Abre el menú de opciones.
     */
    private void openOptions() {
        gsm.setState(GameStateManager.OPTIONSSTATE); // Pasar el jugador al menú de opciones
    }

    /**
     * Finaliza el juego cerrando la aplicación.
     */
    private void exitGame() {
        System.exit(0);
    }

    /**
     * Maneja las entradas del teclado para navegar por el menú.
     * Las teclas permitidas son Enter (para seleccionar),
     * y las flechas hacia arriba y abajo para moverse entre las opciones.
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
     * Navega por las opciones del menú en la dirección especificada.
     *
     * @param direction Dirección de navegación (-1 para arriba, 1 para abajo).
     */
    private void navigateMenu(int direction) {
        JukeBox.play("menuoption", 0);
        currentChoice += direction;
    }
}