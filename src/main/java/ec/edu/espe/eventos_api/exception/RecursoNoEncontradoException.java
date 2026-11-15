package ec.edu.espe.eventos_api.exception;

// Excepcion personalizada cuando no se encuentra un registro en la base de datos
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
