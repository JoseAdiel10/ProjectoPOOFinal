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
        
        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBackground(Principal.COLOR_FONDO);
        grid.setBorder(BorderFactory.createEmptyBorder(0, 80, 80, 80));

        grid.add(crearBoton("Personas", "personas"));
        grid.add(crearBoton("Empresas", "empresas"));
        grid.add(crearBoton("Vacantes", "vacantes"));
        grid.add(crearBoton("Postulaciones", "postulaciones"));
        grid.add(crearBoton("Ranking de Compatibilidad", "matching"));
        grid.add(crearBotonSalir());

        add(grid, BorderLayout.CENTER);
    }
    
    private JButton crearBoton(String texto, final String destino) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(Principal.COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel(destino);
            }
        });
        return btn;
    }
    
    private JButton crearBotonSalir() {
        JButton btn = new JButton("Cerrar Sesion");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(Principal.COLOR_ACENTO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setUsuarioActual(null);
                ventana.mostrarPanel("login");
            }
        });
        return btn;
    }
