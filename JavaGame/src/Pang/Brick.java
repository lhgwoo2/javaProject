package Pang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Brick extends BufferedImage {
	//1
	double x;
	double y;
	double w;
	double h;
	JPanel j;
	
	public Brick(JPanel j,int w,int h,double x,double y,Color color) {
		
		super(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(color);
		g2d.fillRect(0, 0, w, h);
		this.j = j;
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;

	}
	
	public void draw(Graphics2D g2d) {
		g2d.drawImage(this, (int) x, (int) y, null);

	}

}
