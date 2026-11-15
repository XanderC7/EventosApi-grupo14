package ec.edu.espe.eventos_api.exception;

// Excepcion de negocio cuando un evento ya no tiene cupos disponibles
public class CupoMaximoAlcanzadoException extends RuntimeException {

    public CupoMaximoAlcanzadoException(String mensaje) {
        super(mensaje);
    }
}
