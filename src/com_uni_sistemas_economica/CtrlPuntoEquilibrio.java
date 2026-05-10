package com_uni_sistemas_economica;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
        
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()== vista.btnSimular) {
            calcularEquilibrio();
        }else if (e.getSource() == vista.btnExportar) {
            exportar();
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
