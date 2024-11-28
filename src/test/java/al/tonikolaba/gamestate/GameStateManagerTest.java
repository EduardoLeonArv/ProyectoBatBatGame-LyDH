package al.tonikolaba.gamestate;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * Pruebas para la clase GameStateManager.
 *
 * @author N.Kolaba
 */
@RunWith(JUnitPlatform.class)
@DisplayName("Game State Tests")
public class GameStateManagerTest {

	@Test
	@DisplayName("Carga de estados válidos")
	public void testLoadValidStates() {
		// setup
		Player player = new Player(new TileMap(30));
		GameStateManager gm = new GameStateManager(player);

		// run functions
		gm.loadState(0);
		gm.loadState(1);
		gm.loadState(3);
		gm.loadState(4);

		// assert statements
		assertNotNull("El estado 0 debería cargarse", gm.gameStates[0]);
		assertNotNull("El estado 1 debería cargarse", gm.gameStates[1]);
		assertNotNull("El estado 3 debería cargarse", gm.gameStates[3]);
		assertNotNull("El estado 4 debería cargarse", gm.gameStates[4]);
	}

	@Test
	@DisplayName("Carga de estados fuera de los límites")
	public void testLoadOutOfBoundsStates() {
		// setup
		Player player = new Player(new TileMap(30));
		GameStateManager gm = new GameStateManager(player);

		// assert statements para estados fuera de límites
		assertFalse(gm.isStateLoaded(16), "El estado 16 no debería existir");
		assertFalse(gm.isStateLoaded(13), "El estado 13 no debería existir");
	}

}
