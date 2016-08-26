package Pang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ThirdBall extends BufferedImage {
	
	double x;
	double y;
	double xspeed;
	double yspeed;
	double firstyspeed;
	JPanel j;
	boolean tbswitch ;
	boolean yh; 
	
	public ThirdBall(JPanel j,double sbx,double sby,double xspeed,double yspeed) {
		super(40, 40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.green);
		g2d.fillOval(0, 0, 40, 40);
		this.j = j;
		x = sbx;  
		y = sby;				
		this.xspeed = xspeed;			
		this.yspeed = yspeed;			
		this.firstyspeed = yspeed;
	}

	public void loop() {

		if(!yh){
			if(y<j.getHeight()-40){
				yspeed=-yspeed-3;
				yh=true;
			}	
		}
		x += xspeed;
		y += yspeed;
		yspeed+=1;
		if(y >= j.getHeight() -40){
			yspeed=firstyspeed;
		}
		if (x >= j.getWidth() - 40) { 
			x = j.getWidth() - 40;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0;
			xspeed = -xspeed;
		}
		//공와 총알 충돌 탐지
		MainPanel.tbx =x;
		MainPanel.tby =y;
		if (MainPanel.GetDistance(MainFrame.bux, MainFrame.buy,x + 20, y + 20) <= 32.5) {
			tbswitch = true;
		}
		//공와 총알 충돌 탐지
		
		//캐릭터와 볼의 충돌 탐지
		if (MainPanel.GetDistance(MainPanel.tbx + 20, MainPanel.tby + 20, j.getWidth() / 2 + MainPanel.chx,j.getHeight() - 80) <= 20) {
			Character.isDead = true;
			Character.DeadTime = System.currentTimeMillis();
		}
		//캐릭터와 볼의 충돌 탐지
	}
	
	public void draw(Graphics2D g2d) {
		if(!tbswitch){
			g2d.drawImage(this, (int) x, (int) y, null);
		}

	}


}
