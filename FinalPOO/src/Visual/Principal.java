package Visual;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Logico.Bolsa;
import Logico.Usuario;

public class Principal extends JFrame {
    private static final long serialVersionUID = 1L;

    // Paleta de Colores Moderna y Delicada (Estilo Slate & Ocean)
    public static final Color COLOR_FONDO = new Color(248, 250, 252); // Blanco/Gris muy suave
    public static final Color COLOR_PRIMARIO = new Color(30, 41, 59); // Slate Oscuro / Elegante
    public static final Color COLOR_SECUNDARIO = new Color(51, 65, 85); // Azul Cobalto Soft
    public static final Color COLOR_ACENTO = new Color(37, 99, 235); // Azul Moderno/Llamativo
    public static final Color COLOR_EXITO = new Color(16, 185, 129); // Verde Esmeralda
    public static final Color COLOR_TEXTO = new Color(15, 23, 42); // Texto principal
    public static final Color COLOR_MUTED = new Color(100, 116, 139); // Texto secundario

    private Bolsa bolsa;
    private Usuario usuarioActual;
    private JPanel panelContenedor;
    private CardLayout cardLayout;

    private PanelLogin panelLogin;
    private PanelRegistro panelRegistro;
    private PanelMenu panelMenu;
    private PanelPersonas panelPersonas;
    private PanelEmpresas panelEmpresas;
    private PanelVacantes panelVacantes;
    private PanelPostulaciones panelPostulaciones;
    private PanelMatching panelMatching;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Principal frame = new Principal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Principal() {
        this.bolsa = new Bolsa();
        setTitle("Portal de Empleo & Gestión de Talentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setMinimumSize(new Dimension(1024, 720));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(COLOR_FONDO);
        setContentPane(panelContenedor);

     
        panelLogin = new PanelLogin(this);
        panelRegistro = new PanelRegistro(this);
        panelMenu = new PanelMenu(this);
        panelPersonas = new PanelPersonas(this);
        panelEmpresas = new PanelEmpresas(this);
        panelVacantes = new PanelVacantes(this);
        panelPostulaciones = new PanelPostulaciones(this);
        panelMatching = new PanelMatching(this);

        
        panelContenedor.add(panelLogin, "login");
        panelContenedor.add(panelRegistro, "registro");
        panelContenedor.add(panelMenu, "menu");
        panelContenedor.add(panelPersonas, "personas");
        panelContenedor.add(panelEmpresas, "empresas");
        panelContenedor.add(panelVacantes, "vacantes");
        panelContenedor.add(panelPostulaciones, "postulaciones");
        panelContenedor.add(panelMatching, "matching");

        mostrarPanel("login");
    }

    public void mostrarPanel(String nombre) {
        cardLayout.show(panelContenedor, nombre);
        if (nombre.equals("personas")) panelPersonas.refrescar();
        if (nombre.equals("empresas")) panelEmpresas.refrescar();
        if (nombre.equals("vacantes")) panelVacantes.refrescar();
        if (nombre.equals("postulaciones")) panelPostulaciones.refrescar();
        if (nombre.equals("matching")) panelMatching.refrescar();
        if (nombre.equals("menu")) panelMenu.actualizarBienvenida();
    }

    public Bolsa getBolsa() { return bolsa; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void setUsuarioActual(Usuario usuarioActual) { this.usuarioActual = usuarioActual; }
}

