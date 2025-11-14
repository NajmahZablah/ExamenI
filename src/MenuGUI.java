

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
        configurarVentana();
        crearMenu();
    }

    private void configurarVentana() {
        setTitle("Sistema de Renta Multimedia");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearMenu() {
        mainPanel = new JPanel(new GridBagLayout());
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

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // Botón Agregar Ítem
        JButton btnAgregar = BaseGUI.crearBoton("Agregar Ítem", new Color(76, 175, 80));
        btnAgregar.addActionListener(e -> AgregarItem());
        mainPanel.add(btnAgregar, gbc);

        // Botón Rentar
        gbc.gridy = 2;
        JButton btnRentar = BaseGUI.crearBoton("Rentar", new Color(33, 150, 243));
        btnRentar.addActionListener(e -> AgregarItem());
        mainPanel.add(btnRentar, gbc);

        // Botón Ejecutar Submenú
        gbc.gridy = 3;
        JButton btnSubmenu = BaseGUI.crearBoton("Ejecutar Submenú", new Color(255, 152, 0));
        btnSubmenu.addActionListener(e -> AgregarItem());
        mainPanel.add(btnSubmenu, gbc);

        // Botón Imprimir Todo
        gbc.gridy = 4;
        JButton btnImprimir = BaseGUI.crearBoton("Imprimir Todo", new Color(156, 39, 176));
        btnImprimir.addActionListener(e -> AgregarItem());
        mainPanel.add(btnImprimir, gbc);

        // Botón Salir
        gbc.gridy = 5;
        JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(244, 67, 54));
        btnSalir.addActionListener(e -> System.exit(0));
        mainPanel.add(btnSalir, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    // ===== Métodos que abren otros JFrames =====
//
    private void AgregarItem() {
        AgregarItem agregar = new AgregarItem(items); // JFrame de agregar ítem
        agregar.setVisible(true);
    }
    
    private void rentar() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
            return;
        }
        RentarItemGUI ventanaRentar = new RentarItemGUI(items);  // Sin pasar 'this'
        ventanaRentar.setVisible(true);
    }

//    private void abrirRentar() {
//        if (items.isEmpty()) {
//            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
//            return;
//        }
//        RentarItemFrame rentarFrame = new RentarItemFrame(items); // JFrame de renta
//        rentarFrame.setVisible(true);
//    }
//
//    private void abrirSubmenu() {
//        if (items.isEmpty()) {
//            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
//            return;
//        }
//        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
//        if (codigo == null) return;
//
//        RenItem item = buscarItemPorCodigo(codigo);
//        if (item == null) {
//            BaseGUI.mostrarError(this, "Item No Existe");
//            return;
//        }
//
//        if (item instanceof MenuActions) {
//            SubmenuFrame submenu = new SubmenuFrame((MenuActions)item);
//            submenu.setVisible(true);
//        } else {
//            JOptionPane.showMessageDialog(this,
//                "Este ítem no tiene submenú disponible.",
//                "Información", JOptionPane.INFORMATION_MESSAGE);
//        }
//    }
//
//    private void abrirImprimirTodo() {
//        if (items.isEmpty()) {
//            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
//            return;
//        }
//        ImprimirTodoFrame imprimirFrame = new ImprimirTodoFrame(items); // JFrame para listar todo
//        imprimirFrame.setVisible(true);
//    }
//
//    private RenItem buscarItemPorCodigo(int codigo) {
//        for (RenItem item : items) {
//            if (item.getCodigoitem() == codigo) return item;
//        }
//        return null;
//    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}
