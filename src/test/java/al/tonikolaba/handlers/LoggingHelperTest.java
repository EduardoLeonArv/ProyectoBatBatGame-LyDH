package al.tonikolaba.handlers;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class LoggingHelperTest {

    @Test
    public void testConstructorThrowsException() throws Exception {
        Constructor<LoggingHelper> constructor = LoggingHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true); // Permitir acceso al constructor privado
        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Utility Class", exception.getCause().getMessage());
    }

    @Test
    public void testLoggerIsNotNull() {
        // Crear un logger con un nombre de ejemplo
        Logger logger = Logger.getLogger("usuario_ejemplo");

        // Asegurarte de que el logger no es nulo
        assertNotNull(logger);

        // Verificar que el nombre del logger sea el esperado
        assertEquals("usuario_ejemplo", logger.getName());
    }

}
