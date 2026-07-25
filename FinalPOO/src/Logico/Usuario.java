package Logico;
import java.io.Serializable;

public class Usuario implements Serializable{
	
	private String usernameEmpresa;
    private String email;
    private String passwd;
    private boolean personaOEmpresa;
    
    private static final long serialVersionUID = 1L;
    
    public Usuario() {
    }
    
    protected String tipo; 
    

    // EL CONSTRUCTOR QUE FALTA: Prepara al padre para recibir los 3 Strings
    public Usuario(String usernameEmpresa, String passwd, String tipo) {
        this.usernameEmpresa = usernameEmpresa;
        this.passwd = passwd;
        this.tipo = tipo;
    }
    
    public String getUsernameEmpresa() {
        return usernameEmpresa;
    }

    public void setUsernameEmpresa(String usernameEmpresa) {
        this.usernameEmpresa = usernameEmpresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public boolean isPersonaOEmpresa() {
        return personaOEmpresa;
    }

    public void setPersonaOEmpresa(boolean personaOEmpresa) {
        this.personaOEmpresa = personaOEmpresa;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
