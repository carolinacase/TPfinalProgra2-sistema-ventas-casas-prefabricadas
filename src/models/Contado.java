package models;

import enums.ModalidadContado;

import java.time.LocalDate;

public class Contado extends Venta{
    private LocalDate fechaVto;
    private ModalidadContado modalidad;

    public Contado(int idVenta, LocalDate fechaSenia, double montoSenia, Cliente cliente, Vendedor vendedor,
                   Cotizacion cotizacion, LocalDate fechaVto, ModalidadContado modalidad) {
        super(idVenta, fechaSenia, montoSenia, cliente, vendedor, cotizacion);
        this.fechaVto = fechaVto;
        this.modalidad = modalidad;
    }

    public LocalDate getFechaVto() {
        return fechaVto;
    }

    public void setFechaVto(LocalDate fechaVto) {
        this.fechaVto = fechaVto;
    }

    public ModalidadContado getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadContado modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public double calcularCuotas() {
        return super.calcularCuotas();
    }

    @Override
    public int getCantidadCuotas() {
        return super.getCantidadCuotas();
    }
}