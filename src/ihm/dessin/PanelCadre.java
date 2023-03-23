package ihm.dessin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ihm.constantes.Outils;
import main.Controleur;
import metier.actions.formes.Forme;
import metier.actions.formes.FormeCarre;
import metier.actions.formes.FormeCercle;
import metier.actions.formes.FormeLigne;

public class PanelCadre extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener
{
    private Controleur ctrl;
	private int[]      taillePlateau;

	// attributs pour le zoom
	private double facteurZoom    = 1;
	private double facteurZoomMax = 2;
	private double facteurZoomMin = 0.75;

	// attributs pour le drag
    private boolean cliqueDroitDrag;
    private boolean estDrag;
    private double  xDecalage = 50;
    private double  yDecalage = 50;
    private int     xDiff;
    private int     yDiff;
    private Point   pDebutDrag;

	// attributs pour le dessin
	private Point pDebutForme;

	private PanelDessin panelImage;

    public PanelCadre(Controleur ctrl)
    {
        this.ctrl = ctrl;
		this.taillePlateau = new int[] { 500, 500 };

		this.setLayout(null);
		this.setBackground(Color.GRAY);

		// Création des composants
		this.panelImage = new PanelDessin(ctrl, this.taillePlateau);

		// Ajout des composants
		this.add(this.panelImage);
		this.panelImage.setBounds(50, 50, this.taillePlateau[0], this.taillePlateau[1]);

		// Ajout des listeners
		this.addMouseWheelListener (this);
        this.addMouseMotionListener(this);
        this.addMouseListener      (this);
    }

	public void majIHM()
	{
		if (cliqueDroitDrag) 
		{
			this.panelImage.setBounds( (int) (this.xDecalage + this.xDiff ), 
			                           (int) (this.yDecalage + this.yDiff ),
			                           (int) (this.taillePlateau[0] * this.facteurZoom), 
									   (int) (this.taillePlateau[1] * this.facteurZoom) );

			if (this.estDrag) 
			{
				this.xDecalage += this.xDiff;
				this.yDecalage += this.yDiff;
				this.cliqueDroitDrag = false;
			}
		}

		this.panelImage.majIHM();
	}

	public void centrer()
	{
		int largeur = this.getWidth();
		int hauteur = this.getHeight();
		

		// Calcul du facteur de zoom maximal
		double zoomLargeur = (double) largeur / this.taillePlateau[0];
		double zoomHauteur = (double) hauteur / this.taillePlateau[1];
		this.facteurZoom = Math.min(zoomLargeur, zoomHauteur);

		// Vérification des limites du zoom
		if (this.facteurZoom > this.facteurZoomMax)
			this.facteurZoomMax = this.facteurZoom + 1.0;
		
		if (this.facteurZoom < this.facteurZoomMin)
			this.facteurZoomMin = this.facteurZoom - 0.5;


		// Calcul du décalage pour centrer l'image
		this.xDecalage = (largeur - (this.taillePlateau[0] * this.facteurZoom)) / 2;
		this.yDecalage = (hauteur - (this.taillePlateau[1] * this.facteurZoom)) / 2;

		// Mise à jour de l'image
		this.panelImage.setBounds( (int)  this.xDecalage, (int) this.yDecalage,
		                           (int) (this.taillePlateau[0] * facteurZoom), 
								   (int) (this.taillePlateau[1] * facteurZoom) );
		this.panelImage.majZoom(this.facteurZoom);
	}

    public void mouseWheelMoved(MouseWheelEvent e) 
	{
        if (e.getWheelRotation() < 0 && this.facteurZoom * 1.1 < this.facteurZoomMax) 
		{
            this.facteurZoom *= 1.1;
			this.panelImage.majZoom(this.facteurZoom);
        }

        if (e.getWheelRotation() > 0 && this.facteurZoom / 1.1 > this.facteurZoomMin) 
		{
            this.facteurZoom /= 1.1;
			this.panelImage.majZoom(this.facteurZoom);
        }
    }

    public void mouseDragged(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) ((e.getX() - this.xDecalage) * (1 / this.facteurZoom));
			int y = (int) ((e.getY() - this.yDecalage) * (1 / this.facteurZoom));

			if (this.ctrl.getOutilActif() == Outils.LIGNE)
			{
				FormeLigne f = new FormeLigne(
					(int) this.pDebutForme.getX(), 
					(int) this.pDebutForme.getY(), 
					this.ctrl.getEpaisseur(), 
					this.ctrl.getCouleur(), x, y
				);

				this.panelImage.setFormeEnCours(f);
				this.panelImage.majIHM();
			}

			if (this.ctrl.getOutilActif() == Outils.CARRE || this.ctrl.getOutilActif() == Outils.CERCLE)
			{
				Forme f = null;
				int longueur = (int) (x - this.pDebutForme.getX());
				int hauteur  = (int) (y - this.pDebutForme.getY());

				if (this.ctrl.getOutilActif() == Outils.CARRE)
					f = new FormeCarre(
						(int) this.pDebutForme.getX(), 
						(int) this.pDebutForme.getY(), 
						this.ctrl.getEpaisseur(), 
						this.ctrl.getCouleur(), 
						longueur, hauteur, 
						this.ctrl.getRemplir()
					);

				if (this.ctrl.getOutilActif() == Outils.CERCLE)
					f = new FormeCercle(
						(int) this.pDebutForme.getX(), 
						(int) this.pDebutForme.getY(), 
						this.ctrl.getEpaisseur(), 
						this.ctrl.getCouleur(), 
						longueur, hauteur, 
						this.ctrl.getRemplir()
					);

				this.panelImage.setFormeEnCours(f);
				this.panelImage.majIHM();
			}
		}

		if (SwingUtilities.isRightMouseButton(e))
		{
			Point pointActu = e.getLocationOnScreen();

			this.xDiff = pointActu.x - this.pDebutDrag.x;
			this.yDiff = pointActu.y - this.pDebutDrag.y;

			this.cliqueDroitDrag = true;
			majIHM();
		}
    }

	public void mouseClicked(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) ((e.getX() - this.xDecalage) * (1 / this.facteurZoom));
			int y = (int) ((e.getY() - this.yDecalage) * (1 / this.facteurZoom));

			if (this.ctrl.getOutilActif() == Outils.TEXTE)
			{
				String texte = JOptionPane.showInputDialog ( this, "Que voulez-vous écrire ?", "Choisir texte", JOptionPane.QUESTION_MESSAGE );

				if (texte != null)
					this.ctrl.dessinerTexte(x, y, texte);
			}

			if (this.ctrl.getOutilActif() == Outils.SEAU)
			{
				BufferedImage image = new BufferedImage(this.taillePlateau[0], this.taillePlateau[1], BufferedImage.TYPE_INT_ARGB);

				Graphics2D g2d = image.createGraphics();
				this.panelImage.print(g2d);
				g2d.dispose();

				this.ctrl.dessinerSeau(x, y, image);
			}
		}
	}

    public void mousePressed(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) ((e.getX() - this.xDecalage) * (1 / this.facteurZoom));
			int y = (int) ((e.getY() - this.yDecalage) * (1 / this.facteurZoom));
			this.pDebutForme = new Point(x, y);
		}

		if (SwingUtilities.isRightMouseButton(e))
		{
			this.estDrag = false;
			this.pDebutDrag = MouseInfo.getPointerInfo().getLocation();
		}
    }

    public void mouseReleased(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) ((e.getX() - this.xDecalage) * (1 / this.facteurZoom));
			int y = (int) ((e.getY() - this.yDecalage) * (1 / this.facteurZoom));

			if (this.ctrl.getOutilActif() == Outils.LIGNE)
			{
				this.panelImage.setFormeEnCours(null);
				this.ctrl.dessinerLigne(
					(int) this.pDebutForme.getX(), (int) this.pDebutForme.getY(), x, y);
			}

			if (this.ctrl.getOutilActif() == Outils.CARRE || this.ctrl.getOutilActif() == Outils.CERCLE)
			{
				int longueur = (int) (x - this.pDebutForme.getX());
				int hauteur  = (int) (y - this.pDebutForme.getY());

				this.panelImage.setFormeEnCours(null);

				if (this.ctrl.getOutilActif() == Outils.CARRE)
					this.ctrl.dessinerCarre((int) this.pDebutForme.getX(), 
						(int) this.pDebutForme.getY(), longueur, hauteur);

				if (this.ctrl.getOutilActif() == Outils.CERCLE)
					this.ctrl.dessinerCercle((int) this.pDebutForme.getX(), 
						(int) this.pDebutForme.getY(), longueur, hauteur);
			}
		}

		if (SwingUtilities.isRightMouseButton(e))
		{
        	this.estDrag = true;
        	majIHM();
		}
    }

	public void mouseMoved  (MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
}
