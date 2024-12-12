/**
 * @mainpage BatBatGame - Documentación del Proyecto
 *
 * @section introduccion Introducción
 * Bienvenidos a la documentación de *BatBatGame*, un emocionante juego de lucha
 * que presenta acabar con multiples enemigos con 2 tipos de ataques disponibles.
 *
 * @section objetivos Objetivos del Proyecto
 * - Crear una experiencia inmersiva de juego similar al Mario Bros.
 * - Diseñar un sistema modular y escalable para la inclusión de nuevos personajes y habilidades.
 * - Aplicar principios de diseño orientado a objetos y patrones de diseño.
 * - Realiza la inclusion de dependencias mencionadas en clase asi como las pruebas
 *
 * @section tecnologia Tecnología usada
 * - Lenguaje de programación: *Java*.
 * - Librerías: *JFreeChart* para gráficos, entre otras.
 * - Gestión de dependencias: *Maven, **Jacoco, **pmd, **checkstyle, **JUnit, **mockito*.
 * - Herramientas de calidad: *SonarQube, **Doxygen*.
 *
 * @section autores Autores
 * Este proyecto fue desarrollado por:
 * - Eduardo León Arvelo
 * - Marcelo Daniel Choque Mamani
 * - Gabriel Albelo Fabelo
 * - Mikel Mugica Arregui
 *
 * @section licencia Licencia
 * Este proyecto es de uso educativo y no está destinado a fines comerciales.
 */

package al.tonikolaba.main;
import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "al.tonikolaba.main")
@EnableAutoConfiguration
public class BatBatSpring {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class)
                .headless(false)
                .run(args);

		EventQueue.invokeLater(() -> {
			@SuppressWarnings("unused")
			BatBatGame window = context.getBean(BatBatGame.class);
			window.setVisible(true);
		});
	}

}
