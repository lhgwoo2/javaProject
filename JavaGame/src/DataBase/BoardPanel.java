package DataBase;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BoardPanel extends JPanel{
	BufferedImage bimg;
	
	public BoardPanel() {
		drawingMainImage();
	}
	
    //이미지 객체 준비
    //ImageIcon backgroundImg = new ImageIcon("backImg.png");
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.drawImage(bimg, 0, 0, 1605, 978, null);
    	setOpaque(false);//그림을 표시하게 설정,투명하게 조절
        
   }
    public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("boardBG.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}
	}
}

