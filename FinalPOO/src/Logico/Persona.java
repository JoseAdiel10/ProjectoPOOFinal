package Logico;
import java.io.Serializable;

/**
 * Clase base que representa a una persona en el sistema.
 */
public class Persona implements Serializable
{
    protected String nombre;
    protected String cedula;
    protected String telefono;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor por defecto para inicializar una persona.
     */
    public Persona() {
    	
    	inicializarFicheroPersona(nombreArchivo);
    }

    protected File archivoDatos;
    
    
    protected void inicializarFicheroPersona(String nombreArchivo) {
        try {
            this.archivoDatos = new File(nombreArchivo);
            if (this.archivoDatos.createNewFile()) {
                System.out.println("Fichero creado para la entidad: " + nombreArchivo);
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar el fichero en la clase Persona.");
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene el nombre de la persona.
     * @return Cadena con el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     * @param nombre Cadena de texto con el nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la cedula de la persona.
     * @return Cadena con la cedula.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cedula de la persona.
     * @param cedula Cadena de texto con la cedula.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    /**
     * Obtiene el telefono de la persona.
     * @return Cadena con el telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el telefono de la persona.
     * @param telefono Cadena de texto con el telefono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}