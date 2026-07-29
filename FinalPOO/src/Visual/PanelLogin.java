package Visual;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Logico.Usuario;
import excepciones.ExcepcionAutenticacion;

/**
 * Panel de inicio de sesion. A diferencia de un JFrame de login aparte,
 * este panel vive dentro de la ventana Principal y se intercambia via CardLayout.
 */
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

        form.add(lblUsuario);
        form.add(txtUsuario);
        form.add(lblClave);
        form.add(txtClave);
        form.add(new JLabel());
        form.add(btnEntrar);

        add(form, BorderLayout.CENTER);

        JLabel lblAyuda = new JLabel("Usuario por defecto: admin / admin", SwingConstants.CENTER);
        lblAyuda.setForeground(new java.awt.Color(140, 140, 140));
        lblAyuda.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(lblAyuda, BorderLayout.SOUTH);
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        try {
            Usuario u = ventana.getBolsa().iniciarSesion(usuario, clave);
            ventana.setUsuarioActual(u);
            txtUsuario.setText("");
            txtClave.setText("");
            ventana.mostrarPanel("menu");
        } catch (ExcepcionAutenticacion ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de acceso", JOptionPane.WARNING_MESSAGE);
        }
    }
}