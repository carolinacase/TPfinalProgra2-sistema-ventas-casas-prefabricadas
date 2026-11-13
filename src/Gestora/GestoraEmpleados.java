package Gestora;

import Enumeradores.EstadoEmpleados;
import Modelo.Empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestoraEmpleados extends GestoraGenerica<Empleado, String>{

    public List<Empleado> listarEmpleadosActivos(){

        List<Empleado> empleadosActivos = new ArrayList<>();

        for(Empleado emp : listadoElementos.values()){

            if(emp.getEstado().equals(EstadoEmpleados.ACTIVO)){

                empleadosActivos.add(emp);

            }
        }

        return empleadosActivos;
    }

    private void menuDeActualizar(){
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Estado empleado\n" +
                            "0. Salir del menu");

    }

    @Override
    public void actualizar(Empleado elementoAActualizar) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do{
            menuDeActualizar();
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1:
                    System.out.println("Ingrese el nuevo estado del empleado (Activo/Inactivo): ");
                    String nuevoEstado = sc.nextLine();

                    elementoAActualizar.setEstado(EstadoEmpleados.valueOf(nuevoEstado.toUpperCase()));

                    System.out.println("Estado del empleado actualizado con exito!");

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
}
