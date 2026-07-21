package Logico;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class Bolsa {
    
    // Lista global que almacena a todas las personas registradas en el sistema
    private ArrayList<Persona> lasPersonas; 
    
    // Lista global que almacena todas las vacantes publicadas
    private ArrayList<Vacantes> lasVacantes; 

    // Constructor de la Bolsa
    public Bolsa() {
        this.lasPersonas = new ArrayList<>();
        this.lasVacantes = new ArrayList<>();
    }
    
    
    public void guardarDatosEnFichero() {
        try {
            // Intenta abrir el archivo y volcar las listas completas en formato binario
            FileOutputStream fileOut = new FileOutputStream("datos_bolsa.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            // Guardamos por ejemplo la lista de vacantes
            out.writeObject(this.lasVacantes);
            
            out.close();
            fileOut.close();
            System.out.println("¡Datos guardados con éxito!");
            
        } catch (IOException e) {
            // Si algo falla (permisos, disco lleno, etc.), cae aquí y te avisa el error
            System.out.println("Ocurrió un error al intentar guardar el archivo.");
            e.printStackTrace();
        }
    }

    /**
     * Algoritmo de emparejamiento (Match) usando los getters de la Vacante.
     * @param vacante La oferta de trabajo que se desea evaluar.
     * @return Una lista con las personas que superan el porcentaje mínimo exigido.
     */
    public ArrayList<Persona> conectarCandidatos(Vacantes vacante) {
        
        ArrayList<Persona> candidatosAptos = new ArrayList<>();

        // Recorremos a todos los candidatos registrados
        for (Persona candidato : lasPersonas) {
            double puntajeTotal = 0.0;
            
            // --- CRITERIO 1: Provincia (Equivale a un 40% del match) ---
            // Comparamos usando el getter de la vacante y de la persona
            if (candidato.getProvincia().equalsIgnoreCase(vacante.getProvincia()) || 
                candidato.isDispMudar()) {
                puntajeTotal += 40.0;
            }

            // --- CRITERIO 2: Aspiración Salarial (Equivale a un 40% del match) ---
            // Verificamos si el salario que ofrece la vacante cubre la aspiración del candidato
            if (candidato.getAspSalarial() <= vacante.getSalario()) {
                puntajeTotal += 40.0;
            }

            // --- CRITERIO 3: Sexo (Equivale a un 20% del match) ---
            // Validamos contra el requerimiento de la vacante
            if (vacante.getSexo().equalsIgnoreCase("Indistinto") || 
                vacante.getSexo().equalsIgnoreCase(candidato.getSexo())) {
                puntajeTotal += 20.0;
            }

            // --- EVALUACIÓN FINAL ---
            // Comparamos el puntaje acumulado con el mínimo que exige la vacante (getPorcientoDeCoincidencia)
            if (puntajeTotal >= vacante.getPorcientoDeCoincidencia()) {
                candidatosAptos.add(candidato);
            }
        }

        return candidatosAptos;
    }

    // Métodos para agregar elementos a la bolsa (necesarios para que funcione)
    public void agregarPersona(Persona p) {
        this.lasPersonas.add(p);
    }

    public void agregarVacante(Vacantes v) {
        this.lasVacantes.add(v);
    }

    public ArrayList<Persona> getLasPersonas() {
        return lasPersonas;
    }

    public ArrayList<Vacantes> getLasVacantes() {
        return lasVacantes;
    }
}