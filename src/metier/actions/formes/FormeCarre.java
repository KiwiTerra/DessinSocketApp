package metier.actions.formes;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Color;

public class FormeCarre extends Forme implements Serializable {

	private int longueur;
	private int hauteur;
	private boolean pleine;

	public FormeCarre(int x, int y, int stroke, Color couleur, int longueur, int hauteur, boolean pleine) {
		super(x, y, stroke, couleur);

		this.longueur = longueur;
		this.hauteur = hauteur;
		this.pleine = pleine;
	}

	public void dessiner(Graphics2D g) {
		// vérification des valeurs
		if (longueur < 0) {
			this.x += longueur;
			longueur *= -1;
		}

		if (hauteur < 0) {
			this.y += hauteur;
			hauteur *= -1;
		}

		// dessin
		g.setStroke(new BasicStroke(this.stroke));
		g.setColor(this.couleur);

		if (pleine) {
			g.fillRect(this.x, this.y, this.longueur, this.hauteur);
		} else {
			g.drawRect(this.x, this.y, this.longueur, this.hauteur);
		}
	}
	
}