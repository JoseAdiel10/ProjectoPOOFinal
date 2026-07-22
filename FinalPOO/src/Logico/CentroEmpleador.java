package Logico;

import java.io.Serializable;

/**
 * Entidad que representa un centro empleador o empresa.
 */
public class CentroEmpleador implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String rnc;
    private String nombre;
    private float reputacion;
    private String sector;
    private String direccion;
    public CentroEmpleador() {
        this.reputacion = ConstantesGlobales.VALOR_FLOTANTE_CERO;
    }

    /**
     * Constructor parametrizado para inicializar el centro empleador.
     * @param rnc Cadena de texto con el RNC.
     * @param nombre Cadena de texto con el nombre.
     * @param sector Cadena de texto con el sector.
     * @param direccion Cadena de texto con la direccion.
     */
    public CentroEmpleador(String rnc, String nombre, String sector, String direccion) {
        this.rnc = rnc;
        this.nombre = nombre;
        this.sector = sector;
        this.direccion = direccion;
        this.reputacion = ConstantesGlobales.VALOR_FLOTANTE_CERO;
    }

    /**
     * Obtiene el RNC del centro empleador.
     * @return Cadena con el RNC.
     */
    public String getRnc() {
        return rnc;
    }

    /**
     * Obtiene el nombre del centro empleador.
     * @return Cadena con el nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la reputacion del centro empleador.
     * @return Valor flotante con la reputacion.
     */
    public float getReputacion() {
        return reputacion;
    }

    /**
     * Obtiene el sector del centro empleador.
     * @return Cadena con el sector.
     */
    public String getSector() {
        return sector;
    }

    /**
     * Obtiene la direccion del centro empleador.
     * @return Cadena con la direccion.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece el RNC del centro empleador.
     * @param rnc Cadena de texto con el RNC.
     */
    public void setRnc(String rnc) {
        this.rnc = rnc;
    }

    /**
     * Establece el nombre del centro empleador.
     * @param nombre Cadena de texto con el nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la reputacion del centro empleador.
     * @param reputacion Valor flotante a asignar.
     */
    public void setReputacion(float reputacion) {
        this.reputacion = reputacion;
    }

    /**
     * Establece el sector del centro empleador.
     * @param sector Cadena de texto con el sector.
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * Establece la direccion del centro empleador.
     * @param direccion Cadena de texto con la direccion.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
