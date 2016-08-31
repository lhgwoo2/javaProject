package Pang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;


public class Ball extends BufferedImage{
	
	double x;
	double y;
	double xspeed;
	double yspeed;
	JPanel j;
	boolean bool ;

	public Ball(int w,int h,double x,double y,int xspeed,int yspeed,Color color,JPanel j) {
		super(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillOval(0, 0, w, h);
		this.j = j;
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}

	public void loop() {
		x += xspeed;
		y += yspeed;

		if (x >= j.getWidth() - 500) {
			System.out.println(x + "    " + y);
			x = j.getWidth() - 500;
			xspeed = -xspeed;
		}
		if (y >= j.getHeight() - 500) {
			y = j.getHeight() - 500;
			yspeed = -yspeed;
		}
		if (y <= 0) {
			y = 0;
			yspeed = -yspeed;
		}
		if (x <= 0) {
			x = 0;
			xspeed = -xspeed;
		}
		
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(this, (int) x, (int) y, null);

	}

}
