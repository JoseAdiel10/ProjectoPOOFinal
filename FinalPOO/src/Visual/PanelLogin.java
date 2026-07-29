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
        
        JLabel lblTitulo = new JLabel("Bolsa de Empleo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Principal.COLOR_PRIMARIO);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(60, 0, 30, 0));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 15));
        form.setBackground(Principal.COLOR_FONDO);
        form.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300));

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblClave = new JLabel("Contrasena:");
        lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton btnEntrar = new JButton("Iniciar Sesion");
        btnEntrar.setBackground(Principal.COLOR_PRIMARIO);
        btnEntrar.setForeground(java.awt.Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
}
