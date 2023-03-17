package metier.actions.formes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

public class FormeTexte extends Forme implements Serializable {

	private String texte;

	public FormeTexte(int x, int y, int stroke, Color couleur, String texte) {
		super(x, y, stroke, couleur);

		this.texte = texte;
	}

	public void dessiner(Graphics2D g) {
		g.setColor(this.couleur);
		g.setFont(new Font("Serif", Font.BOLD, this.stroke));

		g.drawString(this.texte, this.x, this.y);
	}
}