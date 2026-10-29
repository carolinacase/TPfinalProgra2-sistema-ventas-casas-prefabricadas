package models;

import java.time.LocalDate;

public class Administrativo extends Empleado{
    public Administrativo(String nombre, String apellido, String dni, String domicilio, String correo, int id, boolean activo, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo, id, activo, fechaIngreso);
    }
}
