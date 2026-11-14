package Gestora;

import Modelo.Cliente;
import Modelo.Contado;
import Modelo.Cotizacion;
import Modelo.Vendedor;

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
        int opcion = 0;

        do{
            menuPrincipal();
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    menuVentas();
                    opcion = sc.nextInt();
                    sc.nextLine();

                    do{
                        switch(opcion){
                            case 1:
                                System.out.println("Que tipo de venta va a ser (Contado/Financiado)?: ");
                                String tipoVenta = sc.nextLine();

                                if(tipoVenta.equals("Contado")){


                                }


                                break;
                        }

                    }while (opcion != 0);

                    break;
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
        Cliente cliente = null;
        Vendedor vendedor = null;
        Cotizacion cotizacion = null;

        Scanner sc = new Scanner(System.in);

        LocalDate fechaDeHoy = LocalDate.now();

        System.out.println("Ingrese el monto de la seña: ");
        double montoSenia = sc.nextDouble();
        sc.nextLine();


        System.out.println("Ingrese el nombre del cliente: ");
        String nombreCliente = sc.nextLine();

        System.out.println("Ingrese el apellido del cliente: ");
        String apellidoCliente = sc.nextLine();

        System.out.println("Ingrese el DNI del cliente: ");
        String dniCliente = sc.nextLine();




    }




}




































