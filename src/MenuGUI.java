

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class MenuGUI extends JFrame {

    private ArrayList<RenItem> items;

    public MenuGUI() {
        items = new ArrayList<>();
        configurarVentana();
        crearInterfaz();
    }

    private void configurarVentana() {
        setTitle("Sistema de Renta Multimedia");
        setSize(420, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void crearInterfaz() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        JLabel titulo = new JLabel("Sistema de Renta Multimedia", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(50, 50, 50));
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        panel.add(titulo);

        panel.add(crearBoton("Agregar Ítem", e -> abrirAgregarItem()));
        panel.add(crearEspacio());
        panel.add(crearBoton("Rentar Ítem", e -> abrirAgregarItem()));
        panel.add(crearEspacio());
        panel.add(crearBoton("Ejecutar Submenú", e -> abrirAgregarItem()));
        panel.add(crearEspacio());
        panel.add(crearBoton("Imprimir Todo", e -> abrirAgregarItem()));
        panel.add(crearEspacio());
        panel.add(crearBoton("Salir", e -> System.exit(0)));
        panel.add(Box.createVerticalGlue());
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 17));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(40, 40, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(240, 55));
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(accion);
        return btn;
    }
    
    private void rentar() {
        if (items.isEmpty()) {
            BaseGUI.mostrarAdvertencia(this, "No hay ítems registrados.");
            return;
        }
        RentarItemGUI ventanaRentar = new RentarItemGUI(items);  // Sin pasar 'this'
        ventanaRentar.setVisible(true);
    }

    private Component crearEspacio() {
        return Box.createRigidArea(new Dimension(0, 18));
    }

    // ===========================
    // MÉTODOS PARA CADA BOTÓN
    // ===========================

    private void abrirAgregarItem() {
        // Abrir tu JFrame de agregar ítem
        AgregarItem ventana = new AgregarItem(items); // tu JFrame existente
        ventana.setVisible(true);
    }

//    private void abrirRentarItem() {
//        // Abrir tu JFrame de rentar ítem
//        RentarItemFrame ventana = new RentarItemFrame(items); // tu JFrame existente
//        ventana.setVisible(true);
//    }
//
//    private void abrirSubmenu() {
//        // Abrir tu JFrame de submenú
//        SubmenuFrame ventana = new SubmenuFrame(items); // tu JFrame existente
//        ventana.setVisible(true);
//    }
//
//    private void abrirImprimirTodo() {
//        // Abrir tu JFrame para imprimir todos los ítems
//        ImprimirTodoFrame ventana = new ImprimirTodoFrame(items); // tu JFrame existente
//        ventana.setVisible(true);
//    }
}
