package GamePanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainPanel extends JPanel{
	
	BufferedImage bimg;
	Graphics2D g2d;
	JFrame f;
	
	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1800, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;
		
		repaint();
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
		//g2d.drawImage(bimg,0,0,1800,900,null);
		
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
		//g2d.drawImage(bimg,0,0,1800,900,null);
		
	}
	public void chatPanel(){
		JTextArea chatField = new JTextArea("채팅");

		JScrollPane sp = new JScrollPane(chatField);
		sp.setBounds(0, 600, 350, 300);
		sp.setBackground(new Color(200,0,0,0));
		this.add(sp);
		
	}
	public void drawBlackPanel(){
		BufferedImage chatBImg = new BufferedImage(350, 300, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) chatBImg.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, chatBImg.getWidth(),chatBImg.getHeight());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.dispose();		// 붓 필요없다.
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1800,900,null);
	}

}
