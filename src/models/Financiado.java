package models;

import java.time.LocalDate;

public class Financiado extends Venta{
    private static final double TASA_MENSUAL = 0.02;
    private static final double PRIMER_ANTICIPO = 0.30;
    private double anticipo;
    private LocalDate fechaVtoAnticipo;
    private int cantidadCuotas;
    private Credito credito;

    public Financiado(int idVenta, LocalDate fechaSenia, double montoSenia, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion,
                      double anticipo, LocalDate fechaVtoAnticipo, int cantidadCuotas, Credito credito) {
        super(idVenta, fechaSenia, montoSenia, cliente, vendedor, cotizacion);
        this.anticipo = anticipo;
        this.fechaVtoAnticipo = fechaVtoAnticipo;
        this.cantidadCuotas = cantidadCuotas;
        this.credito = credito;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public LocalDate getFechaVtoAnticipo() {
        return fechaVtoAnticipo;
    }

    public void setFechaVtoAnticipo(LocalDate fechaVtoAnticipo) {
        this.fechaVtoAnticipo = fechaVtoAnticipo;
    }

    @Override
    public String toString() {
        return "Financiado(" + "anticipo: " + anticipo +
                ", fechaVtoAnticipo:" + fechaVtoAnticipo +
                ", cantidadCuotas: " + cantidadCuotas +
                ", credito: " + credito + ')';
    }

    @Override
    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    @Override
    public double calcularCuotas() {
        return calcularSaldo() / cantidadCuotas;
    }
}