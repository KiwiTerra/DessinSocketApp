package ihm.dessin;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;

public class PanelButtons extends JPanel
{
	private final int NB_BTN = 6;

	private JButton    btnCoul;
	private JSeparator sep1;

	private JButton[]  tabTbnOutils;
	private JCheckBox  cbRemplir;
	private JButton    btnUndo;
	private JSeparator sep2;

	private JSlider sliEpai;
	private JLabel  lblEpai; 
	
	public PanelButtons() {	
		
		this.setPreferredSize(new Dimension(250, 482));
		this.setBackground(Color.LIGHT_GRAY);

		// Création des composants
		btnCoul = new JButton("Couleur");
		sep1    = new JSeparator();
		
		tabTbnOutils = new JButton[NB_BTN];
		for (int i = 0; i < NB_BTN; i++) {
			tabTbnOutils[i] = new JButton();
			tabTbnOutils[i].setPreferredSize(new Dimension(50, 50));
		}
		cbRemplir = new JCheckBox("Remplir les formes");
		btnUndo   = new JButton("UNDO");
		sep2      = new JSeparator();

		sliEpai = new JSlider();
		lblEpai = new JLabel("111");

		// Positionnement des composants
		this.setLayout(this.creerLayout());


	}                                                       
         
	private GroupLayout creerLayout() {

		GroupLayout layout = new GroupLayout(this);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addComponent(sep1, GroupLayout.Alignment.TRAILING)
			.addComponent(sep2)
			.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(btnCoul)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addGroup(layout.createSequentialGroup()
				.addGap(25, 25, 25)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addComponent(sliEpai, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(lblEpai, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(23, Short.MAX_VALUE))
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(cbRemplir, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
									.addComponent(tabTbnOutils[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tabTbnOutils[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(tabTbnOutils[4], GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tabTbnOutils[1], GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(tabTbnOutils[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tabTbnOutils[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGap(25, 25, 25))))
			.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(btnUndo)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(33, 33, 33)
				.addComponent(btnCoul)
				.addGap(36, 36, 36)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
					.addComponent(tabTbnOutils[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(layout.createSequentialGroup()
						.addComponent(sep1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(tabTbnOutils[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tabTbnOutils[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(tabTbnOutils[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(tabTbnOutils[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(tabTbnOutils[5], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(26, 26, 26)
				.addComponent(cbRemplir)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(btnUndo)
				.addGap(11, 11, 11)
				.addComponent(sep2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(sliEpai, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblEpai))
				.addContainerGap(122, Short.MAX_VALUE))
		);

		return layout;
	}
	           
}
