package Logico;

import java.io.Serializable;

/**
 * Representa una oferta de empleo publicada por un centro empleador.
 */
public class Vacantes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int idVacante;
    private String titulo;
    private String descripcion;
    private double salario;
    private String estado;
    private double porcientoDeCoincidencia;
    private String sexo;
    private String provincia;
    private int cantidadDeHorasTrabajadas;
    private boolean dispuestoAMudarse;
    /* Campo agregado: antes la vacante no sabia que empresa la publico */
    private CentroEmpleador empleador;
    
    /**
     * Constructor por defecto para inicializar la vacante.
     */
    public Vacantes() {
        this.idVacante = ConstantesGlobales.VALOR_NUMERICO_CERO;
        this.salario = ConstantesGlobales.VALOR_DECIMAL_CERO;
        this.porcientoDeCoincidencia = ConstantesGlobales.PUNTAJE_CERO;
        this.cantidadDeHorasTrabajadas = ConstantesGlobales.VALOR_NUMERICO_CERO;
        this.estado = "Activa";
    }

    /**
     * Obtiene el identificador de la vacante.
     * @return Entero con el id.
     */
    public int getIdVacante() {
        return idVacante;
    }

    public void setIdVacante(int idVacante) {
        this.idVacante = idVacante;
    }

    /**
     * Obtiene el titulo de la vacante.
     * @return Cadena con el titulo.
     */
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripcion del cargo.
     * @return Cadena con la descripcion.
     */
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el salario ofertado.
     * @return Valor decimal con el salario.
     */
    public double getSalario() {
        return salario;
    }

    /**
     * Establece el salario ofertado validando que no sea negativo (Mejora 5).
     * @param salario Monto decimal.
     */
    public void setSalario(double salario) {
        if (salario >= 0) {
            this.salario = salario;
        } else {
            System.out.println("Error: El salario no puede ser un valor negativo.");
        }
    }

    /**
     * Obtiene el estado actual de la vacante.
     * @return Cadena con el estado.
     */
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el porcentaje de coincidencia requerido.
     * @return Valor decimal del porcentaje.
     */
    public double getPorcientoDeCoincidencia() {
        return porcientoDeCoincidencia;
    }

    /**
     * Establece el porcentaje de coincidencia validando que no sea negativo.
     * @param porcientoDeCoincidencia Valor decimal.
     */
    public void setPorcientoDeCoincidencia(double porcientoDeCoincidencia) {
        if (porcientoDeCoincidencia >= 0 && porcientoDeCoincidencia <= 100) {
            this.porcientoDeCoincidencia = porcientoDeCoincidencia;
        } else {
            System.out.println("Error: El porcentaje de coincidencia debe estar entre 0 y 100.");
        }
    }

    /**
     * Obtiene el sexo requerido para la vacante.
     * @return Cadena con el sexo.
     */
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene la provincia de la vacante.
     * @return Cadena con la ubicacion.
     */
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Obtiene la cantidad de horas a laborar.
     * @return Entero con las horas.
     */
    public int getCantidadDeHorasTrabajadas() {
        return cantidadDeHorasTrabajadas;
    }

    public void setCantidadDeHorasTrabajadas(int cantidadDeHorasTrabajadas) {
        this.cantidadDeHorasTrabajadas = cantidadDeHorasTrabajadas;
    }

    /**
     * Verifica si se requiere disponibilidad para mudar de domicilio.
     * @return Verdadero si es necesario, falso en caso contrario.
     */
    public boolean isDispuestoAMudarse() {
        return dispuestoAMudarse;
    }

    public void setDispuestoAMudarse(boolean dispuestoAMudarse) {
        this.dispuestoAMudarse = dispuestoAMudarse;
    }

    /**
     * Obtiene el centro empleador que publico esta vacante.
     * @return Objeto CentroEmpleador vinculado.
     */
    public CentroEmpleador getEmpleador() {
        return empleador;
    }

    /**
     * Vincula la vacante con el centro empleador que la publica.
     * @param empleador Objeto CentroEmpleador.
     */
    public void setEmpleador(CentroEmpleador empleador) {
        this.empleador = empleador;
    }
}
    