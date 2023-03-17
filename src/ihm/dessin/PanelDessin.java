package ihm.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import main.Controleur;
import metier.actions.Action;

public class PanelDessin extends JPanel
{
	private Controleur ctrl;

	private int[] taillePlateau;	
	private double zoomFactor = 1;

	public PanelDessin(Controleur ctrl, int[] taillePlateau)
	{
		this.ctrl = ctrl;
		this.taillePlateau = taillePlateau;

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
System.out.println(ctrl.getActions().size());
		for (Action a : ctrl.getActions())
		{
			a.getForme().dessiner(g2);
		}
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
