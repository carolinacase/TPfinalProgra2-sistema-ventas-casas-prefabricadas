package Enumeradores;

public enum EstadoEmpleados {
    ACTIVO(true), INACTIVO(false);

    private boolean estado;

    EstadoEmpleados(boolean estado) {
        this.estado = estado;
    }

    public boolean getValorEstado(){
        return estado;
    }
}
