/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author andre
 */
public class AgregarItem extends JFrame{
      private ArrayList<RenItem> items;
    private JTextField txtCodigo, txtNombre, txtPrecio;
    private JLabel lblImagen;
    private String rutaImagen = "";
    private JComboBox<String> comboTipo;

    public AgregarItem(ArrayList<RenItem> items) {
        this.items = items;
        setTitle("Agregar Ítem");
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void crearComponentes() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = BaseGUI.crearTitulo("Agregar Nuevo Ítem");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(titulo, gbc);
        gbc.gridwidth = 1;

        // Tipo
        gbc.gridy = 1; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Tipo de Ítem:"), gbc);

        gbc.gridx = 1;
        comboTipo = new JComboBox<>(new String[]{"Movie", "Game"});
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(comboTipo, gbc);

        // Código
        gbc.gridy = 2; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        txtCodigo = new JTextField(20);
        panelPrincipal.add(txtCodigo, gbc);

        // Nombre
        gbc.gridy = 3; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelPrincipal.add(txtNombre, gbc);

        // Precio
        gbc.gridy = 4; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Precio Base:"), gbc);

        gbc.gridx = 1;
        txtPrecio = new JTextField(20);
        panelPrincipal.add(txtPrecio, gbc);

        // Imagen
        gbc.gridy = 5; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Imagen:"), gbc);

        gbc.gridx = 1;
        JButton btnSeleccionar = new JButton("Seleccionar Imagen");
        btnSeleccionar.addActionListener(e -> seleccionarImagen());
        panelPrincipal.add(btnSeleccionar, gbc);

        // Preview
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        lblImagen = new JLabel("No hay imagen seleccionada", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelPrincipal.add(lblImagen, gbc);

        add(panelPrincipal, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnGuardar = BaseGUI.crearBoton("Guardar", new Color(76, 175, 80));
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        btnGuardar.addActionListener(e -> guardarItem());
        panelBotones.add(btnGuardar);

        JButton btnCancelar = BaseGUI.crearBoton("Cancelar", new Color(244, 67, 54));
        btnCancelar.setPreferredSize(new Dimension(150, 40));
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            rutaImagen = selectedFile.getAbsolutePath();

            ImageIcon icon = new ImageIcon(rutaImagen);
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        }
    }

    private void guardarItem() {
        try {
            if (txtCodigo.getText().length() == 0 ||
                txtNombre.getText().length() == 0 ||
                txtPrecio.getText().length() == 0) {
                BaseGUI.mostrarError(this, "Todos los campos son obligatorios.");
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText());
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());

            for (RenItem item : items) {
                if (item.getCodigoitem() == codigo) {
                    BaseGUI.mostrarError(this, "El código ya existe.");
                    return;
                }
            }

            if (rutaImagen.isEmpty()) {
                BaseGUI.mostrarError(this, "Debe seleccionar una imagen.");
                return;
            }

            // Descomenta tu lógica cuando tengas Movie y Game
            /*
            String tipo = (String) comboTipo.getSelectedItem();
            if (tipo.equals("Movie")) {
                items.add(new Movie(codigo, nombre, precio, 0, rutaImagen));
                BaseGUI.mostrarExito(this, "Película agregada!");
            } else {
                items.add(new Game(codigo, nombre, precio, 0, rutaImagen));
                BaseGUI.mostrarExito(this, "Videojuego agregado!");
            }
            */

            dispose();

        } catch (NumberFormatException ex) {
            BaseGUI.mostrarError(this, "Ingrese valores numéricos válidos.");
        } catch (Exception ex) {
            BaseGUI.mostrarError(this, "Error: " + ex.getMessage());
        }
    }
}
