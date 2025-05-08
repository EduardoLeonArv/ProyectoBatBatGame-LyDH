package al.tonikolaba.entity.enemies;

import al.tonikolaba.entity.Enemy;
import al.tonikolaba.tilemap.TileMap;
import al.tonikolaba.entity.Player;

/**
 * Esta clase representa un enemigo de tipo "Flyer" (volador).
 * El enemigo puede ser de diferentes tipos con distintos atributos, como la salud,
 * el daño, la velocidad de movimiento, entre otros.
 *
 * @author NdueKolaba
 */
public class Flyer extends Enemy {

    /** Arreglo de valores iniciales que definen las características de cada tipo de Flyer */
    private double[][] initValues = new double[][] {
            new double[] { 4, 30, 30, 20, 26, 1, 1.5, 0.15, 4.0, -5 },  // Valores para el tipo UFO
            new double[] { 1, 25, 25, 20, 18, 1, 0.8, 0.15, 4.0, -5 }    // Valores para el tipo XHEL_BAT
    };

    /**
     * Constructor que inicializa un objeto Flyer con las características de su tipo.
     *
     * @param tm El mapa de tiles en el que el enemigo está ubicado
     * @param type El tipo de Flyer (UFO o XHEL_BAT)
     * @param player El jugador que interactuará con el enemigo
     */
    public Flyer(TileMap tm, FlyerType type, Player player) {
        super(tm, player);  // Llama al constructor de la clase base Enemy

        // Asigna los valores de las características según el tipo de Flyer
        health = maxHealth = (int) initValues[type.value][0];
        width = (int) initValues[type.value][1];
        height = (int) initValues[type.value][2];
        cwidth = (int) initValues[type.value][3];
        cheight = (int) initValues[type.value][4];
        damage = (int) initValues[type.value][5];
        moveSpeed = initValues[type.value][6];
        fallSpeed = initValues[type.value][7];
        maxFallSpeed = initValues[type.value][8];
        jumpStart = initValues[type.value][9];
    }

    /**
     * Calcula la próxima posición del Flyer en el mapa en función de su movimiento.
     * El Flyer puede moverse hacia la izquierda, derecha, caer o saltar.
     */
    protected void getNextPosition() {
        if (left)
            dx = -moveSpeed;  // Si se mueve a la izquierda
        else if (right)
            dx = moveSpeed;   // Si se mueve a la derecha
        else
            dx = 0;  // Si no se mueve en el eje X

        if (falling) {
            dy += fallSpeed;  // Si está cayendo, se aplica la velocidad de caída
            if (dy > maxFallSpeed)
                dy = maxFallSpeed;  // Limita la velocidad máxima de caída
        }
        if (jumping && !falling) {
            dy = jumpStart;  // Si está saltando, se aplica la velocidad de salto
        }
    }

    /**
     * Enum que define los tipos posibles de Flyer, cada uno con diferentes características.
     */
    public enum FlyerType {
        UFO(0), XHEL_BAT(1);

        public final int value;

        /**
         * Constructor del enum FlyerType.
         *
         * @param val El valor asociado al tipo de Flyer
         */
        FlyerType(int val) {
            value = val;
        }
    }
}
