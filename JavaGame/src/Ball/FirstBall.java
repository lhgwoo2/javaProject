package Ball;
import java.awt.Graphics2D;
import java.awt.Image;
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

public class FirstBall extends BufferedImage {
	
	public double x;
	public double y;
	public double xspeed;
	public double yspeed;
	public double firstyspeed;
	public MainPanel jp;
	public boolean fbswitch;
	Image ballImg;
	
	//총알 충돌
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
	//총알 충돌
	
	//3번 캐릭 벽돌 스킬
	public boolean bluebrickSkill;
	public boolean bluebrickleft;
	public boolean bluebrickright;
	public boolean redbrickSkill;
	public boolean redbrickleft;
	public boolean redbrickright;
	
	//쉴드 충돌 
	public boolean shieldcollision; //쉴드에 충돌을 한번만!

	
	public PointData pData;
	//
	
	public FirstBall(MainPanel j,double x,double y,double xspeed,double yspeed) {
		super(120, 120, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) getGraphics();
		InputStream cheeseBall = getClass().getResourceAsStream("cheeseball.png");
	      try {
	         ballImg = ImageIO.read(cheeseBall);
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      this.jp = j;
		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		this.firstyspeed = yspeed;
		pData = new PointData();
		pData.setPoint(100);
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
		
		//공과 총알 충돌
		direction(ClientComThread.buxB,ClientComThread.buyB,leftB1,rightB1,direction1,"BLUE");	
		direction(ClientComThread.bux2B,ClientComThread.buy2B,leftB2,rightB2,direction2,"BLUE");		
		direction(ClientComThread.buxR,ClientComThread.buyR,leftR1,rightR1,direction4,"RED");
		direction(ClientComThread.bux2R,ClientComThread.buy2R,leftR2,rightR2,direction5,"RED");
		//공과 총알 충돌	
		
		// 실드스킬과 공의 충돌탐지
		//블루2번
		if (ClientComThread.shieldblue) {
			if (jp.GetDistance(x+60,y+60,ShieldBlue.bluesx+75,ShieldBlue.bluesy+75) <= 18800 )
			{
				xspeed = -xspeed;
				yspeed = -yspeed;
			}
		}
		//레드2번
		if (ClientComThread.shieldred) {
			if (jp.GetDistance(x + 60, y + 60, ShieldRed.redsx + 75, ShieldRed.redsy + 75) <= 18800) {
				xspeed = -xspeed;
				yspeed = -yspeed;
			}
		}
		
		// 실드스킬과 공의 충돌탐지
		//블루2번
		if (ClientComThread.shieldblue) {
			if (jp.GetDistance(x+60,y+60,ShieldBlue.bluesx+75,ShieldBlue.bluesy+75) <= 18800&&!shieldcollision )
			{
				yspeed = -yspeed;
				shieldcollision=true; //한번만 !!!
			}else shieldcollision=false;		
		}
		//레드2번
		if (ClientComThread.shieldred) {
			if (jp.GetDistance(x + 60, y + 60, ShieldRed.redsx + 75, ShieldRed.redsy + 75) <= 18800&&!shieldcollision) 
			{
				yspeed = -yspeed;
				shieldcollision=true; //한번만 !!!
			}else shieldcollision=false;
		}
		
		//블루 3번 벽돌 스킬 충돌
		if (ClientComThread.brickblue) {
			if (x < BrickBlue.bluebx && !bluebrickleft) {
				bluebrickSkill = true; // 왼쪽에 있으면 true
				bluebrickleft = true;
				bluebrickright = false;
			} else if (x > BrickBlue.bluebx && !bluebrickright) {
				bluebrickSkill = false;
				bluebrickright = true;
				bluebrickleft = false;
			}
			if(bluebrickSkill&& BrickBlue.bluebx<=x+120){ 
				xspeed = -xspeed;
			}
			if(!bluebrickSkill&& BrickBlue.bluebx+96>=x){
				xspeed = -xspeed;
			}
		}

		// 레드 3번 벽돌 스킬 충돌
		if (ClientComThread.brickred) {
			if (x < BrickRed.redbx && !redbrickleft) {
				redbrickSkill = true; // 왼쪽에 있으면 true
				redbrickleft = true;
				redbrickright = false;
			} else if (x > BrickRed.redbx && !redbrickright) {
				redbrickSkill = false;
				redbrickright = true;
				redbrickleft = false;
			}
			if(redbrickSkill&& BrickRed.redbx<=x+120){ 
				xspeed = -xspeed;
			}
			if(!redbrickSkill&& BrickRed.redbx+96>=x){
				xspeed = -xspeed;
			}
		}

	}
	
	public void secondBall(MainPanel jp,double x, double y){
		MainPanel.sb.add(new SecondBall(jp,x+120,y,13,-28));
		MainPanel.sb.add(new SecondBall(jp,x-120,y,-13,-28)); 
	}
	//공과 총알 충돌 메소드
	public void direction(double bulletx,double bullety,boolean left,boolean right,boolean direction, String teamColor){
		if( x < bulletx&&!left) {
			direction = true; //왼쪽에 있으면 true
			left=true;
			right=false;
		}else if( x > bulletx&&!right) {
			direction=false;
			right=true;
			left=false;
		}	
		if(direction&& bulletx<=x+60 && bullety<=y){ //왼쪽이면서,총알에 x좌표 <=볼에 x좌표 && 
			//  포인트를 서버로 보낸다.
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
	
			secondBall(jp,x,y); 		//첫번째볼이 터지면서 두번째볼을 생성
		
			pData = null;	// 데이터 버리기 
			x=jp.getHeight(); //첫번째볼이 터지면 좌표를 초기화
			y=-10; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
		else if(!direction&&bulletx>=x+60&&bullety<=y){
			//  포인트를 서버로 보낸다.
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
			secondBall(jp,x,y); 		//첫번째볼이 터지면서 두번째볼을 생성
			
			pData = null;	// 데이터 버리기 
			x=jp.getHeight(); //첫번째볼이 터지면 좌표를 초기화
			y=-10; //첫번째볼이 터지면 좌표를 초기화
			fbswitch = true;
		}
	}

	public void draw(Graphics2D g2d) {
		g2d.drawImage(ballImg, (int) x, (int) y, null);
	}
	
}
