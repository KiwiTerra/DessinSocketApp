package ihm.dessin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;

import java.awt.BorderLayout;
import java.awt.Toolkit;

public class FrameApp extends JFrame
{
	private Controleur ctrl;

	private final int LONGUEUR = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int HAUTEUR  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private PanelButtons panelBtns;
	private JPanel panelDessin;

	public FrameApp(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setSize(this.LONGUEUR, this.HAUTEUR);
		this.setTitle("DessinSocketApp");
		this.setLayout(new BorderLayout());

		// Création des composants
		this.panelBtns = new PanelButtons();
		this.panelDessin = new JPanel();
		this.panelDessin.add(new JLabel("dessin"));

		// Positionnement des composants
		this.add(this.panelDessin, BorderLayout.CENTER);
		this.add(this.panelBtns, BorderLayout.EAST);

		this.setVisible(true);
	}
}
