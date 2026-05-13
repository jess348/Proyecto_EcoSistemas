package controllers;

import models.MdlRatiosContables;
import views.FrmDashboardFinanciero;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CtrlDashboard implements ActionListener {

    private FrmDashboardFinanciero vista;
    private MdlRatiosContables modelo;

    public CtrlDashboard(FrmDashboardFinanciero vista) {
        this.vista = vista;
        this.modelo = new MdlRatiosContables();
        this.vista.btnCargarBalance.addActionListener(this);
        this.vista.btnCargarEstadoRes.addActionListener(this);
        this.vista.btnGuardarTXT.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCargarBalance) {
            cargarCSV();
        }
        if (e.getSource() == vista.btnCargarEstadoRes) {
            cargarCSV();
        }
        if (e.getSource() == vista.btnGuardarTXT) {
            guardarTXT();
        }
    }

    private void cargarCSV() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showOpenDialog(vista);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            vista.progressBar.setIndeterminate(true);
            vista.progressBar.setString("Procesando archivo...");

            Thread hilo = new Thread(() -> {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                    String linea;

                    while ((linea = br.readLine()) != null) {
                        Thread.sleep(1000);
                        String[] datos = linea.split(",");

                        try {
                            double activos = Double.parseDouble(datos[0]);
                            double pasivos = Double.parseDouble(datos[1]);
                            double utilidad = Double.parseDouble(datos[2]);
                            double ingresos = Double.parseDouble(datos[3]);

                            if (activos < 0 || ingresos < 0) {
                                JOptionPane.showMessageDialog(vista, "No se permiten valores negativos");
                                return;
                            }

                            double capital = modelo.calcularCapitalTrabajo(activos, pasivos);
                            double margen = modelo.calcularMargenUtilidad(utilidad, ingresos);
                            double deuda = modelo.calcularNivelDeuda(pasivos, activos);

                            SwingUtilities.invokeLater(() -> {
                                vista.lblCapitalTrabajo.setText(String.format("%.2f", capital));
                                vista.lblMargenUtilidad.setText(String.format("%.2f %%", margen));
                                vista.lblNivelDeuda.setText(String.format("%.2f %%", deuda));

                                if (deuda > 60) {
                                    vista.lblNivelDeuda.setForeground(Color.RED);
                                    vista.txtHistorialAlertas.append("[ALERTA] Nivel de deuda crítico\n");
                                } else {
                                    vista.lblNivelDeuda.setForeground(Color.BLACK);
                                }
                            });

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(vista, "Error en datos numéricos del CSV");
                        }
                    }
                    br.close();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vista, "Error leyendo archivo CSV");
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(vista, "El hilo fue interrumpido");
                }

                SwingUtilities.invokeLater(() -> {
                    vista.progressBar.setIndeterminate(false);
                    vista.progressBar.setString("Proceso completado");
                });
            });

            hilo.start();
        }
    }

    private void guardarTXT() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showSaveDialog(vista);

        if (opcion == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                PrintWriter pw = new PrintWriter(archivo);
                pw.println("===== DASHBOARD FINANCIERO =====");
                pw.println();
                pw.println("Capital de Trabajo: " + vista.lblCapitalTrabajo.getText());
                pw.println("Margen de Utilidad: " + vista.lblMargenUtilidad.getText());
                pw.println("Nivel de Deuda: " + vista.lblNivelDeuda.getText());
                pw.println();
                pw.println("===== ALERTAS =====");
                pw.println(vista.txtHistorialAlertas.getText());
                pw.close();
                JOptionPane.showMessageDialog(vista, "Informe TXT guardado correctamente");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(vista, "Error guardando TXT");
            }
        }
    }
}