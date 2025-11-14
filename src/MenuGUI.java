

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class MenuGUI extends JFrame {

    private ArrayList<RenItem> items;
    private JPanel panelPrincipal;
    private Image imagenFondo;

    public MenuGUI() {
        items = new ArrayList<>();
        cargarImagenFondo();
        configurarVentana();
        crearMenu();
    }

    private void cargarImagenFondo() {
        try {
            // Intenta varias rutas posibles
            String[] rutasPosibles = {
                "ImagenesFondos/Fondo_MenuPrincipal.png",
                "Fondo_MenuPrincipal.png",
                "src/ImagenesFondos/Fondo_MenuPrincipal.png"
            };
            
            for (String ruta : rutasPosibles) {
                File archivo = new File(ruta);
                if (archivo.exists()) {
                    ImageIcon icon = new ImageIcon(ruta);
                    imagenFondo = icon.getImage();
                    System.out.println("Imagen cargada desde: " + ruta);
                    return;
                }
            }
            
            System.out.println("No se encontró la imagen de fondo");
            imagenFondo = null;
            
        } catch (Exception e) {
            System.out.println("Error al cargar imagen: " + e.getMessage());
            imagenFondo = null;
        }
    }

    private void configurarVentana() {
        setTitle("Sistema de Renta Multimedia");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void crearMenu() {
        // Panel con imagen de fondo
        panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Color de respaldo si no hay imagen
                    g.setColor(new Color(255, 105, 180));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        panelPrincipal.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Sistema de Renta Multimedia", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0, 0, 0, 180));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        gbc.gridy = 0;
        panelPrincipal.add(titulo, gbc);

        // Subtítulo
        JLabel subtitulo = new JLabel("Gestión de Movies y Games", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitulo.setForeground(Color.WHITE);
        subtitulo.setOpaque(true);
        subtitulo.setBackground(new Color(0, 0, 0, 150));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        gbc.gridy = 1;
        panelPrincipal.add(subtitulo, gbc);

        // Espacio
        gbc.gridy = 2;
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        // Botones con colores del tema
        gbc.gridy = 3;
        JButton btnAgregar = crearBoton("Agregar Ítem", new Color(138, 43, 226)); // Morado
        btnAgregar.addActionListener(e -> agregarItem());
        panelPrincipal.add(btnAgregar, gbc);

        gbc.gridy = 4;
        JButton btnRentar = crearBoton("Rentar", new Color(255, 140, 0)); // Naranja
        btnRentar.addActionListener(e -> rentarItem());
        panelPrincipal.add(btnRentar, gbc);

        gbc.gridy = 5;
        JButton btnSubmenu = crearBoton("Ejecutar Submenú", new Color(255, 20, 147)); // Rosa fuerte
        btnSubmenu.addActionListener(e -> ejecutarSubmenu());
        panelPrincipal.add(btnSubmenu, gbc);

        gbc.gridy = 6;
        JButton btnImprimir = crearBoton("Imprimir Todo", new Color(75, 0, 130)); // Índigo
        btnImprimir.addActionListener(e -> imprimirTodo());
        panelPrincipal.add(btnImprimir, gbc);

        gbc.gridy = 7;
        JButton btnSalir = crearBoton("Salir", new Color(220, 20, 60)); // Rojo carmesí
        btnSalir.addActionListener(e -> salir());
        panelPrincipal.add(btnSalir, gbc);

        add(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(320, 55));
        boton.setFont(new Font("Arial", Font.BOLD, 17));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return boton;
    }

    private void agregarItem() {
        new AgregarItem(items);
    }

    private void rentarItem() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay ítems registrados en el sistema.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        RentarItemGUI ventana = new RentarItemGUI(items);
        ventana.setVisible(true);
    }

    private void ejecutarSubmenu() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay ítems registrados en el sistema.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String codigoStr = JOptionPane.showInputDialog(
            this,
            "Ingrese el código del ítem:",
            "Ejecutar Submenú",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (codigoStr == null || codigoStr.trim().isEmpty()) return;
        
        try {
            int codigo = Integer.parseInt(codigoStr.trim());
            RenItem item = buscarItemPorCodigo(codigo);
            
            if (item == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Item No Existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (item instanceof MenuActions) {
                MenuActions menuItem = (MenuActions) item;
                menuItem.submenu();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Este ítem no tiene submenú disponible.\nSolo los videojuegos tienen submenú.",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "Código inválido.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void imprimirTodo() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No hay ítems registrados en el sistema.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        JDialog dialogo = new JDialog(this, "Lista de Ítems Registrados", true);
        dialogo.setSize(950, 750);
        dialogo.setLocationRelativeTo(this);
        
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(new Color(245, 245, 245));
        contenedor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel encabezado = new JLabel("Total de ítems: " + items.size(), SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 18));
        encabezado.setForeground(new Color(138, 43, 226));
        contenedor.add(encabezado);
        contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        
        for (RenItem item : items) {
            JPanel tarjeta = crearTarjeta(item);
            contenedor.add(tarjeta);
            contenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        JScrollPane scrollPane = new JScrollPane(contenedor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        dialogo.add(scrollPane);
        dialogo.setVisible(true);
    }

    private JPanel crearTarjeta(RenItem item) {
        JPanel tarjeta = new JPanel(new BorderLayout(15, 15));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(900, 240));
        
        if (item.getImagenitem() != null) {
            JLabel lblImagen = new JLabel();
            ImageIcon icon = item.getImagenitem();
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            tarjeta.add(lblImagen, BorderLayout.WEST);
        }
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel lblNombre = new JLabel("Nombre: " + item.getNombreItem());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(new Color(138, 43, 226));
        panelInfo.add(lblNombre);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (item instanceof Movie) {
            Movie movie = (Movie) item;
            JLabel lblEstado = new JLabel("Estado: " + movie.getEstado());
            lblEstado.setFont(new Font("Arial", Font.BOLD, 16));
            lblEstado.setForeground(movie.getEstado().equals("ESTRENO") ? 
                new Color(255, 87, 34) : new Color(76, 175, 80));
            panelInfo.add(lblEstado);
            panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
            
            // Mostrar fecha de estreno
            java.util.Calendar fecha = movie.getFechaEstreno();
            String fechaTexto = fecha.get(java.util.Calendar.DAY_OF_MONTH) + "/" +
                               (fecha.get(java.util.Calendar.MONTH) + 1) + "/" +
                               fecha.get(java.util.Calendar.YEAR);
            JLabel lblFecha = new JLabel("Fecha de Estreno: " + fechaTexto);
            lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
            panelInfo.add(lblFecha);
            panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        if (item instanceof Game) {
            Game game = (Game) item;
            // Mostrar fecha de publicación
            java.util.Calendar fecha = game.getFechaPublicacion();
            String fechaTexto = fecha.get(java.util.Calendar.DAY_OF_MONTH) + "/" +
                               (fecha.get(java.util.Calendar.MONTH) + 1) + "/" +
                               fecha.get(java.util.Calendar.YEAR);
            JLabel lblFecha = new JLabel("Fecha de Publicación: " + fechaTexto);
            lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
            panelInfo.add(lblFecha);
            panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        JLabel lblPrecio = new JLabel("Precio de Renta: Lps. " + 
            String.format("%.2f", item.getPrecioBaseRenta()));
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 15));
        panelInfo.add(lblPrecio);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        
        JLabel lblCodigo = new JLabel("Código: " + item.getCodigoitem());
        lblCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCodigo.setForeground(Color.GRAY);
        panelInfo.add(lblCodigo);
        
        tarjeta.add(panelInfo, BorderLayout.CENTER);
        
        return tarjeta;
    }

    private void salir() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea salir del sistema?",
            "Confirmar Salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI menu = new MenuGUI();
            menu.setVisible(true);
        });
    }
}