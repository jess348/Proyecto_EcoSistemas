package models;
public class MdlSensibilidad {
    
    /**
     * Calcula el VAN simplificado para los escenarios.
     */
    public double calcularVAN(double inversion, double flujo, int periodos, double tasa) {
        double van = -inversion;
        for (int t = 1; t <= periodos; t++) {
            van += flujo / Math.pow(1 + tasa, t);
        }
        return van;
    }

    /**
     * Determina si el proyecto es aceptable.
     */
    public String obtenerEstado(double van) {
        return (van >= 0) ? "[VIABLE]" : "[NO VIABLE]";
    }
}