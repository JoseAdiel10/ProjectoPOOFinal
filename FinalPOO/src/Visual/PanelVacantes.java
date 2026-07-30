package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Logico.CentroEmpleador;
import Logico.Vacantes;
import excepciones.ExcepcionNoEliminable;

public class PanelVacantes extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Vacantes seleccionada = null;

    private JTextField txtTitulo, txtProvincia;
    private JTextArea txtDescripcion;
    private JSpinner spnSalario, spnHoras, spnPorcentaje;
    private JComboBox<String> cmbSexo;
    private JComboBox<CentroEmpleador> cmbEmpresa;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelVacantes(Principal ventana) {
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

        JLabel titulo = new JLabel("Gestion de Vacantes");
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

    private JPanel crearFormulario() {
        JPanel contenedor = new JPanel(new BorderLayout(5, 5));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        contenedor.setPreferredSize(new java.awt.Dimension(340, 100));

        JPanel campos = new JPanel(new GridLayout(0, 1, 4, 6));
        campos.setBackground(Color.WHITE);

        cmbEmpresa = new JComboBox<>();
        txtTitulo = new JTextField();
        txtDescripcion = new JTextArea(3, 15);
        spnSalario = new JSpinner(new SpinnerNumberModel(15000.0, 0.0, 1000000.0, 500.0));
        spnHoras = new JSpinner(new SpinnerNumberModel(40, 0, 80, 1));
        spnPorcentaje = new JSpinner(new SpinnerNumberModel(0, 0, 100, 5));
        cmbSexo = new JComboBox<>(new String[] {"Indiferente", "Masculino", "Femenino"});
        txtProvincia = new JTextField();

        campos.add(etiquetado("Empresa:", cmbEmpresa));
        campos.add(etiquetado("Titulo:", txtTitulo));
        campos.add(etiquetado("Descripcion:", new JScrollPane(txtDescripcion)));
        campos.add(etiquetado("Salario ofertado:", spnSalario));
        campos.add(etiquetado("Horas semanales:", spnHoras));
        campos.add(etiquetado("% Coincidencia minimo:", spnPorcentaje));
        campos.add(etiquetado("Sexo:", cmbSexo));
        campos.add(etiquetado("Provincia:", txtProvincia));

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

        contenedor.add(new JScrollPane(campos), BorderLayout.CENTER);
        contenedor.add(botones, BorderLayout.SOUTH);
        return contenedor;
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
        String[] columnas = {"ID", "Titulo", "Empresa", "Salario", "Estado"};
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
        cmbEmpresa.removeAllItems();
        for (CentroEmpleador emp : ventana.getBolsa().getEmpresas()) {
            cmbEmpresa.addItem(emp);
        }

        modeloTabla.setRowCount(0);
        for (Vacantes v : ventana.getBolsa().getVacantes()) {
            String nombreEmpresa = v.getEmpleador() != null ? v.getEmpleador().getNombre() : "-";
            modeloTabla.addRow(new Object[] {v.getIdVacante(), v.getTitulo(), nombreEmpresa, v.getSalario(), v.getEstado()});
        }
    }

    private void cargarSeleccion(int fila) {
        int id = (int) modeloTabla.getValueAt(fila, 0);
        seleccionada = ventana.getBolsa().buscarVacantePorId(id);
        if (seleccionada == null) return;
        cmbEmpresa.setSelectedItem(seleccionada.getEmpleador());
        txtTitulo.setText(seleccionada.getTitulo());
        txtDescripcion.setText(seleccionada.getDescripcion());
        spnSalario.setValue(seleccionada.getSalario());
        spnHoras.setValue(seleccionada.getCantidadDeHorasTrabajadas());
        spnPorcentaje.setValue((int) seleccionada.getPorcientoDeCoincidencia());
        cmbSexo.setSelectedItem(seleccionada.getSexo() != null ? seleccionada.getSexo() : "Indiferente");
        txtProvincia.setText(seleccionada.getProvincia());
    }

    private void guardar() {
        if (txtTitulo.getText().trim().isEmpty() || cmbEmpresa.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "El titulo y la empresa son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Vacantes v = (seleccionada != null) ? seleccionada : new Vacantes();
        v.setEmpleador((CentroEmpleador) cmbEmpresa.getSelectedItem());
        v.setTitulo(txtTitulo.getText().trim());
        v.setDescripcion(txtDescripcion.getText().trim());
        v.setSalario(((Number) spnSalario.getValue()).doubleValue());
        v.setCantidadDeHorasTrabajadas(((Number) spnHoras.getValue()).intValue());
        v.setPorcientoDeCoincidencia(((Number) spnPorcentaje.getValue()).doubleValue());
        v.setSexo((String) cmbSexo.getSelectedItem());
        v.setProvincia(txtProvincia.getText().trim());

        if (seleccionada == null) {
            v.setIdVacante(ventana.getBolsa().generarIdVacante());
            v.setEstado("Activa");
            ventana.getBolsa().publicarVacante(v);
            JOptionPane.showMessageDialog(this, "Vacante publicada exitosamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Vacante modificada exitosamente.");
        }

        limpiar();
        refrescar();
    }

    private void eliminar() {
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una vacante de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this, "Deseas eliminar la vacante " + seleccionada.getTitulo() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                ventana.getBolsa().eliminarVacante(seleccionada);
                limpiar();
                refrescar();
            } catch (ExcepcionNoEliminable ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo eliminar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiar() {
        seleccionada = null;
        txtTitulo.setText("");
        txtDescripcion.setText("");
        spnSalario.setValue(15000.0);
        spnHoras.setValue(40);
        spnPorcentaje.setValue(0);
        cmbSexo.setSelectedIndex(0);
        txtProvincia.setText("");
        tabla.clearSelection();
    }
}

