package controllers;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import models.MdlEquilibrioOperativo;
import views.FrmAnalisisDialog;
import views.FrmPuntoEquilibrio;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.PrintWriter;
public class CtrlPuntoEquilibrio implements ActionListener {
    private FrmPuntoEquilibrio vista;
    private MdlEquilibrioOperativo modelo;
    

    public CtrlPuntoEquilibrio(FrmPuntoEquilibrio vista, MdlEquilibrioOperativo modelo){
        this.vista = vista;
        this.modelo = modelo;
        this.vista.btnSimular.addActionListener(this);
        this.vista.btnExportar.addActionListener(this);
        //nuevos btn
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnVerAnalisis.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== vista.btnSimular) {
            calcularEquilibrio();
        }else if (e.getSource() == vista.btnExportar) {
            exportar();
        }else if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }else if (e.getSource() == vista.btnVerAnalisis) {
            generarDiagnostico();
        }




    }

        private void limpiarCampos(){
            vista.txtCostoVariable.setText("");
            vista.txtCostosFijos.setText("");
            vista.txtPrecioVenta.setText("");
            vista.lblIngresoEquilibrio.setText("0.00");
            vista.lblUnidadesEquilibrio.setText("0.00");
        }

        public void generarDiagnostico(){

            try {
                double cf = Double.parseDouble(vista.txtCostosFijos.getText());
                double p = Double.parseDouble(vista.txtPrecioVenta.getText());
                double cv = Double.parseDouble(vista.txtCostoVariable.getText());

                double mcu =  p - cv;
                double q = cf/ mcu;

                
                StringBuilder reporte = new StringBuilder();
                reporte.append("<div style='font-family: \"Segoe UI\", Arial, sans-serif; color: #333;'>");
                reporte.append("<h2 style='color: #2C3E50; border-bottom: 2px solid #3498DB; padding-bottom: 5px;'>Diagnóstico de Equilibrio Operativo 📊</h2>");


                // reporte.append("<h2>Interpretacion de Resultados 📈</h2>");
                reporte.append("<h3 style='color: #2980B9;'>1. Margen de Contribución Unitario</h3>");
        reporte.append("<p>Por cada unidad vendida, el negocio genera un margen de <b>C$ ").append(String.format("%.2f", mcu)).append("</b>.</p>");
        reporte.append("<p>Este monto es el remanente disponible, tras cubrir los costos variables, para absorber los <b>C$ ").append(String.format("%.2f", cf)).append("</b> de costos fijos totales.</p>");

        reporte.append("<h3 style='color: #2980B9;'>2. Volumen Crítico</h3>");
        if (q > 0) {

            reporte.append("<p style='color: #27AE60;'>Se requiere comercializar un mínimo de <b>").append(String.format("%.2f", q))
                .append(" unidades</b> para alcanzar el punto donde los ingresos igualan a los egresos (cero utilidades, cero pérdidas).</p>");
        }



        reporte.append("<h3 style='color: #2980B9;'>3. Estrategias de Optimización 💡</h3>");
        reporte.append("<ul style='line-height: 1.6;'>");
        reporte.append("<li><b>Ajuste de Precios:</b> Un incremento estratégico en el precio de venta reduce el volumen necesario para alcanzar el equilibrio.</li>");
        reporte.append("<li><b>Eficiencia en Costos Fijos:</b> La reducción de gastos operativos inelásticos (alquiler, salarios base) disminuye directamente la barrera de rentabilidad.</li>");
        reporte.append("<li><b>Optimización de Costos Variables:</b> Negociar mejores condiciones con proveedores incrementa el margen unitario, facilitando la absorción de los costos fijos.</li>");
        reporte.append("</ul>");
        
        reporte.append("</div>");

                Window own = SwingUtilities.getWindowAncestor(vista);
                FrmAnalisisDialog diag = new FrmAnalisisDialog(own, "Reporte Gerencial - C&S ", reporte.toString());
                diag.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista,"Debe de ingresar datos validos y calcular primero. 🙂");
            }



        }

private void calcularEquilibrio() {
        new Thread(() -> {
            try {
                double cf = Double.parseDouble(vista.txtCostosFijos.getText());
                double p = Double.parseDouble(vista.txtPrecioVenta.getText());
                double cv = Double.parseDouble(vista.txtCostoVariable.getText());

                if (p <= cv) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista, "El precio debe ser mayor al costo variable."));
                    return;
                }

                double q = modelo.calcularPuntoEquilibrio(cf, p, cv);
                Object[][] matriz = modelo.generarSimulacion(cf, p, cv, q);

                SwingUtilities.invokeLater(() -> {
                    vista.lblUnidadesEquilibrio.setText(String.format("%.2f Unidades", q));
                    vista.lblIngresoEquilibrio.setText(String.format("C$ %.2f Necesarios", q * p));
                    vista.modeloTabla.setRowCount(0);
                    for (Object[] fila : matriz) vista.modeloTabla.addRow(fila);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista, "Datos inválidos."));
            }
        }).start();

        // Dentro del SwingUtilities.invokeLater después de llenar la tabla:
    vista.tblSimulacion.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        try {
            // Limpiamos el formato de moneda para convertir a número
            String valStr = value.toString().replace("C$", "").replace(",", "").trim();
            double utilidad = Double.parseDouble(valStr);

            if (utilidad < 0) {
                c.setForeground(Color.RED); // Pérdida en rojo
            } else if (utilidad == 0) {
                c.setForeground(new Color(0, 102, 204)); // Punto de equilibrio en azul
                c.setFont(c.getFont().deriveFont(Font.BOLD));
            } else {
                c.setForeground(new Color(0, 153, 0)); // Ganancia en verde
            }
        } catch (Exception e) {
            c.setForeground(table.getForeground());
        }
        return c;
    }
});
    }

    private void exportar(){
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            new Thread(() ->{
                try(PrintWriter pw = new PrintWriter((new FileWriter(fc.getSelectedFile()+".csv")))){
                    pw.println("Unidades, Ingresos, Costos, Utilidad");
                    for( int i = 0; i < vista.modeloTabla.getRowCount(); i++){
                        pw.println(vista.modeloTabla.getValueAt(i,0)+","+ vista.modeloTabla.getValueAt(i, 1)+","+ vista.modeloTabla.getValueAt(i, 2)+","+ vista.modeloTabla.getValueAt(i, 3));
                    }SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista, "Exportacion exitosa. ✅"));

                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista, "Error al exportar. ⚠️"));
                }
            }).start();
        }
    }



}
