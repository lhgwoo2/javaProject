package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Ball.*;
import Bullets.*;
import Charactor.*;
import Network.GameData;

public class MainPanel extends JPanel {

	public BufferedImage bimg;
	public Graphics2D g2d;
	public JFrame f;
	
	//블루 캐릭터 x 좌표
	public BlueCharacter1 bCharac1;		// 블루팀 1번 캐릭터 클래스
	public BlueCharacter2 bCharac2;		// 블루팀 2번 캐릭터 클래스
	public BlueCharacter3 bCharac3;		// 블루팀 3번 캐릭터 클래스
	
	//레드 캐릭터 x 좌표
	public RedCharacter1 rCharac1;		// 레드팀 1번 캐릭터 클래스
	public RedCharacter2 rCharac2;		// 레드팀 2번 캐릭터 클래스
	public RedCharacter3 rCharac3;		// 레드팀 3번 캐릭터 클래스
	
	
	//public blueCharacter3 bCharac13;		// 블루팀 1번 캐릭터 클래스
	
	//블루 총알 x좌표
	boolean blue1fb ; 			//블루팀 1번 캐릭터 처음에 총알 생성!
	boolean blue2fb ; 			//블루팀 2번 캐릭터 처음에 총알 생성!
	boolean blue3fb ; 			//블루팀 3번 캐릭터 처음에 총알 생성!
	
	//레드 총알 x좌표
	boolean red1fb ; 			//레드팀 1번 캐릭터 처음에 총알 생성!
	boolean red2fb ; 			//레드팀 2번 캐릭터 처음에 총알 생성!
	boolean red3fb ; 			//레드팀 3번 캐릭터 처음에 총알 생성!

	//현재 클라이언트의 팀컬러와 팀순번
	public String teamColor;
	public int teamNumber;
	
	public HashSet<Integer> keyCodes = new HashSet<>();
	public Timer timer;
	public GameData gData;				// 게임데이터 전송
	
	public static List<FirstBall> fb  = new ArrayList<>();		
	public static List<SecondBall> sb  = new ArrayList<>();		
	public static List<ThirdBall> tb  = new ArrayList<>();	
	
	//블루 총알
	public static List<BulletBlue1> bullet1B = new ArrayList<>();		//블루팀 1번 Bullet 배열
	public static List<BulletBlue2> bullet2B = new ArrayList<>();		//블루팀 2번 Bullet 배열
	public static List<BulletBlue3> bullet3B = new ArrayList<>();		//블루팀 3번 Bullet 배열
	
	
	// 레드 총알
	public static List<BulletBlue1> bullet1R = new ArrayList<>(); 		//레드팀 1번 Bullet 배열
	public static List<BulletBlue2> bullet2R = new ArrayList<>(); 		// 레드팀 2번 Bullet 배열
	public static List<BulletBlue3> bullet3R = new ArrayList<>(); 		// 레드팀 3번 Bullet 배열
	
	
	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;

		this.setFocusable(true);
		this.requestFocus();
		drawingMainImage();
		setFocusable(true);			// 메인패널의 포커스를 맟추어준다
		requestFocus();				// 포커스를 요청한다.
	}
	
	public void firstBall(){
		fb.add(new FirstBall(this,0,getHeight()-420,10,-26)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-420,-10,-26)); 
	}
	



	public void eventKey() {
		// 캐릭터를 생성한다. 블루팀
		bCharac1 = new BlueCharacter1(this);
		bCharac2 = new BlueCharacter2(this);
		bCharac3 = new BlueCharacter3(this);
		// 캐릭터 생성한다. 레드팀
		rCharac1 = new RedCharacter1(this);
		rCharac2 = new RedCharacter2(this);
		rCharac3 = new RedCharacter3(this);

		// 팀컬러, 팀순번 입력
		teamColor = LoginPanel.gClient.teamColor;
		teamNumber = LoginPanel.gClient.clientNumber;
		// 키보드 타이머를 줌으로써 중복된 키가 안눌리도록 한다.
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Iterator<Integer> it = keyCodes.iterator();
				while(it.hasNext()) {
					int keyCode = it.next();

					switch (keyCode) {
					case KeyEvent.VK_A:
						gData = new GameData();
						// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(-10);

						LoginPanel.gClient.sendGameData(gData);
						break;
					case KeyEvent.VK_D:
						gData = new GameData();
						// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(+10);

						LoginPanel.gClient.sendGameData(gData);
						break;
					case KeyEvent.VK_N:// 가상키 n , n를 누른경우! bullet 메소드를 부름.
						// if(!Character.isDead) bullet();
						/*if (teamColor.equals("Blue")) {
							if (teamNumber == 1) {
								bP1bullet();
							}
						}
						if (teamColor.equals("Blue")) {
							if (teamNumber == 2) {
								bP2bullet();
							}
						}*/
						
						gData = new GameData();
						// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setBulletStart(true); // 총알이 출발했다.
						LoginPanel.gClient.sendGameData(gData);

						break;
					}
				}
			}
		});

				// adapter는 내가 원하는 메소드만 사용하여 첨부할수 있다.
				// 그러나 listener는 3개의 메소드 모드 사용해야 한다.
				this.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						
						int keyCode = e.getKeyCode();
						keyCodes.add(keyCode);
						if(!timer.isRunning()) timer.start();
						// 키보드가 눌릴때 타이머가 작동함. 기능정지
					}
					
					@Override
					public void keyReleased(KeyEvent e){
						int keyCode = e.getKeyCode();
						keyCodes.remove(keyCode);
						if(timer.isRunning())timer.stop();
						//키보드가 떨어질때 타이머가 해제됨.정지해제
					}
				});
		
	}

	//MainPanel 에 메소드 시작
	
	//블루팀 1번 총알
	public void bP1bullet() { 
		if (!blue1fb) {
			bullet1B.add(new BulletBlue1((getWidth() + 65) / 2 + bCharac1.chx, getHeight() - 270, 15, this));
			blue1fb = true;
			System.out.println("blue1fb:" + "또들어왓냐");
		}
		for (int i = 0; i < bullet1B.size(); i++) {
			bullet1B.get(i).initBulletX((getWidth() + 65) / 2 + bCharac1.chx);
			bullet1B.get(i).initBulletY(getHeight() - 270);
			bullet1B.get(i).initBullet(false);
			System.out.println("blue1fb:" + "새로들어옴");
		}
	}

	// 블루팀 2번 총알
	public void bP2bullet() { 
		if (!blue2fb) {
			bullet2B.add(new BulletBlue2((getWidth() + 65) / 2 + bCharac2.chx, getHeight() - 270, 15, this));
			blue2fb = true;
		}
		for (int i = 0; i < bullet2B.size(); i++) {
			bullet2B.get(i).initBulletX((getWidth() + 65) / 2 + bCharac2.chx);
			bullet2B.get(i).initBulletY(getHeight() - 270);
			bullet2B.get(i).initBullet(false);
		}
	}
	// 블루팀 3번 총알
	public void bP3bullet() { 
		if (!blue3fb) {
			bullet3B.add(new BulletBlue3((getWidth() + 65) / 2 + bCharac3.chx, getHeight() - 270, 15, this));
			blue3fb = true;
		}
		for (int i = 0; i < bullet3B.size(); i++) {
			bullet3B.get(i).initBulletX((getWidth() + 65) / 2 + bCharac3.chx);
			bullet3B.get(i).initBulletY(getHeight() - 270);
			bullet3B.get(i).initBullet(false);
		}
	}
	
	//레드팀 총알
	
	//레드팀 1번 총알
		public void rP1bullet() { 
			if (!red1fb) {
				bullet1R.add(new BulletBlue1((getWidth() + 65) / 2 + rCharac1.chx, getHeight() - 270, 15, this));
				red1fb = true;
			}
			for (int i = 0; i < bullet1R.size(); i++) {
				bullet1R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac1.chx);
				bullet1R.get(i).initBulletY(getHeight() - 270);
				bullet1R.get(i).initBullet(false);
			}
		}

		// 레드팀 2번 총알
		public void rP2bullet() { 
			if (!red2fb) {
				bullet2R.add(new BulletBlue2((getWidth() + 65) / 2 + rCharac2.chx, getHeight() - 270, 15, this));
				red2fb = true;
			}
			for (int i = 0; i < bullet2R.size(); i++) {
				bullet2R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac2.chx);
				bullet2R.get(i).initBulletY(getHeight() - 270);
				bullet2R.get(i).initBullet(false);
			}
		}
		// 레드팀 3번 총알
		public void rP3bullet() { 
			if (!red3fb) {
				bullet3R.add(new BulletBlue3((getWidth() + 65) / 2 + rCharac3.chx, getHeight() - 270, 15, this));
				red3fb = true;
			}
			for (int i = 0; i < bullet3R.size(); i++) {
				bullet3R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac3.chx);
				bullet3R.get(i).initBulletY(getHeight() - 270);
				bullet3R.get(i).initBullet(false);
			}
		}

		
	// 객체간 거리 측정
	public static double GetDistance(double x1, double y1, double x2, double y2) { // 거리
		return (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1);
	}


	public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}
	}

	public void drawingPlayImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback1.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("게임화면 로드 성공");
		} catch (IOException e) {
			System.out.println("게임화면 로드 실패");
			e.printStackTrace();
		}
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1600,900,null);
		
		//캐릭터를 그린는 부분 시작
		//블루팀
		if(bCharac1!=null)bCharac1.draw(g2d);
		if(bCharac2!=null)bCharac2.draw(g2d);
		if(bCharac3!=null)bCharac3.draw(g2d);
		//레드팀
		if(rCharac1!=null)rCharac1.draw(g2d);
		if(rCharac2!=null)rCharac2.draw(g2d);
		if(rCharac3!=null)rCharac3.draw(g2d);
		//캐릭터 그리는 부분 끝!
		
		//총알 그리는 부분 시작
		//블루
		if (bullet1B.size() > 0) for (int i = 0; i < bullet1B.size(); i++)  bullet1B.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	
		if (bullet2B.size() > 0) for (int i = 0; i < bullet2B.size(); i++)  bullet2B.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	
		if (bullet3B.size() > 0) for (int i = 0; i < bullet3B.size(); i++)  bullet3B.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	

		//레드
		if (bullet1R.size() > 0)  for (int i = 0; i < bullet1R.size(); i++)  bullet1R.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	
		if (bullet2R.size() > 0) for (int i = 0; i < bullet2R.size(); i++)  bullet2R.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	
		if (bullet3R.size() > 0) for (int i = 0; i < bullet3R.size(); i++)  bullet3R.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.	
		//총알 그리는 부분 끝
		
		//볼 그리는 부분
		for(int i =0;i<fb.size();i++) if(!fb.get(i).fbswitch) fb.get(i).draw(g2d);	
		for(int i =0;i<sb.size();i++) if(!sb.get(i).sbswitch) sb.get(i).draw(g2d);	
		for(int i =0;i<tb.size();i++) if(!tb.get(i).tbswitch) tb.get(i).draw(g2d);	

		
	}
	


}
