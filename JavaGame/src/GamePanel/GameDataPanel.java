package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class GameDataPanel extends JPanel 
{
	Image img;
	Image boardimg;
	JPanel j;
	int sx1, sy1, sx2, sy2;
	int row, col;
	public GameDataPanel(JPanel j) {
		super();
		
		this.j = j;
		this.setBounds(j.getWidth()-1000, j.getHeight()-175, 1000, 175);
		this.setLayout(null);

		InputStream in = getClass().getResourceAsStream("countdown.png");
		InputStream board = getClass().getResourceAsStream("skillboard.png");
		try {
			img = ImageIO.read(in);
			boardimg = ImageIO.read(board);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loop() {
		sx1 = col++ * 375;
		sy1 = row * 141;
		sx2 = sx1 + 375;
		sy2 = sy1 + 141;

		if (col == 10) {
			row++;
			col = 0;
		}
		if (row == 7) {
			row = 0;
		}
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(boardimg,0, 0, 1000, 175, null);
		g2d.drawImage(img, 45, 60, 245, 140, sx1, sy1, sx2, sy2, null);
	}

}
