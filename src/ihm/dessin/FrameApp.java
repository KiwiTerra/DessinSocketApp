package ihm.dessin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class FrameApp extends JFrame {

	private Controleur ctrl;

	private final int LONGUEUR = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int HAUTEUR  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private PanelDessin  panelDessin;
	private PanelButtons panelBtns;
	private PanelJoueurs panelJoueurs;

	public FrameApp(Controleur ctrl) {

		this.ctrl = ctrl;

		this.setSize(this.LONGUEUR, this.HAUTEUR);
		this.setTitle("DessinSocketApp");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Création des composants
		this.panelDessin = new PanelDessin(ctrl);

		JPanel panelDroite = new JPanel(new GridLayout(2, 1));
		this.panelBtns     = new PanelButtons(ctrl);
		this.panelJoueurs  = new PanelJoueurs(ctrl);

		// Positionnement des composants
		this.add(this.panelDessin, BorderLayout.CENTER);
		this.add(panelDroite, BorderLayout.EAST);

		panelDroite.add(this.panelBtns);
		panelDroite.add(this.panelJoueurs);

		this.setVisible(true);
	}
}
