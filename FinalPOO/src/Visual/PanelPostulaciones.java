package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Logico.Persona;
import Logico.Postulacion;
import Logico.Vacantes;
import excepciones.ExcepcionFormato;

/**
 * Panel para postular a una persona a una vacante y para evaluar
 * (contratar/rechazar) las postulaciones existentes.
 */
public class PanelPostulaciones extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Postulacion seleccionada = null;

    private JComboBox<Persona> cmbPersona;
    private JComboBox<Vacantes> cmbVacante;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public PanelPostulaciones(Principal ventana) {
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

        JLabel titulo = new JLabel("Postulaciones");
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
        contenedor.setPreferredSize(new java.awt.Dimension(320, 100));

        JPanel campos = new JPanel(new GridLayout(0, 1, 4, 8));
        campos.setBackground(Color.WHITE);

        cmbPersona = new JComboBox<>();
        cmbVacante = new JComboBox<>();

        campos.add(etiquetado("Persona:", cmbPersona));
        campos.add(etiquetado("Vacante:", cmbVacante));

        JButton btnPostular = new JButton("Postularse");
        btnPostular.setBackground(Principal.COLOR_PRIMARIO);
        btnPostular.setForeground(Color.WHITE);
        btnPostular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { postular(); }
        });

        JPanel accionesPostulacion = new JPanel(new GridLayout(1, 2, 5, 5));
        accionesPostulacion.setBackground(Color.WHITE);
        JButton btnContratar = new JButton("Contratar");
        btnContratar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { contratar(); }
        });
        JButton btnRechazar = new JButton("Rechazar");
        btnRechazar.setBackground(Principal.COLOR_ACENTO);
        btnRechazar.setForeground(Color.WHITE);
        btnRechazar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { rechazar(); }
        });
        accionesPostulacion.add(btnContratar);
        accionesPostulacion.add(btnRechazar);

        JPanel sur = new JPanel(new GridLayout(2, 1, 5, 5));
        sur.setBackground(Color.WHITE);
        sur.add(btnPostular);
        sur.add(accionesPostulacion);

        contenedor.add(campos, BorderLayout.NORTH);
        contenedor.add(sur, BorderLayout.SOUTH);
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
        String[] columnas = {"ID", "Solicitante", "Vacante", "Fecha", "Estado"};
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
        cmbPersona.removeAllItems();
        for (Persona p : ventana.getBolsa().getListaPersona()) {
            cmbPersona.addItem(p);
        }
        cmbVacante.removeAllItems();
        for (Vacantes v : ventana.getBolsa().getVacantes()) {
            cmbVacante.addItem(v);
        }

        modeloTabla.setRowCount(0);
        for (Postulacion p : ventana.getBolsa().getRegistroPostulaciones()) {
            String nombreSolicitante = p.getSolicitante() != null ? p.getSolicitante().getNombre() : "-";
            String tituloVacante = p.getVacante() != null ? p.getVacante().getTitulo() : "-";
            String fecha = p.getFechaAplicacion() != null ? formatoFecha.format(p.getFechaAplicacion()) : "-";
            modeloTabla.addRow(new Object[] {p.getIdPostulacion(), nombreSolicitante, tituloVacante, fecha, p.getEstado()});
        }
    }

    private void cargarSeleccion(int fila) {
        int id = (int) modeloTabla.getValueAt(fila, 0);
        for (Postulacion p : ventana.getBolsa().getRegistroPostulaciones()) {
            if (p.getIdPostulacion() == id) {
                seleccionada = p;
                break;
            }
        }
    }

    private void postular() {
        Persona persona = (Persona) cmbPersona.getSelectedItem();
        Vacantes vacante = (Vacantes) cmbVacante.getSelectedItem();

        if (persona == null || vacante == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una persona y una vacante.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ventana.getBolsa().postularse(persona, vacante);
            JOptionPane.showMessageDialog(this, "Postulacion registrada exitosamente.");
            refrescar();
        } catch (ExcepcionFormato ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void contratar() {
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una postulacion de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ventana.getBolsa().contratarPostulacion(seleccionada);
        JOptionPane.showMessageDialog(this, "Postulacion marcada como contratada.");
        seleccionada = null;
        refrescar();
    }

    private void rechazar() {
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una postulacion de la tabla primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ventana.getBolsa().rechazarPostulacion(seleccionada);
        JOptionPane.showMessageDialog(this, "Postulacion rechazada.");
        seleccionada = null;
        refrescar();
    }
}
