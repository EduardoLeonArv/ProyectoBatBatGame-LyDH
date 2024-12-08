package al.tonikolaba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import al.tonikolaba.tilemap.TileMap;

@RunWith(JUnitPlatform.class)
@DisplayName("Player Tests")
public class PlayerTest {

	@Test
	public void testHealthMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial health
		assertEquals(5, player.getHealth());
		assertEquals(5, player.getMaxHealth());

		// Test setHealth
		player.setHealth(3);
		assertEquals(3, player.getHealth());

		// Test hit
		player.hit(2);
		assertEquals(1, player.getHealth());

		// Test health does not go below zero
		player.hit(10);
		assertEquals(0, player.getHealth());

		// Test reset restores health
		player.reset();
		assertEquals(5, player.getHealth());
	}

	@Test
	public void testScoreMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial score
		assertEquals(0, player.getScore());

		// Test setScore
		player.setScore(100);
		assertEquals(100, player.getScore());

		// Test increaseScore
		player.increaseScore(50);
		assertEquals(150, player.getScore());
	}

	@Test
	public void testLifeMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial lives
		assertEquals(3, player.getLives());

		// Test gainLife
		player.gainLife();
		assertEquals(4, player.getLives());

		// Test loseLife
		player.loseLife();
		assertEquals(3, player.getLives());

		// Test setLives
		player.setLives(10);
		assertEquals(10, player.getLives());
	}

	@Test
	public void testTimeMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial time
		assertEquals(0, player.getTime());

		// Test setTime and getTime
		player.setTime(1234);
		assertEquals(1234, player.getTime());

		// Test getTimeToString
		player.setTime(7200); // 2 minutes
		assertEquals("2:00", player.getTimeToString());

		player.setTime(3660); // 1 minute, 6 seconds
		assertEquals("1:01", player.getTimeToString());
	}

	@Test
	public void testMovementAndActions() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial state
		assertFalse(player.isDashing());

		// Test dashing
		player.setDashing(true);
		assertTrue(player.isDashing());
		player.setDashing(false);
		assertFalse(player.isDashing());

		// Test jumping
		player.setJumping(true);
		assertTrue(player.jumping);

		// Test stop
		player.stop();
		assertFalse(player.jumping);
		assertFalse(player.isDashing());
	}

	@Test
	public void testStatesAndAnimations() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test setDead
		player.setDead();
		assertEquals(0, player.getHealth());

		// Test teleporting
		player.setTeleporting(true);
		assertTrue(player.isTeleporting());

		// Test charging
		player.setCharging();
		assertTrue(player.isCharging());
	}

	@Test
	public void testSetAttacking() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);
		assertNotNull(player);

		// Case 1
		player.setAttacking();
		assertTrue(player.attacking);

		// Case 2
		player.stop();
		player.setAttacking();
		assertFalse(player.upattacking);

		// Case 3
		player.hit(4);
		player.setAttacking();
		assertFalse(player.attacking);

		// Case 4
		player.getNextPosition();
		player.setAttacking();
		assertTrue(player.attacking);

		// Case 5
		player.hit(5);
		player.getNextPosition();
		player.setAttacking();
		assertTrue(player.attacking);
	}

	@Test
	public void testGetTimeToString() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);
		assertNotNull(player);

		// Case 1
		player.setTime(7200);
		assertEquals("2:00", player.getTimeToString());

		// Case 2
		player.setTime(0);
		assertEquals("0:00", player.getTimeToString());

		// Case 3
		player.setTime(-7200);
		assertEquals("-2:00", player.getTimeToString());

		// Case 4
		player.setTime(3600 / 10);
		assertEquals("0:06", player.getTimeToString());
	}
}
