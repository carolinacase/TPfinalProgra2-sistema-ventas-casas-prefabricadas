package Modelo;

import java.time.LocalDate;
import java.util.Locale;

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
    public String toString() {   // FORMATO LEGIBLE, CON EL MONTO COMPLETO EN FORMATO ARGENTINO
        return "Pago del " + fecha + " por $" + String.format(Locale.of("es", "AR"), "%,.2f", monto);
    }
}
