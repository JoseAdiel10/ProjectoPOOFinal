package FinalPOO.src.Visual;

public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;

    public static final Color COLOR_FONDO = new Color(245, 247, 250);
    public static final Color COLOR_PRIMARIO = new Color(46, 92, 138);
    public static final Color COLOR_ACENTO = new Color(255, 160, 60);
    public static final Color COLOR_TEXTO = new Color(35, 40, 48);
    

    private Bolsa bolsa;
    private Usuario usuarioActual;

    private JPanel panelContenedor;
    private CardLayout cardLayout;

    private PanelLogin panelLogin;
    private PanelMenu panelMenu;
    private PanelPersonas panelPersonas;
    private PanelEmpresas panelEmpresas;
    private PanelVacantes panelVacantes;
    private PanelPostulaciones panelPostulaciones;
    private PanelMatching panelMatching;
    

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Principal frame = new Principal();
                frame.setVisible(true);
            }
        });
    }
    
    public Principal() {
        this.bolsa = new Bolsa();

        setTitle("Bolsa de Empleo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 620);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(COLOR_FONDO);
        setContentPane(panelContenedor);
        
        panelLogin = new PanelLogin(this);
        panelMenu = new PanelMenu(this);
        panelPersonas = new PanelPersonas(this);
        panelEmpresas = new PanelEmpresas(this);
        panelVacantes = new PanelVacantes(this);
        panelPostulaciones = new PanelPostulaciones(this);
        panelMatching = new PanelMatching(this);
        
        panelContenedor.add(panelLogin, "login");
        panelContenedor.add(panelMenu, "menu");
        panelContenedor.add(panelPersonas, "personas");
        panelContenedor.add(panelEmpresas, "empresas");
        panelContenedor.add(panelVacantes, "vacantes");
        panelContenedor.add(panelPostulaciones, "postulaciones");
        panelContenedor.add(panelMatching, "matching");

        mostrarPanel("login");
        }
    
    /**
     * Cambia el panel visible dentro de la ventana.
     * @param nombre Identificador del panel ("login", "menu", "personas", etc.)
     */
    public void mostrarPanel(String nombre) {
        cardLayout.show(panelContenedor, nombre);
        // Refresca los datos de cada panel cada vez que se muestra
        if (nombre.equals("personas")) panelPersonas.refrescar();
        if (nombre.equals("empresas")) panelEmpresas.refrescar();
        if (nombre.equals("vacantes")) panelVacantes.refrescar();
        if (nombre.equals("postulaciones")) panelPostulaciones.refrescar();
        if (nombre.equals("matching")) panelMatching.refrescar();
        if (nombre.equals("menu")) panelMenu.actualizarBienvenida();
    }
     
}