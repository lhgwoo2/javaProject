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
	
	public static double fbx;
	public static double fby;
	public double x;
	public double y;
	public double bx; //����x
	public double by; //����y
	public double bw; //���� ���� ũ��
	public double bh; //���� ���� ũ��
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
		//�� 
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
		//��

		//���� �Ѿ� �浹 Ž��
		//����� �Ѿ˰� �浹
		if (MainPanel.GetDistance(ClientComThread.buxB, ClientComThread.buyB,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); 		//ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2B, ClientComThread.buy2B,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); 		//ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3B, ClientComThread.buy3B,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); //ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		
		
		
		//������ �Ѿ˰� �浹
		if (MainPanel.GetDistance(ClientComThread.buxR, ClientComThread.buyR,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); //ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux2R, ClientComThread.buy2R,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); //ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		if (MainPanel.GetDistance(ClientComThread.bux3R, ClientComThread.buy3R,x + 60, y + 60) <= 6400) { //6400 �� 80*80  
			secondBall(jp,x,y); //ù��°���� �����鼭 �ι�°���� ����
			x=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			y=jp.getHeight()+300; //ù��°���� ������ ��ǥ�� �ʱ�ȭ
			fbswitch = true;
		}
		//���� �Ѿ� �浹 Ž��
		
		
		
		//ĳ���Ϳ� ���� �浹 Ž��
/*		if (MainPanel.GetDistance(MainPanel.fbx+60, MainPanel.fby+60, j.getWidth() / 2 + MainPanel.chx, j.getHeight()-80) <=58) { 
			Character.isDead=true;
			Character.DeadTime = System.currentTimeMillis();
		}*/
		//ĳ���ѿ� ���� �浹 Ž��
		
		//������ �� �浹Ž��
		
		
		
		//������ �� �浹Ž��
		
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
