package com_uni_sistemas_economica;

import javax.swing.*;
import java.awt.*;

public class V_Comparador extends JInternalFrame {
    // Variables para poder extraer los datos numéricos
    public JTextField txtVanA, txtTirA, txtVanB, txtTirB;
    public JButton btnComparar;

    public V_Comparador() {
        super("Comparador de Alternativas de Inversión", true, true, true, true);
        setSize(700, 350);

        // Panel del Proyecto A
        JPanel pnlA = new JPanel(new GridLayout(4, 1, 5, 5));
        pnlA.setBorder(BorderFactory.createTitledBorder("Datos: Proyecto A"));
        pnlA.add(new JLabel("VAN (Valor Actual Neto) C$:")); 
        txtVanA = new JTextField(); pnlA.add(txtVanA);
        pnlA.add(new JLabel("TIR (Tasa Interna Retorno) %:")); 
        txtTirA = new JTextField(); pnlA.add(txtTirA);

        // Panel del Proyecto B
        JPanel pnlB = new JPanel(new GridLayout(4, 1, 5, 5));
        pnlB.setBorder(BorderFactory.createTitledBorder("Datos: Proyecto B"));
        pnlB.add(new JLabel("VAN (Valor Actual Neto) C$:")); 
        txtVanB = new JTextField(); pnlB.add(txtVanB);
        pnlB.add(new JLabel("TIR (Tasa Interna Retorno) %:")); 
        txtTirB = new JTextField(); pnlB.add(txtTirB);

        // Uso del componente avanzado JSplitPane
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlA, pnlB);
        split.setDividerLocation(340);
        add(split, BorderLayout.CENTER);

        // Botón y su Evento
        btnComparar = new JButton("Comparar Rentabilidad y Decidir");
        add(btnComparar, BorderLayout.SOUTH);

        btnComparar.addActionListener(e -> {
            try {
                double vanA = Double.parseDouble(txtVanA.getText());
                double vanB = Double.parseDouble(txtVanB.getText());
                
                String recomendacion = "";
                
                // Lógica económica: Se elige el que tenga mayor VAN y que sea > 0
                if (vanA < 0 && vanB < 0) {
                    recomendacion = "Ninguno de los proyectos es viable. Ambos destruyen valor (VAN negativo).";
                } else if (vanA > vanB) {
                    recomendacion = "Se recomienda invertir en el PROYECTO A.\nGenera un mayor Valor Actual Neto.";
                } else if (vanB > vanA) {
                    recomendacion = "Se recomienda invertir en el PROYECTO B.\nGenera un mayor Valor Actual Neto.";
                } else {
                    recomendacion = "Ambos proyectos generan el mismo valor. Verifique la TIR para desempatar.";
                }

                JOptionPane.showMessageDialog(this, recomendacion, "Decisión de Inversión", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, llene todos los campos con números.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}