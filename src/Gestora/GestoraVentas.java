package Gestora;

import DAO.ConexionVentas;
import Enumeradores.EstadoVenta;
import Enumeradores.ModeloCasa;
import Excepciones.DBConexionException;
import Excepciones.DBCreateTableException;
import Modelo.Financiado;
import Modelo.Pago;
import Modelo.Venta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestoraVentas extends GestoraGenerica<Venta, Integer>{

    // USA EL MISMO SCANNER QUE EMPRESA: CON DOS SCANNER SOBRE System.in UNO PUEDE "COMERSE" LO QUE ESCRIBE EL USUARIO
    private Scanner sc;

    public GestoraVentas(Scanner sc) {
        super();
        this.sc = sc;
    }

    public List<Pago> verificarPagosDeHoy(){

        List<Pago> pagosDeHoy = new ArrayList<>();
        LocalDate hoy = LocalDate.now();

        for(Venta v : listadoElementos.values()){

            for(Pago p : v.getHistorialDePagos()){

                if(p.getFecha().equals(hoy)){

                    pagosDeHoy.add(p);

                }
            }
        }

        return pagosDeHoy;
    }

    private void menuActualizarVenta(){
        System.out.println("==========MENU ACTUALIZAR==========");
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Monto del anticipo\n" +
                "2. Estado venta\n" +
                "3. Modelo de la casa\n" +
                "0. Salir del menu actualizacion");
    }

    @Override
    public void actualizar(Venta elementoAActualizar) {

        int opcion;

        do{
            menuActualizarVenta();

            opcion = leerEntero(sc);

            switch (opcion){
                case 1:
                    if(elementoAActualizar instanceof Financiado){
                        Financiado financiado = (Financiado) elementoAActualizar;

                        System.out.println("Ingrese el nuevo monto del anticipo: ");
                        double nuevoMontoAnticipo = leerDouble(sc);

                        financiado.setAnticipo(nuevoMontoAnticipo);

                        System.out.println("Anticipo actualizado con exito!");
                    }
                    break;
                case 2:
                    // SE ELIGE POR NUMERO PARA EVITAR ERRORES DE TIPEO (antes un texto mal escrito cortaba el programa)
                    System.out.println("Elija el nuevo estado de la venta:");
                    System.out.println("1. Activa");
                    System.out.println("2. Inactiva");
                    int opcionEstado = leerEntero(sc);

                    if(opcionEstado == 1){
                        elementoAActualizar.setEstado(EstadoVenta.ACTIVA);
                        System.out.println("Estado de la venta actualizado con exito!");
                    } else if(opcionEstado == 2){
                        elementoAActualizar.setEstado(EstadoVenta.INACTIVA);
                        System.out.println("Estado de la venta actualizado con exito!");
                    } else {
                        System.out.println("Opcion invalida, el estado no se modificó");
                    }

                    break;
                case 3:
                    System.out.println("Que modelo de casa desea elegir?");

                    ModeloCasa[] modelos = ModeloCasa.values();

                    for(int i = 0; i < modelos.length; i++){

                        System.out.println((i + 1) + ". " + modelos[i].name() + " (Valor m2: $" + modelos[i].getValorM2() + ")\n");
                    }

                    int seleccion = leerEntero(sc);

                    if(seleccion >= 1 && seleccion <= modelos.length){

                        ModeloCasa modeloElegido = modelos[seleccion - 1];

                        elementoAActualizar.getCotizacion().setModeloCasa(modeloElegido);

                        System.out.println("Modelo de casa actualizado con exito a " + modeloElegido + "!");

                    }else{
                        System.out.println("Opcion invalida");

                    }

                    break;
                case 0:
                    System.out.println("Saliendo del menu de actualizacion...");

                    break;
                default:
                    System.out.println("Opcion invalida");

                    break;
            }
        }while (opcion != 0);

    }

    // Guarda la venta en MySQL, le asigna el id que generó la base y la agrega a la gestora
    public void registrarVenta(Venta v) throws DBConexionException, DBCreateTableException {
        int id = ConexionVentas.insertarVenta(v);   // MySQL la guarda y devuelve el id
        v.setIdVenta(id);                           // la venta se queda con ese id
        agregar(id, v);                             // la clave del HashMap es el id
    }

    // Lee un número entero sin que el programa se corte si el usuario escribe letras
    private int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Debe ingresar un número");
            sc.nextLine();
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }

    // Lo mismo para números con decimales
    private double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.println("Debe ingresar un número");
            sc.nextLine();
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }
}
