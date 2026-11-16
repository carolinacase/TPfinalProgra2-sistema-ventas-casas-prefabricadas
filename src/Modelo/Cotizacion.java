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
    private static final double VALOR_PLATEA_M2 = 65000; //PRECIO DE LA PLATEA POR M2
    private static final double MARGEN_PLATEA = 0.10; // LA PLATEA SOBRESALE 20-30 CM ALREDEDOR DE LA CASA: SE SUMA UN 10% A LOS M2
    private static int contadorCotizaciones = 0; // GENERAL EL ID, Y NO SE GUARDA YA QUE LAS COTIZACIONES QUE NO SON VENTAS NO ES NECESARIO GUARDAR

    public Cotizacion(MedioContacto medioContacto, ModeloCasa modeloCasa, double m2, IncluyePlatea incluyePlatea) {
        contadorCotizaciones++;               // SUMA 1 AL CONTADOR
        this.id = contadorCotizaciones;       // ESTA COTIZACION SE QUEDA CON ESE NUMERO
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

    /// PRECIO = M2 DE LA CASA X VALOR DEL MODELO. SI LLEVA PLATEA, SE SUMA LA PLATEA: M2 DE LA CASA + 10%, X VALOR DE LA PLATEA POR M2.
    public double calcularPrecio(){

        double precio = this.m2 * this.modeloCasa.getValorM2();

        if(incluyePlatea.getIncluye()){

            double m2Platea = this.m2 * (1 + MARGEN_PLATEA);   // M2 DE LA CASA + 10%

            return precio + (m2Platea * VALOR_PLATEA_M2);
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
        return "Cotizacion nº " + id +
                ", fecha=" + fecha +
                ", medioContacto=" + medioContacto +
                ", modeloCasa=" + modeloCasa +
                ", m2=" + m2 +
                ", incluyePlatea=" + incluyePlatea;
    }
}
