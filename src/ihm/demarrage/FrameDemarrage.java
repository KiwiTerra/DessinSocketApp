package ihm.demarrage;

import main.Controleur;

import javax.swing.*;

public class FrameDemarrage extends JFrame {

    private final JPanel panel;
    private final Controleur ctrl;

    public FrameDemarrage(Controleur ctrl) {
        super("Écran de démarrage");
        this.setSize(750, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.ctrl = ctrl;
        this.panel = new PanelMenuPrincipal(ctrl);

        this.setContentPane(panel);
        this.setVisible(true);
    }

}
