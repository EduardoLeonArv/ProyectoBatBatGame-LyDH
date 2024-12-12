package al.tonikolaba.handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author N.Kolaba
 *
 */

@RunWith(JUnitPlatform.class)
@DisplayName("Keys")
public class KeysTest {

	@Test
	@SuppressWarnings("unused")
	public void updateTest() {
		Keys keys = new Keys();
		Keys.keySet(1, true);
		Keys.getKeyState();
		Keys.update();
		assertEquals(Keys.prevKeyState[1], false);
	}

	@Test
	@SuppressWarnings("unused")
	public void isPressedTest() {
		Keys keys = new Keys();
		Keys.keySet(1, false);
		Keys.getKeyState();
		Keys.update();
		assertEquals(Keys.isPressed(1), false);
	}






	@Test
	public void testGetKeyState() {
		boolean[] keyState = Keys.getKeyState();
		assertNotNull(keyState);
		assertEquals(Keys.NUM_KEYS, keyState.length);
	}

	@Test
	public void testInvalidKeySet() {
		assertDoesNotThrow(() -> Keys.keySet(-1, true));
	}

}
