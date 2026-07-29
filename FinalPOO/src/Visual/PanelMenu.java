package Visual;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Panel de menu principal: botones grandes para navegar entre los
 * distintos modulos del sistema.
 */
public class PanelMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JLabel lblBienvenida;

    public PanelMenu(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Principal.COLOR_FONDO);

        lblBienvenida = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBienvenida.setForeground(Principal.COLOR_PRIMARIO);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        add(lblBienvenida, BorderLayout.NORTH);
