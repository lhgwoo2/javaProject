package GamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	
	BufferedImage bimg;
	
	public MainPanel() {
		super();
		this.setBounds(0, 50, 1800, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		

		drawingMainImage();
		
	}
	
	public void drawingMainImage(){
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}
	}
	public void drawingPlayImage(){
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback1.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("게임화면 로드 성공");
		} catch (IOException e) {
			System.out.println("게임화면 로드 실패");
			e.printStackTrace();
		}
		
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1800,900,null);
	}

}
