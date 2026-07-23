package Logico;

/**
 * Macros definidas para sustituir cualquier numero literal implicito en el codigo.
 */
public class ConstantesGlobales {
    public static final int VALOR_NUMERICO_CERO = 0;
    public static final double VALOR_DECIMAL_CERO = 0.0;
    public static final float VALOR_FLOTANTE_CERO = 0.0f;
    public static final double PUNTAJE_CERO = 0.0;
    
    /* Escala de porcentajes para el algoritmo de matcheo (Maximo 100%) */
    public static final double PUNTAJE_MAXIMO_PERMITIDO = 100.0;
    public static final double PUNTAJE_EXACTO_PRIMARIO = 50.0;   // Coincidencia exacta de titulo
    public static final double PUNTAJE_EXACTO_SECUNDARIO = 30.0; // Coincidencia fuerte en descripcion
    public static final double PUNTAJE_PARCIAL_ALTO = 25.0;      // Coincidencia parcial de titulo
    public static final double PUNTAJE_PARCIAL_MEDIO = 20.0;     // Mencion fuerte
    public static final double PUNTAJE_PARCIAL_BAJO = 10.0;      // Mencion debil
    public static final double PUNTAJE_MENCION_MINIMA = 5.0;     // Palabra clave encontrada
    public static final double PUNTAJE_POR_ANO_EXPERIENCIA = 1.0; // 1% extra por cada ano
}