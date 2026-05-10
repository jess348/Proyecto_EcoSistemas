package com_uni_sistemas_economica;

import javax.swing.*;
import java.awt.BorderLayout;
// import com_uni_sistemas_economica.V_Amortizacion;
// import com_uni_sistemas_economica.V_EvaluacionProyecto;

public class MenuPrincipal extends JFrame {
    public JDesktopPane desktop;
    // Creamos la instancia del gestor de ventanas
    private VentManager ventManager = new VentManager();

    public MenuPrincipal() {
        setTitle("EcoSystems Pro: Analizador de Inversiones - Ingeniería Económica");
        setSize(930, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktop = new JDesktopPane();
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
            V_EvaluacionProyecto vEval = new V_EvaluacionProyecto();
            CalculadoraFinanciera modelo = new CalculadoraFinanciera();

            // 2. VINCULAR EL CONTROLADOR (Paso crítico)
            // Esto ejecuta: vEval.btnCalcular.addActionListener(...)
            new ControladorEconomico(vEval, modelo);

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
            
            // CORREGIDO: Se agregó la tilde en la 'ó'
            // case "4. Depreciación de Activos": 
            //     ventManager.openInternal(desktop, new V_Depreciacion());
            //     break;

            // CORREGIDO: Se agregó la 'a' faltante en Alternativas
            case "5. Comparador de Alternativas":
                ventManager.openInternal(desktop, new V_Comparador());
                break;

            default:
                // Ventana genérica para los módulos que aún no tienen clase propia
                JInternalFrame framePrueba = new JInternalFrame(title, true, true, true, true);
                framePrueba.setSize(400, 300);
                framePrueba.add(new JLabel("Módulo en construcción: " + title, SwingConstants.CENTER), BorderLayout.CENTER);
                
                // También pasamos las ventanas de prueba por el gestor
                ventManager.openInternal(desktop, framePrueba);
                break;
        }
    }
}