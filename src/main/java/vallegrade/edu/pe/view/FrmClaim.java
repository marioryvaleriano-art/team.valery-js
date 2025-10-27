package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.ClaimController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

public class FrmClaim extends JFrame {

    private JTextField txtNombre = new JTextField(15);
    private JTextField txtApellidos = new JTextField(15);
    private JComboBox<String> cmbTipoDoc = new JComboBox<>(new String[]{"DNI", "CE", "RUC", "Pasaporte"});
    private JTextField txtNumeroDoc = new JTextField(15);
    private JTextField txtTelefono = new JTextField(15);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtProducto = new JTextField(15);
    private JTextField txtDocCompra = new JTextField(15);
    private JTextField txtFechaCompra = new JTextField(10); // YYYY-MM-DD
    private JTextField txtSucursal = new JTextField(15);
    private JComboBox<String> cmbTipoReclamo = new JComboBox<>(new String[]{"Reclamo", "Queja"});
    private JTextArea txtDetalle = new JTextArea(3, 20);
    private JTextArea txtPedido = new JTextArea(3, 20);

    private JButton btnRegistrar = new JButton("Registrar");
    private JButton btnModificar = new JButton("Modificar");
    private JButton btnEliminar = new JButton("Eliminar");
    private JButton btnListar = new JButton("Listar");

    private ClaimController controller = new ClaimController();

    public FrmClaim() {
        setTitle("Reclamaciones Sopanta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // ---------------- PANEL PRINCIPAL CON DEGRADADO ----------------
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(173, 216, 230);
                Color color2 = Color.WHITE;
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // ---------------- TITULO ----------------
        JLabel lblTitulo = new JLabel("RECLAMACIONES SOPANTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Inter", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(0, 70, 130));
        lblTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // ---------------- PANEL FORMULARIO ----------------
        JPanel panelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(new EmptyBorder(10, 40, 10, 40));

        addLabelAndField(panelFormulario, "Nombre:", txtNombre);
        addLabelAndField(panelFormulario, "Apellidos:", txtApellidos);
        addLabelAndField(panelFormulario, "Tipo Documento:", cmbTipoDoc);
        addLabelAndField(panelFormulario, "Número Documento:", txtNumeroDoc);
        addLabelAndField(panelFormulario, "Teléfono:", txtTelefono);
        addLabelAndField(panelFormulario, "Email:", txtEmail);
        addLabelAndField(panelFormulario, "Producto:", txtProducto);
        addLabelAndField(panelFormulario, "Documento de Compra:", txtDocCompra);
        addLabelAndField(panelFormulario, "Fecha Compra (YYYY-MM-DD):", txtFechaCompra);
        addLabelAndField(panelFormulario, "Sucursal:", txtSucursal);
        addLabelAndField(panelFormulario, "Tipo de Reclamo:", cmbTipoReclamo);
        addLabelAndField(panelFormulario, "Detalle:", new JScrollPane(txtDetalle));
        addLabelAndField(panelFormulario, "Pedido:", new JScrollPane(txtPedido));

        mainPanel.add(panelFormulario, BorderLayout.CENTER);

        // ---------------- BOTONES ----------------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);

        btnRegistrar.setBackground(new Color(0, 128, 0));
        btnRegistrar.setForeground(Color.WHITE);
        btnModificar.setBackground(new Color(0, 128, 0));
        btnModificar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(0, 128, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnListar.setBackground(new Color(0, 128, 0));
        btnListar.setForeground(Color.WHITE);

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        // ---------------- ESTILO CAMPOS ----------------
        configurarCampos(txtNombre, txtApellidos, txtNumeroDoc, txtTelefono, txtEmail,
                txtProducto, txtDocCompra, txtFechaCompra, txtSucursal, txtDetalle, txtPedido);

        // ---------------- ACCIONES ----------------
        btnRegistrar.addActionListener(e -> registrarReclamo());
        btnModificar.addActionListener(e -> modificarReclamo());
        btnEliminar.addActionListener(e -> eliminarReclamo());
        btnListar.addActionListener(e -> listarReclamos());

        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Inter", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        panel.add(label);
        panel.add(field);
    }

    private void configurarCampos(JComponent... campos) {
        for (JComponent campo : campos) {
            if (campo instanceof JTextField) {
                campo.setFont(new Font("Inter", Font.BOLD, 14));
                campo.setBackground(new Color(240, 248, 255));
                campo.setForeground(Color.BLACK);
            } else if (campo instanceof JTextArea) {
                campo.setFont(new Font("Inter", Font.BOLD, 14));
                campo.setBackground(new Color(240, 248, 255));
                campo.setForeground(Color.BLACK);
            }
        }
    }

    // ---------------- MÉTODOS FUNCIONALES ----------------

    private void registrarReclamo() {
        try {
            LocalDate fechaCompra = parseFecha(txtFechaCompra.getText());

            if (!esEmailValido(txtEmail.getText())) {
                JOptionPane.showMessageDialog(null, "Email inválido");
                return;
            }

            controller.registrarReclamo(
                    "RCLM-" + System.currentTimeMillis(),
                    LocalDate.now().toString(),
                    txtNombre.getText(),
                    txtApellidos.getText(),
                    cmbTipoDoc.getSelectedItem().toString(),
                    txtNumeroDoc.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtProducto.getText(),
                    txtDocCompra.getText(),
                    fechaCompra.toString(),
                    txtSucursal.getText(),
                    cmbTipoReclamo.getSelectedItem().toString(),
                    txtDetalle.getText(),
                    txtPedido.getText()
            );

            JOptionPane.showMessageDialog(this, "Reclamo registrado correctamente");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }

    private void modificarReclamo() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del reclamo a modificar:");
        if (id == null || id.isEmpty()) return;

        try {
            controller.modificarReclamo(
                    id,
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtDetalle.getText()
            );
            JOptionPane.showMessageDialog(this, "Reclamo modificado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage());
        }
    }

    private void eliminarReclamo() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del reclamo a eliminar:");
        if (id == null || id.isEmpty()) return;

        try {
            controller.eliminarReclamo(id);
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Reclamo eliminado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtNumeroDoc.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtProducto.setText("");
        txtDocCompra.setText("");
        txtFechaCompra.setText("");
        txtSucursal.setText("");
        txtDetalle.setText("");
        txtPedido.setText("");
        cmbTipoDoc.setSelectedIndex(0);
        cmbTipoReclamo.setSelectedIndex(0);
    }

    private void listarReclamos() {
        try {
            List<String[]> reclamos = controller.listarReclamos();
            String[] columnas = {"ID", "Nombre", "Apellidos", "Teléfono", "Email", "Producto", "Fecha"};
            String[][] datos = reclamos.toArray(new String[0][]);

            JTable table = new JTable(datos, columnas);
            JScrollPane scrollPane = new JScrollPane(table);

            JOptionPane.showMessageDialog(this, scrollPane, "Lista de Reclamos", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al listar reclamos: " + ex.getMessage());
        }
    }

    private LocalDate parseFecha(String fecha) throws Exception {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fecha, formatter);
        } catch (DateTimeParseException ex) {
            throw new Exception("Formato de fecha inválido. Use YYYY-MM-DD");
        }
    }

    private boolean esEmailValido(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);
    }
}
