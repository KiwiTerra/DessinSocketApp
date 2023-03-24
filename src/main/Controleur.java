package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import ihm.dessin.PanelDessin;
import metier.Joueur;
import metier.actions.Action;
import metier.actions.formes.*;
import metier.reseaux.multicast.UDPMulticast;
import metier.reseaux.sockets.client.DessinClient;
import metier.reseaux.sockets.serveur.DessinServeur;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
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

	// GETTERS BOUTONS
	public int getOutilActif() { 
		return ((FrameApp)this.fenetreActive).getOutilActif();
	}

	public Color getCouleur() {
		return ((FrameApp)this.fenetreActive).getCouleur();
	}

	public boolean getRemplir() {
		return ((FrameApp)this.fenetreActive).getRemplir();
	}

	public int getEpaisseur() {
		return ((FrameApp)this.fenetreActive).getEpaisseur();
	}

	// DESSINS
	public void dessinerPinceau(int x, int y, ArrayList<Point> points) {
		this.dessiner(new Action(this.joueur, new FormePinceau(x, y, this.getEpaisseur(), this.getCouleur(), points)), true);
	}

	public void dessinerLigne(int x, int y, int xArrive, int yArrive) {
		this.dessiner(new Action(this.joueur, new FormeLigne(x, y, this.getEpaisseur(), this.getCouleur(), xArrive, yArrive)), true);
	}

	public void dessinerCarre(int x, int y, int longueur, int hauteur) {
		this.dessiner(new Action(this.joueur, new FormeCarre(x, y, this.getEpaisseur(), this.getCouleur(), longueur, hauteur, this.getRemplir())), true);
	}

	public void dessinerCercle(int x, int y, int longueur, int hauteur) {
		this.dessiner(new Action(this.joueur, new FormeCercle(x, y, this.getEpaisseur(), this.getCouleur(), longueur, hauteur, this.getRemplir())), true);
	}

	public void dessinerTexte(int x, int y, String texte) {
		this.dessiner(new Action(this.joueur, new FormeTexte(x, y, this.getEpaisseur(), this.getCouleur(), texte)), true);
	}
	
	public void dessiner(Action action, boolean envoyerAuReseau) {



		this.actions.add(action);
		((FrameApp)this.fenetreActive).majIHM();

		if(envoyerAuReseau) {
			UDPMulticast udpMulticast = this.serveur != null ? this.serveur.getMulticast() : this.client.getMulticast();
			try {
				udpMulticast.envoyerAction(action);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setCouleur(Color c) {
		((FrameApp)this.fenetreActive).setCouleur(c);
	}

	public boolean supprimerDernier() {
		Action a = this.joueur.supprimerDernier();
System.out.println(a);
		if (a == null) return false;

		// réseaux 
		this.actions.remove(a);
		((FrameApp)this.fenetreActive).majIHM();

		return true;
	}

	// PARTIES
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

		// Test
		this.actions.add(new Action(joueur, new FormeCercle(50, 50, 2, Color.RED, 100, 50, false)));
		this.actions.add(new Action(joueur, new FormeCarre(100, 100, 2, Color.GREEN, 50, 50, false)));
		this.actions.add(new Action(joueur, new FormeTexte(50, 200, 25, Color.BLACK, "ZIZI")));
		this.actions.add(new Action(joueur, new FormeLigne(200, 50, 4, Color.BLACK, 300, 200)));
		//this.actions.add(new Action(joueur, new FormeSeau(125, 125, 2, Color.PINK)));

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
