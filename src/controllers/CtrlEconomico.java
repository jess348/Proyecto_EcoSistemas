package controllers;

import javax.swing.*;
import models.CalculadoraFinanciera;
import views.FrmEvaluacionProyecto;
import views.FrmAnalisisDialog; 
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CtrlEconomico implements ActionListener {
    private FrmEvaluacionProyecto vista;
    private CalculadoraFinanciera modelo;

    public CtrlEconomico(FrmEvaluacionProyecto vista, CalculadoraFinanciera modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        this.vista.btnCalcular.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnExportar.addActionListener(this);
        this.vista.btnVerAnalisis.addActionListener(this); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCalcular) {
            ejecutarAnalisis();
        }
        else if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }
        else if (e.getSource() == vista.btnExportar) {
            exportarCSV();
        }
        else if (e.getSource() == vista.btnVerAnalisis) { 
            generarDiagnostico();
        }
    }

    private void limpiarCampos() {
        vista.txtMonto.setText("");
        vista.txtTasa.setText("");
        vista.txtPlazo.setText("");
        vista.txtFlujoCaja.setText("");
        vista.cbFrecuencia.setSelectedIndex(0);
        vista.lblVAN.setText("VAN: C$ 0.00");
        vista.lblTIR.setText("TIR: 0.00 %");
        vista.lblVAN.setForeground(new Color(0, 102, 51));
        vista.modeloTabla.setRowCount(0);
        vista.barraProgreso.setValue(0);
    }

    private void exportarCSV() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            new Thread(() -> {
                try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile() + ".csv"))) {
                    pw.println("Periodo,Cuota,Interes,Capital,Saldo");
                    
                    for (int i = 0; i < vista.modeloTabla.getRowCount(); i++) {
                        pw.println(
                            vista.modeloTabla.getValueAt(i, 0) + "," +
                            vista.modeloTabla.getValueAt(i, 1) + "," +
                            vista.modeloTabla.getValueAt(i, 2) + "," +
                            vista.modeloTabla.getValueAt(i, 3) + "," +
                            vista.modeloTabla.getValueAt(i, 4)
                        );
                    }
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(vista, "Exportación exitosa. ✅"));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(vista, "Error al exportar. ⚠️", "Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        }
    }

    private void ejecutarAnalisis() {
        vista.barraProgreso.setIndeterminate(true);
        vista.btnCalcular.setEnabled(false);
        vista.modeloTabla.setRowCount(0);

        new Thread(() -> {
            try {
                double inversion = Double.parseDouble(vista.txtMonto.getText());
                double tasaInput = Double.parseDouble(vista.txtTasa.getText());
                int periodos = Integer.parseInt(vista.txtPlazo.getText());
                String frecuencia = vista.cbFrecuencia.getSelectedItem().toString();
                double flujoIngreso = Double.parseDouble(vista.txtFlujoCaja.getText());
                
                double tasaEfectiva = modelo.convertirTasaMensual(tasaInput, frecuencia);
                double cuota = inversion * (tasaEfectiva / (1 - Math.pow(1 + tasaEfectiva, -periodos)));
                
                double[] flujos = new double[periodos];
                for (int i = 0; i < periodos; i++) {
                    flujos[i] = flujoIngreso;
                }

                double van = -inversion;
                for (int i = 0; i < periodos; i++) {
                    van += flujos[i] / Math.pow(1 + tasaEfectiva, i + 1);
                }
                
                double tirResultado = modelo.calcularTIR(inversion, flujos);
                Object[][] datosTablaLocal = new Object[periodos][5];
                double saldo = inversion;

                for (int i = 0; i < periodos; i++) {
                    double interes = saldo * tasaEfectiva;
                    double capital = cuota - interes;
                    saldo -= capital;
                    datosTablaLocal[i] = new Object[]{
                        i + 1, 
                        String.format("%.2f", cuota), 
                        String.format("%.2f", interes), 
                        String.format("%.2f", capital), 
                        String.format("%.2f", Math.max(saldo, 0))
                    };
                }

                final double finalVan = van;
                final double finalTir = tirResultado;
                final Object[][] datosFinales = datosTablaLocal;

                SwingUtilities.invokeLater(() -> {
                    vista.lblVAN.setText(String.format("VAN: C$ %.2f", finalVan).replace(",", "."));
                    vista.lblTIR.setText(String.format("TIR: %.2f %%", finalTir).replace(",", "."));
                    
                    if (finalVan < 0) {
                        vista.lblVAN.setForeground(Color.RED);
                    } else {
                        vista.lblVAN.setForeground(new Color(0, 102, 51));
                    }

                    vista.modeloTabla.setRowCount(0);
                    for (Object[] fila : datosFinales) {
                        vista.modeloTabla.addRow(fila);
                    }
                    
                    vista.barraProgreso.setIndeterminate(false);
                    vista.btnCalcular.setEnabled(true);
                    
                    String mensaje = (finalVan >= 0) ? "Cálculos exitosos. Proyecto Viable." : "Cálculos exitosos. Proyecto No Viable.";
                    JOptionPane.showMessageDialog(vista, mensaje + "\nHaga clic en 'Ver Análisis Detallado' para el reporte.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    vista.barraProgreso.setIndeterminate(false);
                    vista.btnCalcular.setEnabled(true);
                    JOptionPane.showMessageDialog(vista, "Error en los datos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void generarDiagnostico() {
        try {
            // Extracción de datos directos de la vista para hacer el reporte dinámico
            String vanLimpiado = vista.lblVAN.getText().replace("VAN: C$ ", "").trim();
            String tirLimpiada = vista.lblTIR.getText().replace("TIR: ", "").replace("%", "").trim();

            double van = Double.parseDouble(vanLimpiado);
            double tir = Double.parseDouble(tirLimpiada);
            
            double inversion = Double.parseDouble(vista.txtMonto.getText());
            double flujoMensual = Double.parseDouble(vista.txtFlujoCaja.getText());
            double tasaAnual = Double.parseDouble(vista.txtTasa.getText());
            String frecuencia = vista.cbFrecuencia.getSelectedItem().toString();
            
            // Calculamos la tasa efectiva del periodo (ej. 1% mensual)
            double tasaEfectivaPorcentaje = modelo.convertirTasaMensual(tasaAnual, frecuencia) * 100;

            StringBuilder analisis = new StringBuilder();
            analisis.append("<h2>Interpretación Financiera (VAN y TIR)</h2>");
            
            // --- LÓGICA DEL VAN BASADA EN TU TEXTO ---
            analisis.append("<p style='color: #2C3E50;'><b>Interpretación del VAN (C$ ").append(String.format("%.2f", van)).append("):</b><br>");
            analisis.append("Técnicamente, el VAN trae todos los flujos de caja futuros (los C$ ").append(flujoMensual).append(" por periodo) ");
            analisis.append("al valor del día de hoy descontándolos a la tasa del costo de capital (").append(String.format("%.2f", tasaEfectivaPorcentaje)).append("% ");
            analisis.append(frecuencia.toLowerCase()).append(" equivalente al ").append(tasaAnual).append("% anual).</p>");

            if (van > 0) {
                analisis.append("<p style='color: #27AE60;'>El resultado indica que el proyecto no solo recupera la inversión inicial de C$ ").append(inversion);
                analisis.append(" y paga el costo de financiamiento, sino que genera una riqueza adicional neta de <b>C$ ").append(String.format("%.2f", van)).append("</b>. ");
                analisis.append("Dado que el VAN > 0, la regla de decisión establece que el proyecto es financieramente viable y debe ser aceptado.</p>");
            } else if (Math.abs(van) < 0.1) { 
                analisis.append("<p style='color: #F39C12;'>El resultado indica que el proyecto recupera exactamente la inversión inicial de C$ ").append(inversion);
                analisis.append(" y paga el costo de financiamiento, pero no genera riqueza adicional (Punto de Equilibrio). ");
                analisis.append("La decisión de aceptarlo dependerá de factores estratégicos no financieros.</p>");
            } else {
                analisis.append("<p style='color: #E74C3C;'>El resultado indica que los flujos generados no son suficientes para recuperar la inversión inicial de C$ ").append(inversion);
                analisis.append(" ni cubrir el costo de financiamiento. Se proyecta una destrucción de valor de <b>C$ ").append(String.format("%.2f", van)).append("</b>. ");
                analisis.append("Dado que el VAN < 0, la regla de decisión establece que el proyecto es financieramente inviable y debe ser rechazado.</p>");
            }

            // --- LÓGICA DE LA TIR BASADA EN TU TEXTO ---
            analisis.append("<hr><p style='color: #2C3E50;'><b>Interpretación de la TIR (").append(String.format("%.2f", tir)).append("%):</b><br>");
            analisis.append("La TIR representa la tasa de rentabilidad intrínseca de los flujos del proyecto. En este escenario, el proyecto genera un rendimiento del ");
            analisis.append("<b>").append(String.format("%.2f", tir)).append("%</b> por periodo. Para saber si es bueno, se compara contra la Tasa de Exigencia (Costo de Capital), que es del ");
            analisis.append(String.format("%.2f", tasaEfectivaPorcentaje)).append("% ").append(frecuencia.toLowerCase()).append(".</p>");

            if (tir > tasaEfectivaPorcentaje) {
                analisis.append("<p style='color: #27AE60;'>Dado que la TIR (").append(String.format("%.2f", tir)).append("%) es estrictamente mayor que la Tasa de Exigencia (");
                analisis.append(String.format("%.2f", tasaEfectivaPorcentaje)).append("%), se confirma matemáticamente la viabilidad operativa y la capacidad del proyecto para soportar el crédito o la inversión.</p>");
            } else if (Math.abs(tir - tasaEfectivaPorcentaje) < 0.01) {
                analisis.append("<p style='color: #F39C12;'>Dado que la TIR es exactamente igual a la Tasa de Exigencia, el proyecto no genera rendimientos extraordinarios, operando en el límite exacto de la viabilidad.</p>");
            } else {
                analisis.append("<p style='color: #E74C3C;'>Dado que la TIR (").append(String.format("%.2f", tir)).append("%) es menor que la Tasa de Exigencia (");
                analisis.append(String.format("%.2f", tasaEfectivaPorcentaje)).append("%), se confirma matemáticamente que la rentabilidad no justifica el riesgo ni el costo del capital involucrado.</p>");
            }

            Window padre = SwingUtilities.getWindowAncestor(vista);
            FrmAnalisisDialog diag = new FrmAnalisisDialog(padre, "Reporte Financiero - C&S", analisis.toString());
            diag.setVisible(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Debe ejecutar el análisis primero para generar un diagnóstico válido.");
        }
    }
}