package com_uni_sistemas_economica;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class CtrlComparador implements ActionListener  {

    private FrmComparador vista;

    public CtrlComparador(FrmComparador vista){
        this.vista = vista;

        this.vista.btnComparar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnExportar.addActionListener(this);

        //
    }
    @Override
    public void actionPerformed(ActionEvent e){

        //provicional por error
        // JOptionPane.showMessageDialog(null, "¡El controlador SÍ está escuchando el clic!");

        if (e.getSource() == vista.btnComparar) {
            realizarComparativa();
        }else if (e.getSource() == vista.btnLimpiar) {
            limpiarCampos();
        }
        else if (e.getSource() == vista.btnExportar) {
            exportar();
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
            }else if (vA > vB) {
                vista.actualizarResultado("🏆 GANADOR: PROYECTO A (Mayor VAN)", new Color(41, 128, 185));
            }else if (vB > vA) {
                vista.actualizarResultado("🏆 GANADOR: PROYECTO B (Mayor VAN)", new Color(39, 174, 96));
            }else{
                if (tA > tB) {
                    vista.actualizarResultado("⚖️ EMPATE VAN: PROYECTO A gana por mejor TIR", new Color(41, 128, 185));
                }else if (tB > tA) {
                    vista.actualizarResultado("⚖️ EMPATE VAN: PROYECTO B gana por mejor TIR", new Color(39, 174, 96));
                }else{
                    vista.actualizarResultado("↔️ Alternativas idénticas", Color.GRAY);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,"Error: Porfavor Ingresa valores numéricos válidos." , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos(){
        vista.txtVanA.setText("");
        vista.txtVanB.setText("");
        vista.txtTirA.setText("");
        vista.txtTirB.setText("");
        vista.actualizarResultado("Esperando datos para análisis⌛", new Color(236, 240, 241));
    }

    private void exportar(){
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            new Thread(() ->{
                try (PrintWriter pw = new PrintWriter(new FileWriter(fc.getSelectedFile()))){
                    pw.println("Indicador,Proyecto A,Proyecto B");
                    pw.println("VAN (C$)," + vista.txtVanA.getText() + "," + vista.txtVanB.getText());
                    pw.println("TIR (%)," + vista.txtTirA.getText() + "," + vista.txtTirB.getText());
                    pw.println("Decision Recomendada," + vista.lblGanador.getText());

                    //aplicar
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog((vista),"Comparativa exportada exitosamente. ✅"));
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(vista,"Error al exportar.⚠️"));
                }
            }).start();;
        }
    }
//fck









}
