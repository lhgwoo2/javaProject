package GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PointPanel extends JPanel{

	
	MainPanel mp;
	public JLabel bluePointPanel;
	public JLabel redPointPanel;
	public int blueScore;
	public int redScore;
	
	public PointPanel(MainPanel mp){
		this.mp = mp;
		
		setLayout(null);
		setBounds(0,50,1600,900);
		setOpaque(false);
		
		JLabel point = new JLabel();
		point.setText("Score");
		point.setFont(new Font("TimesRoman",Font.BOLD,50));
		point.setBounds(50, 10, 200, 50);
		point.setForeground(Color.white);
		this.add(point);
		
		bluePointPanel = new JLabel(String.valueOf(blueScore));
		redPointPanel = new JLabel(String.valueOf(redScore));
		
		bluePointPanel.setFont(new Font("TimesRoman", Font.BOLD, 85));
		redPointPanel.setFont(new Font("TimesRoman", Font.BOLD, 85));
		
		bluePointPanel.setBounds(50, 60, 400, 90);
		redPointPanel.setBounds(50, 150, 400, 90);
		
		bluePointPanel.setForeground(Color.BLUE);
		redPointPanel.setForeground(Color.RED);
		
		mp.add(point);
		mp.add(bluePointPanel);
		mp.add(redPointPanel);
		
	}
	public void insertScore(){
		bluePointPanel.setText(String.valueOf(blueScore));
		redPointPanel.setText(String.valueOf(redScore));
	}
	
	
	
}
