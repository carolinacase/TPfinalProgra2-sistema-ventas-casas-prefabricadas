package models;

import enums.EstadoVenta;
import enums.ModalidadContado;

import java.time.LocalDate;

public class Contado extends Venta{
    private LocalDate fechaVto;
    private ModalidadContado modalidad;

    public Contado(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado,
                   Cliente cliente, Vendedor vendedor, Cotizacion cotizacion, LocalDate fechaVto,
                   ModalidadContado modalidad) {
        super(idVenta, fechaSenia, montoSenia, estado, cliente, vendedor, cotizacion);
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
    public double calcularSaldo() {
        return getCotizacion().calcularPrecio() - getMontoSenia();
    }

    @Override
    public double calcularCuotas() {
        return calcularSaldo() / this.modalidad.getCantidadCuotas();
    }

}