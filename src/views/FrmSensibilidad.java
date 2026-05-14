package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FrmSensibilidad extends JInternalFrame {
    public JSlider sldIngresos, sldCostos, sldTasa;
    public JButton btnRecalcular, btnExportar, btnVerAnalisis;
    public JTable tblResultados;
    public DefaultTableModel modeloTabla;
    public JProgressBar prgCalculando;
    public JPanel pnlResultadoHeader;
    public JLabel lblEstadoGeneral;

    public FrmSensibilidad() {
        super("Módulo de Análisis de Sensibilidad (What-If)", true, true, true, true);
        setSize(925, 600);

        JPanel pnlPrincipal = new JPanel(new BorderLayout(15, 15));
        pnlPrincipal.setBackground(new Color(240, 244, 248));
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Panel Superior (Enunciado de la imagen) ---
        pnlResultadoHeader = new JPanel(new BorderLayout());
        pnlResultadoHeader.setBackground(new Color(236, 235, 220)); 
        pnlResultadoHeader.setPreferredSize(new Dimension(0, 70));
        pnlResultadoHeader.setBorder(crearBordeTarjeta());
        
        lblEstadoGeneral = new JLabel("Inversión Base: C$ 100,000 | Tasa Base: 12% | Plazo: 12 meses", SwingConstants.CENTER);
        lblEstadoGeneral.setFont(new Font("Arial", Font.BOLD, 16));
        pnlResultadoHeader.add(lblEstadoGeneral, BorderLayout.CENTER);
        pnlPrincipal.add(pnlResultadoHeader, BorderLayout.NORTH);

        // --- Panel de Controles (WEST) ---
        JPanel pnlControlesWrapper = new JPanel(new BorderLayout());
        pnlControlesWrapper.setBackground(Color.WHITE);
        pnlControlesWrapper.setBorder(crearBordeTarjeta());

        JPanel pnlControles = new JPanel(new GridLayout(6, 1, 5, 5));
        pnlControles.setBackground(Color.WHITE);
        pnlControles.setPreferredSize(new Dimension(260, 0));

        sldIngresos = crearSlider(-20, 20, 0, 10, "Variación Ingresos Mensuales");
        sldCostos = crearSlider(-20, 20, 0, 10, "Variación Costos Operativos");
        sldTasa = crearSlider(-5, 5, 0, 2, "Variación Tasa de Interés");

        pnlControles.add(new JLabel("Variación Ingresos Mensuales")); pnlControles.add(sldIngresos);
        pnlControles.add(new JLabel("Variación Costos Operativos")); pnlControles.add(sldCostos);
        pnlControles.add(new JLabel("Variación Tasa de Interés")); pnlControles.add(sldTasa);

        pnlControlesWrapper.add(pnlControles, BorderLayout.CENTER);
        pnlPrincipal.add(pnlControlesWrapper, BorderLayout.WEST);

        // --- Tabla Estilo Oscuro (CENTER) ---
        String[] col = {"Métrica", "Escenario Pesimista", "Escenario Realista", "Escenario Optimista"};
        modeloTabla = new DefaultTableModel(col, 0);
        tblResultados = new JTable(modeloTabla);
        tblResultados.setBackground(new Color(45, 45, 48));
        tblResultados.setForeground(Color.WHITE);
        tblResultados.setRowHeight(35);
        
        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(45, 45, 48));

        JPanel pnlTablaWrapper = new JPanel(new BorderLayout());
        pnlTablaWrapper.setBackground(Color.WHITE);
        pnlTablaWrapper.setBorder(crearBordeTarjeta());
        pnlTablaWrapper.add(scroll, BorderLayout.CENTER);

        pnlPrincipal.add(pnlTablaWrapper, BorderLayout.CENTER);

        // --- Panel Sur: Botones y Progreso ---
        JPanel pnlSur = new JPanel(new BorderLayout(0, 10));
        pnlSur.setBackground(new Color(240, 244, 248));
        
        JPanel pnlBtns = new JPanel(new GridLayout(1, 3, 10, 10));
        pnlBtns.setBackground(new Color(240, 244, 248));

        btnRecalcular = new JButton("RECALCULAR ESCENARIOS");
        btnRecalcular.setIcon(redimensionarIcono("/images/confirmarIcono.png", 20, 20));
        btnRecalcular.putClientProperty("JButton.buttonType", "roundRect");
        btnRecalcular.setBackground(new Color(39,174,96));
        btnRecalcular.setForeground(Color.WHITE);

        btnExportar = new JButton("Exportar a CSV 📄");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");

        prgCalculando = new JProgressBar();

        btnVerAnalisis = new JButton("Ver Análisis Detallado 📈");
        // btnVerAnalisis.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41,128,40));
        btnVerAnalisis.setForeground(Color.WHITE);
        btnVerAnalisis.setPreferredSize(new Dimension(0,40));
        
        pnlBtns.add(btnRecalcular); 
        pnlBtns.add(btnExportar); 
        pnlBtns.add(btnVerAnalisis);
        
        pnlSur.add(pnlBtns, BorderLayout.CENTER);
        pnlSur.add(prgCalculando, BorderLayout.SOUTH);
        
        pnlPrincipal.add(pnlSur, BorderLayout.SOUTH);

        setContentPane(pnlPrincipal);
    }

    private JSlider crearSlider(int min, int max, int val, int ticks, String tit) {
        JSlider s = new JSlider(min, max, val);
        s.setBackground(Color.WHITE);
        s.setMajorTickSpacing(ticks);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        return s;
    }

    private javax.swing.border.Border crearBordeTarjeta() {
        javax.swing.border.Border bordeLinea = BorderFactory.createLineBorder(new Color(225, 230, 235), 1, true);
        javax.swing.border.Border margenInterno = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        return BorderFactory.createCompoundBorder(bordeLinea, margenInterno);
    }

    public void actualizarEstado(String msg, Color fondo) {
        lblEstadoGeneral.setText(msg);
        pnlResultadoHeader.setBackground(fondo);
        lblEstadoGeneral.setForeground(fondo.getGreen() > 150 || fondo.equals(Color.DARK_GRAY) ? Color.WHITE : Color.BLACK);
    }
    
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