package models;

public class MdlEquilibrioOperativo {
    public double calcularPuntoEquilibrio(double costosFijos, double precio, double costoVariable) {
        if (precio <= costoVariable) return -1; 
        return costosFijos / (precio - costoVariable);
    }

    public Object[][] generarSimulacion(double costosFijos, double precio, double costoVariable, double qEquilibrio) {
        int filas = 11; 
        Object[][] data = new Object[filas][4];
        double intervalo = qEquilibrio * 0.20; 
        double inicial = Math.max(0, qEquilibrio - (intervalo * 5));

        for (int i = 0; i < filas; i++) {
            double q = (i == 5) ? qEquilibrio : inicial + (i * intervalo);
            double ingresos = q * precio;
            double costosTotales = costosFijos + (q * costoVariable);
            double utilidad = ingresos - costosTotales;

            data[i][0] = String.format("%.2f", q);
            data[i][1] = String.format("%.2f", ingresos);
            data[i][2] = String.format("%.2f", costosTotales);
            data[i][3] = String.format("%.2f", utilidad);
        }
        return data;
    }
}