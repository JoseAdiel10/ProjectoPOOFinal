package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Logico.Usuario;

/**
 * Panel de menu principal con control de acceso por roles (Permisos).
 */
public class PanelMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JLabel lblBienvenida;

   
    private JButton btnPersonas;
    private JButton btnEmpresas;
    private JButton btnVacantes;
    private JButton btnPostulaciones;
    private JButton btnMatching;
    private JButton btnSalir;

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

        btnPersonas = crearBoton("Personas", "personas");
        btnEmpresas = crearBoton("Empresas", "empresas");
        btnVacantes = crearBoton("Vacantes", "vacantes");
        btnPostulaciones = crearBoton("Postulaciones", "postulaciones");
        btnMatching = crearBoton("Ranking de Compatibilidad", "matching");
        btnSalir = crearBotonSalir();

        grid.add(btnPersonas);
        grid.add(btnEmpresas);
        grid.add(btnVacantes);
        grid.add(btnPostulaciones);
        grid.add(btnMatching);
        grid.add(btnSalir);

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

    public void actualizarBienvenida() {
        Usuario user = ventana.getUsuarioActual();
        if (user != null) {
            lblBienvenida.setText("Bienvenido, " + user.getUsernameEmpresa());
            
           
            btnPersonas.setVisible(true);
            btnEmpresas.setVisible(true);
            btnVacantes.setVisible(true);
            btnPostulaciones.setVisible(true);
            btnMatching.setVisible(true);
            
            
            String tipo = user.getTipo(); 
            
            if (tipo != null) {
                if (tipo.equalsIgnoreCase("Candidato")) {
                    btnEmpresas.setVisible(false);
                } else if (tipo.equalsIgnoreCase("Empresa")) {
                    
                    btnPersonas.setVisible(false);
                }
            }
        }
    }
}