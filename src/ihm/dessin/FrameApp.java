package ihm.dessin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controleur;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

public class FrameApp extends JFrame {

	private Controleur ctrl;

	private final int LONGUEUR = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final int HAUTEUR  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private PanelCadre   panelCadre;
	private PanelButtons panelBtns;
	private PanelJoueurs panelJoueurs;

	public FrameApp(Controleur ctrl) {

		this.ctrl = ctrl;

		this.setSize(this.LONGUEUR, this.HAUTEUR);
		this.setTitle("DessinSocketApp");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Création des composants
		this.panelCadre = new PanelCadre(ctrl);

		JPanel panelDroite = new JPanel(new GridLayout(2, 1));
		this.panelBtns     = new PanelButtons(ctrl);
		this.panelJoueurs  = new PanelJoueurs(ctrl);

		JScrollPane spBtns = new JScrollPane(panelBtns);
		spBtns.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spBtns.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JScrollPane spJoueurs = new JScrollPane(panelJoueurs);
		spJoueurs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spJoueurs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

		// Positionnement des composants
		this.add(this.panelCadre, BorderLayout.CENTER);
		this.add(panelDroite, BorderLayout.EAST);

		panelDroite.add(spBtns);
		panelDroite.add(spJoueurs);

		this.setVisible(true);
	}
}
