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

public class Game extends RenItem {

    Calendar fechaPublicacion;
    ArrayList<String> especificaciones;

    public Game(int codigo, String nombre, double precioBaseRenta, int copias, String imagen) {
        super(codigo, nombre, precioBaseRenta, copias, imagen);

        fechaPublicacion = Calendar.getInstance();
        especificaciones = new ArrayList<>();
    }

    @Override
    public double pagoRenta(int dias) {
        return 20 * dias; // precio fijo
    }

    public void setFechaPublicacion(int year, int mes, int dia) {
        fechaPublicacion.set(Calendar.YEAR, year);
        fechaPublicacion.set(Calendar.MONTH, mes - 1);
        fechaPublicacion.set(Calendar.DAY_OF_MONTH, dia);
    }

    public Calendar getFechaPublicacion() {
        return fechaPublicacion;
    }

    // Recursivo para imprimir especificaciones
    public void listEspecificaciones() {
        listarRec(0);
    }

    private void listarRec(int pos) {
        if (pos >= especificaciones.size()) return;

        JOptionPane.showMessageDialog(null, especificaciones.get(pos));
        listarRec(pos + 1);
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

}
