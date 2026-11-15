package Modelo;

import Enumeradores.SiNo;

import java.util.Objects;

public class Credito {
    private SiNo dniPresentado;
    private SiNo reciboSueldoPresentado;
    private SiNo servicioPresentado;
    private SiNo aprobado;

    public Credito(SiNo dniPresentado, SiNo reciboSueldoPresentado, SiNo servicioPresentado) {
        this.dniPresentado = dniPresentado;
        this.reciboSueldoPresentado = reciboSueldoPresentado;
        this.servicioPresentado = servicioPresentado;
        this.aprobado = SiNo.NO;
    }

    public SiNo getDniPresentado() {
        return dniPresentado;
    }

    public void setDniPresentado(SiNo dniPresentado) {
        this.dniPresentado = dniPresentado;
    }

    public SiNo getReciboSueldoPresentado() {
        return reciboSueldoPresentado;
    }

    public void setReciboSueldoPresentado(SiNo reciboSueldoPresentado) {
        this.reciboSueldoPresentado = reciboSueldoPresentado;
    }

    public SiNo getServicioPresentado() {
        return servicioPresentado;
    }

    public void setServicioPresentado(SiNo servicioPresentado) {
        this.servicioPresentado = servicioPresentado;
    }

    public SiNo getAprobado() {
        return aprobado;
    }

    public void setAprobado(SiNo aprobado) {
        this.aprobado = aprobado;
    }

    public boolean verificarCredito(){

        return dniPresentado.getValor() && reciboSueldoPresentado.getValor() && servicioPresentado.getValor();

    }

    @Override
    public String toString() {
        return "Credito{" +
                "dniPresentado=" + dniPresentado +
                ", reciboSueldoPresentado=" + reciboSueldoPresentado +
                ", servicioPresentado=" + servicioPresentado +
                ", aprobado=" + aprobado +
                '}';
    }
}