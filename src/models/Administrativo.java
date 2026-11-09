package models;

import enums.EstadoFabricacion;

import java.time.LocalDate;

public class Administrativo extends Empleado{
    public Administrativo(String nombre, String apellido, String dni, String domicilio, String correo, int id, boolean activo, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo, id, activo, fechaIngreso);
    }

    public void aprobarCredito(Credito c){
        c.setAprobado(true);
    }
    public Fabrica registrarPedidoFabrica(LocalDate pedido, LocalDate despacho, LocalDate entrega, EstadoFabricacion estado){
        Fabrica f = new Fabrica(pedido,despacho,entrega,estado);
        return f;
    }


    @Override
    public String toString() {
        return "Administrativo" + super.toString();
    }
}
