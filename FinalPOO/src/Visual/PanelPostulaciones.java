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
