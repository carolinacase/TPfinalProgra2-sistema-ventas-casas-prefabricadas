package manager;

import Interface.IIdUnico;

import java.util.HashMap;

public abstract class GestoraGenerica<T, K> {
    private HashMap<K, T> listadoElementos;

    public GestoraGenerica() {
        this.listadoElementos = new HashMap<>();
    }

    public boolean agregar(K id,T elementoNuevo){

        if(!listadoElementos.containsKey(id)){

            listadoElementos.put(id, elementoNuevo);

            return true;
        }else {

            return false;
        }
    }

    public boolean eliminar(K id){

        if(listadoElementos.containsKey(id)){

            listadoElementos.remove(id);

            return true;
        }else {

            return false;
        }

    }

    public String listar(){

        if(listadoElementos.isEmpty()){

            return "Lista vacia";
        }else {

            StringBuilder sb = new StringBuilder();
            int i = 1;

            for(T elemento : listadoElementos.values()){

                sb.append(i++).append(".").append(elemento.toString()).append("\n");

            }

         return sb.toString();
        }
    }

    public abstract void actualizar(T elementoAActualizar);
}
