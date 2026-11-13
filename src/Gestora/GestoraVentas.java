package Gestora;

import Enumeradores.EstadoVenta;
import Enumeradores.ModeloCasa;
import Modelo.Financiado;
import Modelo.Venta;

import java.util.Scanner;

public class GestoraVentas extends GestoraGenerica<Venta, Integer>{


    private void menuDeActualizar(){
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Monto del anticipo\n" +
                            "2. Estado venta\n" +
                            "3. Modelo de la casa\n" +
                            "0. Salir del menu");

    }


    @Override
    public void actualizar(Venta elementoAActualizar) {

        Scanner sc = new Scanner(System.in);

        int opcion;

        do{
            menuDeActualizar();

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    if(elementoAActualizar instanceof Financiado){
                        Financiado financiado = (Financiado) elementoAActualizar;

                        System.out.println("Ingrese el nuevo monto del anticipo: ");
                        double nuevoMontoAnticipo = sc.nextDouble();
                        sc.nextLine();

                        financiado.setAnticipo(nuevoMontoAnticipo);

                        System.out.println("Anticipo actualizado con exito!");
                    }
                    break;
                case 2:
                    System.out.println("Ingrese el nuevo estado de la venta (Activa/Inactiva): ");
                    String nuevoEstado = sc.nextLine();

                    elementoAActualizar.setEstado(EstadoVenta.valueOf(nuevoEstado.toUpperCase()));

                    System.out.println("Estado de la venta actualizado con exito!");

                    break;
                case 3:
                    System.out.println("Que modelo de casa desea elegir?");

                    ModeloCasa[] modelos = ModeloCasa.values();

                    for(int i = 0; i < modelos.length; i++){

                        System.out.println((i + 1) + ". " + modelos[i].name() + " (Valor m2: $" + modelos[i].getValorM2() + ")\n");
                    }

                    int seleccion = sc.nextInt();
                    sc.nextLine();

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
            }
        }while (opcion != 0);

    }
}
