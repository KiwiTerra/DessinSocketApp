package metier.actions.formes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class FormeSeau extends Forme implements Serializable {
	public FormeSeau(int x, int y, int stroke, Color couleur) {
		super(x, y, stroke, couleur);
	}

	public void dessiner(Graphics2D g) {
		g.setColor(this.couleur);

		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
	
		
		Color oldColor = new Color(image.getRGB(x, y));

		Queue<Point> q = new LinkedList<Point>();

        if(image.getRGB(x, y) != this.couleur.getRGB()) 
        {
            q.add(new Point(x, y));
        }

        while(!q.isEmpty()) 
        {
            Point p = q.poll();
            int px = p.x;
            int py = p.y;

            if( px >= 0 && px < image.getWidth() && py >= 0 && py < image.getHeight() && image.getRGB(px, py) == oldColor.getRGB()) 
            {
                image.setRGB(px, py, couleur.getRGB());
                q.add(new Point(px + 1, py));
                q.add(new Point(px - 1, py));
                q.add(new Point(px, py + 1));
                q.add(new Point(px, py - 1));
            }

        }
		
		g.drawImage(image, 0, 0, null); 
	}
}
