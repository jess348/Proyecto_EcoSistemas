package views;

import controllers.CtrlDashboard;
import javax.swing.*;
import java.awt.*;

public class FrmDashboardFinanciero extends JInternalFrame {

        public JButton btnCargarBalance;
        public JButton btnCargarEstadoRes;
        public JButton btnGuardarTXT;
        public JButton btnCalcularManual;
        public JButton btnLimpiar;

        public JLabel lblCapitalTrabajo;
        public JLabel lblMargenUtilidad;
        public JLabel lblNivelDeuda;

        public JTextArea txtHistorialAlertas;
        public JProgressBar progressBar;

        public JTextField txtActivos;
        public JTextField txtPasivos;
        public JTextField txtUtilidad;
        public JTextField txtIngresos;

        public FrmDashboardFinanciero() {
        // Adaptado a JInternalFrame
        super("Dashboard de Salud Financiera", true, true, true, true);
        setSize(930, 600); // Ajusté el tamaño para que encaje mejor en tu escritorio
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        initComponents();

        // Conecta el controlador automáticamente
        new CtrlDashboard(this);
        }

        private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // --- Panel Superior ---
        JPanel panelSuperior = new JPanel(new FlowLayout());
        btnCargarBalance = new JButton("Cargar Balance");
        btnCargarEstadoRes = new JButton("Cargar Estado Resultado");
        btnGuardarTXT = new JButton("Guardar TXT");

        panelSuperior.add(btnCargarBalance);
        panelSuperior.add(btnCargarEstadoRes);
        panelSuperior.add(btnGuardarTXT);
        add(panelSuperior, BorderLayout.NORTH);

        // --- Panel Lateral (Ingreso Manual) ---
        JPanel panelManual = new JPanel(new GridLayout(6, 2, 10, 10));
        panelManual.setBorder(BorderFactory.createTitledBorder("Ingreso Manual"));

        panelManual.add(new JLabel("Activos"));
        txtActivos = new JTextField();
        panelManual.add(txtActivos);

        panelManual.add(new JLabel("Pasivos"));
        txtPasivos = new JTextField();
        panelManual.add(txtPasivos);

        panelManual.add(new JLabel("Utilidad"));
        txtUtilidad = new JTextField();
        panelManual.add(txtUtilidad);

        panelManual.add(new JLabel("Ingresos"));
        txtIngresos = new JTextField();
        panelManual.add(txtIngresos);

        btnCalcularManual = new JButton("Calcular");
        panelManual.add(btnCalcularManual);

        btnLimpiar = new JButton("Limpiar");
        panelManual.add(btnLimpiar);

        add(panelManual, BorderLayout.WEST);

        // --- Panel Central (Resultados) ---
        JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 10));
        Font fuenteGrande = new Font("Arial", Font.BOLD, 32);

        lblCapitalTrabajo = new JLabel("0", SwingConstants.CENTER);
        lblCapitalTrabajo.setFont(fuenteGrande);
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBorder(BorderFactory.createTitledBorder("Capital de Trabajo"));
        p1.add(lblCapitalTrabajo);

        lblMargenUtilidad = new JLabel("0%", SwingConstants.CENTER);
        lblMargenUtilidad.setFont(fuenteGrande);
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBorder(BorderFactory.createTitledBorder("Margen de Utilidad"));
        p2.add(lblMargenUtilidad);

        lblNivelDeuda = new JLabel("0%", SwingConstants.CENTER);
        lblNivelDeuda.setFont(fuenteGrande);
        JPanel p3 = new JPanel(new BorderLayout());
        p3.setBorder(BorderFactory.createTitledBorder("Nivel de Deuda"));
        p3.add(lblNivelDeuda);

        txtHistorialAlertas = new JTextArea();
        txtHistorialAlertas.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtHistorialAlertas);
        JPanel p4 = new JPanel(new BorderLayout());
        p4.setBorder(BorderFactory.createTitledBorder("Alertas"));
        p4.add(scroll);

        panelCentral.add(p1);
        panelCentral.add(p2);
        panelCentral.add(p3);
        panelCentral.add(p4);

        add(panelCentral, BorderLayout.CENTER);

        // --- Panel Inferior (Progreso) ---
        JPanel panelInferior = new JPanel(new BorderLayout());
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Esperando proceso...");
        panelInferior.add(progressBar);

        add(panelInferior, BorderLayout.SOUTH);
        }
}