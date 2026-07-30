package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Menu principal: botones grandes para navegar entre los distintos
 * modulos del sistema. Las opciones visibles cambian segun el rol
 * del usuario que inicio sesion (Admin, Empresa o Candidato).
 */
public class PanelMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JLabel lblBienvenida;
    private JLabel lblRol;
    private JPanel grid;

    public PanelMenu(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Principal.COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new javax.swing.BoxLayout(encabezado, javax.swing.BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);
        encabezado.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        lblBienvenida = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(Principal.COLOR_PRIMARIO);
        lblBienvenida.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        lblRol = new JLabel(" ", SwingConstants.CENTER);
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setForeground(new Color(130, 140, 150));
        lblRol.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        lblRol.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        encabezado.add(lblBienvenida);
        encabezado.add(lblRol);
        add(encabezado, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(Principal.COLOR_FONDO);
        add(grid, BorderLayout.CENTER);
    }

    private JButton crearBoton(String icono, String texto, final String destino) {
        JButton btn = new JButton("<html><div style='text-align:center;'>"
                + "<span style='font-size:26px;'>" + icono + "</span><br>"
                + "<span style='font-size:14px;'>" + texto + "</span></div></html>");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Principal.COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 10, 18, 10)));
        btn.setPreferredSize(new Dimension(180, 110));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel(destino);
            }
        });
        return btn;
    }

    private JButton crearBotonSalir() {
        JButton btn = new JButton("<html><div style='text-align:center;'>"
                + "<span style='font-size:26px;'>&#128682;</span><br>"
                + "<span style='font-size:14px;'>Cerrar Sesion</span></div></html>");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(Principal.COLOR_ACENTO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(180, 110));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setUsuarioActual(null);
                ventana.mostrarPanel("login");
            }
        });
        return btn;
    }

    /**
     * Reconstruye el grid de botones segun el rol del usuario actual
     * y actualiza el mensaje de bienvenida. Se llama cada vez que se
     * muestra este panel.
     */
    public void actualizarBienvenida() {
        grid.removeAll();

        String rol = "Admin";
        if (ventana.getUsuarioActual() != null) {
            lblBienvenida.setText("Bienvenido, " + ventana.getUsuarioActual().getUsernameEmpresa());
            rol = ventana.getUsuarioActual().getTipo() != null ? ventana.getUsuarioActual().getTipo() : "Admin";
        }

        if ("Empresa".equalsIgnoreCase(rol)) {
            lblRol.setText("Panel de Empresa");
            grid.add(crearBoton("&#127970;", "Mi Empresa", "empresas"));
            grid.add(crearBoton("&#128203;", "Publicar Vacantes", "vacantes"));
            grid.add(crearBoton("&#128196;", "Postulaciones", "postulaciones"));
            grid.add(crearBoton("&#127919;", "Ranking de Candidatos", "matching"));
            grid.add(crearBotonSalir());
        } else if ("Candidato".equalsIgnoreCase(rol)) {
            lblRol.setText("Panel de Candidato");
            grid.add(crearBoton("&#128100;", "Mi Perfil", "personas"));
            grid.add(crearBoton("&#128188;", "Vacantes Disponibles", "vacantes"));
            grid.add(crearBoton("&#128196;", "Mis Postulaciones", "postulaciones"));
            grid.add(crearBotonSalir());
        } else {
            lblRol.setText("Panel de Administrador");
            grid.add(crearBoton("&#128100;", "Personas", "personas"));
            grid.add(crearBoton("&#127970;", "Empresas", "empresas"));
            grid.add(crearBoton("&#128188;", "Vacantes", "vacantes"));
            grid.add(crearBoton("&#128196;", "Postulaciones", "postulaciones"));
            grid.add(crearBoton("&#127919;", "Ranking de Compatibilidad", "matching"));
            grid.add(crearBotonSalir());
        }

        grid.revalidate();
        grid.repaint();
    }
}

