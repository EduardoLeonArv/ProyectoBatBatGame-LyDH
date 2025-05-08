package al.tonikolaba.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;
import java.util.logging.Logger;

/**
 * @author ArtOfSoul
 */

/**
 * Clase que representa un jugador en el juego, extendiendo la clase MapObject.
 * Contiene atributos y métodos relacionados con las acciones, estado y animaciones del jugador.
 */
public class Player extends MapObject {

	/**
	 * Atributo para almacenar el nombre del jugador
	 */
	private String name;

	/**
	 * Constante para representar una emoción de ningún tipo
	 */
	public static final int NONE_EMOTE = 0;

	/**
	 * Constante para representar una emoción de confundido
	 */
	public static final int CONFUSED_EMOTE = 1;

	/**
	 * Constante para representar una emoción de sorprendido
	 */
	public static final int SURPRISED_EMOTE = 2;

	/**
	 * Arreglo de enteros que define el número de fotogramas por cada animación
	 */
	private static final int[] NUMFRAMES = {1, 8, 5, 3, 3, 5, 3, 8, 2, 1, 3};

	/**
	 * Arreglo de enteros que define el ancho de los fotogramas de cada animación
	 */
	private static final int[] FRAMEWIDTHS = {40, 40, 80, 40, 40, 40, 80, 40, 40, 40, 40};

	/**
	 * Arreglo de enteros que define la altura de los fotogramas de cada animación
	 */
	private static final int[] FRAMEHEIGHTS = {40, 40, 40, 40, 40, 80, 40, 40, 40, 40, 40};

	/**
	 * Arreglo de enteros que define los retrasos de cada animación
	 */
	private static final int[] SPRITEDELAYS = {-1, 3, 2, 6, 5, 2, 2, 2, 1, -1, 1};

	/**
	 * Acción de animación para el estado de inactividad
	 */
	private static final int IDLE_ANIM = 0;

	/**
	 * Acción de animación para el estado de caminata
	 */
	private static final int WALKING_ANIM = 1;

	/**
	 * Acción de animación para el estado de ataque
	 */
	public static final int ATTACKING_ANIM = 2;

	/**
	 * Acción de animación para el estado de salto
	 */
	private static final int JUMPING_ANIM = 3;

	/**
	 * Acción de animación para el estado de caída
	 */
	private static final int FALLING_ANIM = 4;

	/**
	 * Acción de animación para el estado de ataque hacia arriba
	 */
	private static final int UPATTACKING_ANIM = 5;

	/**
	 * Acción de animación para el estado de carga
	 */
	private static final int CHARGING_ANIM = 6;

	/**
	 * Acción de animación para el estado de dash (desplazamiento rápido)
	 */
	private static final int DASHING_ANIM = 7;

	/**
	 * Acción de animación para el estado de retroceso por golpe (knockback)
	 */
	private static final int KNOCKBACK_ANIM = 8;

	/**
	 * Acción de animación para el estado de muerte
	 */
	private static final int DEAD_ANIM = 9;

	/**
	 * Acción de animación para el estado de teletransportación
	 */
	private static final int TELEPORTING_ANIM = 10;

	/**
	 * Nombre del archivo de música para el salto del jugador
	 */
	public static final String PLAYERJUMP_MUSICNAME = "playerjump";

	/**
	 * Nombre del archivo de música para el ataque del jugador
	 */
	public static final String PLAYERATTACKMUSICNAME = "playerattack";

	/**
	 * Lista que almacena los enemigos del jugador
	 */
	private ArrayList<Enemy> enemies;

	/**
	 * Número de vidas del jugador
	 */
	private int lives;

	/**
	 * Cantidad de salud actual del jugador
	 */
	private int health;

	/**
	 * Cantidad máxima de salud del jugador
	 */
	private int maxHealth;

	/**
	 * Daño que el jugador inflige con ataques normales
	 */
	private int damage;

	/**
	 * Daño que el jugador inflige cuando está cargando un ataque
	 */
	private int chargeDamage;

	/**
	 * Indicador de si el jugador está siendo afectado por retroceso (knockback)
	 */
	private boolean knockback;

	/**
	 * Indicador de si el jugador está en un estado de aturdimiento (flinching)
	 */
	private boolean flinching;

	/**
	 * Tiempo restante de aturdimiento (flinch)
	 */
	private long flinchCount;

	/**
	 * Puntuación del jugador
	 */
	private int score;

	/**
	 * Indicador de si el jugador puede realizar un segundo salto (double jump)
	 */
	private boolean doubleJump;

	/**
	 * Indicador de si el jugador ya ha realizado el segundo salto
	 */
	private boolean alreadyDoubleJump;

	/**
	 * Momento en que el jugador comenzó el segundo salto
	 */
	private double doubleJumpStart;

	/**
	 * Lista de partículas de energía asociadas al jugador
	 */
	private ArrayList<EnergyParticle> energyParticles;

	/**
	 * Tiempo actual en el que el jugador se encuentra
	 */
	private long time;

	/**
	 * Indicador de si el jugador está realizando un dash (desplazamiento rápido)
	 */
	private boolean dashing;

	/**
	 * Indicador de si el jugador está atacando
	 */
	protected boolean attacking;

	/**
	 * Indicador de si el jugador está atacando hacia arriba
	 */
	protected boolean upattacking;

	/**
	 * Indicador de si el jugador está cargando un ataque
	 */
	private boolean charging;

	/**
	 * Contador de ticks mientras el jugador está cargando un ataque
	 */
	private int chargingTick;

	/**
	 * Indicador de si el jugador está teletransportándose
	 */
	private boolean teleporting;

	/**
	 * Lista de sprites (imágenes) del jugador para las animaciones
	 */
	private ArrayList<BufferedImage[]> sprites;

	/**
	 * Rectángulo de colisión del jugador
	 */
	private Rectangle ar;

	/**
	 * Rectángulo adicional de colisión para la cabeza del jugador
	 */
	private Rectangle aur;

	/**
	 * Rectángulo de colisión para los ataques del jugador
	 */
	private Rectangle cr;

	/**
	 * Imagen para la emoción de confundido
	 */
	private BufferedImage confused;

	/**
	 * Imagen para la emoción de sorprendido
	 */
	private BufferedImage surprised;

	/**
	 * Variable que almacena la emoción actual del jugador
	 */
	private int emote = NONE_EMOTE;

	/**
	 * Constructor de la clase Player.
	 * Este constructor inicializa un nuevo objeto Player, configurando su mapa de tiles
	 * y realizando la configuración inicial del jugador en el juego.
	 *
	 * @param tm El mapa de tiles (TileMap) que representa el entorno donde el jugador se mueve.
	 */
	public Player(TileMap tm) {


		super(tm);
		score = 0;
		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 30;
		ar.height = 20;
		aur = new Rectangle((int) x - 15, (int) y - 45, 30, 30);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;

		width = 30;
		height = 30;
		cwidth = 15;
		cheight = 38;

		moveSpeed = 1.6;
		maxSpeed = 1.6;
		stopSpeed = 1.6;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -3;

		damage = 2;
		chargeDamage = 1;

		facingRight = true;

		lives = 3;
		health = maxHealth = 5;

		// load sprites
		try {

			BufferedImage spritesheet = ImageIO
					.read(getClass().getResourceAsStream("/Sprites/Player/BatterySpirtes.gif"));

			int count = 0;
			sprites = new ArrayList<>();
			for (int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for (int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * FRAMEWIDTHS[i],
							count, FRAMEWIDTHS[i], FRAMEHEIGHTS[i]);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}

			// emotes
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/HUD/Emotes.gif"));
			confused = spritesheet.getSubimage(0, 0, 14, 17);
			surprised = spritesheet.getSubimage(14, 0, 14, 17);

		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}

		energyParticles = new ArrayList<>();

		setAnimation(IDLE_ANIM);

		JukeBox.load("/SFX/playerlands.mp3", "playerlands");
		JukeBox.load("/SFX/playerattack.mp3", PLAYERATTACKMUSICNAME);
		JukeBox.load("/SFX/playerhit.mp3", "playerhit");
		JukeBox.load("/SFX/playercharge.mp3", "playercharge");

	}

	/**
	 * Establece el nombre del jugador.
	 *
	 * @param name El nombre que se asignará al jugador.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el nombre del jugador.
	 *
	 * @return El nombre del jugador.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Inicializa el jugador con las listas de enemigos y partículas de energía.
	 *
	 * @param enemies         Lista de enemigos presentes en el juego.
	 * @param energyParticles Lista de partículas de energía asociadas al jugador.
	 */
	public void init(List<Enemy> enemies, List<EnergyParticle> energyParticles) {
		this.enemies = (ArrayList<Enemy>) enemies;
		this.energyParticles = (ArrayList<EnergyParticle>) energyParticles;
	}

	/**
	 * Obtiene la salud actual del jugador.
	 *
	 * @return La salud actual del jugador.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Establece la salud del jugador.
	 *
	 * @param i El valor de salud que se asignará al jugador.
	 */
	public void setHealth(int i) {
		health = i;
	}

	/**
	 * Obtiene la salud máxima del jugador.
	 *
	 * @return La salud máxima del jugador.
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Establece la emoción actual del jugador.
	 *
	 * @param i El valor que representa la emoción que se asignará al jugador.
	 */
	public void setEmote(int i) {
		emote = i;
	}

	/**
	 * Establece si el jugador está teletransportándose.
	 *
	 * @param b Valor booleano que indica si el jugador está teletransportándose.
	 */
	public void setTeleporting(boolean b) {
		teleporting = b;
	}

	/**
	 * Establece si el jugador está saltando. Si el jugador está en retroceso (knockback), no podrá saltar.
	 * También permite realizar un doble salto si el jugador no lo ha hecho ya.
	 *
	 * @param b Valor booleano que indica si el jugador está saltando.
	 */
	@Override
	public void setJumping(boolean b) {
		if (knockback)
			return;
		if (b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}

	/**
	 * Establece si el jugador está atacando. Si el jugador está en retroceso o cargando un ataque,
	 * no podrá iniciar un ataque.
	 */
	public void setAttacking() {
		if (knockback)
			return;
		if (charging)
			return;
		if (up && !attacking)
			upattacking = true;
		else
			attacking = true;
	}

	/**
	 * Establece si el jugador está cargando un ataque. Si el jugador está en retroceso o ya está atacando,
	 * no podrá iniciar una carga.
	 */
	public void setCharging() {
		if (knockback)
			return;
		if (!attacking && !upattacking && !charging) {
			charging = true;
			JukeBox.play("playercharge");
			chargingTick = 0;
		}
	}

	/**
	 * Devuelve si el jugador está en el estado de dash (desplazamiento rápido).
	 *
	 * @return True si el jugador está en el estado de dash, false en caso contrario.
	 */
	public boolean isDashing() {
		return dashing;
	}

	/**
	 * Establece el estado de dash del jugador. Si el jugador no está cayendo, puede comenzar el dash.
	 *
	 * @param b Valor booleano que indica si el jugador está en el estado de dash.
	 */
	public void setDashing(boolean b) {
		if (!b)
			dashing = false;
		else if (!falling) {
			dashing = true;
		}
	}

	/**
	 * Establece que el jugador está muerto. La salud del jugador se establece a 0 y se detienen sus acciones.
	 */
	public void setDead() {
		health = 0;
		stop();
	}

	/**
	 * Convierte el tiempo en segundos a un formato de minutos:segundos (mm:ss).
	 *
	 * @return El tiempo en formato de cadena "mm:ss".
	 */
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}

	/**
	 * Obtiene el tiempo actual en segundos del jugador.
	 *
	 * @return El tiempo actual en segundos.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Establece el tiempo del jugador.
	 *
	 * @param t El tiempo en segundos que se asignará al jugador.
	 */
	public void setTime(long t) {
		time = t;
	}

	/**
	 * Incrementa las vidas del jugador en 1.
	 */
	public void gainLife() {
		lives++;
	}

	/**
	 * Decrementa las vidas del jugador en 1.
	 */
	public void loseLife() {
		lives--;
	}

	/**
	 * Obtiene el número de vidas actuales del jugador.
	 *
	 * @return El número de vidas actuales del jugador.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Establece el número de vidas del jugador.
	 *
	 * @param i El número de vidas que se asignará al jugador.
	 */
	public void setLives(int i) {
		lives = i;
	}

	/**
	 * Incrementa la puntuación del jugador por un valor especificado y registra el nuevo puntaje.
	 *
	 * @param score El valor que se sumará a la puntuación del jugador.
	 */
	public void increaseScore(int score) {
		Logger logger = Logger.getLogger(getClass().getName()); // Inicializar logger

		this.score += score;
		logger.info(() -> String.format("Current score: %d", this.score)); // Usar formato integrado
	}

	/**
	 * Obtiene la puntuación actual del jugador.
	 *
	 * @return La puntuación actual del jugador.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Establece la puntuación del jugador.
	 *
	 * @param scoreAux El valor que se asignará a la puntuación del jugador.
	 */
	public void setScore(int scoreAux) {
		this.score = scoreAux;
	}

	/**
	 * Aplica daño al jugador y maneja el retroceso (knockback). Si el jugador está en estado de aturdimiento (flinching),
	 * no se puede recibir daño.
	 *
	 * @param damage La cantidad de daño que el jugador recibirá.
	 */
	public void hit(int damage) {
		if (flinching)
			return;

		stop();
		health -= damage;
		if (health < 0)
			health = 0;
		flinching = true;
		flinchCount = 0;
		if (facingRight)
			dx = -1;
		else
			dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}

	/**
	 * Resetea las propiedades del jugador a su estado inicial.
	 * Esto incluye restaurar la salud máxima, la dirección en la que está mirando, y el estado de sus acciones.
	 */
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		knockback = false;
		flinching = false;
		jumping = false;
		stop();
	}

	/**
	 * Detiene todas las acciones del jugador, como el movimiento, salto, ataque, etc.
	 * Se utilizan para poner al jugador en un estado de inactividad.
	 */
	public void stop() {
		left = right = up = down = flinching = dashing = jumping = attacking = upattacking = charging = false;
	}

	/**
	 * Calcula la siguiente posición del jugador en función de su estado actual, incluyendo retroceso (knockback),
	 * movimiento, salto y caída.
	 */
	public void getNextPosition() {

		if (knockback) {
			dy += fallSpeed * 2;
			knockback = !falling;
			return;
		}

		movement();

		jumpAndFall();

	}

	/**
	 * Maneja el salto y la caída del jugador. El jugador puede realizar un salto normal o un doble salto.
	 * También se actualiza la velocidad de caída y los efectos relacionados.
	 */
	void jumpAndFall() {
		// Saltando
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
			JukeBox.play(PLAYERJUMP_MUSICNAME); // Reproducir sonido de salto
		}

		// Doble salto
		if (doubleJump) {
			dy = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
			JukeBox.play(PLAYERJUMP_MUSICNAME); // Reproducir sonido de salto
			for (int i = 0; i < 6; i++) {
				energyParticles.add(new EnergyParticle(tileMap, x, y + cheight / 4.0, EnergyParticle.ENERGY_DOWN));
			}
		}

		if (!falling)
			alreadyDoubleJump = false;

		// Caída
		if (falling) {
			dy += fallSpeed;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed; // Detener salto si no está saltando
			if (dy > maxFallSpeed)
				dy = maxFallSpeed; // Limitar la velocidad de caída
		}
	}

	/**
	 * Maneja el movimiento del jugador, incluyendo las direcciones de movimiento, dash y carga de ataque.
	 */
	void movement() {
		double maxSpeed = this.maxSpeed;
		if (dashing)
			maxSpeed *= 1.75; // Aumentar velocidad en el dash

		// Movimiento
		if (left) {
			dx = Math.max(-maxSpeed, dx - moveSpeed); // Mover a la izquierda
		} else if (right) {
			dx = Math.min(maxSpeed, dx + moveSpeed); // Mover a la derecha
		} else {
			if (dx >= 0) {
				dx = Math.max(0, dx - stopSpeed); // Detener el movimiento si no se está presionando ninguna tecla
			} else {
				dx = Math.min(0, dx + stopSpeed);
			}
		}

		// No se puede mover mientras se está atacando, excepto en el aire
		if ((attacking || upattacking || charging) && !(jumping || falling)) {
			dx = 0;
		}

		// Cargando
		if (charging) {
			chargingTick++;
			if (facingRight)
				dx = moveSpeed * (3 - chargingTick * 0.07); // Reducir velocidad al cargar hacia la derecha
			else
				dx = -moveSpeed * (3 - chargingTick * 0.07); // Reducir velocidad al cargar hacia la izquierda
		}
	}

	/**
	 * Establece la animación actual del jugador según la acción proporcionada.
	 *
	 * @param i El índice de la animación que se establecerá.
	 */
	private void setAnimation(int i) {
		if (currentAction != i) {
			currentAction = i;
			animation.setFrames(sprites.get(currentAction));
			animation.setDelay(SPRITEDELAYS[currentAction]);
			width = FRAMEWIDTHS[currentAction];
			height = FRAMEHEIGHTS[currentAction];
		}
	}

	/**
	 * Actualiza el estado del jugador, incluyendo el movimiento, las interacciones con enemigos, la comprobación de animaciones,
	 * y las partículas de energía.
	 */
	public void update() {

		time++;

		// Comprobar teletransportación
		if (teleporting) {
			energyParticles.add(new EnergyParticle(tileMap, x, y, EnergyParticle.ENERGY_UP));
		}

		// Actualizar posición
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		if (isFalling && !falling) {
			JukeBox.play("playerlands"); // Reproducir sonido de aterrizaje
		}
		if (dx == 0)
			x = (int) x;

		checkAttack();
		checkEnemyInteraction();
		checkAnimations();

		animation.update();

		// Establecer dirección
		if (!attacking && !upattacking && !charging && !knockback) {
			if (right)
				facingRight = true;
			if (left)
				facingRight = false;
		}

	}

	/**
	 * Comprueba si el jugador está atacando, interactuando con enemigos o generando partículas de energía.
	 */
	private void checkAttack() {
		// Comprobar si ha terminado el estado de aturdimiento (flinching)
		if (flinching) {
			flinchCount++;
			flinching = flinchCount <= 120;
		}

		// Partículas de energía
		ArrayList<EnergyParticle> particlesToRemove = new ArrayList<>();
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).update();
			if (energyParticles.get(i).shouldRemove()) {
				particlesToRemove.add(energyParticles.get(i));
			}
		}

		for (EnergyParticle e : particlesToRemove) {
			energyParticles.remove(e);
		}

		// Comprobar si terminó un ataque
		if ((currentAction == ATTACKING_ANIM || currentAction == UPATTACKING_ANIM) && animation.hasPlayedOnce()) {
			attacking = false;
			upattacking = false;
		}
		if (currentAction == CHARGING_ANIM) {
			if (animation.hasPlayed(5)) {
				charging = false;
			}
			cr.y = (int) y - 20;
			if (facingRight) {
				cr.x = (int) x - 15;
				energyParticles.add(new EnergyParticle(tileMap, x + 30, y, EnergyParticle.ENERGY_RIGHT));
			} else {
				cr.x = (int) x - 35;
				energyParticles.add(new EnergyParticle(tileMap, x - 30, y, EnergyParticle.ENERGY_LEFT));
			}
		}
	}

	/**
	 * Comprueba las interacciones con los enemigos. El jugador puede atacar, ser atacado o chocar con enemigos.
	 */
	private void checkEnemyInteraction() {
		// Comprobar interacción con los enemigos
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);

			// Comprobar si el jugador está atacando
			if (currentAction == ATTACKING_ANIM && animation.getFrame() == 3 && animation.getCount() == 0
					&& e.intersects(ar)) {
				e.hit(damage);
			}

			// Comprobar ataque hacia arriba
			if (currentAction == UPATTACKING_ANIM && animation.getFrame() == 3 && animation.getCount() == 0
					&& e.intersects(aur)) {
				e.hit(damage);
			}

			// Comprobar ataque de carga
			if (currentAction == CHARGING_ANIM && animation.getCount() == 0 && e.intersects(cr)) {
				e.hit(chargeDamage);
			}

			// Comprobar colisión con enemigo
			if (!e.isDead() && intersects(e) && !charging) {
				hit(e.getDamage());
			}

			if (e.isDead()) {
				JukeBox.play("explode", 2000); // Reproducir sonido de explosión de enemigo
			}
		}
	}

	/**
	 * Actualiza las animaciones del jugador según su estado actual.
	 */
	private void checkAnimations() {
		// Establecer animación, ordenado por prioridad
		if (teleporting) {
			setAnimation(TELEPORTING_ANIM);
		} else if (knockback) {
			setAnimation(KNOCKBACK_ANIM);
		} else if (health == 0) {
			setAnimation(DEAD_ANIM);
		} else if (upattacking) {
			checkUpAttackingAnim();
		} else if (attacking) {
			checkAttackingAnim();
		} else if (charging) {
			setAnimation(CHARGING_ANIM);
		} else if (dy < 0) {
			setAnimation(JUMPING_ANIM);
		} else if (dy > 0) {
			setAnimation(FALLING_ANIM);
		} else if (dashing && (left || right)) {
			setAnimation(DASHING_ANIM);
		} else if (left || right) {
			setAnimation(WALKING_ANIM);
		} else {
			setAnimation(IDLE_ANIM);
		}
	}

	/**
	 * Comprueba si el jugador está atacando y actualiza la animación y las partículas de energía
	 * generadas durante el ataque. Si el jugador está en estado de ataque, se configuran las
	 * posiciones del área de ataque (ar) y las partículas de energía.
	 */
	private void checkAttackingAnim() {
		if (currentAction != ATTACKING_ANIM) {
			JukeBox.play(PLAYERATTACKMUSICNAME); // Reproducir sonido de ataque
			setAnimation(ATTACKING_ANIM); // Establecer animación de ataque
			ar.y = (int) y - 6;
			ar.x = facingRight ? (int) x + 10 : (int) x - 40; // Definir la posición del área de ataque
		} else {
			// Generar partículas de energía durante el ataque
			if (animation.getFrame() == 4 && animation.getCount() == 0) {
				for (int c = 0; c < 3; c++) {
					if (facingRight)
						energyParticles.add(
								new EnergyParticle(tileMap, ar.x + (double) ar.width - 4,
										ar.y + (double) ar.height / 2, EnergyParticle.ENERGY_RIGHT)); // Partículas hacia la derecha
					else
						energyParticles.add(
								new EnergyParticle(tileMap, ar.x + (double) 4,
										ar.y + (double) ar.height / 2, EnergyParticle.ENERGY_LEFT)); // Partículas hacia la izquierda
				}
			}
		}
	}

	/**
	 * Comprueba si el jugador está atacando hacia arriba y actualiza la animación y las partículas de energía
	 * generadas durante el ataque hacia arriba. Configura la posición del área de ataque hacia arriba (aur).
	 */
	private void checkUpAttackingAnim() {
		if (currentAction != UPATTACKING_ANIM) {
			JukeBox.play(PLAYERATTACKMUSICNAME); // Reproducir sonido de ataque hacia arriba
			setAnimation(UPATTACKING_ANIM); // Establecer animación de ataque hacia arriba
			aur.x = (int) x - 15;
			aur.y = (int) y - 50; // Posición del área de ataque hacia arriba
		} else {
			// Generar partículas de energía hacia arriba durante el ataque
			if (animation.getFrame() == 4 && animation.getCount() == 0) {
				for (int c = 0; c < 3; c++) {
					energyParticles.add(
							new EnergyParticle(tileMap, aur.x + (double) aur.width / 2,
									aur.y + (double) 5, EnergyParticle.ENERGY_UP)); // Partículas de energía hacia arriba
				}
			}
		}
	}

	/**
	 * Dibuja el jugador y sus efectos visuales, incluyendo las partículas de energía y las emotes.
	 * También controla el parpadeo del jugador cuando está aturdido.
	 *
	 * @param g El objeto Graphics2D utilizado para dibujar el jugador.
	 */
	@Override
	public void draw(Graphics2D g) {

		// Dibujar emote si está presente
		if (emote == CONFUSED_EMOTE) {
			g.drawImage(confused, (int) (x + xmap - cwidth / 2.0),
					(int) (y + ymap - 40), null); // Emote confundido
		} else if (emote == SURPRISED_EMOTE) {
			g.drawImage(surprised, (int) (x + xmap - cwidth / 2.0),
					(int) (y + ymap - 40), null); // Emote sorprendido
		}

		// Dibujar partículas de energía
		for (int i = 0; i < energyParticles.size(); i++) {
			energyParticles.get(i).draw(g);
		}

		// Parpadeo cuando está aturdido (flinching)
		if (flinching && !knockback && flinchCount % 10 < 5) {
			return; // No dibujar si está parpadeando
		}

		super.draw(g); // Dibujar el jugador
	}

	/**
	 * Verifica si el jugador está en el estado de teletransportación.
	 *
	 * @return true si el jugador está teletransportándose, false en caso contrario.
	 */
	public boolean isTeleporting() {
		return teleporting;
	}

	/**
	 * Verifica si el jugador está cargando un ataque.
	 *
	 * @return true si el jugador está cargando, false en caso contrario.
	 */
	public boolean isCharging() {
		return charging;
	}

	/**
	 * Verifica si el jugador está en el estado de retroceso (knockback).
	 *
	 * @return true si el jugador está en retroceso, false en caso contrario.
	 */
	public boolean isKnockback() {
		return knockback;
	}

	/**
	 * Establece el estado de retroceso (knockback) del jugador.
	 *
	 * @param knockback true para poner al jugador en retroceso, false para detener el retroceso.
	 */
	public void setKnockback(boolean knockback) {
		this.knockback = knockback;
	}

	/**
	 * Verifica si el jugador está atacando.
	 *
	 * @return true si el jugador está atacando, false en caso contrario.
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * Verifica si el jugador ya ha realizado el doble salto.
	 *
	 * @return true si ya ha realizado el doble salto, false en caso contrario.
	 */
	public boolean isAlreadyDoubleJump() {
		return alreadyDoubleJump;
	}

	/**
	 * Establece si el jugador puede realizar un doble salto.
	 *
	 * @param doubleJump true para habilitar el doble salto, false para deshabilitarlo.
	 */
	public void setDoubleJump(boolean doubleJump) {
		this.doubleJump = doubleJump;
	}

	/**
	 * Verifica si el jugador tiene habilitado el doble salto.
	 *
	 * @return true si el jugador tiene habilitado el doble salto, false en caso contrario.
	 */
	public boolean isDoubleJump() {
		return doubleJump;
	}

	/**
	 * Obtiene el valor de inicio del doble salto.
	 *
	 * @return el valor de inicio del doble salto.
	 */
	public double getDoubleJumpStart() {
		return doubleJumpStart;
	}

	/**
	 * Establece si el jugador está aturdido (flinching).
	 *
	 * @param flinching true para poner al jugador en estado de aturdimiento, false para detenerlo.
	 */
	public void setFlinching(boolean flinching) {
		this.flinching = flinching;
	}

	/**
	 * Verifica si el jugador está aturdido (flinching).
	 *
	 * @return true si el jugador está aturdido, false en caso contrario.
	 */
	public boolean isFlinching() {
		return flinching;
	}

	/**
	 * Obtiene el valor de la animación de caída.
	 *
	 * @return el valor de la animación de caída.
	 */
	public int getFallingAnim() {
		return FALLING_ANIM;
	}

	/**
	 * Obtiene el valor de la animación de salto.
	 *
	 * @return el valor de la animación de salto.
	 */
	public int getJumpingAnim() {
		return JUMPING_ANIM;
	}

	/**
	 * Verifica si el jugador está saltando.
	 *
	 * @return true si el jugador está saltando, false en caso contrario.
	 */
	public boolean isJumping() {
		return jumping;
	}
}