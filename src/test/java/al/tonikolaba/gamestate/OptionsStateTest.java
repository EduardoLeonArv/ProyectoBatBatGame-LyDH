package al.tonikolaba.gamestate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Player;

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
}
