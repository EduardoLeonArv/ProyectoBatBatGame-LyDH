package al.tonikolaba.entity.enemies;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Player;
import al.tonikolaba.tilemap.TileMap;

import java.util.ArrayList;

public class UfoTest {

    @Test
    @DisplayName("Test Ufo Initialization")
    public void testUfoInitialization() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);
        ArrayList<Enemy> enemies = new ArrayList<>();

        Ufo ufo = new Ufo(tmMock, playerMock, enemies);

        assertNotNull("Ufo should be initialized", ufo);
    }

    @Test
    @DisplayName("Test Ufo Update Logic")
    public void testUfoUpdateLogic() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);
        when(playerMock.getx()).thenReturn(50);

        ArrayList<Enemy> enemies = new ArrayList<>();
        Ufo ufo = spy(new Ufo(tmMock, playerMock, enemies));

        // Mock methods to prevent logic errors
        doNothing().when(ufo).checkTileMapCollision();
        doNothing().when(ufo).setPosition(anyDouble(), anyDouble());

        ufo.x = 10;
        ufo.dx = 1; // Ensure non-zero dx
        ufo.dy = 1; // Ensure non-zero dy

        ufo.update();

        assertTrue("Ufo should face right when player is to the right", ufo.isFacingRight());
    }

    @Test
    @DisplayName("Test Ufo Attack Logic")
    public void testUfoAttackLogic() {
        TileMap tmMock = mock(TileMap.class);
        Player playerMock = mock(Player.class);
        when(playerMock.getx()).thenReturn(50);

        ArrayList<Enemy> enemies = new ArrayList<>();
        Ufo ufo = spy(new Ufo(tmMock, playerMock, enemies));

        // Mock methods to prevent logic errors
        doNothing().when(ufo).checkTileMapCollision();
        doNothing().when(ufo).setPosition(anyDouble(), anyDouble());

        try {
            java.lang.reflect.Field stepField = Ufo.class.getDeclaredField("step");
            stepField.setAccessible(true);
            stepField.set(ufo, 2);
        } catch (Exception e) {
            fail("Failed to modify private field 'step': " + e.getMessage());
        }

        ufo.dx = 1; // Ensure non-zero dx
        ufo.dy = 1; // Ensure non-zero dy

        ufo.update();

        assertEquals("An enemy should be added during attack", 1, enemies.size());
    }
}
