package ihm.dessin;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.Controleur;

public class PanelCadre extends JPanel implements MouseWheelListener, MouseListener, MouseMotionListener
{
    private Controleur ctrl;
	private int[]      taillePlateau;

	private JButton btnCentrer;

	// attributs pour le zoom
	private double facteurZoom    = 1;
	private double facteurZoomMax = 2;
	private double facteurZoomMin = 0.75;

	// attributs pour le drag
    private boolean cliqueGaucheDrag;
    private boolean estDrag;
    private double  xDecalage = 50;
    private double  yDecalage = 50;
    private int     xDiff;
    private int     yDiff;
    private Point   pDebutDrag;

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
		if (cliqueGaucheDrag) 
		{
			this.panelImage.setBounds( (int) (this.xDecalage + this.xDiff ), 
			                           (int) (this.yDecalage + this.yDiff ),
			                           (int) (this.taillePlateau[0] * this.facteurZoom), 
									   (int) (this.taillePlateau[1] * this.facteurZoom) );

			if (this.estDrag) 
			{
				this.xDecalage += this.xDiff;
				this.yDecalage += this.yDiff;
				this.cliqueGaucheDrag = false;
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
		if (SwingUtilities.isRightMouseButton(e))
		{
			Point pointActu = e.getLocationOnScreen();

			this.xDiff = pointActu.x - this.pDebutDrag.x;
			this.yDiff = pointActu.y - this.pDebutDrag.y;

			this.cliqueGaucheDrag = true;
			majIHM();
		}
    }

	public void mouseClicked(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			Point p = new Point( (int) ((e.getX() - this.xDecalage) * (1 / this.facteurZoom)), 
			                     (int) ((e.getY() - this.yDecalage) * (1 / this.facteurZoom)) );
		}
	}

    public void mousePressed(MouseEvent e) 
	{
		if (SwingUtilities.isRightMouseButton(e))
		{
			this.estDrag = false;
			this.pDebutDrag = MouseInfo.getPointerInfo().getLocation();
		}
    }

    public void mouseReleased(MouseEvent e) 
	{
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
