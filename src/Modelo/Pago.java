package Modelo;

import java.time.LocalDate;

public class Pago {
    private LocalDate fecha;
    private double monto;

    public Pago(LocalDate fecha, double monto) {
        this.fecha = fecha;
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "fecha=" + fecha +
                ", monto=" + monto +
                '}';
    }
}
