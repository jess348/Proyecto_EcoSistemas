package models;

public class MdlRatiosContables {

    // CAPITAL DE TRABAJO
    public double calcularCapitalTrabajo(
            double activos,
            double pasivos) {

        return activos - pasivos;
    }

    // MARGEN DE UTILIDAD
    public double calcularMargenUtilidad(
            double utilidad,
            double ingresos) {

        return (utilidad / ingresos) * 100;
    }

    // NIVEL DE DEUDA
    public double calcularNivelDeuda(
            double pasivos,
            double activos) {

        return (pasivos / activos) * 100;
    }
}