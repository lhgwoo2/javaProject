package HKM;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bullet extends BufferedImage {

	Random rd = new Random();

	 double x; 
	 double y;  
	 double xspeed ;
	 double yspeed ;
	 JPanel j;
	

	public Bullet(){
	      super(40, 40, BufferedImage.TYPE_INT_ARGB);

	      Graphics2D g2d = (Graphics2D) getGraphics();
	      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2d.setColor(Color.PINK);
	      g2d.fillOval(0, 0, 40, 40);
	}
	public Bullet(double x, double y, double xypeed, JPanel j) {
		this();
		this.x = x;
		this.y = y;
		this.yspeed = xypeed;
		this.j = j;
	}
	
	public void loop(){
		
		y -= yspeed;


		
	}
	
	public void draw(Graphics2D g2d){
		g2d.drawImage(this, (int)x,(int)y,null);
		
	}
}
