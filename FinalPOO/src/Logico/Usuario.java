package Logico;
import java.io.Serializable;

public class Usuario implements Serilizable{
	
	private String usernameEmpresa;
    private String email;
    private String passwd;
    private boolean personaOEmpresa;
    
    private static final long serialVersionUID = 1L;
    
    public Usuario() {
    }
    
    public Usuario(String usernameEmpresa, String email, String passwd, boolean personaOEmpresa) {
        this.usernameEmpresa = usernameEmpresa;
        this.email = email;
        this.passwd = passwd;
        this.personaOEmpresa = personaOEmpresa;
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

}
