package views;

import javax.swing.*;
import java.awt.*;

public class FrmComparador extends JInternalFrame {
    
    public JTextField txtVanA, txtTirA, txtVanB, txtTirB;
    public JButton btnComparar, btnLimpiar, btnExportar, btnVerAnalisis;
    public JLabel lblGanador;
    public JPanel pnlResultado;

    public FrmComparador() {
        super("Comparador de Alternativas de Inversión", true, true, true, true);
        setSize(850, 500);
        
        JPanel pnlPrincipal = new JPanel(new BorderLayout(15, 15));
        pnlPrincipal.setBackground(new Color(240, 244, 248));
        pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        pnlResultado = new JPanel(new BorderLayout());
        pnlResultado.setBackground(Color.WHITE);
        pnlResultado.setBorder(crearBordeTarjeta());
        pnlResultado.setPreferredSize(new Dimension(0, 80));
        
        lblGanador = new JLabel("Esperando datos para análisis...", SwingConstants.CENTER);
        lblGanador.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblGanador.setForeground(new Color(44, 62, 80));
        pnlResultado.add(lblGanador, BorderLayout.CENTER);
        pnlPrincipal.add(pnlResultado, BorderLayout.NORTH);

        txtVanA = new JTextField(); 
        txtVanA.putClientProperty("JTextField.placeholderText", "Ej. 25000");
        aplicarSuperPoderes(txtVanA);
        txtTirA = new JTextField();
        txtTirA.putClientProperty("JTextField.placeholderText", "Ej. 15");
        aplicarSuperPoderes(txtTirA);
        JPanel pnlA = crearPanelProyecto("Proyecto A", new Color(41, 128, 185));
        configurarCampos(pnlA, txtVanA, txtTirA);

        txtVanB = new JTextField(); 
        txtVanB.putClientProperty("JTextField.placeholderText", "Ej. 30000");
        aplicarSuperPoderes(txtVanB);
        txtTirB = new JTextField();
        txtTirB.putClientProperty("JTextField.placeholderText", "Ej. 12");
        aplicarSuperPoderes(txtTirB);
        JPanel pnlB = crearPanelProyecto("Proyecto B", new Color(39, 174, 96));
        configurarCampos(pnlB, txtVanB, txtTirB);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlA, pnlB);
        split.setDividerLocation(400);
        split.setBorder(null);
        split.setBackground(new Color(240, 244, 248));
        split.setOpaque(false);
        pnlPrincipal.add(split, BorderLayout.CENTER);

        JPanel pnlAcciones = new JPanel(new GridLayout(1, 4, 10, 10));
        pnlAcciones.setPreferredSize(new Dimension(0, 50));
        pnlAcciones.setBackground(new Color(240, 244, 248));
        
        btnComparar = new JButton(" Ejecutar Comparativa");
        btnComparar.setIcon(redimensionarIcono("/images/confirmarIcono.png", 20, 20));
        btnComparar.putClientProperty("JButton.buttonType", "roundRect");
        btnComparar.setBackground(new Color(39,174,96));
        btnComparar.setForeground(Color.white);
        
        btnLimpiar = new JButton(" Reiniciar");
        btnLimpiar.setIcon(redimensionarIcono("/images/borrarIcono.png", 20, 20));
        btnLimpiar.putClientProperty("JButton.buttonType", "roundRect");

        btnExportar = new JButton(" Exportar Comparativo");
        btnExportar.setIcon(redimensionarIcono("/images/nuevo-documentoIcono.png", 20, 20));
        btnExportar.putClientProperty("JButton.buttonType", "roundRect");
        
        btnVerAnalisis = new JButton(" Ver Análisis Detallado 📈");
        btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
        btnVerAnalisis.setBackground(new Color(41,128,185));
        btnVerAnalisis.setForeground(Color.WHITE);

        pnlAcciones.add(btnComparar);
        pnlAcciones.add(btnLimpiar);
        pnlAcciones.add(btnExportar);
        pnlAcciones.add(btnVerAnalisis);
        pnlPrincipal.add(pnlAcciones, BorderLayout.SOUTH);

        setContentPane(pnlPrincipal);
    }

    private void aplicarSuperPoderes(JTextField txt) {
        txt.putClientProperty("JTextField.showClearButton", true);
        txt.putClientProperty("JComponent.outline", "focus");
    }

    private JPanel crearPanelProyecto(String titulo, Color colorBase) {
        JPanel p = new JPanel(new GridLayout(5, 1, 10, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
            crearBordeTarjeta(),
            BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), titulo,
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 16), colorBase)
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