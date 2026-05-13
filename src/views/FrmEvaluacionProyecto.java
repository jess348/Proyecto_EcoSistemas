package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmEvaluacionProyecto extends JInternalFrame {
    public JTextField txtMonto, txtTasa, txtPlazo, txtFlujoCaja;
    public JComboBox<String> cbFrecuencia;
    // AQUÍ ESTÁ EL CAMBIO: Agregamos btnVerAnalisis
    public JButton btnCalcular, btnLimpiar, btnExportar, btnVerAnalisis; 
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
        txtMonto.putClientProperty("JTextField.placeholderText", "Ej. 150000"); // Magia FlatLaf
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

        // --- CONFIGURACIÓN DE BOTONES CON ÍCONOS Y FLATLAF ---
        btnCalcular = new JButton(" Ejecutar Análisis");
        btnCalcular.setIcon(redimensionarIcono("/images/confirmarIcono.png", 20, 20));
        btnCalcular.putClientProperty("JButton.buttonType", "roundRect");
        btnCalcular.setBackground(new Color(39, 174, 96)); // Verde
        btnCalcular.setForeground(Color.WHITE);

        btnLimpiar = new JButton(" Limpiar Campos");
        btnLimpiar.setIcon(redimensionarIcono("/images/borrarIcono.png", 20, 20));
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");
        
        JPanel pnlBotonAccion = new JPanel(new GridLayout(1, 2, 5, 5));
        pnlBotonAccion.add(btnCalcular);
        pnlBotonAccion.add(btnLimpiar);

        panelIzquierdo.add(panelCampos, BorderLayout.NORTH);
        panelIzquierdo.add(pnlBotonAccion, BorderLayout.CENTER);

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        panelIzquierdo.add(barraProgreso, BorderLayout.SOUTH);

        // --- PANEL CENTRAL (RESULTADOS) ---
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 15));

        // Dashboard de Resultados
        JPanel pnlDashboard = new JPanel(new GridLayout(1, 2, 10, 10));
        lblVAN = new JLabel("VAN: C$ 0.00", SwingConstants.CENTER);
        lblVAN.setFont(new Font("Arial", Font.BOLD, 18));
        lblVAN.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        lblTIR = new JLabel("TIR: 0.00 %", SwingConstants.CENTER);
        lblTIR.setFont(new Font("Arial", Font.BOLD, 18));
        lblTIR.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        pnlDashboard.add(lblVAN);
        pnlDashboard.add(lblTIR);

        // Tabla
        String[] columnas = {"Periodo", "Cuota", "Interés", "Capital", "Saldo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);

        // --- BOTONES INFERIORES (EXPORTAR Y NUEVO ANÁLISIS) ---
        btnExportar = new JButton("Exportar CSV");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");
        btnExportar.setPreferredSize(new Dimension(0, 40));

        btnVerAnalisis = new JButton("Ver Análisis Detallado");
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41, 128, 185)); // Azul corporativo
        btnVerAnalisis.setForeground(Color.WHITE);
        btnVerAnalisis.setPreferredSize(new Dimension(0, 40));

        JPanel pnlBotonesSur = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBotonesSur.add(btnVerAnalisis);
        pnlBotonesSur.add(btnExportar);

        panelCentro.add(pnlDashboard, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelCentro.add(pnlBotonesSur, BorderLayout.SOUTH);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelCentro, BorderLayout.CENTER);
    }

    // Método para ajustar el tamaño de los íconos automáticamente
    private ImageIcon redimensionarIcono(String ruta, int ancho, int alto) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (Exception e) {
            System.err.println("No se encontró el ícono en: " + ruta);
            return null; // Devuelve nulo si falla para que el programa no se caiga
        }
    }
}