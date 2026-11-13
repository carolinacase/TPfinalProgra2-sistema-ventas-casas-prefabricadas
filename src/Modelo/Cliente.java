package Modelo;

public class Cliente extends Persona {
    private int llave;
    // Constructor
    public Cliente(String nombre, String apellido, String dni, String domicilio, String correo,int llave) {
        super(nombre, apellido, dni, domicilio, correo);
        this.llave=llave;
    }

    public int getLlave() {
        return llave;
    }

    public void setLlave(int llave) {
        this.llave = llave;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "llave=" + llave +
                "} " + super.toString();
    }

    @Override
    public String getIdUnico() {
        return this.getDni();
    }
}
