package GamePanel;

import java.awt.Color;
import java.awt.Font;
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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Ball.FirstBall;
import Ball.FourthBall;
import Ball.SecondBall;
import Ball.ThirdBall;
import Bullets.BulletBlue1;
import Bullets.BulletBlue2;
import Bullets.BulletRed1;
import Bullets.BulletRed2;
import Charactor.BlueCharacter1;
import Charactor.BlueCharacter2;
import Charactor.BlueCharacter3;
import Charactor.RedCharacter1;
import Charactor.RedCharacter2;
import Charactor.RedCharacter3;
import Network.ClientComThread;
import Network.GameData;
import SKILL.BrickBlue;
import SKILL.BrickRed;
import SKILL.ShieldBlue;
import SKILL.ShieldRed;
import SKILL.SnowBlue;
import SKILL.SnowRed;
import SKILL.SpeedBlue;
import SKILL.SpeedRed;



public class MainPanel extends JPanel {

	public BufferedImage bimg;
	public Graphics2D g2d;
	public MainFrame f;
	public GameDataPanel gdp; // 게임정보패널
	public int keyCode ;
	public Clip clip;
	public Clip mainclip;
	public JLabel label;
	public JLabel labellabel;


	// 블루 캐릭터 x 좌표
	public BlueCharacter1 bCharac1; // 블루팀 1번 캐릭터 클래스
	public BlueCharacter2 bCharac2; // 블루팀 2번 캐릭터 클래스
	public BlueCharacter3 bCharac3; // 블루팀 3번 캐릭터 클래스

	// 레드 캐릭터 x 좌표
	public RedCharacter1 rCharac1; // 레드팀 1번 캐릭터 클래스
	public RedCharacter2 rCharac2; // 레드팀 2번 캐릭터 클래스
	public RedCharacter3 rCharac3; // 레드팀 3번 캐릭터 클래스

	//내자신 화살표
	public Arrow arrow;

	// 블루 총알 x좌표
	boolean blue1fb; // 블루팀 1번 캐릭터 처음에 총알 생성!
	boolean blue2fb; // 블루팀 2번 캐릭터 처음에 총알 생성!
	boolean blue3fb; // 블루팀 3번 캐릭터 처음에 총알 생성!

	// 레드 총알 x좌표
	boolean red1fb; // 레드팀 1번 캐릭터 처음에 총알 생성!
	boolean red2fb; // 레드팀 2번 캐릭터 처음에 총알 생성!
	boolean red3fb; // 레드팀 3번 캐릭터 처음에 총알 생성!

	// 현재 클라이언트의 팀컬러와 팀순번
	public String teamColor;
	public int teamNumber;

	public HashSet<Integer> keyCodes = new HashSet<>();
	public static Timer timer;
	public GameData gData; // 게임데이터 전송

	public static List<FirstBall> fb = new ArrayList<>();
	public static List<SecondBall> sb = new ArrayList<>();
	public static List<ThirdBall> tb = new ArrayList<>();
	public static List<FourthBall> fthb = new ArrayList<>();

	// 블루 총알
	public static List<BulletBlue1> bullet1B = new ArrayList<>(); // 블루팀 1번					
	public static List<BulletBlue2> bullet2B = new ArrayList<>(); // 블루팀 2번														
														

	// 레드 총알
	public static List<BulletRed1> bullet1R = new ArrayList<>(); // 레드팀 1번
	public static List<BulletRed2> bullet2R = new ArrayList<>(); // 레드팀 2번
	
	// 캐릭터 좌우 이동
	public boolean left;
	public boolean right;
	
	//스킬 
	public long snow;
	//스킬 쿨타임
	public long blueSpeedTime;
	public long redSpeedTime;
	public long blueShieldTime;
	public long redShieldTime;
	public long blueSnowTime;
	public long redSnowTime;
	public long blueBrickTime;
	public long redBrickTime;

	
	
	// 오브젝트 풀
	public static List<GameData> pointDataPool;	// 포인트를 위한 객체
	public List<GameData> gDataPool;
	// 오브젝트 풀의 사용을 위한 인덱스 처리
	public int listIndex ;

	public MainPanel(MainFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;
		arrow=new Arrow(); //내자신을 알리는 화살표
		

		//블루팀 아이디 표시
		label = new JLabel();
		label.setFont(new Font("맑은 고딕",Font.PLAIN,24));
		label.setBounds(210, 10, 700, 30);
		label.setForeground(Color.BLUE);
		add(label);
		
		//레드팀 아이디 표시
		labellabel = new JLabel();
		labellabel.setFont(new Font("맑은 고딕",Font.PLAIN,24));
		labellabel.setBounds(210, 45, 700, 35);
		labellabel.setForeground(Color.RED);
		add(labellabel);

		backgroundsound("loadingSound.wav",true);
		
		this.setFocusable(true);
		this.requestFocus();
		drawingMainImage();
		setFocusable(true); // 메인패널의 포커스를 맟추어준다
		requestFocus(); // 포커스를 요청한다.

		//게임 데이터의 재사용
		gDataPool = new ArrayList<>();
		for(int i=0;i<6;i++)gDataPool.add(new GameData());
		
		//포인트 전송을 위한 객체 생성
		pointDataPool = new ArrayList<>();
		for(int i=0;i<10;i++)pointDataPool.add(new GameData());
				
		
	}

	public void firstBall() {
		fb.add(new FirstBall(this, 300, -13500, 10, -26));
		fb.add(new FirstBall(this, getWidth()-300, -13500, -10, -26));
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
		timer = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Integer> it = keyCodes.iterator();
				//얼음스킬
				if(teamColor.equals("BLUE")&&ClientComThread.snowred){
					snow=ClientComThread.snowTimered;
				}else if(teamColor.equals("RED")&&ClientComThread.snowblue){
					snow=ClientComThread.snowTimeblue;
				}
				if(System.currentTimeMillis()>snow+2000){
					while (it.hasNext()) {						
						if(listIndex==gDataPool.size()-1) listIndex=0;
						int keyCode = it.next();
						switch (keyCode) {
						case KeyEvent.VK_A:
							// 왼쪽 이동 캐릭터 그림파일 위치 이동
							// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
						if (teamNumber == 1) {
							if (teamColor.equals("BLUE")&& System.currentTimeMillis() < ClientComThread.speedTimeblue + 5000) {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(-20);
								gDataPool.get(listIndex).setLeftMove(true);
							}else if (teamColor.equals("RED")&& System.currentTimeMillis() < ClientComThread.speedTimered + 5000) {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(-20);
								gDataPool.get(listIndex).setLeftMove(true);
							} else {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(-10);
								gDataPool.get(listIndex).setLeftMove(true);
							}
						} else // 나머지 번호일 경우
						{
							gDataPool.get(listIndex).setTeamColor(teamColor);
							gDataPool.get(listIndex).setTeamNum(teamNumber);
							gDataPool.get(listIndex).setChx(-10);
							gDataPool.get(listIndex).setLeftMove(true);
						}

						LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
						break;
						
						case KeyEvent.VK_D:
							// 오른쪽 이동 캐릭터 그림파일 위치 이동
							// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
						if (teamNumber == 1) {
							if (teamColor.equals("BLUE")&& System.currentTimeMillis() < ClientComThread.speedTimeblue + 5000) {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(+20);
								gDataPool.get(listIndex).setRightMove(true);

							} else if (teamColor.equals("RED")&& System.currentTimeMillis() < ClientComThread.speedTimered + 5000) {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(+20);
								gDataPool.get(listIndex).setRightMove(true);
							} else {
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setChx(+10);
								gDataPool.get(listIndex).setRightMove(true);
							}
						} else {
							gDataPool.get(listIndex).setTeamColor(teamColor);
							gDataPool.get(listIndex).setTeamNum(teamNumber);
							gDataPool.get(listIndex).setChx(+10);
							gDataPool.get(listIndex).setRightMove(true);
						}

						LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
						break;

						case KeyEvent.VK_N:// 가상키 n , n를 누른경우! bullet 메소드를 부름.
							// 팀 색과 팀 순번 전송, 캐릭터의 좌표전송
							if(teamNumber!=3){
								gDataPool.get(listIndex).setTeamColor(teamColor);
								gDataPool.get(listIndex).setTeamNum(teamNumber);
								gDataPool.get(listIndex).setBulletStart(true); // 총알이 출발했다.
								LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
								break;
							}
							break;
							
						case KeyEvent.VK_M:
							if(teamNumber==1){ 	//1번 캐릭터스킬 스피드
								if(teamColor.equals("BLUE")&&System.currentTimeMillis()>blueSpeedTime+10000){ //10000== 5초임
									sound("speed2.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setSpeedblue(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									blueSpeedTime = System.currentTimeMillis();
									break;
								}else if(teamColor.equals("RED")&&System.currentTimeMillis()>redSpeedTime+10000){//10000== 5초임
									sound("speed2.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setSpeedred(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									redSpeedTime = System.currentTimeMillis();
									break;
								}
								break;							
							}
							if(teamNumber==2){ 	//2번 캐릭터스킬 쉴드						
								if(teamColor.equals("BLUE")&&System.currentTimeMillis()>blueShieldTime+10000){//10000== 5초임
									sound("shield.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setShieldblue(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									blueShieldTime = System.currentTimeMillis();
									break;
								}else if(teamColor.equals("RED")&&System.currentTimeMillis()>redShieldTime+10000){//10000== 5초임
									sound("shield.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setShieldred(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									redShieldTime = System.currentTimeMillis();
									break;
								}
								break;
							}
							if(teamNumber==3){ 	//3번 캐릭터스킬 얼음							
								if(teamColor.equals("BLUE")&&System.currentTimeMillis()>blueSnowTime+15000){//15000== 10초임
									sound("freeze.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setSnowblue(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									blueSnowTime = System.currentTimeMillis();
									break;
								}else if(teamColor.equals("RED")&&System.currentTimeMillis()>redSnowTime+15000){//15000== 10초임
									sound("freeze.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setSnowred(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									redSnowTime = System.currentTimeMillis();
									break;
								}
								break;
							}
							break;
						case KeyEvent.VK_K: //3번 캐릭터 스킬 벽돌 생성
							if(teamNumber==3){ 	//3번 캐릭터스킬							
								if(teamColor.equals("BLUE")&&System.currentTimeMillis()>blueBrickTime+12000){ //12000== 7초임
									sound("brick.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setBrickblue(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									blueBrickTime = System.currentTimeMillis();
									break;
								}else if(teamColor.equals("RED")&&System.currentTimeMillis()>redBrickTime+12000){ //12000== 7초임
									sound("brick.wav");
									gDataPool.get(listIndex).setTeamColor(teamColor);
									gDataPool.get(listIndex).setTeamNum(teamNumber);
									gDataPool.get(listIndex).setBrickred(true);
									LoginPanel.gClient.sendGameData(gDataPool.get(listIndex));
									redBrickTime = System.currentTimeMillis();
									break;
								}
								break;
							}
							break;
						}
						gDataPool.get(listIndex).setBulletStart(false);
						gDataPool.get(listIndex).setChx(0);
						gDataPool.get(listIndex).setLeftMove(false);
						gDataPool.get(listIndex).setRightMove(false);
						gDataPool.get(listIndex).setShieldblue(false);
						gDataPool.get(listIndex).setShieldred(false);
						gDataPool.get(listIndex).setSpeedblue(false);
						gDataPool.get(listIndex).setSpeedred(false);
						gDataPool.get(listIndex).setSnowblue(false);
						gDataPool.get(listIndex).setSnowred(false);
						gDataPool.get(listIndex).setBrickblue(false);
						gDataPool.get(listIndex).setBrickred(false);
						listIndex++;
					}
				}
			}
		});

		// adapter는 내가 원하는 메소드만 사용하여 첨부할수 있다.
		// 그러나 listener는 3개의 메소드 모드 사용해야 한다.
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				keyCode = e.getKeyCode();
				keyCodes.add(keyCode);
				if (!timer.isRunning())
					timer.start();
				// 키보드가 눌릴때 타이머가 작동함. 기능정지
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyCode = e.getKeyCode();
				keyCodes.remove(keyCode);
				if (timer.isRunning())
					timer.stop();
				// 키보드가 떨어질때 타이머가 해제됨.정지해제
			}
		});
	}

	// MainPanel 에 메소드 시작

	// 블루팀 1번 총알
	public void bP1bullet() {
		if (!blue1fb) {
			bullet1B.add(new BulletBlue1((getWidth() + 65) / 2 + bCharac1.chx, getHeight() - 270, 15, this));
			blue1fb = true;
		}
		for (int i = 0; i < bullet1B.size(); i++) {
			bullet1B.get(i).initBulletX((getWidth() + 65) / 2 + bCharac1.chx);
			bullet1B.get(i).initBulletY(getHeight() - 270);
			bullet1B.get(i).initBullet(false);
		}
		sound("gunFinal.wav");
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
		sound("gunFinal.wav");
	}

	// 레드팀 총알

	// 레드팀 1번 총알
	public void rP1bullet() {
		if (!red1fb) {
			bullet1R.add(new BulletRed1((getWidth() + 65) / 2 + rCharac1.chx, getHeight() - 270, 15, this));
			red1fb = true;
		}
		for (int i = 0; i < bullet1R.size(); i++) {
			bullet1R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac1.chx);
			bullet1R.get(i).initBulletY(getHeight() - 270);
			bullet1R.get(i).initBullet(false);
		}
		sound("gunFinal.wav");
	}

	// 레드팀 2번 총알
	public void rP2bullet() {
		if (!red2fb) {
			bullet2R.add(new BulletRed2((getWidth() + 65) / 2 + rCharac2.chx, getHeight() - 270, 15, this));
			red2fb = true;
		}
		for (int i = 0; i < bullet2R.size(); i++) {
			bullet2R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac2.chx);
			bullet2R.get(i).initBulletY(getHeight() - 270);
			bullet2R.get(i).initBullet(false);
		}
		sound("gunFinal.wav");
	}

	public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("main.png");
		try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}
	}

	public void drawingPlayImage() {
		InputStream is = getClass().getResourceAsStream("pangpangback2.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("게임화면 로드 성공");
		} catch (IOException e) {
			System.out.println("게임화면 로드 실패");
			e.printStackTrace();
		}
	}
	
	// 객체간 거리 측정
	public  double GetDistance(double x1, double y1, double x2, double y2) { // 거리
		return (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1);
	}
	
	//사운드
	public void sound(String sound){ 
		java.net.URL soundURL = getClass().getResource(sound);
		AudioInputStream audioInput;
		try {
			audioInput = AudioSystem.getAudioInputStream(soundURL);
			DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInput);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void mainsound(String sound){ 
		java.net.URL soundURL = getClass().getResource(sound);
		AudioInputStream audioInput;
		try {
			audioInput = AudioSystem.getAudioInputStream(soundURL);
			DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());
			mainclip = (Clip) AudioSystem.getLine(info);
			mainclip.open(audioInput);
			mainclip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void backgroundsound(String file, boolean Loop) {

		java.net.URL soundURL = getClass().getResource(file);
		AudioInputStream audioInput;
		try {
			audioInput = AudioSystem.getAudioInputStream(soundURL);
			DataLine.Info info = new DataLine.Info(Clip.class, audioInput.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInput);
			clip.start();
			if (Loop)
				clip.loop(-1);
			// Loop 값이true면 사운드재생을무한반복시킵니다.
			// false면 한번만재생시킵니다.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.drawImage(bimg, 0, 0, 1600, 900, null);

		// 캐릭터를 그린는 부분 시작
		// 블루팀
		if (bCharac1 != null) bCharac1.draw(g2d);
		if (bCharac2 != null) bCharac2.draw(g2d);
		if (bCharac3 != null) bCharac3.draw(g2d);
		// 레드팀
		if (rCharac1 != null) rCharac1.draw(g2d);
		if (rCharac2 != null) rCharac2.draw(g2d);
		if (rCharac3 != null) rCharac3.draw(g2d);
		// 캐릭터 그리는 부분 끝!
		
		//화살표

		if (teamNumber == 1 && teamColor.equals("BLUE")) {
			arrow.x = BlueCharacter1.x + 30;
			arrow.y = BlueCharacter1.y - 70;
		} else if (teamNumber == 2 && teamColor.equals("BLUE")) {
			arrow.x = BlueCharacter2.x + 30;
			arrow.y = BlueCharacter2.y - 70;
		} else if (teamNumber == 3 && teamColor.equals("BLUE")) {
			arrow.x = BlueCharacter3.x + 30;
			arrow.y = BlueCharacter3.y - 70;
		} else if (teamNumber == 1 && teamColor.equals("RED")) {
			arrow.x = RedCharacter1.x + 30;
			arrow.y = RedCharacter1.y - 70;
		} else if (teamNumber == 2 && teamColor.equals("RED")) {
			arrow.x = RedCharacter2.x + 30;
			arrow.y = RedCharacter2.y - 70;
		} else if (teamNumber == 3 && teamColor.equals("RED")) {
			arrow.x = RedCharacter3.x + 30;
			arrow.y = RedCharacter3.y - 70;
		}
		
		if(arrow.x!=0) arrow.draw(g2d);

		//내자신 화살표

		// 총알 그리는 부분 시작
		// 블루
		for (int i = 0; i < bullet1B.size(); i++) bullet1B.get(i).draw(g2d); 
		for (int i = 0; i < bullet2B.size(); i++) bullet2B.get(i).draw(g2d);  
		// 레드
		for (int i = 0; i < bullet1R.size(); i++) bullet1R.get(i).draw(g2d); 
		for (int i = 0; i < bullet2R.size(); i++) bullet2R.get(i).draw(g2d); 
		// 총알 그리는 부분 끝

		// 볼 그리는 부분
		for (int i = 0; i < fb.size(); i++) if (!fb.get(i).fbswitch) fb.get(i).draw(g2d);
		for (int i = 0; i < sb.size(); i++) if (!sb.get(i).sbswitch) sb.get(i).draw(g2d);
		for (int i = 0; i < tb.size(); i++) if (!tb.get(i).tbswitch) tb.get(i).draw(g2d);
		for (int i = 0; i < fthb.size(); i++) if (!fthb.get(i).fthbswitch) fthb.get(i).draw(g2d);
			
		// 스킬 그리는 부분
		//블루 1번
		if(ClientComThread.speedblue&&!ClientComThread.bcd1death){
			if (System.currentTimeMillis() < ClientComThread.speedTimeblue + 5000) {
				SpeedBlue SpeedSkill = new SpeedBlue();
				SpeedBlue.bluesx = BlueCharacter1.x - 40;
				SpeedBlue.bluesy = BlueCharacter1.y - 70;
				SpeedSkill.paintDraw(g2d);
	
			} else {
				ClientComThread.speedblue = false;
				SpeedBlue.bluesx = 0;
				SpeedBlue.bluesy = 0;
			}
		}
		//레드 1번
		if(ClientComThread.speedred&&!ClientComThread.rcd1death){
			if (System.currentTimeMillis() < ClientComThread.speedTimered + 5000) {
				SpeedRed SpeedSkill = new SpeedRed();
				SpeedRed.redsx = RedCharacter1.x - 40;
				SpeedRed.redsy = RedCharacter1.y - 70;
				SpeedSkill.paintDraw(g2d);
	
			} else {
				ClientComThread.speedred = false;
				SpeedRed.redsx = 0;
				SpeedRed.redsy = 0;
			}
		}
		
		// 블루2번
		if(ClientComThread.shieldblue&&!ClientComThread.bcd2death){
			if (System.currentTimeMillis() < ClientComThread.shieldTimeblue + 5000) {
				// 방어 캐릭터 쉴드 사용시 5초동안 방어벽 생성
				ShieldBlue ShieldSkill = new ShieldBlue();
				ShieldBlue.bluesx = BlueCharacter2.x - 40;
				ShieldBlue.bluesy = BlueCharacter2.y - 70;
				ShieldSkill.paintDraw(g2d);
			} else {
				ClientComThread.shieldblue = false;
				ShieldBlue.bluesx = 0;
				ShieldBlue.bluesy = 0;
			}
		}
		// 레드2번
		if(ClientComThread.shieldred&&!ClientComThread.rcd2death){
			if (System.currentTimeMillis() < ClientComThread.shieldTimered + 5000) {
				// 방어 캐릭터 쉴드 사용시 5초동안 방어벽 생성
				ShieldRed ShieldSkill = new ShieldRed();
				ShieldRed.redsx = RedCharacter2.x - 40;
				ShieldRed.redsy = RedCharacter2.y - 70;
				ShieldSkill.paintDraw(g2d);
			} else {
				ClientComThread.shieldred = false;
				ShieldRed.redsx = 0;
				ShieldRed.redsy = 0;
			}
		}	
		// 블루3번 얼리기 스킬
		if(ClientComThread.snowblue){
			if (System.currentTimeMillis() < ClientComThread.snowTimeblue + 2000) {
				SnowRed snowSkill = new SnowRed();
				SnowRed.redsx1 = RedCharacter1.x - 40;
				SnowRed.redsy1 = RedCharacter1.y - 70;
				SnowRed.redsx2 = RedCharacter2.x - 40;
				SnowRed.redsy2 = RedCharacter2.y - 70;
				SnowRed.redsx3 = RedCharacter3.x - 40;
				SnowRed.redsy3 = RedCharacter3.y - 70;
				snowSkill.paintDraw(g2d);
			} else {
				ClientComThread.snowblue = false;
				SnowRed.redsx1 = 0;
				SnowRed.redsy1 = 0;
				SnowRed.redsx2 = 0;
				SnowRed.redsy2 = 0;
				SnowRed.redsx3 = 0;
				SnowRed.redsy3 = 0;
			}
		}
		// 레드3번 얼리기 스킬
		if(ClientComThread.snowred){
			if (System.currentTimeMillis() < ClientComThread.snowTimered + 2000) {
				SnowBlue snowSkill = new SnowBlue();
				SnowBlue.bluesx1 = BlueCharacter1.x - 40;
				SnowBlue.bluesy1 = BlueCharacter1.y - 70;
				SnowBlue.bluesx2 = BlueCharacter2.x - 40;
				SnowBlue.bluesy2 = BlueCharacter2.y - 70;
				SnowBlue.bluesx3 = BlueCharacter3.x - 40;
				SnowBlue.bluesy3 = BlueCharacter3.y - 70;
				snowSkill.paintDraw(g2d);
			} else {
				ClientComThread.snowred = false;
				SnowBlue.bluesx1 = 0;
				SnowBlue.bluesy1 = 0;
				SnowBlue.bluesx2 = 0;
				SnowBlue.bluesy2 = 0;
				SnowBlue.bluesx3 = 0;
				SnowBlue.bluesy3 = 0;
			}
		}	
		// 블루3번 벽돌 스킬
		if(ClientComThread.brickblue&&!ClientComThread.bcd3death){
			if (System.currentTimeMillis() < ClientComThread.brickTimeblue + 3000) {
				BrickBlue brickSkill = new BrickBlue();
				BrickBlue.bluebx = BlueCharacter3.x;
				BrickBlue.blueby = -60;
				brickSkill.paintDraw(g2d);	
			} else {
				ClientComThread.brickblue = false;
				BrickBlue.bluebx = 0;
				BrickBlue.blueby = 0;
			}
		}
		// 레드3번 벽돌 스킬
		if (ClientComThread.brickred && !ClientComThread.rcd3death) {
			if (System.currentTimeMillis() < ClientComThread.brickTimered + 3000) {
				BrickRed brickSkill = new BrickRed();
				BrickRed.redbx = RedCharacter3.x;
				BrickRed.redby = -60;
				brickSkill.paintDraw(g2d);
			} else {
				ClientComThread.brickred = false;
				BrickRed.redbx = 0;
				BrickRed.redby = 0;
			}
		}
	}

}
