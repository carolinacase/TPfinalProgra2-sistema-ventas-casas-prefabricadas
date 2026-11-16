package Enumeradores;

public enum EstadoVenta {
    ACTIVA(true), INACTIVA(false),
    PENDIENTE(true);   // FINANCIADO GUARDADO CON DOCUMENTACION INCOMPLETA: FALTA APROBAR EL CREDITO

    private boolean estado;

    EstadoVenta(boolean estado) {
        this.estado = estado;
    }

    public boolean getValorEstado(){
        return estado;
    }
}
