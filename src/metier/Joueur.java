package metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import metier.actions.Action;

public class Joueur implements Serializable {

	private String nom;
	private transient LinkedList<Action> actions;

	public Joueur(String nom) {
		this.nom = nom;
		this.actions = new LinkedList<Action>();
	}

	public void ajouterAction(Action a) {
		this.actions.add(a);
	}

	public Action supprimerDernier() {
		if (actions.isEmpty()) 
			return null;

		return actions.removeLast();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
