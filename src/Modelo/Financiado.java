package Modelo;

import Enumeradores.EstadoVenta;
import Enumeradores.PlanFinanciacion;
import Excepciones.AnticipoInsuficienteException;

import java.time.LocalDate;
import java.util.Locale;

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

    // Multiplicamos por el precio de la cotizacion y el primer anticipo
    public double calcularAnticipo(){
        anticipo = getCotizacion().calcularPrecio() * PRIMER_ANTICIPO;

        return anticipo;

    }

    // ANTICIPO MINIMO PARA UN PRECIO DADO (30%). ES static PORQUE Empresa LO NECESITA ANTES DE CREAR LA VENTA
    public static double calcularAnticipoMinimo(double precioTotal) {
        return precioTotal * PRIMER_ANTICIPO;
    }

    // Hacemos lo mismo para validar el anticipo
    // Por otro lado, si el anticipo es menor al minimo, lanzamos un mensaje con AnticipoInsuficienteException
    public void validarAnticipo() throws AnticipoInsuficienteException {

        double minimo = calcularAnticipoMinimo(getCotizacion().calcularPrecio());

        if (anticipo < minimo) {
            throw new AnticipoInsuficienteException("El anticipo debe ser de al menos $" + String.format(Locale.of("es", "AR"), "%,.2f", minimo));   // MONTO COMPLETO, SIN NOTACION E
        }

    }

    @Override
    public String toString() {
        return "Financiado(" + "anticipo: " + anticipo +
                ", cantidadCuotas: " + cantidadCuotas +
                ", credito: " + credito + ')';
    }

    // Para calcular el saldo, restamos por el precio total, el monto de la seña, su anticipo y su monto pagado.
    @Override
    public double calcularSaldo() {
        return getPrecioTotal() - getMontoSenia() - anticipo - getMontoPagado();
    }

}