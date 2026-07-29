package excepciones;

/**
 * Excepcion personalizada que se lanza cuando se intenta eliminar
 * un registro que esta vinculado a otro y por tanto no puede borrarse.
 */
public class ExcepcionNoEliminable extends Exception {

    private static final long serialVersionUID = 1L;

    public ExcepcionNoEliminable(String mensaje) {
        super(mensaje);
    }
}