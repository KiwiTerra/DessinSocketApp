package metier.actions.formes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class FormeSeau extends Forme implements Serializable {
	public FormeSeau(int x, int y, int stroke, Color couleur) {
		super(x, y, stroke, couleur);
	}

	public void dessiner(Graphics2D g) {

		BufferedImage image = new BufferedImage(g.getDeviceConfiguration().getBounds().width, g.getDeviceConfiguration().getBounds().height, BufferedImage.TYPE_INT_ARGB);
		System.out.println(g.getDeviceConfiguration().getBounds().width);
        System.out.println(g.getDeviceConfiguration().getBounds().height);
		Color oldColor = new Color(0);

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
            System.out.println("coucou");
            System.out.println("point:" + px + " " + py );
            System.out.println("width:" + image.getWidth());
            System.out.println("height:" + image.getHeight());
            System.out.println("oldColor:" + oldColor.getRGB());
            System.out.println("point couleur: " + image.getRGB(px, py));

            if( px >= 0 && px < image.getWidth() && py >= 0 && py < image.getHeight() && image.getRGB(px, py) == oldColor.getRGB()) 
            {
                System.out.println("point dans boucle:" + px + " " + py );
                image.setRGB(px, py, 14481400);
                q.add(new Point(px + 1, py));
                System.out.println("ajout");
                q.add(new Point(px - 1, py));
                System.out.println("ajout");
                q.add(new Point(px, py + 1));
                System.out.println("ajout");
                q.add(new Point(px, py - 1));
                System.out.println("ajout");
            }

        }
		
		g.drawImage(image, 0, 0, null);
	}
}
