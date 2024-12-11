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
        Logger logger = LoggingHelper.LOGGER;
        assertNotNull(logger);
        assertEquals("al.tonikolaba.handlers.LoggingHelper", logger.getName());
    }
}
