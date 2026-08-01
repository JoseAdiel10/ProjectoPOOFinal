package servidor;

import java.io.*;
import java.net.*;

public class ServerBackup {

    /**
     * Hace una copia de seguridad de TODOS los archivos .txt del sistema.
     */
    public static void respaldarSistemaCompleto() {
        
        String[] archivosDelSistema = {
            "vacantes.txt", "candidatos.txt", "empresas.txt", 
            "personas.txt", "postulaciones.txt", "usuarios.txt"
        };

        System.out.println("Iniciando respaldo del sistema...");

        for (String nombreArchivo : archivosDelSistema) {
            File archivoLocal = new File(nombreArchivo);
            
           
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

            
            salidaRed.writeUTF(archivo.getName());

           
            int unByte;
            while ((unByte = lectorArchivo.read()) != -1) {
                salidaRed.write(unByte);
            }
            
            salidaRed.flush();
            
        } catch (Exception e) {
            System.out.println("Error al enviar " + archivo.getName() + ": " + e.getMessage());
        }
    }


public static void main(String[] args) {
    System.out.println("Iniciando herramienta de respaldo manual por Sockets...");
    respaldarSistemaCompleto();
}
}