package al.tonikolaba.gamestate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import al.tonikolaba.handlers.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;

import java.awt.*;
import static org.junit.Assert.assertEquals;

public class OptionsStateTest {

    private OptionsState optionsState;
    private GameStateManager gsmMock;

    @BeforeEach
    public void setUp() {
        gsmMock = mock(GameStateManager.class);
        optionsState = new OptionsState(gsmMock, mock(Player.class));
    }

    @Test
    @DisplayName("Test Initialization of OptionsState")
    public void testInitialization() {
        assertNotNull("OptionsState should be initialized", optionsState);
    }

    @Test
    @DisplayName("Test Current Choice Setter and Getter")
    public void testCurrentChoiceSetterGetter() {
        optionsState.setCurrentChoice(1);
        assertEquals("Current choice should be 1", 1, optionsState.getCurrentChoice());
    }

    @Test
    @DisplayName("Test Selection")
    public void testSelection() {
        optionsState.setCurrentChoice(0);
        optionsState.select();
        verify(gsmMock).setState(GameStateManager.HOWTOPLAY);

        optionsState.setCurrentChoice(1);
        optionsState.select();
        verify(gsmMock).setState(GameStateManager.OPTIONSSTATE);

        optionsState.setCurrentChoice(2);
        optionsState.select();
        verify(gsmMock).setState(GameStateManager.MENUSTATE);
    }

    @Test
    @DisplayName("Test Menu Draw")
    void testMenuDraw() {
        Graphics2D mockGraphics = mock(Graphics2D.class);

        optionsState.draw(mockGraphics);

        verify(mockGraphics, times(1)).drawString("HowTo Play", 300, 223);
        verify(mockGraphics, times(1)).drawString("Language", 300, 248);
        verify(mockGraphics, times(1)).drawString("Back", 300, 273);
    }


    @Test
    @DisplayName("Test Navigate Menu")
    void testNavigateMenu() {
        optionsState.setCurrentChoice(0);

        // Navegar hacia abajo
        optionsState.navigateMenu(1);
        assertEquals("La opción seleccionada debería ser 1.", 1, optionsState.getCurrentChoice());

        // Navegar hacia arriba
        optionsState.navigateMenu(-1);
        assertEquals("La opción seleccionada debería regresar a 0.", 0, optionsState.getCurrentChoice());
    }

    @Test
    @DisplayName("Test Navigation Bounds")
    public void testNavigationBounds() {
        // Configuración inicial
        optionsState.setCurrentChoice(0);

        // Navegar hacia abajo desde la primera opción
        optionsState.navigateMenu(1);
        int expectedChoice = 1; // La opción debería ser 1
        assertEquals("La opción seleccionada debería cambiar a 1.", expectedChoice, optionsState.getCurrentChoice());

        // Configuración inicial al final del menú
        optionsState.setCurrentChoice(2); // Suponiendo que hay 3 opciones (0, 1, 2)
        optionsState.navigateMenu(1); // Navegar hacia abajo desde la última opción
        expectedChoice = 2; // La opción debería permanecer en 2
        assertEquals("La opción seleccionada debería permanecer en el límite superior.", expectedChoice, optionsState.getCurrentChoice());

        // Navegar hacia arriba desde la última opción
        optionsState.navigateMenu(-1);
        expectedChoice = 1; // Debería moverse a la opción anterior
        assertEquals("La opción seleccionada debería cambiar a 1.", expectedChoice, optionsState.getCurrentChoice());
    }

}
