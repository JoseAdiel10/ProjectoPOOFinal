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

/**
 * Panel unico para registrar, consultar, modificar y eliminar personas
 * (Candidatos, Obreros, Tecnicos y Universitarios). El formulario cambia
 * dinamicamente sus campos segun el tipo elegido.
 */
public class PanelPersonas extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private Persona personaSeleccionada = null;

    // Campos comunes
    private JTextField txtNombre, txtCedula, txtTelefono, txtProvincia;
    private JSpinner spnSalario;
    private JCheckBox chkMudarse;
    private JComboBox<String> cmbTipo;

    // Campos especificos por tipo (dentro de un CardLayout propio)
    private JPanel pnlEspecifico;
    private CardLayout cardEspecifico;

    private JTextField txtPerfil, txtInteres;       // Candidatos
    private JTextField txtHabilidades;               // Obrero
    private JTextField txtTipoTecnico;                // Tecnico
    private JSpinner spnAnios;                        // Tecnico
    private JTextField txtCarrera;                    // Universitario

    private JTable tabla;
    private DefaultTableModel modeloTabla;

}
