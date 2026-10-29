package models;

import java.time.LocalDate;

public class Financiado extends Venta{
   private static final double TASA_MENSUAL = 0.02;
   private static final double PRIMER_ANTICIPO = 0.30;
   private double anticipo;
   private LocalDate fechaVtoAnticipo;
   private int cantidadCuotas;
   private Credito credito;

    public Financiado(double anticipo, LocalDate fechaVtoAnticipo, int cantidadCuotas, Credito credito) {
        this.anticipo = anticipo;
        this.fechaVtoAnticipo = fechaVtoAnticipo;
        this.cantidadCuotas = cantidadCuotas;
        this.credito = credito;
    }

    public
}
