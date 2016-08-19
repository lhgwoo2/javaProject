package HKM;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	static Image img; //캐릭터
	static Character ch ; //캐릭터 클래스
	static Bullet bu;
	double chx;
	Timer timer;
	Set<Integer> key = new HashSet<>();
	
	public GamePanel(){
		setLayout(null);
		setBounds(10,10,600,500);
		setBackground(Color.WHITE);
		ch = new Character(); //캐릭터 클래스 

		this.img=ch.img;
			
		//타미어
		 timer=new Timer(50, new ActionListener() { //반복되는 주기 를 50 으로 줬음.
			@Override
			public void actionPerformed(ActionEvent e) {
				Iterator<Integer> it = key.iterator();
				while(it.hasNext()){
					int keyCode = it.next();
						switch(keyCode){
							case KeyEvent.VK_A: chx+=5;ch.loop(); break; 	//가상키 A , A를 누른경우!
							case KeyEvent.VK_D: chx-=5;ch.loop(); break;	//가상키 D , D를 누른경우!
							case KeyEvent.VK_N: bullet(); break;	//가상키 n , n를 누른경우!
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
	
	public void bullet(){
        bu = new Bullet((getWidth()+45)/2-chx, getHeight()/2,15,this);

	}
	
	
	@Override
	protected void paintComponent(Graphics g) {  //repaint 해서 부를수 있다.
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(bu != null) 
		{
			bu.loop();
			bu.draw(g2d);
		}
		
		AffineTransform old = g2d.getTransform(); //유사 변환 행렬 변환에 대한정보를 가지고 있는 객체이다. 붓의 초기 위치 값을 가지고 있다 
		g2d.translate(getWidth()/2-chx, getHeight()/2); //붓을 가지고 위치를 가라.
		if(getWidth()/2-chx>=this.getWidth()||getWidth()/2-chx<=0)
		{
			System.out.println("칸을넘음");
		}
		else g2d.drawImage(img, 0, 0,90,90,ch.a,ch.b,ch.c,ch.d,this);
		g2d.setTransform(old); //붓을 원래위치로 ㅊ초기화
		
	}
	
	
}
