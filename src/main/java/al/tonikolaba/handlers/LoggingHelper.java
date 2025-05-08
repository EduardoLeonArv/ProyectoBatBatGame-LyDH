package al.tonikolaba.handlers;

import java.util.logging.Logger;

/**
 * Clase de utilidad para el manejo de logs en la aplicación.
 * Proporciona una instancia de {@link Logger} para registrar mensajes de log en toda la aplicación.
 * Es una clase no instanciable.
 */
public class LoggingHelper {

    /** Instancia estática de {@link Logger} utilizada para el registro de logs. */
    public static final Logger LOGGER = Logger.getLogger(LoggingHelper.class.getName());

    /**
     * Constructor privado para evitar la instanciación de esta clase de utilidad.
     * Lanza una excepción si se intenta instanciar.
     */
    private LoggingHelper() {
        throw new IllegalStateException("Utility Class");
    }
}
