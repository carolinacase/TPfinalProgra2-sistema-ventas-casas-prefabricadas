package Modelo;

import Enumeradores.IncluyePlatea;
import Enumeradores.MedioContacto;
import Enumeradores.ModeloCasa;

import java.time.LocalDate;
import java.util.Objects;

public class Cotizacion {

    private Integer id;
    private LocalDate fecha;
    private MedioContacto medioContacto;
    private ModeloCasa modeloCasa;
    private double m2;
    private IncluyePlatea incluyePlatea;
    private static final double VALOR_PLATEA_M2 = 65000;

    public Cotizacion(MedioContacto medioContacto, ModeloCasa modeloCasa, double m2, IncluyePlatea incluyePlatea) {
        this.id = null;
        this.fecha = LocalDate.now();
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

    public IncluyePlatea getIncluyePlatea() {
        return incluyePlatea;
    }

    public void setIncluyePlatea(IncluyePlatea incluyePlatea) {
        this.incluyePlatea = incluyePlatea;
    }

    public static double getValorPlateaM2() {
        return VALOR_PLATEA_M2;
    }

    // Calculamos el precio con los m2 y el valor de la platea en m2.
    // Si el modelo de la casa incluye platea, retornamos una suma
    // entre el precio y una multiplicación entre los metros cuadrados y su valor de platea por m2.
    public double calcularPrecio(){

        double precio = this.m2 * this.modeloCasa.getValorM2();

        if(incluyePlatea.getIncluye()){

            return precio + (this.m2 * VALOR_PLATEA_M2);

        }

        return precio;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cotizacion that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cotizacion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", medioContacto=" + medioContacto +
                ", modeloCasa=" + modeloCasa +
                ", m2=" + m2 +
                ", incluyePlatea=" + incluyePlatea +
                '}';
    }
}
