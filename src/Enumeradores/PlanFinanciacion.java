package Enumeradores;

public enum PlanFinanciacion {
    PRIMER_ANIO(12),
    SEGUNDO_ANIO(24),
    TERCER_ANIO(36),
    CUARTO_ANIO(48),
    QUINTO_ANIO(60),
    SEXTO_ANIO(72);


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
}
