package Logico;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

/**
 * Clase utilitaria encargada de manejar la persistencia de datos del sistema de forma segura.
 */
public class GestorPersistencia {

    /**
     * Guarda cualquier objeto o matriz del sistema en un archivo.
     * Utiliza un bloque trywith resources para garantizar el cierre seguro de los flujos de memoria.
     * 
     * @param rutaFichero Nombre o ruta del archivo (ej. "vacantes.txt").
     * @param datos Objeto o estructura de datos a serializar y guardar.
     */
    public static void guardarDatos(String rutaFichero, Object datos) {
        try (ObjectOutputStream flujoSalida = new ObjectOutputStream(new FileOutputStream(rutaFichero))) {
            flujoSalida.writeObject(datos);
        } catch (IOException excepcion) {
            System.out.println("Error critico al serializar los datos en: " + rutaFichero + " - " + excepcion.getMessage());
        }
    }

    /**
     * Recupera la informacion guardada en un archivo de forma segura.
     * Si el archivo no existe o esta corrupto, captura el error sin detener la ejecucion del programa.
     * 
     * @param rutaFichero Nombre o ruta del archivo a leer.
     * @return El objeto leido (se debe castear al recibirlo), o nulo si el archivo no existe.
     */
    public static Object cargarDatos(String rutaFichero) {
        File archivo = new File(rutaFichero);
        
        // Verificacion preventiva: Si el archivo no existe aun, retornamos nulo antes de intentar abrir flujos.
        if (!archivo.exists()) {
            return null;
        }
        
        try (ObjectInputStream flujoEntrada = new ObjectInputStream(new FileInputStream(archivo))) {
            return flujoEntrada.readObject();
        } catch (Exception excepcion) {
            System.out.println("Advertencia: No se pudo cargar el archivo " + rutaFichero + ". Iniciando con datos vacios.");
            return null;
        }
    }
}