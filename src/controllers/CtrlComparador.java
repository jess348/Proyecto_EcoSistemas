package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import views.FrmComparador;
import views.FrmAnalisisDialog; // Importación necesaria para el JDialog

public class CtrlComparador implements ActionListener  {

    private FrmComparador vista;

    public CtrlComparador(FrmComparador vista){
        this.vista = vista;

        this.vista.btnComparar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnExportar.addActionListener(this);
        this.vista.btnVerAnalisis.addActionListener(this); // Botón registrado
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == vista.btnComparar) {
            realizarComparativa();
        } else if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        } else if (e.getSource() == vista.btnExportar) {
            exportar();
        } else if (e.getSource() == vista.btnVerAnalisis) { // Nuevo evento
            generarDiagnostico();
        }
    }

    private void realizarComparativa(){
        try {
            double vA = Double.parseDouble(vista.txtVanA.getText());
            double tA = Double.parseDouble(vista.txtTirA.getText());
            double vB = Double.parseDouble(vista.txtVanB.getText());
            double tB = Double.parseDouble(vista.txtTirB.getText());

            if (vA < 0 && vB < 0) {
                vista.actualizarResultado("❌ RECHAZADOS: Ambos destruyen valor", new Color(231, 76, 60));
            } else if (vA > vB) {
                vista.actualizarResultado("🏆 GANADOR: PROYECTO A (Mayor VAN)", new Color(41, 128, 185));
            } else if (vB > vA) {
                vista.actualizarResultado("🏆 GANADOR: PROYECTO B (Mayor VAN)", new Color(39, 174, 96));
            } else {
                if (tA > tB) {
                    vista.actualizarResultado("⚖️ EMPATE VAN: PROYECTO A gana por mejor TIR", new Color(41, 128, 185));
                } else if (tB > tA) {
                    vista.actualizarResultado("⚖️ EMPATE VAN: PROYECTO B gana por mejor TIR", new Color(39, 174, 96));
                } else {
                    vista.actualizarResultado("↔️ Alternativas idénticas", Color.GRAY);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,"Error: Por favor ingresa valores numéricos válidos." , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos(){
        vista.txtVanA.setText("");
        vista.txtVanB.setText("");
        vista.txtTirA.setText("");
        vista.txtTirB.setText("");
        vista.actualizarResultado("Esperando datos para análisis...", new Color(236, 240, 241));
        vista.lblGanador.setForeground(Color.BLACK); // Para que se lea en el fondo gris
    }

    private void exportar(){
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            new Thread(() ->{
                try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile() + ".csv"))){ // Se asegura extensión CSV
                    pw.println("Indicador,Proyecto A,Proyecto B");
                    pw.println("VAN (C$)," + vista.txtVanA.getText() + "," + vista.txtVanB.getText());
                    pw.println("TIR (%)," + vista.txtTirA.getText() + "," + vista.txtTirB.getText());
                    pw.println("Decision Recomendada," + vista.lblGanador.getText());

                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog((vista),"Comparativa exportada exitosamente. ✅"));
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista,"Error al exportar.⚠️"));
                }
            }).start();
        }
    }

    //diagnostico
    private void generarDiagnostico() {
        try {
            double vA = Double.parseDouble(vista.txtVanA.getText());
            double tA = Double.parseDouble(vista.txtTirA.getText());
            double vB = Double.parseDouble(vista.txtVanB.getText());
            double tB = Double.parseDouble(vista.txtTirB.getText());

            StringBuilder reporte = new StringBuilder();

            reporte.append("<div style='font-family: \"Segoe UI\", Arial, sans-serif; color: #333;'>");
            reporte.append("<h2 style='color: #2C3E50; border-bottom: 2px solid #3498DB; padding-bottom: 5px;'>Análisis Comparativo de Inversiones 📊</h2>");
            reporte.append("<p>El presente informe detalla la comparativa entre dos alternativas de inversión <b>mutuamente excluyentes</b>, evaluadas bajo los criterios de Valor Actual Neto (VAN) y Tasa Interna de Retorno (TIR).</p>");

            reporte.append("<h3 style='color: #2980B9;'>1. Creación de Valor Neto (VAN)</h3>");
            reporte.append("<p>El Proyecto A proyecta un valor añadido de <b>C$ ").append(String.format("%,.2f", vA)).append("</b>, mientras que el Proyecto B proyecta <b>C$ ").append(String.format("%,.2f", vB)).append("</b>.</p>");
            
            if (vA < 0 && vB < 0) {
                reporte.append("<p style='color: #E74C3C;'><b>Alerta Crítica:</b> Ambas alternativas destruyen valor económico. Ninguna debería ser aceptada bajo métricas financieras estrictas.</p>");
            } else if (vA > vB) {
                reporte.append("<p>El <b>Proyecto A</b> es superior en términos de riqueza neta generada y es la opción preferida por este indicador.</p>");
            } else if (vB > vA) {
                reporte.append("<p>El <b>Proyecto B</b> es superior en términos de riqueza neta generada y es la opción preferida por este indicador.</p>");
            } else {
                reporte.append("<p>Ambos proyectos generan exactamente el mismo valor neto. Se debe recurrir a la TIR para desempatar el nivel de eficiencia.</p>");
            }

            reporte.append("<h3 style='color: #2980B9;'>2. Eficiencia del Capital (TIR)</h3>");
            reporte.append("<p>La rentabilidad intrínseca (TIR) del Proyecto A es del <b>").append(String.format("%.2f", tA)).append("%</b>, frente al <b>").append(String.format("%.2f", tB)).append("%</b> del Proyecto B.</p>");

            reporte.append("<hr><h3 style='color: #2C3E50;'>Veredicto y Justificación ⚖️</h3>");
            String ganador = vista.lblGanador.getText(); 

            if (vA < 0 && vB < 0) {
                reporte.append("<p style='color: #E74C3C;'><b>RECHAZO TOTAL:</b> Se recomienda descartar ambas propuestas y buscar nuevas alternativas de inversión para proteger el capital de la empresa.</p>");
            } else {
                reporte.append("<p>En la evaluación de proyectos mutuamente excluyentes, la regla académica y gerencial dicta que <b>el VAN siempre tiene prioridad de decisión sobre la TIR</b>, ya que el VAN mide en términos absolutos (dinero real) cuánto crecerá la riqueza de la empresa.</p>");
                reporte.append("<p style='font-size: 14px; padding: 10px; background-color: #ECF0F1; border-left: 4px solid #3498DB;'><b>Conclusión del Sistema:</b> ").append(ganador).append("</p>");
            }

            reporte.append("</div>");

            // Mostrar el JDialog
            java.awt.Window owner = SwingUtilities.getWindowAncestor(vista);
            FrmAnalisisDialog diag = new FrmAnalisisDialog(owner, "Reporte Comparativo - C&S", reporte.toString());
            diag.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Por favor, ejecute la comparativa primero antes de solicitar el análisis detallado.", "Datos Faltantes", JOptionPane.WARNING_MESSAGE);
        }
    }
}