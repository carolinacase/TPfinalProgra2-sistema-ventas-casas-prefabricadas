package Modelo;

import Enumeradores.EstadoVenta;
import Excepciones.VentaInvalidaException;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Venta{
    private int idVenta;
    private LocalDate fechaSenia;
    private LocalDate fechaVto = fechaSenia.plusDays(15);
    private double montoSenia;
    private EstadoVenta estado;
    private Cliente cliente;
    private Vendedor vendedor;
    private Cotizacion cotizacion;

    public Venta(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion) {
        this.idVenta = idVenta;
        this.fechaSenia = fechaSenia;
        this.montoSenia = montoSenia;
        this.estado = estado;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.cotizacion = cotizacion;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaSenia() {
        return fechaSenia;
    }

    public void setFechaSenia(LocalDate fechaSenia) {
        this.fechaSenia = fechaSenia;
    }

    public double getMontoSenia() {
        return montoSenia;
    }

    public LocalDate getFechaVto() {
        return fechaVto;
    }

    public void setMontoSenia(double montoSenia) {
        this.montoSenia = montoSenia;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public Cotizacion getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(Cotizacion cotizacion) {
        this.cotizacion = cotizacion;
    }

    public abstract double calcularSaldo();

    public void validar() throws VentaInvalidaException {
            if (cliente == null) {
                throw new VentaInvalidaException("La venta no tiene cliente asignado");
            }
            if (vendedor == null) {
                throw new VentaInvalidaException("La venta no tiene vendedor asignado");
            }
            if (cotizacion == null) {
                throw new VentaInvalidaException("La venta no tiene una cotización asociada");
            }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return idVenta == venta.idVenta;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idVenta);
    }

    @Override
    public String toString(){
        return "Venta[ idVenta: " + idVenta + ", fechaSenia: " + fechaSenia + ", montoSenia: " + montoSenia
                + ", cliente: " + cliente +  ", vendedor: " + vendedor + ", cotización: " + "]";
    }
}