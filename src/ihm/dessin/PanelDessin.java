package ihm.dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	
		g2.setColor(Color.WHITE);
		g2.drawRect(0, 0, taillePlateau[0], taillePlateau[1]);

		Iterator<Action> itAction = ctrl.getActions().iterator();
		while (itAction.hasNext()) {
			Action a = itAction.next();
			a.getForme().dessiner(g2);
		}

		if (this.formeEnCours != null)
			this.formeEnCours.dessiner(g2);
	}

	public void setFormeEnCours(Forme f) {
		this.formeEnCours = f;
	}

	public void majIHM()
	{
		this.repaint();
	}

}
