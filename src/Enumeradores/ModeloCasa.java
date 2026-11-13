package Enumeradores;

public enum ModeloCasa {
    BASICO(550000),
    ESTANDAR(650000),
    PREMIUM(700000);

    private double valorM2;

    ModeloCasa(double valorM2) {
        this.valorM2 = valorM2;
    }

    public double getValorM2() {
        return valorM2;
    }
}
