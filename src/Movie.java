/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Calendar;

/**
 *
 * @author najma
 */
public class Movie extends RenItem {
    
    // atributo
    private Calendar fechaEstreno;
    
    // constructor
    public Movie(int codigoItem, String nombreItem, double precioBaseRenta, 
            int cantcopias, String imagenitem) {
        super(codigoItem, nombreItem, precioBaseRenta, cantcopias, imagenitem);
        this.fechaEstreno = Calendar.getInstance();
    }
    
    // Getter
    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }
    
    // Setter
    public void setFechaEstreno(Calendar fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
    
    // Método claves
    public String getEstado() {
        Calendar fechaActual = Calendar.getInstance();
        
        int anioDiferencia = fechaActual.get(Calendar.YEAR) - fechaEstreno.get(Calendar.YEAR);
        int mesDiferencia = fechaActual.get(Calendar.MONTH) - fechaEstreno.get(Calendar.MONTH);
        int totalMeses = anioDiferencia * 12 + mesDiferencia;
        
        if (totalMeses <= 3) {
            return "ESTRENO";
        } else {
            return "NORMAL";
        }
    }
    
    @Override 
    public String toString() {
        return super.toString() + " - Movie";
    }
    
    @Override 
    public double pagoRenta(int dias) {
        double cargo = 0;
        String estado = getEstado();
        
        if (estado.equals("ESTRENO")) {
            if (dias > 2) {
                int diasAdicionales = dias - 2;
                cargo = super.pagoRenta(dias) + (diasAdicionales * 50);
            } else {
                cargo = super.pagoRenta(dias);
            }
        } else {
            if (dias > 5) {
                int diasAdicionales = dias - 5;
                cargo = super.pagoRenta(dias) + (diasAdicionales * 30);
            } else {
                cargo = super.pagoRenta(dias);
            }
        }
        
        return cargo;
    }
}
