package com_uni_sistemas_economica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class V_EvaluacionProyecto extends JInternalFrame {
    // Componentes de entrada
    public JTextField txtMonto, txtTasa, txtPlazo;
    public JTextField txtFlujoCaja;
    public JComboBox<String> cbFrecuencia;
    public JButton btnCalcular;
    
    // Componentes de salida
    public JTable tablaResultados;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;
    public JLabel lblVAN, lblTIR;

    public V_EvaluacionProyecto() {
        super("Análisis de Inversión: VAN y TIR", true, true, true, true);
        setSize(750, 600);
        setLayout(new BorderLayout());

        JTabbedPane pestañas = new JTabbedPane();

        // --- PANEL DE CONFIGURACIÓN (ENTRADA) ---
        JPanel panelConfig = new JPanel(new GridLayout(8, 2, 10, 10));
        panelConfig.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        panelConfig.add(new JLabel("Inversión Inicial (C$):"));
        txtMonto = new JTextField("5000"); 
        panelConfig.add(txtMonto);
        
        panelConfig.add(new JLabel("Tasa de Interés (%):"));
        txtTasa = new JTextField("12"); 
        panelConfig.add(txtTasa);

        panelConfig.add(new JLabel("Frecuencia de la Tasa:"));
        cbFrecuencia = new JComboBox<>(new String[]{"Anual", "Semestral", "Trimestral", "Mensual"});
        panelConfig.add(cbFrecuencia);
        
        panelConfig.add(new JLabel("Plazo (Periodos):"));
        txtPlazo = new JTextField("12"); 
        panelConfig.add(txtPlazo);

        panelConfig.add(new JLabel("Flujo de Caja Estimado (C$):"));
        txtFlujoCaja = new JTextField("500"); 
        panelConfig.add(txtFlujoCaja);
        
        btnCalcular = new JButton("Ejecutar Análisis Financiero");
        panelConfig.add(btnCalcular);
        
        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        panelConfig.add(barraProgreso);

        // Etiquetas de Resultados
        lblVAN = new JLabel("VAN: C$ 0.00");
        lblVAN.setFont(new Font("Arial", Font.BOLD, 14));
        lblVAN.setForeground(new Color(0, 102, 51)); // Verde oscuro
        panelConfig.add(lblVAN);

        lblTIR = new JLabel("TIR: 0.00 %");
        lblTIR.setFont(new Font("Arial", Font.BOLD, 14));
        lblTIR.setForeground(new Color(0, 51, 153)); // Azul oscuro
        panelConfig.add(lblTIR);

        // --- PANEL DE TABLA (RESULTADOS) ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        String[] columnas = {"Periodo", "Cuota", "Interés", "Capital", "Saldo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        panelTabla.add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        // Agregar pestañas
        pestañas.addTab("Parámetros de Inversión", panelConfig);
        pestañas.addTab("Tabla de Amortización", panelTabla);

        add(pestañas, BorderLayout.CENTER);
    }
}