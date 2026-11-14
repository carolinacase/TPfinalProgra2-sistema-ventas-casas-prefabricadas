package Gestora;

import Modelo.Cliente;

import java.util.Scanner;

public class GestoraClientes extends GestoraGenerica<Cliente, String> {

    private void menuDeActualizar(){
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Domicilio\n" +
                            "2. Correo electronico\n" +
                            "0. Salir del menu");
    }


    @Override
    public void actualizar(Cliente elementoAActualizar) {
        Scanner sc = new Scanner(System.in);

        int opcion;

        do{
            menuDeActualizar();
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion){
                case 1:
                    System.out.println("Ingrese el nuevo domicilio: ");
                    String domicilioNuevo = sc.nextLine();

                    elementoAActualizar.setDomicilio(domicilioNuevo);

                    System.out.println("Domicilio actualizado con exito!");

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo correo electronico: ");
                    String correoNuevo = sc.nextLine();

                    elementoAActualizar.setCorreo(correoNuevo);

                    System.out.println("Correo actualizado con exito!");

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


    public Cliente buscarCliente(String dni){

        return this.listadoElementos.get(dni);

    }
}
