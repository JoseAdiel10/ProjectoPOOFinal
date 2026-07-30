package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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

import Logico.Candidatos;
import Logico.Obrero;
import Logico.Persona;
import Logico.Tecnico;
import Logico.Universitario;

public class PanelPersonas extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Persona seleccionada = null;

    private JTextField txtNombre, txtCedula, txtTelefono, txtProvincia;
    private JSpinner spnSalario;
    private JCheckBox chkMudarse;
    private JComboBox<String> cmbSexo, cmbSubtipo;
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

        JLabel titulo = new JLabel("👥 Gestión de Candidatos y Personal");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Principal.COLOR_PRIMARIO);

        JButton btnVolver = new JButton("⬅ Volver al Menú");
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

        JPanel campos = new JPanel(new GridLayout(0, 1, 6, 8));
        campos.setBackground(Color.WHITE);

        txtNombre = new JTextField();
        txtCedula = new JTextField();
        txtTelefono = new JTextField();
        txtProvincia = new JTextField();
        spnSalario = new JSpinner(new SpinnerNumberModel(15000.0, 0.0, 1000000.0, 500.0));
        chkMudarse = new JCheckBox("Dispuesto a mudarse");
        chkMudarse.setBackground(Color.WHITE);
        cmbSexo = new JComboBox<>(new String[] {"Masculino", "Femenino"});
        cmbSubtipo = new JComboBox<>(new String[] {"Candidato General", "Obrero", "Técnico", "Universitario"});

        campos.add(campo("Nombre completo:", txtNombre));
        campos.add(campo("Cédula:", txtCedula));
        campos.add(campo("Teléfono:", txtTelefono));
        campos.add(campo("Provincia:", txtProvincia));
        campos.add(campo("Salario esperado:", spnSalario));
        campos.add(campo("Sexo:", cmbSexo));
        campos.add(campo("Tipo de Perfil:", cmbSubtipo));
        campos.add(chkMudarse);

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

        contenedor.add(new JScrollPane(campos), BorderLayout.CENTER);
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
        String[] columnas = {"Cédula", "Nombre", "Teléfono", "Provincia", "Tipo"};
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
                         (p instanceof Tecnico) ? "Técnico" :
                         (p instanceof Universitario) ? "Universitario" : "Candidato";
            modeloTabla.addRow(new Object[] {p.getCedula(), p.getNombre(), p.getTelefono(), p.getProvincia(), tipo});
        }
    }

    private void cargarSeleccion(int fila) {
        String cedula = (String) modeloTabla.getValueAt(fila, 0);
        seleccionada = ventana.getBolsa().buscarPersonaPorCedula(cedula);
        if (seleccionada == null) return;

        txtNombre.setText(seleccionada.getNombre());
        txtCedula.setText(seleccionada.getCedula());
        txtTelefono.setText(seleccionada.getTelefono());
        txtProvincia.setText(seleccionada.getProvincia());
        spnSalario.setValue(seleccionada.getSalarioEsperado());
        chkMudarse.setSelected(seleccionada.isDispuestoAMudarse());
        cmbSexo.setSelectedItem(seleccionada.getSexo());
    }

    private void guardar() {
        if (txtNombre.getText().trim().isEmpty() || txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y cédula son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (seleccionada == null) {
            Persona nueva = new Candidatos();
            nueva.setCedula(txtCedula.getText().trim());
            nueva.setNombre(txtNombre.getText().trim());
            nueva.setTelefono(txtTelefono.getText().trim());
            nueva.setProvincia(txtProvincia.getText().trim());
            nueva.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
            nueva.setDispuestoAMudarse(chkMudarse.isSelected());
            nueva.setSexo((String) cmbSexo.getSelectedItem());

            ventana.getBolsa().registrarCandidato((Candidatos) nueva);
            JOptionPane.showMessageDialog(this, "Candidato registrado.");
        } else {
            seleccionada.setNombre(txtNombre.getText().trim());
            seleccionada.setTelefono(txtTelefono.getText().trim());
            seleccionada.setProvincia(txtProvincia.getText().trim());
            seleccionada.setSalarioEsperado(((Number) spnSalario.getValue()).doubleValue());
            seleccionada.setDispuestoAMudarse(chkMudarse.isSelected());
            seleccionada.setSexo((String) cmbSexo.getSelectedItem());
            JOptionPane.showMessageDialog(this, "Datos actualizados.");
        }

        limpiar();
        refrescar();
    }

    private void eliminar() {
        if (seleccionada == null) return;
        ventana.getBolsa().getListaPersona().remove(seleccionada);
        limpiar();
        refrescar();
        JOptionPane.showMessageDialog(this, "Candidato eliminado.");
    }

    private void limpiar() {
        seleccionada = null;
        txtNombre.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
        txtProvincia.setText("");
        spnSalario.setValue(15000.0);
        chkMudarse.setSelected(false);
        cmbSexo.setSelectedIndex(0);
        tabla.clearSelection();
    }
}

