package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import metier.Joueur;
import metier.sockets.DessinClient;
import metier.sockets.DessinClientServeur;
import metier.sockets.DessinServeur;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
