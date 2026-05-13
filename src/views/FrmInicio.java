package views;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class FrmInicio extends JInternalFrame {

    public FrmInicio(){
        super("Inicio - Capital & Systems", true,true, true,true);
        setSize(920,600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel lblContenidoWeb = new JLabel();
        lblContenidoWeb.setVerticalAlignment(SwingConstants.TOP);
        lblContenidoWeb.setHorizontalAlignment(SwingConstants.CENTER);

        String htmlCorporativo = "<html>"
                + "<div style='font-family: \"Segoe UI\", Arial, sans-serif; padding: 30px; width: 700px;'>"
                
                // Cabecera
                + "<h1 style='color: #2C3E50; font-size: 32px; text-align: center; margin-bottom: 5px;'>"
                + "C&amp;S (Capital &amp; Systems)</h1>"
                + "<h3 style='color: #2980B9; font-size: 16px; text-align: center; margin-top: 0px; border-bottom: 2px solid #ECF0F1; padding-bottom: 15px;'>"
                + "Desarrollo de Software para Análisis de Inversiones e Ingeniería Económica</h3>"
                
                // Quiénes somos
                + "<h2 style='color: #34495E; font-size: 18px; margin-top: 25px;'>🏢 ¿Quiénes Somos?</h2>"
                + "<p style='color: #555555; font-size: 13px; text-align: justify; line-height: 1.6;'>"
                + "Somos una firma de soluciones tecnológicas con un profundo ADN en la ingeniería de sistemas y la economía corporativa. "
                + "En <b>C&amp;S</b> no nos limitamos a escribir código; diseñamos arquitecturas de decisión. Traducimos la complejidad de la "
                + "ingeniería económica y el análisis financiero en software ágil e intuitivo, brindando a las empresas la certeza "
                + "matemática necesaria para invertir, optimizar sus recursos y crecer sostenidamente.</p>"
                
                // A qué nos dedicamos
                + "<h2 style='color: #34495E; font-size: 18px; margin-top: 25px;'>⚙️ Nuestras Soluciones</h2>"
                + "<ul style='color: #555555; font-size: 13px; line-height: 1.6;'>"
                + "<li><b>Evaluación de Proyectos:</b> Automatización de indicadores críticos (VAN y TIR) para comparar alternativas y proteger el capital.</li>"
                + "<li><b>Salud Financiera Empresarial:</b> Dashboards interactivos para monitorear el capital de trabajo, márgenes de utilidad y niveles de deuda.</li>"
                + "<li><b>Inteligencia Operativa:</b> Herramientas de cálculo para puntos de equilibrio y análisis de sensibilidad bajo múltiples escenarios.</li>"
                + "</ul>"
                
                // Nuestro trasfondo
                + "<h2 style='color: #34495E; font-size: 18px; margin-top: 25px;'>📖 Nuestro Trasfondo</h2>"
                + "<p style='color: #555555; font-size: 13px; text-align: justify; line-height: 1.6;'>"
                + "C&amp;S nace de una observación crítica en el ecosistema de las medianas y pequeñas empresas: la falta de herramientas "
                + "accesibles para evaluar la rentabilidad real de sus negocios. Evolucionamos para digitalizar esas decisiones, dejando atrás "
                + "las hojas de cálculo desconectadas y garantizando que cada análisis en nuestro sistema esté respaldado por el rigor de la ingeniería."
                + "</p>"
                
                + "</div>"
                + "</html>";

                lblContenidoWeb.setText(htmlCorporativo);

                JScrollPane scroll= new JScrollPane(lblContenidoWeb);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.setBackground(Color.WHITE);
                scroll.getViewport().setBackground(Color.WHITE);

                add(scroll, BorderLayout.CENTER);






    }

}
