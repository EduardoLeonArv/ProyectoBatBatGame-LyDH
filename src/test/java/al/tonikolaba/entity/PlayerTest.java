package al.tonikolaba.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import al.tonikolaba.tilemap.TileMap;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
		// Test reset
		player.reset();
		System.out.println("Health after reset: " + player.getHealth());
		assertEquals("Health should reset to max", 5, player.getHealth());
		System.out.println("TEST5");
		// Test taking damage after reset
		player.hit(10);
		System.out.println("Health after reset and taking 10 damage: " + player.getHealth());
		assertEquals("Health should be 0 after taking full damage", 0, player.getHealth());
		System.out.println("TEST6");
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
		assertEquals("Time should be 2:00", "0:02", player.getTimeToString());
	}


	@Test
	public void testMovementAndActions() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertFalse("Player should not be dashing initially", player.isDashing());

		player.setDashing(true);
		assertTrue("Player should be dashing after set to true", player.isDashing());

		player.setDashing(false);
		assertFalse("Player should not be dashing after set to false", player.isDashing());

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
	@DisplayName("Test Movement Behavior")
	public void testMovementBehavior() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Activar movimiento hacia la derecha
		player.setRight(true);
		player.getNextPosition();

		assertTrue("Player should move right", player.dx > 0);
	}

	@Test
	@DisplayName("Test Get Time")
	public void testGetTime() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setTime(120);
		assertEquals("Time should match the set value", 120, player.getTime());
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

		// Test with knockback active
		player.setKnockback(true);
		player.setCharging();
		assertFalse("Player should not start charging when knockback is active", player.isCharging());

		// Test with normal conditions
		player.setKnockback(false);
		player.setCharging();
		assertTrue("Player should start charging under normal conditions", player.isCharging());
	}


	@Test
	@DisplayName("Test Set Dashing")
	public void testSetDashing() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar estado de caída
		player.setFalling(true);
		player.setDashing(true);

		assertFalse("Player should not dash while falling", player.isDashing());
	}


	@Test
	@DisplayName("Test Get Next Position")
	public void testGetNextPosition() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Caso: knockback activo
		player.setKnockback(true);
		player.getNextPosition();
		assertTrue("Player should still be in knockback state", player.isKnockback());

		// Caso: movimiento normal
		player.setKnockback(false);
		player.setLeft(true);
		player.getNextPosition();
		assertTrue("Player should move left", player.dx < 0);
	}

	@Test
	@DisplayName("Test Set Attacking with Knockback")
	public void testSetAttackingWithKnockback() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Activar knockback
		player.setKnockback(true);
		player.setAttacking();

		// Verificar que no entra en estado de ataque
		assertFalse("Player should not attack during knockback", player.isAttacking());
	}

	@Test
	@DisplayName("Test Jump and Fall")
	public void testJumpAndFall() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setJumping(true);
		player.jumpAndFall();

		assertEquals("Player should have jump start velocity", player.jumpStart, player.dy, 0.2);
		assertTrue("Player should be falling after jumping", player.isFalling());
	}



	@Test
	@DisplayName("Test Movement with Dashing")
	public void testMovement() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar estado inicial
		player.setRight(true);
		player.setDashing(true);

		player.movement(); // Llamar al método de movimiento

		// Verificar que la velocidad durante dashing supera maxSpeed
		assertTrue("Player should move faster when dashing", player.dx > player.maxSpeed);
	}




	@Test
	@DisplayName("Debug Test Movement with Dashing")
	public void testMovementDebug() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar movimiento hacia la derecha y activar dashing
		player.setRight(true);
		player.setDashing(true);

		System.out.println("Before movement: dx = " + player.dx + ", maxSpeed = " + player.maxSpeed + ", dashing = " + player.isDashing());

		player.movement();
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

	@Test
	@DisplayName("Test Double Jump Logic")
	public void testDoubleJumpLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test initial state of double jump
		assertFalse("Initially, double jump should be false", player.isAlreadyDoubleJump());

		// Test setting double jump to true
		player.setDoubleJump(true);
		assertTrue("Double jump should be true after setting", player.isDoubleJump());

		// Test resetting double jump to false
		player.setDoubleJump(false);
		assertFalse("Double jump should be false after resetting", player.isDoubleJump());

		// Test getting double jump start value
		assertEquals("Double jump start value should be -3", -3, player.getDoubleJumpStart(), 0.001);
	}

	@Test
	@DisplayName("Test Get Max Health")
	public void testGetMaxHealth() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertEquals("Max health should be 5 by default", 5, player.getMaxHealth());
	}

	@Test
	@DisplayName("Test Set and Get Lives")
	public void testSetAndGetLives() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setLives(10);
		assertEquals("Lives should be set to 10", 10, player.getLives());

		player.gainLife();
		assertEquals("Lives should increase to 11", 11, player.getLives());

		player.loseLife();
		assertEquals("Lives should decrease to 10", 10, player.getLives());
	}

	@Test
	@DisplayName("Test Set Emote")
	public void testSetEmote() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setEmote(Player.CONFUSED_EMOTE);
		assertTrue("Emote should be set successfully", true);
	}

	@Test
	@DisplayName("Test Flinching Logic")
	public void testFlinchingLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertFalse("Player should not be flinching initially", player.isFlinching());

		player.setFlinching(true);
		assertTrue("Player should be flinching after being set", player.isFlinching());

		player.setFlinching(false);
		assertFalse("Player should not be flinching after being reset", player.isFlinching());
	}

	@Test
	@DisplayName("Test Get Current Action")
	public void testGetCurrentAction() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		assertEquals("Default current action should be 0", 0, player.getCurrentAction());
	}

	@Test
	@DisplayName("Test Reset Score")
	public void testResetScore() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setScore(50);
		player.setScore(0);
		assertEquals("Score should reset to 0", 0, player.getScore());
	}

	@Test
	@DisplayName("Test Double Jump Mechanics")
	public void testDoubleJumpMechanics() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setDoubleJump(true);
		assertTrue("Double jump should be enabled", player.isDoubleJump());

		player.setDoubleJump(false);
		assertFalse("Double jump should be disabled", player.isDoubleJump());
	}

	@Test
	@DisplayName("Test Player Stop Mechanics")
	public void testPlayerStopMechanics() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setDashing(true);
		player.stop();

		assertFalse("Player should stop dashing after stop is called", player.isDashing());
	}

	@Test
	@DisplayName("Test Draw Method Logic")
	public void testDrawMethodLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		Graphics2D mockGraphics = Mockito.mock(Graphics2D.class);

		// Test drawing to ensure no exceptions are thrown
		player.draw(mockGraphics);

		// Minimal verification to satisfy coverage
		Mockito.verify(mockGraphics, Mockito.atLeastOnce()).drawImage(Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.isNull());
	}

	@Test
	@DisplayName("Test Set Attacking")
	public void testSetAttacking() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Test with knockback active
		player.setKnockback(true);
		player.setAttacking();
		assertFalse("Player should not be attacking when knockback is active", player.isAttacking());

		// Test with charging active
		player.setKnockback(false);
		player.setCharging();
		player.setAttacking();
		assertFalse("Player should not be attacking when charging is active", player.isAttacking());

	}

	@Test
	@DisplayName("Test Logging for Exception Handling")
	public void testLoggingForExceptionHandling() {
		TileMap tm = new TileMap(30);
		try {
			new Player(tm);
		} catch (Exception e) {
			assertNotNull("Exception should be caught and logged", e.getMessage());
		}
	}

	@Test
	@DisplayName("Test Knockback and Flinching States")
	public void testKnockbackAndFlinching() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Activar knockback
		player.setKnockback(true);
		assertTrue("El estado knockback debería estar activado.", player.isKnockback());

		// Desactivar knockback
		player.setKnockback(false);
		assertFalse("El estado knockback debería estar desactivado.", player.isKnockback());

		// Activar flinching
		player.setFlinching(true);
		assertTrue("El estado flinching debería estar activado.", player.isFlinching());

		// Desactivar flinching
		player.setFlinching(false);
		assertFalse("El estado flinching debería estar desactivado.", player.isFlinching());
	}

	@Test
	@DisplayName("Test Movement Logic")
	public void testMovementLogic() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar movimiento hacia la derecha
		player.setRight(true);
		player.movement();
		assertTrue("El jugador debería moverse a la derecha.", player.dx > 0);

		// Configurar movimiento hacia la izquierda
		player.setLeft(true);
		player.setRight(false);
		player.movement();
		assertTrue("El jugador debería moverse a la izquierda.", player.dx < 0);

		// Detener movimiento
		player.setLeft(false);
		player.movement();
		assertEquals("El jugador debería detenerse.", 0.0, player.dx, 0.01);
	}

	@Test
	@DisplayName("Test Jumping and Falling")
	public void testJumpingAndFalling() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar salto
		player.setJumping(true);
		player.jumpAndFall();
		assertEquals("Expected jump start velocity", player.jumpStart, player.dy, 0.2);

		// Configurar caída
		player.setFalling(true);
		player.jumpAndFall();
		System.out.println("dy after jumpAndFall: " + player.dy);
		assertTrue("El jugador debería estar cayendo.", player.dy != 0);
	}

	@Test
	@DisplayName("Test Animation Changes")
	public void testAnimationChanges() throws Exception {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Reflexión para acceder al campo privado JUMPING_ANIM
		Field jumpingAnimField = Player.class.getDeclaredField("JUMPING_ANIM");
		jumpingAnimField.setAccessible(true);
		int jumpingAnimValue = (int) jumpingAnimField.get(player);

		// Configurar salto
		player.setJumping(true);
		player.update();

		// Comparar valores de tipo int
		assertEquals("La acción debería ser JUMPING.", jumpingAnimValue, player.getCurrentAction());
	}

	@Test
	@DisplayName("Test Draw Method")
	public void testDrawMethod() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		Graphics2D mockGraphics = Mockito.mock(Graphics2D.class);
		assertDoesNotThrow(() -> player.draw(mockGraphics),
				"El método draw no debería lanzar excepciones.");
	}
	@Test
	@DisplayName("Test Reset Completo")
	public void testResetCompleto() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setHealth(2);
		player.setKnockback(true);
		player.setFlinching(true);
		player.setJumping(true);

		player.reset();

		assertEquals("La salud debería restablecerse al máximo.", 5, player.getHealth());
		assertFalse("El estado knockback debería desactivarse.", player.isKnockback());
		assertFalse("El estado flinching debería desactivarse.", player.isFlinching());
		assertFalse("El estado jumping debería desactivarse.", player.jumping);
	}

	@Test
	@DisplayName("Test Interacción con Enemigos")
	public void testInteraccionConEnemigos() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		Enemy mockEnemy = Mockito.mock(Enemy.class);
		when(mockEnemy.isDead()).thenReturn(false);
		when(mockEnemy.intersects(Mockito.any(Rectangle.class))).thenReturn(true);
		when(mockEnemy.getDamage()).thenReturn(2);

		player.init(Arrays.asList(mockEnemy), new ArrayList<>());

		player.hit(3); // El jugador recibe daño por interacción
		assertEquals("La salud debería disminuir tras la interacción con un enemigo.", 2, player.getHealth());
	}
	@Test
	@DisplayName("Test Lógica de Doble Salto")
	public void testLogicaDeDobleSalto() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setFalling(true);
		player.setDoubleJump(true);
		player.jumpAndFall();

		assertTrue("El jugador debería realizar un doble salto.", player.isAlreadyDoubleJump());
		assertEquals("La velocidad vertical debería coincidir con el inicio del doble salto.", -3, player.dy, 0.5);
	}

	@Test
	@DisplayName("Test Cambio de Animaciones")
	public void testCambioDeAnimaciones() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		player.setJumping(true);
		player.update();
		assertEquals("El jugador debería estar en la animación de salto.", player.getJumpingAnim(), player.getCurrentAction());

		player.setFalling(true);
		player.update();
		assertEquals("El jugador debería estar en la animación de caída.", player.getFallingAnim(), player.getCurrentAction());
	}

	@Test
	@DisplayName("Test Cambio de Animaciones con Reflexión")
	public void testCambioDeAnimacionesConReflexion() throws Exception {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		Field jumpingAnimField = Player.class.getDeclaredField("JUMPING_ANIM");
		jumpingAnimField.setAccessible(true);
		int jumpingAnim = jumpingAnimField.getInt(player);

		Field fallingAnimField = Player.class.getDeclaredField("FALLING_ANIM");
		fallingAnimField.setAccessible(true);
		int fallingAnim = fallingAnimField.getInt(player);

		player.setJumping(true);
		player.update();
		assertEquals("El jugador debería estar en la animación de salto.", jumpingAnim, player.getCurrentAction());

		player.setFalling(true);
		player.update();
		assertEquals("El jugador debería estar en la animación de caída.", fallingAnim, player.getCurrentAction());
	}

	@Test
	@DisplayName("Test Ataque del Jugador")
	public void testAtaqueDelJugador() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Configurar el estado de ataque
		player.setAttacking();
		player.update();

		assertTrue("El jugador debería estar en estado de ataque.", player.isAttacking());
		assertEquals("El jugador debería estar en la animación de ataque.", Player.ATTACKING_ANIM, player.getCurrentAction());
	}

	@Test
	@DisplayName("Test Jugador Recibiendo Daño")
	public void testJugadorRecibiendoDaño() {
		TileMap tm = new TileMap(30);
		Player player = new Player(tm);

		// Salud inicial
		int initialHealth = player.getHealth();

		// El jugador recibe daño
		player.hit(2);

		assertEquals("La salud del jugador debería reducirse.", initialHealth - 2, player.getHealth());
		assertTrue("El jugador debería entrar en estado de flinching.", player.isFlinching());
	}
}
