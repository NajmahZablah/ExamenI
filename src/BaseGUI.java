/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author andre
 */
public class BaseGUI {
    //aqui solo estan los metodos para la interfaz de la pantalla main :3
    
        public static JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
   
    public static JPanel crearTarjeta(RenItem item) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(850, 200));
        
        // Imagen
        if (item.getImagenitem() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = item.getImagenitem();
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            tarjeta.add(lblImagen, BorderLayout.WEST);
        }
        
        // info
        JPanel panelInfo = crearPanelInfo(item);
        tarjeta.add(panelInfo, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
   
    private static JPanel crearPanelInfo(RenItem item) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel lblNombre = new JLabel("Nombre: " + item.getNombreItem());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        
//        if (item instanceof Movie) {
//            Movie movie = (Movie) item;
//            JLabel lblEstado = new JLabel("Estado: " + movie.getEstado());
//            lblEstado.setFont(new Font("Arial", Font.PLAIN, 14));
//            panelInfo.add(lblEstado);
//            panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
//        }
//        
        JLabel lblPrecio = new JLabel("Precio de Renta: Lps. " + 
            String.format("%.2f", item.getPrecioBaseRenta()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfo.add(lblPrecio);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel lblCodigo = new JLabel("Código: " + item.getCodigoitem());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCodigo.setForeground(Color.GRAY);
        panelInfo.add(lblCodigo);
        
        return panelInfo;
    }
    
    /**
     * Muestra un mensaje de error
     */
    public static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de éxito
     */
    public static void mostrarExito(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
   
    public static void mostrarAdvertencia(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
   
    public static JPanel crearPanelInfoConImagen(RenItem item) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea info = new JTextArea(item.toString());
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(new JScrollPane(info), BorderLayout.CENTER);
        
        if (item.getImagenitem() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = item.getImagenitem();
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            panel.add(lblImagen, BorderLayout.WEST);
        }
        
        return panel;
    }
    
    
    public static Integer solicitarEntero(Component parent, String mensaje) {
        try {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return null;
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            mostrarError(parent, "Error: Ingrese un número válido.");
            return null;
        }
    }
    
   
    public static Double solicitarDouble(Component parent, String mensaje) {
        try {
            String input = JOptionPane.showInputDialog(parent, mensaje);
            if (input == null) return null;
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            mostrarError(parent, "Error: Ingrese un número válido.");
            return null;
        }
    }
  
    public static String solicitarTexto(Component parent, String mensaje) {
        String input = JOptionPane.showInputDialog(parent, mensaje);
        if (input != null && input.trim().isEmpty()) {
            return null;
        }
        return input;
    }
    
   
    public static JLabel crearTitulo(String texto) {
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        return titulo;
    }
}
