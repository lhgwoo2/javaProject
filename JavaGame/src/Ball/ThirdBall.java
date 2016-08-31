package Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GamePanel.MainPanel;
import Network.ClientComThread;

public class ThirdBall extends BufferedImage {
	
	public static double tbx;
	public static double tby;
	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public double firstyspeed;
	public JPanel j;
	public boolean tbswitch ;
	public boolean yh; 
	
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

		x += xspeed;
		y += yspeed;
		yspeed+=1;
		if(y >= j.getHeight() -220){
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

		//���� �Ѿ� �浹 Ž��
		//����� �Ѿ� Ž��
		if (MainPanel.GetDistance(ClientComThread.buxB, ClientComThread.buyB,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2B, ClientComThread.buy2B,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3B, ClientComThread.buy3B,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		
		
		//������ �Ѿ� Ž��
		if (MainPanel.GetDistance(ClientComThread.buxR, ClientComThread.buyR,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2R, ClientComThread.buy2R,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3R, ClientComThread.buy3R,x + 20, y + 20) <= 1056.25) { // 1056.25 �� 32.5*32.5
			tbswitch = true;
			x=j.getHeight()+300;
			y=j.getHeight()+300;
		}
		
		//���� �Ѿ� �浹 Ž��
		
		//ĳ���Ϳ� ���� �浹 Ž��
/*		if (MainPanel.GetDistance(MainPanel.tbx + 20, MainPanel.tby + 20, j.getWidth() / 2 + MainPanel.chx,j.getHeight() - 80) <= 20) {
			Character.isDead = true;
			Character.DeadTime = System.currentTimeMillis();
		}*/
		//ĳ���Ϳ� ���� �浹 Ž��
	}
	
	public void draw(Graphics2D g2d) {
		if(!tbswitch){
			g2d.drawImage(this, (int) x, (int) y, null);
		}

	}


}
