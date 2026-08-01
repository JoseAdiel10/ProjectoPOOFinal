package Logico;

import java.io.File;
import java.io.Serializable;

/**
 * Clase base que representa a una persona en el sistema.
 */
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String nombre;
    protected String cedula;
    protected String telefono;
    protected String sexo;
    
    /* Nuevos atributos para los Filtros Duros (Mejora 3) */
    protected String provincia;
    protected double salarioEsperado;
    protected boolean dispuestoAMudarse;
    
    protected File archivoDatos;
    
    /**
     * Constructor por defecto para inicializar una persona.
     */
    public Persona() {
        this.salarioEsperado = ConstantesGlobales.VALOR_DECIMAL_CERO;
        inicializarFicheroPersona("registro_persona.txt");
    }

    /**
     * Crea un archivo de texto inicial para la entidad si no existe.
     * @param nombreArchivo Nombre del archivo a crear.
     */
    protected void inicializarFicheroPersona(String nombreArchivo) {
        try {
            this.archivoDatos = new File(nombreArchivo);
            this.archivoDatos.createNewFile();
        } catch (Exception excepcion) {}
    }
    
    /**
     * Obtiene el nombre de la persona.
     * @return Cadena con el nombre.
     */
    public String getNombre()
    { return nombre; }
    
    /**
     * Establece el nombre de la persona.
     * @param nombre Cadena con el nombre.
     */
    public void setNombre(String nombre) 
    { this.nombre = nombre; }
    
    /**
     * Obtiene la cedula de la persona.
     * @return Cadena con la cedula.
     */
    public String getCedula() { return cedula; }
    
    /**
     * Establece la cedula aplicando validacion estricta (Mejora 5).
     * @param cedula Cadena de texto. Debe tener 11 digitos tras limpiarla.
     */
    public void setCedula(String cedula) {
        
        this.cedula = cedula; 
    }
    
    /**
     * Obtiene la provincia de residencia.
     * @return Cadena con la provincia.
     */
    public String getProvincia() 
    { return provincia; }
    
    /**
     * Establece la provincia de residencia de la persona.
     * @param provincia Nombre de la provincia.
     */
    public void setProvincia(String provincia) 
    { this.provincia = provincia; }

    /**
     * Obtiene el salario minimo que la persona espera ganar.
     * @return Valor decimal del salario.
     */
    public double getSalarioEsperado() 
    { return salarioEsperado; }
    
    /**
     * Establece el salario esperado por la persona.
     * @param salarioEsperado Valor decimal.
     */
    public void setSalarioEsperado(double salarioEsperado) 
    { this.salarioEsperado = salarioEsperado; }

    /**
     * Verifica si la persona puede cambiar de provincia por trabajo.
     * @return Verdadero si puede mudarse.
     */
    public boolean isDispuestoAMudarse() 
    { return dispuestoAMudarse; }
    
    /**
     * Establece la disponibilidad de mudanza.
     * @param dispuestoAMudarse Valor booleano.
     */
    public void setDispuestoAMudarse(boolean dispuestoAMudarse)
    { this.dispuestoAMudarse = dispuestoAMudarse; }
    
    /**
     * Indica si la persona esta actualmente empleada.
     * Las subclases (Obrero, Tecnico, Universitario) sobrescriben este
     * comportamiento devolviendo su propio atributo "empleado".
     * @return Falso por defecto para una Persona generica.
     */
    public boolean isEmpleado() {
        return false;
    }

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	@Override
	public String toString() 
	{
	    return this.nombre; 
	    
	}
}
