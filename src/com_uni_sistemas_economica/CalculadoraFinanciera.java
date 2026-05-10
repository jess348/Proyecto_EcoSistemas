package com_uni_sistemas_economica;

public class CalculadoraFinanciera {

    // Método para convertir cualquier tasa a su equivalente mensual según el JComboBox 
    public double convertirTasaMensual(double tasaEntrada, String frecuencia) {
        switch (frecuencia) {
            case "Mensual": return tasaEntrada / 100;
            case "Trimestral": return (tasaEntrada / 100) / 3;
            case "Semestral": return (tasaEntrada / 100) / 6;
            case "Anual": return (tasaEntrada / 100) / 12;
            default: return tasaEntrada / 100;
        }
    }

    // Método para calcular la TIR mediante Newton-Raphson (Proceso pesado para hilos) [cite: 44, 47]
    public double calcularTIR(double inversionInicial, double[] flujos) {
        double tir = 0.1;
        double precision = 1e-7;
        for (int i = 0; i < 1000; i++) {
            double f = -inversionInicial;
            double df = 0;
            for (int t = 0; t < flujos.length; t++) {
                f += flujos[t] / Math.pow(1 + tir, t + 1);
                df -= (t + 1) * flujos[t] / Math.pow(1 + tir, t + 2);
            }
            double nuevaTir = tir - f / df;
            if (Math.abs(nuevaTir - tir) < precision) return nuevaTir * 100;
            tir = nuevaTir;
        }
        return tir * 100;
    }
}