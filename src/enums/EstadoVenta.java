package enums;

public enum EstadoVenta {
    ACTIVA(true), INACTIVA(false);

    private boolean estado;

    EstadoVenta(boolean estado) {
        this.estado = estado;
    }

    public boolean getValorEstado(){
        return estado;
    }
}
