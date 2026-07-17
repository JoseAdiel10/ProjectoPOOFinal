package Logico;

/**
 * Representa a un candidato en la bolsa de trabajo, heredando de Persona.
 */
public class Candidatos extends Persona {
    private String perfilProfesional;
    private String areaInteres;

    /**
     * Constructor por defecto para inicializar un candidato.
     */
    public Candidatos() {
        super();
    }

    /**
     * Obtiene el perfil profesional del candidato.
     * @return Cadena con el perfil profesional.
     */
    public String getPerfilProfesional() {
        return perfilProfesional;
    }

    /**
     * Establece el perfil profesional del candidato.
     * @param perfilProfesional Cadena con los datos del perfil.
     */
    public void setPerfilProfesional(String perfilProfesional) {
        this.perfilProfesional = perfilProfesional;
    }

    /**
     * Obtiene el area de interes del candidato.
     * @return Cadena con el area de interes.
     */
    public String getAreaInteres() {
        return areaInteres;
    }

    /**
     * Establece el area de interes del candidato.
     * @param areaInteres Cadena con el sector de interes.
     */
    public void setAreaInteres(String areaInteres) {
        this.areaInteres = areaInteres;
    }
}
