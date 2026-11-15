package Enumeradores;

public enum IncluyePlatea {
    SI(true), NO(false);

    private boolean incluyePlatea;

    IncluyePlatea(boolean incluye) {

        this.incluyePlatea = incluye;

    }

    public boolean getIncluye() {

        return incluyePlatea;

    }
}
