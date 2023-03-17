package ihm.dessin;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;

public class PanelDessin extends JPanel {
	
	private Controleur ctrl;

	public PanelDessin(Controleur ctrl) {
		this.ctrl = ctrl;

		this.add(new JLabel("dessin"));
	}
}
