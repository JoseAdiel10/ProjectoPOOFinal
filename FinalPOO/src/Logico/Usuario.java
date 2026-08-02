package Logico;

import java.io.Serializable;

/**
 * Credenciales y configuracion de acceso de un usuario al sistema.
 *
 * Roles posibles (tipo): "Admin", "Empresa", "Candidato".
 * Para usuarios de tipo Empresa, idReferencia guarda el RNC de su CentroEmpleador.
 * Para usuarios de tipo Candidato, idReferencia guarda la cedula de su Persona.
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String usernameEmpresa;
    private String email;
    private String passwd;
    private boolean personaOEmpresa;

    protected String tipo;

    /** Cedula (Candidato) o RNC (Empresa) del perfil vinculado a esta cuenta. Null para Admin. */
    private String idReferencia;

    public Usuario() {
    }

    public Usuario(String usernameEmpresa, String passwd, String tipo) {
        this.usernameEmpresa = usernameEmpresa;
        this.passwd = passwd;
        this.tipo = tipo;
    }

    /**
     * Constructor completo, usado al registrar cuentas de Empresa o Candidato
     * que quedan vinculadas a un perfil existente.
     */
    public Usuario(String usernameEmpresa, String passwd, String tipo, String email, String idReferencia) {
        this.usernameEmpresa = usernameEmpresa;
        this.passwd = passwd;
        this.tipo = tipo;
        this.email = email;
        this.idReferencia = idReferencia;
        this.personaOEmpresa = "Empresa".equalsIgnoreCase(tipo);
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
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            System.out.println("Error: Formato de email invalido para el usuario " + this.usernameEmpresa);
        }
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

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    /**
     * Verifica si las credenciales ingresadas coinciden con las de este usuario.
     */
    public boolean match(String usuario, String clave) {
        return this.usernameEmpresa != null && this.usernameEmpresa.equals(usuario)
                && this.passwd != null && this.passwd.equals(clave);
    }
}
