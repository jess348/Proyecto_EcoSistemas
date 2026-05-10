package com_uni_sistemas_economica;

// import java.awt.event.WindowAdapter;
// import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
// import javax.swing.JDialog;
// import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * Clase VentManager que gestiona la apertura de ventanas en la aplicación.
 * Su propósito es asegurar que solo se pueda abrir una ventana secundaria a la vez,
 * mostrando un mensaje de error si se intenta abrir otra mientras hay una activa.
 * Funciona manteniendo una referencia a la ventana activa y limpiándola cuando se cierra.
 */
public class VentManager {
    // private JFrame activeFrame;

    
    // Verifica si se puede abrir una nueva ventana.
    //  Retorna true si no hay ventana activa o si la ventana activa ya no es visible (cerrada).
    //  @return true si se puede abrir una nueva ventana, false en caso contrario.
    // public boolean canOpen() {
    //     return activeFrame == null || !activeFrame.isDisplayable();
    // }

    
    // Intenta abrir una nueva ventana secundaria.
    // Si ya hay una ventana abierta, muestra un JOptionPane con un mensaje de error.
    // Si no, asigna la nueva ventana como activa, añade un listener para limpiarla al cerrar,
    // configura su posición relativa al owner y la hace visible.
    // @param owner La ventana principal (padre) para centrar la nueva ventana y mostrar el error.
    // @param frame La nueva ventana a abrir.
    //se corrigio el error de que se podia abrir varias ventanas a la vez, ahora solo se puede abrir una ventana a la vez, si se intenta abrir otra ventana mientras hay una activa, se muestra un mensaje de error ademas se muestra por encima del subFrame ya que no se podia por el AlwaysOnTop del subframe.
        public void openInternal(JDesktopPane desktop, JInternalFrame frame) {

            JInternalFrame[] frames = desktop.getAllFrames();

            if (frames.length > 0) {
                JOptionPane.showMessageDialog(
                    desktop,
                    "Solo puede abrir una ventana a la vez.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                try {
                    frames[0].setSelected(true);
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
                
            }

        // for (JInternalFrame fr : desktop.getAllFrames()){
        //     if (fr.getClass().isInstance(frame)){
        //         try{
        //             fr.setSelected(true);
        //             fr.setIcon(false);
        //             JOptionPane.showMessageDialog(desktop, "Ya hay una ventana de este tipo abierta.", "Error", JOptionPane.ERROR_MESSAGE);

        //         }catch(PropertyVetoException e){
        //             e.printStackTrace();
        //         }
        //         return;
        //     }
        // }
        desktop.add(frame);
        frame.setVisible(true);
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
