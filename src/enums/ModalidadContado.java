package enums;

public enum ModalidadContado {
    PAGO_TOTAL(1),
    PLAN_6(6),
    PLAN_12(12);
    private int cuotas;

    ModalidadContado(int cuotas) {
        this.cuotas = cuotas;
    }


}
