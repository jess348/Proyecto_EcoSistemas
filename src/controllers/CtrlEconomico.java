package controllers;

import javax.swing.*;

import models.CalculadoraFinanciera;
import views.FrmEvaluacionProyecto;

import java.awt.Color;
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
        
        // Registro de escuchadores
        this.vista.btnCalcular.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnExportar.addActionListener(this);
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
    }
    //seteo
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

    //reciclado de crtlPuntoEquilibrio
    private void exportarCSV() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            new Thread(() -> {
                try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile() + ".csv"))) {
                    // Encabezados según tu tabla de amortización
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
                    vista.lblVAN.setText(String.format("VAN: C$ %.2f", finalVan));
                    vista.lblTIR.setText(String.format("TIR: %.2f %%", finalTir));
                    
                    // Lógica visual: Rojo si el VAN es negativo
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
                    
                    String mensaje = (finalVan >= 0) ? "Inversión Viable" : "Inversión No Viable";
                    JOptionPane.showMessageDialog(vista, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
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
}