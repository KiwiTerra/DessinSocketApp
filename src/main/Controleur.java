package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import metier.Joueur;

import javax.swing.*;

public class Controleur {

	private final Joueur joueur;
	private JFrame fenetreActive;

	public Controleur() {
		this.joueur = new Joueur(this.chargerNomJoueur());
		this.fenetreActive = new FrameDemarrage(this);
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
