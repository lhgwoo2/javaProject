package Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

import GamePanel.MainPanel;
import Network.ClientComThread;

public class SecondBall extends BufferedImage {
	Random rd = new Random();
	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public double firstyspeed;
	public JPanel jp;
	public boolean sbswitch ;

	public boolean yh; 

	public SecondBall(JPanel j,double fbx,double fby,double xspeed,double yspeed) {
		super(80, 80, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.GRAY);
		g2d.fillOval(0, 0, 80, 80);
		this.jp = j;
		x = fbx;  				//FirstBall의 위치로 잡아 줘야 됨
		y = fby;				//FirstBall의 위치로 잡아 줘야 됨
		this.xspeed = xspeed;			//FirstBall보다 약간 빨라야됨
		this.yspeed = yspeed;			//FirstBall보다 약간 빨라야됨
		this.firstyspeed = yspeed;
	}

	public void loop() {
/*		if(!yh){
			if(y<jp.getHeight()-660){
				yspeed=-yspeed;
				yh=true;
			}	
		}*/
		x += xspeed;
		y += yspeed;
		yspeed+=1;
		if(y >= jp.getHeight() -270){
			yspeed=firstyspeed-4;
			
		}
		if (x >= jp.getWidth() - 80) { //FirstBall보다 약간 빨라야됨 -500 이라는 숫자는 FirstBall의 크기가 포함된것 각 클래스 볼에 맞게 해야됨
			x = jp.getWidth() - 80;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0;
			xspeed = -xspeed;
		}
		//공와 총알 충돌 탐지
		if (MainPanel.GetDistance(ClientComThread.bux, ClientComThread.buy,x + 40, y + 40) <= 52.5) {
			thirdBall(jp,x,y);
			x=jp.getHeight()+300;
			y=jp.getHeight()+300;
			sbswitch = true;
		}
		//공와 총알 충돌 탐지
		
		//캐릭터와 볼의 충돌 탐지
/*		if (MainPanel.GetDistance(MainPanel.sbx+40, MainPanel.sby+40, j.getWidth() / 2 + MainPanel.chx, j.getHeight()-80) <=35) { 
			Character.isDead=true;
			Character.DeadTime = System.currentTimeMillis();
		}*/
		//캐릭터와 볼의 충돌 탐지
	}
	
	public void thirdBall(JPanel jp,double x, double y){
		MainPanel.tb.add(new ThirdBall(jp,x+120,y,13,-32));
		MainPanel.tb.add(new ThirdBall(jp,x-120,y,-13,-32)); 
	}


	public void draw(Graphics2D g2d) {
		if(!sbswitch){
			g2d.drawImage(this, (int) x, (int) y, null);
		}

	}

}
