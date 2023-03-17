package ihm.demarrage;

import main.Controleur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PanelMenuPrincipal extends JPanel implements ActionListener {

    private final Controleur ctrl;

    private JButton btnTempAppli;

    private JButton btnCreerSalon;
    private JButton btnRejoindreSalon;
    private JButton btnQuitter;
    private JLabel lblBienvenue;
    private JButton btnModifier;

    public PanelMenuPrincipal(Controleur ctrl) {
        this.ctrl = ctrl;
        this.setLayout(null);

        Color couleurGrise = new Color(64, 67, 78);

        JLabel lblTitre = new JLabel("Dessin Socket App", JLabel.CENTER);
        lblTitre.setFont(lblTitre.getFont().deriveFont(50f));
        lblTitre.setForeground(couleurGrise);
        lblTitre.setBounds(0, 75, 750, 100);

        this.btnCreerSalon = new JButton("Créer un salon");
        this.btnCreerSalon.setBounds(250, 200, 250, 50);
        this.btnCreerSalon.setFont(this.btnCreerSalon.getFont().deriveFont(15f));
        this.btnCreerSalon.setForeground(Color.WHITE);
        this.btnCreerSalon.setBackground(couleurGrise);

        this.btnRejoindreSalon = new JButton("Rejoindre un salon");
        this.btnRejoindreSalon.setBounds(250, 275, 250, 50);
        this.btnRejoindreSalon.setFont(this.btnRejoindreSalon.getFont().deriveFont(15f));
        this.btnRejoindreSalon.setForeground(Color.WHITE);
        this.btnRejoindreSalon.setBackground(couleurGrise);

        this.btnQuitter = new JButton("Quitter");
        this.btnQuitter.setBounds(250, 350, 250, 50);
        this.btnQuitter.setFont(this.btnQuitter.getFont().deriveFont(15f));
        this.btnQuitter.setForeground(Color.WHITE);
        this.btnQuitter.setBackground(couleurGrise);

        this.lblBienvenue = new JLabel("Bienvenue " + this.ctrl.getJoueur().getNom());
        this.lblBienvenue.setFont(this.lblBienvenue.getFont().deriveFont(15f));
        this.lblBienvenue.setForeground(couleurGrise);
        this.lblBienvenue.setBounds(150, 135, 100 + (this.ctrl.getJoueur().getNom().length() * 9), 50);

        this.btnTempAppli = new JButton("Temp");
        this.btnTempAppli.setBounds(0, 0, 100, 50);
        this.btnTempAppli.setFont(this.btnTempAppli.getFont().deriveFont(15f));
        this.btnTempAppli.setForeground(Color.WHITE);
        this.btnTempAppli.setBackground(couleurGrise);

        try {
            System.out.println(new File("./ressources/img/edit.png").getAbsolutePath());
            this.btnModifier = new JButton(new ImageIcon(ImageIO.read(new File("./ressources/img/edit.png")).getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            this.btnModifier = new JButton("?");
            this.btnModifier.setForeground(Color.WHITE);
            e.printStackTrace();
        }
        this.btnModifier.setBounds(675, 415, 32, 32);
        this.btnModifier.setBackground(couleurGrise);
        this.btnModifier.setBorderPainted(false);
        this.btnModifier.setFocusPainted(false);

        this.add(lblTitre);
        this.add(this.lblBienvenue);
        this.add(this.btnCreerSalon);
        this.add(this.btnRejoindreSalon);
        this.add(this.btnQuitter);
        this.add(this.btnModifier);
        this.add(this.btnTempAppli);



        this.btnTempAppli.addActionListener(this);
        this.btnCreerSalon.addActionListener(this);
        this.btnRejoindreSalon.addActionListener(this);
        this.btnQuitter.addActionListener(this);
        this.btnModifier.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnTempAppli) {
            this.ctrl.afficherFenetreDessin();
        }

        if (e.getSource() == this.btnCreerSalon) {
            this.ctrl.afficherFenetreDessin();
        }

        if (e.getSource() == this.btnRejoindreSalon) {
            this.afficherRejoindrePartie();
        }

        if (e.getSource() == this.btnQuitter) {
            this.ctrl.quitter();
        }

        if (e.getSource() == this.btnModifier) {
            this.afficherModifierNom();
        }
    }

    public void afficherRejoindrePartie ()
    {
        String ip = JOptionPane.showInputDialog ( this, "Entrez l'adresse IP de l'hôte", "Rejoindre une partie", JOptionPane.QUESTION_MESSAGE );
        if ( ip != null )
        {
            //this.ctrl.rejoindrePartie ( ip );
        }
    }

    public void afficherModifierNom ()
    {
        String nom = JOptionPane.showInputDialog ( this, "Entrez votre nouveau nom", "Modifier le nom", JOptionPane.QUESTION_MESSAGE );
        if ( nom != null )
        {
            this.ctrl.modifierNomJoueur(nom);
            this.lblBienvenue.setText("Bienvenue " + this.ctrl.getJoueur().getNom());
            this.lblBienvenue.setBounds(150, 135, 100 + (this.ctrl.getJoueur().getNom().length() * 9), 50);
        }
    }
}
