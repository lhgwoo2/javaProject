package Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import GamePanel.ChatPanel;
import GamePanel.MainPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
	
	//블루팀 총알 좌표
	public static double buxB;
	public static double buyB;
	public static double bux2B;
	public static double buy2B;
	public static double bux3B;
	public static double buy3B;
	
	//레드팀 총알 좌표
	public static double buxR;
	public static double buyR;
	public static double bux2R;
	public static double buy2R;
	public static double bux3R;
	public static double buy3R;
	
	//캐릭터 좌우 이동 플래그
	public boolean b1MoveFlag;				//블루팀
	public boolean b2MoveFlag;
	public boolean b3MoveFlag;
	public boolean r1MoveFlag;				//레드팀
	public boolean r2MoveFlag;
	public boolean r3MoveFlag;
	
	MainPanel mp;
	ChatPanel cp;
	GameClient gc;
	// static DataFormat uData;

	ClientComThread(Socket socket, MainPanel mp, GameClient gc) {
		this.socket = socket;
		this.mp = mp;
		this.gc=gc;
	}

	@Override
	public void run() {
		cp = new ChatPanel(gc,mp);
		
		while (true) {
			try {
					fromServer = new ObjectInputStream(socket.getInputStream()); 
					Object obj = fromServer.readObject();

				// 클라이언트 로그인 및 채팅데이터
				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;
					//모든 팀 입장 게임화면 진입.
					if (cData.isAllTeamOK()) {
						gc.loadthread.interrupt();			// 로딩화면 중지 - 쓰레드 중지
						mp.setVisible(true);						// 메인화면 다시 보여주기
						mp.drawingPlayImage();
						mp.setFocusable(true);
						mp.requestFocus();
						mp.eventKey();		//이벤트 처리를 위한 키보드사용
						mp.firstBall();
						mp.add(cp);
						mp.repaint();
					}
					if(cData.getChatMsg()!=null){
						cp.chatAppendMsg(cData);
						cp.repaint();
					}

					// 게임 데이터 받는 부분
				} else if (obj instanceof GameData) {
					long start = System.currentTimeMillis();			
					GameData gData = (GameData) obj;

					if (gData.getTeamColor().equals("Blue")) {
						if (gData.getTeamNum() == 1) {
							if (gData.getChx() != 0) {
								mp.bCharac1.chx+= gData.getChx(); mp.bCharac1.loop(); gData.setChx(0);
								if(!b1MoveFlag && gData.isLeftMove()){mp.bCharac1.setMove(90, 180);b1MoveFlag = true;}
								else if(b1MoveFlag && gData.isRightMove()){	mp.bCharac1.setMove(0, 90);b1MoveFlag=false;	}
							}
							if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
								mp.bP1bullet();						
							}
						}
						else if (gData.getTeamNum() == 2) {
							if (gData.getChx() != 0) {
								mp.bCharac2.chx+= gData.getChx(); mp.bCharac2.loop(); gData.setChx(0);
								if(!b2MoveFlag && gData.isLeftMove()){mp.bCharac2.setMove(90, 180);b2MoveFlag = true;}
								else if(b2MoveFlag && gData.isRightMove()){	mp.bCharac2.setMove(0, 90);b2MoveFlag=false;	}
							}
							if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
								mp.bP2bullet();
							}
						}
						else if (gData.getTeamNum() == 3) {
							if (gData.getChx() != 0) {
								mp.bCharac3.chx+= gData.getChx();mp.bCharac3.loop();gData.setChx(0);
								if(!b3MoveFlag && gData.isLeftMove()){mp.bCharac3.setMove(90, 180);b3MoveFlag = true;}
								else if(b3MoveFlag && gData.isRightMove()){	mp.bCharac3.setMove(0, 90);b3MoveFlag=false;	}
							}
							if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
								mp.bP3bullet();
							}
						}
					}else if (gData.getTeamColor().equals("Red")) { //레드일때!
							if (gData.getTeamNum() == 1) {
								if (gData.getChx() != 0) {
									mp.rCharac1.chx+= gData.getChx();mp.rCharac1.loop();gData.setChx(0);
									if(!r1MoveFlag && gData.isLeftMove()){mp.rCharac1.setMove(90, 180);r1MoveFlag = true;}
									else if(r1MoveFlag && gData.isRightMove()){	mp.rCharac1.setMove(0, 90);r1MoveFlag=false;	}
								}
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
									mp.rP1bullet();
								}
							}
							else if (gData.getTeamNum() == 2) {
								if (gData.getChx() != 0) {
									mp.rCharac2.chx+= gData.getChx(); mp.rCharac2.loop();gData.setChx(0);
									if(!r2MoveFlag && gData.isLeftMove()){mp.rCharac2.setMove(90, 180);r2MoveFlag = true;}
									else if(r2MoveFlag && gData.isRightMove()){	mp.rCharac2.setMove(0, 90);r2MoveFlag=false;	}
								}
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
									mp.rP2bullet();
								}
							}
							else if (gData.getTeamNum() == 3) {
								if (gData.getChx() != 0) {
									mp.rCharac3.chx+= gData.getChx(); mp.rCharac3.loop();gData.setChx(0);
									if(!r3MoveFlag && gData.isLeftMove()){mp.rCharac3.setMove(90, 180);r3MoveFlag = true;}
									else if(r3MoveFlag && gData.isRightMove()){	mp.rCharac3.setMove(0, 90);r3MoveFlag=false;	}
								}
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
									mp.rP3bullet();
								}
							}
					}

					
					
					System.out.println("ball루프전");
					
					//Ball 루프 
					for(int i =0;i<mp.fb.size();i++){
						if(!mp.fb.get(i).fbswitch)	mp.fb.get(i).loop();	
					}
					for(int i =0;i<mp.sb.size();i++){
						if(!mp.sb.get(i).sbswitch)	mp.sb.get(i).loop();	
					}
					for(int i =0;i<mp.tb.size();i++){
						if(!mp.tb.get(i).tbswitch)	mp.tb.get(i).loop();	

					}
					//Ball 루프 

					//총알 루프 시작
					//블루 1번 총알 루프
						if (mp.bullet1B.size() > 0) {
							mp.bullet1B.get(0).loop();
							buxB = mp.bullet1B.get(0).x;
							buyB = mp.bullet1B.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					// 블루 2번 총알 루프
						if (mp.bullet2B.size() > 0) {
							mp.bullet2B.get(0).loop();
							bux2B = mp.bullet2B.get(0).x;
							buy2B = mp.bullet2B.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					// 블루 3번 총알 루프
						if (mp.bullet3B.size() > 0) {
							mp.bullet3B.get(0).loop();
							bux3B = mp.bullet3B.get(0).x;
							buy3B = mp.bullet3B.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}			
		
					//레드팀 1번 총알 루프
						if (mp.bullet1R.size() > 0) {
							mp.bullet1R.get(0).loop();
							buxR = mp.bullet1R.get(0).x;
							buyR = mp.bullet1R.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					// 레드 2번 총알 루프
						if (mp.bullet2R.size() > 0) {
							mp.bullet2R.get(0).loop();
							bux2R = mp.bullet2R.get(0).x;
							buy2R = mp.bullet2R.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					// 레드 3번 총알 루프
						if (mp.bullet3R.size() > 0) {
							mp.bullet3R.get(0).loop();
							bux3R = mp.bullet3R.get(0).x;
							buy3R = mp.bullet3R.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					//총알 루프 끝
					
					mp.repaint();

					long end = System.currentTimeMillis();
					System.out.printf("클라이언트에서 받는데 걸린시간:%d\n",end-start);
				}
				} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
