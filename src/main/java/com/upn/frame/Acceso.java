package com.upn.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Login didáctico para la Pastelería.
 * Flujo: se escribe el DNI -> Enter -> aparece el nombre completo
 * -> se escribe la contraseña -> Ingresar.
 */
public class Acceso extends JFrame {

    // "Base de datos" de prueba: DNI -> {nombre completo, contraseña}
    private final Map<String, String[]> usuarios = new HashMap<>();

    private JLabel lblTitulo;
    private JLabel lblDni;
    private JLabel lblNombreTitulo;
    private JLabel lblNombre;
    private JLabel lblClave;
    private JTextField txtDni;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private JButton btnLimpiar;
    private JButton btnSalir;

    public Acceso() {
        cargarUsuarios();
        initComponents();
    }

    // Usuarios de ejemplo (cámbialos o conéctalos a tu base de datos)
    private void cargarUsuarios() {
        usuarios.put("12345678", new String[]{"Maria Fernanda Lopez Ruiz", "1234"});
        usuarios.put("87654321", new String[]{"Carlos Alberto Diaz Vega", "abcd"});
        usuarios.put("45678912", new String[]{"Lucia Valeria Torres Rojas", "pastel"});
    }

    private void initComponents() {
        setTitle("Pastelería - Inicio de sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Título
        lblTitulo = new JLabel("Pastelería", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.ITALIC, 26));
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(lblTitulo, gbc);

        // DNI
        lblDni = new JLabel("DNI");
        gbc.gridy = 1;
        panel.add(lblDni, gbc);

        txtDni = new JTextField(15);
        txtDni.setToolTipText("Escribe tu DNI (8 dígitos) y presiona Enter");
        gbc.gridy = 2;
        panel.add(txtDni, gbc);

        // Nombre completo (solo lectura, aparece al dar Enter en el DNI)
        lblNombreTitulo = new JLabel("Nombre completo");
        gbc.gridy = 3;
        panel.add(lblNombreTitulo, gbc);

        lblNombre = new JLabel(" ");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(245, 245, 245));
        gbc.gridy = 4;
        panel.add(lblNombre, gbc);

        // Contraseña
        lblClave = new JLabel("Contraseña");
        gbc.gridy = 5;
        panel.add(lblClave, gbc);

        txtClave = new JPasswordField(15);
        gbc.gridy = 6;
        panel.add(txtClave, gbc);

        // Botones
        btnIngresar = new JButton("Ingresar");
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");

        JPanel panelBotones = new JPanel(new java.awt.GridLayout(1, 3, 8, 0));
        panelBotones.add(btnIngresar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);
        gbc.gridy = 7;
        gbc.insets = new Insets(18, 5, 5, 5);
        panel.add(panelBotones, gbc);

        add(panel);

        // ---------- EVENTOS ----------

        // Solo números, máximo 8 dígitos; al editar el DNI se borra el nombre mostrado
        txtDni.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    return;
                }
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                } else if (Character.isDigit(c) && txtDni.getText().length() >= 8) {
                    e.consume();
                }
                lblNombre.setText(" ");
            }
        });

        // Enter en DNI -> buscar y mostrar nombre
        txtDni.addActionListener(e -> buscarNombre());

        // Enter en contraseña -> equivale a Ingresar
        txtClave.addActionListener(e -> ingresar());

        btnIngresar.addActionListener(e -> ingresar());
        btnLimpiar.addActionListener(e -> limpiar());
        btnSalir.addActionListener(e -> salir());

        getRootPane().setDefaultButton(btnIngresar);
        pack();
        setLocationRelativeTo(null); // centrar en pantalla
    }

    // Busca el DNI y muestra el nombre completo
    private boolean buscarNombre() {
        String dni = txtDni.getText().trim();

        if (dni.length() != 8) {
            mostrarNombre("El DNI debe tener 8 dígitos", true);
            txtDni.requestFocus();
            return false;
        }

        String[] datos = usuarios.get(dni);
        if (datos == null) {
            mostrarNombre("DNI no registrado", true);
            txtDni.requestFocus();
            return false;
        }

        mostrarNombre(datos[0], false);
        txtClave.requestFocus(); // pasa el cursor a la contraseña
        return true;
    }

    private void mostrarNombre(String texto, boolean esError) {
        lblNombre.setForeground(esError ? Color.RED : Color.BLACK);
        lblNombre.setText(texto);
    }

    private void ingresar() {
        if (!buscarNombre()) {
            return;
        }

        String dni = txtDni.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa tu contraseña.",
                    "Falta contraseña", JOptionPane.WARNING_MESSAGE);
            txtClave.requestFocus();
            return;
        }

        if (usuarios.get(dni)[1].equals(clave)) {
            JOptionPane.showMessageDialog(this,
                    "¡Bienvenido(a), " + usuarios.get(dni)[0] + "!",
                    "Acceso correcto", JOptionPane.INFORMATION_MESSAGE);
            // Aquí abre tu siguiente ventana, por ejemplo:
            // new Menu_Pasteleria().setVisible(true);
            // this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta.",
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
            txtClave.setText("");
            txtClave.requestFocus();
        }
    }

    private void limpiar() {
        txtDni.setText("");
        txtClave.setText("");
        lblNombre.setText(" ");
        txtDni.requestFocus();
    }

    private void salir() {
        int op = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas salir?",
                "Salir", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_Pasteleria.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Login_Pasteleria().setVisible(true));
    }
}