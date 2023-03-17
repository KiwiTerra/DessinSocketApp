package metier.actions.formes;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Color;

public class FormeCercle extends Forme implements Serializable {

	private int longueur;
	private int hauteur;
	private boolean pleine;

	public FormeCercle(int x, int y, int stroke, Color couleur, int longueur, int hauteur, boolean pleine) {
		super(x, y, stroke, couleur);

		this.longueur = longueur;
		this.hauteur = hauteur;
		this.pleine = pleine;
	}

	public void dessiner(Graphics2D g) {
		g.setStroke(new BasicStroke(this.stroke));
		g.setColor(this.couleur);

		if (pleine) {
			g.fillOval(this.x, this.y, this.longueur, this.hauteur);
		} else {
			g.drawOval(this.x, this.y, this.longueur, this.hauteur);
		}
	}
	
}
