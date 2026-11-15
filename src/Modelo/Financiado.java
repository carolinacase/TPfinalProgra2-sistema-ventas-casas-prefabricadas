package Modelo;

import Enumeradores.EstadoVenta;
import Enumeradores.PlanFinanciacion;
import Excepciones.AnticipoInsuficienteException;

import java.time.LocalDate;

public class Financiado extends Venta{
    private static final double PRIMER_ANTICIPO = 0.30;
    private double anticipo;
    private PlanFinanciacion cantidadCuotas;
    private Credito credito;

    public Financiado(LocalDate fechaSenia, double montoSenia, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, double anticipo, PlanFinanciacion cantidadCuotas, Credito credito) {
        super(fechaSenia, montoSenia, cliente, vendedor, cotizacion);
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
                ", cantidadCuotas: " + cantidadCuotas +
                ", credito: " + credito + ')';
    }

    @Override
    public double calcularSaldo() {
        return getPrecioTotal() - getMontoSenia() - anticipo - getMontoPagado();
    }

}