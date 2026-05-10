package com_uni_sistemas_economica;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorEconomico implements ActionListener {
    private V_EvaluacionProyecto vista;
    private CalculadoraFinanciera modelo;

    public ControladorEconomico(V_EvaluacionProyecto vista, CalculadoraFinanciera modelo) {
        this.vista = vista;
        this.modelo = modelo;
        // Asignación del escuchador al botón de la vista
        this.vista.btnCalcular.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Validación del origen del evento [cite: 10]
        if (e.getSource() == vista.btnCalcular) {
            
            // 1. Preparación de la Interfaz (Hilo Principal - EDT)
            vista.barraProgreso.setIndeterminate(true);
            vista.btnCalcular.setEnabled(false);
            vista.modeloTabla.setRowCount(0);


Thread hiloProceso = new Thread(() -> {
    double van = 0;
    double tirResultado = 0;
    Object[][] datosTablaLocal = null;

    try {
        double inversion = Double.parseDouble(vista.txtMonto.getText());
        double tasaInput = Double.parseDouble(vista.txtTasa.getText());
        int periodos = Integer.parseInt(vista.txtPlazo.getText());
        String frecuencia = vista.cbFrecuencia.getSelectedItem().toString();
        double flujoIngreso = Double.parseDouble(vista.txtFlujoCaja.getText());
        double tasaEfectiva = modelo.convertirTasaMensual(tasaInput, frecuencia);
        double cuota = inversion * (tasaEfectiva / (1 - Math.pow(1 + tasaEfectiva, -periodos)));
        
        double[] flujos = new double[periodos];
        for(int i = 0; i < periodos; i++) {
            flujos[i] = flujoIngreso; // <--- AQUÍ USAS LA VARIABLE
        }
        van = -inversion;
        for (int i = 0; i < periodos; i++) {
            van += flujos[i] / Math.pow(1 + tasaEfectiva, i + 1);
        }
        tirResultado = modelo.calcularTIR(inversion, flujos);

        datosTablaLocal = new Object[periodos][5];
        double saldo = inversion;
        for (int i = 0; i < periodos; i++) {
            double interes = saldo * tasaEfectiva;
            double capital = cuota - interes;
            saldo -= capital;
            datosTablaLocal[i] = new Object[]{i + 1, String.format("%.2f", cuota), 
                                String.format("%.2f", interes), String.format("%.2f", capital), 
                                String.format("%.2f", Math.max(saldo, 0))};
        }

        // Variables finales para el hilo de la interfaz
        final double finalVan = van;
        final double finalTir = tirResultado;
        final Object[][] datosFinales = datosTablaLocal;

        SwingUtilities.invokeLater(() -> {
            vista.lblVAN.setText(String.format("VAN: C$ %.2f", finalVan));
            vista.lblTIR.setText(String.format("TIR: %.2f %%", finalTir));
            
            vista.modeloTabla.setRowCount(0);
            for (Object[] fila : datosFinales) {
                vista.modeloTabla.addRow(fila);
            }
            
            vista.barraProgreso.setIndeterminate(false);
            vista.btnCalcular.setEnabled(true);
            
            String mensaje = (finalVan >= 0) ? "Inversión Viable" : "Inversión No Viable";
            JOptionPane.showMessageDialog(vista, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        });

    } catch (Exception ex) {
        SwingUtilities.invokeLater(() -> {
            vista.barraProgreso.setIndeterminate(false);
            vista.btnCalcular.setEnabled(true);
            JOptionPane.showMessageDialog(vista, "Error en los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
});
hiloProceso.start();

            hiloProceso.start(); // Inicia el hilo secundario [cite: 44]
        }
    }
}