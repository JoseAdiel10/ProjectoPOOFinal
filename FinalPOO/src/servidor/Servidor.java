package servidor;

import java.io.*;

import java.net.*;
/**
 * Clase encargada de levantar un servidor TCP/IP en segundo plano para recibir
 * las copias de seguridad (archivos de texto) enviadas por el sistema cliente.
 * Extiende de Thread para permitir futuras implementaciones de hilos múltiples.
 */
public class Servidor extends Thread {
	/**
     * Punto de entrada de la aplicación del servidor.
     * Inicia un ServerSocket y se queda en un bucle infinito escuchando conexiones entrantes,
     * leyendo flujos de bytes y guardándolos como archivos locales de respaldo.
     */
    
    public static void main(String args[]) {
        ServerSocket sfd = null;
        try {
            sfd = new ServerSocket(7000);
            System.out.println("=== SERVIDOR DE RESPALDO ACTIVO ===");
            System.out.println("Esperando archivos del sistema...");
        } catch (IOException ioe) {
            System.out.println("Comunicacion rechazada." + ioe);
            System.exit(1);
        }

        while (true) {
            try {
                Socket nsfd = sfd.accept();
                DataInputStream oos = new DataInputStream(nsfd.getInputStream());
                
                
                String nombreArchivo = oos.readUTF();
                System.out.println("Recibiendo copia de seguridad de: " + nombreArchivo);
                
                
                DataOutputStream escritor = new DataOutputStream(new FileOutputStream(new File("respaldo_" + nombreArchivo)));
                
                int unByte;
                try {
                    
                    while ((unByte = oos.read()) != -1) {
                        escritor.write(unByte);
                    }
                    oos.close();
                    escritor.close();
                    System.out.println("-> Archivo " + nombreArchivo + " guardado con exito.");
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            } catch(IOException ioe) {
                System.out.println("Error: " + ioe);
            }
        }
    }
}