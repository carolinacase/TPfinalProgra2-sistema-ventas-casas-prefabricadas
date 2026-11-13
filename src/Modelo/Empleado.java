package Modelo;

import Enumeradores.EstadoEmpleados;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Empleado extends Persona{
    private int id;
    private EstadoEmpleados estado;
    private LocalDate fechaIngreso;

    public Empleado(String nombre, String apellido, String dni, String domicilio, String correo, int id, EstadoEmpleados estado, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo);
        this.id = id;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoEmpleados getEstado() {
        return estado;
    }

    public void setEstado(EstadoEmpleados estado) {
        this.estado = estado;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Empleado empleado = (Empleado) o;
        return id == empleado.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", estado=" + estado +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}
