package al.tonikolaba.tilemap;

import al.tonikolaba.handlers.LoggingHelper;
import al.tonikolaba.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.security.SecureRandom;
/**
 * @author ArtOfSoul
 */

public class TileMap {

    // Agregamos los cambios para solventar security hotspots
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    // position
    private double x;
    private double y;

    // bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    private double tween;

    // map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    // tileset
    private int numTilesAcross;
    private Tile[][] tiles;

    // drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;

    // effects
    private boolean shaking;
    private int intensity;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

    public void loadTiles(String s) {

        try {
            BufferedImage tileset;
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
    
            // Leer número de columnas y filas
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                throw new IllegalArgumentException("El archivo del mapa está vacío o mal formateado.");
            }
            numCols = Integer.parseInt(line);
    
            line = br.readLine();
            if (line == null || line.isEmpty()) {
                throw new IllegalArgumentException("El archivo del mapa está incompleto.");
            }
            numRows = Integer.parseInt(line);
    
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;
    
            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;
    
            String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                line = br.readLine();
                if (line == null || line.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                        "El archivo del mapa tiene menos filas de las especificadas."
                    );
                }
                String[] tokens = line.split(delims);
                if (tokens.length != numCols) {
                    throw new IllegalArgumentException(
                        "La fila " + row + " del mapa no coincide con el número de columnas especificado."
                    );
                }
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, "Error al cargar el mapa: " + e.getMessage());
        }
    }
    

    public int getTileSize() {
        return tileSize;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    public boolean isShaking() {
        return shaking;
    }

    public void setTween(double d) {
        tween = d;
    }

    public void setShaking(boolean b, int i) {
        shaking = b;
        intensity = i;
    }

    public void setBounds(int i1, int i2, int i3, int i4) {
        xmin = GamePanel.WIDTH - i1;
        ymin = GamePanel.WIDTH - i2;
        xmax = i3;
        ymax = i4;
    }

    public void setPosition(double x, double y) {

        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;

    }

    public void fixBounds() {
        if (x < xmin)
            x = xmin;
        if (y < ymin)
            y = ymin;
        if (x > xmax)
            x = xmax;
        if (y > ymax)
            y = ymax;
    }

    public void update() {
        if (shaking) {
            this.x += SECURE_RANDOM.nextDouble() * intensity - intensity / 2.0;
            this.y += SECURE_RANDOM.nextDouble() * intensity - intensity / 2.0;
        }
    }

    public void draw(Graphics2D g) {

        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

            if (row >= numRows)
                break;

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {

                if (col >= numCols)
                    break;
                if (map[row][col] != 0) {

                    int rc = map[row][col];
                    int r = rc / numTilesAcross;
                    int c = rc % numTilesAcross;

                    g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize, (int) y + row * tileSize, null);
                }

            }

        }

    }

}
