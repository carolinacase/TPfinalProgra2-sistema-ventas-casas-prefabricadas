package Gestora;

import DAO.ConexionClientes;
import DAO.ConexionVentas;
import Enumeradores.*;
import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Modelo.*;

import java.time.LocalDate;
import java.util.Scanner;


public class Empresa {
    private String nombre;
    private GestoraVentas ventas;
    private GestoraEmpleados empleados;
    private GestoraClientes clientes;
    Scanner sc = new Scanner(System.in);

    public Empresa(String nombre) {
        this.ventas = new GestoraVentas();
        this.empleados = new GestoraEmpleados();
        this.clientes = new GestoraClientes();
        this.nombre = nombre;
    }

    public void consultarEmpresa(){
        cargarBaseDeDatos();   // prepara tablas y trae los clientes antes de mostrar el menú


        int opcionPrincipal;

        do{
            menuPrincipal();
            opcionPrincipal = sc.nextInt();
            sc.nextLine();

            switch(opcionPrincipal){
                case 1:
                    int opcionVentas;

                    do{
                        menuVentas();
                        opcionVentas = sc.nextInt();
                        sc.nextLine();

                        switch(opcionVentas){
                            case 1:
                                System.out.println("Carga de Venta: ");
                                System.out.println("Ingrese tipo de venta -> Contado/Financiado ");
                                String tipoVenta = sc.nextLine();

                                if(tipoVenta.equalsIgnoreCase("Contado")){

                                    Contado newContado = ingresarVentaContado();

                                    //ventas.agregar(, newContado); //Falta traer el id de la BD

                                } else if (tipoVenta.equalsIgnoreCase("Financiado")) {

                                    //Falta implementar ingresarVentaFinanciado

                                }

                                break;
                            case 2:
                                System.out.println("Ingrese el id de la venta a eliminar: ");
                                int idEliminar = sc.nextInt();
                                sc.nextLine();

                                System.out.println(ventas.eliminar(idEliminar) ? "Venta eliminada con exito" : "Venta no se pudo eliminar");

                                break;
                            case 3:

                                ventas.listar();

                                break;
                            case 4:
                                System.out.println("Ingrese el id de la venta a actualizar: ");
                                int idActualizar = sc.nextInt();
                                sc.nextLine();

                                Venta ventaBuscada =  ventas.buscar(idActualizar);

                                ventas.actualizar(ventaBuscada);

                                break;
                            case 5:

                                for(Pago pg : ventas.verificarPagosDeHoy()){

                                    System.out.println(pg);

                                }

                                break;
                            case 0:
                                System.out.println("Saliendo del menú de ventas...");

                                break;
                            default:
                                System.out.println("Opción inválida");
                        }

                    }while (opcionVentas != 0);

                    break;
                case 2:

                    break;
                case 0:

                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while (opcionPrincipal != 0);
    }
    private void cargarBaseDeDatos() {
        try {
            ConexionClientes.crearTablaCliente();   // primero creo bdd clientes
            ConexionVentas.crearTablaVentas();      // después bdd ventas, porque depende de clientes

            // trae de MySQL los clientes guardados y los carga en la gestora
            for (Cliente c : ConexionClientes.obtenerClientes().values()) {
                clientes.agregar(c.getDni(), c);
            }
        } catch (DBConexionException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (DBCreateTableException e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        }
    }

    private void menuPrincipal(){
        System.out.println("==========MENU PRINCIPAL==========");
        System.out.println("1.=====VENTAS=====");
        System.out.println("2.=====EMPLEADOS=====");
        System.out.println("3.=====CLIENTES=====");
        System.out.println("0. Salir");

    }

    private void menuVentas(){
        System.out.println("==========MENU VENTAS==========");
        System.out.println("1.=====Cargar una venta=====");
        System.out.println("2.=====Eliminar una venta=====");
        System.out.println("3.=====Mostrar todas las ventas=====");
        System.out.println("4.=====Actualizar una venta=====");
        System.out.println("5.=====Mostrar los pagos de hoy=====");
        System.out.println("0. Salir");
    }

    private Contado ingresarVentaContado(){

        LocalDate fechaDeHoy = LocalDate.now();

        System.out.println("Ingrese el monto de la seña: ");
        double montoSenia = sc.nextDouble();
        sc.nextLine();

        Cliente cliente = ingresarCliente();

        Vendedor vendedor = elegirVendedor();

        Cotizacion cotizacion = ingresarCotizacion();

        System.out.println("Ingrese su plan de contado (Pago total/ Plan 6/ Plan 12): ");
        String planContado = sc.nextLine();

        Contado contado = new Contado(fechaDeHoy, montoSenia, cliente, vendedor, cotizacion, PlanContado.valueOf(planContado));

        return contado;

    }

    private Cliente ingresarCliente(){

        System.out.println("Ingrese el nombre del cliente: ");
        String nombreCliente = sc.nextLine();

        System.out.println("Ingrese el apellido del cliente: ");
        String apellidoCliente = sc.nextLine();

        System.out.println("Ingrese el DNI del cliente: ");
        String dniCliente = sc.nextLine();

        System.out.println("Ingrese el domicilio del cliente: ");
        String domicilioCliente = sc.nextLine();

        System.out.println("Ingrese el correo del cliente: ");
        String correoCliente = sc.nextLine();

        return new Cliente(nombreCliente, apellidoCliente, dniCliente, domicilioCliente,correoCliente);
    }

    private Cotizacion ingresarCotizacion(){

        System.out.println("Ingrese el medio de contacto (Redes/ Telefono/ Presencial): ");
        String medioContacto = sc.nextLine();

        System.out.println("Ingrese el modelo de la casa (Basico/ Estanar/ Premium): ");
        String modeloCasa = sc.nextLine();

        System.out.println("Ingrese los metros cuadrados del terreno: ");
        double m2Terreno = sc.nextDouble();
        sc.nextLine();

        System.out.println("El terreno incluye platea (Si/No)?: ");
        String platea = sc.nextLine();

        return new Cotizacion(MedioContacto.valueOf(medioContacto), ModeloCasa.valueOf(modeloCasa),
                                m2Terreno, IncluyePlatea.valueOf(platea));

    }

    private Vendedor elegirVendedor(){

        Vendedor vendedorElegido = null;

        System.out.println("Ingrese el nombre del vendedor: ");
        String nombreVendedor = sc.nextLine();


        for(Empleado emp : empleados.listarEmpleadosActivos()){

            if(emp.getNombre().equals(nombreVendedor)){

                vendedorElegido = (Vendedor) emp;

                break;
            }

        }

        return vendedorElegido;
    }

    private Financiado ingresarVentaFinanciado(){

        LocalDate fechaDeHoy = LocalDate.now();

        System.out.println("Ingrese el monto de la seña: ");
        double montoSenia = sc.nextDouble();
        sc.nextLine();

        Cliente cliente = ingresarCliente();

        Vendedor vendedor = elegirVendedor();

        Cotizacion cotizacion = ingresarCotizacion();

        System.out.println("Ingrese el anticipo: ");
        double anticipo =  sc.nextDouble();
        sc.nextLine();

        System.out.println("Ingrese el plan de financiacion: ");
        int plan = sc.nextInt();
        sc.nextLine();

        return null;

    }//Falta terminar

    private Credito ingresarCredito() {

        System.out.println("Presento el dni? (Si/No)");
        String dniCredito = sc.nextLine();

        System.out.println("Presento el recibo de sueldo (Si/No): ");
        String reciboCredito = sc.nextLine();

        System.out.println("Presento el algun servicio (Sin/No): ");
        String servicioCredito = sc.nextLine();


        return new Credito(SiNo.valueOf(dniCredito.toUpperCase()), SiNo.valueOf(reciboCredito.toUpperCase()), SiNo.valueOf(servicioCredito.toUpperCase()));

    }


}




































