package Visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import Logico.Candidatos;
import Logico.Obrero;
import Logico.Persona;
import Logico.Tecnico;
import Logico.Universitario;
import excepciones.ExcepcionNoEliminable;

public class PanelPersonas extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Persona personaSeleccionada = null;

    private JTextField txtNombre, txtCedula, txtTelefono, txtProvincia;
    private JSpinner spnSalario;
    private JCheckBox chkMudarse;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbSexo;

    private JPanel pnlEspecifico;
    private CardLayout cardEspecifico;

    private JTextField txtPerfil, txtInteres;
    private JTextField txtHabilidades;
    private JTextField txtTipoTecnico;
    private JSpinner spnAnios;
    private JTextField txtCarrera;

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    
    
    public PanelPersonas(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.WEST);
        add(crearTabla(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_FONDO);

        JLabel titulo = new JLabel("Gestion de Personas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Principal.COLOR_PRIMARIO);
        panel.add(titulo, BorderLayout.WEST);

        JButton btnVolver = new JButton("Volver al Menu");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("menu");
            }
        });
        panel.add(btnVolver, BorderLayout.EAST);
        return panel;
    }

    private JScrollPane crearFormulario() {
        JPanel contenedor = new JPanel(new BorderLayout(5, 5));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel comunes = new JPanel(new GridLayout(0, 1, 4, 6));
        comunes.setBackground(Color.WHITE);

        cmbTipo = new JComboBox<>(new String[] {"Candidato General", "Obrero", "Tecnico", "Universitario"});
        cmbTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardEspecifico.show(pnlEspecifico, (String) cmbTipo.getSelectedItem());
            }
        });

        cmbSexo = new JComboBox<>(new String[] {"Masculino", "Femenino"});
        cmbSexo.setBackground(Color.WHITE);

        txtNombre = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();
        txtProvincia = new JTextField();
        spnSalario = new JSpinner(new SpinnerNumberModel(15000.0, 0.0, 1000000.0, 500.0));
        chkMudarse = new JCheckBox("Dispuesto a mudarse");
        chkMudarse.setBackground(Color.WHITE);

        comunes.add(etiquetado("Tipo:", cmbTipo));
        comunes.add(etiquetado("Nombre:", txtNombre));
        comunes.add(etiquetado("Cedula:", txtCedula));
        comunes.add(etiquetado("Sexo:", cmbSexo));
        comunes.add(etiquetado("Telefono:", txtTelefono));
        comunes.add(etiquetado("Provincia:", txtProvincia));
        comunes.add(etiquetado("Salario esperado:", spnSalario));
        comunes.add(chkMudarse);

        cardEspecifico = new CardLayout();
        pnlEspecifico = new JPanel(cardEspecifico);
        pnlEspecifico.setBackground(Color.WHITE);

        JPanel pCandidato = new JPanel(new GridLayout(0, 1, 4, 6));
        pCandidato.setBackground(Color.WHITE);
        txtPerfil = new JTextField();
        txtInteres = new JTextField();
        pCandidato.add(etiquetado("Perfil profesional:", txtPerfil));
        pCandidato.add(etiquetado("Area de interes:", txtInteres));

        JPanel pObrero = new JPanel(new GridLayout(0, 1, 4, 6));
        pObrero.setBackground(Color.WHITE);
        txtHabilidades = new JTextField();
        pObrero.add(etiquetado("Habilidades:", txtHabilidades));

        JPanel pTecnico = new JPanel(new GridLayout(0, 1, 4, 6));
        pTecnico.setBackground(Color.WHITE);
        txtTipoTecnico = new JTextField();
        spnAnios = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        pTecnico.add(etiquetado("Tipo de tecnico:", txtTipoTecnico));
        pTecnico.add(etiquetado("Anios de experiencia:", spnAnios));

        JPanel pUniversitario = new JPanel(new GridLayout(0, 1, 4, 6));
        pUniversitario.setBackground(Color.WHITE);
        txtCarrera = new JTextField();
        pUniversitario.add(etiquetado("Carrera:", txtCarrera));

        pnlEspecifico.add(pCandidato, "Candidato General");
        pnlEspecifico.add(pObrero, "Obrero");
        pnlEspecifico.add(pTecnico, "Tecnico");
        pnlEspecifico.add(pUniversitario, "Universitario");

        JPanel botones = new JPanel(new GridLayout(1, 3, 5, 5));
        botones.setBackground(Color.WHITE);
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(Principal.COLOR_PRIMARIO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { guardar(); }
        });
        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { limpiar(); }
        });
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(Principal.COLOR_ACENTO);
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { eliminar(); }
        });
        botones.add(btnGuardar);
        botones.add(btnLimpiar);
        botones.add(btnEliminar);

        JPanel panelCentralForm = new JPanel(new BorderLayout(0, 8));
        panelCentralForm.setBackground(Color.WHITE);
        panelCentralForm.add(comunes, BorderLayout.NORTH);
        panelCentralForm.add(pnlEspecifico, BorderLayout.CENTER);

        contenedor.add(panelCentralForm, BorderLayout.CENTER);
        contenedor.add(botones, BorderLayout.SOUTH);

        JScrollPane scrollFormulario = new JScrollPane(contenedor);
        scrollFormulario.setPreferredSize(new java.awt.Dimension(360, 0));
        scrollFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollFormulario.getVerticalScrollBar().setUnitIncrement(16);

        return scrollFormulario;
    }

    private JPanel etiquetado(String texto, java.awt.Component campo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        p.add(lbl, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"Cedula", "Nombre", "Tipo", "Provincia", "Empleado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(26);
        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                    cargarSeleccion(tabla.getSelectedRow());
                }
            }
        });
        return new JScrollPane(tabla);
    }

    public void refrescar() {
        modeloTabla.setRowCount(0);
        for (Persona p : ventana.getBolsa().getListaPersona()) {
            modeloTabla.addRow(new Object[] {
                    p.getCedula(), p.getNombre(), obtenerTipoTexto(p), p.getProvincia(),
                    p.isEmpleado() ? "Si" : "No"
            });
        }
    }

    private String obtenerTipoTexto(Persona p) {
        if (p instanceof Obrero) return "Obrero";
        if (p instanceof Tecnico) return "Tecnico";
        if (p instanceof Universitario) return "Universitario";
        if (p instanceof Candidatos) return "Candidato General";
        return "Persona";
    }

    private void cargarSeleccion(int fila) {
        String cedula = (String) modeloTabla.getValueAt(fila, 0);
        personaSeleccionada = ventana.getBolsa().buscarPersonaPorCedula(cedula);
        if (personaSeleccionada == null) return;

        txtNombre.setText(personaSeleccionada.getNombre());
        txtCedula.setText(personaSeleccionada.getCedula());
        txtCedula.setEditable(false);
        txtTelefono.setText(personaSeleccionada.getTelefono());
        txtProvincia.setText(personaSeleccionada.getProvincia());
        spnSalario.setValue(personaSeleccionada.getSalarioEsperado());
        chkMudarse.setSelected(personaSeleccionada.isDispuestoAMudarse());
        
        if (personaSeleccionada.getSexo() != null) {
            cmbSexo.setSelectedItem(personaSeleccionada.getSexo());
        }

        if (personaSeleccionada instanceof Obrero) {
            cmbTipo.setSelectedItem("Obrero");
            txtHabilidades.setText(((Obrero) personaSeleccionada).getHabilidades());
        } else if (personaSeleccionada instanceof Tecnico) {
            cmbTipo.setSelectedItem("Tecnico");
            txtTipoTecnico.setText(((Tecnico) personaSeleccionada).getTipoDeTecnico());
            spnAnios.setValue(((Tecnico) personaSeleccionada).getAnoDeExperiencia());
        } else if (personaSeleccionada instanceof Universitario) {
            cmbTipo.setSelectedItem("Universitario");
            txtCarrera.setText(((Universitario) personaSeleccionada).getCarrera());
        } else if (personaSeleccionada instanceof Candidatos) {
            cmbTipo.setSelectedItem("Candidato General");
            txtPerfil.setText(((Candidatos) personaSeleccionada).getPerfilProfesional());
            txtInteres.setText(((Candidatos) personaSeleccionada).getAreaInteres());
        }
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y cedula son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipo = (String) cmbTipo.getSelectedItem();
        String cedulaIngresada = txtCedula.getText().trim();

        if (personaSeleccionada != null) {
            personaSeleccionada.setNombre(txtNombre.getText().trim());
            personaSeleccionada.setTelefono(txtTelefono.getText().trim());
            personaSeleccionada.setProvincia(txtProvincia.getText().trim());
            personaSeleccionada.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
            personaSeleccionada.setDispuestoAMudarse(chkMudarse.isSelected());
            personaSeleccionada.setSexo((String) cmbSexo.getSelectedItem());

            if (personaSeleccionada instanceof Obrero) {
                ((Obrero) personaSeleccionada).setHabilidades(txtHabilidades.getText().trim());
            } else if (personaSeleccionada instanceof Tecnico) {
                ((Tecnico) personaSeleccionada).setTipoDeTecnico(txtTipoTecnico.getText().trim());
                ((Tecnico) personaSeleccionada).setAnoDeExperiencia(((Number) spnAnios.getValue()).intValue());
            } else if (personaSeleccionada instanceof Universitario) {
                ((Universitario) personaSeleccionada).setCarrera(txtCarrera.getText().trim());
            } else if (personaSeleccionada instanceof Candidatos) {
                ((Candidatos) personaSeleccionada).setPerfilProfesional(txtPerfil.getText().trim());
                ((Candidatos) personaSeleccionada).setAreaInteres(txtInteres.getText().trim());
            }

            JOptionPane.showMessageDialog(this, "Persona modificada exitosamente.");
            limpiar();
            refrescar();
            return;
        }

        if (ventana.getBolsa().buscarPersonaPorCedula(cedulaIngresada) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una persona con esa cedula.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Persona nueva;
        if (tipo.equals("Obrero")) {
            Obrero o = new Obrero();
            o.setHabilidades(txtHabilidades.getText().trim());
            nueva = o;
        } else if (tipo.equals("Tecnico")) {
            Tecnico t = new Tecnico();
            t.setTipoDeTecnico(txtTipoTecnico.getText().trim());
            t.setAnoDeExperiencia(((Number) spnAnios.getValue()).intValue());
            nueva = t;
        } else if (tipo.equals("Universitario")) {
            Universitario u = new Universitario();
            u.setCarrera(txtCarrera.getText().trim());
            nueva = u;
        } else {
            Candidatos c = new Candidatos();
            c.setPerfilProfesional(txtPerfil.getText().trim());
            c.setAreaInteres(txtInteres.getText().trim());
            nueva = c;
        }

        nueva.setNombre(txtNombre.getText().trim());
        nueva.setCedula(cedulaIngresada);
        nueva.setTelefono(txtTelefono.getText().trim());
        nueva.setProvincia(txtProvincia.getText().trim());
        nueva.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
        nueva.setDispuestoAMudarse(chkMudarse.isSelected());
        nueva.setSexo((String) cmbSexo.getSelectedItem());

        if (tipo.equals("Obrero")) {
            ventana.getBolsa().registrarObrero((Obrero) nueva);
        } else if (tipo.equals("Tecnico")) {
            ventana.getBolsa().registrarTecnico((Tecnico) nueva);
        } else if (tipo.equals("Universitario")) {
            ventana.getBolsa().registrarUniversitario((Universitario) nueva);
        } else {
            ventana.getBolsa().registrarCandidato((Candidatos) nueva);
        }

        JOptionPane.showMessageDialog(this, "Persona registrada exitosamente.");
        limpiar();
        refrescar();
    }

    private void eliminar() {
        if (personaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una persona de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "Deseas eliminar a " + personaSeleccionada.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                ventana.getBolsa().eliminarPersona(personaSeleccionada);
                limpiar();
                refrescar();
            } catch (ExcepcionNoEliminable ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiar() {
        personaSeleccionada = null;
        txtNombre.setText("");
        txtCedula.setText("");
        txtCedula.setEditable(true);
        txtTelefono.setText("");
        txtProvincia.setText("");
        spnSalario.setValue(15000.0);
        chkMudarse.setSelected(false);
        cmbSexo.setSelectedIndex(0);
        txtPerfil.setText("");
        txtInteres.setText("");
        txtHabilidades.setText("");
        txtTipoTecnico.setText("");
        spnAnios.setValue(0);
        txtCarrera.setText("");
        cmbTipo.setSelectedIndex(0);
        tabla.clearSelection();
    }
    
    
}
