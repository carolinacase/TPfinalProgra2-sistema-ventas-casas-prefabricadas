package Modelo;
import Enumeradores.EstadoEmpleados;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Vendedor extends Empleado{
    private Map<String, Cliente> clientes;
    private Map<Integer, Venta> ventas;

    public Vendedor(String nombre, String apellido, String dni, String domicilio,
                    String correo, int id, EstadoEmpleados estado, LocalDate fechaIngreso) {

        super(nombre, apellido, dni, domicilio, correo, id, estado, fechaIngreso);

        this.clientes = new HashMap<>();
        this.ventas = new HashMap<>();
    }

    public void agregarClienteDeVendedor(Cliente cliente) {

        clientes.put(cliente.getDni(), cliente);

    }

    public void agregarVentaDeVendedor(Venta venta) {

        ventas.put(venta.getIdVenta(), venta);

    }

    public boolean eliminarClienteDeVendedor(String dni) {

        return clientes.remove(dni) != null;

    }

    public boolean eliminarVentaDeVendedor(int idVenta) {

        return ventas.remove(idVenta) != null;

    }

    public Cliente mostrarClienteDeVendedor(String dni) {

        return clientes.get(dni);

    }

    public Venta mostrarVentaDeVendedor(int idVenta) {

        return ventas.get(idVenta);

    }

    public String listarClientesDeVendedor() {

        StringBuilder sb = new StringBuilder();

        int i = 1;

        for (Cliente c : clientes.values()) {

            sb.append(i++).append(". ").append(c.toString()).append("\n");

        }

        return sb.toString();
    }

    public String listarVentasDeVendedor() {

        StringBuilder sb = new StringBuilder();

        int i = 1;

        for (Venta v : ventas.values()) {

            sb.append(i++).append(". ").append(v.toString()).append("\n");

        }
        return sb.toString();

    }

    @Override
    public String toString() {   // ASI EN LOS LISTADOS SE VE QUE ES VENDEDOR
        return "Vendedor | " + super.toString();
    }

    @Override
    public String getIdUnico() {

        return this.getDni();

    }
}
