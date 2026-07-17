package Logico;

/**
 * Representa a un obrero, heredando de la clase Persona.
 */
public class Obrero extends Persona {
    private String habilidades;
    private boolean empleado;
    
    /**
     * Constructor por defecto para inicializar un obrero.
     */
    public Obrero() {
        super();
    }

    /**
     * Obtiene las habilidades del obrero.
     * @return Cadena con las habilidades.
     */
    public String getHabilidades() {
        return habilidades;
    }

    /**
     * Establece las habilidades del obrero.
     * @param habilidades Cadena con el listado de habilidades.
     */
    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    /**
     * Verifica si el obrero esta empleado.
     * @return Verdadero si esta empleado, falso en caso contrario.
     */
    public boolean isEmpleado() {
        return empleado;
    }

    /**
     * Establece el estado laboral del obrero.
     * @param empleado Valor booleano del estado.
     */
    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }
}