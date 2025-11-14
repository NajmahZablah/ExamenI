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

    public RenItem(int codigoitem, String nombreItem, double precioBaseRenta, int cantcopias, String imagenitem) {
        this.codigoitem = codigoitem;
        this.nombreItem = nombreItem;
        this.precioBaseRenta = precioBaseRenta;
        this.cantcopias = 0;
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


    @Override
    public String toString() {
        return "Codigo: " + codigoitem +
               ", Nombre: " + nombreItem +
               ", Precio Base: Lps. " + precioBaseRenta +
               ", Copias: " + cantcopias;
    }
}

