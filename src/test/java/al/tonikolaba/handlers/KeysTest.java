package al.tonikolaba.handlers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

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
	public void testKeySet() {
		Keys.setPressed(Keys.UP, true);
		assertTrue(Keys.isPressed(Keys.UP));

		Keys.setPressed(Keys.UP, false);
		assertFalse(Keys.isPressed(Keys.UP));
	}

	@Test
	public void testUpdate() {
		Keys.setPressed(Keys.RIGHT, true);
		Keys.update();
		assertTrue(Keys.prevKeyState[Keys.RIGHT]);
		assertFalse(Keys.isPressed(Keys.RIGHT));
	}

	@Test
	public void testAnyKeyPress() {
		Keys.setPressed(Keys.LEFT, true);
		assertTrue(Keys.anyKeyPress());

		Keys.setPressed(Keys.LEFT, false);
		assertFalse(Keys.anyKeyPress());
	}

	@Test
	public void testGetKeyState() {
		boolean[] keyState = Keys.getKeyState();
		assertNotNull(keyState);
		assertEquals(Keys.NUM_KEYS, keyState.length);
	}
}
