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
 * Clase que representa un mapa de baldosas (TileMap) en el juego.
 * Permite cargar un conjunto de baldosas, administrar su posición,
 * y configurar efectos visuales como sacudidas.
 */
public class TileMap {

    /** Generador seguro de números aleatorios para efectos como sacudidas. */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /** Posición en el eje X del mapa. */
    private double x;
    /** Posición en el eje Y del mapa. */
    private double y;

    /** Límite mínimo en el eje X del mapa. */
    private int xmin;
    /** Límite mínimo en el eje Y del mapa. */
    private int ymin;
    /** Límite máximo en el eje X del mapa. */
    private int xmax;
    /** Límite máximo en el eje Y del mapa. */
    private int ymax;

    /** Factor de suavizado para el movimiento del mapa. */
    private double tween;

    /** Matriz que almacena los valores del mapa. */
    private int[][] map;
    /** Tamaño de cada baldosa en píxeles. */
    private int tileSize;
    /** Número de filas en el mapa. */
    private int numRows;
    /** Número de columnas en el mapa. */
    private int numCols;
    /** Ancho total del mapa en píxeles. */
    private int width;
    /** Altura total del mapa en píxeles. */
    private int height;

    /** Número de baldosas a lo largo del conjunto de baldosas. */
    private int numTilesAcross;
    /** Matriz de baldosas, donde se almacenan las imágenes y tipos. */
    private Tile[][] tiles;

    /** Desplazamiento de filas al dibujar el mapa. */
    private int rowOffset;
    /** Desplazamiento de columnas al dibujar el mapa. */
    private int colOffset;
    /** Número de filas que se deben dibujar. */
    private int numRowsToDraw;
    /** Número de columnas que se deben dibujar. */
    private int numColsToDraw;

    /** Indica si el mapa está en un estado de sacudida. */
    private boolean shaking;
    /** Intensidad del efecto de sacudida. */
    private int intensity;

    /**
     * Constructor de la clase TileMap.
     *
     * @param tileSize Tamaño de cada baldosa en píxeles.
     */
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

    /**
     * Carga el conjunto de baldosas desde un archivo de imagen.
     * Cada baldosa puede ser de tipo NORMAL o BLOCKED.
     *
     * @param s Ruta del archivo que contiene el conjunto de baldosas.
     */
    public void loadTiles(String s) {
        try {
            BufferedImage tileset;
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                // Cargar las baldosas del tipo NORMAL
                subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                // Cargar las baldosas del tipo BLOCKED
                subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }

        } catch (Exception e) {
            LoggingHelper.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }


    /**
     * Carga un mapa desde un archivo de texto y configura las dimensiones del mapa.
     *
     * @param s Ruta del archivo que contiene el mapa.
     */
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

    /**
     * Obtiene el tamaño de cada baldosa en píxeles.
     *
     * @return Tamaño de la baldosa.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Obtiene la posición X del mapa.
     *
     * @return Posición X del mapa.
     */
    public double getx() {
        return x;
    }

    /**
     * Obtiene la posición Y del mapa.
     *
     * @return Posición Y del mapa.
     */
    public double gety() {
        return y;
    }

    /**
     * Obtiene el ancho total del mapa en píxeles.
     *
     * @return Ancho del mapa.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Obtiene la altura total del mapa en píxeles.
     *
     * @return Altura del mapa.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtiene el número de filas en el mapa.
     *
     * @return Número de filas.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Obtiene el número de columnas en el mapa.
     *
     * @return Número de columnas.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Obtiene el tipo de baldosa en una posición específica.
     *
     * @param row Fila de la baldosa.
     * @param col Columna de la baldosa.
     * @return Tipo de la baldosa (por ejemplo, NORMAL o BLOCKED).
     */
    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getType();
    }

    /**
     * Verifica si el mapa está en estado de sacudida.
     *
     * @return {@code true} si el mapa está sacudiéndose, {@code false} en caso contrario.
     */
    public boolean isShaking() {
        return shaking;
    }

    /**
     * Establece el factor de suavizado para el movimiento del mapa.
     *
     * @param d Valor del factor de suavizado.
     */
    public void setTween(double d) {
        tween = d;
    }

    /**
     * Activa o desactiva el efecto de sacudida del mapa.
     *
     * @param b {@code true} para activar la sacudida, {@code false} para desactivarla.
     * @param i Intensidad de la sacudida.
     */
    public void setShaking(boolean b, int i) {
        shaking = b;
        intensity = i;
    }

    /**
     * Establece los límites del mapa.
     *
     * @param i1 Límite mínimo en el eje X.
     * @param i2 Límite mínimo en el eje Y.
     * @param i3 Límite máximo en el eje X.
     * @param i4 Límite máximo en el eje Y.
     */
    public void setBounds(int i1, int i2, int i3, int i4) {
        xmin = GamePanel.WIDTH - i1;
        ymin = GamePanel.WIDTH - i2;
        xmax = i3;
        ymax = i4;
    }

    /**
     * Establece la posición del mapa en la pantalla.
     * Calcula el desplazamiento de filas y columnas según la nueva posición.
     *
     * @param x Nueva posición en el eje X.
     * @param y Nueva posición en el eje Y.
     */
    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    /**
     * Ajusta los límites del mapa para evitar que se salga del área visible.
     */
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

    /**
     * Actualiza la posición del mapa. Si está activado el efecto de sacudida,
     * aplica desplazamientos aleatorios.
     */
    public void update() {
        if (shaking) {
            this.x += SECURE_RANDOM.nextDouble() * intensity - intensity / 2.0;
            this.y += SECURE_RANDOM.nextDouble() * intensity - intensity / 2.0;
        }
    }

    /**
     * Dibuja el mapa en pantalla.
     *
     * @param g Objeto Graphics2D utilizado para realizar el dibujo.
     */
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
