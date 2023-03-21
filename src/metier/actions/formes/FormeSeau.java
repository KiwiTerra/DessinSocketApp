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

	private BufferedImage image;

	public FormeSeau(int x, int y, int stroke, Color couleur, BufferedImage image) {
		super(x, y, stroke, couleur);

		this.image = image;
	}

	public void dessiner(Graphics2D g) { //bug sur le zoom à fix
		
		// test
		try {ImageIO.write(image, "PNG", new File("test.png"));} catch (IOException e) {}

		Color oldColor = new Color(image.getRGB(x, y));
		Queue<Point> q = new LinkedList<Point>();

        if(image.getRGB(x, y) != this.couleur.getRGB())
            q.add(new Point(x, y));

        while(!q.isEmpty()) {
            Point p = q.poll();
            int px = p.x;
            int py = p.y;

            if( px >= 0 && px < image.getWidth() && 
				py >= 0 && py < image.getHeight() && 
				image.getRGB(px, py) == oldColor.getRGB() ) {

                image.setRGB(px, py, this.couleur.getRGB());
                q.add(new Point(px + 1, py));
                q.add(new Point(px - 1, py));
                q.add(new Point(px, py + 1));
                q.add(new Point(px, py - 1));
            }
        }

		// test
		try {ImageIO.write(image, "PNG", new File("testFinal.png"));} catch (IOException e) {}

		g.drawImage(image, 0, 0, null);
	}
}
