package al.tonikolaba.entity.batbat;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import al.tonikolaba.entity.MapObject;
import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.tilemap.TileMap;

/**
 * Esta clase representa una pieza de un objeto del tipo "bat" en el juego.
 * Se encarga de gestionar la animación y el comportamiento visual de una pieza
 * del "bat" que se mueve en el mapa.
 *
 * @author ArtOfSoul
 */
public class Piece extends MapObject {

	/** Arreglo que contiene los sprites de la pieza */
	private BufferedImage[] sprites;

	/**
	 * Constructor que inicializa la pieza, cargando la animación desde un archivo de imagen.
	 *
	 * @param tm El mapa de tiles en el que la pieza está ubicada
	 * @param mapCoords Las coordenadas (x, y, ancho, alto) para obtener el sprite de la pieza
	 */
	public Piece(TileMap tm, int[] mapCoords) {
		super(tm);
		try {
			// Carga la hoja de sprites desde el archivo
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Other/ballBatBoss.gif")
			);

			// Inicializa el arreglo de sprites con un solo sprite
			sprites = new BufferedImage[1];
			width = height = 4;  // Establece las dimensiones de la pieza
			sprites[0] = spritesheet.getSubimage(mapCoords[0], mapCoords[1], mapCoords[2], mapCoords[3]);

			// Establece la animación con el sprite cargado y sin retardo
			animation.setFrames(sprites);
			animation.setDelay(-1);

		} catch (Exception e) {
			// Maneja cualquier error que ocurra al cargar la imagen
			LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Actualiza la posición y la animación de la pieza.
	 * Esta función es llamada para mover la pieza y actualizar su animación.
	 */
	public void update() {
		x += dx;  // Actualiza la posición en el eje X
		y += dy;  // Actualiza la posición en el eje Y
		animation.update();  // Actualiza la animación
	}

	/**
	 * Dibuja la pieza en la pantalla utilizando las coordenadas actuales.
	 *
	 * @param g El objeto Graphics2D que se utiliza para dibujar la pieza
	 */
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);  // Llama al método de la clase base para dibujar la pieza
	}
}
