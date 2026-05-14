package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmEvaluacionProyecto extends JInternalFrame {
    public JTextField txtMonto, txtTasa, txtPlazo, txtFlujoCaja;
    public JComboBox<String> cbFrecuencia;
    public JButton btnCalcular, btnLimpiar, btnExportar, btnVerAnalisis; 
    public JTable tablaResultados;
    public DefaultTableModel modeloTabla;
    public JProgressBar barraProgreso;
    public JLabel lblVAN, lblTIR;

    public FrmEvaluacionProyecto() {
        super("Análisis de Inversión: VAN y TIR", true, true, true, true);
        setSize(920, 470);

        JPanel pnlPrincipal = new JPanel(new BorderLayout(15, 15));
        pnlPrincipal.setBackground(new Color(240, 244, 248));
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(10, 10));
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setBorder(crearBordeTarjeta());
        panelIzquierdo.setPreferredSize(new Dimension(320, 0));

        JPanel panelCampos = new JPanel(new GridLayout(10, 1, 5, 5));
        panelCampos.setBackground(Color.WHITE);
        panelCampos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(), "Parámetros de Inversión", 
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80)
        ));

        panelCampos.add(new JLabel("Inversión Inicial (C$):"));
        txtMonto = new JTextField("");
        txtMonto.putClientProperty("JTextField.placeholderText", "Ej. 5000");
        txtMonto.putClientProperty("JTextField.showClearButton", true);
        txtMonto.putClientProperty("JComponent.outline", "focus");
        panelCampos.add(txtMonto);

        panelCampos.add(new JLabel("Tasa de Interés (%):"));
        txtTasa = new JTextField("");
        txtTasa.putClientProperty("JTextField.placeholderText", "Ej. 12");
        txtTasa.putClientProperty("JTextField.showClearButton", true);
        txtTasa.putClientProperty("JComponent.outline", "focus");
        panelCampos.add(txtTasa);

        panelCampos.add(new JLabel("Frecuencia:"));
        cbFrecuencia = new JComboBox<>(new String[]{"Anual", "Semestral", "Trimestral", "Mensual"});
        panelCampos.add(cbFrecuencia);

        panelCampos.add(new JLabel("Plazo (Periodos):"));
        txtPlazo = new JTextField("");
        txtPlazo.putClientProperty("JTextField.placeholderText", "Ej. 12");
        txtPlazo.putClientProperty("JTextField.showClearButton", true);
        txtPlazo.putClientProperty("JComponent.outline", "focus");
        panelCampos.add(txtPlazo);

        panelCampos.add(new JLabel("Flujo de Caja Mensual (C$):"));
        txtFlujoCaja = new JTextField("");
        txtFlujoCaja.putClientProperty("JTextField.placeholderText","Ej. 500");
        txtFlujoCaja.putClientProperty("JTextField.showClearButton", true);
        txtFlujoCaja.putClientProperty("JComponent.outline", "focus");
        panelCampos.add(txtFlujoCaja);

        btnCalcular = new JButton(" Ejecutar Análisis");
        btnCalcular.setIcon(redimensionarIcono("/images/confirmarIcono.png", 20, 20));
        btnCalcular.putClientProperty("JButton.buttonType", "roundRect");
        btnCalcular.setBackground(new Color(39, 174, 96));
        btnCalcular.setForeground(Color.WHITE);

        btnLimpiar = new JButton(" Limpiar Campos");
        btnLimpiar.setIcon(redimensionarIcono("/images/borrarIcono.png", 20, 20));
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");
        
        JPanel pnlBotonAccion = new JPanel(new GridLayout(1, 2, 5, 5));
        pnlBotonAccion.setBackground(Color.WHITE);
        pnlBotonAccion.add(btnCalcular);
        pnlBotonAccion.add(btnLimpiar);

        panelIzquierdo.add(panelCampos, BorderLayout.NORTH);
        panelIzquierdo.add(pnlBotonAccion, BorderLayout.CENTER);

        barraProgreso = new JProgressBar();
        barraProgreso.setStringPainted(true);
        panelIzquierdo.add(barraProgreso, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.setBorder(crearBordeTarjeta());

        JPanel pnlDashboard = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlDashboard.setBackground(Color.WHITE);
        lblVAN = new JLabel("VAN: C$ 0.00", SwingConstants.CENTER);
        lblVAN.setFont(new Font("Arial", Font.BOLD, 18));
        lblVAN.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)));

        lblTIR = new JLabel("TIR: 0.00 %", SwingConstants.CENTER);
        lblTIR.setFont(new Font("Arial", Font.BOLD, 18));
        lblTIR.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)));

        pnlDashboard.add(lblVAN);
        pnlDashboard.add(lblTIR);

        String[] columnas = {"Periodo", "Cuota", "Interés", "Capital", "Saldo"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaResultados = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)));

        btnExportar = new JButton("Exportar CSV");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");
        btnExportar.setPreferredSize(new Dimension(0, 40));

        btnVerAnalisis = new JButton("Ver Análisis Detallado");
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41, 128, 185));
        btnVerAnalisis.setForeground(Color.WHITE);
        btnVerAnalisis.setPreferredSize(new Dimension(0, 40));

        JPanel pnlBotonesSur = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBotonesSur.setBackground(Color.WHITE);
        pnlBotonesSur.add(btnVerAnalisis);
        pnlBotonesSur.add(btnExportar);

        panelCentro.add(pnlDashboard, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelCentro.add(pnlBotonesSur, BorderLayout.SOUTH);

        pnlPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        pnlPrincipal.add(panelCentro, BorderLayout.CENTER);
        
        setContentPane(pnlPrincipal);
    }

    private javax.swing.border.Border crearBordeTarjeta() {
        javax.swing.border.Border bordeLinea = BorderFactory.createLineBorder(new Color(225, 230, 235), 1, true);
        javax.swing.border.Border margenInterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        return BorderFactory.createCompoundBorder(bordeLinea, margenInterno);
    }

    private ImageIcon redimensionarIcono(String ruta, int ancho, int alto) {
        try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (Exception e) {
            return null;
        }
    }
}