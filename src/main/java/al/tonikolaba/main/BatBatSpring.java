package al.tonikolaba.main;

import java.awt.EventQueue;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

/**
 * Clase principal de configuración y ejecución de la aplicación Spring Boot.
 * Configura componentes y beans necesarios para la ejecución segura y funcionalidad del juego.
 */
@Configuration
@ComponentScan(basePackages = "al.tonikolaba.main")
@EnableAutoConfiguration
public class BatBatSpring {

	/**
	 * Método principal que inicia la aplicación Spring Boot y lanza la interfaz gráfica del juego.
	 *
	 * @param args Argumentos pasados desde la línea de comandos.
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(BatBatSpring.class)
				.headless(false)
				.run(args);

		// Ejecuta la interfaz gráfica del juego en el Event Dispatch Thread (EDT).
		EventQueue.invokeLater(() -> {
			@SuppressWarnings("unused")
			BatBatGame window = context.getBean(BatBatGame.class);
			window.setVisible(true);
		});
	}

	/**
	 * Configura una instancia de {@link DocumentBuilderFactory} con características seguras
	 * para mitigar vulnerabilidades relacionadas con XML External Entities (XXE).
	 *
	 * @return Una instancia segura de {@link DocumentBuilderFactory}.
	 */
	@Bean
	public DocumentBuilderFactory documentBuilderFactory() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Bloquea acceso a DTDs externos
			factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // Bloquea acceso a esquemas externos
			return factory;
		} catch (Exception e) {
			throw new IllegalStateException("Error al configurar DocumentBuilderFactory de forma segura", e);
		}
	}

	/**
	 * Configura un bean de {@link ObjectMapper} de Jackson para la deserialización de JSON.
	 * Incluye características deshabilitadas para mayor seguridad.
	 *
	 * @return Una instancia de {@link ObjectMapper} configurada.
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// Deshabilita funciones inseguras
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		return mapper;
	}
}