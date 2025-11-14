

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
public class MenuGUI extends JFrame {

    private ArrayList<RenItem> items;
    private JPanel mainPanel;

    public MenuGUI() {
        items = new ArrayList<>();
        Pantalla();
        Menu();
    }

   private void Pantalla() {
        setTitle("Sistema de Renta Multimedia");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void Menu() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título
        JLabel titulo = BaseGUI.crearTitulo("Sistema de Renta Multimedia");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titulo, gbc);
        
        // Botones del menú
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        JButton btnAgregar = BaseGUI.crearBoton("Agregar Ítem", new Color(76, 175, 80));
        btnAgregar.addActionListener(e -> agregarItem());
        mainPanel.add(btnAgregar, gbc);
        
        gbc.gridy = 2;
        JButton btnRentar = BaseGUI.crearBoton("Rentar", new Color(33, 150, 243));
        btnRentar.addActionListener(e -> rentar());
        mainPanel.add(btnRentar, gbc);
        
        gbc.gridy = 3;
        JButton btnSubmenu = BaseGUI.crearBoton("Ejecutar Submenú", new Color(255, 152, 0));
        btnSubmenu.addActionListener(e -> ejecutarSubmenu());
        mainPanel.add(btnSubmenu, gbc);
        
        gbc.gridy = 4;
        JButton btnImprimir = BaseGUI.crearBoton("Imprimir Todo", new Color(156, 39, 176));
        btnImprimir.addActionListener(e -> imprimirTodo());
        mainPanel.add(btnImprimir, gbc);
        
        gbc.gridy = 5;
        JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(244, 67, 54));
        btnSalir.addActionListener(e -> System.exit(0));
        mainPanel.add(btnSalir, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    // a. Agregar Ítem (15%)
    private void agregarItem() {
        String[] opciones = {"Movie", "Game"};
        int tipo = JOptionPane.showOptionDialog(this, 
            "Seleccione el tipo de ítem:", 
            "Agregar Ítem",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opciones, 
            opciones[0]);
        
        if (tipo == -1) return;
        
        // Solicitar datos
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
        if (codigo == null) return;
        
        // Validar código único
        if (buscarItemPorCodigo(codigo) != null) {
            BaseGUI.mostrarError(this, "El código ya existe. Ingrese uno diferente.");
            return;
        }
        
        String nombre = BaseGUI.solicitarTexto(this, "Ingrese el nombre del ítem:");
        if (nombre == null) return;
        
        Double precio = BaseGUI.solicitarDouble(this, "Ingrese el precio base de renta:");
        if (precio == null) return;
        
        // Seleccionar imagen
        String rutaImagen = seleccionarImagen();
        if (rutaImagen == null) return;
        
        // Crear el ítem según el tipo
//        try {
//            if (tipo == 0) { // Movie
//                Movie movie = new Movie(codigo, nombre, precio, 0, rutaImagen);
//                items.add(movie);
//                BaseGUI.mostrarExito(this, "Película agregada exitosamente!");
//            } else { // Game
//                Game game = new Game(codigo, nombre, precio, 0, rutaImagen);
//                items.add(game);
//                BaseGUI.mostrarExito(this, "Videojuego agregado exitosamente!");
//            }
//        } catch (Exception ex) {
//            BaseGUI.mostrarError(this, "Error al agregar ítem: " + ex.getMessage());
//        }
    }
    
    // b. Rentar (10%)
    private void rentar() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
            return;
        }
        
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
        if (codigo == null) return;
        
        RenItem item = buscarItemPorCodigo(codigo);
        
        if (item == null) {
            BaseGUI.mostrarError(this, "Item No Existe");
            return;
        }
        
        // Mostrar información del ítem con imagen
        JPanel panel = BaseGUI.crearPanelInfoConImagen(item);
        JOptionPane.showMessageDialog(this, panel, "Información del Ítem", 
            JOptionPane.INFORMATION_MESSAGE);
        
        Integer dias = BaseGUI.solicitarEntero(this, "Ingrese la cantidad de días:");
        if (dias == null) return;
        
        if (dias <= 0) {
            BaseGUI.mostrarError(this, "La cantidad de días debe ser mayor a 0.");
            return;
        }
        
        double total = item.pagoRenta(dias);
        BaseGUI.mostrarExito(this, 
            "Monto total de la renta:\nLps. " + String.format("%.2f", total));
    }
    
    // c. Ejecutar Submenú (15%)
    private void ejecutarSubmenu() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
            return;
        }
        
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
        if (codigo == null) return;
        
        RenItem item = buscarItemPorCodigo(codigo);
        
        if (item == null) {
            BaseGUI.mostrarError(this, "Item No Existe");
            return;
        }
        
        if (item instanceof MenuActions) {
            MenuActions menuItem = (MenuActions) item;
            menuItem.submenu();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Este ítem no tiene submenú disponible.", 
                "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // d. Imprimir Todo (10%)
    private void imprimirTodo() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
            return;
        }
        
        JDialog dialogo = new JDialog(this, "Lista de Ítems", true);
        dialogo.setSize(900, 700);
        dialogo.setLocationRelativeTo(this);
        
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(Color.WHITE);
        
        for (RenItem item : items) {
            JPanel tarjeta = BaseGUI.crearTarjeta(item);
            contenedor.add(tarjeta);
            contenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        JScrollPane scrollPane = new JScrollPane(contenedor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        dialogo.add(scrollPane);
        dialogo.setVisible(true);
    }
    
    // Métodos auxiliares
    private RenItem buscarItemPorCodigo(int codigo) {
        for (RenItem item : items) {
            if (item.getCodigoitem() == codigo) {
                return item;
            }
        }
        return null;
    }
    
    private String seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
}
