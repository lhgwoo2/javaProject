package GamePanel;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameDataPanel extends JPanel 
{
	JPanel j;
	public GameDataPanel(JPanel j) {
		super();
		
		this.j = j;
		this.setBounds(j.getWidth()-1000, j.getHeight()-200, 1000, 200);
		this.setLayout(null);
		this.setBackground(Color.WHITE);

	}

}
