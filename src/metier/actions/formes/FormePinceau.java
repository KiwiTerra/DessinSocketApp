package metier.actions.formes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Point;

public class FormePinceau extends Forme implements Serializable {

    private ArrayList<Point> points;

    public FormePinceau(int x, int y, int stroke, Color couleur, ArrayList<Point> points) {
        super(x, y, stroke, couleur);

        this.couleur = couleur;
        this.x = x;
        this.y = y;
        this.stroke = stroke;
        this.points = points;
    }
    
    
    public void dessiner(Graphics2D g) {
        
        for(Point p : points) {
            g.setColor(couleur);
            g.fillOval(p.x, p.y, stroke, stroke);
        }
    }
}
