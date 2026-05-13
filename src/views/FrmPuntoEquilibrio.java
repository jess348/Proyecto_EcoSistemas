package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmPuntoEquilibrio extends JInternalFrame {
    
    public JTextField txtCostosFijos, txtPrecioVenta, txtCostoVariable;
    // Se agregan btnLimpiar y btnVerAnalisis
    public JButton btnSimular, btnLimpiar, btnExportar, btnVerAnalisis;
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

        //usar placeHolder para mejorar el Field

        JPanel grupoCF = new JPanel(new BorderLayout());
        grupoCF.add(new JLabel("Costos fijos totales (C$):"), BorderLayout.NORTH);
        txtCostosFijos = new JTextField();
        txtCostosFijos.putClientProperty("JTextField.placeholderText", "Ej. 1200");
        grupoCF.add(txtCostosFijos, BorderLayout.CENTER);
        pnlEntrada.add(grupoCF);

        JPanel grupoPV = new JPanel(new BorderLayout());
        grupoPV.add(new JLabel("Precio Venta x Unidad (C$):"), BorderLayout.NORTH);
        txtPrecioVenta = new JTextField();
        txtPrecioVenta.putClientProperty("JTextField.placeholderText", "Ej. 150");
        grupoPV.add(txtPrecioVenta, BorderLayout.CENTER);
        pnlEntrada.add(grupoPV);

        JPanel grupoCV = new JPanel(new BorderLayout());
        grupoCV.add(new JLabel("Costo Variable x Unidad (C$):"), BorderLayout.NORTH);
        txtCostoVariable = new JTextField();
        txtCostoVariable.putClientProperty("JTextField.placeholderText", "Ej. 70");
        grupoCV.add(txtCostoVariable, BorderLayout.CENTER);
        pnlEntrada.add(grupoCV);

        // Configuración de botones con FlatLaf e Íconos
        //roundRect, dejar botones redondos ja
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
        pnlBotonesAccion.add(btnSimular);
        pnlBotonesAccion.add(btnLimpiar);
        pnlEntrada.add(pnlBotonesAccion);

        pnlContenedorIzquierdo.add(pnlEntrada, BorderLayout.NORTH);
        add(pnlContenedorIzquierdo, BorderLayout.WEST);

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));

        String[] columnas = {"Unidades", "Ingresos", "Costos Tot.", "Utilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tblSimulacion = new JTable(modeloTabla);
        tblSimulacion.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(tblSimulacion);
        pnlCentro.add(scroll, BorderLayout.CENTER);

        // Botones inferiores
        btnExportar = new JButton("Exportar Tabla (CSV)");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");
        btnExportar.setPreferredSize(new Dimension(0, 40));
        
        //nuevo btn
        btnVerAnalisis = new JButton("Ver Análisis Detallado 📊");
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41, 128, 185));
        btnVerAnalisis.setForeground(Color.WHITE);
        btnVerAnalisis.setPreferredSize(new Dimension(0, 40));

        JPanel pnlBotonesSur = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlBotonesSur.add(btnVerAnalisis);
        pnlBotonesSur.add(btnExportar);

        pnlCentro.add(pnlBotonesSur, BorderLayout.SOUTH);
        add(pnlCentro, BorderLayout.CENTER);
    }

    //para que el icono siempre encaje bien
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