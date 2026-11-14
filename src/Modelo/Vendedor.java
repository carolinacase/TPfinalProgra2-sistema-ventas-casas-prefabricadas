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

    public void argegarCliente(Cliente cliente){clientes.add(cliente);}
    public void agregarVenta(Venta venta){ventas.add(venta);}

    public Cliente eliminarCliente(int dni){return clientes.remove(dni);}
    public Venta eliminarVenta(int idVenta){return ventas.remove(idVenta);}

    public Cliente mostrarCliente(int dni){return clientes.get(dni);}
    public Venta mostrarVenta(int idVenta){return ventas.get(idVenta);}

    public String listarClientes(){
        StringBuilder sb=new StringBuilder();
        for(Cliente c:clientes) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }

    public String listarVentas(){
        StringBuilder sb =new StringBuilder();
        for(Venta v:ventas){
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }


//    @Override
//    public String toString() {
//        return "Vendedor[" + "clientes: " + clientes + ", ventas: " + ventas + ']';
//    }

    @Override
    public String getIdUnico() {
        return this.getDni();
    }
}
