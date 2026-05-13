package views;

import controllers.CtrlDashboard;
import javax.swing.*;
import java.awt.*;

public class FrmDashboardFinanciero extends JInternalFrame {

        public JButton btnCargarBalance;
        public JButton btnCargarEstadoRes;
        public JButton btnGuardarTXT;

        public JLabel lblCapitalTrabajo;
        public JLabel lblMargenUtilidad;
        public JLabel lblNivelDeuda;

        public JTextArea txtHistorialAlertas;
        public JProgressBar progressBar;

        public FrmDashboardFinanciero() {
                // Se cambió super() para heredar de JInternalFrame y soportar
                // redimensionado/cierre
                super("Dashboard de Salud Financiera", true, true, true, true);
                setSize(1000, 700);

                // Se cambió EXIT_ON_CLOSE a DISPOSE_ON_CLOSE para no cerrar todo el programa
                setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

                initComponents();

                new CtrlDashboard(this);
        }

        private void initComponents() {
                setLayout(new BorderLayout(10, 10));

                JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

                btnCargarBalance = new JButton("Cargar Balance");
                btnCargarEstadoRes = new JButton("Cargar Estado Resultado");

                btnCargarBalance.setPreferredSize(new Dimension(180, 40));
                btnCargarEstadoRes.setPreferredSize(new Dimension(220, 40));

                panelSuperior.add(btnCargarBalance);
                panelSuperior.add(btnCargarEstadoRes);

                add(panelSuperior, BorderLayout.NORTH);

                JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 10));
                panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                Font fuenteGrande = new Font("Arial", Font.BOLD, 36);

                lblCapitalTrabajo = new JLabel("0", SwingConstants.CENTER);
                lblCapitalTrabajo.setFont(fuenteGrande);

                lblMargenUtilidad = new JLabel("0%", SwingConstants.CENTER);
                lblMargenUtilidad.setFont(fuenteGrande);

                lblNivelDeuda = new JLabel("0%", SwingConstants.CENTER);
                lblNivelDeuda.setFont(fuenteGrande);

                JPanel p1 = new JPanel(new BorderLayout());
                p1.setBorder(BorderFactory.createTitledBorder("Capital de Trabajo"));
                p1.add(lblCapitalTrabajo, BorderLayout.CENTER);

                JPanel p2 = new JPanel(new BorderLayout());
                p2.setBorder(BorderFactory.createTitledBorder("Margen de Utilidad"));
                p2.add(lblMargenUtilidad, BorderLayout.CENTER);

                JPanel p3 = new JPanel(new BorderLayout());
                p3.setBorder(BorderFactory.createTitledBorder("Nivel de Deuda"));
                p3.add(lblNivelDeuda, BorderLayout.CENTER);

                txtHistorialAlertas = new JTextArea();
                txtHistorialAlertas.setEditable(false);
                txtHistorialAlertas.setFont(new Font("Consolas", Font.PLAIN, 14));
                JScrollPane scroll = new JScrollPane(txtHistorialAlertas);

                JPanel p4 = new JPanel(new BorderLayout());
                p4.setBorder(BorderFactory.createTitledBorder("Historial de Alertas"));
                p4.add(scroll, BorderLayout.CENTER);

                panelCentral.add(p1);
                panelCentral.add(p2);
                panelCentral.add(p3);
                panelCentral.add(p4);

                add(panelCentral, BorderLayout.CENTER);

                JPanel panelInferior = new JPanel(new BorderLayout());

                btnGuardarTXT = new JButton("Guardar Informe TXT");
                btnGuardarTXT.setPreferredSize(new Dimension(200, 40));

                progressBar = new JProgressBar();
                progressBar.setIndeterminate(false);
                progressBar.setStringPainted(true);
                progressBar.setString("Esperando procesamiento...");
                progressBar.setPreferredSize(new Dimension(100, 30));

                panelInferior.add(btnGuardarTXT, BorderLayout.NORTH);
                panelInferior.add(progressBar, BorderLayout.SOUTH);

                add(panelInferior, BorderLayout.SOUTH);
        }
}