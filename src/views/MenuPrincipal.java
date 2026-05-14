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

        // Despues de inicializar el desktop, abrimos la pantalla de inicio
        // Se usa invokeLater para asegurar que la ventana principal ya sea visible
        SwingUtilities.invokeLater(() -> {
            try {
                FrmInicio ventanaInicio = new FrmInicio();
                ventManager.openInternal(desktop, ventanaInicio);

                ventanaInicio.setMaximum(true);
            } catch (java.beans.PropertyVetoException e) {
                    // por posible error para debug
                    System.err.println("Error al maximizar la ventana de inicio: " + e.getMessage());
                } catch (Exception e) {
                    // por posible error para debug

                    System.err.println("Error al cargar la pantalla de inicio: " + e.getMessage());
                }
            });
            //         try {
            //     vInicio.setMaximum(true); 
            // } catch (java.beans.PropertyVetoException e) {
            //     System.err.println("Error al maximizar la ventana de inicio: " + e.getMessage());
            // }
        
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

                try {
                    vDash.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println("No se pudo maximizar la ventana.");
                }
                break;




            case "3. Optimizador de Punto de Equilibrio":
                FrmPuntoEquilibrio vEqui = new FrmPuntoEquilibrio();
                MdlEquilibrioOperativo mEqui = new MdlEquilibrioOperativo();
                new CtrlPuntoEquilibrio(vEqui, mEqui);
                ventManager.openInternal(desktop, vEqui);

                try {
                    vEqui.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println("No se pudo maximizar la ventana.");
                }
                break;

            case "4. Análisis de Sensibilidad (What-If)":
                FrmSensibilidad vSen = new FrmSensibilidad();
                MdlSensibilidad mSen = new MdlSensibilidad();
                new CtrlSensibilidad((vSen), mSen);
                ventManager.openInternal(desktop, vSen);
                

                try {
                    vSen.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println("No se pudo maximizar la ventana.");
                }
                break;

            case "5. Comparador de Alternativas":
                FrmComparador vComp = new FrmComparador();
                new CtrlComparador(vComp); 
                ventManager.openInternal(desktop, vComp);

                try {
                    vComp.setMaximum(true);
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println("No se pudo maximizar la ventana.");
                }
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