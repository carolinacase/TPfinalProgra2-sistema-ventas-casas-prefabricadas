package Enumeradores;

public enum PlanFinanciacion {
    UN_ANIO(12),
    DOS_ANIOS(24),
    TRES_ANIOS(36),
    CUATRO_ANIOS(48),
    CINCO_ANIOS(60),
    SEIS_ANIOS(72);


    private static final double TASA_MENSUAL = 0.02;
    private int cuota;

    PlanFinanciacion(int cuota){

        this.cuota = cuota;

    }

    public int getCuota(){

        return this.cuota;

    }

    public double calcularCuotas(double saldo){

        double totalConInteres = saldo * (1 + TASA_MENSUAL * cuota);

        return totalConInteres / cuota;

    }

    public static PlanFinanciacion obtenerPorCuotas(int cantidadCuotas) {

        for (PlanFinanciacion plan : PlanFinanciacion.values()) {

            if (plan.getCuota() == cantidadCuotas) {

                return plan;

            }

        }

        return null;
        //throw new IllegalArgumentException("Cantidad de cuotas no válida: " + cantidadCuotas);
    }
}
