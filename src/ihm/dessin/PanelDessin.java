package ihm.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.JPanel;

import main.Controleur;
import metier.actions.Action;
import metier.actions.formes.Forme;

public class PanelDessin extends JPanel
{
	private Controleur ctrl;
	private Forme formeEnCours;

	private int[] taillePlateau;	
	private double zoomFactor = 1;

	public PanelDessin(Controleur ctrl, int[] taillePlateau)
	{
		this.ctrl = ctrl;
		this.taillePlateau = taillePlateau;
		this.formeEnCours = null;

		this.setSize(taillePlateau[0], taillePlateau[1]);
	}

	@Override
    public void paint(Graphics g) 
    {
		super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

		AffineTransform at = new AffineTransform();
		at.scale(zoomFactor, zoomFactor);
        g2.transform(at);

		g2.setColor(Color.WHITE);
		g2.drawRect(0, 0, taillePlateau[0], taillePlateau[1]);

		System.out.println("nb action : " + ctrl.getActions().size());
		Iterator<Action> itAction = ctrl.getActions().iterator();
		while (itAction.hasNext())
		{
			Action a = itAction.next();

			a.getForme().dessiner(g2);
		}

		if (this.formeEnCours != null)
			this.formeEnCours.dessiner(g2);
	}

	public void setFormeEnCours(Forme f) {
		this.formeEnCours = f;
	}
	
	public void majZoom(double zoomFactor)
	{
		this.zoomFactor = zoomFactor;
		this.setSize( (int) (this.taillePlateau[0] * zoomFactor), 
		              (int) (this.taillePlateau[1] * zoomFactor) );
		this.repaint();
	}

	public void majIHM()
	{
		this.repaint();
	}

}
