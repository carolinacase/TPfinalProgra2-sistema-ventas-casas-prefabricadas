package Modelo;

import Enumeradores.EstadoEmpleados;
import Enumeradores.SiNo;
import Excepciones.DocumentacionIncompletaException;

import java.time.LocalDate;

public class Administrativo extends Empleado{

    public Administrativo(String nombre, String apellido, String dni, String domicilio, String correo, int id, EstadoEmpleados estado, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo, id, estado, fechaIngreso);
    }

    public void aprobarCredito(Financiado f) throws DocumentacionIncompletaException {

        if (!f.getCredito().verificarCredito()) {

            throw new DocumentacionIncompletaException("Falta documentacion para el credito");

        }

        f.getCredito().setAprobado(SiNo.SI);

    }


    @Override
    public String toString() {
        return "Administrativo | " + super.toString();
    }

    @Override
    public String getIdUnico() {
        return this.getDni();
    }
}
