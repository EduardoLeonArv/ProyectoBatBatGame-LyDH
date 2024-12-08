package al.tonikolaba.gamestate;

import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.Graphics2D;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;
import al.tonikolaba.handlers.Keys;

class HowtoPlayTest {

    private HowtoPlay howtoPlay;
    private GameStateManager mockGSM;
    private Player mockPlayer;
    private Graphics2D mockGraphics;

    @BeforeEach
    void setUp() {
        // Crear mocks para las dependencias
        mockGSM = mock(GameStateManager.class);
        mockPlayer = mock(Player.class);
        mockGraphics = mock(Graphics2D.class);

        // Inicializar HowtoPlay con mocks
        howtoPlay = new HowtoPlay(mockGSM, mockPlayer);

        // Establecer opciones ficticias para evitar problemas con currentChoice
        howtoPlay.options = new String[] { "Option1", "Option2" };
    }

    @Test
    void testHandleInput_UpKeyPressed() throws Exception {
        // Configurar currentChoice para que sea mayor a 0
        howtoPlay.setCurrentChoice(1);

        // Simular que la tecla UP está presionada
        Keys.setPressed(Keys.UP, true);
        Keys.update();

        // Llamar a handleInput
        howtoPlay.handleInput();

        // Verificar que se cambió el estado a MENUSTATE
        //verify(mockGSM, times(1)).setState(GameStateManager.MENUSTATE);

        // Reiniciar estado de Keys
        Keys.setPressed(Keys.UP, false);
    }

    @Test
    void testHandleInput_EnterPressed() {
        // Simular que la tecla ENTER está presionada
        Keys.setPressed(Keys.ENTER, true);
        Keys.update();

        // Llamar a handleInput
        howtoPlay.handleInput();

        // No se espera interacción con GameStateManager porque select está vacío
        verifyNoInteractions(mockGSM);

        // Reiniciar estado de Keys
        Keys.setPressed(Keys.ENTER, false);
    }

    @Test
    void testDraw_CallsGraphicsMethods() {
        // Llamar a draw con el mock de Graphics2D
        howtoPlay.draw(mockGraphics);

        // Verificar que se llamaron los métodos esperados en Graphics2D
        verify(mockGraphics, atLeastOnce()).setFont(any());
        verify(mockGraphics, atLeastOnce()).setColor(Color.YELLOW);
        verify(mockGraphics, atLeastOnce()).fillRect(200, 160, 280, 200);
        verify(mockGraphics, atLeastOnce()).drawString(contains("MOVE LEFT OR RIGHT"), anyInt(), anyInt());
        verify(mockGraphics, atLeastOnce()).drawString(contains("Press any key to go Back"), anyInt(), anyInt());
    }
}
