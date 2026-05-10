package com_uni_sistemas_economica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPuntoEquilibrio extends JInternalFrame {
    
    public JTextField txtCostosFijos, txtPrecioVenta, txtCostoVariable;
    public JButton btnSimular, btnExportar;
    public JLabel lblUnidadesEquilibrio, lblIngresoEquilibrio;
    public JTable tblSimulacion;
    public DefaultTableModel modeloTabla;

    public FrmPuntoEquilibrio() {
        super("Optimizador de Punto de Equilibrio", true, true, true, true);
        setSize(900, 550);
        setLayout(new BorderLayout(15, 15));

        lblUnidadesEquilibrio = new JLabel("0.00 Unidades para el Equilibrio", SwingConstants.CENTER);
        lblUnidadesEquilibrio.setFont(new Font("Arial", Font.BOLD, 32));
        lblUnidadesEquilibrio.setForeground(new Color(41, 128, 185));

        lblIngresoEquilibrio = new JLabel("C$ 0.00 Ingresos necesarios", SwingConstants.CENTER);
        lblIngresoEquilibrio.setFont(new Font("Arial", Font.BOLD, 26));
        lblIngresoEquilibrio.setForeground(new Color(44, 62, 80));

        JPanel pnlCards = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlCards.setOpaque(false);

        JPanel cardUnidades = new JPanel(new BorderLayout());
        cardUnidades.setBorder(BorderFactory.createTitledBorder("Volumen Crítico"));
        cardUnidades.add(lblUnidadesEquilibrio, BorderLayout.CENTER);

        JPanel cardIngreso = new JPanel(new BorderLayout());
        cardIngreso.setBorder(BorderFactory.createTitledBorder("Venta Mínima Requerida"));
        cardIngreso.add(lblIngresoEquilibrio, BorderLayout.CENTER);

        pnlCards.add(cardUnidades);
        pnlCards.add(cardIngreso);

        this.add(pnlCards, BorderLayout.NORTH);

        JPanel pnlContenedorIzquierdo = new JPanel(new BorderLayout());
        pnlContenedorIzquierdo.setPreferredSize(new Dimension(300, 0));

        JPanel pnlEntrada = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlEntrada.setBorder(BorderFactory.createTitledBorder("Datos de Operación"));

        JPanel grupoCF = new JPanel(new BorderLayout());
        grupoCF.add(new JLabel("Costos fijos totales (C$):"), BorderLayout.NORTH);
        txtCostosFijos = new JTextField();
        grupoCF.add(txtCostosFijos, BorderLayout.CENTER);
        pnlEntrada.add(grupoCF);

        JPanel grupoPV = new JPanel(new BorderLayout());
        grupoPV.add(new JLabel("Precio Venta x Unidad (C$):"), BorderLayout.NORTH);
        txtPrecioVenta = new JTextField();
        grupoPV.add(txtPrecioVenta, BorderLayout.CENTER);
        pnlEntrada.add(grupoPV);

        JPanel grupoCV = new JPanel(new BorderLayout());
        grupoCV.add(new JLabel("Costo Variable x Unidad (C$):"), BorderLayout.NORTH);
        txtCostoVariable = new JTextField();
        grupoCV.add(txtCostoVariable, BorderLayout.CENTER);
        pnlEntrada.add(grupoCV);

        btnSimular = new JButton("Calcular Punto de Equilibrio ✅");
        btnSimular.setFont(new Font("Arial", Font.BOLD, 14));
        pnlEntrada.add(btnSimular);

        pnlContenedorIzquierdo.add(pnlEntrada, BorderLayout.NORTH);
        add(pnlContenedorIzquierdo, BorderLayout.WEST);

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));

        String[] columnas = {"Unidades", "Ingresos", "Costos Tot.", "Utilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblSimulacion = new JTable(modeloTabla);
        tblSimulacion.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblSimulacion);
        pnlCentro.add(scroll, BorderLayout.CENTER);

        add(pnlCentro, BorderLayout.CENTER);

        btnExportar = new JButton("Exportar Tabla (CSV)");
        btnExportar.setPreferredSize(new Dimension(0, 40));
        add(btnExportar, BorderLayout.SOUTH);
    }
}