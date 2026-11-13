package Modelo;
import Enumeradores.EstadoEmpleados;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Vendedor extends Empleado{
    private List<Cliente> clientes;
    private List<Venta> ventas;

    public Vendedor(String nombre, String apellido, String dni, String domicilio, String correo, int id, EstadoEmpleados estado, LocalDate fechaIngreso) {
        super(nombre, apellido, dni, domicilio, correo, id, estado, fechaIngreso);
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    @Override
    public String toString() {
        return "Vendedor[" + "clientes: " + clientes + ", ventas: " + ventas + ']';
    }

    @Override
    public String getIdUnico() {
        return this.getDni();
    }
}
