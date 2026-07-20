package Logico;

import java.util.Date;

/**
 * Entidad transaccional que registra la aplicacion de un candidato.
 */
public class Postulacion {
    private int idPostulacion;
    private Date fechaAplicacion;
    private String estado;
    
    /**
     * Constructor por defecto para inicializar la postulacion.
     */
    public Postulacion() {
        this.idPostulacion = ConstantesGlobales.VALOR_NUMERICO_CERO;
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

    /**
     * Establece el estado de la postulacion.
     * @param estado Cadena de texto indicativa.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}