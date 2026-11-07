package models;

public class Credito {
    private boolean dniPresentado;
    private boolean reciboSueldoPresentado;
    private boolean servicioPresentado;
    private boolean aprobado;

    public Credito(boolean dniPresentado, boolean reciboSueldoPresentado, boolean servicioPresentado, boolean aprobado) {
        this.dniPresentado = dniPresentado;
        this.reciboSueldoPresentado = reciboSueldoPresentado;
        this.servicioPresentado = servicioPresentado;
        this.aprobado = false;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public boolean isDniPresentado() {
        return dniPresentado;
    }

    public void setDniPresentado(boolean dniPresentado) {
        this.dniPresentado = dniPresentado;
    }

    public boolean isReciboSueldoPresentado() {
        return reciboSueldoPresentado;
    }

    public void setReciboSueldoPresentado(boolean reciboSueldoPresentado) {
        this.reciboSueldoPresentado = reciboSueldoPresentado;
    }

    public boolean isServicioPresentado() {
        return servicioPresentado;
    }

    public void setServicioPresentado(boolean servicioPresentado) {
        this.servicioPresentado = servicioPresentado;
    }

    public boolean documentacionCompleta(){
        return dniPresentado && reciboSueldoPresentado && servicioPresentado;
    }
}
