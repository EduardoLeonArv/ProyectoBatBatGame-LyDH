package al.tonikolaba.gamestate;

import al.tonikolaba.gamestate.MenuState;
import al.tonikolaba.gamestate.GameStateManager;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuStateTest {

    private MenuState menuState;
    private GameStateManager mockGsm;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        mockGsm = mock(GameStateManager.class);
        mockPlayer = mock(Player.class);
        menuState = new MenuState(mockGsm, mockPlayer);
    }
    @Test
    void testNavigateMenu() {
        menuState.setCurrentChoice(0);

        // Navegar hacia abajo
        menuState.handleInput();
        Keys.setPressed(Keys.DOWN, true);
        menuState.handleInput();
        assertEquals(1, menuState.getCurrentChoice(), "La opción seleccionada debería ser 1 después de presionar ABAJO.");

        // Navegar hacia arriba
        Keys.setPressed(Keys.UP, true);
        menuState.handleInput();
        assertEquals(0, menuState.getCurrentChoice(), "La opción seleccionada debería ser 0 después de presionar ARRIBA.");
    }

    @Test
    void testNavigationBounds() {
        menuState.setCurrentChoice(0);

        // Intentar navegar más arriba del límite superior
        Keys.setPressed(Keys.UP, true);
        menuState.handleInput();
        assertEquals(0, menuState.getCurrentChoice(), "La opción seleccionada no debería cambiar al intentar navegar más arriba del límite.");

        // Configurar la opción actual al final
        menuState.setCurrentChoice(2);

        // Intentar navegar más abajo del límite inferior
        Keys.setPressed(Keys.DOWN, true);
        menuState.handleInput();
        assertEquals(2, menuState.getCurrentChoice(), "La opción seleccionada no debería cambiar al intentar navegar más abajo del límite.");
    }
    @Test
    void testSelectPlay() {
        menuState.setCurrentChoice(0);
        Keys.setPressed(Keys.ENTER, true);
        menuState.handleInput();

        verify(mockGsm).setState(GameStateManager.LEVEL1STATE);
    }

    @Test
    void testSelectOptions() {
        menuState.setCurrentChoice(1);
        Keys.setPressed(Keys.ENTER, true);
        menuState.handleInput();

        verify(mockGsm).setState(GameStateManager.OPTIONSSTATE);
    }

    @Test
    void testSelectQuit() {
        menuState.setCurrentChoice(2);
        Keys.setPressed(Keys.ENTER, true);

        assertDoesNotThrow(() -> menuState.handleInput(), "El programa debería cerrarse sin lanzar excepciones.");
    }
    @Test
    void testDrawMenu() {
        Graphics2D mockGraphics = mock(Graphics2D.class);

        assertDoesNotThrow(() -> menuState.draw(mockGraphics),
                "El método draw no debería lanzar excepciones.");
    }
    @Test
    void testHandleInputPlayOption() {
        // Configuración inicial
        menuState.setCurrentChoice(0); // Selección inicial: "Play"

        // Simula la tecla ENTER presionada
        Keys.setPressed(Keys.ENTER, true);
        menuState.handleInput();

        // Verifica que se llama a setState con el estado correcto
        verify(mockGsm).setState(GameStateManager.LEVEL1STATE);
    }

}
