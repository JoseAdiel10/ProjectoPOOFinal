package Logico;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.LinkedHashMap;

import excepciones.ExcepcionAutenticacion;
import excepciones.ExcepcionFormato;
import excepciones.ExcepcionNoEliminable;

/**
 * Clase principal que administra las operaciones de la plataforma de empleo.
 * Controla listas, logica de negocio, matching y persistencia de datos.
 */
public class Bolsa {
    
    private List<Vacantes> vacantes;
    private List<Candidatos> candidatos;
    private List<CentroEmpleador> empresas;
    private List<Persona> listaPersona;
    private List<Postulacion> registroPostulaciones;
    private List<Usuario> usuarios;

    private int contadorVacante;
    private int contadorPostulacion;

    private final String ARCHIVO_CANDIDATOS = "candidatos.txt";
    private final String ARCHIVO_EMPRESAS = "empresas.txt";
    private final String ARCHIVO_VACANTES = "vacantes.txt";
    private final String ARCHIVO_POSTULACIONES = "postulaciones.txt";
    private final String ARCHIVO_PERSONAS = "personas.txt";
    private final String ARCHIVO_USUARIOS = "usuarios.txt";

    /**
     * Constructor principal. Inicializa listas, carga los archivos del disco, 
     * actualiza IDs y asegura que exista un administrador por defecto.
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

    /** Crea un administrador de emergencia si el archivo de usuarios esta vacio. */
    private void verificarUsuarioPorDefecto() {
        if (this.usuarios.isEmpty()) {
            Usuario admin = new Usuario("admin", "admin", "Admin");
            this.usuarios.add(admin);
            GestorPersistencia.guardarDatos(ARCHIVO_USUARIOS, this.usuarios);
        }
    }

    /**
     * Valida las credenciales de acceso.
     * @return El objeto Usuario si coincide.
     * @throws ExcepcionAutenticacion Si las credenciales son incorrectas.
     */
    public Usuario iniciarSesion(String nombreUsuario, String clave) throws ExcepcionAutenticacion {
        for (Usuario u : this.usuarios) {
            if (u.match(nombreUsuario, clave)) {
                return u;
            }
        }
        throw new ExcepcionAutenticacion();
    }

    /** Verifica si un nombre de usuario ya esta registrado en el sistema. */
    public boolean existeUsuario(String nombreUsuario) {
        if (nombreUsuario == null) return false;
        for (Usuario u : this.usuarios) {
            if (u.getUsernameEmpresa() != null && u.getUsernameEmpresa().equalsIgnoreCase(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    /** Guarda un nuevo usuario en la memoria y en el archivo de texto. */
    public void registrarUsuario(Usuario nuevoUsuario) {
        if (nuevoUsuario != null) {
            this.usuarios.add(nuevoUsuario);
            GestorPersistencia.guardarDatos(ARCHIVO_USUARIOS, this.usuarios);
        }
    }

    /** Lee los archivos cargados para continuar la secuencia de IDs sin sobrescribir datos. */
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
            if (p.getIdPostulacion() != null) {
                try {
                    int idActual = Integer.parseInt(p.getIdPostulacion());
                    if (idActual > maxPostulacion) {
                        maxPostulacion = idActual;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        this.contadorPostulacion = maxPostulacion + 1;
    }

    public int generarIdVacante() { return this.contadorVacante++; }
    public int generarIdPostulacion() { return this.contadorPostulacion++; }

    /**
     * Algoritmo principal de Matching.
     * Evalua a todas las personas y devuelve una lista ordenada con los mas aptos.
     * @param ofertaLaboral La vacante a evaluar.
     * @return Lista de candidatos compatibles ordenada por puntaje descendente.
     */
    public List<Persona> evaluarCompatibilidadCandidatos(Vacantes ofertaLaboral) {
        List<Persona> candidatosCompatibles = new ArrayList<>();
        Map<Persona, Double> registroDePuntajes = new HashMap<>();

        for (Persona personaActual : this.listaPersona) {
            
            if (personaActual.isEmpleado()) continue;

            if (ofertaLaboral.getSalario() > 0 && ofertaLaboral.getSalario() < personaActual.getSalarioEsperado()) {
                continue; 
            }

            String provVacante = ofertaLaboral.getProvincia();
            String provPersona = personaActual.getProvincia();
            if (provVacante != null && !provVacante.isEmpty() && provPersona != null && !provPersona.isEmpty()) {
                if (!provVacante.equalsIgnoreCase(provPersona) && !personaActual.isDispuestoAMudarse()) {
                    continue; 
                }
            }

            double nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_CERO;
            String tituloVacante = ofertaLaboral.getTitulo() != null ? ofertaLaboral.getTitulo().toLowerCase() : "";
            String descVacante = ofertaLaboral.getDescripcion() != null ? ofertaLaboral.getDescripcion().toLowerCase() : "";

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

            if (nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO) {
                nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO;
            }

            if (nivelDeCompatibilidad >= ofertaLaboral.getPorcientoDeCoincidencia() && nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_CERO) {
                candidatosCompatibles.add(personaActual);
                registroDePuntajes.put(personaActual, nivelDeCompatibilidad);
            }
        }

        // Ordena la lista resultante en base a los puntajes obtenidos (Mayor a Menor)
        candidatosCompatibles.sort((persona1, persona2) -> Double.compare(registroDePuntajes.get(persona2), registroDePuntajes.get(persona1)));

        return candidatosCompatibles;
    }

    /**
     * Calcula la compatibilidad exacta entre una persona y una vacante.
     * @return El porcentaje de compatibilidad (0.0 a 100.0).
     */
    public double calcularPuntajeIndividual(Persona personaActual, Vacantes ofertaLaboral) {
        if (personaActual.isEmpleado()) return 0.0;
        
        if (ofertaLaboral.getSalario() > 0 && ofertaLaboral.getSalario() < personaActual.getSalarioEsperado()) {
            return 0.0;
        }
        
        String provVacante = ofertaLaboral.getProvincia();
        String provPersona = personaActual.getProvincia();
        if (provVacante != null && !provVacante.trim().isEmpty() && provPersona != null && !provPersona.trim().isEmpty()) {
            if (!provVacante.trim().equalsIgnoreCase(provPersona.trim()) && !personaActual.isDispuestoAMudarse()) {
                return 0.0;
            }
        }

        double nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_CERO;
        String tituloVacante = ofertaLaboral.getTitulo() != null ? ofertaLaboral.getTitulo().trim().toLowerCase() : "";
        String descVacante = ofertaLaboral.getDescripcion() != null ? ofertaLaboral.getDescripcion().trim().toLowerCase() : "";

        if (personaActual instanceof Tecnico) {
            Tecnico perfilTecnico = (Tecnico) personaActual;
            String tipo = perfilTecnico.getTipoDeTecnico() != null ? perfilTecnico.getTipoDeTecnico().trim().toLowerCase() : "";
            
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
            String carrera = perfilUniv.getCarrera() != null ? perfilUniv.getCarrera().trim().toLowerCase() : "";
            
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
            String habilidades = perfilObrero.getHabilidades() != null ? perfilObrero.getHabilidades().trim().toLowerCase() : "";
            
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
            
        } else if (personaActual instanceof Candidatos) {
            Candidatos perfilCandidato = (Candidatos) personaActual;
            String perfil = perfilCandidato.getPerfilProfesional() != null ? perfilCandidato.getPerfilProfesional().trim().toLowerCase() : "";
            String interes = perfilCandidato.getAreaInteres() != null ? perfilCandidato.getAreaInteres().trim().toLowerCase() : "";

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
        }

        if (nivelDeCompatibilidad > ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO) {
            nivelDeCompatibilidad = ConstantesGlobales.PUNTAJE_MAXIMO_PERMITIDO;
        } else if (nivelDeCompatibilidad > 100.0) {
            nivelDeCompatibilidad = 100.0;
        }

        return nivelDeCompatibilidad;
    }

    // METODOS CRUD Y REGISTRO //

    public void publicarVacante(Vacantes nuevaVacante) {
        if (nuevaVacante != null) {
            this.vacantes.add(nuevaVacante);
            GestorPersistencia.guardarDatos(ARCHIVO_VACANTES, this.vacantes);
        }
    }

    public void registrarCandidato(Candidatos nuevoCandidato) {
        if (nuevoCandidato != null) {
            this.candidatos.add(nuevoCandidato);
            this.listaPersona.add(nuevoCandidato);
            GestorPersistencia.guardarDatos(ARCHIVO_CANDIDATOS, this.candidatos);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    public void registrarObrero(Obrero nuevoObrero) {
        if (nuevoObrero != null) {
            this.listaPersona.add(nuevoObrero);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    public void registrarUniversitario(Universitario nuevoUniversitario) {
        if (nuevoUniversitario != null) {
            this.listaPersona.add(nuevoUniversitario);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    public void registrarTecnico(Tecnico nuevoTecnico) {
        if (nuevoTecnico != null) {
            this.listaPersona.add(nuevoTecnico);
            GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        }
    }

    public void registrarEmpresa(CentroEmpleador nuevaEmpresa) {
        if (nuevaEmpresa != null) {
            this.empresas.add(nuevaEmpresa);
            GestorPersistencia.guardarDatos(ARCHIVO_EMPRESAS, this.empresas);
        }
    }

    public void registrarPostulacion(Postulacion nuevaPostulacion) {
        if (nuevaPostulacion != null) {
            this.registroPostulaciones.add(nuevaPostulacion);
            GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
        }
    }

    /**
     * Vincula a un candidato con una vacante si no se ha postulado previamente.
     * @throws ExcepcionFormato Si la persona ya aplico y no ha sido rechazada.
     */
    public void postularse(Persona persona, Vacantes vacante) throws ExcepcionFormato {
        for (Postulacion p : this.registroPostulaciones) {
            if (p.getSolicitante() != null && p.getSolicitante().equals(persona) 
                    && p.getVacante() != null && p.getVacante().equals(vacante)
                    && p.getEstado() != null && !p.getEstado().equals("Rechazada")) {
                throw new ExcepcionFormato("Esta persona ya se postulo a esta vacante.");
            }
        }
        
        String idGenerado = String.valueOf(generarIdPostulacion());
        Postulacion nueva = new Postulacion(idGenerado, persona, vacante);
        registrarPostulacion(nueva);
    }

    /** Cambia el estado a 'Contratado' y saca al candidato del mercado laboral. */
    public void contratarPostulacion(Postulacion postulacion) {
        postulacion.setEstado("Contratado");
        marcarEmpleado(postulacion.getSolicitante(), true);
        GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
        GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
    }

    /** Cambia el estado a 'Rechazada'. */
    public void rechazarPostulacion(Postulacion postulacion) {
        postulacion.setEstado("Rechazada");
        GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
    }

    /** Utilidad interna para cambiar el estado 'isEmpleado' en clases heredadas. */
    private void marcarEmpleado(Persona persona, boolean empleado) {
        if (persona instanceof Obrero) {
            ((Obrero) persona).setEmpleado(empleado);
        } else if (persona instanceof Tecnico) {
            ((Tecnico) persona).setEmpleado(empleado);
        } else if (persona instanceof Universitario) {
            ((Universitario) persona).setEmpleado(empleado);
        }
    }

    // METODOS DE BUSQUEDA //

    public Persona buscarPersonaPorCedula(String cedula) {
        for (Persona p : this.listaPersona) {
            if (p.getCedula() != null && p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public CentroEmpleador buscarEmpresaPorRnc(String rnc) {
        for (CentroEmpleador e : this.empresas) {
            if (e.getRnc() != null && e.getRnc().equals(rnc)) {
                return e;
            }
        }
        return null;
    }

    public Vacantes buscarVacantePorId(int id) {
        for (Vacantes v : this.vacantes) {
            if (v.getIdVacante() == id) {
                return v;
            }
        }
        return null;
    }

    public List<Postulacion> obtenerPostulacionesDeVacante(Vacantes vacante) {
        List<Postulacion> resultado = new ArrayList<>();
        for (Postulacion p : this.registroPostulaciones) {
            if (p.getVacante() != null && p.getVacante().equals(vacante)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // METODOS DE ELIMINACION CON INTEGRIDAD //

    public void eliminarVacante(Vacantes vacante) throws ExcepcionNoEliminable {
        if (!obtenerPostulacionesDeVacante(vacante).isEmpty()) {
            throw new ExcepcionNoEliminable("La vacante no puede eliminarse porque ya tiene postulaciones asociadas.");
        }
        this.vacantes.remove(vacante);
        GestorPersistencia.guardarDatos(ARCHIVO_VACANTES, this.vacantes);
    }

    public void eliminarEmpresa(CentroEmpleador empresa) throws ExcepcionNoEliminable {
        for (Vacantes v : this.vacantes) {
            if (v.getEmpleador() != null && v.getEmpleador().equals(empresa)) {
                throw new ExcepcionNoEliminable("La empresa no puede eliminarse porque tiene vacantes publicadas.");
            }
        }
        this.empresas.remove(empresa);
        GestorPersistencia.guardarDatos(ARCHIVO_EMPRESAS, this.empresas);
    }

    public void eliminarPersona(Persona persona) throws ExcepcionNoEliminable {
        for (Postulacion p : this.registroPostulaciones) {
            if (p.getSolicitante() != null && p.getSolicitante().equals(persona)) {
                throw new ExcepcionNoEliminable("La persona no puede eliminarse porque tiene postulaciones registradas.");
            }
        }
        this.listaPersona.remove(persona);
        this.candidatos.remove(persona);
        GestorPersistencia.guardarDatos(ARCHIVO_PERSONAS, this.listaPersona);
        GestorPersistencia.guardarDatos(ARCHIVO_CANDIDATOS, this.candidatos);
    }

    public void verCandidatosPostulados(int idVacante) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() != null && 
                postulacionActual.getIdPostulacion().equals(String.valueOf(idVacante))) {
                System.out.println("Estado actual: " + postulacionActual.getEstado());
            }
        }
    }

    public void evaluarPostulacion(int idPostulacion, String nuevoEstado) {
        for (Postulacion postulacionActual : this.registroPostulaciones) {
            if (postulacionActual.getIdPostulacion() != null && 
                postulacionActual.getIdPostulacion().equals(String.valueOf(idPostulacion))) {
                postulacionActual.setEstado(nuevoEstado);
                GestorPersistencia.guardarDatos(ARCHIVO_POSTULACIONES, this.registroPostulaciones);
                break;
            }
        }
    }

    /** Carga todos los datos persistidos en archivos hacia las listas de memoria RAM. */
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

        Object datosUsuarios = GestorPersistencia.cargarDatos(ARCHIVO_USUARIOS);
        if (datosUsuarios != null) this.usuarios = (List<Usuario>) datosUsuarios;
    }
    
    /**
     * Calcula estadisticas de contratacion por empresa.
     * @return Un mapa con las 3 empresas que mas postulantes han contratado, ordenadas de mayor a menor.
     */
    public Map<String, Integer> obtenerTop3EmpresasContratistas() {
        Map<String, Integer> conteoEmpresas = new HashMap<>();
        for (Postulacion p : this.registroPostulaciones) {
            if (p.getEstado() != null && p.getEstado().equalsIgnoreCase("Contratado")) {
                if (p.getVacante() != null && p.getVacante().getEmpleador() != null) {
                    String nombreEmpresa = p.getVacante().getEmpleador().getNombre();
                    
                    conteoEmpresas.put(nombreEmpresa, conteoEmpresas.getOrDefault(nombreEmpresa, 0) + 1);
                }
            }
        }

        return conteoEmpresas.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new 
                ));
    }
    
    // GETTERS Y SETTERS //

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

    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }
}