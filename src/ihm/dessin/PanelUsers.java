package ihm.dessin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Controleur;

public class PanelUsers extends JPanel {
	
	private Controleur ctrl;

	public PanelUsers(Controleur ctrl) {
		
		this.ctrl = ctrl;

		this.setPreferredSize(new Dimension(250, 0));
		this.setBackground(Color.LIGHT_GRAY);

		this.add(new JLabel("Utilisateurs"));
	}
}
