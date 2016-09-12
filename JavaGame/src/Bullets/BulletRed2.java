package Bullets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BulletRed2 extends BufferedImage implements Bullets{

	public Image img;
	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public JPanel j;
	public boolean drawbool;
	public int a = 0;
	public int b = 0;
	public int c = 58;
	public int d = 580; //584에 8번


	public BulletRed2() {
		super(58, 580, BufferedImage.TYPE_INT_ARGB);

		InputStream is = getClass().getResourceAsStream("bulletred.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public BulletRed2(double x, double y, double yspeed, JPanel j) {
		this();
		this.x = x;
		this.y = y;
		this.yspeed = yspeed;
		this.j = j;
	}
	
	public void loop() { // x좌표는 캐릭터의 x 좌표임.
		if (y <= 0) {
			drawbool = true;
			y=j.getHeight();
		}else if(!drawbool) y -= yspeed;
	}
	
	public void draw(Graphics2D g2d){
		if (!drawbool){
			g2d.drawImage(img, (int)x, (int)y,(int)x+50,(int)y+580,a,b,c,d,null);
		}
	}
	public void initBullet(boolean bool){
		 this.drawbool=bool;
	}
	public boolean getBulletBool(){
		return this.drawbool;
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
