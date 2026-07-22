package Logico;

import java.io.Serializable;

/**
 * Representa a un universitario, heredando de la clase Persona.
 */
public class Universitario extends Persona implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String carrera;
    private boolean empleado;
    
    /**
     * Constructor por defecto para inicializar un universitario.
     */
    public Universitario() {
        super();
        this.inicializarFicheroPersona("universitarios_registrados.txt");
    }

    /**
     * Obtiene la carrera del universitario.
     * @return Cadena con la carrera.
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * Establece la carrera del universitario.
     * @param carrera Cadena con el nombre de la carrera.
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * Verifica si el universitario esta empleado.
     * @return Verdadero si esta empleado, falso en caso contrario.
     */
    public boolean isEmpleado() {
        return empleado;
    }

    /**
     * Establece el estado laboral del universitario.
     * @param empleado Valor booleano del estado.
     */
    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }
}