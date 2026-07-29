package FinalPOO.src.Visual;

public class PanelLogin extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public PanelLogin(Principal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Principal.COLOR_FONDO);
        
}
