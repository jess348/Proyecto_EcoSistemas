package views;

import javax.swing.*;
import java.awt.*;

public class FrmAnalisisDialog extends JDialog {

    public FrmAnalisisDialog(Window owner, String tituloAnalisis, String contenidoHtml) {
        super(owner, "Consultoría C&S - Diagnóstico Financiero", ModalityType.APPLICATION_MODAL);
        setSize(600, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pnlHeader.setBackground(new Color(44, 62, 80)); // Azul oscuro C&S
        
        JLabel lblTitulo = new JLabel(tituloAnalisis.toUpperCase());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        JLabel lblTexto = new JLabel();
        lblTexto.setVerticalAlignment(SwingConstants.TOP);
        lblTexto.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        lblTexto.setText("<html><div style='width: 450px; font-family: \"Segoe UI\", sans-serif; font-size: 11px; color: #2C3E50;'>"
                        + contenidoHtml + "</div></html>");

        JScrollPane scroll = new JScrollPane(lblTexto);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);
        
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        pnlBotones.setBackground(new Color(245, 245, 245));

        JButton btnCerrar = new JButton("Entendido");
        // Estilo FlatLaf para botón redondeado y prominente
        btnCerrar.putClientProperty("JButton.buttonType", "roundRect");
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.addActionListener(e -> dispose());

        pnlBotones.add(btnCerrar);
        add(pnlBotones, BorderLayout.SOUTH);
    }
}