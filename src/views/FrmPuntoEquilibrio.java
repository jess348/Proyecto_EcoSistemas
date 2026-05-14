package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPuntoEquilibrio extends JInternalFrame {
    
    public JTextField txtCostosFijos, txtPrecioVenta, txtCostoVariable;
    public JButton btnSimular, btnLimpiar, btnExportar, btnVerAnalisis;
    public JLabel lblUnidadesEquilibrio, lblIngresoEquilibrio;
    public JTable tblSimulacion;
    public DefaultTableModel modeloTabla;

    public FrmPuntoEquilibrio() {
        super("Optimizador de Punto de Equilibrio", true, true, true, true);
        setSize(900, 550);
        
        JPanel pnlPrincipal = new JPanel(new BorderLayout(15, 15));
        pnlPrincipal.setBackground(new Color(240, 244, 248));
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblUnidadesEquilibrio = new JLabel("0.00 Unidades", SwingConstants.CENTER);
        lblUnidadesEquilibrio.setFont(new Font("Arial", Font.BOLD, 32));
        lblUnidadesEquilibrio.setForeground(new Color(41, 128, 185));

        lblIngresoEquilibrio = new JLabel("C$ 0.00", SwingConstants.CENTER);
        lblIngresoEquilibrio.setFont(new Font("Arial", Font.BOLD, 26));
        lblIngresoEquilibrio.setForeground(new Color(39, 174, 96));

        JPanel pnlCards = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlCards.setBackground(new Color(240, 244, 248));

        JPanel cardUnidades = new JPanel(new BorderLayout());
        cardUnidades.setBackground(Color.WHITE);
        cardUnidades.setBorder(BorderFactory.createCompoundBorder(
            crearBordeTarjeta(),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Volumen Crítico para Equilibrio", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80))
        ));
        cardUnidades.add(lblUnidadesEquilibrio, BorderLayout.CENTER);

        JPanel cardIngreso = new JPanel(new BorderLayout());
        cardIngreso.setBackground(Color.WHITE);
        cardIngreso.setBorder(BorderFactory.createCompoundBorder(
            crearBordeTarjeta(),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Venta Mínima Requerida", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80))
        ));
        cardIngreso.add(lblIngresoEquilibrio, BorderLayout.CENTER);

        pnlCards.add(cardUnidades);
        pnlCards.add(cardIngreso);

        pnlPrincipal.add(pnlCards, BorderLayout.NORTH);

        JPanel pnlContenedorIzquierdo = new JPanel(new BorderLayout());
        pnlContenedorIzquierdo.setPreferredSize(new Dimension(300, 0));
        pnlContenedorIzquierdo.setBackground(new Color(240, 244, 248));

        JPanel pnlEntrada = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlEntrada.setBackground(Color.WHITE);
        pnlEntrada.setBorder(BorderFactory.createCompoundBorder(
            crearBordeTarjeta(),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Datos de Operación", 
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80))
        ));

        JPanel grupoCF = new JPanel(new BorderLayout());
        grupoCF.setBackground(Color.WHITE);
        grupoCF.add(new JLabel("Costos fijos totales (C$):"), BorderLayout.NORTH);
        txtCostosFijos = new JTextField();
        txtCostosFijos.putClientProperty("JTextField.placeholderText", "Ej. 1200");
        txtCostosFijos.putClientProperty("JTextField.showClearButton", true);
        txtCostosFijos.putClientProperty("JComponent.outline", "focus");
        grupoCF.add(txtCostosFijos, BorderLayout.CENTER);
        pnlEntrada.add(grupoCF);

        JPanel grupoPV = new JPanel(new BorderLayout());
        grupoPV.setBackground(Color.WHITE);
        grupoPV.add(new JLabel("Precio Venta x Unidad (C$):"), BorderLayout.NORTH);
        txtPrecioVenta = new JTextField();
        txtPrecioVenta.putClientProperty("JTextField.placeholderText", "Ej. 150");
        txtPrecioVenta.putClientProperty("JTextField.showClearButton", true);
        txtPrecioVenta.putClientProperty("JComponent.outline", "focus");
        grupoPV.add(txtPrecioVenta, BorderLayout.CENTER);
        pnlEntrada.add(grupoPV);

        JPanel grupoCV = new JPanel(new BorderLayout());
        grupoCV.setBackground(Color.WHITE);
        grupoCV.add(new JLabel("Costo Variable x Unidad (C$):"), BorderLayout.NORTH);
        txtCostoVariable = new JTextField();
        txtCostoVariable.putClientProperty("JTextField.placeholderText", "Ej. 70");
        txtCostoVariable.putClientProperty("JTextField.showClearButton", true);
        txtCostoVariable.putClientProperty("JComponent.outline", "focus");
        grupoCV.add(txtCostoVariable, BorderLayout.CENTER);
        pnlEntrada.add(grupoCV);

        btnSimular = new JButton(" Calcular Equilibrio");
        btnSimular.setIcon(redimensionarIcono("/images/confirmarIcono.png", 20, 20));
        btnSimular.putClientProperty("JButton.buttonType", "roundRect");
        btnSimular.setBackground(new Color(39, 174, 96));
        btnSimular.setForeground(Color.WHITE);
        btnSimular.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnLimpiar = new JButton(" Limpiar Campos");
        btnLimpiar.setIcon(redimensionarIcono("/images/borrarIcono.png", 20, 20));
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");

        JPanel pnlBotonesAccion = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlBotonesAccion.setBackground(Color.WHITE);
        pnlBotonesAccion.add(btnSimular);
        pnlBotonesAccion.add(btnLimpiar);
        pnlEntrada.add(pnlBotonesAccion);

        pnlContenedorIzquierdo.add(pnlEntrada, BorderLayout.NORTH);
        pnlPrincipal.add(pnlContenedorIzquierdo, BorderLayout.WEST);

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        pnlCentro.setBackground(new Color(240, 244, 248));

        String[] columnas = {"Unidades", "Ingresos", "Costos Tot.", "Utilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblSimulacion = new JTable(modeloTabla);
        tblSimulacion.setRowHeight(25);
        
        JScrollPane scroll = new JScrollPane(tblSimulacion);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel pnlTablaWrapper = new JPanel(new BorderLayout());
        pnlTablaWrapper.setBackground(Color.WHITE);
        pnlTablaWrapper.setBorder(crearBordeTarjeta());
        pnlTablaWrapper.add(scroll, BorderLayout.CENTER);
        
        pnlCentro.add(pnlTablaWrapper, BorderLayout.CENTER);

        btnExportar = new JButton("Exportar Tabla (CSV)");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");
        btnExportar.setPreferredSize(new Dimension(0, 40));
        
        btnVerAnalisis = new JButton("Ver Análisis Detallado 📊");
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41, 128, 185));
        btnVerAnalisis.setForeground(Color.WHITE);
        btnVerAnalisis.setPreferredSize(new Dimension(0, 40));

        JPanel pnlBotonesSur = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBotonesSur.setBackground(new Color(240, 244, 248));
        pnlBotonesSur.add(btnVerAnalisis);
        pnlBotonesSur.add(btnExportar);

        pnlCentro.add(pnlBotonesSur, BorderLayout.SOUTH);
        pnlPrincipal.add(pnlCentro, BorderLayout.CENTER);
        
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