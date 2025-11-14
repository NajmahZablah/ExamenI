/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Component;

/**
 *
 * @author najma
 */
public class RentarItemGUI extends JFrame {
    
    private ArrayList<RenItem> items;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JPanel panelInfo;
    private RenItem itemEncontrado;
    
    public RentarItemGUI(ArrayList<RenItem> items) {
        this.items = items;
        this.itemEncontrado = null;
        
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Rentar Ítem");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
    }
    
    private void inicializarComponentes() {
        JPanel panelBusqueda = crearPanelBusqueda();
        add(panelBusqueda, BorderLayout.NORTH);
        
        panelInfo = new JPanel();
        panelInfo.setLayout(new BorderLayout());
        panelInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            "Información del Ítem",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(138, 43, 226)
        ));
        panelInfo.setPreferredSize(new Dimension(630, 360));
        panelInfo.setBackground(Color.WHITE);
        add(panelInfo, BorderLayout.CENTER);
        
        JPanel panelAcciones = crearPanelAcciones();
        add(panelAcciones, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        JLabel lblCodigo = new JLabel("Código del Ítem:");
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 15));
        
        txtCodigo = new JTextField(15);
        txtCodigo.setFont(new Font("Arial", Font.PLAIN, 15));
        txtCodigo.setPreferredSize(new Dimension(200, 35));
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(138, 43, 226));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.setPreferredSize(new Dimension(100, 35));
        btnBuscar.addActionListener(e -> buscarItem());
        
        txtCodigo.addActionListener(e -> buscarItem());
        
        panel.add(lblCodigo);
        panel.add(txtCodigo);
        panel.add(btnBuscar);
        
        return panel;
    }
    
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        
        JButton btnRentar = new JButton("Rentar");
        btnRentar.setPreferredSize(new Dimension(160, 45));
        btnRentar.setFont(new Font("Arial", Font.BOLD, 15));
        btnRentar.setBackground(new Color(255, 140, 0));
        btnRentar.setForeground(Color.WHITE);
        btnRentar.setFocusPainted(false);
        btnRentar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRentar.addActionListener(e -> procesarRenta());
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(160, 45));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 15));
        btnCerrar.setBackground(new Color(220, 20, 60));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());
        
        panel.add(btnRentar);
        panel.add(btnCerrar);
        
        return panel;
    }
    
    private void buscarItem() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay ítems registrados en el sistema.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigoStr = txtCodigo.getText().trim();
        if (codigoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese un código.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }
        
        try {
            int codigo = Integer.parseInt(codigoStr);
            itemEncontrado = buscarItemPorCodigo(codigo);
            
            if (itemEncontrado == null) {
                JOptionPane.showMessageDialog(this, 
                    "Item No Existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                limpiarPanelInfo();
            } else {
                mostrarInformacionItem();
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "El código debe ser un número válido.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
        }
    }
    
    private void mostrarInformacionItem() {
        panelInfo.removeAll();
        
        JPanel contenedor = new JPanel(new BorderLayout(15, 15));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (itemEncontrado.getImagenitem() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = itemEncontrado.getImagenitem();
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
            contenedor.add(lblImagen, BorderLayout.WEST);
        }
        
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        
        JLabel lblNombre = new JLabel("Nombre: " + itemEncontrado.getNombreItem());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(new Color(138, 43, 226));
        panelDatos.add(lblNombre);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 12)));
        
        if (itemEncontrado instanceof Movie) {
            Movie movie = (Movie) itemEncontrado;
            JLabel lblEstado = new JLabel("Estado: " + movie.getEstado());
            lblEstado.setFont(new Font("Arial", Font.BOLD, 17));
            lblEstado.setForeground(movie.getEstado().equals("ESTRENO") ? 
                new Color(255, 87, 34) : new Color(76, 175, 80));
            panelDatos.add(lblEstado);
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
            
            // Fecha de estreno
            Calendar fecha = movie.getFechaEstreno();
            String fechaTexto = fecha.get(Calendar.DAY_OF_MONTH) + "/" +
                               (fecha.get(Calendar.MONTH) + 1) + "/" +
                               fecha.get(Calendar.YEAR);
            JLabel lblFecha = new JLabel("Fecha de Estreno: " + fechaTexto);
            lblFecha.setFont(new Font("Arial", Font.PLAIN, 15));
            panelDatos.add(lblFecha);
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        if (itemEncontrado instanceof Game) {
            Game game = (Game) itemEncontrado;
            JLabel lblTipoGame = new JLabel("Videojuego - PS3");
            lblTipoGame.setFont(new Font("Arial", Font.BOLD, 17));
            lblTipoGame.setForeground(new Color(156, 39, 176));
            panelDatos.add(lblTipoGame);
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
            
            // Fecha de publicación
            Calendar fecha = game.getFechaPublicacion();
            String fechaTexto = fecha.get(Calendar.DAY_OF_MONTH) + "/" +
                               (fecha.get(Calendar.MONTH) + 1) + "/" +
                               fecha.get(Calendar.YEAR);
            JLabel lblFecha = new JLabel("Fecha de Publicación: " + fechaTexto);
            lblFecha.setFont(new Font("Arial", Font.PLAIN, 15));
            panelDatos.add(lblFecha);
            panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        JLabel lblPrecio = new JLabel("Precio: Lps. " + 
            String.format("%.2f", itemEncontrado.getPrecioBaseRenta()) + " / día");
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 16));
        panelDatos.add(lblPrecio);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel lblCopias = new JLabel("Copias disponibles: " + itemEncontrado.getCantcopias());
        lblCopias.setFont(new Font("Arial", Font.BOLD, 16));
        
        if (itemEncontrado.getCantcopias() == 0) {
            lblCopias.setForeground(Color.RED);
        } else if (itemEncontrado.getCantcopias() <= 2) {
            lblCopias.setForeground(new Color(255, 152, 0));
        } else {
            lblCopias.setForeground(new Color(76, 175, 80));
        }
        
        panelDatos.add(lblCopias);
        panelDatos.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel lblCodigo = new JLabel("Código: " + itemEncontrado.getCodigoitem());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCodigo.setForeground(Color.GRAY);
        panelDatos.add(lblCodigo);
        
        contenedor.add(panelDatos, BorderLayout.CENTER);
        
        panelInfo.add(contenedor, BorderLayout.CENTER);
        panelInfo.revalidate();
        panelInfo.repaint();
    }
    
    private void procesarRenta() {
        if (itemEncontrado == null) {
            JOptionPane.showMessageDialog(this, 
                "Primero debe buscar y seleccionar un ítem.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // VALIDACIÓN: Verificar disponibilidad
        if (!itemEncontrado.hayDisponibilidad()) {
            JOptionPane.showMessageDialog(this, 
                "NO HAY COPIAS DISPONIBLES\n\n" +
                "Este ítem está agotado en este momento.\n" +
                "Copias disponibles: " + itemEncontrado.getCantcopias() + "\n\n" +
                "Por favor, intente más tarde.",
                "Error - Sin inventario",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String diasStr = JOptionPane.showInputDialog(this, 
            "Ingrese la cantidad de días de renta:",
            "Cantidad de Días",
            JOptionPane.QUESTION_MESSAGE);
        
        if (diasStr == null || diasStr.trim().isEmpty()) return;
        
        try {
            int dias = Integer.parseInt(diasStr.trim());
            
            if (dias <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "La cantidad de días debe ser mayor a 0.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double total = itemEncontrado.pagoRenta(dias);
            
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Desea confirmar la renta?\n\n" +
                "Ítem: " + itemEncontrado.getNombreItem() + "\n" +
                "Días: " + dias + "\n" +
                "Total: Lps. " + String.format("%.2f", total),
                "Confirmar Renta",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }
            
            // Descontar del inventario
            if (itemEncontrado.rentarCopia()) {
                JOptionPane.showMessageDialog(this, 
                    "Renta Exitosa\n\n" +
                    "Ítem: " + itemEncontrado.getNombreItem() + "\n" +
                    "Días de renta: " + dias + "\n" +
                    "TOTAL A PAGAR: Lps. " + String.format("%.2f", total) + "\n\n" +
                    "Copias restantes: " + itemEncontrado.getCantcopias(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                mostrarInformacionItem();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al procesar la renta.\nNo hay copias disponibles.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Debe ingresar un número válido.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private RenItem buscarItemPorCodigo(int codigo) {
        for (RenItem item : items) {
            if (item.getCodigoitem() == codigo) {
                return item;
            }
        }
        return null;
    }
    
    private void limpiarPanelInfo() {
        panelInfo.removeAll();
        panelInfo.revalidate();
        panelInfo.repaint();
        itemEncontrado = null;
    }
    
    private void limpiarFormulario() {
        txtCodigo.setText("");
        limpiarPanelInfo();
        txtCodigo.requestFocus();
    }
}