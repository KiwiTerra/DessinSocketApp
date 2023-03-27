package metier.actions.formes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Point;

public class FormePinceau extends Forme implements Serializable {

	private ArrayList<Point> points;

	public FormePinceau(int x, int y, int stroke, Color couleur) {
		super(x, y, stroke, couleur);

		this.points = new ArrayList<Point>();
	}

	public FormePinceau(int x, int y, int stroke, Color couleur, ArrayList<Point> p) {
		this(x, y, stroke, couleur);

		points.addAll(p);
	}
	
	public void dessiner(Graphics2D g) {

		this.points.add(0, new Point(this.x, this.y));
		
		g.setStroke(new BasicStroke(this.stroke));
		g.setColor(this.couleur);
		
		for (int x = 0 ; x < this.points.size()-1 ; x++) {
			Point p1 = this.points.get(x);
			Point p2 = this.points.get(x+1);

			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	public void ajouterPoint(Point p) {
		points.add(p);
	}
}
