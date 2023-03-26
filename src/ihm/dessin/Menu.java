package ihm.dessin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.Controleur;

public class Menu extends JMenuBar implements ActionListener {

	private Controleur ctrl;

	private final String[] FORMATS_IMAGES = new String[] {"png", "jpeg", "gif", "bmp"};

	private JMenu menuFichier;
	private JMenu menuExporter;
	private JMenuItem[] menuExporterFormat;

	public Menu(Controleur ctrl) {
		this.ctrl = ctrl;

		// Création des composants
		this.menuFichier = new JMenu("Fichier");
		this.menuExporter = new JMenu("Exporter");

		this.menuExporterFormat = new JMenuItem[this.FORMATS_IMAGES.length];
		for (int cpt = 0 ; cpt < this.FORMATS_IMAGES.length ; cpt++)
			this.menuExporterFormat[cpt] = new JMenuItem("." + this.FORMATS_IMAGES[cpt]);
		
		// Placement des composants
		for (JMenuItem jmi : this.menuExporterFormat)
			this.menuExporter.add(jmi);
		
		this.menuFichier.add(this.menuExporter);
		this.add(this.menuFichier);

		// Activation des composants
		for (JMenuItem jmi : this.menuExporterFormat)
			jmi.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		for (int cpt = 0 ; cpt < this.FORMATS_IMAGES.length ; cpt++)
			if (this.menuExporterFormat[cpt] == e.getSource())
				this.ctrl.exporterSous(this.FORMATS_IMAGES[cpt]);
	}
	
}
