package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Logico.Persona;
import Logico.Vacantes;

public class PanelMatching extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;

    private JComboBox<Vacantes> cmbVacantes;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public PanelMatching(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(15, 15));
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearFiltroSuperior(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_FONDO);

        JLabel titulo = new JLabel("🎯 Algoritmo de Coincidencia e IA (Matching)");
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

    private JPanel crearFiltroSuperior() {
        JPanel contenedor = new JPanel(new BorderLayout(15, 15));
        contenedor.setOpaque(false);

        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel lblSel = new JLabel("Selecciona una Vacante para evaluar candidatos:");
        lblSel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSel.setForeground(Principal.COLOR_TEXTO);

        cmbVacantes = new JComboBox<>();
        cmbVacantes.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnCalcular = new JButton("⚡ Ejecutar Matching");
        btnCalcular.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCalcular.setBackground(Principal.COLOR_ACENTO);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalcular.addActionListener(e -> ejecutarMatching());

        panelBusqueda.add(lblSel, BorderLayout.NORTH);
        panelBusqueda.add(cmbVacantes, BorderLayout.CENTER);
        panelBusqueda.add(btnCalcular, BorderLayout.EAST);

        // Tabla de Resultados
        String[] columnas = {"Cédula", "Candidato", "Teléfono", "% Coincidencia", "Estado Recomendado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(32);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        contenedor.add(panelBusqueda, BorderLayout.NORTH);
        contenedor.add(scrollTabla, BorderLayout.CENTER);
        return contenedor;
    }

    public void refrescar() {
        cmbVacantes.removeAllItems();
        for (Vacantes v : ventana.getBolsa().getVacantes()) {
            cmbVacantes.addItem(v);
        }
        modeloTabla.setRowCount(0);
    }

    private void ejecutarMatching() {
        Vacantes vacante = (Vacantes) cmbVacantes.getSelectedItem();
        if (vacante == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una vacante.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        modeloTabla.setRowCount(0);
        for (Persona p : ventana.getBolsa().getListaPersona()) {
            double porciento = ventana.getBolsa().calcularPuntajeIndividual(p, vacante);
            String estado = porciento >= vacante.getPorcientoDeCoincidencia() ? "✅ APTO / RECOMENDADO" : "❌ No alcanza el mínimo";
            modeloTabla.addRow(new Object[] {p.getCedula(), p.getNombre(), p.getTelefono(), String.format("%.1f%%", porciento), estado});
        }
    }
}
