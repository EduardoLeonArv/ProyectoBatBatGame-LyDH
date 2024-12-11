/** Copyright to N.Kolaba
All rights reserved ©.
**/
package al.tonikolaba.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.handlers.Keys;

/**
 * Clase que representa el estado del menú principal.
 * Permite al jugador navegar por las opciones del menú.
 * 
 * @author tonikolaba
 */
public class MenuState extends BasicState {

    private static final String PLAY_OPTION = "Play";
    private static final String OPTIONS_OPTION = "Options";
    private static final String QUIT_OPTION = "Quit";

    private Player player; // Referencia al jugador

    /**
     * Constructor para inicializar el estado del menú.
     *
     * @param gsm   Gestor de estados del juego.
     * @param player Referencia al jugador.
     */
    public MenuState(GameStateManager gsm, Player player) {
        super(gsm);
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
        // títulos y fuentes
        g.setFont(fontMenu);
        g.setColor(Color.RED);
        g.drawString(PLAY_OPTION, 300, 223);
        g.drawString(OPTIONS_OPTION, 300, 248);
        g.drawString(QUIT_OPTION, 300, 273);
    }

    /**
     * Maneja la selección de opciones del menú.
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
     * Navega por las opciones del menú.
     *
     * @param direction Dirección de navegación (-1 para arriba, 1 para abajo).
     */
    private void navigateMenu(int direction) {
        JukeBox.play("menuoption", 0);
        currentChoice += direction;
    }
}
