package Logico;

import java.util.List;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import excepciones.ExcepcionAutenticacion;
import excepciones.ExcepcionFormato;
import excepciones.ExcepcionNoEliminable;

/**
 * Clase principal que administra las operaciones de la plataforma de empleo.
 * Delega la carga y guardado de archivos a la clase GestorPersistencia.
 */
public class Bolsa {
    
	private List<Vacantes> vacantes;
    private List<Candidatos> candidatos;
    private List<CentroEmpleador> empresas;
    private List<Persona> listaPersona;
    private List<Postulacion> registroPostulaciones;
    private List<Usuario> usuarios;

    /* Contadores en memoria para generar IDs unicos. Se recalculan al cargar. */
    private int contadorVacante;
    private int contadorPostulacion;

    // Archivos configurados como .txt
    private final String ARCHIVO_CANDIDATOS = "candidatos.txt";
    private final String ARCHIVO_EMPRESAS = "empresas.txt";
    private final String ARCHIVO_VACANTES = "vacantes.txt";
    private final String ARCHIVO_POSTULACIONES = "postulaciones.txt";
    private final String ARCHIVO_PERSONAS = "personas.txt";
    private final String ARCHIVO_USUARIOS = "usuarios.txt";

    /**
     * Constructor que inicializa las matrices y carga los datos serializados existentes.
     */
    public Bolsa() {
        this.vacantes = new ArrayList<>();
        this.candidatos = new ArrayList<>();
        this.empresas = new ArrayList<>();
        this.listaPersona = new ArrayList<>();
        this.registroPostulaciones = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.cargarEstadoSistema();
        this.actualizarContadores();
        this.verificarUsuarioPorDefecto();
    }
    
    /**
     * Crea un usuario administrador por defecto si el sistema arranca sin usuarios.
     * Usuario: admin / Contrasena: admin
     */
    private void verificarUsuarioPorDefecto() {
        if (this.usuarios.isEmpty()) {
            Usuario admin = new Usuario("admin", "admin", "Admin");
            this.usuarios.add(admin);
            GestorPersistencia.guardarDatos(ARCHIVO_USUARIOS, this.usuarios);
        }
    }

    /**
     * Valida las credenciales ingresadas contra la lista de usuarios registrados.
     * @param nombreUsuario Nombre de usuario ingresado en el login.
     * @param clave Contrasena ingresada en el login.
     * @return El Usuario correspondiente si las credenciales son correctas.
     * @throws ExcepcionAutenticacion Si no existe coincidencia.
     */
    public Usuario iniciarSesion(String nombreUsuario, String clave) throws ExcepcionAutenticacion {
        for (Usuario u : this.usuarios) {
            if (u.match(nombreUsuario, clave)) {
                return u;
            }
        }
        throw new ExcepcionAutenticacion();
    }

    /**
     * Registra un nuevo usuario del sistema y serializa la lista.
     */
    public void registrarUsuario(Usuario nuevoUsuario) {
        if (nuevoUsuario != null) {
            this.usuarios.add(nuevoUsuario);
            GestorPersistencia.guardarDatos(ARCHIVO_USUARIOS, this.usuarios);
        }
    }

    /**
     * Recorre las listas cargadas y ajusta los contadores para que el
     * proximo ID generado nunca choque con uno ya existente.
     */
    private void actualizarContadores() {
        int maxVacante = 0;
        for (Vacantes v : this.vacantes) {
            if (v.getIdVacante() > maxVacante) {
                maxVacante = v.getIdVacante();
            }
        }
        this.contadorVacante = maxVacante + 1;

        int maxPostulacion = 0;
        for (Postulacion p : this.registroPostulaciones) {
            if (p.getIdPostulacion() > maxPostulacion) {
                maxPostulacion = p.getIdPostulacion();
            }
        }
        this.contadorPostulacion = maxPostulacion + 1;
    }

    /**
     * Genera un nuevo identificador unico para una vacante.
     * @return Entero disponible para asignar.
     */
    public int generarIdVacante() {
        return this.contadorVacante++;
    }

    /**
     * Genera un nuevo identificador unico para una postulacion.
     * @return Entero disponible para asignar.
     */
    public int generarIdPostulacion() {
        return this.contadorPostulacion++;
    }

    /**
     * Algoritmo de matcheo de alta precision que incluye Filtros Duros.
     * Evalua, puntua y devuelve una lista ordenada de mayor a menor compatibilidad.
     * @param ofertaLaboral Objeto de tipo Vacantes con los requisitos del puesto.
     * @return Matriz de personas compatibles ordenadas por coincidencia (Ranking).
     */
    public List<Persona> evaluarCompatibilidadCandidatos(Vacantes ofertaLaboral) {
        List<Persona> candidatosCompatibles = new ArrayList<>();
        Map<Persona, Double> registroDePuntajes = new HashMap<>(); // Guarda temporalmente los puntajes para poder ordenar

        for (Persona personaActual : this.listaPersona) {
            
            /* --- INICIO FILTROS DUROS --- */
            
            // 1. Filtro de Empleo: Si ya trabaja, se descarta.
            if (personaActual instanceof Obrero && ((Obrero) personaActual).isEmpleado()) continue;
            if (personaActual instanceof Universitario && ((Universitario) personaActual).isEmpleado()) continue;
            if (personaActual instanceof Tecnico && ((Tecnico) personaActual).isEmpleado()) continue;

            // 2. Filtro de Salario: Si la empresa ofrece menos de lo que el candidato exige, se descarta.
            if (ofertaLaboral.getSalario() > 0 && ofertaLaboral.getSalario() < personaActual.getSalarioEsperado()) {
                continue; 
            }

            // 3. Filtro de Provincia: Si son de provincias distintas y el candidato no quiere mudarse, se descarta.
            String provVacante = ofertaLaboral.getProvincia();
            String provPersona = personaActual.getProvincia();
            if (provVacante != null && !provVacante.isEmpty() && provPersona != null && !provPersona.isEmpty()) {
                if (!provVacante.equalsIgnoreCase(provPersona) && !personaActual.isDispuestoAMudarse()) {
                    continue; 
                }
            }
            
            /* --- FIN FILTROS DUROS --- */

            double nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_CERO;
            String tituloVacante = ofertaLaboral.getTitulo() != null ? ofertaLaboral.getTitulo().toLowerCase() : "";
            String descVacante = ofertaLaboral.getDescripcion() != null ? ofertaLaboral.getDescripcion().toLowerCase() : "";

            // Acumulacion granular de puntos segun el tipo de perfil
            if (personaActual instanceof Candidatos) {
                Candidatos perfilCandidato = (Candidatos) personaActual;
                String perfil = perfilCandidato.getPerfilProfesional() != null ? perfilCandidato.getPerfilProfesional().toLowerCase() : "";
                String interes = perfilCandidato.getAreaInteres() != null ? perfilCandidato.getAreaInteres().toLowerCase() : "";

                if (!perfil.isEmpty() && tituloVacante.equals(perfil)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!perfil.isEmpty() && tituloVacante.contains(perfil)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; 
                }

                if (!interes.isEmpty() && descVacante.contains(interes)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!interes.isEmpty() && tituloVacante.contains(interes)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_BAJO; 
                }
                
            } else if (personaActual instanceof Tecnico) {
                Tecnico perfilTecnico = (Tecnico) personaActual;
                String tipo = perfilTecnico.getTipoDeTecnico() != null ? perfilTecnico.getTipoDeTecnico().toLowerCase() : "";
                
                if (!tipo.isEmpty() && tituloVacante.equals(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!tipo.isEmpty() && tituloVacante.contains(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; 
                }
                
                if (!tipo.isEmpty() && descVacante.contains(tipo)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_SECUNDARIO; 
                }

                nivelDeCompatibilidad += (perfilTecnico.getAnoDeExperiencia() * ConstantesGlobales.PUNTAJE_POR_ANO_EXPERIENCIA);
                
            } else if (personaActual instanceof Universitario) {
                Universitario perfilUniv = (Universitario) personaActual;
                String carrera = perfilUniv.getCarrera() != null ? perfilUniv.getCarrera().toLowerCase() : "";
                
                if (!carrera.isEmpty() && tituloVacante.equals(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!carrera.isEmpty() && tituloVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; 
                }

                if (!carrera.isEmpty() && descVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!carrera.isEmpty() && tituloVacante.contains(carrera)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_MENCION_MINIMA; 
                }
                
            } else if (personaActual instanceof Obrero) {
                Obrero perfilObrero = (Obrero) personaActual;
                String habilidades = perfilObrero.getHabilidades() != null ? perfilObrero.getHabilidades().toLowerCase() : "";
                
                if (!habilidades.isEmpty() && tituloVacante.equals(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!habilidades.isEmpty() && tituloVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_PARCIAL_ALTO; 
                }

                if (!habilidades.isEmpty() && descVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_EXACTO_PRIMARIO; 
                } else if (!habilidades.isEmpty() && tituloVacante.contains(habilidades)) {
                    nivelDeCompatibilidad += ConstantesGlobales.PUNTAJE_MENCION_MINIMA; 
                }
            }

            // Tope maximo: Nadie puede ser compatible por encima del 100%
            if (nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO) {
                nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO;
            }

            // Verificacion final de coincidencia requerida y guardado temporal
            if (nivelDeCompatibilidad >= ofertaLaboral.getPorcientoDeCoincidencia() && nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_CERO) {
                candidatosCompatibles.add(personaActual);
                registroDePuntajes.put(personaActual, nivelDeCompatibilidad); // Asociamos la persona con su puntaje
            }
        }

        // --- SISTEMA DE ORDENAMIENTO (De mayor a menor) ---
        candidatosCompatibles.sort((persona1, persona2) -> Double.compare(registroDePuntajes.get(persona2), registroDePuntajes.get(persona1)));

        // --- IMPRESION DEL RANKING ---
        System.out.println("\n--- RANKING DE COMPATIBILIDAD PARA: " + (ofertaLaboral.getTitulo() != null ? ofertaLaboral.getTitulo().toUpperCase() : "VACANTE") + " ---");
        if (candidatosCompatibles.isEmpty()) {
            System.out.println("No se encontraron candidatos que cumplan con los requisitos minimos.");
        } else {
            for (int i = 0; i < candidatosCompatibles.size(); i++) {
                Persona p = candidatosCompatibles.get(i);
                System.out.println((i + 1) + ". [Match Exitoso] " + p.getNombre() + " - " + registroDePuntajes.get(p) + "% compatible.");
            }
        }

        return candidatosCompatibles;
    }

    /**
     * Publica una nueva oferta laboral y serializa la matriz delegando al Gestor.
     */
    public void publicarVacante(Vacantes nuevaVacante) {
        if (nuevaVacante != null) {
            this.vacantes.add(nuevaVacante);
            GestorPersistencia.guardarDatos(ARCHIVO_VACANTES, this.vacantes);
        }
    }

    /**
     * Registra un nuevo candidato y serializa.
     */
    public void registrarCandidato(Candidatos nuevoCandidato) {
        if (nuevoCandidato != null) {
            this.candidatos.add(nuevoCandidato);
            this.listaPersona.add(nuevoCandidato);
            GestorPersistencia.guardarDatos(ARCHIVO_CANDIDATOS, this.candidatos);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo obrero y serializa.
     */
    public void registrarObrero(Obrero nuevoObrero) {
        if (nuevoObrero != null) {
            this.listaPersona.add(nuevoObrero);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo universitario y serializa.
     */
    public void registrarUniversitario(Universitario nuevoUniversitario) {
        if (nuevoUniversitario != null) {
            this.listaPersona.add(nuevoUniversitario);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra un nuevo tecnico y serializa.
     */
    public void registrarTecnico(Tecnico nuevoTecnico) {
        if (nuevoTecnico != null) {
            this.listaPersona.add(nuevoTecnico);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    /**
     * Registra una nueva entidad empleadora y serializa.
     */
    public void registrarEmpresa(CentroEmpleador nuevaEmpresa) {
        if (nuevaEmpresa != null) {
            this.empresas.add(nuevaEmpresa);
            GestorPersistencia.guardarDatos(ARCHIVO_EMPRESAS, this.empresas);
        }
    }

    /**
     * Registra una nueva postulacion y serializa.
     */
    public void registrarPostulacion(Postulacion nuevaPostulacion) {
        if (nuevaPostulacion != null) {
            this.registroPostulaciones.add(nuevaPostulacion);
            GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
        }
    }

    /**
     * Consulta las postulaciones asociadas a un identificador de vacante.
     */
    public void verCandidatosPostulados(int idVacante) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() == idVacante) {
                System.out.println("Estado actual: " + postulacionActual.getEstado());
            }
        }
    }

    /**
     * Actualiza el estado de una postulacion especifica y guarda los cambios en el archivo.
     */
    public void evaluarPostulacion(int idPostulacion, String nuevoEstado) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() == idPostulacion) {
                postulacionActual.setEstado(nuevoEstado);
                GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
                break;
            }
        }
    }

    /**
     * Recupera todas las matrices previamente guardadas utilizando el Gestor de Persistencia.
     */
    @SuppressWarnings("unchecked")
    private void cargarEstadoSistema() {
        Object datosVacantes = GestorPersistencia.cargarDatos(ARCHIVO_VACANTES);
        if (datosVacantes != null) this.vacantes = (List<Vacantes>) datosVacantes;

        Object datosCandidatos = GestorPersistencia.cargarDatos(ARCHIVO_CANDIDATOS);
        if (datosCandidatos != null) this.candidatos = (List<Candidatos>) datosCandidatos;

        Object datosEmpresas = GestorPersistencia.cargarDatos(ARCHIVO_EMPRESAS);
        if (datosEmpresas != null) this.empresas = (List<CentroEmpleador>) datosEmpresas;

        Object datosPostulaciones = GestorPersistencia.cargarDatos(ARCHIVO_POSTULACIONES);
        if (datosPostulaciones != null) this.registroPostulaciones = (List<Postulacion>) datosPostulaciones;

        Object datosPersonas = GestorPersistencia.cargarDatos(ARCHIVO_PERSONAS);
        if (datosPersonas != null) this.listaPersona = (List<Persona>) datosPersonas;
    }

    //  GETTERS Y SETTERS DE LAS LISTAS 

    public List<Vacantes> getVacantes() { return vacantes; }
    public void setVacantes(List<Vacantes> vacantes) { this.vacantes = vacantes; }

    public List<Candidatos> getCandidatos() { return candidatos; }
    public void setCandidatos(List<Candidatos> candidatos) { this.candidatos = candidatos; }

    public List<CentroEmpleador> getEmpresas() { return empresas; }
    public void setEmpresas(List<CentroEmpleador> empresas) { this.empresas = empresas; }

    public List<Persona> getListaPersona() { return listaPersona; }
    public void setListaPersona(List<Persona> listaPersona) { this.listaPersona = listaPersona; }

    public List<Postulacion> getRegistroPostulaciones() { return registroPostulaciones; }
    public void setRegistroPostulaciones(List<Postulacion> registroPostulaciones) { this.registroPostulaciones = registroPostulaciones; }
}