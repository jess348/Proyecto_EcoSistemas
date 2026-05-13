package views;

import controllers.CtrlDashboard;
import javax.swing.*;
import java.awt.*;

public class FrmDashboardFinanciero extends JInternalFrame {

        public JButton btnCargarBalance, btnCargarEstadoRes, btnGuardarTXT, btnCalcularManual, btnLimpiar,
                        btnVerAnalisis;
        public JLabel lblCapitalTrabajo, lblMargenUtilidad, lblNivelDeuda;
        public JTextArea txtHistorialAlertas;
        public JProgressBar progressBar;
        public JTextField txtActivos, txtPasivos, txtUtilidad, txtIngresos;

        public FrmDashboardFinanciero() {
                super("Dashboard de Salud Financiera", true, true, true, true);
                setSize(930, 600);
                setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
                initComponents();
                new CtrlDashboard(this);
        }

        private void initComponents() {
                setLayout(new BorderLayout(10, 10));

                JPanel panelSuperior = new JPanel(new FlowLayout());

                btnCargarBalance = crearBotonConIcono(" Cargar Balance", "/images/nuevo-documentoIcono.png");
                btnCargarEstadoRes = crearBotonConIcono(" Cargar Estado Res.", "/images/nuevo-documentoIcono.png");
                btnGuardarTXT = crearBotonConIcono(" Guardar Informe", "/images/confirmarIcono.png");

                panelSuperior.add(btnCargarBalance);
                panelSuperior.add(btnCargarEstadoRes);
                panelSuperior.add(btnGuardarTXT);
                add(panelSuperior, BorderLayout.NORTH);

                JPanel panelManual = new JPanel(new GridLayout(6, 2, 10, 10));
                panelManual.setBorder(BorderFactory.createTitledBorder("Ingreso Manual"));

                txtActivos = crearTextField("Activos");
                txtPasivos = crearTextField("Pasivos");
                txtUtilidad = crearTextField("Utilidad");
                txtIngresos = crearTextField("Ingresos");

                panelManual.add(new JLabel("Activos:"));
                panelManual.add(txtActivos);
                panelManual.add(new JLabel("Pasivos:"));
                panelManual.add(txtPasivos);
                panelManual.add(new JLabel("Utilidad:"));
                panelManual.add(txtUtilidad);
                panelManual.add(new JLabel("Ingresos:"));
                panelManual.add(txtIngresos);

                btnCalcularManual = crearBotonConIcono(" Calcular", "/images/confirmarIcono.png");
                btnCalcularManual.setBackground(new Color(39, 174, 96));
                btnCalcularManual.setForeground(Color.WHITE);

                btnLimpiar = crearBotonConIcono(" Limpiar", "/images/borrarIcono.png");

                panelManual.add(btnCalcularManual);
                panelManual.add(btnLimpiar);
                add(panelManual, BorderLayout.WEST);

                JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 10));
                lblCapitalTrabajo = crearLabelGrande();
                lblMargenUtilidad = crearLabelGrande();
                lblNivelDeuda = crearLabelGrande();

                panelCentral.add(crearPanelIndicador("Capital de Trabajo", lblCapitalTrabajo));
                panelCentral.add(crearPanelIndicador("Margen de Utilidad", lblMargenUtilidad));
                panelCentral.add(crearPanelIndicador("Nivel de Deuda", lblNivelDeuda));

                btnVerAnalisis = new JButton(" Ver Análisis Detallado 📊");
                btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
                btnVerAnalisis.setBackground(new Color(41, 128, 185)); // Azul corporativo
                btnVerAnalisis.setForeground(Color.WHITE);
                btnVerAnalisis.setFont(new Font("Segoe UI", Font.BOLD, 14));

                JPanel pnlAlertas = new JPanel(new BorderLayout());
                pnlAlertas.setBorder(BorderFactory.createTitledBorder("Alertas y Diagnóstico"));
                txtHistorialAlertas = new JTextArea();
                txtHistorialAlertas.setEditable(false);
                pnlAlertas.add(new JScrollPane(txtHistorialAlertas), BorderLayout.CENTER);
                pnlAlertas.add(btnVerAnalisis, BorderLayout.SOUTH);

                panelCentral.add(pnlAlertas);
                add(panelCentral, BorderLayout.CENTER);

                progressBar = new JProgressBar();
                progressBar.setStringPainted(true);
                add(progressBar, BorderLayout.SOUTH);
        }

        private JButton crearBotonConIcono(String texto, String ruta) {
                JButton b = new JButton(texto);
                b.setIcon(redimensionarIcono(ruta, 20, 20));
                b.putClientProperty("JButton.buttonType", "roundRect");
                return b;
        }

        private JTextField crearTextField(String placeholder) {
                JTextField t = new JTextField();
                t.putClientProperty("JTextField.placeholderText", placeholder);
                return t;
        }

        private JLabel crearLabelGrande() {
                JLabel l = new JLabel("0", SwingConstants.CENTER);
                l.setFont(new Font("Arial", Font.BOLD, 32));
                return l;
        }

        private JPanel crearPanelIndicador(String titulo, JLabel label) {
                JPanel p = new JPanel(new BorderLayout());
                p.setBorder(BorderFactory.createTitledBorder(titulo));
                p.add(label);
                return p;
        }

        private ImageIcon redimensionarIcono(String ruta, int ancho, int alto) {
                try {
                        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
                        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto,
                                        Image.SCALE_SMOOTH);
                        return new ImageIcon(imagenEscalada);
                } catch (Exception e) {
                        return null;
                }
        }
}