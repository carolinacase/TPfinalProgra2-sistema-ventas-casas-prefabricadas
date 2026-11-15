package Gestora;

import DAO.ConexionClientes;
import DAO.ConexionVentas;
import Enumeradores.*;
import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Excepciones.DocumentacionIncompletaException;
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

                                    Contado vc = ingresarVentaContado();

                                    //ventas.agregar(, vc); //Falta traer el id de la BD

                                } else if (tipoVenta.equalsIgnoreCase("Financiado")) {

                                    Financiado vf = ingresarVentaFinanciado();
                                    Administrativo administrativo = elegirAdministrativo();

                                    try{

                                        administrativo.aprobarCredito(vf);

                                        //ventas.agregar(, vf)//Falta traer el id de la BD


                                    }catch (DocumentacionIncompletaException e){

                                        System.out.println(e.getMessage());

                                    }
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

        PlanContado plan = elegirPlanContado();

        Contado contado = new Contado(fechaDeHoy, montoSenia, cliente, vendedor, cotizacion, plan);
        mostrarVenta(contado);

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

    private Cotizacion ingresarCotizacion(){ //LA COTIZACION SE CALCULA EN BASE A LOS M2 COMPRADOS Y SI SE ADQUIERE O NO PLATEA, LA CUAL LLEVARIA LA MISMA SUPERFICIE

        MedioContacto medio = elegirMedioContacto();

        ModeloCasa modelo = elegirModeloCasa();

        System.out.println("Ingrese los metros cuadrados de la casa: ");
        double m2Casa = sc.nextDouble();
        sc.nextLine();

        IncluyePlatea platea = elegirIncluyePlatea();

        return new Cotizacion(medio, modelo, m2Casa, platea);

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

        PlanFinanciacion planElegido = elegirPlanFinanciacion();

        Credito credito = ingresarCredito();


        return new Financiado(fechaDeHoy, montoSenia, cliente, vendedor, cotizacion, anticipo,  planElegido, credito);

    }

    private Credito ingresarCredito() {

        SiNo dniCredito = elegirSiNo("Presentó el DNI?");

        SiNo reciboCredito = elegirSiNo("Presentó el recibo de sueldo?");

        SiNo servicioCredito = elegirSiNo("Presentó algún servicio?");

        return new Credito(dniCredito, reciboCredito, servicioCredito);

    }

    private Administrativo elegirAdministrativo(){

        Administrativo administrativoElegido = null;

        System.out.println("Ingrese el nombre del administrativo: ");
        String nombreAdministrativo = sc.nextLine();

        for(Empleado emp : empleados.listarEmpleadosActivos()){

            if(emp.getNombre().equals(nombreAdministrativo)){

                administrativoElegido = (Administrativo) emp;

                break;
            }

        }

        return administrativoElegido;
    }

    private PlanContado elegirPlanContado() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        PlanContado plan = null;

        do {
            System.out.println("Elija el plan de contado:");
            System.out.println("1. Pago total");
            System.out.println("2. Plan 6 cuotas");
            System.out.println("3. Plan 12 cuotas");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    plan = PlanContado.PAGO_TOTAL;
                    break;
                case 2:
                    plan = PlanContado.PLAN_6;
                    break;
                case 3:
                    plan = PlanContado.PLAN_12;
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
            }
        } while (plan == null);

        return plan;
    }

    private MedioContacto elegirMedioContacto() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        MedioContacto medio = null;

        do {
            System.out.println("Elija el medio de contacto:");
            System.out.println("1. Redes");
            System.out.println("2. Telefono");
            System.out.println("3. Presencial");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    medio = MedioContacto.REDES;
                    break;
                case 2:
                    medio = MedioContacto.TELEFONO;
                    break;
                case 3:
                    medio = MedioContacto.PRESENCIAL;
                    break;
                default:
                    System.out.println("Opción invalida, intente de nuevo");
            }
        } while (medio == null);

        return medio;
    }

    private ModeloCasa elegirModeloCasa() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        ModeloCasa modelo = null;

        do {
            System.out.println("Elija el modelo de la casa:");
            System.out.println("1. Basico");
            System.out.println("2. Estandar");
            System.out.println("3. Premium");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    modelo = ModeloCasa.BASICO;
                    break;
                case 2:
                    modelo = ModeloCasa.ESTANDAR;
                    break;
                case 3:
                    modelo = ModeloCasa.PREMIUM;
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo");
            }
        } while (modelo == null);

        return modelo;
    }

    private IncluyePlatea elegirIncluyePlatea() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        IncluyePlatea platea = null;

        do {
            System.out.println("La venta incluye platea?");
            System.out.println("1. Si");
            System.out.println("2. No");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    platea = IncluyePlatea.SI;
                    break;
                case 2:
                    platea = IncluyePlatea.NO;
                    break;
                default:
                    System.out.println("Opción invalida, intente de nuevo");
            }
        } while (platea == null);

        return platea;
    }

    private SiNo elegirSiNo(String pregunta) {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        SiNo respuesta = null;

        do {
            System.out.println(pregunta);
            System.out.println("1. Sí");
            System.out.println("2. No");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    respuesta = SiNo.SI;
                    break;
                case 2:
                    respuesta = SiNo.NO;
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo");
            }
        } while (respuesta == null);

        return respuesta;
    }

    private PlanFinanciacion elegirPlanFinanciacion() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        PlanFinanciacion plan = null;

        do {
            System.out.println("Elija el plan de financiación:");
            System.out.println("1. 12 cuotas (1 año)");
            System.out.println("2. 24 cuotas (2 años)");
            System.out.println("3. 36 cuotas (3 años)");
            System.out.println("4. 48 cuotas (4 años)");
            System.out.println("5. 60 cuotas (5 años)");
            System.out.println("6. 72 cuotas (6 años)");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    plan = PlanFinanciacion.UN_ANIO;
                    break;
                case 2:
                    plan = PlanFinanciacion.DOS_ANIOS;
                    break;
                case 3:
                    plan = PlanFinanciacion.TRES_ANIOS;
                    break;
                case 4:
                    plan = PlanFinanciacion.CUATRO_ANIOS;
                    break;
                case 5:
                    plan = PlanFinanciacion.CINCO_ANIOS;
                    break;
                case 6:
                    plan = PlanFinanciacion.SEIS_ANIOS;
                    break;
                default:
                    System.out.println("Opcion invalida, intente de nuevo");
            }
        } while (plan == null);

        return plan;
    }
    // Muestra por pantalla los datos de la venta

    private void mostrarVenta(Venta v) {
        System.out.println("===== VENTA =====");
        System.out.println("Cliente: " + v.getCliente().getNombreCompleto() + " (DNI " + v.getCliente().getDni() + ")");
        System.out.println("Modelo: " + v.getCotizacion().getModeloCasa() + " - " + v.getCotizacion().getM2() + " m2");
        System.out.println("Incluye platea: " + v.getCotizacion().getIncluyePlatea());
        System.out.println("Precio total: $" + v.getPrecioTotal());
        System.out.println("Seña: $" + v.getMontoSenia());
        System.out.println("Saldo: $" + v.calcularSaldo());
    }

}




































