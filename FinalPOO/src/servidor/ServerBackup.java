package servidor;

import java.io.*;
import java.net.*;

public class ServerBackup {

    /**
     * Hace una copia de seguridad de TODOS los archivos .txt del sistema.
     */
    public static void respaldarSistemaCompleto() {
        // Esta es la lista de todos los archivos que maneja tu GestorPersistencia
        String[] archivosDelSistema = {
            "vacantes.txt", "candidatos.txt", "empresas.txt", 
            "personas.txt", "postulaciones.txt", "usuarios.txt"
        };

        System.out.println("Iniciando respaldo del sistema...");

        for (String nombreArchivo : archivosDelSistema) {
            File archivoLocal = new File(nombreArchivo);
            
            // Solo enviamos los archivos que realmente existen y tienen datos
            if (archivoLocal.exists()) {
                enviarArchivo(archivoLocal);
            }
        }
        System.out.println("Respaldo del sistema completado.");
    }

    /**
     * Envia un archivo fisico al Servidor copiandolo byte a byte.
     */
    private static void enviarArchivo(File archivo) {
        try (Socket socket = new Socket("127.0.0.1", 7000);
             DataOutputStream salidaRed = new DataOutputStream(socket.getOutputStream());
             DataInputStream lectorArchivo = new DataInputStream(new FileInputStream(archivo))) {

            // 1 Le decimos al servidor como se llama el archivo
            salidaRed.writeUTF(archivo.getName());

            // 2 Leemos el archivo local y lo enviamos por la red byte a byte
            int unByte;
            while ((unByte = lectorArchivo.read()) != -1) {
                salidaRed.write(unByte);
            }
            
            salidaRed.flush();
            
        } catch (Exception e) {
            System.out.println("Error al enviar " + archivo.getName() + ": " + e.getMessage());
        }
    }
}
