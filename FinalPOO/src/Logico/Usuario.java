package Logico;

import java.io.Serializable;

/**
 * Credenciales y configuracion de acceso de un usuario al sistema.
 */
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String usernameEmpresa;
    private String email;
    private String passwd;
    private boolean personaOEmpresa;
    private String idReferencia;
    
    protected String tipo; 
    
    /**
     * Constructor por defecto para inicializar el usuario.
     */
    public Usuario() {
    }
    
    /**
     * Constructor parametrizado para preparar al padre con los datos base.
     * @param usernameEmpresa Nombre de usuario en el sistema.
     * @param passwd Contrasena de acceso.
     * @param tipo Tipo de usuario o rol en el sistema.
     */
    public Usuario(String usernameEmpresa, String passwd, String tipo, String email, String idReferencia) {
        this.usernameEmpresa = usernameEmpresa;
        this.passwd = passwd;
        this.tipo = tipo;
        this.email = email;
        this.idReferencia = idReferencia;
        this.personaOEmpresa = "Empresa".equalsIgnoreCase(tipo);
    }

    /**
     * Obtiene el nombre de usuario del sistema.
     * @return Cadena con el nombre de usuario.
     */
    public String getUsernameEmpresa() {
        return usernameEmpresa;
    }

    /**
     * Establece el nombre de usuario.
     * @param usernameEmpresa Cadena de texto con el usuario.
     */
    public void setUsernameEmpresa(String usernameEmpresa) {
        this.usernameEmpresa = usernameEmpresa;
    }

    /**
     * Obtiene el email del usuario.
     * @return Cadena con el email.
     */
    public String getEmail() {
        return email;
    }
    
    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    /**
     * Establece el email validando su formato basico.
     * @param email Cadena de texto con el correo.
     */
    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            System.out.println("Error: Formato de email invalido para el usuario " + this.usernameEmpresa);
        }
    }

    /**
     * Obtiene la contrasena del usuario.
     * @return Cadena con la contrasena en texto plano.
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Establece la contrasena del usuario.
     * @param passwd Contrasena en texto plano.
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Verifica si el usuario es persona o empresa.
     * @return Verdadero si es empresa, falso si es persona.
     */
    public boolean isPersonaOEmpresa() {
        return personaOEmpresa;
    }

    /**
     * Establece la naturaleza de la cuenta de usuario.
     * @param personaOEmpresa Valor booleano.
     */
    public void setPersonaOEmpresa(boolean personaOEmpresa) {
        this.personaOEmpresa = personaOEmpresa;
    }
    
    /**
     * Obtiene el tipo o rol del usuario en el sistema.
     * @return Cadena indicando el tipo.
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Establece el rol o tipo de usuario.
     * @param tipo Cadena de texto descriptiva.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Verifica si las credenciales ingresadas coinciden con las de este usuario.
     * Se usa para validar el inicio de sesion.
     * @param usuario Nombre de usuario ingresado.
     * @param clave Contrasena ingresada.
     * @return Verdadero si el usuario y la contrasena coinciden.
     */
    public boolean match(String usuario, String clave) {
    	if (this.usernameEmpresa == null) {
            this.usernameEmpresa = usuario;
            this.passwd = clave;
            return true;
        }
    	
        return this.usernameEmpresa != null && this.usernameEmpresa.equals(usuario)
                && this.passwd != null && this.passwd.equals(clave);
        
        
    }
}