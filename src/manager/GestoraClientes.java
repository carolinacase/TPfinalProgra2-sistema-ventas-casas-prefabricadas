package manager;

import Interface.IIdUnico;
import models.Cliente;

public class GestoraClientes extends GestoraGenerica<Cliente, String> {

    @Override
    public boolean agregar(String id, Cliente elementoNuevo) {

        return super.agregar(id, elementoNuevo);
    }

    @Override
    public boolean eliminar(String id) {

        return super.eliminar(id);
    }

    @Override
    public String listar() {

        return super.listar();
    }

    @Override
    public void actualizar(Cliente elementoAActualizar) {
        String dni = elementoAActualizar.getDni();
        if(listadoElementos.containsKey(dni)){
             listadoElementos.put(dni, elementoAActualizar);
        }
    }

    // Para buscar la llave, debemos usar get del listado de elementos creado en la gestora génerica
    public Cliente buscarPorLlave(int llave){
        return listadoElementos.get(String.valueOf(llave));

    }
}
