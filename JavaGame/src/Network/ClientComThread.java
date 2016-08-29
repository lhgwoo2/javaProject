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
		cp = new ChatPanel(gc);
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

				} else if (obj instanceof GameData) {
					GameData gData = (GameData) obj;
					if(gData.getChx()!=0)
					{	
						System.out.println("클라이언트에서 게임데이터 받음");
						mp.pCharac.chx+=gData.getChx();
						mp.pCharac.loop();
						mp.repaint();
					}
					
				}

				/*
				 * if (uData.getFileDate() != null) { // Client.fileSave(uData);
				 * WinMain.dp = new DrawingPanel(uData.getFileDate());
				 * WinMain.dp.setBounds(400, 100, 600, 480);
				 * WinMain.f.add(WinMain.dp); WinMain.dp.repaint();
				 * WinMain.ta.append(uData.getUserId() + " : " +
				 * uData.getFileName() + "\n"); } else
				 * WinMain.ta.append(uData.getUserId() + " : " + uData.getMsg()
				 * + "\n");
				 * 
				 */ } catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
