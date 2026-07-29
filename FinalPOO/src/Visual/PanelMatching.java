package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.table.DefaultTableModel;

import Logico.Persona;
import Logico.Postulacion;
import Logico.Vacantes;
import excepciones.ExcepcionFormato;

/**
 * Panel que muestra, para una vacante seleccionada, el ranking de
 * personas compatibles ordenado de mayor a menor coincidencia.
 */
public class PanelMatching extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JComboBox<Vacantes> cmbVacante;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private List<Persona> ultimoRanking;

    public PanelMatching(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearBarraSuperior(), BorderLayout.PAGE_START);
        add(crearTabla(), BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Principal.COLOR_FONDO);

        JLabel titulo = new JLabel("Ranking de Compatibilidad");
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

        JButton btnPostular = new JButton("Postular seleccionado");
        panel.add(btnPostular, BorderLayout.SOUTH);
        btnPostular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { postularSeleccionado(); }
        });

        return panel;
    }
    
    private JScrollPane crearTabla() {
        String[] columnas = {"#", "Nombre", "Cedula", "Provincia", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(26);
        return new JScrollPane(tabla);
    }

    public void refrescar() {
        cmbVacante.removeAllItems();
        for (Vacantes v : ventana.getBolsa().getVacantes()) {
            cmbVacante.addItem(v);
        }
        modeloTabla.setRowCount(0);
        ultimoRanking = null;
    }
    