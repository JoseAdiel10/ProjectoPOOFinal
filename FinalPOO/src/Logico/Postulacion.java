package Logico;

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad transaccional que registra la aplicacion de un candidato.
 */
public class Postulacion implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String idPostulacion;
    private Date fechaAplicacion;
    private String estado;

    /* Campos agregados: antes la postulacion no sabia quien aplico ni a que vacante */
    private Persona solicitante;
    private Vacantes vacante;

    /**
     * Constructor parametrizado que crea una postulacion completa,
     * lista para ser registrada en la Bolsa.
     * @param idPostulacion Identificador unico generado por la Bolsa.
     * @param solicitante Persona que se postula.
     * @param vacante Vacante a la que se postula.
     */
    public Postulacion(String idPostulacion, Persona solicitante, Vacantes vacante) {
        this.idPostulacion = idPostulacion;
        this.solicitante = solicitante;
        this.vacante = vacante;
        this.fechaAplicacion = new Date();
        this.estado = "Enviada";
    }

    public String getIdPostulacion() {
        return idPostulacion;
    }

    public void setIdPostulacion(String idPostulacion) {
        this.idPostulacion = idPostulacion;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Persona getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Persona solicitante) {
        this.solicitante = solicitante;
    }

    public Vacantes getVacante() {
        return vacante;
    }

    public void setVacante(Vacantes vacante) {
        this.vacante = vacante;
    }

    @Override
    public String toString() {
        return idPostulacion; 
    }
}
