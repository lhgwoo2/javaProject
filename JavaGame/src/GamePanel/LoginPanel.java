package GamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	
	
	public LoginPanel() {
		super();
		this.setBounds(0, 0, 1800, 50);
		this.setLayout(null);
		this.setBackground(Color.GREEN);

		// sever entrance - ID
		JLabel ja = new JLabel("User ID");
		ja.setBounds(200, 20, 50, 30);
		this.add(ja);

		JLabel ja2 = new JLabel("Team Selection");
		ja2.setBounds(500, 20, 100, 30);
		this.add(ja2);

		// login text field
		JTextField jt = new JTextField("1");
		jt.setBounds(300, 20, 150, 30);
		this.add(jt);

		// Team selection button
		// Blue team
		JRadioButton jb = new JRadioButton("Blue Team");
		jb.setBounds(600, 20, 100, 30);
		jb.setBackground(Color.CYAN);
		this.add(jb);

		// Red team
		JRadioButton jb2 = new JRadioButton("Red Team");
		jb2.setBounds(700, 20, 100, 30);
		jb2.setBackground(Color.red);
		this.add(jb2);
		
		//  Team button group
		ButtonGroup teamBg = new ButtonGroup();
		teamBg.add(jb);
		teamBg.add(jb2);
				
		// Entrance button
		JButton entB = new JButton("Entrance");
		entB.setBounds(900, 20, 100, 30);
		this.add(entB);

	}
	
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		
	}

}
