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
	public GameDataPanel gdp ;  //���������г�
	
	//��� ĳ���� x ��ǥ
	public BlueCharacter1 bCharac1;		// ����� 1�� ĳ���� Ŭ����
	public BlueCharacter2 bCharac2;		// ����� 2�� ĳ���� Ŭ����
	public BlueCharacter3 bCharac3;		// ����� 3�� ĳ���� Ŭ����
	
	//���� ĳ���� x ��ǥ
	public RedCharacter1 rCharac1;		// ������ 1�� ĳ���� Ŭ����
	public RedCharacter2 rCharac2;		// ������ 2�� ĳ���� Ŭ����
	public RedCharacter3 rCharac3;		// ������ 3�� ĳ���� Ŭ����
	
	
	//public blueCharacter3 bCharac13;		// ����� 1�� ĳ���� Ŭ����
	
	//��� �Ѿ� x��ǥ
	boolean blue1fb ; 			//����� 1�� ĳ���� ó���� �Ѿ� ����!
	boolean blue2fb ; 			//����� 2�� ĳ���� ó���� �Ѿ� ����!
	boolean blue3fb ; 			//����� 3�� ĳ���� ó���� �Ѿ� ����!
	
	//���� �Ѿ� x��ǥ
	boolean red1fb ; 			//������ 1�� ĳ���� ó���� �Ѿ� ����!
	boolean red2fb ; 			//������ 2�� ĳ���� ó���� �Ѿ� ����!
	boolean red3fb ; 			//������ 3�� ĳ���� ó���� �Ѿ� ����!

	//���� Ŭ���̾�Ʈ�� ���÷��� ������
	public String teamColor;
	public int teamNumber;
	
	public HashSet<Integer> keyCodes = new HashSet<>();
	public Timer timer;
	public GameData gData;		// ���ӵ����� ����
	
	public static List<FirstBall> fb  = new ArrayList<>();		
	public static List<SecondBall> sb  = new ArrayList<>();		
	public static List<ThirdBall> tb  = new ArrayList<>();	
	
	//��� �Ѿ�
	public static List<BulletBlue1> bullet1B = new ArrayList<>();		//����� 1�� Bullet �迭
	public static List<BulletBlue2> bullet2B = new ArrayList<>();		//����� 2�� Bullet �迭
	public static List<BulletBlue3> bullet3B = new ArrayList<>();		//����� 3�� Bullet �迭
	
	
	// ���� �Ѿ�
	public static List<BulletRed1> bullet1R = new ArrayList<>(); 		//������ 1�� Bullet �迭
	public static List<BulletRed2> bullet2R = new ArrayList<>(); 		// ������ 2�� Bullet �迭
	public static List<BulletRed3> bullet3R = new ArrayList<>(); 		// ������ 3�� Bullet �迭
	
	
	// ĳ���� �¿� �̵�
	public boolean left;
	public boolean right;
	
	public MainPanel(JFrame f) {
		super();
		this.setBounds(0, 50, 1600, 900);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.f = f;
		
		gdp= new GameDataPanel(this);
		this.add(gdp);
		//gdp.setVisible(true);

		this.setFocusable(true);
		this.requestFocus();
		drawingMainImage();
		setFocusable(true);			// �����г��� ��Ŀ���� ���߾��ش�
		requestFocus();				// ��Ŀ���� ��û�Ѵ�.
		
	}
	
	public void firstBall(){
		
		fb.add(new FirstBall(this,0,getHeight()-420,10,-26)); 
		fb.add(new FirstBall(this,getWidth()-120,getHeight()-420,-10,-26)); 
	}
	



	public void eventKey() {
		// ĳ���͸� �����Ѵ�. �����
		bCharac1 = new BlueCharacter1(this);
		bCharac2 = new BlueCharacter2(this);
		bCharac3 = new BlueCharacter3(this);
		// ĳ���� �����Ѵ�. ������
		rCharac1 = new RedCharacter1(this);
		rCharac2 = new RedCharacter2(this);
		rCharac3 = new RedCharacter3(this);

		// ���÷�, ������ �Է�
		teamColor = LoginPanel.gClient.teamColor;
		teamNumber = LoginPanel.gClient.clientNumber;
		// Ű���� Ÿ�̸Ӹ� �����ν� �ߺ��� Ű�� �ȴ������� �Ѵ�.
		timer = new Timer(25, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Iterator<Integer> it = keyCodes.iterator();
				while(it.hasNext()) {
					int keyCode = it.next();

					switch (keyCode) {
					case KeyEvent.VK_A:
						//���� �̵� ĳ���� �׸����� ��ġ �̵�
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData= new GameData();
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(-10);
						gData.setLeftMove(true);
						LoginPanel.gClient.sendGameData(gData);

						break;
					case KeyEvent.VK_D:
						//������ �̵� ĳ���� �׸����� ��ġ �̵�
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData= new GameData();
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setChx(+10);
						gData.setRightMove(true);
						LoginPanel.gClient.sendGameData(gData);
		
						break;
					case KeyEvent.VK_N:// ����Ű n , n�� �������! bullet �޼ҵ带 �θ�.
						// �� ���� �� ���� ����, ĳ������ ��ǥ����
						gData= new GameData();
						gData.setTeamColor(teamColor);
						gData.setTeamNum(teamNumber);
						gData.setBulletStart(true); // �Ѿ��� ����ߴ�.
						LoginPanel.gClient.sendGameData(gData);

						break;
					}
				}
			}
		});

				// adapter�� ���� ���ϴ� �޼ҵ常 ����Ͽ� ÷���Ҽ� �ִ�.
				// �׷��� listener�� 3���� �޼ҵ� ��� ����ؾ� �Ѵ�.
				this.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						
						int keyCode = e.getKeyCode();
						keyCodes.add(keyCode);
						if(!timer.isRunning()) timer.start();
						// Ű���尡 ������ Ÿ�̸Ӱ� �۵���. �������
					}
					
					@Override
					public void keyReleased(KeyEvent e){
						int keyCode = e.getKeyCode();
						keyCodes.remove(keyCode);
						if(timer.isRunning())timer.stop();
						//Ű���尡 �������� Ÿ�̸Ӱ� ������.��������
					}
				});
		
	}

	//MainPanel �� �޼ҵ� ����
	
	//����� 1�� �Ѿ�
	public void bP1bullet() { 
		if (!blue1fb) {
			bullet1B.add(new BulletBlue1((getWidth() + 65) / 2 + bCharac1.chx, getHeight() - 270, 15, this));
			blue1fb = true;
			System.out.println("blue1fb:" + "�ǵ��ӳ�");
		}
		for (int i = 0; i < bullet1B.size(); i++) {
			bullet1B.get(i).initBulletX((getWidth() + 65) / 2 + bCharac1.chx);
			bullet1B.get(i).initBulletY(getHeight() - 270);
			bullet1B.get(i).initBullet(false);
			System.out.println("blue1fb:" + "���ε���");
		}
	}

	// ����� 2�� �Ѿ�
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
	// ����� 3�� �Ѿ�
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
	
	//������ �Ѿ�
	
	//������ 1�� �Ѿ�
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
		}

		// ������ 2�� �Ѿ�
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
		}
		// ������ 3�� �Ѿ�
		public void rP3bullet() { 
			if (!red3fb) {
				bullet3R.add(new BulletRed3((getWidth() + 65) / 2 + rCharac3.chx, getHeight() - 270, 15, this));
				red3fb = true;
			}
			for (int i = 0; i < bullet3R.size(); i++) {
				bullet3R.get(i).initBulletX((getWidth() + 65) / 2 + rCharac3.chx);
				bullet3R.get(i).initBulletY(getHeight() - 270);
				bullet3R.get(i).initBullet(false);
			}
		}

		
	// ��ü�� �Ÿ� ����
	public static double GetDistance(double x1, double y1, double x2, double y2) { // �Ÿ�
		return (y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1);
	}


	public void drawingMainImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangMain.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
	}

	public void drawingPlayImage() {
		InputStream is = getClass().getResourceAsStream("/imagePack/pangpangback2.jpg");
		try {
			bimg = ImageIO.read(is);
			System.out.println("����ȭ�� �ε� ����");
		} catch (IOException e) {
			System.out.println("����ȭ�� �ε� ����");
			e.printStackTrace();
		}
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		g2d.drawImage(bimg,0,0,1600,900,null);
		
		//ĳ���͸� �׸��� �κ� ����
		//�����
		if(bCharac1!=null)bCharac1.draw(g2d);
		if(bCharac2!=null)bCharac2.draw(g2d);
		if(bCharac3!=null)bCharac3.draw(g2d);
		//������
		if(rCharac1!=null)rCharac1.draw(g2d);
		if(rCharac2!=null)rCharac2.draw(g2d);
		if(rCharac3!=null)rCharac3.draw(g2d);
		//ĳ���� �׸��� �κ� ��!
		
		//�Ѿ� �׸��� �κ� ����
		//���
		if (bullet1B.size() > 0) for (int i = 0; i < bullet1B.size(); i++)  bullet1B.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		if (bullet2B.size() > 0) for (int i = 0; i < bullet2B.size(); i++)  bullet2B.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		if (bullet3B.size() > 0) for (int i = 0; i < bullet3B.size(); i++)  bullet3B.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	

		//����
		if (bullet1R.size() > 0)  for (int i = 0; i < bullet1R.size(); i++)  bullet1R.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		if (bullet2R.size() > 0) for (int i = 0; i < bullet2R.size(); i++)  bullet2R.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		if (bullet3R.size() > 0) for (int i = 0; i < bullet3R.size(); i++)  bullet3R.get(i).draw(g2d); // �Ѿ��� ��ǥ�� ��� �гο� �׸���.	
		//�Ѿ� �׸��� �κ� ��
		
		//�� �׸��� �κ�
		for(int i =0;i<fb.size();i++) if(!fb.get(i).fbswitch) fb.get(i).draw(g2d);	
		for(int i =0;i<sb.size();i++) if(!sb.get(i).sbswitch) sb.get(i).draw(g2d);	
		for(int i =0;i<tb.size();i++) if(!tb.get(i).tbswitch) tb.get(i).draw(g2d);	

		
	}
	


}
