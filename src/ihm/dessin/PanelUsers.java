package ihm.dessin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Controleur;
import metier.Joueur;

public class PanelUsers extends JPanel {
	
	private Controleur ctrl;

	private DefaultListModel<String> listModel;
    private JList<String> joueursList;

	public PanelUsers(Controleur ctrl) {
		this.ctrl = ctrl;

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Liste des joueurs"));
		this.setBackground(Color.LIGHT_GRAY);

		this.listModel = new DefaultListModel<String>();
        this.joueursList = new JList<String>(listModel);

		JScrollPane scrollPane = new JScrollPane(joueursList);
		scrollPane.setPreferredSize(new Dimension(200, 200));

		this.majIHM();

		this.add(scrollPane, BorderLayout.CENTER);
	}

	public void majIHM() {
		this.listModel.clear();
		for(Joueur joueur : this.ctrl.getJoueurs()) {
			this.listModel.addElement(joueur.getNom());
		}
	}

}
