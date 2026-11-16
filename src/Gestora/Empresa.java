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
import java.util.Locale;
import java.util.Scanner;


public class Empresa {
    private String nombre;
    private GestoraVentas ventas;
    private GestoraEmpleados empleados;
    private GestoraClientes clientes;
    Scanner sc = new Scanner(System.in);

    public Empresa(String nombre) {
        this.ventas = new GestoraVentas(sc);   // LE PASA EL MISMO Scanner (HAY UNO SOLO EN TODO EL PROGRAMA)
        this.empleados = new GestoraEmpleados();
        this.clientes = new GestoraClientes();
        this.nombre = nombre;
    }

    public void consultarEmpresa(){
        cargarBaseDeDatos();   // TRAE LOS CLIENTES ANTES DE MOSTRAR EL MENU


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
                                    System.out.println("No hay vendedores activos. Primero de de alta uno en el menu de empleados");
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
                                        System.out.println("No hay administrativos activos para aprobar el credito. Primero de de alta uno en el menu de empleados");
                                        break;
                                    }

                                    Financiado vf = ingresarVentaFinanciado();

                                    try{
                                        vf.validarAnticipo();          // EL ANTICIPO TIENE QUE SER AL MENOS EL 30% DEL PRECIO

                                        Administrativo administrativo = elegirAdministrativo();
                                        administrativo.aprobarCredito(vf);
                                        guardarVenta(vf);
                                    }catch (AnticipoInsuficienteException e){
                                        System.out.println(e.getMessage());   // MUESTRA EL ANTICIPO MINIMO
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
                                List<Venta> todasLasVentas = ventas.obtenerVentas();

                                if (todasLasVentas.isEmpty()) {
                                    System.out.println("No hay ventas cargadas en esta sesion");
                                } else {
                                    for (Venta v : todasLasVentas) {
                                        mostrarVenta(v);   // MISMO FORMATO QUE AL CARGARLA (CON SU Nº)
                                    }
                                }

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
                                System.out.println("Saliendo del menu de ventas...");

                                break;
                            default:
                                System.out.println("Opcion invalida");
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
                                    System.out.println("Empleado cargado correctamente. Legajo asignado: " + nuevo.getId());
                                } catch (DBConexionException e) {
                                    System.out.println("Error de conexion: " + e.getMessage());
                                } catch (DBCreateTableException e) {
                                    System.out.println("No se pudo guardar el empleado: " + e.getMessage());
                                }
                                break;
                            case 2: // ELIMINAR EMPLEADO
                                System.out.println("Ingrese el DNI del empleado a eliminar: ");
                                String dniEliminar = sc.nextLine().trim();
                                try {
                                    if (empleados.eliminarEmpleado(dniEliminar)) {
                                        System.out.println("Empleado eliminado correctamente");
                                    } else {
                                        System.out.println("No existe un empleado con ese DNI");
                                    }
                                } catch (DBConexionException e) {
                                    System.out.println("Error de conexion: " + e.getMessage());
                                } catch (DBCreateTableException e) {
                                    System.out.println("No se pudo eliminar el empleado: " + e.getMessage());
                                }
                                break;

                            case 3:// LISTAR EMPLEADOS
                                System.out.println(empleados.listar());   // listar() DEVUELVE EL TEXTO, Empresa LO IMPRIME
                                break;
                            case 4://BUSCAR EMPLEADO
                                System.out.println("Ingrese el DNI del empleado: ");
                                String dniEmpleado = sc.nextLine().trim();
                                Empleado empleadoBuscado = empleados.buscar(dniEmpleado);

                                if (empleadoBuscado == null) {
                                    System.out.println("No existe un empleado con ese DNI");
                                } else {
                                    System.out.println(empleadoBuscado);
                                }
                                break;
                            case 0:
                                System.out.println("Saliendo del menu de empleados...");
                                break;
                            default:
                                System.out.println("Opcion invalida");
                        }
                    }while (opcionEmpleados != 0);

                    break;
//------------------------------------------------------------------------
                case 3: // CLIENTES (NO HAY ALTA NI BAJA: EL CLIENTE SE CREA AL CARGAR UNA VENTA Y NO PUEDE EXISTIR SIN VENTAS)
                    int opcionClientes;

                    do{
                        menuClientes();
                        opcionClientes = leerEntero();

                        switch(opcionClientes){
                            case 1: // LISTAR CLIENTES
                                System.out.println(clientes.listar());
                                break;
                            case 2: // BUSCAR CLIENTE
                                System.out.println("Ingrese el DNI del cliente: ");
                                String dniBuscado = sc.nextLine().trim();
                                Cliente encontrado = clientes.buscarCliente(dniBuscado);

                                if (encontrado == null) {
                                    System.out.println("No existe un cliente con ese DNI");
                                } else {
                                    System.out.println(encontrado);
                                }
                                break;
                            case 3: // VENTAS DE UN CLIENTE
                                System.out.println("Ingrese el DNI del cliente: ");
                                String dniVentas = sc.nextLine().trim();
                                List<Venta> ventasCliente = ventas.buscarVentasPorCliente(dniVentas);

                                if (ventasCliente.isEmpty()) {
                                    System.out.println("No hay ventas cargadas para ese DNI");
                                } else {
                                    for (Venta v : ventasCliente) {
                                        mostrarVenta(v);   // REUTILIZA EL RESUMEN DE LA VENTA
                                    }
                                }
                                break;
                            case 0:
                                System.out.println("Saliendo del menu de clientes...");
                                break;
                            default:
                                System.out.println("Opcion invalida");
                        }
                    }while (opcionClientes != 0);

                    break;

                case 0:

                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }while (opcionPrincipal != 0);
    }
//-------------------------- BDD------------------------------
    private void cargarBaseDeDatos() {
        try {
            ConexionClientes.crearTablaCliente();   // PRIMERO CREO BDD CLIENTES
            ConexionVentas.crearTablaVentas();      // DESPUES BDD VENTAS, PORQUE DEPENDE DE CLIENTES
            ConexionEmpleados.crearTabla();         // TABLA DE EMPLEADOS

            // TRAE DE MySQL LOS CLIENTES GUARDADOS Y LOS CARGA EN LA GESTORA
            for (Cliente c : ConexionClientes.obtenerClientes().values()) {
                clientes.agregar(c.getDni(), c);
            }
            // TRAE DE MySQL LOS EMPLEADOS (VENDEDORES Y ADMINISTRATIVOS) Y LOS CARGA EN LA GESTORA
            for (Empleado e : ConexionEmpleados.listarEmpleados().values()) {
                empleados.agregar(e.getDni(), e);
            }
        } catch (DBConexionException e) {
            System.out.println("Error de conexion: " + e.getMessage());
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

    private void menuClientes(){
        System.out.println("==========MENU CLIENTES==========");
        System.out.println("1.=====Mostrar todos los clientes=====");
        System.out.println("2.=====Buscar un cliente por DNI=====");
        System.out.println("3.=====Ver las ventas de un cliente=====");
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

    // PRIMERO SE PIDE EL DNI: SI EL CLIENTE YA COMPRO ANTES, SE USA EL QUE YA ESTA Y NO SE VUELVEN A PEDIR SUS DATOS
    private Cliente ingresarCliente(){

        System.out.println("Ingrese el DNI del cliente: ");
        String dniCliente = sc.nextLine().trim();   // trim SACA ESPACIOS DE MAS

        Cliente existente = clientes.buscarCliente(dniCliente);
        if (existente != null) {
            System.out.println("Cliente ya registrado: " + existente.getNombreCompleto());
            return existente;
        }

        System.out.println("Ingrese el nombre del cliente: ");
        String nombreCliente = sc.nextLine().trim();

        System.out.println("Ingrese el apellido del cliente: ");
        String apellidoCliente = sc.nextLine().trim();

        System.out.println("Ingrese el domicilio del cliente: ");
        String domicilioCliente = sc.nextLine().trim();

        System.out.println("Ingrese el correo del cliente: ");
        String correoCliente = sc.nextLine().trim();

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

    // DEVUELVE LOS VENDEDORES ACTIVOS (SIRVE PARA MOSTRARLOS Y PARA SABER SI HAY ALGUNO)
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
                System.out.println("Opcion invalida, intente de nuevo");
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

        // ANTES DEL ANTICIPO SE MUESTRA EL TOTAL, PARA SABER SOBRE QUE MONTO SE CALCULA
        double precioTotal = cotizacion.calcularPrecio();
        System.out.println("Precio total de la casa: $" + formatearMonto(precioTotal));

        // EL ANTICIPO SE CONTROLA APENAS SE INGRESA: SI NO LLEGA AL MINIMO SE VUELVE A PEDIR
        double anticipoMinimo = Financiado.calcularAnticipoMinimo(precioTotal);
        double anticipo;

        do {
            System.out.println("Ingrese el anticipo: ");
            anticipo = leerDouble();

            if (anticipo < anticipoMinimo) {
                System.out.println("El anticipo debe ser de al menos $" + formatearMonto(anticipoMinimo));
            }
        } while (anticipo < anticipoMinimo);

        // DESPUES DEL ANTICIPO SE MUESTRA LO QUE QUEDA POR FINANCIAR
        double saldo = precioTotal - montoSenia - anticipo;
        System.out.println("Saldo a financiar: $" + formatearMonto(saldo));

        PlanFinanciacion planElegido = elegirPlanFinanciacion();

        // DESPUES DE ELEGIR EL PLAN SE MUESTRA EL VALOR DE CADA CUOTA (calcularCuotas YA SUMA EL INTERES)
        System.out.println(planElegido.getCuota() + " cuotas de $" + formatearMonto(planElegido.calcularCuotas(saldo)) + " (incluye interes mensual)");

        Credito credito = ingresarCredito();


        return new Financiado(fechaDeHoy, montoSenia, cliente, vendedor, cotizacion, anticipo,  planElegido, credito);

    }

    private Credito ingresarCredito() {

        SiNo dniCredito = elegirSiNo("Presento el DNI?");

        SiNo reciboCredito = elegirSiNo("Presento el recibo de sueldo?");

        SiNo servicioCredito = elegirSiNo("Presento algun servicio?");

        return new Credito(dniCredito, reciboCredito, servicioCredito);

    }

    // DEVUELVE LOS ADMINISTRATIVOS ACTIVOS (SIRVE PARA MOSTRARLOS Y PARA SABER SI HAY ALGUNO)
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
                System.out.println("Opcion invalida, intente de nuevo");
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
                    System.out.println("Opcion invalida, intente de nuevo");
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
                    System.out.println("Opcion invalida, intente de nuevo");
            }
        } while (modelo == null);

        return modelo;
    }

    private IncluyePlatea elegirIncluyePlatea() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        IncluyePlatea platea = null;

        do {
            System.out.println("La venta incluye platea? (Si/No)");
            String respuesta = sc.nextLine().trim();   // TRIM SACA ESPACIOS DE MAS

            if (respuesta.equalsIgnoreCase("si") || respuesta.equalsIgnoreCase("sí")) {   // ACEPTA si, Si, SI Y TAMBIEN CON TILDE
                platea = IncluyePlatea.SI;
            } else if (respuesta.equalsIgnoreCase("no")) {
                platea = IncluyePlatea.NO;
            } else {
                System.out.println("Respuesta invalida, escriba Si o No");
            }
        } while (platea == null);

        return platea;
    }

    private SiNo elegirSiNo(String pregunta) {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        SiNo respuesta = null;

        do {
            System.out.println(pregunta + " (Si/No)");
            String texto = sc.nextLine().trim();   // TRIM SACA ESPACIOS DE MAS

            if (texto.equalsIgnoreCase("si") || texto.equalsIgnoreCase("sí")) {   // ACEPTA si, Si, SI Y TAMBIEN CON TILDE
                respuesta = SiNo.SI;
            } else if (texto.equalsIgnoreCase("no")) {
                respuesta = SiNo.NO;
            } else {
                System.out.println("Respuesta invalida, escriba Si o No");
            }
        } while (respuesta == null);

        return respuesta;
    }

    private PlanFinanciacion elegirPlanFinanciacion() {  // RESUELVE PROBLEMA CON ENUM, EVITANDO POSIBLES DIFERENCIAS DE TIPEO
        PlanFinanciacion plan = null;

        do {
            System.out.println("Elija el plan de financiacion:");
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
    // MUESTRA POR PANTALLA LOS DATOS DE LA VENTA

    private void mostrarVenta(Venta v) {
        System.out.println("===== VENTA Nº " + v.getIdVenta() + " =====");   // EL ID SE USA PARA ELIMINAR O ACTUALIZAR
        System.out.println("Fecha de venta: " + v.getFechaSenia());
        System.out.println("Estado de venta: " + v.getEstado());
        System.out.println("Cliente: " + v.getCliente().getNombreCompleto() + " (DNI " + v.getCliente().getDni() + ")");
        System.out.println("Modelo: " + v.getCotizacion().getModeloCasa() + " - " + v.getCotizacion().getM2() + " m2");
        System.out.println("Incluye platea: " + v.getCotizacion().getIncluyePlatea());
        System.out.println("Seña: $" + formatearMonto(v.getMontoSenia()));
        System.out.println("Total: $" + formatearMonto(v.getPrecioTotal()));
        Financiado f = (Financiado) v;
        System.out.println("Fecha vto anticipo: " + v.getFechaVto());
        System.out.println("Anticipo: $" + f.getAnticipo());
        System.out.println("Saldo: $" + formatearMonto(v.calcularSaldo()));
    }

    // MUESTRA EL MONTO COMPLETO CON PUNTOS DE MILES Y 2 DECIMALES (EJ: 12.430.000,00) EN VEZ DE 1.243E7
    private String formatearMonto(double monto) {
        return String.format(Locale.of("es", "AR"), "%,.2f", monto);   // FORMATO ARGENTINO: PUNTO DE MILES Y COMA DECIMAL
    }
    // GUARDA LA VENTA COMPLETA: PRIMERO EL CLIENTE (SI ES NUEVO) Y DESPUES LA VENTA
    private void guardarVenta(Venta v) {
        try {
            clientes.registrarCliente(v.getCliente());
            ventas.registrarVenta(v);
            System.out.println("Venta cargada correctamente");
            mostrarVenta(v);
        } catch (DBConexionException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        } catch (DBCreateTableException e) {
            System.out.println("No se pudo guardar la venta: " + e.getMessage());
        }
    }

    //---------------------METODOS EMPLEADOS ---------------------

    // PIDE LOS DATOS DE UN EMPLEADO NUEVO. SE CARGA ACTIVO Y CON LA FECHA DE HOY
    // EL LEGAJO NO SE PIDE: LO ASIGNA MYSQL AL GUARDARLO (POR AHORA VA 0)
    private Empleado ingresarEmpleado(){
        System.out.println("Ingrese el nombre: ");
        String nombre = sc.nextLine().trim();   // trim SACA ESPACIOS DE MAS
        System.out.println("Ingrese el apellido: ");
        String apellido = sc.nextLine().trim();
        System.out.println("Ingrese el DNI: ");
        String dni = sc.nextLine().trim();
        System.out.println("Ingrese el domicilio: ");
        String domicilio = sc.nextLine().trim();
        System.out.println("Ingrese el correo: ");
        String correo = sc.nextLine().trim();
        int legajo = 0;

        Empleado nuevo = null;
        do {
            System.out.println("Es vendedor o administrativo? (V/A)");
            String tipo = sc.nextLine().trim();

            if (tipo.equalsIgnoreCase("V")) {
                nuevo = new Vendedor(nombre, apellido, dni, domicilio, correo, legajo, EstadoEmpleados.ACTIVO, LocalDate.now());
            } else if (tipo.equalsIgnoreCase("A")) {
                nuevo = new Administrativo(nombre, apellido, dni, domicilio, correo, legajo, EstadoEmpleados.ACTIVO, LocalDate.now());
            } else {
                System.out.println("Respuesta invalida, escriba V o A");
            }
        } while (nuevo == null);

        return nuevo;
    }

    //---------------------LECTURA SEGURA DE NUMEROS ---------------------

    // LEE UN NUMERO ENTERO. SI EL USUARIO ESCRIBE LETRAS, AVISA Y VUELVE A PREGUNTAR (EVITA QUE EL PROGRAMA SE CORTE)
    private int leerEntero() {
        while (!sc.hasNextInt()) {          // MIENTRAS LO ESCRITO NO SEA UN ENTERO...
            System.out.println("Debe ingresar un numero");
            sc.nextLine();                  // ...DESCARTA LO QUE ESCRIBIO
        }
        int numero = sc.nextInt();
        sc.nextLine();                      // LIMPIA EL ENTER
        return numero;
    }

    // LO MISMO PARA NUMEROS CON DECIMALES (MONTOS, METROS CUADRADOS)
    private double leerDouble() {
        while (!sc.hasNextDouble()) {
            System.out.println("Debe ingresar un numero");
            sc.nextLine();
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }

}
