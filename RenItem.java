/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public abstract class RenItem {
    int codigoitem;
     String nombreItem;
     double precioBrenta;
     int cantcopias;
     String imagenitem;

    public RenItem(int codigoitem, String nombreItem, double precioBrenta, int cantcopias, String imagenitem) {
        this.codigoitem = codigoitem;
        this.nombreItem = nombreItem;
        this.precioBrenta = precioBrenta;
        this.cantcopias = 0;
        this.imagenitem = imagenitem;
    }

   public toString(){}
   public abstract double pagoRenta(int dias);

    public int getCodigoitem() {
        return codigoitem;
    }

    public int getCantcopias() {
        return cantcopias;
    }

    public String getImagenitem() {
        return imagenitem;
    }
   
}
