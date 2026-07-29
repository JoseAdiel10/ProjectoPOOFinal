package excepciones;

/**
 * Excepcion personalizada que se lanza cuando el usuario o la
 * contrasena ingresados en el login no coinciden con ningun registro.
 */
public class ExcepcionAutenticacion extends Exception {

    private static final long serialVersionUID = 1L;

    public ExcepcionAutenticacion() {
        super("Usuario o contrasena incorrectos.");
    }

    public ExcepcionAutenticacion(String mensaje) {
        super(mensaje);
    }
}