package Logico;

import java.io.Serializable;

/**
 * Representa a un tecnico, heredando de la clase Persona.
 */
public class Tecnico extends Persona implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int anoDeExperiencia;
    private String tipoDeTecnico;
    private boolean empleado;
    
    /**
     * Constructor por defecto para inicializar un tecnico.
     */
    public Tecnico() {
        super();
        this.inicializarFicheroPersona("tecnicos_registrados.txt");
        this.anoDeExperiencia = ConstantesGlobales.VALOR_NUMERICO_CERO;
    }

    /**
     * Obtiene los anos de experiencia del tecnico.
     * @return Entero con los anos de experiencia.
     */
    public int getAnoDeExperiencia() {
        return anoDeExperiencia;
    }

    /**
     * Establece los anos de experiencia del tecnico.
     * @param anoDeExperiencia Entero con la cantidad de anos.
     */
    public void setAnoDeExperiencia(int anoDeExperiencia) {
        this.anoDeExperiencia = anoDeExperiencia;
    }

    /**
     * Obtiene el tipo de tecnico.
     * @return Cadena con el tipo de especialidad.
     */
    public String getTipoDeTecnico() {
        return tipoDeTecnico;
    }

    /**
     * Establece el tipo de tecnico.
     * @param tipoDeTecnico Cadena con el tipo de tecnico.
     */
    public void setTipoDeTecnico(String tipoDeTecnico) {
        this.tipoDeTecnico = tipoDeTecnico;
    }

    /**
     * Verifica si el tecnico esta empleado.
     * @return Verdadero si esta empleado, falso en caso contrario.
     */
    public boolean isEmpleado() {
        return empleado;
    }

    /**
     * Establece el estado laboral del tecnico.
     * @param empleado Valor booleano del estado.
     */
    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }
}