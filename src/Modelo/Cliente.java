package Modelo;

public class Cliente extends Persona {

    public Cliente(String nombre, String apellido, String dni, String domicilio, String correo) {
        super(nombre, apellido, dni, domicilio, correo);
    }

    @Override
    public String getIdUnico() {
        return this.getDni();
    }
}
