package al.tonikolaba.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnitPlatform.class)
@DisplayName("Spring Boot Load Tests")
public class BatBatSpringTest {
/*
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

    @Test
    @DisplayName("Test Bean Initialization")
    void testBeanInitialization() {
        try (ConfigurableApplicationContext context = SpringApplication.run(BatBatSpring.class)) {
            assertNotNull(context.getBean(BatBatGame.class), "El bean BatBatGame debería inicializarse correctamente.");
        }
    }
    @Test
    @DisplayName("Test Beans Initialization in Application Context")
    void testBeansInContext() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class)
                .headless(false)
                .run()) {

            assertNotNull(context.getBean("batBatGame"), "El bean 'batBatGame' debería inicializarse correctamente.");
            assertTrue(context.containsBean("batBatGame"), "El contexto debería contener el bean 'batBatGame'.");
        }
    }

    @Test
    @DisplayName("Test Application Context with Missing Bean")
    void testContextWithoutSpecificBean() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class)
                .headless(false)
                .run()) {

            assertFalse(context.containsBean("missingBean"), "El contexto no debería contener un bean inexistente.");
        }
    }

    @Test
    @DisplayName("Test Application Context Fails Gracefully")
    void testApplicationContextFailure() {
        try {
            // Proporciona una clase inválida para iniciar el contexto
            SpringApplication.run(Object.class, new String[] {});
            fail("El contexto debería fallar al intentar ejecutarse con una clase no válida.");
        } catch (Exception e) {
            assertNotNull(e, "Debería lanzarse una excepción al usar una clase no válida.");
        }
    }

*/
}