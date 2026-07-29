package excepciones;

/**
 * Excepcion personalizada que se lanza cuando un dato ingresado
 * en un formulario no cumple con el formato o validacion esperada.
 */
public class ExcepcionFormato extends Exception {

    private static final long serialVersionUID = 1L;

    public ExcepcionFormato(String mensaje) {
        super(mensaje);
    }
}