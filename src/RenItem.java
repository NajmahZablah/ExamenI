/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.ImageIcon;

/**
 *
 * @author andre
 */
public abstract class RenItem {
     
    int codigoitem;
    String nombreItem;
    double precioBaseRenta;
    int cantcopias;
    ImageIcon imagenitem;

    public RenItem(int codigoitem, String nombreItem, double precioBaseRenta, int cantcopias, String rutaImagen) {
        this.codigoitem = codigoitem;
        this.nombreItem = nombreItem;
        this.precioBaseRenta = precioBaseRenta;
        this.cantcopias = cantcopias;

        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            this.imagenitem = new ImageIcon(rutaImagen);
        }
    }

    public int getCodigoitem() {
        return codigoitem;
    }

    public String getNombreItem() {
        return nombreItem;
    }

    public double getPrecioBaseRenta() {
        return precioBaseRenta;
    }

    public int getCantcopias() {
        return cantcopias;
    }

    public ImageIcon getImagenitem() {
        return imagenitem;
    }

    public void setImagenitem(ImageIcon imagenitem) {
        this.imagenitem = imagenitem;
    }
    
    public boolean hayDisponibilidad() {
        return cantcopias > 0;
    }
    
    public boolean rentarCopia() {
        if (cantcopias > 0) {
            cantcopias--;
            return true;
        }
        return false;
    }
    
    public void devolverCopia() {
        cantcopias++;
    }
    
    public abstract double pagoRenta(int dias);
    
    @Override
    public String toString() {
        return "Codigo: " + codigoitem +
               ", Nombre: " + nombreItem +
               ", Precio Base: Lps. " + precioBaseRenta +
               ", Copias: " + cantcopias;
    }
}