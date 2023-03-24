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
		BasicStroke bs = new BasicStroke(this.stroke);
        int decalage = (int) (bs.getLineWidth() / 2);
		
		g.setColor(this.couleur);
		g.fillOval(this.x - decalage, this.y - decalage, this.stroke, this.stroke);
		
		for(Point p : this.points)
			g.fillOval(p.x - decalage, p.y - decalage, this.stroke, this.stroke);
	}

	public void ajouterPoint(Point p) {
		points.add(p);
	}
}
