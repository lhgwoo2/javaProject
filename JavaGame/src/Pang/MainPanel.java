package Pang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel  {
	
	static Character ch ; 			//캐릭터 클래스
	static double chx; 					//Character 의 x 좌표
	//static Bullet bu; 				//총알 클래스 
	static List<Bullet> bullet = new ArrayList<>();
	static List<FirstBall> fb  = new ArrayList<>();
	static List<SecondBall> sb = new ArrayList<>();
	static List<ThirdBall> tb = new ArrayList<>(); //ThirdBall 배열
	static List<Brick> brick = new ArrayList<>();
	//각 공의 좌표는 따로받아야되나..
	//볼 객체를 하나를 이용해서 할 수 있음. 그렇게 해야 하는지.
	static double bux; 				//Bullet 의 x 좌표
	static double buy; 				//Bullet 의 y 좌표 
	static double fbx; 				//firstBall 의 x 좌표
	static double fby; 				//firstBall 의 y 좌표
	static double sbx; 				//secondBall 의 x 좌표
	static double sby; 				//secondBall 의 y 좌표
	static double tbx; 				//thirdBall 의 x 좌표
	static double tby; 				//thirdBall 의 y 좌표
	boolean bool = true;
	boolean firstbullet ;

	
	Timer timer;
	Set<Integer> key = new HashSet<>(); //키보드 입력값 저장 컬렉션
	
	public MainPanel(){
		setLayout(null);
		setBounds(0, 50, 1600, 900);
		setBackground(Color.WHITE);
		ch = new Character(this); //캐릭터 클래스 
		firstBall(); //첫번째 공 메소드
		brick();

		//타이머
		timer = new Timer(30, new ActionListener() { // 반복되는 주기 를 30 으로 줬음.
			@Override
			public void actionPerformed(ActionEvent e) {
				// ball().loop();
				Iterator<Integer> it = key.iterator();
				while (it.hasNext()) {
					int keyCode = it.next();
					switch (keyCode) {
					case KeyEvent.VK_A: // 가상키 A , A를 누른경우!
						chx -= 10;
						ch.loop();
						break; 
					case KeyEvent.VK_D: // 가상키 D , D를 누른경우!
						chx += 10;
						ch.loop();
						break; 
					case KeyEvent.VK_N:// 가상키 n , n를 누른경우! bullet 메소드를 부름.
						bullet();
						break; 

					}
				}
			}
		});
		
		//키보드
		this.addKeyListener(new KeyAdapter() {		
			@Override
			public void keyPressed(KeyEvent e) {  ////키가 눌렀을 경우
				int keyCode = e.getKeyCode();//어느키가눌렸는지!
				key.add(keyCode);
				if(!timer.isRunning()) timer.start();			
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//우리가 만든 이벤트처리 루프가 중단되게 한다.
				key.remove(e.getKeyCode());
				if(key.isEmpty()) timer.stop(); //만약에 set 컬렉션에 없으면 타이머 스탑! 
				
			}
		});

	} // 생성자 끝


	//paintComponent 시작
	@Override
	protected void paintComponent(Graphics g) {  //repaint 해서 부를수 있다.
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(ch!=null) ch.draw(g2d); //캐릭터를 그린다. 

		if (bullet.size() > 0) {
			for (int i = 0; i < bullet.size(); i++) {
				if (!bullet.get(i).bool) bullet.get(i).draw(g2d); // 총알의 좌표를 계속 패널에 그린다.
			}
		}
		if(fb.size()>0){ //첫번째볼을그린다
			for (int i = 0; i < fb.size(); i++) {
				if(!fb.get(i).fbswitch) fb.get(i).draw(g2d); //bool 값이 true 가 되면 그림
			}
		}
		if(sb.size()>0){//두번째볼을 그린다.
			for (int i = 0; i < sb.size(); i++) {
				if(!sb.get(i).sbswitch) sb.get(i).draw(g2d);
			}
		}
		if(tb.size()>0){//두번째볼을 그린다.
			for (int i = 0; i < tb.size(); i++) {
				if(!tb.get(i).tbswitch) tb.get(i).draw(g2d);
			}
		} 
		for(int i = 0; i < brick.size(); i++){//벽돌을 그린다.
			brick.get(i).draw(g2d);
		}
	}
	//paintComponent 시작
	
	//MainPanel 에 메소드 시작
	public void bullet(){ //컬렉션에 넣어줘야할듯! 재활용 성공!
		if (!firstbullet) {
			bullet.add(new Bullet((getWidth() + 65) / 2 + chx, getHeight() - 80, 15, this));
			firstbullet = true;
		}
		for (int i = 0; i < bullet.size(); i++) {
			if (bullet.get(i).bool) {
				bullet.get(i).x = (getWidth() + 65) / 2 + chx;
				bullet.get(i).y = getHeight() - 80;
				bullet.get(i).bool = false;
				return;
			} else {
				return;
			}
		}

	}
	public void firstBall(){ //첫번째 볼
		fb.add(new FirstBall(this,0,getHeight()-400,10,-30)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-400,-10,-30)); 
	}
	public void secondBall(){ //두번째 볼
		sb.add(new SecondBall(this,fbx+120,fby,13,-32));
		sb.add(new SecondBall(this,fbx-120,fby,-13,-32)); 
	}
	public void thirdBall(){//세번째볼
		tb.add(new ThirdBall(this,sbx+80,sby,16,-34));
		tb.add(new ThirdBall(this,sbx-80,sby,-16,-34)); 
	}
	private void brick() {
		brick.add(new Brick(this,200,50,300,300,Color.magenta));
		brick.add(new Brick(this,200,40,600,300,Color.RED));
		
	}
	public static double GetDistance(double x1, double y1, double x2, double y2) { // 거리
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	//MainPanel 메소드 끝


}//클래스end
