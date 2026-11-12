package models;

import enums.PlanFinanciacion;
import enums.EstadoVenta;

import java.time.LocalDate;

public class Financiado extends Venta{
    private static final double PRIMER_ANTICIPO = 0.30;
    private double anticipo;
    private PlanFinanciacion cantidadCuotas;
    private Credito credito;

    public Financiado(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado, Cliente cliente,
                      Vendedor vendedor, Cotizacion cotizacion, double anticipo,
                      PlanFinanciacion cantidadCuotas, Credito credito) {
        super(idVenta, fechaSenia, montoSenia, estado, cliente, vendedor, cotizacion);
        this.anticipo = anticipo;
        this.cantidadCuotas = cantidadCuotas;
        this.credito = credito;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public double calcularAnticipo(){

        anticipo = getCotizacion().calcularPrecio() * PRIMER_ANTICIPO;

        return anticipo;

    }

    @Override
    public String toString() {
        return "Financiado(" + "anticipo: " + anticipo +
                ", fechaVtoAnticipo:" +
                ", cantidadCuotas: " + cantidadCuotas +
                ", credito: " + credito + ')';
    }

    @Override
    public double calcularSaldo() {
        return getCotizacion().calcularPrecio() - getMontoSenia() - anticipo;
    }

}