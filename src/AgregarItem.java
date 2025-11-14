/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Calendar;
/**
 *
 * @author andre
 */
public class AgregarItem extends JFrame {
    
    private ArrayList<RenItem> items;
    private String rutaImagenSeleccionada;
    private JLabel lblVistaPrevia;
    
    public AgregarItem(ArrayList<RenItem> items) {
        this.items = items;
        this.rutaImagenSeleccionada = null;
        
        configurarVentana();
        mostrarDialogoAgregar();
    }
    
    private void configurarVentana() {
        setTitle("Agregar Ítem");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void mostrarDialogoAgregar() {
        String[] opciones = {"Movie", "Game"};
        int tipo = JOptionPane.showOptionDialog(this, 
            "Seleccione el tipo de ítem:", 
            "Agregar Ítem",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opciones, 
            opciones[0]);
        
        if (tipo == -1) {
            dispose();
            return;
        }
        
        // Solicitar código
        Integer codigo = BaseGUI.solicitarEntero(this, "Ingrese el código del ítem:");
        if (codigo == null) {
            dispose();
            return;
        }
        
        // Validar código único
        if (buscarItemPorCodigo(codigo) != null) {
            BaseGUI.mostrarError(this, "El código ya existe. Ingrese uno diferente.");
            dispose();
            return;
        }
        
        // Solicitar nombre
        String nombre = BaseGUI.solicitarTexto(this, "Ingrese el nombre del ítem:");
        if (nombre == null || nombre.trim().isEmpty()) {
            BaseGUI.mostrarError(this, "El nombre no puede estar vacío.");
            dispose();
            return;
        }
        
        // Solicitar precio
        Double precio = BaseGUI.solicitarDouble(this, "Ingrese el precio base de renta:");
        if (precio == null || precio <= 0) {
            BaseGUI.mostrarError(this, "El precio debe ser mayor a 0.");
            dispose();
            return;
        }
        
        // Solicitar copias
        Integer copias = BaseGUI.solicitarEntero(this, "Ingrese la cantidad de copias:");
        if (copias == null || copias < 0) {
            BaseGUI.mostrarError(this, "La cantidad de copias debe ser mayor o igual a 0.");
            dispose();
            return;
        }
        
        // Seleccionar imagen
        String rutaImagen = seleccionarImagen();
        if (rutaImagen == null) {
            BaseGUI.mostrarError(this, "Debe seleccionar una imagen para el ítem.");
            dispose();
            return;
        }
        
        // Crear el ítem según el tipo
        try {
            if (tipo == 0) { // Movie
                Movie movie = new Movie(codigo, nombre, precio, copias, rutaImagen);
                
                // Preguntar por la fecha de estreno
                int opcionFecha = JOptionPane.showConfirmDialog(this,
                    "¿Desea establecer una fecha de estreno personalizada?\n" +
                    "(Si selecciona 'No', se usará la fecha actual)",
                    "Fecha de Estreno",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcionFecha == JOptionPane.YES_OPTION) {
                    Integer anio = BaseGUI.solicitarEntero(this, "Año de estreno:");
                    Integer mes = BaseGUI.solicitarEntero(this, "Mes (1-12):");
                    Integer dia = BaseGUI.solicitarEntero(this, "Día (1-31):");
                    
                    if (anio != null && mes != null && dia != null) {
                        if (mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31) {
                            Calendar fecha = Calendar.getInstance();
                            fecha.set(Calendar.YEAR, anio);
                            fecha.set(Calendar.MONTH, mes - 1); // Meses en Calendar son 0-11
                            fecha.set(Calendar.DAY_OF_MONTH, dia);
                            movie.setFechaEstreno(fecha);
                        } else {
                            BaseGUI.mostrarAdvertencia(this, 
                                "Fecha inválida. Se usará la fecha actual.");
                        }
                    }
                }
                
                items.add(movie);
                
                // Mostrar resumen
                String resumen = "Película agregada exitosamente!\n\n" +
                               "Código: " + codigo + "\n" +
                               "Nombre: " + nombre + "\n" +
                               "Precio: Lps. " + String.format("%.2f", precio) + "\n" +
                               "Copias: " + copias + "\n" +
                               "Estado: " + movie.getEstado();
                
                BaseGUI.mostrarExito(this, resumen);
                
            } else { // Game
                Game game = new Game(codigo, nombre, precio, copias, rutaImagen);
                
                // Preguntar por fecha de publicación
                int opcionFecha = JOptionPane.showConfirmDialog(this,
                    "¿Desea establecer una fecha de publicación personalizada?\n" +
                    "(Si selecciona 'No', se usará la fecha actual)",
                    "Fecha de Publicación",
                    JOptionPane.YES_NO_OPTION);
                
                if (opcionFecha == JOptionPane.YES_OPTION) {
                    Integer anio = BaseGUI.solicitarEntero(this, "Año de publicación:");
                    Integer mes = BaseGUI.solicitarEntero(this, "Mes (1-12):");
                    Integer dia = BaseGUI.solicitarEntero(this, "Día (1-31):");
                    
                    if (anio != null && mes != null && dia != null) {
                        if (mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31) {
                            game.setFechaPublicacion(anio, mes, dia);
                        } else {
                            BaseGUI.mostrarAdvertencia(this, 
                                "Fecha inválida. Se usará la fecha actual.");
                        }
                    }
                }
                
                items.add(game);
                
                // Mostrar resumen
                String resumen = "Videojuego agregado exitosamente!\n\n" +
                               "Código: " + codigo + "\n" +
                               "Nombre: " + nombre + "\n" +
                               "Precio: Lps. " + String.format("%.2f", precio) + "\n" +
                               "Copias: " + copias;
                
                BaseGUI.mostrarExito(this, resumen);
            }
            
        } catch (Exception ex) {
            BaseGUI.mostrarError(this, "Error al agregar ítem: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        dispose();
    }
    
    private String seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Establecer directorio inicial (carpeta de imágenes del proyecto)
        File directorioImagenes = new File("Imagenes Movie");
        if (directorioImagenes.exists() && directorioImagenes.isDirectory()) {
            fileChooser.setCurrentDirectory(directorioImagenes);
        } else {
            // Si no existe, usar el directorio actual del proyecto
            fileChooser.setCurrentDirectory(new File("."));
        }
        
        // Configurar filtro de archivos
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Imágenes (*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        // Agregar vista previa de imagen
        fileChooser.setAccessory(crearPanelVistaPrevia());
        fileChooser.addPropertyChangeListener(evt -> {
            if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(evt.getPropertyName())) {
                File file = (File) evt.getNewValue();
                if (file != null && esImagenValida(file)) {
                    mostrarVistaPrevia(file);
                } else {
                    limpiarVistaPrevia();
                }
            }
        });
        
        fileChooser.setDialogTitle("Seleccione una imagen para el ítem");
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Validar que el archivo existe y es una imagen
            if (selectedFile.exists() && esImagenValida(selectedFile)) {
                return selectedFile.getAbsolutePath();
            } else {
                BaseGUI.mostrarError(this, "El archivo seleccionado no es una imagen válida.");
                return null;
            }
        }
        return null;
    }
    
    private JPanel crearPanelVistaPrevia() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 200));
        panel.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
        
        lblVistaPrevia = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblVistaPrevia.setPreferredSize(new Dimension(180, 180));
        lblVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        panel.add(lblVistaPrevia, BorderLayout.CENTER);
        return panel;
    }
    
    private void mostrarVistaPrevia(File file) {
        try {
            ImageIcon icon = new ImageIcon(file.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            lblVistaPrevia.setIcon(new ImageIcon(img));
            lblVistaPrevia.setText("");
        } catch (Exception e) {
            limpiarVistaPrevia();
        }
    }
    
    private void limpiarVistaPrevia() {
        lblVistaPrevia.setIcon(null);
        lblVistaPrevia.setText("Sin imagen");
    }
    
    private boolean esImagenValida(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        
        String nombre = file.getName().toLowerCase();
        return nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || 
               nombre.endsWith(".png") || nombre.endsWith(".gif");
    }
    
    private RenItem buscarItemPorCodigo(int codigo) {
        for (RenItem item : items) {
            if (item.getCodigoitem() == codigo) {
                return item;
            }
        }
        return null;
    }
}