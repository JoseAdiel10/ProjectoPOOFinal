package Visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import Logico.Candidatos;
import Logico.CentroEmpleador;
import Logico.Obrero;
import Logico.Persona;
import Logico.Tecnico;
import Logico.Universitario;
import Logico.Usuario;

public class PanelRegistro extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;

    // Selector de tipo de cuenta
    private JComboBox<String> cmbTipoCuenta;
    private CardLayout cardTipoCuenta;
    private JPanel pnlTipoCuenta;

    // Credenciales comunes
    private JTextField txtUsuario, txtEmail;
    private JPasswordField txtClave, txtClaveConfirm;

    // --- Campos Empresa ---
    private JTextField txtRncEmp, txtNombreEmp, txtDireccionEmp;
    private JComboBox<String> cmbSectorEmp;
    private static final String[] SECTORES = {
            "Turismo", "Tecnologia", "Salud", "Comercio", "Educacion",
            "Agricultura", "Construccion", "Juridico", "Transporte"
    };

    // --- Campos Persona ---
    private JTextField txtNombrePer, txtCedulaPer, txtTelefonoPer, txtProvinciaPer;
    private JSpinner spnSalarioPer;
    private JCheckBox chkMudarsePer;
    private JComboBox<String> cmbSexoPer, cmbSubtipoPer;
    private CardLayout cardSubtipo;
    private JPanel pnlSubtipo;
    
    private JTextField txtPerfil, txtInteres;   // Candidato general
    private JTextField txtHabilidades;          // Obrero
    private JTextField txtTipoTecnico;          // Tecnico
    private JSpinner spnAnios;                  // Tecnico
    private JTextField txtCarrera;              // Universitario

    public PanelRegistro(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Principal.COLOR_FONDO);

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearCuerpo(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_PRIMARIO);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel titulo = new JLabel("Crear Cuenta Nueva");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.WEST);

        JLabel subtitulo = new JLabel("Registrate como empresa para publicar vacantes, o como candidato para buscar empleo");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(225, 235, 245));
        panel.add(subtitulo, BorderLayout.SOUTH);

        JButton btnVolver = new JButton("Ya tengo cuenta");
        btnVolver.setFocusPainted(false);
        btnVolver.addActionListener(e -> ventana.mostrarPanel("login"));
        panel.add(btnVolver, BorderLayout.EAST);
        return panel;
    }

    private JScrollPane crearCuerpo() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new javax.swing.BoxLayout(contenedor, javax.swing.BoxLayout.Y_AXIS));
        contenedor.setBackground(Principal.COLOR_FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(25, 120, 40, 120));

        contenedor.add(crearTarjeta("Tipo de cuenta", crearSelectorTipo()));
        contenedor.add(Box_espacio());
        contenedor.add(crearTarjeta("Datos de acceso", crearCredenciales()));
        contenedor.add(Box_espacio());

        cardTipoCuenta = new CardLayout();
        pnlTipoCuenta = new JPanel(cardTipoCuenta);
        pnlTipoCuenta.setOpaque(false);
        
        // Creamos los formularios
        crearInstanciasDeCampos();
        aplicarValidaciones(); // Aplica los KeyListeners
        
        pnlTipoCuenta.add(crearFormularioEmpresa(), "Empresa");
        pnlTipoCuenta.add(crearFormularioPersona(), "Candidato");
        contenedor.add(crearTarjeta("Datos del perfil", pnlTipoCuenta));
        contenedor.add(Box_espacio());

        JButton btnRegistrar = new JButton("Crear mi cuenta");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegistrar.setBackground(Principal.COLOR_ACENTO);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnRegistrar.setMaximumSize(new Dimension(300, 46));
        btnRegistrar.setPreferredSize(new Dimension(300, 46));
        btnRegistrar.addActionListener(e -> registrar());
        
        JPanel panelBtn = new JPanel();
        panelBtn.setOpaque(false);
        panelBtn.add(btnRegistrar);
        contenedor.add(panelBtn);

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }
    
    private void crearInstanciasDeCampos() {
        // Empresa
        txtRncEmp = new JTextField();
        txtNombreEmp = new JTextField();
        cmbSectorEmp = new JComboBox<>(SECTORES);
        txtDireccionEmp = new JTextField();
        
        // Persona Comunes
        txtNombrePer = new JTextField();
        txtCedulaPer = new JTextField();
        txtTelefonoPer = new JTextField();
        txtProvinciaPer = new JTextField();
        spnSalarioPer = new JSpinner(new SpinnerNumberModel(15000.0, 0.0, 1000000.0, 500.0));
        chkMudarsePer = new JCheckBox("Dispuesto a mudarse");
        chkMudarsePer.setOpaque(false);
        cmbSexoPer = new JComboBox<>(new String[] {"Masculino", "Femenino"});
        cmbSubtipoPer = new JComboBox<>(new String[] {"Candidato General", "Obrero", "Tecnico", "Universitario"});
        
        // Persona Especificos
        txtPerfil = new JTextField();
        txtInteres = new JTextField();
        txtHabilidades = new JTextField();
        txtTipoTecnico = new JTextField();
        spnAnios = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        txtCarrera = new JTextField();
    }

    private void aplicarValidaciones() {
        // Validador: Solo Numeros
        KeyAdapter soloNumeros = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) e.consume();
            }
        };

        // Validador: Solo Letras y Espacios
        KeyAdapter soloLetras = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) e.consume();
            }
        };

        // Aplicando validaciones a campos correspondientes
        txtRncEmp.addKeyListener(soloNumeros);
        txtCedulaPer.addKeyListener(soloNumeros);
        txtTelefonoPer.addKeyListener(soloNumeros);
        
        txtNombrePer.addKeyListener(soloLetras);
        txtCarrera.addKeyListener(soloLetras);
    }

    private JPanel Box_espacio() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(1, 15));
        p.setMaximumSize(new Dimension(5000, 15));
        return p;
    }

    private JPanel crearTarjeta(String titulo, JPanel contenido) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 10));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(5000, 5000));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 22, 18, 22)));

        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(Principal.COLOR_PRIMARIO);
        tarjeta.add(lbl, BorderLayout.NORTH);
        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    private JPanel crearSelectorTipo() {
        JPanel p = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        cmbTipoCuenta = new JComboBox<>(new String[] {"Candidato (busco empleo)", "Empresa (busco personal)"});
        cmbTipoCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTipoCuenta.addActionListener(e -> {
            String sel = (String) cmbTipoCuenta.getSelectedItem();
            if (sel != null && sel.startsWith("Empresa")) {
                cardTipoCuenta.show(pnlTipoCuenta, "Empresa");
            } else {
                cardTipoCuenta.show(pnlTipoCuenta, "Candidato");
            }
        });
        p.add(new JLabel("Quiero registrarme como:"));
        p.add(cmbTipoCuenta);
        return p;
    }

    private JPanel crearCredenciales() {
        JPanel p = new JPanel(new GridLayout(0, 2, 15, 10));
        p.setOpaque(false);
        txtUsuario = new JTextField();
        txtEmail = new JTextField();
        txtClave = new JPasswordField();
        txtClaveConfirm = new JPasswordField();
        p.add(campo("Nombre de usuario:", txtUsuario));
        p.add(campo("Correo electronico:", txtEmail));
        p.add(campo("Contrasena:", txtClave));
        p.add(campo("Confirmar contrasena:", txtClaveConfirm));
        return p;
    }

    private JPanel crearFormularioEmpresa() {
        JPanel p = new JPanel(new GridLayout(0, 2, 15, 10));
        p.setOpaque(false);
        p.add(campo("RNC (Solo numeros):", txtRncEmp));
        p.add(campo("Nombre de la empresa:", txtNombreEmp));
        p.add(campo("Sector:", cmbSectorEmp));
        p.add(campo("Direccion:", txtDireccionEmp));
        return p;
    }

    private JPanel crearFormularioPersona() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 12));
        contenedor.setOpaque(false);

        JPanel comunes = new JPanel(new GridLayout(0, 2, 15, 10));
        comunes.setOpaque(false);
        
        cmbSubtipoPer.addActionListener(e -> cardSubtipo.show(pnlSubtipo, (String) cmbSubtipoPer.getSelectedItem()));

        comunes.add(campo("Nombre completo:", txtNombrePer));
        comunes.add(campo("Cedula (Solo numeros):", txtCedulaPer));
        comunes.add(campo("Telefono (Solo numeros):", txtTelefonoPer));
        comunes.add(campo("Provincia:", txtProvinciaPer));
        comunes.add(campo("Sexo:", cmbSexoPer));
        comunes.add(campo("Salario esperado:", spnSalarioPer));
        comunes.add(campo("Perfil:", cmbSubtipoPer));
        comunes.add(chkMudarsePer);

        cardSubtipo = new CardLayout();
        pnlSubtipo = new JPanel(cardSubtipo);
        pnlSubtipo.setOpaque(false);

        JPanel pCandidato = new JPanel(new GridLayout(0, 2, 15, 10));
        pCandidato.setOpaque(false);
        pCandidato.add(campo("Perfil profesional:", txtPerfil));
        pCandidato.add(campo("Area de interes:", txtInteres));

        JPanel pObrero = new JPanel(new GridLayout(0, 2, 15, 10));
        pObrero.setOpaque(false);
        pObrero.add(campo("Habilidades manuales:", txtHabilidades));

        JPanel pTecnico = new JPanel(new GridLayout(0, 2, 15, 10));
        pTecnico.setOpaque(false);
        pTecnico.add(campo("Especialidad tecnica:", txtTipoTecnico));
        pTecnico.add(campo("Anios de experiencia:", spnAnios));

        JPanel pUniversitario = new JPanel(new GridLayout(0, 2, 15, 10));
        pUniversitario.setOpaque(false);
        pUniversitario.add(campo("Carrera Universitaria:", txtCarrera));

        pnlSubtipo.add(pCandidato, "Candidato General");
        pnlSubtipo.add(pObrero, "Obrero");
        pnlSubtipo.add(pTecnico, "Tecnico");
        pnlSubtipo.add(pUniversitario, "Universitario");

        contenedor.add(comunes, BorderLayout.NORTH);
        contenedor.add(pnlSubtipo, BorderLayout.CENTER);
        return contenedor;
    }

    private JPanel campo(String texto, java.awt.Component componente) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(100, 110, 120));
        p.add(lbl, BorderLayout.NORTH);
        p.add(componente, BorderLayout.CENTER);
        return p;
    }

    private void registrar() {
        String usuario = txtUsuario.getText().trim();
        String email = txtEmail.getText().trim();
        String clave = new String(txtClave.getPassword());
        String claveConfirm = new String(txtClaveConfirm.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario y la contrasena son obligatorios.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!clave.equals(claveConfirm)) {
            JOptionPane.showMessageDialog(this, "Las contrasenas no coinciden.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ventana.getBolsa().existeUsuario(usuario)) {
            JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya esta en uso.", "Usuario duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoCuenta = (String) cmbTipoCuenta.getSelectedItem();
        boolean esEmpresa = tipoCuenta != null && tipoCuenta.startsWith("Empresa");

        if (esEmpresa) {
            registrarComoEmpresa(usuario, email, clave);
        } else {
            registrarComoPersona(usuario, email, clave);
        }
    }

    private void registrarComoEmpresa(String usuario, String email, String clave) {
        String rnc = txtRncEmp.getText().trim();
        String nombre = txtNombreEmp.getText().trim();
        String direccion = txtDireccionEmp.getText().trim();

        if (rnc.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El RNC y el nombre de la empresa son obligatorios.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ventana.getBolsa().buscarEmpresaPorRnc(rnc) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una empresa registrada con ese RNC.", "Dato duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CentroEmpleador nuevaEmpresa = new CentroEmpleador(rnc, nombre, (String) cmbSectorEmp.getSelectedItem(), direccion);
        ventana.getBolsa().registrarEmpresa(nuevaEmpresa);

        Usuario nuevoUsuario = new Usuario(usuario, clave, "Empresa", email, rnc);
        ventana.getBolsa().registrarUsuario(nuevoUsuario);

        finalizarRegistro();
    }

    private void registrarComoPersona(String usuario, String email, String clave) {
        String nombre = txtNombrePer.getText().trim();
        String cedula = txtCedulaPer.getText().trim();

        if (nombre.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y la cedula son obligatorios.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (ventana.getBolsa().buscarPersonaPorCedula(cedula) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una persona registrada con esa cedula.", "Dato duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String subtipo = (String) cmbSubtipoPer.getSelectedItem();
        Persona nueva;
        
        if ("Obrero".equals(subtipo)) {
            Obrero o = new Obrero();
            o.setHabilidades(txtHabilidades.getText().trim());
            nueva = o;
        } else if ("Tecnico".equals(subtipo)) {
            Tecnico t = new Tecnico();
            t.setTipoDeTecnico(txtTipoTecnico.getText().trim());
            t.setAnoDeExperiencia(((Number) spnAnios.getValue()).intValue());
            nueva = t;
        } else if ("Universitario".equals(subtipo)) {
            Universitario u = new Universitario();
            u.setCarrera(txtCarrera.getText().trim());
            nueva = u;
        } else {
            Candidatos c = new Candidatos();
            c.setPerfilProfesional(txtPerfil.getText().trim());
            c.setAreaInteres(txtInteres.getText().trim());
            nueva = c;
        }

        nueva.setNombre(nombre);
        nueva.setCedula(cedula);
        nueva.setTelefono(txtTelefonoPer.getText().trim());
        nueva.setProvincia(txtProvinciaPer.getText().trim());
        nueva.setSalarioEsperado(((Number) spnSalarioPer.getValue()).doubleValue());
        nueva.setDispuestoAMudarse(chkMudarsePer.isSelected());
        nueva.setSexo((String) cmbSexoPer.getSelectedItem());

        if ("Obrero".equals(subtipo)) {
            ventana.getBolsa().registrarObrero((Obrero) nueva);
        } else if ("Tecnico".equals(subtipo)) {
            ventana.getBolsa().registrarTecnico((Tecnico) nueva);
        } else if ("Universitario".equals(subtipo)) {
            ventana.getBolsa().registrarUniversitario((Universitario) nueva);
        } else {
            ventana.getBolsa().registrarCandidato((Candidatos) nueva);
        }

        Usuario nuevoUsuario = new Usuario(usuario, clave, "Candidato", email, cedula);
        ventana.getBolsa().registrarUsuario(nuevoUsuario);

        finalizarRegistro();
    }

    private void finalizarRegistro() {
        JOptionPane.showMessageDialog(this,
                "Cuenta creada exitosamente. Ahora puedes iniciar sesion.",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
        limpiar();
        ventana.mostrarPanel("login");
    }

    private void limpiar() {
        txtUsuario.setText("");
        txtEmail.setText("");
        txtClave.setText("");
        txtClaveConfirm.setText("");
        txtRncEmp.setText("");
        txtNombreEmp.setText("");
        txtDireccionEmp.setText("");
        cmbSectorEmp.setSelectedIndex(0);
        txtNombrePer.setText("");
        txtCedulaPer.setText("");
        txtTelefonoPer.setText("");
        txtProvinciaPer.setText("");
        spnSalarioPer.setValue(15000.0);
        chkMudarsePer.setSelected(false);
        cmbSexoPer.setSelectedIndex(0);
        cmbSubtipoPer.setSelectedIndex(0);
        txtPerfil.setText("");
        txtInteres.setText("");
        txtHabilidades.setText("");
        txtTipoTecnico.setText("");
        spnAnios.setValue(0);
        txtCarrera.setText("");
        cmbTipoCuenta.setSelectedIndex(0);
        cardTipoCuenta.show(pnlTipoCuenta, "Candidato");
    }
}
