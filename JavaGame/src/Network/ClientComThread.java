package Network;

import java.io.ObjectInputStream;
import java.net.Socket;

import GamePanel.ChatPanel;
import GamePanel.MainPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
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
					mp.bCharac1.chx+=gbData.getBlueP1x();
					mp.bCharac2.chx+=gbData.getBlueP2x();
					mp.bCharac1.loop();
					mp.bCharac2.loop();
					for(int i =0;i<mp.fb.size();i++){
						mp.fb.get(i).loop();	
					}
					//총알 start
					//블루1번총알 시작
					if(gbData.isbP1bulletStart()){
						mp. bP1bullet();
					}
					//블루1번총알 끝
					
					//블루2번총알 시작
					if(gbData.isbP2bulletStart()){
						mp. bP2bullet();
					}
					//블루2번총알 끝
					//총알 end
					
					//총알 루프 시작
					//블루 1번 총알 루프
					for (int i = 0; i < mp.bullet.size(); i++) {
						if (mp.bullet.size() > 0) {
							mp.bullet.get(i).loop();// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					
					//블루 2번 총알 루프
					for (int i = 0; i < mp.bullet2.size(); i++) {
						if (mp.bullet2.size() > 0) {
							mp.bullet2.get(i).loop();// 총알의 좌표를 계속 패널에 그린다.
						}
					}
					
					//총알 루프 시작
				
					mp.repaint();
					
					/*if(gData.getChx()!=0)
					{
						if(gData.getTeamColor().equals("Blue"))
						{
							if(gData.getTeamNum()==1)
							{
								mp.bCharac1.chx+=gData.getChx();
								mp.bCharac1.loop();
								mp.repaint();
							}
							else if(gData.getTeamNum()==2)
							{
								mp.bCharac2.chx+=gData.getChx();
								mp.bCharac2.loop();
								mp.repaint();
							}
						}
						
					}*/
					
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
