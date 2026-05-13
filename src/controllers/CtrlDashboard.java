package controllers;

import models.MdlRatiosContables;
import views.FrmAnalisisDialog;
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

        vista.btnCargarBalance.addActionListener(this);
        vista.btnCargarEstadoRes.addActionListener(this);
        vista.btnGuardarTXT.addActionListener(this);
        vista.btnCalcularManual.addActionListener(this);
        vista.btnLimpiar.addActionListener(this);
        vista.btnVerAnalisis.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCargarBalance || e.getSource() == vista.btnCargarEstadoRes) {
            cargarCSV();
        } else if (e.getSource() == vista.btnGuardarTXT) {
            guardarTXT();
        } else if (e.getSource() == vista.btnCalcularManual) {
            calcularManual();
        } else if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        } else if (e.getSource() == vista.btnVerAnalisis) {
            generarAnalisisFinanciero();
        }
    }
    
    private void generarAnalisisFinanciero() {
        try {
            double capital = Double.parseDouble(vista.lblCapitalTrabajo.getText().replace(",", ""));
            double margen = Double.parseDouble(vista.lblMargenUtilidad.getText().replace(" %", "").replace(",", ""));
            double deuda = Double.parseDouble(vista.lblNivelDeuda.getText().replace(" %", "").replace(",", ""));

            StringBuilder reporte = new StringBuilder();
            reporte.append("<h2>Diagnóstico de Salud Financiera C&S</h2>");

            // aqui empiezo a armar el string con etiquetas html para el capital de trabajo
            reporte.append("<h3>1. Liquidez y Operación</h3>");
            if (capital > 0) {
                reporte.append("<p style='color: #27AE60;'><b>Estado Positivo:</b> El Capital de Trabajo es de C$ ").append(String.format("%.2f", capital))
                    .append(". Esto indica que la empresa cuenta con recursos suficientes para cubrir sus obligaciones de corto plazo.");
            } else {
                reporte.append("<p style='color: #E74C3C;'><b>Alerta de Liquidez:</b> El Capital de Trabajo es negativo o nulo. Se recomienda revisar la gestión de activos corrientes para evitar interrupciones operativas.");
            }
            reporte.append("</p>");

            //aqui evaluo el margen, le puse colores dependiendo de si pasa de 20 o de 5 para que se entienda mejor
            reporte.append("<h3>2. Eficiencia Operativa</h3>");
            reporte.append("<p>El Margen de Utilidad se sitúa en el <b>").append(String.format("%.2f", margen)).append("%</b>. ");
            if (margen > 20) {
                reporte.append("Este nivel refleja una excelente conversión de ingresos en beneficios netos.</p>");
            } else if (margen > 5) {
                reporte.append("La empresa mantiene un margen saludable, aunque existe espacio para optimizar costos fijos.</p>");
            } else {
                reporte.append("<p style='color: #F39C12;'>El margen es reducido. Se recomienda un análisis detallado de la estructura de costos operativos.</p>");
            }

            // validacion de la deuda, si es mas de 60 se pinta en rojo alerta xd
            reporte.append("<h3>3. Nivel de Endeudamiento</h3>");
            if (deuda > 60) {
                reporte.append("<p style='color: #E74C3C;'><b>RIESGO CRÍTICO:</b> El nivel de deuda del ").append(String.format("%.2f", deuda))
                    .append("% supera el umbral de seguridad. Se recomienda frenar nuevas inversiones financiadas hasta estabilizar el balance.</p>");
            } else {
                reporte.append("<p style='color: #27AE60;'><b>Riesgo Controlado:</b> El nivel de deuda está bajo el 60%. La empresa tiene capacidad de apalancamiento estratégico.</p>");
            }

            // aqui mando a llamar la ventanita modal pasandole la variable reporte convertida a string
            Window owner = SwingUtilities.getWindowAncestor(vista);
            FrmAnalisisDialog diag = new FrmAnalisisDialog(owner, "Interpretación de Ratios Contables", reporte.toString());
            diag.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ejecute un cálculo antes de solicitar el análisis detallado.");
        }
    }

    private void limpiarCampos() {
        vista.txtActivos.setText("");
        vista.txtPasivos.setText("");
        vista.txtUtilidad.setText("");
        vista.txtIngresos.setText("");
        vista.lblCapitalTrabajo.setText("0");
        vista.lblMargenUtilidad.setText("0%");
        vista.lblNivelDeuda.setText("0%");
        vista.txtHistorialAlertas.setText("");
        vista.lblNivelDeuda.setForeground(Color.BLACK);
        vista.progressBar.setIndeterminate(false);
        vista.progressBar.setString("Esperando proceso...");
    }

    private void calcularManual() {
        try {
            double activos = Double.parseDouble(vista.txtActivos.getText());
            double pasivos = Double.parseDouble(vista.txtPasivos.getText());
            double utilidad = Double.parseDouble(vista.txtUtilidad.getText());
            double ingresos = Double.parseDouble(vista.txtIngresos.getText());

            if (activos < 0 || pasivos < 0 || utilidad < 0 || ingresos < 0) {
                JOptionPane.showMessageDialog(vista, "No se permiten números negativos");
                return;
            }

            vista.progressBar.setIndeterminate(true);
            vista.progressBar.setString("Calculando...");

            new Thread(() -> {
                try {
                    Thread.sleep(1000);

                    double capital = modelo.calcularCapitalTrabajo(activos, pasivos);
                    double margen = modelo.calcularMargenUtilidad(utilidad, ingresos);
                    double deuda = modelo.calcularNivelDeuda(pasivos, activos);

                    SwingUtilities.invokeLater(() -> {
                        vista.lblCapitalTrabajo.setText(String.format("%.2f", capital));
                        vista.lblMargenUtilidad.setText(String.format("%.2f %%", margen));
                        vista.lblNivelDeuda.setText(String.format("%.2f %%", deuda));

                        if (deuda > 60) {
                            vista.lblNivelDeuda.setForeground(Color.RED);
                            vista.txtHistorialAlertas.append("[ALERTA] Nivel de deuda crítico (" + String.format("%.2f", deuda) + "%)\n");
                        } else {
                            vista.lblNivelDeuda.setForeground(new Color(39, 174, 96));
                        }

                        vista.progressBar.setIndeterminate(false);
                        vista.progressBar.setString("Proceso completado");
                    });

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese solo números válidos");
        }
    }

    private void cargarCSV() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            vista.progressBar.setIndeterminate(true);
            vista.progressBar.setString("Procesando CSV...");

            new Thread(() -> {
                try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        Thread.sleep(1000);
                        String[] datos = linea.split(",");
                        
                        double activos = Double.parseDouble(datos[0]);
                        double pasivos = Double.parseDouble(datos[1]);
                        double utilidad = Double.parseDouble(datos[2]);
                        double ingresos = Double.parseDouble(datos[3]);

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
                                vista.lblNivelDeuda.setForeground(new Color(39, 174, 96));
                            }
                        });
                    }

                    SwingUtilities.invokeLater(() -> {
                        vista.progressBar.setIndeterminate(false);
                        vista.progressBar.setString("Proceso completado");
                    });

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(vista, "Error leyendo CSV");
                        vista.progressBar.setIndeterminate(false);
                        vista.progressBar.setString("Error");
                    });
                }
            }).start();
        }
    }

    private void guardarTXT() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(archivo)) {
                pw.println("===== DASHBOARD =====");
                pw.println();
                pw.println("Capital: " + vista.lblCapitalTrabajo.getText());
                pw.println("Margen: " + vista.lblMargenUtilidad.getText());
                pw.println("Deuda: " + vista.lblNivelDeuda.getText());
                pw.println();
                pw.println("ALERTAS");
                pw.println(vista.txtHistorialAlertas.getText());
                
                JOptionPane.showMessageDialog(vista, "TXT guardado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error guardando el archivo");
            }
        }
    }
}