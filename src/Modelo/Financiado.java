package Modelo;

import Enumeradores.EstadoVenta;
import Enumeradores.PlanFinanciacion;
import Excepciones.AnticipoInsuficienteException;

import java.time.LocalDate;

public class Financiado extends Venta{
    private static final double PRIMER_ANTICIPO = 0.30;
    private double anticipo;
    private LocalDate fechaVtoAnticipo;
    private PlanFinanciacion financiacion;
    private Credito credito;

    public Financiado(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado,
                      Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, double anticipo,
                      LocalDate fechaVtoAnticipo, PlanFinanciacion financiacion, Credito credito) {
        super(idVenta, fechaSenia, montoSenia, estado, cliente, vendedor, cotizacion);
        this.anticipo = anticipo;
        this.fechaVtoAnticipo = fechaVtoAnticipo;
        this.financiacion = financiacion;
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

    public PlanFinanciacion getFinanciacion() {
        return financiacion;
    }

    public Credito getCredito() {
        return credito;
    }

    // Multiplicamos la cotizacion y el precio calculado por el primer anticipo.
    // Luego retornamos el anticipo.
    public double calcularAnticipo(){
        anticipo = getCotizacion().calcularPrecio() * PRIMER_ANTICIPO;

        return anticipo;

    }

    // Si el anticipo es menor al minimo, lanzamos AnticipoInsuficienteException.
    // Para obtener el anticipo minimo, multiplicamos la cotizacion y el precio minimo por el primer anticipo.
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
                ", financiación: " + financiacion +
                ", credito: " + credito + ')';
    }

    //Para calcular el saldo, restamos su precio total con el monto de la seña,
    // su anticipo y el monto pagado.
    @Override
    public double calcularSaldo() {
        return getPrecioTotal() - getMontoSenia() - anticipo - getMontoPagado();
    }

}