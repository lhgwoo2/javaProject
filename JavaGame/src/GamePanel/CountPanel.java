package GamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CountPanel extends JPanel{
	
	Image countImg;
	int sx1, sy1, sx2, sy2;
	int row, col;
	MainPanel mp;
	
	public CountPanel(MainPanel mp){
		this.mp = mp;
		InputStream count = getClass().getResourceAsStream("count.png");
		setLayout(null);
		setBounds(657,257,286,286);
		setOpaque(false);
		try {
			countImg = ImageIO.read(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mp.add(this);
	}
	
	public void loop() {
		sx1 = col++ * 286;
		sy1 = row * 286;
		sx2 = sx1 + 286;
		sy2 = sy1 + 286;

		if (col == 5) {
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
		g2d.drawImage(countImg, 0, 0, 286, 286, sx1, sy1, sx2, sy2, null);
		
	}

	

}
