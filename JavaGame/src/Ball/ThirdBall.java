package Ball;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import GamePanel.LoginPanel;
import GamePanel.MainPanel;
import Network.ClientComThread;
import Network.PointData;
import SKILL.BrickBlue;
import SKILL.BrickRed;
import SKILL.ShieldBlue;
import SKILL.ShieldRed;

public class ThirdBall extends BufferedImage {

	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public double firstyspeed;
	public MainPanel jp;
	public boolean tbswitch;
	Image ballImg;
	//�Ѿ� �浹
	public boolean leftB1;
	public boolean rightB1;
	public boolean leftB2;
	public boolean rightB2;
	public boolean leftR1;
	public boolean rightR1;
	public boolean leftR2;
	public boolean rightR2;
	public boolean direction1;
	public boolean direction2;
	public boolean direction4;
	public boolean direction5;
	//�Ѿ� �浹
	
	//3�� ĳ�� ���� ��ų
	public boolean bluebrickSkill;
	public boolean bluebrickleft;
	public boolean bluebrickright;
	public boolean redbrickSkill;
	public boolean redbrickleft;
	public boolean redbrickright;
	
	//���� �浹 
	public boolean shieldcollision; //���忡 �浹�� �ѹ���!
	
	public PointData pData;
	
	public ThirdBall(MainPanel j, double sbx, double sby, double xspeed, double yspeed) {
		super(40, 40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		InputStream cheeseBall = getClass().getResourceAsStream("orangeball.png");
	      try {
	         ballImg = ImageIO.read(cheeseBall);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
		this.jp = j;
		x = sbx;
		y = sby;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.firstyspeed = yspeed;
		pData = new PointData(); 
	}

	public void loop() {

		x += xspeed;
		y += yspeed;
		yspeed += 1;
		if (y >= jp.getHeight() - 220) {
			yspeed = firstyspeed;
		}
		if (x >= jp.getWidth() - 40) {
			x = jp.getWidth() - 40;
			xspeed = -xspeed;
		}
		if (x <= 0) {
			x = 0;
			xspeed = -xspeed;
		}

		// ���� �Ѿ� �浹
		direction(ClientComThread.buxB, ClientComThread.buyB, leftB1, rightB1, direction1, "BLUE");
		direction(ClientComThread.bux2B, ClientComThread.buy2B, leftB2, rightB2, direction2, "BLUE");
		direction(ClientComThread.buxR, ClientComThread.buyR, leftR1, rightR1, direction4, "RED");
		direction(ClientComThread.bux2R, ClientComThread.buy2R, leftR2, rightR2, direction5, "RED");
		// ���� �Ѿ� �浹
		
		// �ǵ彺ų�� ���� �浹Ž��
				//���2��
				if (ClientComThread.shieldblue) {
					if (jp.GetDistance(x + 20, y + 20, ShieldBlue.bluesx + 75, ShieldBlue.bluesy + 75) <= 9025&&!shieldcollision) {
						yspeed = -yspeed;
						shieldcollision=true; //�ѹ��� !!!
					}
					shieldcollision=false;
				}
				// ����2��
				if (ClientComThread.shieldred) {
					if (jp.GetDistance(x + 20, y + 20, ShieldRed.redsx + 75, ShieldRed.redsy + 75) <= 9025&&!shieldcollision) {
						yspeed = -yspeed;
						shieldcollision=true; //�ѹ��� !!!
					}
					shieldcollision=false;
				}
				
				//��� 3�� ���� ��ų �浹
				if (ClientComThread.brickblue) {
					if (x < BrickBlue.bluebx && !bluebrickleft) {
						bluebrickSkill = true; // ���ʿ� ������ true
						bluebrickleft = true;
						bluebrickright = false;
					} else if (x > BrickBlue.bluebx && !bluebrickright) {
						bluebrickSkill = false;
						bluebrickright = true;
						bluebrickleft = false;
					}
					if(bluebrickSkill&& BrickBlue.bluebx<=x+40){ 
						xspeed = -xspeed;
					}
					if(!bluebrickSkill&& BrickBlue.bluebx+96>=x){
						xspeed = -xspeed;
					}
				}

				// ���� 3�� ���� ��ų �浹
				if (ClientComThread.brickred) {
					if (x < BrickRed.redbx && !redbrickleft) {
						redbrickSkill = true; // ���ʿ� ������ true
						redbrickleft = true;
						redbrickright = false;
					} else if (x > BrickRed.redbx && !redbrickright) {
						redbrickSkill = false;
						redbrickright = true;
						redbrickleft = false;
					}
					if(redbrickSkill&& BrickRed.redbx<=x+40){ 
						xspeed = -xspeed;
					}
					if(!redbrickSkill&& BrickRed.redbx+96>=x){
						xspeed = -xspeed;
					}
				}

	}

	public void fourthBall(MainPanel jp, double x, double y) {
		MainPanel.fthb.add(new FourthBall(jp, x + 120, y, 13, -36));
		MainPanel.fthb.add(new FourthBall(jp, x - 120, y, -13, -36));
	}

	// ���� �Ѿ� �浹 �޼ҵ�
	public void direction(double bulletx, double bullety, boolean left, boolean right, boolean direction,
			String teamColor) {
		if (x < bulletx && !left) {
			direction = true; // ���ʿ� ������ true
			left = true;
			right = false;
		} else if (x > bulletx && !right) {
			direction = false;
			right = true;
			left = false;
		}
		if (direction && bulletx <= x + 60 && bullety <= y) { // �����̸鼭,�Ѿ˿� x��ǥ
		
		 // ����Ʈ�� ������ ������.
			if(teamColor.equals("BLUE")) {
				pData.setTeamColor("BLUE"); 
				pData.setPoint(100);
			}
			else if(teamColor.equals("RED")) {
				pData.setTeamColor("RED");
				pData.setPoint(100);				
			}
			LoginPanel.gClient.sendGamePointData(pData);
			jp.sound("pop.wav");
			
			fourthBall(jp, x, y); // ����°���� �����鼭 �׹�°���� ����
		
			
			pData = null; // ������ ������

			x = jp.getHeight(); // ����°���� ������ ��ǥ�� �ʱ�ȭ
			y=-10; // ����°���� ������ ��ǥ�� �ʱ�ȭ
			tbswitch = true;
		}
		else if (!direction && bulletx >= x + 60 && bullety <= y) {
			// ����Ʈ�� ������ ������.
			if(teamColor.equals("BLUE")) {
				pData.setTeamColor("BLUE"); 
				pData.setPoint(100);
			}
			else if(teamColor.equals("RED")) {
				pData.setTeamColor("RED");
				pData.setPoint(100);				
			}

			LoginPanel.gClient.sendGamePointData(pData);
			jp.sound("pop.wav");
			
			fourthBall(jp, x, y); // ����°���� �����鼭 �׹�°���� ����

		
			pData = null; // ������ ������

			x = jp.getHeight(); // ����°���� ������ ��ǥ�� �ʱ�ȭ
			y=-10; // ����°���� ������ ��ǥ�� �ʱ�ȭ
			tbswitch = true;
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(ballImg, (int) x, (int) y, null);
	}

}
