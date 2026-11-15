package Enumeradores;

public enum SiNo {
    SI(true),
    NO(false);

    private boolean valor;

    SiNo(boolean valor) {
        this.valor = valor;
    }

    public boolean getValor() {
        return valor;
    }
}
