package metier.actions.formes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import ihm.dessin.PanelDessin;

public class FormeSeau extends Forme implements Serializable {

	private PanelDessin panel;

	public FormeSeau(int x, int y, int stroke, Color couleur, PanelDessin panel) {
		super(x, y, stroke, couleur);

		this.panel = panel;
	}

	public void dessiner(Graphics2D g) {
		//BufferedImage image = null;
		BufferedImage image = new BufferedImage(this.panel.getWidth(), this.panel.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = image.createGraphics();
		this.panel.print(g2d);
		g2d.dispose();

	   try {
		System.out.println("transformation image");
		ImageIO.write(image, "PNG", new File("test.png"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

/*
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
           // System.out.println("coucou");
           // System.out.println("point:" + px + " " + py );
           // System.out.println("width:" + image.getWidth());
          //  System.out.println("height:" + image.getHeight());
          //  System.out.println("oldColor:" + oldColor.getRGB());
          //  System.out.println("point couleur: " + image.getRGB(px, py));

            if( px >= 0 && px < image.getWidth() && py >= 0 && py < image.getHeight() && image.getRGB(px, py) == oldColor.getRGB()) 
            {
              //  System.out.println("point dans boucle:" + px + " " + py );
                image.setRGB(px, py, 14481400);
                q.add(new Point(px + 1, py));
               // System.out.println("ajout");
                q.add(new Point(px - 1, py));
               // System.out.println("ajout");
                q.add(new Point(px, py + 1));
               // System.out.println("ajout");
                q.add(new Point(px, py - 1));
               // System.out.println("ajout");
            }

        }
		
		g.drawImage(image, 0, 0, null);*/
	}
}
