package ihm.dessin;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;

public class PanelJoueurs extends JPanel {
	
	private Controleur ctrl;

	public PanelJoueurs(Controleur ctrl) {
		this.ctrl = ctrl;

		this.add(new JLabel("joueurs"));
	}
}
