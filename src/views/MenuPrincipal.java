package views;

import javax.swing.*;
import controllers.CtrlComparador;
// import controllers.CtrlDashboard;
import controllers.CtrlEconomico;
import controllers.CtrlPuntoEquilibrio;
import controllers.CtrlSensibilidad;
import models.CalculadoraFinanciera;
import models.MdlEquilibrioOperativo;
import models.MdlSensibilidad;
import utils.VentManager;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

public class MenuPrincipal extends JFrame {
    public JDesktopPane desktop;
    private VentManager ventManager = new VentManager();

    public MenuPrincipal() {
        // Título actualizado con el rebranding C&S
        setTitle("C&S (Capital & Systems) - Analizador de Inversiones - Ingeniería Económica");
        setSize(930, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. PRIMERO inicializamos el desktop para que no sea null
        desktop = new JDesktopPane(){
            private Image img;

            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (img == null) {
                    try {
                        img = new ImageIcon(getClass().getResource("/images/imagenEmpresa5.png")).getImage();
                    } catch (Exception e) {
                        System.err.println("Imagen no encontrada en la ruta especifica. :(");
                    }
                }
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        // 2. DESPUÉS de inicializar el desktop, abrimos la pantalla de inicio
        // Usamos invokeLater para asegurar que la ventana principal ya sea visible
        SwingUtilities.invokeLater(() -> {
            try {
                FrmInicio ventanaInicio = new FrmInicio();
                ventManager.openInternal(desktop, ventanaInicio);
            } catch (Exception e) {
                System.err.println("Error al cargar la pantalla de inicio: " + e.getMessage());
            }
        });
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        String[] menuNom = {
                "1. Evaluación VAN y TIR",
                "2. Salud Financiera",
                "3. Optimizador de Punto de Equilibrio",
                "4. Análisis de Sensibilidad (What-If)",
                "5. Comparador de Alternativas"
        };

        for (String name : menuNom) {
            JMenu menu = new JMenu(name);
            JMenuItem item = new JMenuItem("Abrir " + name);
            item.addActionListener(e -> openSubFrame(name)); 
            
            menu.add(item);
            menuBar.add(menu);
        }

        return menuBar;
    }

    private void openSubFrame(String title) {
        switch (title) {
            case "1. Evaluación VAN y TIR":
                FrmEvaluacionProyecto vEval = new FrmEvaluacionProyecto();
                CalculadoraFinanciera modelo = new CalculadoraFinanciera();
                new CtrlEconomico(vEval, modelo);
                ventManager.openInternal(desktop, vEval);
                break;

            case "2. Salud Financiera":
                FrmDashboardFinanciero vDash = new FrmDashboardFinanciero();
                ventManager.openInternal(desktop, vDash);
                break;



            case "3. Optimizador de Punto de Equilibrio":
                FrmPuntoEquilibrio vEqui = new FrmPuntoEquilibrio();
                MdlEquilibrioOperativo mEqui = new MdlEquilibrioOperativo();
                new CtrlPuntoEquilibrio(vEqui, mEqui);
                ventManager.openInternal(desktop, vEqui);
                break;

            case "4. Análisis de Sensibilidad (What-If)":
                FrmSensibilidad vSen = new FrmSensibilidad();
                MdlSensibilidad mSen = new MdlSensibilidad();
                new CtrlSensibilidad((vSen), mSen);
                ventManager.openInternal(desktop, vSen);

            case "5. Comparador de Alternativas":
                FrmComparador vComp = new FrmComparador();
                new CtrlComparador(vComp); 
                ventManager.openInternal(desktop, vComp); 
                break;

            default:
                JInternalFrame framePrueba = new JInternalFrame(title, true, true, true, true);
                framePrueba.setSize(400, 300);
                framePrueba.add(new JLabel("Módulo en construcción: " + title, SwingConstants.CENTER), BorderLayout.CENTER);
                ventManager.openInternal(desktop, framePrueba);
                break;
        }
    }
}