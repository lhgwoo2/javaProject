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
						GameClient.loadthread.interrupt();			// 로딩화면 중지 - 쓰레드 중지
						mp.setVisible(true);						// 메인화면 다시 보여주기
						mp.drawingPlayImage();
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
				} else if (obj instanceof GameBroadData) {
					long start = System.currentTimeMillis();
					GameBroadData gbData = (GameBroadData) obj;
					//캐릭터가 이동하여 좌표변경하여 다시 그려준다.
					
					//블루 캐릭터
					mp.bCharac1.chx+=gbData.getBlueP1x(); 
					mp.bCharac2.chx+=gbData.getBlueP2x();
					mp.bCharac3.chx+=gbData.getBlueP3x();
					if(gbData.getBlueP1x()!=0)	mp.bCharac1.loop();
					if(gbData.getBlueP2x()!=0)	mp.bCharac2.loop();
					if(gbData.getBlueP3x()!=0) mp.bCharac3.loop();
				
									
					//레드 캐릭터
					mp.rCharac1.chx+=gbData.getRedP1x(); 
					mp.rCharac2.chx+=gbData.getRedP2x();
					mp.rCharac3.chx+=gbData.getRedP3x();
					if(gbData.getRedP1x()!=0)	mp.rCharac1.loop();
					if(gbData.getRedP2x()!=0)	mp.rCharac2.loop();
					if(gbData.getRedP3x()!=0) mp.rCharac3.loop();
				
							
					
					// 캐릭터 좌우이동 변환
					// 블루팀
					if(!b1MoveFlag && gbData.isB1left()){mp.bCharac1.setMove(90, 180);b1MoveFlag = true;}
					else if(b1MoveFlag && gbData.isB1right()){	mp.bCharac1.setMove(0, 90);b1MoveFlag=false;	}
					if(!b2MoveFlag && gbData.isB2left()){mp.bCharac2.setMove(90, 180);b2MoveFlag = true;}
					else if(b2MoveFlag && gbData.isB2right()){	mp.bCharac2.setMove(0, 90);b2MoveFlag=false;	}
					if(!b3MoveFlag && gbData.isB3left()){mp.bCharac3.setMove(90, 180);b3MoveFlag = true;}
					else if(b3MoveFlag && gbData.isB3right()){	mp.bCharac3.setMove(0, 90);b3MoveFlag=false;	}
					
					if(!r1MoveFlag && gbData.isR1left()){mp.rCharac1.setMove(90, 180);r1MoveFlag = true;}
					else if(r1MoveFlag && gbData.isR1right()){	mp.rCharac1.setMove(0, 90);r1MoveFlag=false;	}
					if(!r2MoveFlag && gbData.isR2left()){mp.rCharac2.setMove(90, 180);r2MoveFlag = true;}
					else if(r2MoveFlag && gbData.isR2right()){	mp.rCharac2.setMove(0, 90);r2MoveFlag=false;	}
					if(!r3MoveFlag && gbData.isR3left()){mp.rCharac3.setMove(90, 180);r3MoveFlag = true;}
					else if(r3MoveFlag && gbData.isR3right()){	mp.rCharac3.setMove(0, 90);r3MoveFlag=false;	}
					
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
					
					//총알 start
					//블루 팀 총알
					if(gbData.isbP1bulletStart()) mp. bP1bullet();
					if(gbData.isbP2bulletStart()) mp. bP2bullet();
					if(gbData.isbP3bulletStart()) mp. bP3bullet();

					//레드 팀 총알
					if(gbData.isrP1bulletStart()) mp. rP1bullet();
					if(gbData.isrP2bulletStart()) mp. rP2bullet();
					if(gbData.isrP3bulletStart()) mp. rP3bullet();
					//총알 end
					
					//총알 루프 시작
					//블루 1번 총알 루프
					for (int i = 0; i < mp.bullet1B.size(); i++) {
						if (mp.bullet1B.size() > 0) {
							mp.bullet1B.get(i).loop();
							buxB = mp.bullet1B.get(i).x;
							buyB = mp.bullet1B.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					// 블루 2번 총알 루프
					for (int i = 0; i < mp.bullet2B.size(); i++) {
						if (mp.bullet2B.size() > 0) {
							mp.bullet2B.get(i).loop();
							bux2B = mp.bullet2B.get(i).x;
							buy2B = mp.bullet2B.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					// 블루 3번 총알 루프
					for (int i = 0; i < mp.bullet3B.size(); i++) {
						if (mp.bullet3B.size() > 0) {
							mp.bullet3B.get(i).loop();
							bux3B = mp.bullet3B.get(i).x;
							buy3B = mp.bullet3B.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					
					//레드팀 1번 총알 루프
					for (int i = 0; i < mp.bullet1R.size(); i++) {
						if (mp.bullet1R.size() > 0) {
							mp.bullet1R.get(i).loop();
							buxR = mp.bullet1R.get(i).x;
							buyR = mp.bullet1R.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					// 레드 2번 총알 루프
					for (int i = 0; i < mp.bullet2R.size(); i++) {
						if (mp.bullet2R.size() > 0) {
							mp.bullet2R.get(i).loop();
							bux2R = mp.bullet2R.get(i).x;
							buy2R = mp.bullet2R.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					// 레드 3번 총알 루프
					for (int i = 0; i < mp.bullet3R.size(); i++) {
						if (mp.bullet3R.size() > 0) {
							mp.bullet3R.get(i).loop();
							bux3R = mp.bullet3R.get(i).x;
							buy3R = mp.bullet3R.get(i).y;// 총알의 좌표를 계속 패널에 그린다.
						}
					}
			
					//총알 루프 끝
				
					mp.repaint();

					long end = System.currentTimeMillis();
					System.out.printf("클라이언트에서 받는데 걸린시간:%d\n",end-start);
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
