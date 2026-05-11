package com_uni_sistemas_economica;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
// import com_uni_sistemas_economica.V_Amortizacion;
// import com_uni_sistemas_economica.V_EvaluacionProyecto;
import java.awt.Image;

public class MenuPrincipal extends JFrame {
    public JDesktopPane desktop;
    // Creamos la instancia del gestor de ventanas
    private VentManager ventManager = new VentManager();

    public MenuPrincipal() {
        setTitle("EcoSystems Pro: Analizador de Inversiones - Ingeniería Económica");
        setSize(930, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop = new JDesktopPane(){
            private Image img;

            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                if (img == null) {
                    try {
                        img = new ImageIcon(getClass().getResource("/images/imagenEmpresa4.png")).getImage();

                    } catch (Exception e) {
                        System.err.println("Imagen no encontrada en la ruta especifica. :(");
                    }
                }
                if (img != null) {
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // desktop = new JDesktopPane();
        setContentPane(desktop);

        setJMenuBar(createMenuBar());
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        String[] menuNom = {
                "1. Evaluación VAN y TIR",
                "Tabla de Amortización",
                "3. Optimizador de Punto de Equilibrio",
                "Depreciación de Activos",
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
            // 1. Instanciar la vista y el modelo
            FrmEvaluacionProyecto vEval = new FrmEvaluacionProyecto();
            CalculadoraFinanciera modelo = new CalculadoraFinanciera();

            // 2. VINCULAR EL CONTROLADOR (Paso crítico)
            // Esto ejecuta: vEval.btnCalcular.addActionListener(...)
            new CtrlEconomico(vEval, modelo);

            // 3. Pasar la vista al gestor de ventanas
            ventManager.openInternal(desktop, vEval);
            break;

            case "3. Optimizador de Punto de Equilibrio":
                FrmPuntoEquilibrio vEqui = new FrmPuntoEquilibrio();
                MdlEquilibrioOperativo mEqui = new MdlEquilibrioOperativo();
                new CtrlPuntoEquilibrio(vEqui, mEqui);
                ventManager.openInternal(desktop, vEqui);
                break;


            // case "2. Tabla de Amortización":
            //     ventManager.openInternal(desktop, new V_Amortizacion());
            //     break;
            
            // case "3. Productividad Laboral":
            //     ventManager.openInternal(desktop, new V_ProductividadLaboral());
            //     break;
            
            // CORREGIDO:
            // case "4. Depreciación de Activos": 
            //     ventManager.openInternal(desktop, new V_Depreciacion());
            //     break;

            // CORREGIDO: Se agregó la 'a' faltante en Alternativas
                case "5. Comparador de Alternativas":
                FrmComparador vComp = new FrmComparador();
                new CtrlComparador(vComp); // Conectamos el control a vComp
                ventManager.openInternal(desktop, vComp); // ¡Abrimos vComp en el escritorio!
                break;

            default:
                //ventanas default o prueba
                JInternalFrame framePrueba = new JInternalFrame(title, true, true, true, true);
                framePrueba.setSize(400, 300);
                framePrueba.add(new JLabel("Módulo en construcción: " + title, SwingConstants.CENTER), BorderLayout.CENTER);
                
                ventManager.openInternal(desktop, framePrueba);
                break;
        }
    }
}