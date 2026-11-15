package Modelo;

import Enumeradores.EstadoVenta;
import Enumeradores.PlanContado;

import java.time.LocalDate;

public class Contado extends Venta{
    private PlanContado modalidad;

    public Contado(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, PlanContado modalidad) {
        super(idVenta, fechaSenia, montoSenia, estado, cliente, vendedor, cotizacion);
        this.modalidad = modalidad;
    }

    public PlanContado getModalidad() {
        return modalidad;
    }

    public void setModalidad(PlanContado modalidad) {
        this.modalidad = modalidad;
    }

    // Restamos por el precio total, monto de la seña y el monto a pagar para obtener el saldo.
    @Override
    public double calcularSaldo() {
        return getPrecioTotal() - getMontoSenia() - getMontoPagado();
    }



}