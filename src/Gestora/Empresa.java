package Gestora;

import Enumeradores.IncluyePlatea;
import Enumeradores.MedioContacto;
import Enumeradores.ModeloCasa;
import Enumeradores.PlanContado;
import Modelo.*;

import java.time.LocalDate;
import java.util.List;
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
        int opcion;

        do{
            menuPrincipal();
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    int opcionVentas;

                    do{
                        menuVentas();
                        opcionVentas = sc.nextInt();
                        sc.nextLine();

                        switch(opcionVentas){
                            case 1:
                                System.out.println("Que tipo de venta va a ser (Contado/Financiado)?: ");
                                String tipoVenta = sc.nextLine();

                                if(tipoVenta.equalsIgnoreCase("Contado")){

                                    Contado newContado = ingresarVentaContado();

                                    ventas.agregar(, newContado);

                                } else if (tipoVenta.equalsIgnoreCase("Financiado")) {



                                }

                                break;
                            case 2:
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
        }while (opcion != 0);
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

    private void menuActualizarVenta(){
        System.out.println("==========MENU ACTUALIZAR==========");
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Monto del anticipo\n" +
                            "2. Estado venta\n" +
                            "3. Modelo de la casa\n" +
                            "0. Salir del menu");
    }

    private Contado ingresarVentaContado(){

        Scanner sc = new Scanner(System.in);

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

        Scanner sc = new Scanner(System.in);

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
        Scanner sc = new Scanner(System.in);

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
        Scanner sc = new Scanner(System.in);

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

    }

    private Credito ingresarCredito(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Tiene el dni presentado?");

    }

}




































