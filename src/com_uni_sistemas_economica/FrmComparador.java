package com_uni_sistemas_economica;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FrmComparador extends JInternalFrame {
    // Componentes públicos para que el Controlador pueda acceder a ellos
    public JTextField txtVanA, txtTirA, txtVanB, txtTirB;
    public JButton btnComparar, btnLimpiar, btnExportar;
    public JLabel lblGanador;
    public JPanel pnlResultado;

    public FrmComparador() {
        super("Comparador de Alternativas de Inversión", true, true, true, true);
        setSize(850, 500);
        setLayout(new BorderLayout(15, 15));

        pnlResultado = new JPanel(new BorderLayout());
        pnlResultado.setBackground(new Color(236, 240, 241));
        pnlResultado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pnlResultado.setPreferredSize(new Dimension(0, 80));
        
        lblGanador = new JLabel("Esperando datos para análisis...", SwingConstants.CENTER);
        lblGanador.setFont(new Font("Arial", Font.BOLD, 22));
        pnlResultado.add(lblGanador, BorderLayout.CENTER);
        add(pnlResultado, BorderLayout.NORTH);

        JPanel pnlA = crearPanelProyecto("Proyecto A", new Color(41, 128, 185));
        txtVanA = new JTextField(); txtTirA = new JTextField();
        configurarCampos(pnlA, txtVanA, txtTirA);

        JPanel pnlB = crearPanelProyecto("Proyecto B", new Color(39, 174, 96));
        txtVanB = new JTextField(); txtTirB = new JTextField();
        configurarCampos(pnlB, txtVanB, txtTirB);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlA, pnlB);
        split.setDividerLocation(400);
        add(split, BorderLayout.CENTER);

        JPanel pnlAcciones = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlAcciones.setPreferredSize(new Dimension(0, 50));
        
        btnComparar = new JButton("Ejecutar Comparativa 🏆");
        btnLimpiar = new JButton("Reiniciar 🧹");
        btnExportar = new JButton("Exportar Comparativo 📄");

        pnlAcciones.add(btnComparar);
        pnlAcciones.add(btnLimpiar);
        pnlAcciones.add(btnExportar);
        add(pnlAcciones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelProyecto(String titulo, Color colorBase) {
        JPanel p = new JPanel(new GridLayout(5, 1, 10, 10));
        TitledBorder border = BorderFactory.createTitledBorder(titulo);
        border.setTitleColor(colorBase);
        border.setTitleFont(new Font("Arial", Font.BOLD, 16));
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            border
        ));
        return p;
    }

    private void configurarCampos(JPanel p, JTextField van, JTextField tir) {
        p.add(new JLabel("VAN (Valor Actual Neto) C$:"));
        p.add(van);
        p.add(new JLabel("TIR (Rentabilidad Esperada) %:"));
        p.add(tir);
    }

    public void actualizarResultado(String texto, Color fondo) {
        lblGanador.setText(texto);
        lblGanador.setForeground(Color.WHITE);
        pnlResultado.setBackground(fondo);
    }
}