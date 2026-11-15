package ec.edu.espe.eventos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Clase principal que inicia la aplicacion Spring Boot
@SpringBootApplication
public class EventosApiApplication {

    public static void main(String[] args) {
        // Crea el contexto de Spring y levanta el servidor Tomcat embebido
        SpringApplication.run(EventosApiApplication.class, args);
    }
}
