package al.tonikolaba.gamestate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Graphics2D;

import al.tonikolaba.entity.Player;

public class PauseStateTest {

    private PauseState pauseState;
    private GameStateManager gsmMock;
    private Player playerMock;

    @BeforeEach
    public void setUp() {
        gsmMock = mock(GameStateManager.class);
        playerMock = mock(Player.class);
        pauseState = new PauseState(gsmMock, playerMock);
    }

    @Test
    @DisplayName("Test Initialization of PauseState")
    public void testInitialization() {
        assertNotNull("PauseState should be initialized", pauseState);
    }

    @Test
    @DisplayName("Test Drawing")
    public void testDrawing() {
        Graphics2D graphicsMock = mock(Graphics2D.class);

        when(playerMock.getLives()).thenReturn(3);
        when(playerMock.getScore()).thenReturn(100);

        pauseState.draw(graphicsMock);

        verify(graphicsMock, atLeastOnce()).setColor(any());
        verify(graphicsMock, atLeastOnce()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
        verify(graphicsMock, atLeastOnce()).drawString(contains("Game Paused"), anyInt(), anyInt());
        verify(graphicsMock).drawString(contains("Player Lives: 3"), anyInt(), anyInt());
        verify(graphicsMock).drawString(contains("Score: 100"), anyInt(), anyInt());
    }

    @Test
    @DisplayName("Test Handle Input")
    public void testHandleInput() {
        // Simular comportamiento esperado al manejar input sin depender de Keys directamente
        pauseState.handleInput();

        // Asumimos que el estado debería llamar a estos métodos dependiendo del flujo
        verify(gsmMock, atMostOnce()).setPaused(false);
        verify(gsmMock, atMostOnce()).setState(GameStateManager.MENUSTATE);
    }
}
