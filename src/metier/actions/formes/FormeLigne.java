package metier.actions.formes;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.awt.BasicStroke;
import java.awt.Color;

public class FormeLigne extends Forme implements Serializable {

	private int xArrivee;
	private int yArrivee;

	public FormeLigne(int x, int y, int stroke, Color couleur, int xArrivee, int yArrivee) {
		super(x, y, stroke, couleur);

		this.xArrivee = xArrivee;
		this.yArrivee = yArrivee;
	}

	public void dessiner(Graphics2D g) {
		g.setStroke(new BasicStroke(this.stroke));
		g.setColor(this.couleur);

		g.drawLine(this.x, this.y, this.xArrivee, this.yArrivee);
	}
	
}