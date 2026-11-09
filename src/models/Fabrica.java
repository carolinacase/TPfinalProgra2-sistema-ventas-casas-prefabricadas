package models;

import enums.EstadoFabricacion;

import java.time.LocalDate;

public class Fabrica {
    private LocalDate fechaPedido;
    private LocalDate fechaDespacho;
    private LocalDate fechaEntrega;
    private EstadoFabricacion estado;

    public Fabrica() {
        this.fechaPedido=null;
        this.fechaDespacho=null;
        this.fechaEntrega=null;
        this.estado=null;
    }

    public Fabrica(LocalDate fechaPedido, LocalDate fechaDespacho, LocalDate fechaEntrega, EstadoFabricacion estado) {
        this.fechaPedido = fechaPedido;
        this.fechaDespacho = fechaDespacho;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public LocalDate getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(LocalDate fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoFabricacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoFabricacion estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Fabrica{" +
                "fechaPedido=" + fechaPedido +
                ", fechaDespacho=" + fechaDespacho +
                ", fechaEntrega=" + fechaEntrega +
                ", estado=" + estado +
                '}';
    }
}
