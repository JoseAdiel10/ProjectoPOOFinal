package Logico;

/**
 * Representa una oferta de empleo publicada por un centro empleador.
 */
public class Vacantes {
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
    
    /**
     * Constructor por defecto para inicializar la vacante.
     */
    public Vacantes() {
        this.idVacante = ConstantesGlobales.VALOR_NUMERICO_CERO;
        this.salario = ConstantesGlobales.VALOR_DECIMAL_CERO;
        this.porcientoDeCoincidencia = ConstantesGlobales.VALOR_DECIMAL_CERO;
        this.cantidadDeHorasTrabajadas = ConstantesGlobales.VALOR_NUMERICO_CERO;
    }

    /**
     * Obtiene el identificador de la vacante.
     * @return Entero con el id.
     */
    public int getIdVacante() {
        return idVacante;
    }

    /**
     * Establece el identificador de la vacante.
     * @param idVacante Entero con el valor.
     */
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

    /**
     * Establece el titulo de la vacante.
     * @param titulo Cadena de texto descriptiva.
     */
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

    /**
     * Establece la descripcion de la vacante.
     * @param descripcion Cadena de texto detallada.
     */
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
     * Establece el salario ofertado en la vacante.
     * @param salario Monto decimal.
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * Obtiene el estado actual de la vacante.
     * @return Cadena con el estado.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la vacante.
     * @param estado Cadena de texto con el nuevo estado.
     */
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
     * Establece el porcentaje de coincidencia.
     * @param porcientoDeCoincidencia Valor decimal.
     */
    public void setPorcientoDeCoincidencia(double porcientoDeCoincidencia) {
        this.porcientoDeCoincidencia = porcientoDeCoincidencia;
    }

    /**
     * Obtiene el sexo requerido para la vacante.
     * @return Cadena con el sexo.
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Establece el sexo para la vacante.
     * @param sexo Cadena de texto.
     */
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

    /**
     * Establece la provincia de la oferta.
     * @param provincia Cadena de texto.
     */
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

    /**
     * Establece la cantidad de horas laborales.
     * @param cantidadDeHorasTrabajadas Entero numerico.
     */
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

    /**
     * Establece el requerimiento de mudanza.
     * @param dispuestoAMudarse Valor booleano.
     */
    public void setDispuestoAMudarse(boolean dispuestoAMudarse) {
        this.dispuestoAMudarse = dispuestoAMudarse;
    }
}
