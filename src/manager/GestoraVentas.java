package manager;

import models.Venta;

import java.util.HashMap;
import java.util.Scanner;

public class GestoraVentas extends GestoraGenerica<Venta, Integer>{


    private void menuDeActualizar(){
        System.out.println("Que campo desea modificar?");
        System.out.println( "1. Monto de la seña\n" +
                            "2.");

    }


    @Override
    public void actualizar(Venta elementoAActualizar) {
        Scanner sc = new Scanner(System.in);




    }
}
