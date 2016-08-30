package Ball;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import GamePanel.MainPanel;
import Network.ClientComThread;
import Ball.SecondBall;

import javax.swing.JPanel;

public class FirstBall extends BufferedImage {

	public double x;
	public double y;
	public double bx; //벽돌x
	public double by; //벽돌y
	public double bw; //벽돌 가로 크기
	public double bh; //벽돌 세로 크기
	public double xspeed;
	public double yspeed;
	public double firstyspeed;
	public JPanel jp;
	public boolean fbswitch  ;

	public FirstBall(JPanel j,double x,double y,double xspeed,double yspeed) {
		super(120, 120, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		g2d.fillOval(0, 0, 120, 120);
		this.jp = j;
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.firstyspeed = yspeed;
	}

	public void loop() {
		//공 
		x += xspeed;
		y += yspeed;
		yspeed += 1;
		if (y >= jp.getHeight() - 310) {
			yspeed = firstyspeed - 8;
		}
		if (x >= jp.getWidth() - 120) {
			x = jp.getWidth() - 120;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0; xspeed = -xspeed;
		}
		//공
		
		//공와 총알 충돌 탐지
		//블루팀 총알과 충돌
		if (MainPanel.GetDistance(ClientComThread.buxB, ClientComThread.buyB,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); 		//첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2B, ClientComThread.buy2B,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); 		//첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3B, ClientComThread.buy3B,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); //첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		
		
		
		//레드팀 총알과 충돌
		if (MainPanel.GetDistance(ClientComThread.buxR, ClientComThread.buyR,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); //첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2R, ClientComThread.buy2R,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); //첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3R, ClientComThread.buy3R,x + 60, y + 60) <= 6400) { //6400 은 80*80  
			secondBall(jp,x,y); //첫번째볼이 터지면서 두번째볼을 생성
			x=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			y=jp.getHeight()+300; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		//공와 총알 충돌 탐지
		
		
		
		//캐릭터와 볼의 충돌 탐지
/*		if (MainPanel.GetDistance(MainPanel.fbx+60, MainPanel.fby+60, j.getWidth() / 2 + MainPanel.chx, j.getHeight()-80) <=58) { 
			Character.isDead=true;
			Character.DeadTime = System.currentTimeMillis();
		}*/
		//캐리겉와 볼의 충돌 탐지
		
		//벽돌과 볼 충돌탐지
		
		
		
		//벽돌과 볼 충돌탐지
		
	}
	
	public void secondBall(JPanel jp,double x, double y){
		MainPanel.sb.add(new SecondBall(jp,x+120,y,13,-28));
		MainPanel.sb.add(new SecondBall(jp,x-120,y,-13,-28)); 
	}

	public void draw(Graphics2D g2d) {
		if(!fbswitch){
			g2d.drawImage(this, (int) x, (int) y, null);
		}

	}
	
}
