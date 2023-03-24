package metier.actions;

import java.io.Serializable;

import metier.Joueur;
import metier.actions.formes.Forme;

public class Action implements Serializable
{
	private String utilisateur;
	private Forme forme;

	public Action(Joueur utilisateur, Forme forme)
	{
		this.utilisateur = utilisateur.getNom();
		this.forme = forme;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public Forme getForme() {
		return forme;
	}
}