package ec.edu.espe.eventos_api.exception;

// Excepcion de negocio cuando se incumple una validacion o regla del sistema
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
