package metier.actions;

import metier.Joueur;
import metier.actions.formes.Forme;

public class Action 
{
	private Joueur utilisateur;
	private Forme forme;

	public Action(Joueur utilisateur, Forme forme)
	{
		this.utilisateur = utilisateur;
		this.forme = forme;
	}

	public Joueur getUtilisateur() {
		return utilisateur;
	}

	public Forme getForme() {
		return forme;
	}
}