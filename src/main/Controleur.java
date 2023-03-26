package main;

import ihm.demarrage.FrameDemarrage;
import ihm.dessin.FrameApp;
import ihm.dessin.PanelDessin;
import metier.Joueur;
import metier.actions.Action;
import metier.actions.formes.*;
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
import java.util.stream.Collectors;

import javax.swing.*;


public class Controleur {

	private final Joueur joueur;
	private JFrame fenetreActive;

	private DessinServeur serveur;
	private DessinClient client;

	private ArrayList<Action> actions;
	private ArrayList<Joueur> joueurs;

	public Controleur() {
		this.joueur = new Joueur(this.chargerNomJoueur());
		this.menuPrincipal();
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
		System.out.println("Noms joueurs:"  + this.joueurs.stream().map(Joueur::getNom).collect(Collectors.joining(",")));
		this.actions.add(action);
		for(Joueur j : this.joueurs) {
			if(j.getNom().equals(action.getUtilisateur())) {
				j.ajouterAction(action);
				break;
			}
		}
		
		((FrameApp)this.fenetreActive).majIHM();

		if(envoyerAuReseau) {
			try {
				if(this.serveur != null) {
					this.serveur.envoyerAction(action);
				} else if(this.client != null) {
					this.client.envoyerAction(action);
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
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

		this.supprimerAction(a, true);

		return true;
	}

	public void supprimerAction(Action action, boolean envoyerAuReseau) {
		this.actions.remove(action);
		((FrameApp)this.fenetreActive).majIHM();

		if(envoyerAuReseau) {
			try {
				if(this.serveur != null) {
					this.serveur.supprimerAction(action);
				} else if(this.client != null) {
					this.client.supprimerAction(action);
				}
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// EXPORTER
	public void exporterSous(String format) {System.out.println(format);
		((FrameApp)this.fenetreActive).exporterSous(format);
	}

	// PARTIES
	public void creerPartie() {
		this.serveur = new DessinServeur(this);
		try {
			this.serveur.demarrerServeur();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.fenetreActive, "Impossible de démarrer le serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
			this.serveur = null;
			return;
		}

		this.serveur.start();
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
			this.client.deconnexion();
			this.client = null;
			return;
		}
	}

	public void afficherFenetreDessin() {

		this.fenetreActive.dispose();
		this.fenetreActive = new FrameApp(this);
	}

	public void menuPrincipal() {
		if(this.fenetreActive != null)
			this.fenetreActive.dispose();

		if(this.serveur != null) {
			this.serveur.arreterServeur();
			this.serveur = null;
		}

		if(this.client != null) {
			this.client.deconnexion();
			this.client = null;
		}
		
		this.fenetreActive = new FrameDemarrage(this);
		this.actions = new ArrayList<Action>();
		this.joueurs = new ArrayList<Joueur>();
		this.joueurs.add(this.joueur);
	}


	public Joueur ajouterJoueur(String pseudo) {
		Joueur j = new Joueur(pseudo);
		return this.ajouterJoueur(j);
    }

	public Joueur ajouterJoueur(Joueur j1) {
		for(Joueur j2 : this.joueurs) {
			if(j1.equals(j2)) {
				return null;
			}
		}

		this.joueurs.add(j1);
		if(this.fenetreActive instanceof FrameApp)
			((FrameApp)this.fenetreActive).majIHM();
		return j1;
	}

	public void retirerJoueur(String pseudo) {
		Joueur joueurTrouve = null;
		for (Joueur j : this.joueurs) {
			if (j.getNom().equals(pseudo)) {
				joueurTrouve = j;
				break;
			}
		}

		if (joueurTrouve != null) {
			this.joueurs.remove(joueurTrouve);
			if(this.fenetreActive instanceof FrameApp)
				((FrameApp)this.fenetreActive).majIHM();
		}
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
		for (Joueur j : this.joueurs) {
			if (j.getNom().equals(pseudo)) {
				return false;
			}
		}
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

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public static void main(String[] args) {
		new Controleur();
	}
}
