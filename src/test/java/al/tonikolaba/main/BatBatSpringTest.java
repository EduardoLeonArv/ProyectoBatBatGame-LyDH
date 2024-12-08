package al.tonikolaba.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@DisplayName("Spring Boot Load Tests")
public class BatBatSpringTest {

    @Test
    @DisplayName("Application Context Loads")
    void contextLoads() {
        try (ConfigurableApplicationContext context = SpringApplication.run(BatBatSpring.class)) {
            assertNotNull(context, "El contexto de Spring debería cargarse correctamente.");
        } catch (Exception e) {
            fail("El contexto de Spring no debería lanzar excepciones.");
        }
    }

    @Test
    @DisplayName("Main Method Executes Without Exceptions")
    void testMainMethod() {
        try {
            BatBatSpring.main(new String[] {});
        } catch (Exception e) {
            fail("El método main no debería lanzar excepciones.");
        }
    }
}