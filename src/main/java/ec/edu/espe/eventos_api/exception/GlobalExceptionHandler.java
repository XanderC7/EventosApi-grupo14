package ec.edu.espe.eventos_api.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Manejador centralizado de excepciones para devolver respuestas HTTP coherentes
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja recursos no encontrados y responde con estado 404
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("status", HttpStatus.NOT_FOUND.value());
        respuesta.put("message", ex.getMessage());
        respuesta.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    // Maneja el error cuando se alcanza el cupo maximo del evento y responde con estado 409
    @ExceptionHandler(CupoMaximoAlcanzadoException.class)
    public ResponseEntity<Map<String, Object>> manejarCupoMaximo(CupoMaximoAlcanzadoException ex) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("status", HttpStatus.CONFLICT.value());
        respuesta.put("message", ex.getMessage());
        respuesta.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(respuesta, HttpStatus.CONFLICT);
    }

    // Maneja incumplimientos de reglas de negocio y responde con estado 400
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<Map<String, Object>> manejarReglaNegocio(ReglaNegocioException ex) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("message", ex.getMessage());
        respuesta.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier otro error general no previsto y responde con estado 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(Exception ex) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("message", "Error interno en el servidor: " + ex.getMessage());
        respuesta.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
