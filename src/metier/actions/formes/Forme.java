package metier.actions.formes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Forme implements Serializable
{
	protected int x;
	protected int y;
	protected int stroke;
	protected Color couleur;

	public Forme(int x, int y, int stroke, Color couleur)
	{
		this.x = x;
		this.y = y;
		this.stroke = stroke;
		this.couleur = couleur;
	}

	public abstract void dessiner(Graphics2D g);
}
