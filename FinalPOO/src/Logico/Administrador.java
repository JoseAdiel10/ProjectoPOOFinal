package Logico;

/**
 * Clase que representa a un Administrador dentro del sistema.
 * Hereda de la clase base Usuario y añade propiedades específicas 
 * para gestionar permisos elevados, como un código de seguridad de validación.
 */
public class Administrador extends Usuario {
    
    private String codigoSeguridad;
    
    /*
     * super(): Invoca al constructor de la clase padre (Usuario).
     * Se le pasan el username y el password recibidos, pero el tercer parámetro (tipo/rol) 
     * se envía directamente como "Admin" para garantizar que nadie pueda crear 
     * un administrador con un rol equivocado.
     */
    public Administrador(String username, String passwd, String codigoSeguridad) {
      
        super(username, passwd, "Admin"); 
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
}

