package ihm.dessin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import ihm.constantes.Outils;
import main.Controleur;
import metier.actions.formes.Forme;
import metier.actions.formes.FormeCarre;
import metier.actions.formes.FormeCercle;
import metier.actions.formes.FormeLigne;
import metier.actions.formes.FormePinceau;

public class PanelCadre extends JPanel implements MouseListener, MouseMotionListener
{
    private Controleur ctrl;
	private int[]      taillePlateau;

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
	private FormePinceau pinceau;
	private ArrayList<Point> pointsPinceau;

	private PanelDessin panelImage;

    public PanelCadre(Controleur ctrl)
    {
        this.ctrl = ctrl;
		this.taillePlateau = new int[] { 1200, 800 };

		this.setLayout(null);
		this.setBackground(Color.GRAY);

		// Création des composants
		this.panelImage = new PanelDessin(ctrl, this.taillePlateau);

		// Ajout des composants
		this.add(this.panelImage);
		this.panelImage.setBounds(50, 50, this.taillePlateau[0], this.taillePlateau[1]);

		// Ajout des listeners
        this.addMouseMotionListener(this);
        this.addMouseListener      (this);
    }

	public void majIHM()
	{
		if (cliqueDroitDrag) 
		{
			this.panelImage.setBounds( (int) (this.xDecalage + this.xDiff ), 
			                           (int) (this.yDecalage + this.yDiff ),
			                           this.taillePlateau[0], 
									   this.taillePlateau[1] );

			if (this.estDrag) 
			{
				this.xDecalage += this.xDiff;
				this.yDecalage += this.yDiff;
				this.cliqueDroitDrag = false;
			}
		}

		this.panelImage.majIHM();
	}

	public void exporterSous(String format) {
		// Choit du répertoire de sauvegarde
		JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		choose.setFileFilter(new FileNameExtensionFilter("Création d'une image au format " + format, format));
		int res = choose.showSaveDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) 
		{
			// Récupération des paramètres du fichier
			File file = choose.getSelectedFile();
			String filePath = file.getAbsolutePath();

			// Transformer le dessin en image
			BufferedImage image = new BufferedImage(this.taillePlateau[0], this.taillePlateau[1], BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = image.createGraphics();
			this.panelImage.paint(g2d);
			g2d.dispose();

			// Enregistrement du fichier dans le répertoire choisi
			try {
				ImageIO.write(image, format, new File(filePath + "." + format));
				JOptionPane.showMessageDialog(this, "Exportation réussi");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Erreur lors de l'exportation");
			}
		}
	}
    
    public void mouseDragged(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) (e.getX() - this.xDecalage);
			int y = (int) (e.getY() - this.yDecalage);

			if(this.ctrl.getOutilActif() == Outils.PINCEAU)
			{
				this.pointsPinceau.add(new Point(x, y));
				this.pinceau.ajouterPoint(new Point(x, y));
				this.panelImage.majIHM();
			}

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
			int x = (int) (e.getX() - this.xDecalage);
			int y = (int) (e.getY() - this.yDecalage);

			if (this.ctrl.getOutilActif() == Outils.TEXTE)
			{
				String texte = JOptionPane.showInputDialog(this, "Que voulez-vous écrire ?", "Choisir texte", JOptionPane.QUESTION_MESSAGE);

				if (texte != null)
					this.ctrl.dessinerTexte(x, y, texte);
			}

			if (this.ctrl.getOutilActif() == Outils.PIPETTE)
			{
				if ( x >= 0 && x < this.taillePlateau[0] && y >= 0 && y < this.taillePlateau[1] )
				{
					// transformer le panel dessin en BufferedImage
					BufferedImage image = new BufferedImage(this.taillePlateau[0], this.taillePlateau[1], BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = image.createGraphics();
					this.panelImage.paint(g2d);
					g2d.dispose();

					this.ctrl.setCouleur(new Color(image.getRGB(x, y)));
				}
			}
		}
	}

    public void mousePressed(MouseEvent e) 
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			int x = (int) (e.getX() - this.xDecalage);
			int y = (int) (e.getY() - this.yDecalage);
			this.pDebutForme = new Point(x, y);

			if(this.ctrl.getOutilActif() == Outils.PINCEAU)
			{
				this.pointsPinceau = new ArrayList<Point>();
				this.pinceau = new FormePinceau(
					x, y, this.ctrl.getEpaisseur(), this.ctrl.getCouleur()
				);

				this.panelImage.setFormeEnCours(this.pinceau);
				this.panelImage.majIHM();
			}
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
			int x = (int) (e.getX() - this.xDecalage);
			int y = (int) (e.getY() - this.yDecalage);

			if(this.ctrl.getOutilActif() == Outils.PINCEAU)
			{
				this.ctrl.dessinerPinceau((int) this.pDebutForme.getX(), (int) this.pDebutForme.getY(), this.pointsPinceau);
				this.panelImage.setFormeEnCours(null);
				this.pinceau = null;
			}

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
