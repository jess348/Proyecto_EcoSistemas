package controllers;

import models.MdlSensibilidad;
import views.FrmSensibilidad;
import javax.swing.*;
// import java.awt.Color;
import java.io.File;
import java.io.PrintWriter;

public class CtrlSensibilidad {
    private FrmSensibilidad vista;
    private MdlSensibilidad modelo;

    public CtrlSensibilidad(FrmSensibilidad vista, MdlSensibilidad modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.vista.btnRecalcular.addActionListener(e -> ejecutarProceso());
        this.vista.btnExportar.addActionListener(e -> exportarACSV());
        this.vista.btnVerAnalisis.addActionListener(e -> generarDiagnostico());
    }

    private void ejecutarProceso() {
        vista.prgCalculando.setIndeterminate(true);
        new Thread(() -> {
            try {
                Thread.sleep(1000); 
                double vanP = modelo.calcularVAN(100000, 15000, 12, 0.20);
                double vanR = modelo.calcularVAN(100000, 25000, 12, 0.12);
                double vanO = modelo.calcularVAN(100000, 35000, 12, 0.08);

                SwingUtilities.invokeLater(() -> {
                    vista.modeloTabla.setRowCount(0);
                    vista.modeloTabla.addRow(new Object[]{"VAN Estimado", "C$ " + (int)vanP, "C$ " + (int)vanR, "C$ " + (int)vanO});
                    vista.modeloTabla.addRow(new Object[]{"Estado", modelo.obtenerEstado(vanP), modelo.obtenerEstado(vanR), modelo.obtenerEstado(vanO)});
                    vista.prgCalculando.setIndeterminate(false);
                    vista.prgCalculando.setValue(100);
                });
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void exportarACSV() {
        JFileChooser selector = new JFileChooser();
        if (selector.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(archivo.getAbsolutePath() + ".csv")) {
                pw.println("Metrica,Pesimista,Realista,Optimista");
                for (int f = 0; f < vista.modeloTabla.getRowCount(); f++) {
                    String fila = vista.modeloTabla.getValueAt(f, 0) + "," +
                                vista.modeloTabla.getValueAt(f, 1) + "," +
                                vista.modeloTabla.getValueAt(f, 2) + "," +
                                vista.modeloTabla.getValueAt(f, 3);
                    pw.println(fila.replace("C$", "").trim());
                }
                JOptionPane.showMessageDialog(vista, "Reporte exportado con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error al exportar.");
            }
        }
    }

    private void generarDiagnostico(){


        try {
            // Se calculan los valores usando el modelo (igual que en ejecutarProceso)
            double vanP = modelo.calcularVAN(100000, 15000, 12, 0.20);
            double vanR = modelo.calcularVAN(100000, 25000, 12, 0.12);
            double vanO = modelo.calcularVAN(100000, 35000, 12, 0.08);

            StringBuilder reporte = new StringBuilder();
            
            // Contenedor principal con CSS
            reporte.append("<div style='font-family: \"Segoe UI\", Arial, sans-serif; color: #333;'>");
            reporte.append("<h2 style='color: #2C3E50; border-bottom: 2px solid #3498DB; padding-bottom: 5px;'>Análisis de Sensibilidad (What-If) 📈</h2>");
            reporte.append("<p>Este análisis evalúa la robustez del proyecto sometiendo los flujos de caja y la tasa de descuento a condiciones extremas para medir el riesgo de inversión.</p>");

            // 1. Escenario Realista (Base)
            reporte.append("<h3 style='color: #2980B9;'>1. Escenario Realista (Caso Base)</h3>");
            reporte.append("<p>Bajo proyecciones económicas normales, el proyecto genera un VAN de <b>C$ ").append(String.format("%,.2f", vanR)).append("</b>. ");
            if (vanR > 0) {
                reporte.append("<span style='color: #27AE60;'>Esto indica que la propuesta original es financieramente <b>VIABLE</b>.</span></p>");
            } else {
                reporte.append("<span style='color: #E74C3C;'>Esto indica que la propuesta original <b>NO ES RENTABLE</b>.</span></p>");
            }

            // 2. Escenario Pesimista (Estrés)
            reporte.append("<h3 style='color: #2980B9;'>2. Escenario Pesimista (Prueba de Estrés)</h3>");
            reporte.append("<p>Asumiendo una fuerte caída en las ventas y un aumento en el costo de financiamiento (inflación/riesgo), el VAN cae a <b>C$ ").append(String.format("%,.2f", vanP)).append("</b>. ");
            if (vanP >= 0) {
                reporte.append("<span style='color: #27AE60;'>Sorprendentemente, el proyecto sobrevive al estrés y no genera pérdidas. Esto demuestra una <b>alta robustez estructural</b>.</span></p>");
            } else {
                reporte.append("<span style='color: #E74C3C;'>Bajo estas condiciones adversas, el proyecto destruiría valor. Existe un <b>riesgo de capital latente</b> si el mercado se contrae.</span></p>");
            }

            // 3. Escenario Optimista
            reporte.append("<h3 style='color: #2980B9;'>3. Escenario Optimista (Expansión)</h3>");
            reporte.append("<p>Si la aceptación del mercado supera las expectativas y se logra financiamiento económico, el proyecto podría catapultar su VAN hasta <b>C$ ").append(String.format("%,.2f", vanO)).append("</b>, maximizando la riqueza de los accionistas.</p>");

            // 4. Veredicto Final / Riesgo
            reporte.append("<hr><h3 style='color: #2C3E50;'>Veredicto de Riesgo Gerencial ⚖️</h3>");
            if (vanR > 0 && vanP >= 0) {
                reporte.append("<p style='color: #27AE60;'><b>Nivel de Riesgo: BAJO.</b> El proyecto soporta variaciones negativas fuertes sin entrar en cifras rojas. Se recomienda <b>APROBAR</b> sin reservas.</p>");
            } else if (vanR > 0 && vanP < 0) {
                reporte.append("<p style='color: #F39C12;'><b>Nivel de Riesgo: MODERADO.</b> El proyecto es viable hoy, pero es altamente sensible a cambios en la economía. Se recomienda <b>PROCEDER CON CAUTELA</b> y firmar contratos de costos fijos a largo plazo.</p>");
            } else {
                reporte.append("<p style='color: #E74C3C;'><b>Nivel de Riesgo: ALTO.</b> El caso base ya es perjudicial. Se aconseja <b>RECHAZAR</b> la inversión inmediatamente.</p>");
            }

            reporte.append("</div>");

            // Mostrar el JDialog reutilizable
            java.awt.Window owner = SwingUtilities.getWindowAncestor(vista);
            views.FrmAnalisisDialog diag = new views.FrmAnalisisDialog(owner, "Reporte de Sensibilidad - C&S", reporte.toString());
            diag.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al generar el diagnóstico de sensibilidad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
                


                


    }
}