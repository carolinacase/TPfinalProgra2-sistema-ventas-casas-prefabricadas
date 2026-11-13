package Modelo;

import Enumeradores.EstadoVenta;
import Excepciones.VentaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Venta{
    private int idVenta;
    private LocalDate fechaSenia;
    private LocalDate fechaVto;
    private double montoSenia;
    private double montoPagado;
    private double precioTotal;
    private List<Pago> historialDePagos;
    private EstadoVenta estado;
    private Cliente cliente;
    private Vendedor vendedor;
    private Cotizacion cotizacion;

    public Venta(int idVenta, LocalDate fechaSenia, double montoSenia, EstadoVenta estado, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion) {
        this.idVenta = idVenta;
        this.fechaSenia = fechaSenia;
        this.fechaVto = fechaSenia.plusDays(15);
        this.montoSenia = montoSenia;
        this.montoPagado = 0.0;
        this.precioTotal = cotizacion.calcularPrecio();
        this.historialDePagos = new ArrayList<>();
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

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public List<Pago> getHistorialDePagos() {
        return historialDePagos;
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

    public void registrarPago(LocalDate fecha, double monto){

        Pago nuevoPago = new Pago(fecha, monto);

        this.historialDePagos.add(nuevoPago);

        this.montoPagado += monto;


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