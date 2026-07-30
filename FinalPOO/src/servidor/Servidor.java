package servidor;

import java.io.*;
import java.net.*;

public class Servidor extends Thread {
    
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
                
                // 1  Leemos el nombre del archivo que viene en camino (ej. "vacantes.txt")
                String nombreArchivo = oos.readUTF();
                System.out.println("Recibiendo copia de seguridad de: " + nombreArchivo);
                
                // 2 Creamos el archivo local con ese nombre
                DataOutputStream escritor = new DataOutputStream(new FileOutputStream(new File("respaldo_" + nombreArchivo)));
                
                int unByte;
                try {
                    // 3 Copiamos el archivo byte por byte
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