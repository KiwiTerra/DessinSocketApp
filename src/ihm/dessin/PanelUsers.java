package ihm.dessin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;
import metier.Joueur;

public class PanelUsers extends JPanel {
	
	private Controleur ctrl;
	private ArrayList<Joueur> joueurs;

	public PanelUsers(Controleur ctrl) {
		
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250, 0));
		this.setBackground(Color.LIGHT_GRAY);

		this.joueurs = this.ctrl.getJoueurs();

		JLabel lblNom = new JLabel("Philippe le gros fils de pute ");
		JLabel lblUtilisateurs = new JLabel("Utilisateurs");
		lblUtilisateurs.setHorizontalAlignment(JLabel.CENTER);
		this.add(lblUtilisateurs, BorderLayout.NORTH);


		JPanel panelBas = new JPanel();
		panelBas.setLayout(new GridLayout(10, 1, 5, 5));
		panelBas.setBackground(Color.LIGHT_GRAY);
		
		int cpt = 1;
		for (Joueur joueur : this.joueurs) {
			JPanel panelJoueur = new JPanel();
			panelJoueur.setLayout(new GridLayout(2,1));
			panelJoueur.setSize(25,20);
			panelJoueur.add(new JLabel("Utilisateur " + cpt + ": "));
			panelJoueur.add(new JLabel(joueur.getNom(), JLabel.CENTER));
			panelBas.add(panelJoueur);
			cpt++;
		}

		JPanel panelJoueur = new JPanel();
		panelJoueur.setLayout(new GridLayout(2,1));
		panelJoueur.setSize(25,20);
		panelJoueur.add(new JLabel("Utilisateur " + cpt + ": "));
		panelJoueur.add(new JLabel("philippe le pissenlit", JLabel.CENTER));
		panelBas.add(panelJoueur);		

		this.add(panelBas, BorderLayout.CENTER);
	}
}
