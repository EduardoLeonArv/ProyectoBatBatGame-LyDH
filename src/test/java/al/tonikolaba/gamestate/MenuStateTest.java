package al.tonikolaba.gamestate;

import al.tonikolaba.gamestate.MenuState;
import al.tonikolaba.gamestate.GameStateManager;
import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
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
    public void testConstructorWithValidName() {
        // Simular el comportamiento de JOptionPane para ingresar un nombre válido
        String expectedName = "TestPlayer";
        mockStatic(JOptionPane.class);
        when(JOptionPane.showInputDialog(any(), eq("Enter your player name:"), eq("Welcome to BatBat Game!"), eq(JOptionPane.PLAIN_MESSAGE)))
                .thenReturn(expectedName);

        // Crear instancias simuladas
        Player mockPlayer = new Player(null); // TileMap puede ser null si no es necesario para esta prueba
        GameStateManager mockGsm = mock(GameStateManager.class);

        // Crear instancia de MenuState
        MenuState menuState = new MenuState(mockGsm, mockPlayer);

        // Verificar que el nombre se asignó correctamente
        assertEquals(expectedName, mockPlayer.getName());
    }

    @Test
    public void testConstructorWithEmptyName() {
        // Simular el comportamiento de JOptionPane cuando no se ingresa un nombre
        mockStatic(JOptionPane.class);
        when(JOptionPane.showInputDialog(any(), eq("Enter your player name:"), eq("Welcome to BatBat Game!"), eq(JOptionPane.PLAIN_MESSAGE)))
                .thenReturn("");

        // Crear instancias simuladas
        Player mockPlayer = new Player(null);
        GameStateManager mockGsm = mock(GameStateManager.class);

        // Crear instancia de MenuState
        MenuState menuState = new MenuState(mockGsm, mockPlayer);

        // Verificar que el nombre predeterminado se asignó
        assertEquals("Default Player", mockPlayer.getName());
    }

    @Test
    public void testConstructorWithCancel() {
        // Simular el comportamiento de JOptionPane cuando se presiona "Cancelar"
        mockStatic(JOptionPane.class);
        when(JOptionPane.showInputDialog(any(), eq("Enter your player name:"), eq("Welcome to BatBat Game!"), eq(JOptionPane.PLAIN_MESSAGE)))
                .thenReturn(null);

        // Crear instancias simuladas
        Player mockPlayer = new Player(null);
        GameStateManager mockGsm = mock(GameStateManager.class);

        // Crear instancia de MenuState
        MenuState menuState = new MenuState(mockGsm, mockPlayer);

        // Verificar que el nombre predeterminado se asignó
        assertEquals("Default Player", mockPlayer.getName());
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


}
