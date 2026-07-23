package Logico;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Clase principal que administra las operaciones de la plataforma de empleo integrando serializacion de objetos.
 */
public class Bolsa {
    
    private List<Vacantes> vacantes;
    private List<Candidatos> candidatos;
    private List<CentroEmpleador> empresas;
    private List<Persona> listaPersona;
    private List<Postulacion> registroPostulaciones;

    private final String ARCHIVO_CANDIDATOS = "candidatos.dat";
    private final String ARCHIVO_EMPRESAS = "empresas.dat";
    private final String ARCHIVO_VACANTES = "vacantes.dat";
    private final String ARCHIVO_POSTULACIONES = "postulaciones.dat";
    private final String ARCHIVO_PERSONAS = "personas.dat";

    /**
     * Constructor que inicializa las matrices y carga los datos serializados existentes.
     */
    public Bolsa() {
        this.vacantes = new ArrayList<>();
        this.candidatos = new ArrayList<>();
        this.empresas = new ArrayList<>();
        this.listaPersona = new ArrayList<>();
        this.registroPostulaciones = new ArrayList<>();
        this.cargarEstadoSistema();
    }

    /**
     * Algoritmo de matcheo de alta precision que evalua la compatibilidad porcentual de una persona con una vacante.
     * Utiliza un sistema granular que acumula puntos hasta un limite de 100% (empleado perfecto).
     * @param ofertaLaboral Objeto de tipo Vacantes con los requisitos del puesto a evaluar.
     * @return Matriz dinamica de personas que cumplen con el indice de coincidencia de la vacante.
     */
    public List<Persona> evaluarCompatibilidadCandidatos(Vacantes ofertaLaboral) {
        List<Persona> candidatosCompatibles = new ArrayList<>();

        for (Persona personaActual : this.listaPersona) {
            boolean tieneEmpleoActivo = false;
            double nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_CERO;

            // 1. Filtro de disponibilidad: Si ya trabaja, se descarta.
            if (personaActual instanceof Obrero) {
                tieneEmpleoActivo = ((Obrero) personaActual).isEmpleado();
            } else if (personaActual instanceof Universitario) {
                tieneEmpleoActivo = ((Universitario) personaActual).isEmpleado();
            } else if (personaActual instanceof Tecnico) {
                tieneEmpleoActivo = ((Tecnico) personaActual).isEmpleado();
            }

            if (tieneEmpleoActivo) {
                continue;
            }

            // Normalizacion de textos para evitar fallos por mayusculas
            String tituloVacante = ofertaLaboral.getTitulo() != null ? ofertaLaboral.getTitulo().toLowerCase() : "";
            String descVacante = ofertaLaboral.getDescripcion() != null ? ofertaLaboral.getDescripcion().toLowerCase() : "";

            // 2. Acumulacion granular de puntos segun el tipo de perfil
            if (personaActual instanceof Candidatos) {
                Candidatos perfilCandidato = (Candidatos) personaActual;
                String perfil = perfilCandidato.getPerfilProfesional() != null ? perfilCandidato.getPerfilProfesional().toLowerCase() : "";
                String interes = perfilCandidato.getAreaInteres() != null ? perfilCandidato.getAreaInteres().toLowerCase() : "";

                if (!perfil.isEmpty() && tituloVacante.equals(perfil)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!perfil.isEmpty() && tituloVacante.contains(perfil)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; // +25
                }

                if (!interes.isEmpty() && descVacante.contains(interes)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!interes.isEmpty() && tituloVacante.contains(interes)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_BAJO; // +10
                }
                
            } else if (personaActual instanceof Tecnico) {
                Tecnico perfilTecnico = (Tecnico) personaActual;
                String tipo = perfilTecnico.getTipoDeTecnico() != null ? perfilTecnico.getTipoDeTecnico().toLowerCase() : "";
                
                if (!tipo.isEmpty() && tituloVacante.equals(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!tipo.isEmpty() && tituloVacante.contains(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; // +25
                }
                
                if (!tipo.isEmpty() && descVacante.contains(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_SECUNDARIO; // +30
                }

                // Suma 1% por cada año de experiencia para rellenar el perfil
                nivelDeCompatibilidad += (perfilTecnico.getAnoDeExperiencia() * ConstantesGlobales.PUNTAJE_POR_ANO_EXPERIENCIA);
                
            } else if (personaActual instanceof Universitario) {
                Universitario perfilUniv = (Universitario) personaActual;
                String carrera = perfilUniv.getCarrera() != null ? perfilUniv.getCarrera().toLowerCase() : "";
                
                if (!carrera.isEmpty() && tituloVacante.equals(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!carrera.isEmpty() && tituloVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; // +25
                }

                if (!carrera.isEmpty() && descVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!carrera.isEmpty() && tituloVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_MENCION_MINIMA; // +5
                }
                
            } else if (personaActual instanceof Obrero) {
                Obrero perfilObrero = (Obrero) personaActual;
                String habilidades = perfilObrero.getHabilidades() != null ? perfilObrero.getHabilidades().toLowerCase() : "";
                
                if (!habilidades.isEmpty() && tituloVacante.equals(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!habilidades.isEmpty() && tituloVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; // +25
                }

                if (!habilidades.isEmpty() && descVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; // +50
                } else if (!habilidades.isEmpty() && tituloVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_MENCION_MINIMA; // +5
                }
            }

            // 3. Tope maximo: Nadie puede ser compatible por encima del 100%
            if (nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO) {
                nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO;
            }

            // 4. Verificacion final y registro
            if (nivelDeCompatibilidad >= ofertaLaboral.getPorcientoDeCoincidencia() && nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_CERO) {
                // Aqui se muestra visualmente el puntaje
                System.out.println("-> [Match Exitoso] El candidato " + personaActual.getNombre() + " es " + nivelDeCompatibilidad + "% compatible para el puesto.");
                candidatosCompatibles.add(personaActual);
            }
        }

        return candidatosCompatibles;
    }

    /**
     * Publica una nueva oferta laboral y serializa la matriz.
     * @param nuevaVacante Objeto de tipo Vacantes.
     */
    public void publicarVacante(Vacantes nuevaVacante) {
        if (nuevaVacante != null) {
            this.vacantes.add(nuevaVacante);
            this.serializarLista(ARCHIVO_VACANTES, this.vacantes);
        }
    }

    /**
     * Registra un nuevo candidato en las matrices del sistema y lo serializa.
     * @param nuevoCandidato Objeto de tipo Candidatos.
     */
    public void registrarCandidato(Candidatos nuevoCandidato) {
        if (nuevoCandidato != null) {
            this.candidatos.add(nuevoCandidato);
            this.listaPersona.add(nuevoCandidato);
            this.serializarLista(ARCHIVO_CANDIDATOS, this.candidatos);
            this.serializarLista(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo obrero en la matriz del sistema y lo serializa.
     * @param nuevoObrero Objeto de tipo Obrero.
     */
    public void registrarObrero(Obrero nuevoObrero) {
        if (nuevoObrero != null) {
            this.listaPersona.add(nuevoObrero);
            this.serializarLista(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo universitario en la matriz del sistema y lo serializa.
     * @param nuevoUniversitario Objeto de tipo Universitario.
     */
    public void registrarUniversitario(Universitario nuevoUniversitario) {
        if (nuevoUniversitario != null) {
            this.listaPersona.add(nuevoUniversitario);
            this.serializarLista(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo tecnico en la matriz del sistema y lo serializa.
     * @param nuevoTecnico Objeto de tipo Tecnico.
     */
    public void registrarTecnico(Tecnico nuevoTecnico) {
        if (nuevoTecnico != null) {
            this.listaPersona.add(nuevoTecnico);
            this.serializarLista(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra una nueva entidad empleadora y serializa la matriz.
     * @param nuevaEmpresa Objeto de tipo CentroEmpleador.
     */
    public void registrarEmpresa(CentroEmpleador nuevaEmpresa) {
        if (nuevaEmpresa != null) {
            this.empresas.add(nuevaEmpresa);
            this.serializarLista(ARCHIVO_EMPRESAS, this.empresas);
        }
    }

    /**
     * Registra una nueva postulacion y serializa la matriz.
     * @param nuevaPostulacion Objeto de tipo Postulacion.
     */
    public void registrarPostulacion(Postulacion nuevaPostulacion) {
        if (nuevaPostulacion != null) {
            this.registroPostulaciones.add(nuevaPostulacion);
            this.serializarLista(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
        }
    }

    /**
     * Consulta las postulaciones asociadas a un identificador de vacante.
     * @param idVacante Identificador numerico de la oferta laboral.
     */
    public void verCandidatosPostulados(int idVacante) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() == idVacante) {
                System.out.println("Estado actual: " + postulacionActual.getEstado());
            }
        }
    }

    /**
     * Actualiza el estado de una postulacion especifica y guarda los cambios en el archivo binario.
     * @param idPostulacion Identificador de la postulacion a evaluar.
     * @param nuevoEstado Cadena de texto con el veredicto.
     */
    public void evaluarPostulacion(int idPostulacion, String nuevoEstado) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() == idPostulacion) {
                postulacionActual.setEstado(nuevoEstado);
                this.serializarLista(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
                break;
            }
        }
    }

    /**
     * Metodo generico interno para guardar (serializar) cualquier matriz del sistema.
     * @param rutaFichero Ubicacion del archivo .dat
     * @param lista Matriz dinamica a guardar.
     */
    private void serializarLista(String rutaFichero, List<?> lista) {
        try (ObjectOutputStream flujoSalida = new ObjectOutputStream(new FileOutputStream(rutaFichero))) {
            flujoSalida.writeObject(lista);
        } catch (IOException excepcion) {
            System.out.println("Error al serializar los datos en: " + rutaFichero);
        }
    }

    /**
     * Recupera todas las matrices previamente guardadas en los archivos binarios.
     */
    @SuppressWarnings("unchecked")
    private void cargarEstadoSistema() {
        try {
            ObjectInputStream flujoEntrada;
            
            try {
                flujoEntrada = new ObjectInputStream(new FileInputStream(ARCHIVO_VACANTES));
                this.vacantes = (List<Vacantes>) flujoEntrada.readObject();
                flujoEntrada.close();
            } catch (Exception e) { /* Fichero no existe aun */ }
            
            try {
                flujoEntrada = new ObjectInputStream(new FileInputStream(ARCHIVO_CANDIDATOS));
                this.candidatos = (List<Candidatos>) flujoEntrada.readObject();
                flujoEntrada.close();
            } catch (Exception e) {}
            
            try {
                flujoEntrada = new ObjectInputStream(new FileInputStream(ARCHIVO_EMPRESAS));
                this.empresas = (List<CentroEmpleador>) flujoEntrada.readObject();
                flujoEntrada.close();
            } catch (Exception e) {}
            
            try {
                flujoEntrada = new ObjectInputStream(new FileInputStream(ARCHIVO_POSTULACIONES));
                this.registroPostulaciones = (List<Postulacion>) flujoEntrada.readObject();
                flujoEntrada.close();
            } catch (Exception e) {}
            
            try {
                flujoEntrada = new ObjectInputStream(new FileInputStream(ARCHIVO_PERSONAS));
                this.listaPersona = (List<Persona>) flujoEntrada.readObject();
                flujoEntrada.close();
            } catch (Exception e) {}

        } catch (Exception excepcionGeneral) {
            System.out.println("Error general restaurando el estado del sistema.");
        }
    }

    /**
     * Obtiene la matriz completa de vacantes registradas.
     * @return Matriz dinamica de objetos Vacantes.
     */
    public List<Vacantes> getVacantes() {
        return vacantes;
    }

    /**
     * Establece una nueva matriz dinamica de vacantes.
     * @param vacantes Estructura de matriz con vacantes.
     */
    public void setVacantes(List<Vacantes> vacantes) {
        this.vacantes = vacantes;
    }

    /**
     * Obtiene la matriz de candidatos del sistema.
     * @return Matriz dinamica de objetos Candidatos.
     */
    public List<Candidatos> getCandidatos() {
        return candidatos;
    }

    /**
     * Establece una nueva matriz dinamica de candidatos registrados.
     * @param candidatos Estructura de matriz con candidatos.
     */
    public void setCandidatos(List<Candidatos> candidatos) {
        this.candidatos = candidatos;
    }

    /**
     * Obtiene la matriz de centros empleadores o empresas.
     * @return Matriz dinamica de objetos CentroEmpleador.
     */
    public List<CentroEmpleador> getEmpresas() {
        return empresas;
    }

    /**
     * Establece una nueva matriz de centros empleadores.
     * @param empresas Estructura de matriz de empresas.
     */
    public void setEmpresas(List<CentroEmpleador> empresas) {
        this.empresas = empresas;
    }

    /**
     * Obtiene el registro global de personas en el sistema.
     * @return Matriz dinamica de objetos Persona.
     */
    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    /**
     * Establece un nuevo registro global de personas.
     * @param listaPersona Estructura de matriz de personas generales.
     */
    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    /**
     * Obtiene el registro global de postulaciones procesadas.
     * @return Matriz dinamica de objetos Postulacion.
     */
    public List<Postulacion> getRegistroPostulaciones() {
        return registroPostulaciones;
    }

    /**
     * Establece el registro de postulaciones del sistema.
     * @param registroPostulaciones Matriz de postulaciones.
     */
    public void setRegistroPostulaciones(List<Postulacion> registroPostulaciones) {
        this.registroPostulaciones = registroPostulaciones;
    }
}