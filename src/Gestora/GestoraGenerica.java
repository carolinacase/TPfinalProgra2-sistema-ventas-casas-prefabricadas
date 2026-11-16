package Gestora;

import java.util.HashMap;

public abstract class GestoraGenerica<T, K> {
    protected HashMap<K, T> listadoElementos;

    public GestoraGenerica() {

        this.listadoElementos = new HashMap<>();
    }

    //-- METODOS --

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

    public T buscar(K id){

         return listadoElementos.get(id);

    }

    // ABSTRACTO: CADA GESTORA HIJA DEFINE COMO ACTUALIZAR SU TIPO DE ELEMENTO
    public abstract void actualizar(T elementoAActualizar);
}
