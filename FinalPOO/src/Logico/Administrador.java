package Logico;

public class Administrador extends Usuario {
    
    private String codigoSeguridad;
    
    public Administrador(String username, String passwd, String codigoSeguridad) {
        // Esto ya no dar� error porque el padre ya sabe recibir (String, String, String)
        super(username, passwd, "Admin"); 
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }
}

