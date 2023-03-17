package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import metier.Joueur;
import metier.actions.Action;
import metier.actions.formes.*;
import metier.sockets.DessinClient;
import metier.sockets.DessinServeur;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;


public class Controleur {

	private final Joueur joueur;
	private JFrame fenetreActive;

	private DessinServeur serveur;
	private DessinClient client;

	private ArrayList<Action> actions; 

	public Controleur() {
		this.joueur = new Joueur(this.chargerNomJoueur());
		this.fenetreActive = new FrameDemarrage(this);
		this.actions = new ArrayList<Action>();
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

		this.actions.add(new Action(joueur, new FormeCercle(50, 50, 2, Color.RED, 50, 50, true)));
		this.actions.add(new Action(joueur, new FormeCarre(100, 100, 2, Color.GREEN, 50, 50, true)));

		this.afficherFenetreDessin();
	}

	public void rejoindrePartie(String ip) {
		this.client = new DessinClient(this, ip);
		try {
			this.client.connexion();
			this.client.start();
			this.client.envoyerPseudo(this.joueur.getNom());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.fenetreActive, "Impossible de se connecter au serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	public void afficherFenetreDessin() {
		this.fenetreActive.dispose();
		this.fenetreActive = new FrameApp(this);
	}

	public String chargerNomJoueur() {
        File fichier = new File("nomJoueur.txt");
        String nomJoueur = "Joueur" + (int)(Math.random()*1000);
        if (!fichier.exists()) {
            try {
                fichier.createNewFile();
                FileWriter fw = new FileWriter(fichier);
                fw.write(nomJoueur);
                fw.close();
                return nomJoueur;
            } catch (IOException e) {e.printStackTrace();}
        }
        else {
            try {
                Scanner sc = new Scanner(fichier);
                nomJoueur = sc.nextLine();
                sc.close();
                return nomJoueur;
            } catch (IOException e) {e.printStackTrace();}
        }
        return nomJoueur;
    }

    public void modifierNomJoueur(String nomJoueur) {
        try {
            FileWriter fw = new FileWriter("nomJoueur.txt", false);
            fw.write(nomJoueur);
            fw.close();
            this.joueur.setNom(nomJoueur);

        } catch (Exception e) {e.printStackTrace();}
    }

	public boolean verifierPseudo(String pseudo) {
        return true;
    }

    public Joueur getJoueur() {
        return joueur;
    }

	public ArrayList<Action> getActions() {
		return this.actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}

    public void quitter() {
        this.fenetreActive.dispose();
    }

	public static void main(String[] args) {
		new Controleur();
	}
}
