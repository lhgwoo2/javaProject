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

public class BulletRed1 extends BufferedImage implements Bullets{

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
	public int d = 580; //584�� 8��
	
	public BulletRed1(){
		super(58,580, BufferedImage.TYPE_INT_ARGB);
	      
	      
	      Graphics2D g2d = (Graphics2D) getGraphics();
	      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			InputStream is = getClass().getResourceAsStream("/imagePack/bulletred.png"); 
	        try { 
	        	img = ImageIO.read(is); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        }
	        g2d.drawImage(img, 0, 0,58/2,580,a,b,c,d,null);
	}
	public BulletRed1(double x, double y, double yspeed, JPanel j) {
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
	}
	
	public void draw(Graphics2D g2d){
		if (!bool){
			AffineTransform old = g2d.getTransform();
			g2d.translate((int)x, (int)y);
			g2d.drawImage(img, 0, 0,58/2,580,a,b,c,d,null);
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
