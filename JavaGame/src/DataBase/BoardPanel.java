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
	
    //�̹��� ��ü �غ�
    //ImageIcon backgroundImg = new ImageIcon("backImg.png");
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.drawImage(bimg, 0, 0, 1605, 978, null);
    	setOpaque(false);//�׸��� ǥ���ϰ� ����,�����ϰ� ����
        
   }
    public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("boardBG.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
	}
}

