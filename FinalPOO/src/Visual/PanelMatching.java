package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Logico.Persona;
import Logico.Vacantes;
import excepciones.ExcepcionFormato;

/**
 * Panel que muestra, para una vacante seleccionada, el ranking de
 * personas compatibles ordenado de mayor a menor coincidencia.
 * La columna de porcentaje se pinta en verde/amarillo/rojo segun el nivel.
 */
public class PanelMatching extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JComboBox<Vacantes> cmbVacante;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private List<Persona> ultimoRanking;
    private Vacantes vacanteMostrada;

    public PanelMatching(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Principal.COLOR_FONDO);
        panelNorte.add(crearEncabezado(), BorderLayout.NORTH);
        panelNorte.add(crearBarraSuperior(), BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearBarraInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_FONDO);

        JLabel titulo = new JLabel("Ranking de Compatibilidad");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Principal.COLOR_PRIMARIO);
        panel.add(titulo, BorderLayout.WEST);

        JButton btnVolverArriba = new JButton("Volver al Menu");
        btnVolverArriba.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("menu");
            }
        });
        panel.add(btnVolverArriba, BorderLayout.EAST);
        return panel;
    }

    private JPanel crearBarraSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Principal.COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        cmbVacante = new JComboBox<>();
        panel.add(new JLabel("Vacante:"), BorderLayout.WEST);
        panel.add(cmbVacante, BorderLayout.CENTER);

        JButton btnCalcular = new JButton("Calcular Ranking");
        btnCalcular.setBackground(Principal.COLOR_PRIMARIO);
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { calcular(); }
        });
        panel.add(btnCalcular, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearBarraInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Principal.COLOR_FONDO);

        JButton btnPostular = new JButton("Postular seleccionado");
        btnPostular.setBackground(Principal.COLOR_PRIMARIO);
        btnPostular.setForeground(Color.WHITE);
        btnPostular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { postularSeleccionado(); }
        });

        JButton btnVolverAbajo = new JButton("Volver");
        btnVolverAbajo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("menu");
            }
        });

        panel.add(btnPostular);
        panel.add(btnVolverAbajo);
        return panel;
    }

    private JScrollPane crearTabla() {
        String[] columnas = {"#", "Nombre", "Cedula", "Provincia", "Tipo", "% Compatibilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(28);

        // Renderer de color para la columna de porcentaje
        tabla.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
                double porcentaje = 0;
                try {
                    porcentaje = Double.parseDouble(String.valueOf(value).replace("%", "").trim());
                } catch (Exception ex) { /* deja porcentaje en 0 */ }

                if (!isSelected) {
                    if (porcentaje >= 70) {
                        lbl.setBackground(new Color(224, 247, 235));
                        lbl.setForeground(new Color(23, 130, 84));
                    } else if (porcentaje >= 40) {
                        lbl.setBackground(new Color(255, 246, 219));
                        lbl.setForeground(new Color(150, 110, 15));
                    } else {
                        lbl.setBackground(new Color(253, 232, 230));
                        lbl.setForeground(new Color(180, 60, 50));
                    }
                    lbl.setOpaque(true);
                }
                return lbl;
            }
        });

        return new JScrollPane(tabla);
    }

    public void refrescar() {
        cmbVacante.removeAllItems();
        for (Vacantes v : ventana.getBolsa().getVacantes()) {
            cmbVacante.addItem(v);
        }
        modeloTabla.setRowCount(0);
        ultimoRanking = null;
        vacanteMostrada = null;
    }

    private void calcular() {
        Vacantes vacanteSeleccionada = (Vacantes) cmbVacante.getSelectedItem();
        if (vacanteSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una vacante primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        vacanteMostrada = vacanteSeleccionada;
        ultimoRanking = ventana.getBolsa().evaluarCompatibilidadCandidatos(vacanteMostrada);
        modeloTabla.setRowCount(0);

        if (ultimoRanking.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron personas compatibles con los requisitos minimos.");
            return;
        }

        int puesto = 1;
        for (Persona p : ultimoRanking) {
            double puntaje = ventana.getBolsa().calcularPuntajeIndividual(p, vacanteMostrada);
            String nombre = (p.getNombre() != null && !p.getNombre().trim().isEmpty()) ? p.getNombre() : "No registrado";
            String cedula = (p.getCedula() != null && !p.getCedula().trim().isEmpty()) ? p.getCedula() : "No registrado";
            String provincia = (p.getProvincia() != null && !p.getProvincia().trim().isEmpty()) ? p.getProvincia() : "No registrado";

            modeloTabla.addRow(new Object[] {
                puesto,
                nombre,
                cedula,
                provincia,
                obtenerTipo(p),
                String.format("%.1f %%", puntaje)
            });
            puesto++;
        }
    }

    private String obtenerTipo(Persona p) {
        return p.getClass().getSimpleName();
    }

    private void postularSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || ultimoRanking == null || vacanteMostrada == null) {
            JOptionPane.showMessageDialog(this, "Primero calcula el ranking y selecciona una persona de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Persona persona = ultimoRanking.get(fila);
        try {
            ventana.getBolsa().postularse(persona, vacanteMostrada);
            JOptionPane.showMessageDialog(this, persona.getNombre() + " fue postulado a la vacante " + vacanteMostrada.getTitulo() + ".");
        } catch (ExcepcionFormato ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}
