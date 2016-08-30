package Network;

import java.io.ObjectInputStream;
import java.net.Socket;

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

				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;
					//모든 팀 입장 게임화면 진입.
					if (cData.isAllTeamOK()) {
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

				} else if (obj instanceof GameBroadData) {
					GameBroadData gbData = (GameBroadData) obj;
					//캐릭터가 이동하여 좌표변경하여 다시 그려준다.
					
					//블루 캐릭터
					mp.bCharac1.chx+=gbData.getBlueP1x(); 
					mp.bCharac2.chx+=gbData.getBlueP2x();
					mp.bCharac3.chx+=gbData.getBlueP3x();
					mp.bCharac1.loop();
					mp.bCharac2.loop();
					mp.bCharac3.loop();
					
					//레드 캐릭터
					mp.rCharac1.chx+=gbData.getRedP1x(); 
					mp.rCharac2.chx+=gbData.getRedP2x();
					mp.rCharac3.chx+=gbData.getRedP3x();
					mp.rCharac1.loop();
					mp.rCharac2.loop();
					mp.rCharac3.loop();
					
					
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

					
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
