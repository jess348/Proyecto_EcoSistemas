package main;

import com.formdev.flatlaf.FlatLightLaf; // Importar el tema claro

import views.MenuPrincipal;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Aplicamos el Look and Feel moderno
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar el tema FlatLaf");
        }

        // Luego inicias tu aplicación normalmente
        new MenuPrincipal().setVisible(true);
    }
}