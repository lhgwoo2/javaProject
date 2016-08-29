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
					//mp.bCharac1.loop();
					//mp.bCharac2.loop();
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
