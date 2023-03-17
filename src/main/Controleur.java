package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import metier.Joueur;
import metier.sockets.DessinClient;
import metier.sockets.DessinClientServeur;
import metier.sockets.DessinServeur;

import javax.swing.*;

public class Controleur {

	private final Joueur joueur;
	private JFrame fenetreActive;

	private DessinServeur serveur;
	private DessinClient client;


	public Controleur() {
		this.joueur = new Joueur(this.chargerNomJoueur());
		this.fenetreActive = new FrameDemarrage(this);
	}

	public void creerPartie() {
		this.serveur = new DessinServeur(this);
		try {
			this.serveur.demarrerServeur();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.fenetreActive, "Impossible de démarrer le serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.serveur.start();
		this.afficherFenetreDessin();
	}

	public void rejoindrePartie(String ip) {
		this.client = new DessinClient(this, ip);
		try {
			this.client.connexion();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.fenetreActive, "Impossible de se connecter au serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.client.start();
		this.afficherFenetreDessin();
	}

	public void afficherFenetreDessin() {
		this.fenetreActive.dispose();
		this.fenetreActive = new FrameApp(this);
	}

	public String chargerNomJoueur() {
		return "Joueur" + (int) (Math.random() * 1000);
	}

    public Joueur getJoueur() {
        return joueur;
    }

    public void quitter() {
        this.fenetreActive.dispose();
    }

	public static void main(String[] args) {
		new Controleur();
	}
}
