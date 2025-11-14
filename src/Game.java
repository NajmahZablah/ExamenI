/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author najma
 */
import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Game extends RenItem implements MenuActions {

    Calendar fechaPublicacion;
    ArrayList<String> especificaciones;

    public Game(int codigo, String nombre, double precioBaseRenta, int copias, String rutaImagen) {
        super(codigo, nombre, precioBaseRenta, copias, rutaImagen);

        fechaPublicacion = Calendar.getInstance();
        especificaciones = new ArrayList<>();
    }

    @Override
    public double pagoRenta(int dias) {
        return 20 * dias;
    }

    public void setFechaPublicacion(int year, int mes, int dia) {
        fechaPublicacion.set(Calendar.YEAR, year);
        fechaPublicacion.set(Calendar.MONTH, mes - 1);
        fechaPublicacion.set(Calendar.DAY_OF_MONTH, dia);
    }

    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void listEspecificaciones() {
    listEspecificaciones(0);
}

    public void listEspecificaciones(int pos) {
        if (pos >= especificaciones.size()) 
            return;
    JOptionPane.showMessageDialog(null, especificaciones.get(pos));
    listEspecificaciones(pos + 1);
}


    @Override
    public String toString() {
        return super.toString() +
               ", Fecha Publicación: " +
               fechaPublicacion.get(Calendar.DAY_OF_MONTH) + "/" +
               (fechaPublicacion.get(Calendar.MONTH) + 1) + "/" +
               fechaPublicacion.get(Calendar.YEAR) +
               " – PS3 Game";
    }
    
    @Override
    public void submenu() {
        new Submenu(this); 
    }


    @Override
        public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                try {
                    int anio = Integer.parseInt(JOptionPane.showInputDialog("Año:"));
                    int mes = Integer.parseInt(JOptionPane.showInputDialog("Mes (1-12):"));
                    int dia = Integer.parseInt(JOptionPane.showInputDialog("Día (1-31):"));
                    setFechaPublicacion(anio, mes, dia);
                    JOptionPane.showMessageDialog(null, "Fecha actualizada exitosamente!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar fecha");
                }
                break;

            case 2:
                String esp = JOptionPane.showInputDialog("Ingrese la especificación:");
                if (esp != null && !esp.trim().isEmpty()) {
                    especificaciones.add(esp);
                    JOptionPane.showMessageDialog(null, "Especificación agregada!");
                }
                break;

            case 3:
                if (especificaciones.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay especificaciones registradas.");
                } else {
                    listEspecificaciones();
                }
                break;

            case 4:
                JOptionPane.showMessageDialog(null, "Saliendo del submenú...");
                break;

            default:
                JOptionPane.showMessageDialog(null, "Opción inválida");
        }
    }
}
