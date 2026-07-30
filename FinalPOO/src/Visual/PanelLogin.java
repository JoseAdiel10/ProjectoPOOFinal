package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Logico.Usuario;
import excepciones.ExcepcionAutenticacion;

public class PanelLogin extends JPanel {

    private static final long serialVersionUID = 1L;
    private Principal ventana;

    private JTextField txtUsuario;
    private JPasswordField txtClave;

    public PanelLogin(Principal ventana) {
        this.ventana = ventana;
        setLayout(new GridLayout(1, 2)); // Divide la pantalla exactamente en 2 mitad izquierda/derecha
        setBackground(Principal.COLOR_FONDO);

        add(crearLateralIzquierdo());
        add(crearLateralDerecho());
    }

    /**
     * Panel Izquierdo: Branding, colores delicados y mensaje publicitario
     */
    private JPanel crearLateralIzquierdo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Principal.COLOR_PRIMARIO);
        p.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        JLabel lblLogo = new JLabel("💼 BOLSA TRABAJO");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setForeground(Color.WHITE);

        JLabel lblSlogan = new JLabel("Conectando el talento con las mejores oportunidades");
        lblSlogan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSlogan.setForeground(new Color(203, 213, 225));

        JLabel item1 = crearItemInfo("✨ Algoritmo inteligente de Matching");
        JLabel item2 = crearItemInfo("🏢 Gestión para Empresas y Candidatos");
        JLabel item3 = crearItemInfo("📊 Control completo de Vacantes y Postulaciones");

        p.add(lblLogo);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(lblSlogan);
        p.add(Box.createRigidArea(new Dimension(0, 50)));
        p.add(item1);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(item2);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(item3);
        p.add(Box.createVerticalGlue());

        JLabel lblFooter = new JLabel("© 2026 Sistema de Gestión de Empleo");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(Principal.COLOR_MUTED);
        p.add(lblFooter);

        return p;
    }

    private JLabel crearItemInfo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setForeground(new Color(241, 245, 249));
        return lbl;
    }

    /**
     * Panel Derecho: Formulario de Iniciar Sesión / Enlaces de Registro
     */
    private JPanel crearLateralDerecho() {
        JPanel contenedor = new JPanel(new GridBagLayout()); // Centra la tarjeta en el lado derecho
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
            // Intenta iniciar sesión con la Bolsa
            Usuario user = ventana.getBolsa().iniciarSesion(u, p);
            
            // Si no se lanza ninguna excepción, la autenticación fue exitosa:
            ventana.setUsuarioActual(user);
            limpiar();
            ventana.mostrarPanel("menu");

        } catch (ExcepcionAutenticacion e) {
            // Si las credenciales fallan, salta directamente a este bloque:
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

