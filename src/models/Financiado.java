package models;

import enums.EstadoVenta;
import exception.AnticipoInsuficienteException;

import java.time.LocalDate;

public class Financiado extends Venta{
    private static final double TASA_MENSUAL = 0.02;
    private static final double PRIMER_ANTICIPO = 0.30;
    private double anticipo;
    private LocalDate fechaVtoAnticipo;
    private int cantidadCuotas;
    private Credito credito;

    public Financiado(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado,
                      Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, double anticipo,
                      LocalDate fechaVtoAnticipo, int cantidadCuotas, Credito credito) {
        super(idVenta, fechaSenia, montoSenia, estado, cliente, vendedor, cotizacion);
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

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public Credito getCredito() {
        return credito;
    }

    public double calcularAnticipo(){
        anticipo = getCotizacion().calcularPrecio() * PRIMER_ANTICIPO;

        return anticipo;

    }

    public void validarAnticipo() throws AnticipoInsuficienteException {
        double minimo = getCotizacion().calcularPrecio() * PRIMER_ANTICIPO;
        if (anticipo < minimo) {
            throw new AnticipoInsuficienteException("El anticipo debe ser de al menos $" + minimo);
        }
    }

    @Override
    public String toString() {
        return "Financiado(" + "anticipo: " + anticipo +
                ", fechaVtoAnticipo:" + fechaVtoAnticipo +
                ", cantidadCuotas: " + cantidadCuotas +
                ", credito: " + credito + ')';
    }

    @Override
    public double calcularSaldo() {
        return getCotizacion().calcularPrecio() - getMontoSenia() - anticipo;
    }

    @Override
    public double calcularCuotas() {
        return calcularSaldo() / (cantidadCuotas * TASA_MENSUAL);
    }
}