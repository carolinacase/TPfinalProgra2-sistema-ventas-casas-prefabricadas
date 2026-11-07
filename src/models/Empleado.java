package models;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Empleado extends Persona{
    private int id;
    private boolean activo;
    private LocalDate fechaIngreso;

    public Empleado(String nombre, String apellido, String dni, String domicilio, String correo, int id, boolean activo, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo);
        this.id = id;
        this.activo = activo;
        this.fechaIngreso = fechaIngreso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
    public String getNombreCompleto() {
        return getNombre() + " - " + getApellido();
    }
}
