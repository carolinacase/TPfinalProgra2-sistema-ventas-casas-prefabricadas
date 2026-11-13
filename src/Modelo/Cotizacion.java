package Modelo;

import Enumeradores.MedioContacto;
import Enumeradores.ModeloCasa;

import java.time.LocalDate;
import java.util.Objects;

public class Cotizacion {

    private int id;
    private LocalDate fecha;
    private MedioContacto medioContacto;
    private ModeloCasa modeloCasa;
    private double m2;
    private boolean incluyePlatea;
    private static final double VALOR_PLATEA_M2 = 65000;

    public Cotizacion(int id, LocalDate fecha,
                      MedioContacto medioContacto, ModeloCasa modeloCasa, double m2, boolean incluyePlatea) {
        this.id = id;
        this.fecha = fecha;
        this.medioContacto = medioContacto;
        this.modeloCasa = modeloCasa;
        this.m2 = m2;
        this.incluyePlatea = incluyePlatea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public MedioContacto getMedioContacto() {
        return medioContacto;
    }

    public void setMedioContacto(MedioContacto medioContacto) {
        this.medioContacto = medioContacto;
    }

    public ModeloCasa getModeloCasa() {
        return modeloCasa;
    }

    public void setModeloCasa(ModeloCasa modeloCasa) {
        this.modeloCasa = modeloCasa;
    }

    public double getM2() {
        return m2;
    }

    public void setM2(double m2) {
        this.m2 = m2;
    }

    public boolean isIncluyePlatea() {
        return incluyePlatea;
    }

    public void setIncluyePlatea(boolean incluyePlatea) {
        this.incluyePlatea = incluyePlatea;
    }

    public static double getValorPlateaM2() {
        return VALOR_PLATEA_M2;
    }

    public double calcularPrecio(){

        double precio = this.m2 * this.modeloCasa.getValorM2();

        if(incluyePlatea){

            return precio + (this.m2 * VALOR_PLATEA_M2);

        }

        return precio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cotizacion that = (Cotizacion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
