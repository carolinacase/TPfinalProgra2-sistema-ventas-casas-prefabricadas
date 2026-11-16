package Gestora;

import DAO.ConexionClientes;
import DAO.ConexionEmpleados;
import DAO.ConexionVentas;
import Enumeradores.*;
import Excepciones.AnticipoInsuficienteException;
import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Excepciones.DocumentacionIncompletaException;
import Modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Empresa {
    private String nombre;
    private GestoraVentas ventas;
    private GestoraEmpleados empleados;
    private GestoraClientes clientes;
    Scanner sc = new Scanner(System.in);

    public Empresa(String nombre) {
        this.ventas = new GestoraVentas(sc);   // le pasa el mismo Scanner (hay uno solo en todo el programa)
        this.empleados = new GestoraEmpleados();
        this.clientes = new GestoraClientes();
        this.nombre = nombre;
    }

    public void consultarEmpresa(){
        cargarBaseDeDatos();   // trae los clientes antes de mostrar el menú


        int opcionPrincipal;

        do{
            menuPrincipal();
            opcionPrincipal = leerEntero();

            switch(opcionPrincipal){  // SE ELIGE EN QUE OPCION INGRESAR: VENTAS, EMPLEADOS, CLIENTES
                case 1: // VENTAS
                    int opcionVentas;

                    do{
                        menuVentas();
                        opcionVentas = leerEntero();

                        switch(opcionVentas){
                            case 1: // CARGAR VENTA
                                if(listarVendedores().isEmpty()){   // SIN VENDEDORES NO SE PUEDE CARGAR UNA VENTA
                                    System.out.println("No hay vendedores activos. Primero dé de alta uno en el menú de empleados");
                                    break;
                                }

                                System.out.println("Carga de Venta: ");
                                System.out.println("Ingrese tipo de venta -> Contado/Financiado ");
                                String tipoVenta = sc.nextLine();

                                if(tipoVenta.equalsIgnoreCase("Contado")){

                                    Contado vc = ingresarVentaContado();
                                    guardarVenta(vc);

                                } else if (tipoVenta.equalsIgnoreCase("Financiado")) {

                                    if(listarAdministrativos().isEmpty()){   // SIN ADMINISTRATIVOS NADIE PUEDE APROBAR EL CREDITO
                                        System.out.println("No hay administrativos activos para aprobar el crédito. Primero dé de alta uno en el menú de empleados");
                                        break;
                                    }

                                    Financiado vf = ingresarVentaFinanciado();

                                    try{
                                        vf.validarAnticipo();          // el anticipo tiene que ser al menos el 30% del precio

                                        Administrativo administrativo = elegirAdministrativo();
                                        administrativo.aprobarCredito(vf);
                                        guardarVenta(vf);
                                    }catch (AnticipoInsuficienteException e){
                                        System.out.println(e.getMessage());   // muestra el anticipo minimo
                                    }catch (DocumentacionIncompletaException e){
                                        System.out.println(e.getMessage());
                                    }
                                }

                                break;

                            case 2: // ELIMINAR VENTA
                                System.out.println("Ingrese el id de la venta a eliminar: ");
                                int idEliminar = leerEntero();

                                System.out.println(ventas.eliminar(idEliminar) ? "Venta eliminada con exito" : "Venta no se pudo eliminar");

                                break;
                            case 3: // LISTAR VENTAS

                                ventas.listar();

                                break;
                            case 4: // BUSCAR VENTA
                                System.out.println("Ingrese el id de la venta a actualizar: ");
                                int idActualizar = leerEntero();

                                Venta ventaBuscada =  ventas.buscar(idActualizar);

                                if(ventaBuscada == null){   // BUSCAR DEVUELVE NULL SI EL ID NO EXISTE
                                    System.out.println("No existe una venta con ese id");
                                } else {
                                    ventas.actualizar(ventaBuscada);
                                }

                                break;
                            case 5: // VERIFICAR PAGOS DE HOY

                                for(Pago pg : ventas.verificarPagosDeHoy()){

                                    System.out.println(pg);

                                }

                                break;
                            case 0: // SALIR
                                System.out.println("Saliendo del menú de ventas...");

                                break;
                            default:
                                System.out.println("Opción inválida");
                        }

                    }while (opcionVentas != 0);
//------------------------------------------------------------------------
                    break;
                case 2: // EMPLEADOS
                    int opcionEmpleados;

                    do{
                        menuEmpleados();
                        opcionEmpleados = leerEntero();

                        switch(opcionEmpleados){
                            case 1: // INGRESAR EMPLEADO
                                Empleado nuevo = ingresarEmpleado();
                                try {
                                    empleados.registrarEmpleado(nuevo);
                                    System.out.println("Empleado cargado correctamente");
                                } catch (DBConexionException e) {
                                    System.out.println("Error de conexión: " + e.getMessage());
                                } catch (DBCreateTableException e) {
                                    System.out.println("No se pudo guardar el empleado: " + e.getMessage());
                                }
                                break;
                            case 2: // ELIMINAR EMPLEADO
                                System.out.println("Ingrese el DNI del empleado a eliminar: ");
                                String dniEliminar = sc.nextLine();
                                try {
                                    if (empleados.eliminarEmpleado(dniEliminar)) {
                                        System.out.println("Empleado eliminado correctamente");
                                    } else {
                                        System.out.println("No existe un empleado con ese DNI");
                                    }
                                } catch (DBConexionException e) {
                                    System.out.println("Error de conexión: " + e.getMessage());
                                } catch (DBCreateTableException e) {
                                    System.out.println("No se pudo eliminar el empleado: " + e.getMessage());
                                }
                                break;

                            case 3:// LISTAR EMPLEADOS
                                System.out.println(empleados.listar());   // listar() devuelve el texto, Empresa lo imprime
                                break;
                            case 4://BUSCAR EMPLEADO
                                break;
                            case 0:
                                System.out.println("Saliendo del menú de empleados...");
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }while (opcionEmpleados != 0);

                    break;

                case 0:

                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }while (opcionPrincipal != 0);
    }
//-------------------------- BDD------------------------------
    private void cargarBaseDeDatos() {
        try {
            ConexionClientes.crearTablaCliente();   // primero creo bdd clientes
            ConexionVentas.crearTablaVentas();      // después bdd ventas, porque depende de clientes
            ConexionEmpleados.crearTabla();         // tabla de empleados

            // trae de MySQL los clientes guardados y los carga en la gestora
            for (Cliente c : ConexionClientes.obtenerClientes().values()) {
                clientes.agregar(c.getDni(), c);
            }
            // trae de MySQL los empleados (vendedores y administrativos) y los carga en la gestora
            for (Empleado e : ConexionEmpleados.listarEmpleados().values()) {
                empleados.agregar(e.getDni(), e);
            }
        } catch (DBConexionException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (DBCreateTableException e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        }
    }
//---------------------------------MENU---------------------
    private void menuPrincipal(){
        System.out.println("==========MENU PRINCIPAL==========");
        System.out.println("1.=====VENTAS=====");
        System.out.println("2.=====EMPLEADOS=====");
        System.out.println("3.=====CLIENTES=====");
        System.out.println("0. Salir");

    }

    private void menuEmpleados(){
        System.out.println("==========MENU EMPLEADOS==========");
        System.out.println("1.=====Dar de alta un empleado=====");
        System.out.println("2.=====Eliminar un empleado=====");
        System.out.println("3.=====Mostrar empleados=====");
        System.out.println("4.=====Buscar un empleado=====");
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
//--------------------------------METODOS VENTAS----------------
    private Contado ingresarVentaContado(){

        LocalDate fechaDeHoy = LocalDate.now();

        System.out.println("Ingrese el monto de la seña: ");
        double montoSenia = leerDouble();

        Cliente cliente = ingresarCliente();

        Vendedor vendedor = elegirVendedor();

        Cotizacion cotizacion = ingresarCotizacion();

        PlanContado plan = elegirPlanContado();

        Contado contado = new Contado(fechaDeHoy, montoSenia, cliente, vendedor, cotizacion, plan);

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
        double m2Casa = leerDouble();

        IncluyePlatea platea = elegirIncluyePlatea();

        return new Cotizacion(medio, modelo, m2Casa, platea);

    }

    // Devuelve los vendedores activos (sirve para mostrarlos y para saber si hay alguno)
    private List<Vendedor> listarVendedores(){

        List<Vendedor> listaVendedores = new ArrayList<>();

        for(Empleado emp : empleados.listarEmpleadosActivos()){

            if(emp instanceof Vendedor){

                listaVendedores.add((Vendedor) emp);

            }
        }

        return listaVendedores;
    }

    private Vendedor elegirVendedor(){

        List<Vendedor> listaVendedores = listarVendedores();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < listaVendedores.size(); i++){

            sb.append(i + 1).append(". ").append(listaVendedores.get(i).getNombreCompleto()).append("\n");

        }

        System.out.println(sb);

        int indiceVendedorElegido;

        do {   // SE REPITE HASTA QUE ELIJA UN NUMERO QUE ESTE EN LA LISTA
            System.out.println("Ingrese el indice del vendedor que elige: ");
            indiceVendedorElegido = leerEntero();

            if(indiceVendedorElegido < 1 || indiceVendedorElegido > listaVendedores.size()){
                System.out.println("Opción inválida, intente de nuevo");
            }
        } while (indiceVendedorElegido < 1 || indiceVendedorElegido > listaVendedores.size());

        return listaVendedores.get(indiceVendedorElegido - 1);
    }

    private Financiado ingresarVentaFinanciado(){

        LocalDate fechaDeHoy = LocalDate.now();

        System.out.println("Ingrese el monto de la seña: ");
        double montoSenia = leerDouble();

        Cliente cliente = ingresarCliente();

        Vendedor vendedor = elegirVendedor();

        Cotizacion cotizacion = ingresarCotizacion();

        System.out.println("Ingrese el anticipo: ");
        double anticipo =  leerDouble();

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

    // Devuelve los administrativos activos (sirve para mostrarlos y para saber si hay alguno)
    private List<Administrativo> listarAdministrativos(){

        List<Administrativo> listaAdministrativos = new ArrayList<>();

        for(Empleado emp : empleados.listarEmpleadosActivos()){

            if(emp instanceof Administrativo){

                listaAdministrativos.add((Administrativo) emp);

            }
        }

        return listaAdministrativos;
    }

    // IGUAL QUE ELEGIR VENDEDOR: SE MUESTRA LA LISTA NUMERADA Y SE ELIGE POR NUMERO
    private Administrativo elegirAdministrativo(){

        List<Administrativo> listaAdministrativos = listarAdministrativos();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < listaAdministrativos.size(); i++){

            sb.append(i + 1).append(". ").append(listaAdministrativos.get(i).getNombreCompleto()).append("\n");

        }

        System.out.println(sb);

        int indiceElegido;

        do {   // SE REPITE HASTA QUE ELIJA UN NUMERO QUE ESTE EN LA LISTA
            System.out.println("Ingrese el indice del administrativo que aprueba el credito: ");
            indiceElegido = leerEntero();

            if(indiceElegido < 1 || indiceElegido > listaAdministrativos.size()){
                System.out.println("Opción inválida, intente de nuevo");
            }
        } while (indiceElegido < 1 || indiceElegido > listaAdministrativos.size());

        return listaAdministrativos.get(indiceElegido - 1);
    }

    private PlanContado elegirPlanContado() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        PlanContado plan = null;

        do {
            System.out.println("Elija el plan de contado:");
            System.out.println("1. Pago total");
            System.out.println("2. Plan 6 cuotas");
            System.out.println("3. Plan 12 cuotas");
            int opcion = leerEntero();

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
            int opcion = leerEntero();

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
            int opcion = leerEntero();

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
            System.out.println("La venta incluye platea? (Si/No)");
            String respuesta = sc.nextLine().trim();   // trim saca espacios de más

            if (respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("sí")) {   // acepta si, Si, SI y con tilde
                platea = IncluyePlatea.SI;
            } else if (respuesta.equalsIgnoreCase("no")) {
                platea = IncluyePlatea.NO;
            } else {
                System.out.println("Respuesta inválida, escriba Si o No");
            }
        } while (platea == null);

        return platea;
    }

    private SiNo elegirSiNo(String pregunta) {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        SiNo respuesta = null;

        do {
            System.out.println(pregunta + " (Si/No)");
            String texto = sc.nextLine().trim();   // trim saca espacios de más

            if (texto.equalsIgnoreCase("si") || texto.equalsIgnoreCase("sí")) {   // acepta si, Si, SI y con tilde
                respuesta = SiNo.SI;
            } else if (texto.equalsIgnoreCase("no")) {
                respuesta = SiNo.NO;
            } else {
                System.out.println("Respuesta inválida, escriba Si o No");
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
            int opcion = leerEntero();

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
    // Guarda la venta completa: primero el cliente (si es nuevo) y después la venta
    private void guardarVenta(Venta v) {
        try {
            clientes.registrarCliente(v.getCliente());
            ventas.registrarVenta(v);
            System.out.println("Venta cargada correctamente");
            mostrarVenta(v);
        } catch (DBConexionException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (DBCreateTableException e) {
            System.out.println("No se pudo guardar la venta: " + e.getMessage());
        }
    }

    //---------------------METODOS EMPLEADOS ---------------------

    // Pide los datos de un empleado nuevo. Se carga activo y con la fecha de hoy
    private Empleado ingresarEmpleado(){
        System.out.println("Ingrese el nombre: ");
        String nombre = sc.nextLine();
        System.out.println("Ingrese el apellido: ");
        String apellido = sc.nextLine();
        System.out.println("Ingrese el DNI: ");
        String dni = sc.nextLine();
        System.out.println("Ingrese el domicilio: ");
        String domicilio = sc.nextLine();
        System.out.println("Ingrese el correo: ");
        String correo = sc.nextLine();
        System.out.println("Ingrese el número de legajo: ");
        int legajo = leerEntero();

        Empleado nuevo = null;
        do {
            System.out.println("Es vendedor o administrativo? (V/A)");
            String tipo = sc.nextLine().trim();

            if (tipo.equalsIgnoreCase("V")) {
                nuevo = new Vendedor(nombre, apellido, dni, domicilio, correo, legajo, EstadoEmpleados.ACTIVO, LocalDate.now());
            } else if (tipo.equalsIgnoreCase("A")) {
                nuevo = new Administrativo(nombre, apellido, dni, domicilio, correo, legajo, EstadoEmpleados.ACTIVO, LocalDate.now());
            } else {
                System.out.println("Respuesta inválida, escriba V o A");
            }
        } while (nuevo == null);

        return nuevo;
    }

    //---------------------LECTURA SEGURA DE NUMEROS ---------------------

    // Lee un número entero. Si el usuario escribe letras, avisa y vuelve a preguntar (evita que el programa se corte)
    private int leerEntero() {
        while (!sc.hasNextInt()) {          // mientras lo escrito NO sea un entero...
            System.out.println("Debe ingresar un número");
            sc.nextLine();                  // ...descarta lo que escribió
        }
        int numero = sc.nextInt();
        sc.nextLine();                      // limpia el Enter
        return numero;
    }

    // Lo mismo para números con decimales (montos, metros cuadrados)
    private double leerDouble() {
        while (!sc.hasNextDouble()) {
            System.out.println("Debe ingresar un número");
            sc.nextLine();
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }

}
