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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Logico.CentroEmpleador;
import excepciones.ExcepcionNoEliminable;

/**
 * Panel para registrar, consultar, modificar y eliminar centros empleadores.
 */
public class PanelEmpresas extends JPanel
{
	
	private static final long serialVersionUID = 1L;

    private Principal ventana;
    private CentroEmpleador seleccionada = null;

    private JTextField txtRnc, txtNombre, txtDireccion;
    private JComboBox<String> cmbSector;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private static final String[] SECTORES = {
            "Turismo", "Tecnologia", "Salud", "Comercio", "Educacion",
            "Agricultura", "Construccion", "Juridico", "Transporte"
    };

    public PanelEmpresas(Principal ventana) {
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

        JLabel titulo = new JLabel("Gestion de Empresas");
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

        txtRnc = new JTextField();
        txtNombre = new JTextField();
        txtDireccion = new JTextField();
        cmbSector = new JComboBox<>(SECTORES);

        campos.add(etiquetado("RNC:", txtRnc));
        campos.add(etiquetado("Nombre:", txtNombre));
        campos.add(etiquetado("Sector:", cmbSector));
        campos.add(etiquetado("Direccion:", txtDireccion));

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

        contenedor.add(campos, BorderLayout.NORTH);
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
        String[] columnas = {"RNC", "Nombre", "Sector", "Direccion"};
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
	
	
	
}
