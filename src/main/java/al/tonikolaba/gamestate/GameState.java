package al.tonikolaba.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import al.tonikolaba.audio.JukeBox;
import al.tonikolaba.entity.Enemy;
import al.tonikolaba.entity.Enemy.EnemyType;
import al.tonikolaba.entity.EnemyProjectile;
import al.tonikolaba.entity.EnergyParticle;
import al.tonikolaba.entity.Explosion;
import al.tonikolaba.entity.HUD;
import al.tonikolaba.entity.Player;
import al.tonikolaba.entity.PlayerSave;
import al.tonikolaba.entity.Portal;
import al.tonikolaba.entity.Teleport;
import al.tonikolaba.entity.Title;
import al.tonikolaba.entity.enemies.RedEnergy;
import al.tonikolaba.entity.enemies.XhelBat;
import al.tonikolaba.entity.enemies.Zogu;
import al.tonikolaba.handlers.Keys;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.Background;
import al.tonikolaba.tilemap.TileMap;

/**
 * @author ArtOfSoul
 */

public abstract class GameState extends BasicState {

	/** Name of the teleportation music. */
	private static final String TELEPORT_MUSIC_NAME = "teleport";

	/** List of enemies in the level. */
	protected ArrayList<Enemy> enemies;

	/** List of enemy projectiles. */
	protected ArrayList<EnemyProjectile> eprojectiles;

	/** List of explosions in the game. */
	protected ArrayList<Explosion> explosions;

	/** Heads-Up Display (HUD) for player stats. */
	protected HUD hud;

	/** Image for the starting event. */
	protected BufferedImage batBatStart;

	/** Title of the event or level. */
	protected Title title;

	/** Subtitle of the event or level. */
	protected Title subtitle;

	/** Teleport object for level transitions. */
	protected Teleport teleport;

	/** Event timing counter. */
	protected int eventCount = 0;

	/** Indicates if the start event is active. */
	protected boolean eventStart;

	/** List of rectangles for event visual effects. */
	protected ArrayList<Rectangle> tb;

	/** Indicates if the finish event is active. */
	protected boolean eventFinish;

	/** Indicates if the dead event is active. */
	protected boolean eventDead;

	/** Indicates if the portal event is active. */
	protected boolean eventPortal;

	/** Portal object for level transitions. */
	protected Portal portal;

	/** Enemy types in the current level. */
	protected EnemyType[] enemyTypesInLevel;

	/** Coordinates for enemy placement. */
	protected int[][] coords;

	/** Background for the sky. */
	protected Background sky;

	/** Background for the clouds. */
	protected Background clouds;

	/** Background for the mountains. */
	protected Background mountains;

	/** Background for the sunset. */
	protected Background perendimi;

	/** Background for the temple. */
	protected Background temple;

	/** Player's starting X coordinate. */
	protected int playerXStart;

	/** Player's starting Y coordinate. */
	protected int playerYStart;

	/** State of the next level. */
	protected int nextLevelState;

	/** Name of the level music. */
	protected String levelMusicName;

	/** Reference to the player. */
	protected Player player;

	/** Tile map for the level. */
	protected TileMap tileMap;

	/** Indicates if the earthquake event is active. */
	protected boolean eventQuake;

	/** Indicates if input is blocked. */
	protected boolean blockInput;

	/** Constructor de la clase GameState*/
	public GameState(GameStateManager gsm, Player player) {
		super(gsm);
		this.player = player;
		this.eventQuake = false;
		this.blockInput = false;
		this.eventCount = 0;
	}

	/**
	 * Inicializa el estado del nivel con el próximo nivel a cargar.
	 *
	 * @param nextLevel Estado del próximo nivel.
	 */
	public void init(int nextLevel) {
		nextLevelState = nextLevel;
	}

	/**
	 * Maneja las entradas del jugador y activa las acciones correspondientes.
	 * También permite pausar el juego si se presiona ESCAPE.
	 */
	@Override
	public void handleInput() {
		if (Keys.isPressed(Keys.ESCAPE)) {
			gsm.setPaused(true); // Pausar el juego si se presiona ESCAPE.
		}
		if (blockInput || player.getHealth() == 0) {
			return; // Bloquear la entrada si está activo o el jugador está muerto.
		}
		player.setUp(Keys.getKeyState()[Keys.UP]); // Movimiento hacia arriba.
		player.setLeft(Keys.getKeyState()[Keys.LEFT]); // Movimiento hacia la izquierda.
		player.setDown(Keys.getKeyState()[Keys.DOWN]); // Movimiento hacia abajo.
		player.setRight(Keys.getKeyState()[Keys.RIGHT]); // Movimiento hacia la derecha.
		player.setJumping(Keys.getKeyState()[Keys.BUTTON1]); // Salto del jugador.
		player.setDashing(Keys.getKeyState()[Keys.BUTTON2]); // Desplazamiento rápido del jugador.
		if (Keys.isPressed(Keys.BUTTON3)) {
			player.setAttacking(); // Activar ataque del jugador.
		}
		if (Keys.isPressed(Keys.BUTTON4)) {
			player.setCharging(); // Activar carga del jugador.
		}
	}

	/**
	 * Maneja y actualiza los objetos del nivel, incluyendo enemigos, proyectiles enemigos y explosiones.
	 *
	 * @param tileMap         Mapa de tiles donde se desarrolla el nivel.
	 * @param enemies         Lista de enemigos presentes en el nivel.
	 * @param eprojectiles    Lista de proyectiles enemigos presentes en el nivel.
	 * @param explosions      Lista de explosiones generadas en el nivel.
	 */
	protected void handleObjects(TileMap tileMap, List<Enemy> enemies, List<EnemyProjectile> eprojectiles,
								 List<Explosion> explosions) {
		ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
		// Actualizar enemigos
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemiesToRemove.add(e); // Añadir enemigo a la lista de eliminación
				explosions.add(new Explosion(tileMap, e.getx(), e.gety())); // Crear explosión donde muere el enemigo
			}
		}

		// Eliminar enemigos muertos
		for (Enemy enemy : enemiesToRemove) {
			enemies.remove(enemy);
		}

		ArrayList<EnemyProjectile> projectilesToRemove = new ArrayList<>();
		// Actualizar proyectiles enemigos
		for (int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if (ep.shouldRemove()) {
				projectilesToRemove.add(ep); // Añadir proyectil a la lista de eliminación
			}
		}

		// Eliminar proyectiles que ya no deben existir
		for (EnemyProjectile enemyProjectile : projectilesToRemove) {
			eprojectiles.remove(enemyProjectile);
		}

		ArrayList<Explosion> explosionsToRemove = new ArrayList<>();
		// Actualizar explosiones
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if (explosions.get(i).shouldRemove()) {
				explosionsToRemove.add(explosions.get(i)); // Añadir explosión a la lista de eliminación
			}
		}

		// Eliminar explosiones que ya no deben existir
		for (Explosion explosion : explosionsToRemove) {
			explosions.remove(explosion);
		}
	}


	/** Metodo generateTileMap*/
	protected void generateTileMap(String map, int x, int y, boolean bounds) {
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/ruinstileset.gif");
		tileMap.loadMap(map);
		tileMap.setPosition(x, y);
		if (bounds)
			tileMap.setBounds(tileMap.getWidth() - 1 * tileMap.getTileSize(),
					tileMap.getHeight() - 2 * tileMap.getTileSize(), 0, 0);
		tileMap.setTween(1);
	}
	/** Metodo setupGameObject */
	protected void setupGameObjects(int playerX, int playerY,
	int goalX, int goalY, boolean portal) {
		// player
		playerXStart = playerX;
		playerYStart = playerY;
		player.setTileMap(tileMap);
		player.setPosition(playerX, playerY);
		player.setHealth(PlayerSave.getHealth());
		player.setLives(PlayerSave.getLives());
		player.setTime(PlayerSave.getTime());

		// enemies
		enemies = new ArrayList<>();
		eprojectiles = new ArrayList<>();

		// energy particle
		ArrayList<EnergyParticle> energyParticles;
		energyParticles = new ArrayList<>();

		// init player
		player.init(enemies, energyParticles);

		// explosions
		explosions = new ArrayList<>();

		// hud
		hud = new HUD(player);

		// teleport
		if (!portal) {
			teleport = new Teleport(tileMap);
			teleport.setPosition(goalX, goalY);
		} else {
			this.portal = new Portal(tileMap);
			this.portal.setPosition(goalX, goalY);
		}

		// start event
		eventStart = true;
		tb = new ArrayList<>();
		eventStartFunc();
	}

	/** Metodo set */
	protected void setupMusic(String level, String bgMusic, boolean loop) {
		// sfx
		levelMusicName = level;
		JukeBox.load("/SFX/teleport.mp3", TELEPORT_MUSIC_NAME);
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");

		// music
		JukeBox.load(bgMusic, level);
		if (loop)
			JukeBox.loop(level, 600, JukeBox.getFrames(level) - 2200);
	}

	protected void setupTitle(int[] titleCoords, int[] subtitleCoords) {
		// title and subtitle
		try {
			batBatStart = ImageIO.read(
				getClass().getResourceAsStream("/HUD/batbat.gif"));
			this.title = new Title(
					batBatStart.getSubimage(titleCoords[0],
					titleCoords[1], titleCoords[2], titleCoords[3]));
			this.title.sety(60);
			this.subtitle = new Title(batBatStart.
			getSubimage(subtitleCoords[0],
			subtitleCoords[1], subtitleCoords[2],
					subtitleCoords[3]));
			this.subtitle.sety(85);
		} catch (Exception e) {
			LoggingHelper.LOGGER.log(Level.SEVERE,
			e.getMessage());
		}
	}

	/**
	 * Población de enemigos en el nivel.
	 * Crea y posiciona a los enemigos en base a sus tipos y coordenadas iniciales.
	 *
	 * @param enemyTypes Arreglo con los tipos de enemigos que se deben generar.
	 * @param coords     Arreglo bidimensional con las coordenadas iniciales de cada enemigo.
	 */
	protected void populateEnemies(EnemyType[] enemyTypes, int[][] coords) {
		this.enemies.clear(); // Limpia cualquier enemigo previo.

		for (int i = 0; i < enemyTypes.length; i++) {
			Enemy e = null; // Inicializa un enemigo nulo.
			switch (enemyTypes[i]) {
				case RED_ENERGY:
					e = new RedEnergy(tileMap, player); // Crea un enemigo de tipo RedEnergy.
					break;
				case XHELBAT:
					e = new XhelBat(tileMap, player); // Crea un enemigo de tipo XhelBat.
					break;
				case ZOGU:
					e = new Zogu(tileMap, player); // Crea un enemigo de tipo Zogu.
					break;
			}

			if (e != null) {
				e.setPosition(coords[i][0], coords[i][1]); // Configura la posición inicial del enemigo.
				this.enemies.add(e); // Agrega el enemigo a la lista de enemigos del nivel.
			}
		}
	}

	/**
	 * Actualiza el estado del nivel.
	 * Gestiona eventos como inicio, muerte y finalización, además de actualizar
	 * al jugador, enemigos, objetos del nivel y el mapa de tiles.
	 */
	@Override
	public void update() {

		// Verifica las entradas del jugador.
		handleInput();

		// Verifica si el evento de finalización del nivel debe comenzar.
		if (teleport != null && teleport.contains(player)) {
			eventFinish = blockInput = true; // Activa el evento de finalización y bloquea la entrada.
		}

		// Verifica si el evento de muerte del jugador debe comenzar.
		if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true; // Activa el evento de muerte y bloquea la entrada.
		}

		// Ejecuta los eventos activos.
		if (eventStart) eventStartFunc();
		if (eventDead) eventDeadFunc();
		if (eventFinish) eventFinishFunc();

		// Actualiza el título y subtítulo del nivel.
		if (title != null) {
			title.update();
		}
		if (subtitle != null) {
			subtitle.update();
		}

		// Actualiza la posición de los fondos según el mapa de tiles.
		if (clouds != null) clouds.setPosition(tileMap.getx(), tileMap.gety());
		if (mountains != null) mountains.setPosition(tileMap.getx(), tileMap.gety());
		if (sky != null) sky.setPosition(tileMap.getx(), tileMap.gety());
		if (perendimi != null) perendimi.setPosition(tileMap.getx(), tileMap.gety());
		if (temple != null) temple.setPosition(tileMap.getx(), tileMap.gety());

		// Actualiza al jugador.
		player.update();

		// Actualiza el mapa de tiles.
		tileMap.setPosition(GamePanel.WIDTH / 2.0 - player.getx(),
				GamePanel.HEIGHT / 2.0 - player.gety());
		tileMap.update();
		tileMap.fixBounds();

		// Gestiona los enemigos, proyectiles y explosiones.
		handleObjects(tileMap, enemies, eprojectiles, explosions);

		// Actualiza el teletransporte, si está presente.
		if (teleport != null) {
			teleport.update();
		}
	}


	/**
	 * Dibuja todos los elementos del nivel en el contexto gráfico proporcionado.
	 * Incluye el fondo, el mapa, los enemigos, explosiones, proyectiles, el jugador, el HUD, y elementos de transición.
	 *
	 * @param g Contexto gráfico utilizado para renderizar los elementos.
	 */
	@Override
	public void draw(Graphics2D g) {
		// Dibuja el fondo
		if (sky != null) sky.draw(g);
		if (clouds != null) clouds.draw(g);
		if (mountains != null) mountains.draw(g);
		if (perendimi != null) perendimi.draw(g);
		if (temple != null) temple.draw(g);

		// Dibuja el mapa de tiles
		tileMap.draw(g);

		// Dibuja los enemigos
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// Dibuja los proyectiles enemigos
		for (int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}

		// Dibuja las explosiones
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}

		// Dibuja al jugador
		player.draw(g);

		// Dibuja el teletransporte y el portal, si están disponibles
		if (teleport != null) teleport.draw(g);
		if (portal != null) portal.draw(g);

		// Dibuja el HUD
		hud.draw(g);

		// Dibuja el título y subtítulo, si están disponibles
		if (title != null) title.draw(g);
		if (subtitle != null) subtitle.draw(g);

		// Dibuja las cajas de transición
		g.setColor(java.awt.Color.YELLOW);
		for (int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
	}

	/**
	 * Reinicia el nivel.
	 * Restaura la posición inicial del jugador, repuebla a los enemigos,
	 * y resetea el estado del nivel para comenzar desde el principio.
	 */
	protected void reset() {
		player.reset(); // Reinicia el estado del jugador.
		player.setPosition(playerXStart, playerYStart); // Coloca al jugador en la posición inicial.
		populateEnemies(enemyTypesInLevel, coords); // Rellena el nivel con los enemigos definidos.
		blockInput = true; // Bloquea la entrada del jugador temporalmente.
		eventCount = 0; // Reinicia el contador de eventos.
		tileMap.setShaking(false, 0); // Detiene cualquier sacudida del mapa.
		eventStart = true; // Activa el evento de inicio.
		eventStartFunc(); // Ejecuta la función de inicio del evento.
		if (title != null) title.reset(); // Reinicia el título, si está disponible.
		if (subtitle != null) subtitle.reset(); // Reinicia el subtítulo, si está disponible.
	}

	/**
	 * Maneja el evento de inicio del nivel.
	 * Muestra una animación de transición con rectángulos que se mueven hacia afuera,
	 * y configura los elementos iniciales como el portal y subtítulos.
	 */
	protected void eventStartFunc() {
		eventCount++;
		if (eventCount == 1) {
			// Configura los rectángulos iniciales para la animación de transición.
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if (eventCount > 1 && eventCount < 60) {
			// Mueve los rectángulos hacia afuera de la pantalla para completar la transición.
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if (eventCount == 30 && title != null) {
			title.begin(); // Inicia la animación del título.
		}
		if (eventCount == 60) {
			// Finaliza el evento de inicio y habilita los controles del jugador.
			eventStart = blockInput = false;
			eventCount = 0;
			if (portal != null) eventPortal = blockInput = true; // Activa el evento del portal.
			if (subtitle != null) subtitle.begin(); // Inicia la animación del subtítulo.
			tb.clear(); // Limpia las cajas de transición.
		}
	}


	/**
	 * Maneja el evento de muerte del jugador.
	 * Cambia el estado del jugador a "muerto", muestra una animación de transición,
	 * y verifica si debe reiniciar el nivel o volver al menú principal en caso de que el jugador pierda todas las vidas.
	 */
	protected void eventDeadFunc() {
		eventCount++;
		if (eventCount == 1) {
			player.setDead(); // Marca al jugador como muerto.
			player.stop(); // Detiene el movimiento del jugador.
		}
		if (eventCount == 60) {
			// Inicia la animación de transición centrada en la pantalla.
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2,
					GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 60) {
			// Expande la animación de transición.
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if (eventCount >= 120) {
			// Verifica si el jugador tiene vidas restantes.
			if (player.getLives() == 0) {
				gsm.setState(GameStateManager.MENUSTATE); // Regresa al menú principal.
			} else {
				// Reinicia el nivel y reduce una vida.
				eventDead = blockInput = false;
				eventCount = 0;
				player.loseLife();
				reset();
			}
		}
	}

	/**
	 * Maneja el evento de finalización del nivel.
	 * Reproduce música de teletransporte, realiza una animación de transición
	 * y guarda el progreso del jugador antes de pasar al siguiente nivel.
	 */
	protected void eventFinishFunc() {
		JukeBox.stop(levelMusicName); // Detiene la música del nivel.
		eventCount++;
		if (eventCount == 1) {
			JukeBox.play(TELEPORT_MUSIC_NAME); // Reproduce música de teletransporte.
			player.setTeleporting(true); // Marca al jugador como teletransportado.
			player.stop(); // Detiene el movimiento del jugador.
		} else if (eventCount == 120) {
			// Inicia la animación de transición centrada en la pantalla.
			tb.clear();
			tb.add(new Rectangle(GamePanel.WIDTH / 2,
					GamePanel.HEIGHT / 2, 0, 0));
		} else if (eventCount > 120) {
			// Expande la animación de transición.
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			JukeBox.stop(TELEPORT_MUSIC_NAME); // Detiene la música de teletransporte.
		}
		if (eventCount == 180) {
			// Guarda el progreso del jugador y pasa al siguiente nivel.
			PlayerSave.setHealth(player.getHealth());
			PlayerSave.setLives(player.getLives());
			PlayerSave.setTime(player.getTime());
			gsm.setState(nextLevelState);
		}
	}

	/**
	 * Configura los enemigos en el nivel actual según sus tipos y coordenadas.
	 *
	 * @param enemyTypes   Arreglo de tipos de enemigos que se deben generar.
	 * @param coordinates  Arreglo bidimensional con las coordenadas de los enemigos.
	 */
	protected void configureEnemies(EnemyType[] enemyTypes, int[][] coordinates) {
		this.enemies.clear();
		for (int i = 0; i < enemyTypes.length; i++) {
			// Crea y agrega enemigos al nivel.
			Enemy enemy = createEnemy(enemyTypes[i], coordinates[i][0], coordinates[i][1]);
			if (enemy != null) {
				this.enemies.add(enemy);
			}
		}
	}

	/**
	 * Crea un enemigo específico basado en su tipo y coordenadas iniciales.
	 *
	 * @param type Tipo de enemigo a crear.
	 * @param x    Coordenada X inicial del enemigo.
	 * @param y    Coordenada Y inicial del enemigo.
	 * @return Una instancia del enemigo creado o {@code null} si el tipo no es válido.
	 */
	private Enemy createEnemy(EnemyType type, int x, int y) {
		switch (type) {
			case RED_ENERGY: return new RedEnergy(tileMap, player);
			case XHELBAT: return new XhelBat(tileMap, player);
			case ZOGU: return new Zogu(tileMap, player);
			default: return null;
		}
	}

	/**
	 * Maneja el evento de un terremoto en el nivel.
	 * Configura las acciones del jugador y el estado del mapa, como la animación de sacudida.
	 *
	 * @param quakeTriggerX Coordenada X en la que se activa el terremoto.
	 * @param emoteTimings  Arreglo de tiempos en los que se muestran distintas expresiones del jugador.
	 */
	protected void handleEventQuake(int quakeTriggerX, int[] emoteTimings) {
		if (player.getx() > quakeTriggerX && !tileMap.isShaking()) {
			eventQuake = blockInput = true; // Activa el evento de terremoto y bloquea la entrada del jugador.
		}
		if (eventQuake) {
			eventCount++;
			if (eventCount == 1) {
				player.stop(); // Detiene al jugador.
				player.setPosition(quakeTriggerX, player.gety()); // Reposiciona al jugador.
			} else if (eventCount == emoteTimings[0]) {
				player.setEmote(Player.CONFUSED_EMOTE); // Muestra emoción de confusión.
			} else if (eventCount == emoteTimings[1]) {
				player.setEmote(Player.NONE_EMOTE); // Limpia emociones previas.
			} else if (eventCount == emoteTimings[2]) {
				tileMap.setShaking(true, 10); // Activa la animación de sacudida del mapa.
			} else if (eventCount == emoteTimings[3]) {
				player.setEmote(Player.SURPRISED_EMOTE); // Muestra emoción de sorpresa.
			} else if (eventCount >= emoteTimings[4]) {
				// Finaliza el evento de terremoto.
				player.setEmote(Player.NONE_EMOTE);
				eventQuake = blockInput = false;
				eventCount = 0;
			}
		}
	}

}
