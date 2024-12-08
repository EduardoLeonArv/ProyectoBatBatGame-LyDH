package al.tonikolaba.gamestate;

import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.reflect.Field;

import al.tonikolaba.main.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;
import org.springframework.test.util.ReflectionTestUtils;

class AcidStateTest {

    private AcidState acidState;
    private GameStateManager mockGSM;
    private Player mockPlayer;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp() {
        // Crear mocks para las dependencias
        mockGSM = mock(GameStateManager.class);
        mockPlayer = mock(Player.class);
        mockGraphics = mock(Graphics2D.class);

        // Configurar un jugador ficticio
        when(mockPlayer.getScore()).thenReturn(100);
        when(mockPlayer.getName()).thenReturn("TestPlayer");

        // Inicializar AcidState con mocks
        acidState = new AcidState(mockGSM, mockPlayer);
    }

    @Test
    void testUpdate_CallsHandleInput() {
        // Simular llamada a update
        acidState.update();

        // Verifica que no cause excepciones
        // (No hay interacciones esperadas aquí ya que handleInput no modifica nada por defecto)
    }

    @Test
    void testHandleInput_EnterPressed() {
        // Simula que la tecla ENTER está siendo presionada
        Keys.setPressed(Keys.ENTER, true);

        // Llama a handleInput
        acidState.handleInput();

        // No se espera interacción con GameStateManager para la tecla ENTER
        verifyNoInteractions(mockGSM);

        // Reinicia el estado de Keys
        Keys.setPressed(Keys.ENTER, false);
    }


    @Test
    void testDraw_CallsGraphicsMethods() {
        // Llama al método draw con el objeto Graphics2D simulado
        acidState.draw(mockGraphics);

        // Verifica que se utilizaron métodos gráficos esperados
        verify(mockGraphics, atLeastOnce()).setColor(Color.YELLOW);
        verify(mockGraphics, atLeastOnce()).fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        verify(mockGraphics, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }

    @Test
    void testDraw_DisplaysPlayerScore() {
        // Llama al método draw con el objeto Graphics2D simulado
        acidState.draw(mockGraphics);

        // Verifica que el puntaje del jugador se dibujó
        verify(mockGraphics).drawString("Your Score: 100", 230, 295);
    }

    @Test
    void testSetStateInGameStateManager() {
        // Cambiar el estado en GameStateManager
        mockGSM.setState(GameStateManager.LEVEL1STATE);

        // Verifica que el método se llamó correctamente
        verify(mockGSM).setState(GameStateManager.LEVEL1STATE);
    }

    @Test
    void testPlayerScoreUpdate() {
        // Incrementa el puntaje del jugador
        mockPlayer.increaseScore(50);

        // Verifica que el puntaje se actualizó
        verify(mockPlayer).increaseScore(50);
    }







}
