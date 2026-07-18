package Logico;

public class Usuario {
	
	private String usernameEmpresa;
    private String email;
    private String passwd;
    private boolean personaOEmpresa;
    
    public Usuario() {
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
