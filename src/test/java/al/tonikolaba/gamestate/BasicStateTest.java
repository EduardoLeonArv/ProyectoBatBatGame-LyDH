package al.tonikolaba.gamestate;

import al.tonikolaba.gamestate.BasicState;
import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

class BasicStateTest {

    @Test
    void testHandleInput() {
        BasicState state = new BasicStateExtension(null);
        state.setCurrentChoice(1);
        state.handleInput();
        assertEquals(0, state.getCurrentChoice(), "El handleInput debería actualizar la elección.");
    }

    static class BasicStateExtension extends BasicState {
        public BasicStateExtension(GameStateManager gsm) {
            super(gsm);
        }

        @Override
        protected void select() {
            // Implementación vacía para pruebas
        }
    }
}
