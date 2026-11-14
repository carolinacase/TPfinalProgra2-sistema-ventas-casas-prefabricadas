package Modelo;

import Enumeradores.EstadoVenta;
import Enumeradores.PlanContado;

import java.time.LocalDate;

public class Contado extends Venta{
    private PlanContado modalidad;

    public Contado(LocalDate fechaSenia, double montoSenia, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, PlanContado modalidad) {
        super(fechaSenia, montoSenia, cliente, vendedor, cotizacion);
        this.modalidad = modalidad;
    }


    public PlanContado getModalidad() {
        return modalidad;
    }

    public void setModalidad(PlanContado modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public double calcularSaldo() {
        return getPrecioTotal() - getMontoSenia() - getMontoPagado();
    }


}