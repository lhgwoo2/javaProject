package GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel{
	
	Image loadingIcon;
	Image bg;
	Image sprite;
	int sx1, sy1, sx2, sy2;
	int row, col;
	MainPanel mp;
	
	public LoadingPanel(MainPanel mp){
		this.mp = mp;
		//loadingIcon = Toolkit.getDefaultToolkit().createImage("C:/Users/duniv026/Desktop/ball.gif");
		InputStream in = getClass().getResourceAsStream("/ImagePack/LoadingBG.jpg");
		
		InputStream ball = getClass().getResourceAsStream("/ImagePack/ball_bounce.png");
		setLayout(null);
		setBounds(0,50,1600,900);
		
		try {
			bg = ImageIO.read(in);
			sprite = ImageIO.read(ball);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//ImageIcon imageIcon = new ImageIcon(ball);
		JLabel label = new JLabel();
		label.setText("Loading...");
		label.setFont(new Font("πŸ≈¡",Font.ITALIC,50));
		label.setBounds(700, 450, 300, 200);
		label.setForeground(Color.white);
		this.add(label);
		
	}
	
	public void loop() {
		sx1 = col++ * 50;
		sy1 = row * 50;
		sx2 = sx1 + 50;
		sy2 = sy1 + 50;

		if (col == 6) {
			row++;
			col = 0;
		}
		if (row == 1) {
			row = 0;
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg, 0, 0, 1600, 900, null);
		g2d.drawImage(sprite, 750, 400, 850, 500, sx1, sy1, sx2, sy2, null);
		
	}

}
