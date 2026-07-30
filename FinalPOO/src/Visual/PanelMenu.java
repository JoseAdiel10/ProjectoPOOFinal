package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Logico.Usuario;

public class PanelMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    private Principal ventana;

    private JLabel lblBienvenida;
    private JLabel lblRol;

    // Tarjetas Métricas / Estadísticas
    private JLabel lblTotalPersonas;
    private JLabel lblTotalEmpresas;
    private JLabel lblTotalVacantes;
    private JLabel lblTotalPostulaciones;

    // Botones del Sidebar Lateral
    private JPanel pnlNavegacion;
    private JButton btnPersonas;
    private JButton btnEmpresas;
    private JButton btnVacantes;
    private JButton btnPostulaciones;
    private JButton btnMatching;

    public PanelMenu(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Principal.COLOR_FONDO);

        add(crearSidebarLateral(), BorderLayout.WEST);
        add(crearCuerpoPrincipal(), BorderLayout.CENTER);
    }

    /**
     * Barra lateral de navegación estilo App Web moderna
     */
    private JPanel crearSidebarLateral() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Principal.COLOR_PRIMARIO);
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 15, 25, 15));

        // Título / Brand
        JLabel lblBrand = new JLabel("⚡ TALENT HUB");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setAlignmentX(LEFT_ALIGNMENT);

        sidebar.add(lblBrand);
        sidebar.add(Box.createRigidArea(new Dimension(0, 35)));

        // Panel de botones dinámicos
        pnlNavegacion = new JPanel();
        pnlNavegacion.setLayout(new BoxLayout(pnlNavegacion, BoxLayout.Y_AXIS));
        pnlNavegacion.setOpaque(false);

        btnPersonas = crearBotonMenu("👥 Candidatos", "personas");
        btnEmpresas = crearBotonMenu("🏢 Empresas", "empresas");
        btnVacantes = crearBotonMenu("📌 Vacantes", "vacantes");
        btnPostulaciones = crearBotonMenu("📄 Postulaciones", "postulaciones");
        btnMatching = crearBotonMenu("🎯 Matching IA", "matching");

        pnlNavegacion.add(btnPersonas);
        pnlNavegacion.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlNavegacion.add(btnEmpresas);
        pnlNavegacion.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlNavegacion.add(btnVacantes);
        pnlNavegacion.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlNavegacion.add(btnPostulaciones);
        pnlNavegacion.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlNavegacion.add(btnMatching);

        sidebar.add(pnlNavegacion);
        sidebar.add(Box.createVerticalGlue());

        // Botón Cerrar Sesión
        JButton btnCerrarSesion = new JButton("🚪 Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCerrarSesion.setForeground(new Color(252, 165, 165));
        btnCerrarSesion.setContentAreaFilled(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.setAlignmentX(LEFT_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> {
            ventana.setUsuarioActual(null);
            ventana.mostrarPanel("login");
        });

        sidebar.add(btnCerrarSesion);
        return sidebar;
    }

    private JButton crearBotonMenu(String texto, String panelDestino) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(Principal.COLOR_SECUNDARIO);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(210, 40));
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.addActionListener(e -> ventana.mostrarPanel(panelDestino));
        return btn;
    }

    /**
     * Cuerpo Principal: Banner Superior + Tarjetas Métricas del Dashboard
     */
    private JScrollPane crearCuerpoPrincipal() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Principal.COLOR_FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- Banner de Bienvenida ---
        JPanel banner = new JPanel(new BorderLayout());
        banner.setBackground(Color.WHITE);
        banner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        JPanel textosBanner = new JPanel(new GridLayout(2, 1, 0, 5));
        textosBanner.setOpaque(false);

        lblBienvenida = new JLabel("¡Hola de nuevo!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBienvenida.setForeground(Principal.COLOR_TEXTO);

        lblRol = new JLabel("Rol de usuario: Administrador");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRol.setForeground(Principal.COLOR_MUTED);

        textosBanner.add(lblBienvenida);
        textosBanner.add(lblRol);
        banner.add(textosBanner, BorderLayout.WEST);

        contenedor.add(banner);
        contenedor.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Título del Dashboard ---
        JLabel lblSeccion = new JLabel("Resumen del Sistema");
        lblSeccion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSeccion.setForeground(Principal.COLOR_TEXTO);
        lblSeccion.setAlignmentX(LEFT_ALIGNMENT);

        contenedor.add(lblSeccion);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- Grid de Tarjetas de Estadísticas ---
        JPanel gridTarjetas = new JPanel(new GridLayout(2, 2, 20, 20));
        gridTarjetas.setOpaque(false);
        gridTarjetas.setMaximumSize(new Dimension(2000, 260));

        lblTotalPersonas = new JLabel("0", SwingConstants.CENTER);
        lblTotalEmpresas = new JLabel("0", SwingConstants.CENTER);
        lblTotalVacantes = new JLabel("0", SwingConstants.CENTER);
        lblTotalPostulaciones = new JLabel("0", SwingConstants.CENTER);

        gridTarjetas.add(crearTarjetaMetrica("Candidatos Registrados", lblTotalPersonas, new Color(37, 99, 235)));
        gridTarjetas.add(crearTarjetaMetrica("Empresas Afiliadas", lblTotalEmpresas, new Color(16, 185, 129)));
        gridTarjetas.add(crearTarjetaMetrica("Vacantes Publicadas", lblTotalVacantes, new Color(245, 158, 11)));
        gridTarjetas.add(crearTarjetaMetrica("Postulaciones Activas", lblTotalPostulaciones, new Color(139, 92, 246)));

        contenedor.add(gridTarjetas);
        contenedor.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        return scroll;
    }

    private JPanel crearTarjetaMetrica(String titulo, JLabel lblValor, Color colorAcento) {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 10));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 5, 0, 0, colorAcento),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(226, 232, 240)),
                        BorderFactory.createEmptyBorder(18, 20, 18, 20))));

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTit.setForeground(Principal.COLOR_MUTED);

        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValor.setForeground(Principal.COLOR_TEXTO);

        tarjeta.add(lblTit, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        return tarjeta;
    }

    /**
     * Refresca la información cada vez que se muestra el menú
     */
    public void actualizarBienvenida() {
        Usuario u = ventana.getUsuarioActual();
        if (u != null) {
            lblBienvenida.setText("¡Bienvenido, " + u.getUsernameEmpresa() + "!");
            lblRol.setText("Tipo de cuenta: " + u.getTipo());

            // Configurar visibilidad del menú según el rol
            boolean esAdmin = "Admin".equalsIgnoreCase(u.getTipo());
            boolean esEmpresa = "Empresa".equalsIgnoreCase(u.getTipo());

            btnPersonas.setVisible(esAdmin);
            btnEmpresas.setVisible(esAdmin);
            btnVacantes.setVisible(esAdmin || esEmpresa);
            btnPostulaciones.setVisible(true);
            btnMatching.setVisible(true);
        }

        // Actualizar valores de las métricas en tiempo real
        lblTotalPersonas.setText(String.valueOf(ventana.getBolsa().getListaPersona().size()));
        lblTotalEmpresas.setText(String.valueOf(ventana.getBolsa().getEmpresas().size()));
        lblTotalVacantes.setText(String.valueOf(ventana.getBolsa().getVacantes().size()));
        lblTotalPostulaciones.setText(String.valueOf(ventana.getBolsa().getRegistroPostulaciones().size()));
    }
}
