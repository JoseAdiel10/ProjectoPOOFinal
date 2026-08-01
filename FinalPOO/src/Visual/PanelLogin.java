package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Logico.Usuario;
import excepciones.ExcepcionAutenticacion;

public class PanelLogin extends JPanel {

    private static final long serialVersionUID = 1L;
    private Principal ventana;

    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public PanelLogin(Principal ventana) {
        this.ventana = ventana;
        setLayout(new GridLayout(1, 2)); 
        setBackground(Principal.COLOR_FONDO);

        add(crearLateralIzquierdo());
        add(crearLateralDerecho());
    }

    /**
     * Panel Izquierdo: Fondo con la imagen y los textos institucionales arriba.
     */
    private JPanel crearLateralIzquierdo() {
        JPanel p = new JPanel() {
            private static final long serialVersionUID = 1L;
            private Image imagenFondo = cargarImagen();

            private Image cargarImagen() {
                try {
                    URL url = getClass().getResource("comptrabajo.jpg");
                    if (url != null) {
                        return new ImageIcon(url).getImage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Principal.COLOR_PRIMARIO);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false); 
        p.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel lblLogo = new JLabel(" CONNECTWORK RD");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setOpaque(false); 

        JLabel lblSlogan = new JLabel("Conectando el talento con las mejores oportunidades");
        lblSlogan.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSlogan.setForeground(new Color(226, 232, 240));
        lblSlogan.setOpaque(false); 

        p.add(lblLogo);
        p.add(Box.createRigidArea(new Dimension(0, 8)));
        p.add(lblSlogan);
        p.add(Box.createVerticalGlue()); 

        return p;
    }

    /**
     * Panel Derecho: Formulario de Iniciar Sesión / Enlaces de Registro
     */
    private JPanel crearLateralDerecho() {
        JPanel contenedor = new JPanel(new GridBagLayout()); 
        contenedor.setBackground(Principal.COLOR_FONDO);

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setPreferredSize(new Dimension(420, 520));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(35, 35, 35, 35)));

        JLabel lblTitulo = new JLabel("¡Bienvenido!");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Principal.COLOR_TEXTO);

        JLabel lblSub = new JLabel("Ingresa tus credenciales para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(Principal.COLOR_MUTED);

        // Campos
        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtClave = new JPasswordField();
        txtClave.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnIngresar.setBackground(Principal.COLOR_ACENTO);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setMaximumSize(new Dimension(400, 42));
        btnIngresar.addActionListener(e -> login());

        // Botón Registrarse
        JButton btnRegistrar = new JButton("¿No tienes cuenta? Regístrate aquí");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setForeground(Principal.COLOR_ACENTO);
        btnRegistrar.setContentAreaFilled(false);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(e -> ventana.mostrarPanel("registro"));

        // Acceso Directo Administrador
        JButton btnAdmin = new JButton("Acceso rápido como Administrador");
        btnAdmin.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        btnAdmin.setForeground(Principal.COLOR_MUTED);
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Principal.COLOR_MUTED));
        btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdmin.addActionListener(e -> loginAdmin());

        // Ensamble
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 5)));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 30)));

        tarjeta.add(crearCampo("Usuario / Correo:", txtUsuario));
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(crearCampo("Contraseña:", txtClave));
        tarjeta.add(Box.createRigidArea(new Dimension(0, 25)));

        tarjeta.add(btnIngresar);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(btnRegistrar);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(btnAdmin);

        contenedor.add(tarjeta);
        return contenedor;
    }

    private JPanel crearCampo(String titulo, java.awt.Component comp) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setOpaque(false);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(Principal.COLOR_TEXTO);
        p.add(lbl, BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private void login() {
        String u = txtUsuario.getText().trim();
        String p = new String(txtClave.getPassword());

        if (u.isEmpty() || p.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario user = ventana.getBolsa().iniciarSesion(u, p);
            ventana.setUsuarioActual(user);
            limpiar();
            ventana.mostrarPanel("menu");
        } catch (ExcepcionAutenticacion e) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loginAdmin() {
        try {
            Usuario admin = ventana.getBolsa().iniciarSesion("admin", "admin");
            ventana.setUsuarioActual(admin);
            limpiar();
            ventana.mostrarPanel("menu");
        } catch (ExcepcionAutenticacion e) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario Admin por defecto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiar() {
        txtUsuario.setText("");
        txtClave.setText("");
    }
}

