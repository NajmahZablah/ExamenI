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
public class AgregarItem {
    
    private ArrayList<RenItem> items;

    public AgregarItem(ArrayList<RenItem> items) {
        this.items = items;
        iniciarProceso();
    }

    private void iniciarProceso() {
        // Elegir tipo de ítem
        String[] opciones = {"Movie", "Game"};
        int tipoSeleccionado = JOptionPane.showOptionDialog(
            null,
            "Seleccione el tipo de ítem que desea agregar:",
            "Agregar Ítem",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (tipoSeleccionado == -1) {
            return; // Usuario canceló
        }
        
        try {
            // Solicitar código
            String codigoStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el código del ítem:",
                "Código",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (codigoStr == null || codigoStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un código válido.");
                return;
            }
            
            int codigo = Integer.parseInt(codigoStr.trim());
            
            // Validar código único
            for (RenItem item : items) {
                if (item.getCodigoitem() == codigo) {
                    JOptionPane.showMessageDialog(
                        null,
                        "El código ya existe. Por favor use otro código.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
            }
            
            // Solicitar nombre
            String nombre = JOptionPane.showInputDialog(
                null,
                "Ingrese el nombre del ítem:",
                "Nombre",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un nombre válido.");
                return;
            }
            
            // Solicitar precio
            String precioStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el precio base de renta (Lps):",
                "Precio",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (precioStr == null || precioStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un precio válido.");
                return;
            }
            
            double precio = Double.parseDouble(precioStr.trim());
            
            if (precio <= 0) {
                JOptionPane.showMessageDialog(null, "El precio debe ser mayor a cero.");
                return;
            }
            
            // Solicitar cantidad de copias
            String copiasStr = JOptionPane.showInputDialog(
                null,
                "Ingrese la cantidad de copias:",
                "Copias",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (copiasStr == null || copiasStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad válida.");
                return;
            }
            
            int copias = Integer.parseInt(copiasStr.trim());
            
            if (copias < 0) {
                JOptionPane.showMessageDialog(null, "La cantidad de copias no puede ser negativa.");
                return;
            }
            
            // Seleccionar imagen
            String rutaImagen = seleccionarImagen();
            
            if (rutaImagen == null) {
                JOptionPane.showMessageDialog(
                    null,
                    "Debe seleccionar una imagen para el ítem.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            // Crear el ítem según el tipo seleccionado
            if (tipoSeleccionado == 0) {
                // Movie
                Movie movie = new Movie(codigo, nombre, precio, copias, rutaImagen);
                
                // Preguntar si desea establecer fecha de estreno
                int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea establecer una fecha de estreno personalizada?\n(Si selecciona 'No', se usará la fecha actual)",
                    "Fecha de Estreno",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    establecerFechaEstreno(movie);
                }
                
                items.add(movie);
                
                JOptionPane.showMessageDialog(
                    null,
                    "Película agregada exitosamente.\n\n" +
                    "Código: " + codigo + "\n" +
                    "Nombre: " + nombre + "\n" +
                    "Estado: " + movie.getEstado() + "\n" +
                    "Copias: " + copias,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } else {
                // Game
                Game game = new Game(codigo, nombre, precio, copias, rutaImagen);
                
                // Preguntar si desea establecer fecha de publicación
                int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea establecer una fecha de publicación personalizada?\n(Si selecciona 'No', se usará la fecha actual)",
                    "Fecha de Publicación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    establecerFechaPublicacion(game);
                }
                
                items.add(game);
                
                JOptionPane.showMessageDialog(
                    null,
                    "Videojuego agregado exitosamente.\n\n" +
                    "Código: " + codigo + "\n" +
                    "Nombre: " + nombre + "\n" +
                    "Copias: " + copias,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Por favor ingrese valores numéricos válidos.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al agregar el ítem: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private String seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        
        File directorioImagenes = new File("Imagenes Movie");
        if (directorioImagenes.exists()) {
            fileChooser.setCurrentDirectory(directorioImagenes);
        }
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
            "Archivos de imagen (*.jpg, *.jpeg, *.png, *.gif)",
            "jpg", "jpeg", "png", "gif"
        );
        fileChooser.setFileFilter(filtro);
        fileChooser.setDialogTitle("Seleccionar imagen del ítem");
        
        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            return archivoSeleccionado.getAbsolutePath();
        }
        
        return null;
    }
    
    private void establecerFechaEstreno(Movie movie) {
        try {
            String anioStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el año de estreno:",
                "Año",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (anioStr == null) return;
            
            String mesStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el mes (1-12):",
                "Mes",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (mesStr == null) return;
            
            String diaStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el día (1-31):",
                "Día",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (diaStr == null) return;
            
            int anio = Integer.parseInt(anioStr.trim());
            int mes = Integer.parseInt(mesStr.trim());
            int dia = Integer.parseInt(diaStr.trim());
            
            if (mes < 1 || mes > 12 || dia < 1 || dia > 31) {
                JOptionPane.showMessageDialog(
                    null,
                    "Fecha inválida. Se usará la fecha actual.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            Calendar fecha = Calendar.getInstance();
            fecha.set(Calendar.YEAR, anio);
            fecha.set(Calendar.MONTH, mes - 1);
            fecha.set(Calendar.DAY_OF_MONTH, dia);
            movie.setFechaEstreno(fecha);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error en el formato de la fecha. Se usará la fecha actual.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
    
    private void establecerFechaPublicacion(Game game) {
        try {
            String anioStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el año de publicación:",
                "Año",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (anioStr == null) return;
            
            String mesStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el mes (1-12):",
                "Mes",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (mesStr == null) return;
            
            String diaStr = JOptionPane.showInputDialog(
                null,
                "Ingrese el día (1-31):",
                "Día",
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (diaStr == null) return;
            
            int anio = Integer.parseInt(anioStr.trim());
            int mes = Integer.parseInt(mesStr.trim());
            int dia = Integer.parseInt(diaStr.trim());
            
            if (mes < 1 || mes > 12 || dia < 1 || dia > 31) {
                JOptionPane.showMessageDialog(
                    null,
                    "Fecha inválida. Se usará la fecha actual.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            game.setFechaPublicacion(anio, mes, dia);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error en el formato de la fecha. Se usará la fecha actual.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}