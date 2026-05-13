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
}