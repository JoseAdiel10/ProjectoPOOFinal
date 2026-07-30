package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Logico.Usuario;
import excepciones.ExcepcionAutenticacion;

/**
 * Panel de inicio de sesion. Vive dentro de la ventana Principal y se
 * intercambia via CardLayout. Incluye acceso al registro de cuentas nuevas.
 */
public class PanelLogin extends JPanel {

    private static final long serialVersionUID = 1L;

    private Principal ventana;
    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public PanelLogin(Principal ventana) {
        this.ventana = ventana;
        setLayout(new GridBagLayout());
        setBackground(Principal.COLOR_PRIMARIO);

        JPanel tarjeta = crearTarjeta();
        add(tarjeta);
    }

    private JPanel crearTarjeta() {
        JPanel tarjeta = new JPanel(new BorderLayout(0, 22));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(420, 480));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(40, 40, 30, 40));

        // --- Encabezado con "logo" circular ---
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new javax.swing.BoxLayout(encabezado, javax.swing.BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);

        JLabel logo = new JLabel("BE", SwingConstants.CENTER) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Principal.COLOR_PRIMARIO);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        logo.setPreferredSize(new Dimension(64, 64));
        logo.setMaximumSize(new Dimension(64, 64));

        JLabel lblTitulo = new JLabel("Bolsa de Empleo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Principal.COLOR_TEXTO);
        lblTitulo.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(14, 0, 4, 0));

        JLabel lblSub = new JLabel("Conectando talento con oportunidades", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(130, 140, 150));
        lblSub.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        JPanel wrapLogo = new JPanel();
        wrapLogo.setOpaque(false);
        wrapLogo.add(logo);

        encabezado.add(wrapLogo);
        encabezado.add(lblTitulo);
        encabezado.add(lblSub);

        // --- Formulario ---
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 0, 6, 0);
        gc.gridx = 0;

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsuario.setForeground(new Color(90, 100, 110));

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 224)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel lblClave = new JLabel("Contrasena");
        lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblClave.setForeground(new Color(90, 100, 110));

        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtClave.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 216, 224)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        txtClave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { iniciarSesion(); }
        });

        gc.gridy = 0; form.add(lblUsuario, gc);
        gc.gridy = 1; form.add(txtUsuario, gc);
        gc.gridy = 2; form.add(lblClave, gc);
        gc.gridy = 3; form.add(txtClave, gc);

        JButton btnEntrar = new JButton("Iniciar Sesion");
        btnEntrar.setBackground(Principal.COLOR_PRIMARIO);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setPreferredSize(new Dimension(0, 42));
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { iniciarSesion(); }
        });
        gc.gridy = 4; gc.insets = new Insets(16, 0, 6, 0); form.add(btnEntrar, gc);

        JButton btnRegistro = new JButton("Crear una cuenta nueva");
        btnRegistro.setBackground(Color.WHITE);
        btnRegistro.setForeground(Principal.COLOR_PRIMARIO);
        btnRegistro.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistro.setFocusPainted(false);
        btnRegistro.setBorder(BorderFactory.createLineBorder(Principal.COLOR_PRIMARIO));
        btnRegistro.setPreferredSize(new Dimension(0, 40));
        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarPanel("registro");
            }
        });
        gc.gridy = 5; gc.insets = new Insets(6, 0, 0, 0); form.add(btnRegistro, gc);

        JLabel lblAyuda = new JLabel("<html><center>Cuenta de administrador de prueba:<br><b>admin / admin</b></center></html>", SwingConstants.CENTER);
        lblAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblAyuda.setForeground(new Color(160, 168, 176));

        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(lblAyuda, BorderLayout.CENTER);
        sur.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

        tarjeta.add(encabezado, BorderLayout.NORTH);
        tarjeta.add(form, BorderLayout.CENTER);
        tarjeta.add(sur, BorderLayout.SOUTH);
        return tarjeta;
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa tu usuario y contrasena.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario u = ventana.getBolsa().iniciarSesion(usuario, clave);
            ventana.setUsuarioActual(u);
            txtUsuario.setText("");
            txtClave.setText("");
            ventana.mostrarPanel("menu");
        } catch (ExcepcionAutenticacion ex) {
            JOptionPane.showMessageDialog(this, "Usuario o contrasena incorrectos.", "Error de acceso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
