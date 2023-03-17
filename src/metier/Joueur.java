package metier;

import java.io.Serializable;

public class Joueur implements Serializable {

	private String nom;

	public Joueur(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
