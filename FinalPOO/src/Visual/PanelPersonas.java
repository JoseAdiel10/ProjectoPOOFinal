package Visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import Logico.Candidatos;
import Logico.Obrero;
import Logico.Persona;
import Logico.Tecnico;
import Logico.Universitario;

public class PanelPersonas extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Persona seleccionada = null;

    // Campos comunes
    private JTextField txtNombre, txtCedula, txtTelefono, txtProvincia;
    private JSpinner spnSalario;
    private JCheckBox chkMudarse;
    private JComboBox<String> cmbSexo, cmbSubtipo;
    
    // El CardLayout magico y sus paneles
    private CardLayout cardSubtipo;
    private JPanel pnlSubtipo;
    
    // Campos dinamicos segun profesion
    private JTextField txtPerfil, txtInteres;   // Candidato general
    private JTextField txtHabilidades;          // Obrero
    private JTextField txtTipoTecnico;          // Tecnico
    private JSpinner spnAnios;                  // Tecnico
    private JTextField txtCarrera;              // Universitario

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelPersonas(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(15, 15));
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.WEST);
        add(crearTabla(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_FONDO);

        JLabel titulo = new JLabel("Gestion de Candidatos y Personal");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Principal.COLOR_PRIMARIO);

        JButton btnVolver = new JButton("Volver al Menu");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setBackground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> ventana.mostrarPanel("menu"));

        panel.add(titulo, BorderLayout.WEST);
        panel.add(btnVolver, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearFormulario() {
        JPanel contenedor = new JPanel(new BorderLayout(10, 10));
        contenedor.setBackground(Color.WHITE);
        contenedor.setPreferredSize(new Dimension(350, 0));
        contenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        JPanel pnlArriba = new JPanel(new BorderLayout(0, 10));
        pnlArriba.setBackground(Color.WHITE);

        JPanel camposComunes = new JPanel(new GridLayout(0, 1, 6, 8));
        camposComunes.setBackground(Color.WHITE);

        txtNombre = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();
        txtProvincia = new JTextField();
        spnSalario = new JSpinner(new SpinnerNumberModel(15000.0, 0.0, 1000000.0, 500.0));
        chkMudarse = new JCheckBox("Dispuesto a mudarse");
        chkMudarse.setBackground(Color.WHITE);
        cmbSexo = new JComboBox<>(new String[] {"Masculino", "Femenino"});
        
        cmbSubtipo = new JComboBox<>(new String[] {"Candidato General", "Obrero", "Tecnico", "Universitario"});
        cmbSubtipo.addActionListener(e -> cardSubtipo.show(pnlSubtipo, (String) cmbSubtipo.getSelectedItem()));

        // --- VALIDACIONES CON EVENTOS DE TECLADO ---
        // Nombre: Solo permite letras y espacios
        txtNombre.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    e.consume(); // Ignora el caracter si no es letra o espacio
                }
            }
        });
        
        // Cedula y Telefono: Solo permiten numeros
        KeyAdapter soloNumeros = new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignora el caracter si es una letra o simbolo
                }
            }
        };
        txtCedula.addKeyListener(soloNumeros);
        txtTelefono.addKeyListener(soloNumeros);
        // -------------------------------------------

        camposComunes.add(campo("Nombre completo:", txtNombre));
        camposComunes.add(campo("Cedula (Solo numeros):", txtCedula));
        camposComunes.add(campo("Telefono (Solo numeros):", txtTelefono));
        camposComunes.add(campo("Provincia:", txtProvincia));
        camposComunes.add(campo("Salario esperado:", spnSalario));
        camposComunes.add(campo("Sexo:", cmbSexo));
        camposComunes.add(campo("Tipo de Perfil:", cmbSubtipo));
        camposComunes.add(chkMudarse);

        // --- PANEL DINAMICO (CARDLAYOUT) ---
        cardSubtipo = new CardLayout();
        pnlSubtipo = new JPanel(cardSubtipo);
        pnlSubtipo.setBackground(Color.WHITE);

        JPanel pCandidato = new JPanel(new GridLayout(0, 1, 6, 8));
        pCandidato.setBackground(Color.WHITE);
        txtPerfil = new JTextField();
        txtInteres = new JTextField();
        pCandidato.add(campo("Perfil profesional:", txtPerfil));
        pCandidato.add(campo("Area de interes:", txtInteres));

        JPanel pObrero = new JPanel(new GridLayout(0, 1, 6, 8));
        pObrero.setBackground(Color.WHITE);
        txtHabilidades = new JTextField();
        pObrero.add(campo("Habilidades manuales:", txtHabilidades));

        JPanel pTecnico = new JPanel(new GridLayout(0, 1, 6, 8));
        pTecnico.setBackground(Color.WHITE);
        txtTipoTecnico = new JTextField();
        spnAnios = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        pTecnico.add(campo("Especialidad tecnica:", txtTipoTecnico));
        pTecnico.add(campo("Anios de experiencia:", spnAnios));

        JPanel pUniversitario = new JPanel(new GridLayout(0, 1, 6, 8));
        pUniversitario.setBackground(Color.WHITE);
        txtCarrera = new JTextField();
        
        // Evita numeros en la carrera universitaria
        txtCarrera.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar())) e.consume();
            }
        });
        pUniversitario.add(campo("Carrera Universitaria:", txtCarrera));

        pnlSubtipo.add(pCandidato, "Candidato General");
        pnlSubtipo.add(pObrero, "Obrero");
        pnlSubtipo.add(pTecnico, "Tecnico");
        pnlSubtipo.add(pUniversitario, "Universitario");

        pnlArriba.add(camposComunes, BorderLayout.NORTH);
        pnlArriba.add(pnlSubtipo, BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(1, 3, 8, 0));
        botones.setBackground(Color.WHITE);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(Principal.COLOR_PRIMARIO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardar());

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiar());

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 38, 38));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminar());

        botones.add(btnGuardar);
        botones.add(btnLimpiar);
        botones.add(btnEliminar);

        contenedor.add(new JScrollPane(pnlArriba), BorderLayout.CENTER);
        contenedor.add(botones, BorderLayout.SOUTH);
        return contenedor;
    }

    private JPanel campo(String texto, java.awt.Component comp) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(Principal.COLOR_MUTED);
        p.add(lbl, BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Cedula", "Nombre", "Telefono", "Provincia", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(30);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarSeleccion(tabla.getSelectedRow());
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        return scroll;
    }

    public void refrescar() {
        modeloTabla.setRowCount(0);
        for (Persona p : ventana.getBolsa().getListaPersona()) {
            String tipo = (p instanceof Obrero) ? "Obrero" :
                         (p instanceof Tecnico) ? "Tecnico" :
                         (p instanceof Universitario) ? "Universitario" : "Candidato General";
            modeloTabla.addRow(new Object[] {p.getCedula(), p.getNombre(), p.getTelefono(), p.getProvincia(), tipo});
        }
    }

    private void cargarSeleccion(int fila) {
        String cedula = (String) modeloTabla.getValueAt(fila, 0);
        seleccionada = ventana.getBolsa().buscarPersonaPorCedula(cedula);
        if (seleccionada == null) return;

        txtNombre.setText(seleccionada.getNombre());
        txtCedula.setText(seleccionada.getCedula());
        txtCedula.setEditable(false); // No permitir cambiar la cedula si esta editando
        txtTelefono.setText(seleccionada.getTelefono());
        txtProvincia.setText(seleccionada.getProvincia());
        spnSalario.setValue(seleccionada.getSalarioEsperado());
        chkMudarse.setSelected(seleccionada.isDispuestoAMudarse());
        cmbSexo.setSelectedItem(seleccionada.getSexo());

        // Cargar datos especificos e inhabilitar el combo de tipo
        cmbSubtipo.setEnabled(false); 
        
        if (seleccionada instanceof Universitario) {
            cmbSubtipo.setSelectedItem("Universitario");
            txtCarrera.setText(((Universitario) seleccionada).getCarrera());
        } else if (seleccionada instanceof Tecnico) {
            cmbSubtipo.setSelectedItem("Tecnico");
            txtTipoTecnico.setText(((Tecnico) seleccionada).getTipoDeTecnico());
            spnAnios.setValue(((Tecnico) seleccionada).getAnoDeExperiencia());
        } else if (seleccionada instanceof Obrero) {
            cmbSubtipo.setSelectedItem("Obrero");
            txtHabilidades.setText(((Obrero) seleccionada).getHabilidades());
        } else if (seleccionada instanceof Candidatos) {
            cmbSubtipo.setSelectedItem("Candidato General");
            txtPerfil.setText(((Candidatos) seleccionada).getPerfilProfesional());
            txtInteres.setText(((Candidatos) seleccionada).getAreaInteres());
        }
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y cedula son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (seleccionada == null) {
            if (ventana.getBolsa().buscarPersonaPorCedula(txtCedula.getText().trim()) != null) {
                JOptionPane.showMessageDialog(this, "Esa cedula ya esta registrada.");
                return;
            }

            String subtipo = (String) cmbSubtipo.getSelectedItem();
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

            nueva.setCedula(txtCedula.getText().trim());
            nueva.setNombre(txtNombre.getText().trim());
            nueva.setTelefono(txtTelefono.getText().trim());
            nueva.setProvincia(txtProvincia.getText().trim());
            nueva.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
            nueva.setDispuestoAMudarse(chkMudarse.isSelected());
            nueva.setSexo((String) cmbSexo.getSelectedItem());

            if (nueva instanceof Obrero) ventana.getBolsa().registrarObrero((Obrero) nueva);
            else if (nueva instanceof Tecnico) ventana.getBolsa().registrarTecnico((Tecnico) nueva);
            else if (nueva instanceof Universitario) ventana.getBolsa().registrarUniversitario((Universitario) nueva);
            else ventana.getBolsa().registrarCandidato((Candidatos) nueva);

            JOptionPane.showMessageDialog(this, "Personal registrado exitosamente.");
        } else {
            // Modo Edicion (Solo se actualizan los datos)
            seleccionada.setNombre(txtNombre.getText().trim());
            seleccionada.setTelefono(txtTelefono.getText().trim());
            seleccionada.setProvincia(txtProvincia.getText().trim());
            seleccionada.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
            seleccionada.setDispuestoAMudarse(chkMudarse.isSelected());
            seleccionada.setSexo((String) cmbSexo.getSelectedItem());

            if (seleccionada instanceof Universitario) {
                ((Universitario) seleccionada).setCarrera(txtCarrera.getText().trim());
            } else if (seleccionada instanceof Tecnico) {
                ((Tecnico) seleccionada).setTipoDeTecnico(txtTipoTecnico.getText().trim());
                ((Tecnico) seleccionada).setAnoDeExperiencia(((Number) spnAnios.getValue()).intValue());
            } else if (seleccionada instanceof Obrero) {
                ((Obrero) seleccionada).setHabilidades(txtHabilidades.getText().trim());
            } else if (seleccionada instanceof Candidatos) {
                ((Candidatos) seleccionada).setPerfilProfesional(txtPerfil.getText().trim());
                ((Candidatos) seleccionada).setAreaInteres(txtInteres.getText().trim());
            }

            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente.");
        }

        limpiar();
        refrescar();
    }

    private void eliminar() {
        if (seleccionada == null) return;
        ventana.getBolsa().getListaPersona().remove(seleccionada);
        limpiar();
        refrescar();
        JOptionPane.showMessageDialog(this, "Registro eliminado.");
    }

    private void limpiar() {
        seleccionada = null;
        txtNombre.setText("");
        txtCedula.setText("");
        txtCedula.setEditable(true);
        txtTelefono.setText("");
        txtProvincia.setText("");
        spnSalario.setValue(15000.0);
        chkMudarse.setSelected(false);
        cmbSexo.setSelectedIndex(0);
        
        cmbSubtipo.setEnabled(true);
        cmbSubtipo.setSelectedIndex(0);
        cardSubtipo.show(pnlSubtipo, "Candidato General");
        
        txtPerfil.setText("");
        txtInteres.setText("");
        txtHabilidades.setText("");
        txtTipoTecnico.setText("");
        spnAnios.setValue(0);
        txtCarrera.setText("");
        
        tabla.clearSelection();
    }
}
