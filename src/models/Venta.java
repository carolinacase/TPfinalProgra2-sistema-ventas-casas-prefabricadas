package models;

import Interface.IPagable;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Venta implements IPagable {
    private int idVenta;
    private LocalDate fechaSenia;
    private double montoSenia;
    private Cliente cliente;
    private Vendedor vendedor;
    private Cotizacion cotizacion;

    public Venta(int idVenta, LocalDate fechaSenia, double montoSenia, Cliente cliente, Vendedor vendedor, Cotizacion cotizacion) {
        this.idVenta = idVenta;
        this.fechaSenia = fechaSenia;
        this.montoSenia = montoSenia;
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

    public void setMontoSenia(double montoSenia) {
        this.montoSenia = montoSenia;
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

    public void validar(){
        if(cliente == null || vendedor == null || cotizacion == null){
            throw new IllegalArgumentException("Faltan datos para registrar la venta");
        }
    }

    public double calcularSaldo(){
        return cotizacion.calcularPrecio() - montoSenia;
    }

    @Override
    public double calcularCuotas() {
        return 0;
    }

    @Override
    public int getCantidadCuotas() {
        return 0;
    }
}