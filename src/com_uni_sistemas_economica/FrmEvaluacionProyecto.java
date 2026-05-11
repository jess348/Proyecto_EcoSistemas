package com_uni_sistemas_economica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmEvaluacionProyecto extends JInternalFrame {
    public JTextField txtMonto, txtTasa, txtPlazo, txtFlujoCaja;
    public JComboBox<String> cbFrecuencia;
    public JButton btnCalcular, btnLimpiar, btnExportar; // Agregados
    public JTable tablaResultados;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;
    public JLabel lblVAN, lblTIR;

    public FrmEvaluacionProyecto() {
        super("Análisis de Inversión: VAN y TIR", true, true, true, true);
        setSize(920, 410);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL IZQUIERDO (CONFIGURACIÓN) ---
        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));

        JPanel panelCampos = new JPanel(new GridLayout(10, 1, 5, 5));
        panelCampos.setBorder(BorderFactory.createTitledBorder("Parámetros de Inversión"));

        panelCampos.add(new JLabel("Inversión Inicial (C$):"));
        txtMonto = new JTextField("5000");
        panelCampos.add(txtMonto);

        panelCampos.add(new JLabel("Tasa de Interés (%):"));
        txtTasa = new JTextField("12");
        panelCampos.add(txtTasa);

        panelCampos.add(new JLabel("Frecuencia:"));
        cbFrecuencia = new JComboBox<>(new String[]{"Anual", "Semestral", "Trimestral", "Mensual"});
        panelCampos.add(cbFrecuencia);

        panelCampos.add(new JLabel("Plazo (Periodos):"));
        txtPlazo = new JTextField("12");
        panelCampos.add(txtPlazo);

        panelCampos.add(new JLabel("Flujo de Caja Mensual (C$):"));
        txtFlujoCaja = new JTextField("500");
        panelCampos.add(txtFlujoCaja);

        btnCalcular = new JButton("Ejecutar Análisis ✅");
        btnLimpiar = new JButton("Limpiar Campos 🧹");
        
        JPanel pnlBotonAccion = new JPanel(new GridLayout(1, 2, 5, 5));
        pnlBotonAccion.add(btnCalcular);
        pnlBotonAccion.add(btnLimpiar);

        panelIzquierdo.add(panelCampos, BorderLayout.NORTH);
        panelIzquierdo.add(pnlBotonAccion, BorderLayout.CENTER);

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        panelIzquierdo.add(barraProgreso, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));

        // Dashboard de Resultados
        JPanel pnlDashboard = new JPanel(new GridLayout(1, 2, 10, 10));
        lblVAN = new JLabel("VAN: C$ 0.00", SwingConstants.CENTER);
        lblVAN.setFont(new Font("Arial", Font.BOLD, 18));
        lblVAN.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));//verde oscuro

        lblTIR = new JLabel("TIR: 0.00 %", SwingConstants.CENTER);
        lblTIR.setFont(new Font("Arial", Font.BOLD, 18));
        lblTIR.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));//verde oscuro

        pnlDashboard.add(lblVAN);
        pnlDashboard.add(lblTIR);

        // Tabla
        String[] columnas = {"Periodo", "Cuota", "Interés", "Capital", "Saldo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        // Botón Exportar al fondo de la tabla
        btnExportar = new JButton("Exportar Resultados a CSV 📄");
        btnExportar.setPreferredSize(new Dimension(0, 40));

        panelCentro.add(pnlDashboard, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelCentro.add(btnExportar, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
    }
}