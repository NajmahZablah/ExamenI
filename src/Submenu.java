/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jerem
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Submenu extends JDialog {

    private ArrayList<RenItem> items;
    private RenItem itemSeleccionado;

    public Submenu(JFrame parent, ArrayList<RenItem> items) {
        super(parent, "Submenú Ítem", true);
        this.items = items;
        configurarVentana();
        seleccionarItem();
    }

    private void configurarVentana() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
    }

    private void seleccionarItem() {
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el codigo del item:");
        if (codigo == null) {
            dispose();
            return;
        }

        itemSeleccionado = null;
        for (RenItem item : items) {
            if (item.getCodigoitem() == codigo) {
                itemSeleccionado = item;
                break;
            }
        }

        if (itemSeleccionado == null) {
            BaseGUI.mostrarError(this, "Item No Existe");
            dispose();
            return;
        }

        mostrarInfoItem();

        if (!(itemSeleccionado instanceof MenuActions)) {
            BaseGUI.mostrarAdvertencia(this, "Este ítem no tiene submenú.");
            dispose();
            return;
        }

        crearBotonesSubmenu();
    }

    private void mostrarInfoItem() {
        JPanel panel = BaseGUI.crearPanelInfoConImagen(itemSeleccionado);
        add(panel, BorderLayout.CENTER);
    }

    private void crearBotonesSubmenu() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btn1 = BaseGUI.crearBoton("Actualizar Fecha de Publicación", new Color(33, 150, 243));
        JButton btn2 = BaseGUI.crearBoton("Agregar Especificación", new Color(76, 175, 80));
        JButton btn3 = BaseGUI.crearBoton("Ver Especificaciones", new Color(255, 152, 0));
        JButton btnSalir = BaseGUI.crearBoton("Salir", new Color(244, 67, 54));

        panelBotones.add(btn1);
        panelBotones.add(btn2);
        panelBotones.add(btn3);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        MenuActions menuItem = (MenuActions) itemSeleccionado;
//aaaaaa
        btn1.addActionListener(e -> menuItem.ejecutarOpcion(1));
        btn2.addActionListener(e -> menuItem.ejecutarOpcion(2));
        btn3.addActionListener(e -> menuItem.ejecutarOpcion(3));
        btnSalir.addActionListener(e -> dispose());
    }
}

