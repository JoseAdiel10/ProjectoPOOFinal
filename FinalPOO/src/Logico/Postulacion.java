package Logico;

import java.util.Date;

/**
 * Entidad transaccional que registra la aplicacion de un candidato.
 */

import java.io.Serializable;

public class Postulacion implements Serializable {
    private int idPostulacion;
    private Date fechaAplicacion;
    private String estado;

    /* Campos agregados: antes la postulacion no sabia quien aplico ni a que vacante */
    private Persona solicitante;
    private Vacantes vacante;
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor parametrizado que crea una postulacion completa,
     * lista para ser registrada en la Bolsa.
     * @param idPostulacion Identificador unico generado por la Bolsa.
     * @param solicitante Persona que se postula.
     * @param vacante Vacante a la que se postula.
     */
    public Postulacion(int idPostulacion, Persona solicitante, Vacantes vacante) {
        this.idPostulacion = idPostulacion;
        this.solicitante = solicitante;
        this.vacante = vacante;
        this.fechaAplicacion = new Date();
        this.estado = "Enviada";
    }

    /**
     * Obtiene el identificador de la postulacion.
     * @return Entero con el id.
     */
    public int getIdPostulacion() {
        return idPostulacion;
    }

    /**
     * Establece el identificador de la postulacion.
     * @param idPostulacion Numero entero unico.
     */
    public void setIdPostulacion(int idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    /**
     * Obtiene la fecha en la que se realizo la aplicacion.
     * @return Objeto Date con la fecha.
     */
    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    /**
     * Establece la fecha de la aplicacion.
     * @param fechaAplicacion Objeto tipo Date.
     */
    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    /**
     * Obtiene el estado del proceso de la postulacion.
     * @return Cadena con el estado actual.
     */
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la persona que realizo la postulacion.
     * @return Objeto Persona (o alguna de sus subclases).
     */
    public Persona getSolicitante() {
        return solicitante;
    }

    /**
     * Establece la persona que realiza la postulacion.
     * @param solicitante Objeto Persona.
     */
    public void setSolicitante(Persona solicitante) {
        this.solicitante = solicitante;
    }

    /**
     * Obtiene la vacante a la que corresponde esta postulacion.
     * @return Objeto Vacantes.
     */
    public Vacantes getVacante() {
        return vacante;
    }

    /**
     * Establece la vacante a la que corresponde esta postulacion.
     * @param vacante Objeto Vacantes.
     */
    public void setVacante(Vacantes vacante) {
        this.vacante = vacante;
    }
}
