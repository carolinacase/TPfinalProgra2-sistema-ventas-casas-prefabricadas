package enums;

public enum PlanContado {
    PAGO_TOTAL(1),
    PLAN_6(6),
    PLAN_12(12);

    private int cuotas;

    PlanContado(int cuotas) {
        this.cuotas = cuotas;
    }

    public int getCantidadCuotas(){
        return cuotas;
    }

    public double calcularCuotas(double saldo){

        return saldo / cuotas;

    }
}
