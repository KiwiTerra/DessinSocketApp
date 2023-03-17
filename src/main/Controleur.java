package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import metier.Joueur;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
