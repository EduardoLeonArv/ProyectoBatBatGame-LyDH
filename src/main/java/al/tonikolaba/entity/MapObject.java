package al.tonikolaba.entity;

import java.awt.Rectangle;

import al.tonikolaba.main.GamePanel;
import al.tonikolaba.tilemap.Tile;
import al.tonikolaba.tilemap.TileMap;

/**
 * MapObject represents an object in the game world that interacts with the map's tiles.
 * This class handles the position, movement, collision detection, and animation of objects in the game world.
 *
 * @author ArtOfSoul
 */
public class MapObject {

	// tile stuff
	/** The tile map the object is part of */
	protected TileMap tileMap;

	/** Size of the tiles in the tile map */
	protected int tileSize;

	/** The x position of the map */
	protected double xmap;

	/** The y position of the map */
	protected double ymap;

	// position and vector
	/** X position of the object */
	public double x;

	/** Y position of the object */
	public double y;

	/** Change in x (movement velocity) */
	public double dx;

	/** Change in y (movement velocity) */
	public double dy;

	// dimensions
	/** Width of the object */
	protected int width;

	/** Height of the object */
	protected int height;

	// collision box
	/** Width of the collision box */
	protected int cwidth;

	/** Height of the collision box */
	protected int cheight;

	// collision
	/** Current row of the object on the tile map */
	protected int currRow;

	/** Current column of the object on the tile map */
	protected int currCol;

	/** Destination x for movement */
	protected double xdest;

	/** Destination y for movement */
	protected double ydest;

	/** Temporary x position used for collision checks */
	protected double xtemp;

	/** Temporary y position used for collision checks */
	protected double ytemp;

	/** Whether the top-left corner of the object collides */
	protected boolean topLeft;

	/** Whether the top-right corner of the object collides */
	protected boolean topRight;

	/** Whether the bottom-left corner of the object collides */
	protected boolean bottomLeft;

	/** Whether the bottom-right corner of the object collides */
	protected boolean bottomRight;

	// animation
	/** The animation object used for the object */
	protected Animation animation;

	/** The current action state of the object (e.g., walking, jumping) */
	protected int currentAction;

	/** The previous action state of the object */
	protected int previousAction;

	/** Whether the object is facing right */
	protected boolean facingRight;

	// movement
	/** Whether the object is moving left */
	protected boolean left;

	/** Whether the object is moving right */
	protected boolean right;

	/** Whether the object is moving up */
	protected boolean up;

	/** Whether the object is moving down */
	protected boolean down;

	/** Whether the object is jumping */
	protected boolean jumping;

	/** Whether the object is falling */
	protected boolean falling;

	// movement attributes
	/** Movement speed of the object */
	protected double moveSpeed;

	/** Maximum speed of the object */
	protected double maxSpeed;

	/** Speed at which the object stops moving */
	protected double stopSpeed;

	/** Speed at which the object falls */
	protected double fallSpeed;

	/** Maximum falling speed of the object */
	protected double maxFallSpeed;

	/** Initial speed for jumping */
	protected double jumpStart;

	/** Speed at which the object stops jumping */
	protected double stopJumpSpeed;


	/**
	 * Constructor for the MapObject class.
	 * Initializes the object with a given TileMap and sets initial values for various attributes.
	 *
	 * @param tm The TileMap that the object is part of
	 */
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
		animation = new Animation();
		facingRight = true;
		currentAction = -1;
	}

	/**
	 * Checks if this MapObject intersects with another MapObject.
	 *
	 * @param o The other MapObject to check intersection with
	 * @return True if the two objects intersect, otherwise false
	 */
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	/**
	 * Checks if this MapObject intersects with a given rectangle.
	 *
	 * @param r The rectangle to check intersection with
	 * @return True if the object intersects with the rectangle, otherwise false
	 */
	public boolean intersects(Rectangle r) {
		return getRectangle().intersects(r);
	}

	/**
	 * Checks if this MapObject contains another MapObject within its bounds.
	 *
	 * @param o The other MapObject to check containment
	 * @return True if this object contains the other, otherwise false
	 */
	public boolean contains(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.contains(r2);
	}

	/**
	 * Sets the TileMap for this MapObject and updates the tile size.
	 *
	 * @param tm The new TileMap to associate with this object
	 */
	public void setTileMap(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	/**
	 * Checks if this MapObject contains a given rectangle.
	 *
	 * @param r The rectangle to check containment
	 * @return True if the object contains the rectangle, otherwise false
	 */
	public boolean contains(Rectangle r) {
		return getRectangle().contains(r);
	}

	/**
	 * Gets the rectangle representing the collision box of this MapObject.
	 *
	 * @return The rectangle representing the collision bounds of the object
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int) x - cwidth / 2, (int) y - cheight / 2, cwidth, cheight);
	}

	/**
	 * Calculates the corners of the MapObject based on its position.
	 * This is used for collision detection.
	 *
	 * @param x The x position to check
	 * @param y The y position to check
	 */
	public void calculateCorners(double x, double y) {
		int leftTile = (int) (x - cwidth / 2.0) / tileSize;
		int rightTile = (int) (x + cwidth / 2.0 - 1) / tileSize;
		int topTile = (int) (y - cheight / 2.0) / tileSize;
		int bottomTile = (int) (y + cheight / 2.0 - 1) / tileSize;
		if (topTile < 0 || bottomTile >= tileMap.getNumRows()
				|| leftTile < 0 || rightTile >= tileMap.getNumCols()) {
			topLeft = topRight = bottomLeft = bottomRight = false;
			return;
		}
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}

	/**
	 * Checks for tile map collisions and adjusts the object's position accordingly.
	 */
	public void checkTileMapCollision() {

		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;

		xdest = x + dx;
		ydest = y + dy;

		xtemp = x;
		ytemp = y;

		checkYCollision();

		checkXCollision();

		if (!falling) {
			calculateCorners(x, ydest + 1);
			if (!bottomLeft && !bottomRight) {
				falling = true;
			}
		}

	}

	/**
	 * Checks for X-axis collisions and adjusts the object's horizontal position.
	 */
	private void checkXCollision() {
		calculateCorners(xdest, y);
		if (dx < 0) {
			if (topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2.0;
			} else {
				xtemp += dx;
			}
		}
		if (dx > 0) {
			if (topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2.0;
			} else {
				xtemp += dx;
			}
		}
	}

	/**
	 * Checks for Y-axis collisions and adjusts the object's vertical position.
	 */
	private void checkYCollision() {
		calculateCorners(x, ydest);
		if (dy < 0) {
			if (topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2.0;
			} else {
				ytemp += dy;
			}
		}
		if (dy > 0) {
			if (bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2.0;
			} else {
				ytemp += dy;
			}
		}
	}

	/** Getter and setter methods. */
	public int getx() {
		return (int) x;
	}

	/**
	 *
	 *
	 * @return
	 */
	public int gety() {
		return (int) y;
	}

	/**
	 *
	 *
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 *
	 *
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 *
	 *
	 * @return
	 */
	public int getCWidth() {
		return cwidth;
	}

	/**
	 *
	 *
	 * @return
	 */
	public int getCHeight() {
		return cheight;
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean isFacingRight() {
		return facingRight;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setLeft(boolean b) {
		left = b;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setRight(boolean b) {
		right = b;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setUp(boolean b) {
		up = b;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setDown(boolean b) {
		down = b;
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setJumping(boolean b) {
		jumping = b;
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean notOnScreen() {
		return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH || y + ymap + height < 0
				|| y + ymap - height > GamePanel.HEIGHT;
	}

	/**
	 * Draws the MapObject on the screen with its animation.
	 *
	 * @param g The Graphics2D object used to draw the object
	 */
	public void draw(java.awt.Graphics2D g) {
		setMapPosition();
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2.0),
					(int) (y + ymap - height / 2.0), null);
		} else {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2.0 + width),
					(int) (y + ymap - height / 2.0),
					-width, height, null);
		}
	}

	/**
	 *
	 *
	 * @return
	 */
	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean isFalling() { return  falling; }

	/**
	 *
	 *
	 * @return
	 */
	public int getCurrentAction() {
		return currentAction;
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getDy() {
		return dy;
	}

	/**
	 *
	 *
	 * @return
	 */
	public double getDx() {
		return dx;
	}
}
