package Bullets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BulletRed3 extends BufferedImage implements Bullets{

	Random rd = new Random();
	public Image img;
	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public JPanel j;
	public double bx; // ����x
	public double by; // ����y
	public double bw; // ���� ���� ũ��
	public double bh; // ���� ���� ũ��
	public boolean bool;
	public int a = 0;
	public int b = 0;
	public int c = 58;
	public int d = 73; //584�� 8��

	public BulletRed3(){
	      super(40, 40, BufferedImage.TYPE_INT_ARGB);
	      
	      
	      Graphics2D g2d = (Graphics2D) getGraphics();
	      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			InputStream is = getClass().getResourceAsStream("123.png"); 
	        try { 
	        	img = ImageIO.read(is); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        }
	        g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
	}
	public BulletRed3(double x, double y, double yspeed, JPanel j) {
		this();
		this.x = x;
		this.y = y;
		this.yspeed = yspeed;
		this.j = j;
		bool = false;		// ó�� �Ѿ��� �׸����� �Ѵ�.
	}
	
	public void loop() { // x��ǥ�� ĳ������ x ��ǥ��.
		y -= yspeed;
		if (y <= 0) {
			bool = true;
		}
		/*for (int i = 0; i < MainPanel.brick.size(); i++) {
			bx = MainPanel.brick.get(i).x;
			by = MainPanel.brick.get(i).y;
			bw = MainPanel.brick.get(i).w;
			bh = MainPanel.brick.get(i).h;
			if (MainPanel.GetDistance(x + 12.5, y + 12.5, bx + bw / 2, by + bh / 2) <= this.getWidth() / 2 + bh) {
				this.y=j.getHeight()+50;
				bool = true;
			}
		}*/
		//MainFrame.bux = x + 12.5;		//���� ���� ���� �ȿ�
		//MainFrame.buy = y + 12.5;
		
		a+=90;							//�Ѿ��̹����� ��ȯ
		c+=90;
		if(a==450){
			a=0;
			c=90;				
		}
	}
	
	public void draw(Graphics2D g2d){
		if (!bool){
			AffineTransform old = g2d.getTransform();
			g2d.translate((int)x, (int)y);
			g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
			g2d.setTransform(old); //���� ������ġ�� ���ʱ�ȭ
		}
	}
	public void initBullet(boolean bool){
		 this.bool=bool;
	}
	public boolean getBulletBool(){
		return this.bool;
	}
	public void initBulletX(double initX)
	{
		x= initX;
	}
	public void initBulletY(double initY)
	{
		y= initY;
	}
}
