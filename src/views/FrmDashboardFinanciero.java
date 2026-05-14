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
                JPanel pnlPrincipal = new JPanel(new BorderLayout(15, 15));
                pnlPrincipal.setBackground(new Color(240, 244, 248));
                pnlPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
                panelSuperior.setBackground(Color.WHITE);
                panelSuperior.setBorder(crearBordeTarjeta());

                btnCargarBalance = crearBotonConIcono(" Cargar Balance", "/images/nuevo-documentoIcono.png");
                btnCargarEstadoRes = crearBotonConIcono(" Cargar Estado Res.", "/images/nuevo-documentoIcono.png");
                btnGuardarTXT = crearBotonConIcono(" Guardar Informe", "/images/confirmarIcono.png");

                panelSuperior.add(btnCargarBalance);
                panelSuperior.add(btnCargarEstadoRes);
                panelSuperior.add(btnGuardarTXT);
                pnlPrincipal.add(panelSuperior, BorderLayout.NORTH);

                JPanel panelManual = new JPanel(new GridLayout(6, 2, 10, 10));
                panelManual.setBackground(Color.WHITE);
                panelManual.setBorder(BorderFactory.createCompoundBorder(
                                crearBordeTarjeta(),
                                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Ingreso Manual",
                                                javax.swing.border.TitledBorder.LEFT,
                                                javax.swing.border.TitledBorder.TOP,
                                                new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80))));

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
                pnlPrincipal.add(panelManual, BorderLayout.WEST);

                JPanel panelCentral = new JPanel(new GridLayout(2, 2, 15, 15));
                panelCentral.setBackground(new Color(240, 244, 248));

                lblCapitalTrabajo = crearLabelGrande(new Color(41, 128, 185));
                lblMargenUtilidad = crearLabelGrande(new Color(39, 174, 96));
                lblNivelDeuda = crearLabelGrande(new Color(211, 84, 0));

                panelCentral.add(
                                crearPanelIndicador("Capital de Trabajo", lblCapitalTrabajo, new Color(235, 245, 251)));
                panelCentral.add(
                                crearPanelIndicador("Margen de Utilidad", lblMargenUtilidad, new Color(233, 247, 239)));
                panelCentral.add(crearPanelIndicador("Nivel de Deuda", lblNivelDeuda, new Color(253, 235, 208)));

                JPanel pnlAlertas = new JPanel(new BorderLayout(0, 10));
                pnlAlertas.setBackground(Color.WHITE);
                pnlAlertas.setBorder(BorderFactory.createCompoundBorder(
                crearBordeTarjeta(),BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                "Alertas y Diagnóstico",javax.swing.border.TitledBorder.LEFT,javax.swing.border.
                TitledBorder.TOP,new Font("Segoe UI", Font.BOLD, 14), new Color(44, 62, 80))));

                txtHistorialAlertas = new JTextArea();
                txtHistorialAlertas.setEditable(false);
                txtHistorialAlertas.setFont(new Font("Segoe UI", Font.PLAIN, 13));

                JScrollPane scrollAlertas = new JScrollPane(txtHistorialAlertas);
                scrollAlertas.setBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)));
                pnlAlertas.add(scrollAlertas, BorderLayout.CENTER);

                btnVerAnalisis = new JButton(" Ver Análisis Detallado 📊");
                btnVerAnalisis.putClientProperty("JButton.buttonType", "roundRect");
                btnVerAnalisis.setBackground(new Color(41, 128, 185));
                btnVerAnalisis.setForeground(Color.WHITE);
                btnVerAnalisis.setFont(new Font("Segoe UI", Font.BOLD, 14));
                pnlAlertas.add(btnVerAnalisis, BorderLayout.SOUTH);

                panelCentral.add(pnlAlertas);
                pnlPrincipal.add(panelCentral, BorderLayout.CENTER);

                progressBar = new JProgressBar();
                progressBar.setStringPainted(true);
                pnlPrincipal.add(progressBar, BorderLayout.SOUTH);

                setContentPane(pnlPrincipal);
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
                t.putClientProperty("JTextField.showClearButton", true);
                t.putClientProperty("JComponent.outline", "focus");
                return t;
        }

        private JLabel crearLabelGrande(Color colorTexto) {
                JLabel l = new JLabel("0", SwingConstants.CENTER);
                l.setFont(new Font("Arial", Font.BOLD, 32));
                l.setForeground(colorTexto);
                return l;
        }

        private JPanel crearPanelIndicador(String titulo, JLabel label, Color colorFondo) {
                JPanel p = new JPanel(new BorderLayout());
                p.setBackground(colorFondo);
                p.setBorder(BorderFactory.createCompoundBorder(
                crearBordeTarjeta(),BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
                lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblTitulo.setForeground(new Color(44, 62, 80));
                p.add(lblTitulo, BorderLayout.NORTH);
                p.add(label, BorderLayout.CENTER);
                return p;
        }

        private javax.swing.border.Border crearBordeTarjeta() {
                javax.swing.border.Border bordeLinea = BorderFactory.createLineBorder(new Color(225, 230, 235), 1, true);
                javax.swing.border.Border margenInterno = BorderFactory.createEmptyBorder(10, 10, 10, 10);
                return BorderFactory.createCompoundBorder(bordeLinea, margenInterno);
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