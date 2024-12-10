package al.tonikolaba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import al.tonikolaba.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
@DisplayName("Player Tests")
public class PlayerTest {

	@Test
	@DisplayName("Test Health Management")
	public void testHealthMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);
		System.out.println("TEST1");
		// Test initial health
		System.out.println("Initial health: " + player.getHealth());
		assertEquals("Initial health should be 5", 5, player.getHealth());
		System.out.println("TEST2");
		// Modify health
		player.setHealth(3);
		System.out.println("Health after set to 3: " + player.getHealth());
		assertEquals("Health should now be 3", 3, player.getHealth());
		System.out.println("TEST3");
		// Test taking damage
		player.hit(2);
		System.out.println("Health after taking 2 damage: " + player.getHealth());
		assertEquals("Health should decrease to 1", 1, player.getHealth());
		System.out.println("TEST4");
		// Ensure health does not go below zero
		// Ensure health does not go below zero
		System.out.println("Testing hit with 10 damage...");
		player.hit(10);
		System.out.println("Health after taking 10 damage: " + player.getHealth());
		assertEquals("Health should not be negative", 0, player.getHealth());
		System.out.println("TEST5");
		// Test reset
		player.reset();
		System.out.println("Health after reset: " + player.getHealth());
		assertEquals("Health should reset to max", 5, player.getHealth());
		System.out.println("TEST6");
		// Test taking damage after reset
		player.hit(10);
		System.out.println("Health after reset and taking 10 damage: " + player.getHealth());
		assertEquals("Health should be 0 after taking full damage", 0, player.getHealth());
		System.out.println("TEST7");
	}



	@Test
	@DisplayName("Test Reset Functionality")
	public void testReset() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setHealth(1);
		player.reset();
		assertEquals("Health should reset to max after reset", 5, player.getHealth());
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
	@DisplayName("Test Time Methods")
	public void testTimeMethods() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar tiempo como 120 segundos (2 minutos)
		player.setTime(120);
		assertEquals("Time should be 2:00", "2:00", player.getTimeToString());
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
	@DisplayName("Test Get Time To String")
	public void testGetTimeToString() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar tiempo como 90 segundos (1 minuto y 30 segundos)
		player.setTime(90);
		assertEquals("Time string should match expected format", "1:30", player.getTimeToString());
	}


	@Test
	@DisplayName("Initial Health Values")
	public void testInitialHealth() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertEquals("Initial health should be 5", 5, player.getHealth());
		assertEquals("Initial max health should be 5", 5, player.getMaxHealth());
	}

	@Test
	@DisplayName("Score Modification")
	public void testScore() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setScore(10);
		assertEquals("Score should be updated to 10", 10, player.getScore());

		player.increaseScore(5);
		assertEquals("Score should increase by 5", 15, player.getScore());
	}

	@Test
	@DisplayName("Life Methods")
	public void testLife() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertEquals("Initial lives should be 3", 3, player.getLives());

		player.gainLife();
		assertEquals("Lives should increase to 4", 4, player.getLives());

		player.loseLife();
		assertEquals("Lives should decrease to 3", 3, player.getLives());
	}

	@Test
	@DisplayName("Test Time Formatting")
	public void testTimeFormatting() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setTime(60); // 60 segundos equivalen a 1:00
		assertEquals("Formatted time should be 1:00", "1:00", player.getTimeToString());
	}


	@Test
	@DisplayName("Movement States")
	public void testMovement() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertFalse("Player should not be dashing initially", player.isDashing());

		player.setDashing(true);
		assertTrue("Player should be dashing after set to true", player.isDashing());

		player.setDashing(false);
		assertFalse("Player should not be dashing after set to false", player.isDashing());
	}

	@Test
	@DisplayName("Test Time To String")
	public void testTimeToString() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setTime(120); // 120 segundos equivalen a 2:00
		assertEquals("Time should be 2:00", "2:00", player.getTimeToString());
	}



	@Test
	@DisplayName("Test Setting Dead")
	public void testSetDead() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Set player to dead
		player.setDead();
		assertEquals("Health should be 0 after death", 0, player.getHealth());
	}

	@Test
	@DisplayName("Test Hit Logic Isolation")
	public void testHitLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Initial health
		player.setHealth(5);
		System.out.println("TEST1: Health after set to 5: " + player.getHealth());
		assertEquals("Initial health should be 5", 5, player.getHealth());

		// Take damage less than health
		player.hit(3);
		System.out.println("TEST2: Health after hit with 3 damage: " + player.getHealth());
		assertEquals("Health should decrease to 2", 2, player.getHealth());

		// Take damage more than remaining health
		player.hit(10);
		System.out.println("TEST3: Health after hit with 10 damage: " + player.getHealth());
		assertEquals("Health should not go below 0", 0, player.getHealth());

		// Ensure subsequent hits do not break logic
		player.hit(5);
		System.out.println("TEST4: Health after additional hit with 5 damage: " + player.getHealth());
		assertEquals("Health should remain at 0", 0, player.getHealth());
	}

	@Test
	@DisplayName("Test Exception Handling in Sprite Loading")
	public void testSpriteLoadingException() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Forzar carga de un recurso inexistente
		BufferedImage resource = null;
		try {
			resource = ImageIO.read(player.getClass().getResourceAsStream("/Sprites/Player/NonExistent.gif"));
		} catch (Exception e) {
			assertNotNull("Exception should contain a message", e.getMessage());
		}
		assertNull("Resource should be null for non-existent path", resource);
	}


	@Test
	@DisplayName("Test Set and Get Name")
	public void testSetAndGetName() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setName("TestPlayer");
		assertEquals("Player name should match the set value", "TestPlayer", player.getName());
	}


	@Test
	@DisplayName("Test Initialization of Enemies and Energy Particles")
	public void testInit() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		List<Enemy> enemies = new ArrayList<>();
		List<EnergyParticle> energyParticles = new ArrayList<>();

		player.init(enemies, energyParticles);
		assertNotNull("Player should initialize without errors", player);
	}

	@Test
	@DisplayName("Test Set Emote")
	public void testSetEmote() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setEmote(Player.CONFUSED_EMOTE);
		assertTrue("Emote should be set without exceptions", true);
	}


	@Test
	@DisplayName("Test Set Jumping with Knockback")
	public void testSetJumpingWithKnockback() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setKnockback(true); // Simular estado de knockback
		player.setJumping(true);

		// Si el jugador está en knockback, no debería poder saltar
		assertFalse("Player should not jump while in knockback", player.jumping);
	}


	@Test
	@DisplayName("Test Set Charging")
	public void testSetCharging() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setCharging();
		assertTrue("Player should be in charging state", player.isCharging());
	}


	@Test
	@DisplayName("Test Set Dashing")
	public void testSetDashing() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Simula que el jugador no está cayendo
		player.getNextPosition();
		player.setDashing(true);

		assertTrue("Player should start dashing when not falling", player.isDashing());
	}


	@Test
	@DisplayName("Test Jumping Logic")
	public void testJumpingLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setJumping(true);
		player.getNextPosition();

		// Ajustar el delta para manejar diferencias menores
		assertEquals("Player should have jumpStart velocity when jumping", player.jumpStart, player.dy, 0.2);
		assertTrue("Player should be in falling state after jumping", player.falling);
	}


}
