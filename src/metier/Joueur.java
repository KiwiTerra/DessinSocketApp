package metier;

import java.io.Serializable;
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
		if(this.actions == null)
			this.actions = new LinkedList<Action>();

		this.actions.add(a);
	}

	public Action supprimerDernier() {
		if (this.actions == null || this.actions.isEmpty()) 
			return null;

		return this.actions.removeLast();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Joueur) obj).getNom().equals(this.nom);
	}
}
