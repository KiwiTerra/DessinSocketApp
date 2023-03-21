package ihm.dessin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Controleur;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;

public class PanelButtons extends JPanel implements ActionListener, ChangeListener {

	private final int      NB_BTN      = 6;
	private final String[] TAB_IMG_BTN = new String[] {"Pinceau", "Ligne", "Rectangle", "Rond", "Texte", "Seau"};

	private Controleur ctrl;
	private int 	   outilActif;

	private JButton    btnCoul;
	private JSeparator sep1;

	private JButton[]  tabTbnOutils;
	private JCheckBox  cbRemplir;
	private JButton    btnUndo;
	private JSeparator sep2;

	private JSlider sliEpai;
	private JLabel  lblEpai; 
	
	public PanelButtons(Controleur ctrl) {	
		
		this.ctrl = ctrl;
		this.setPreferredSize(new Dimension(250, 400));
		this.setBackground(Color.LIGHT_GRAY);

		//    Création des composants	

		// Couleurs
		this.btnCoul = new JButton("Couleur");
		this.btnCoul.setBackground(Color.WHITE);

		this.sep1 = new JSeparator();
		
		// Outils
		this.tabTbnOutils = new JButton[this.NB_BTN];
		for (int i = 0; i < this.NB_BTN; i++) {
			this.tabTbnOutils[i] = new JButton();
			this.tabTbnOutils[i].setBackground(Color.WHITE);
			this.tabTbnOutils[i].setPreferredSize(new Dimension(50, 50));
			
			String img = "./ressources/img/outils/" + this.TAB_IMG_BTN[i] + ".png";
			try{
				this.tabTbnOutils[i].setIcon(new ImageIcon(ImageIO.read(new File(img)).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			}catch(Exception e){}
		}
		this.tabTbnOutils[0].setBackground(Color.GREEN);
		this.outilActif = 0;

		this.cbRemplir = new JCheckBox("Remplir les formes");
		this.cbRemplir.setBackground(Color.LIGHT_GRAY);

		this.btnUndo   = new JButton("UNDO");
		this.btnUndo.setBackground(Color.WHITE);

		this.sep2      = new JSeparator();

		// Epaisseur
		this.sliEpai = new JSlider();
		this.sliEpai.setBackground(Color.LIGHT_GRAY);
		this.sliEpai.setMinimum(1);
		this.sliEpai.setMaximum(100);
		this.sliEpai.setValue(5);

		this.lblEpai = new JLabel("  1");
		this.lblEpai.setBackground(Color.LIGHT_GRAY);

		//    Positionnement des composants
		this.setLayout(this.creerLayout());


		//    Activation des composants
		this.btnCoul.addActionListener(this);

		for (int i = 0; i < this.NB_BTN; i++)
			this.tabTbnOutils[i].addActionListener(this);

		this.btnUndo.addActionListener(this);

		this.sliEpai.addChangeListener(this);
	}  

	public int getOutilActif() { 
		return this.outilActif;
	}

	public Color getCouleur() {
		return this.btnCoul.getBackground();
	}

	public boolean getRemplir() {
		return this.cbRemplir.isSelected();
	}

	public int getEpaisseur() {
		return this.sliEpai.getValue();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.btnCoul) {
			Color couleur = JColorChooser.showDialog(this, "Choisir une couleur", Color.WHITE);

			if (couleur != null) 
				this.btnCoul.setBackground(couleur);
		}
		
		for (int i = 0; i < this.NB_BTN; i++) {
			if (e.getSource() == this.tabTbnOutils[i]) {
				
				for (int j = 0; j < this.NB_BTN; j++)
					this.tabTbnOutils[j].setBackground(Color.WHITE);
				
				
				this.tabTbnOutils[i].setBackground(Color.GREEN);
				this.outilActif = i;
				break;
			}
		}

		if (e.getSource() == this.btnUndo) {
			System.out.println("UNDO");
		}
	}

	public void stateChanged(ChangeEvent e) {
		this.lblEpai.setText(String.format("%3d",this.sliEpai.getValue()));
	}
         
	private GroupLayout creerLayout() {

		GroupLayout layout = new GroupLayout(this);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(this.sep1, GroupLayout.Alignment.TRAILING)
			.addComponent(this.sep2)
			.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(this.btnCoul)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addGroup(layout.createSequentialGroup()
				.addGap(25, 25, 25)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(this.sliEpai, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(this.lblEpai, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(23, Short.MAX_VALUE))
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(this.cbRemplir, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
									.addComponent(this.tabTbnOutils[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(this.tabTbnOutils[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(this.tabTbnOutils[4], GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(this.tabTbnOutils[1], GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(this.tabTbnOutils[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(this.tabTbnOutils[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(25, 25, 25))))
			.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(this.btnUndo)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(33, 33, 33)
				.addComponent(this.btnCoul)
				.addGap(36, 36, 36)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addComponent(this.tabTbnOutils[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(this.sep1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(this.tabTbnOutils[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(this.tabTbnOutils[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(this.tabTbnOutils[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(this.tabTbnOutils[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(this.tabTbnOutils[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(26, 26, 26)
				.addComponent(this.cbRemplir)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(this.btnUndo)
				.addGap(15, 15, 15)
				.addComponent(this.sep2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(this.sliEpai, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(this.lblEpai))
				.addContainerGap(122, Short.MAX_VALUE))
		);

		return layout;
	}
}
