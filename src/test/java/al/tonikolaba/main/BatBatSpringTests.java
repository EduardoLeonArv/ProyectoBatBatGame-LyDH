package al.tonikolaba.main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author N.Kolaba
 *
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RunWith(JUnitPlatform.class)
@DisplayName("Spring Boot Load")
public class BatBatSpringTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Test Configurable Application Context")
	void testApplicationContext() {
		try (ConfigurableApplicationContext context = SpringApplication.run(BatBatSpring.class)) {
			assertNotNull(context.getEnvironment(), "El contexto debería cargar el entorno.");
			assertTrue(context.containsBean("batBatGame"), "El contexto debería contener el bean 'batBatGame'.");
		}
	}

}
