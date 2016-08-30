package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import GamePanel.ChatPanel;
import GamePanel.MainFrame;
import GamePanel.MainPanel;

public class GameClient {

	public ObjectOutputStream toServer;
	public ObjectInputStream fromServer;
	public Socket socket;
	public String clientId;
	public MainPanel mp;
	public int clientNumber;		// 클라이언트 캐릭터 번호
	public String teamColor;		// 팀 칼라
	
	public GameClient(MainPanel mp) {
		this.mp = mp;
	}

	// 한개에 프로그램에 접속하는 것으로 고정아이피, 고정포트넘버사용
	public boolean connect() {
		try {
			socket = new Socket("192.168.2.13", 1234);
			System.out.println("Server Connectted");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void streamOpen() {
		try {
			this.toServer = new ObjectOutputStream(socket.getOutputStream());
			this.fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean loginSend(ClientData cData) {

		boolean loginOk = false;
		String str = null;
		try {
			toServer.writeObject(cData);
			toServer.flush();

			cData = (ClientData) fromServer.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// team 인원수 파악 및 로그인 성공
		if (cData.isLoginOK()) {
			loginOk = true;
			clientId = cData.getUserId();
			teamColor = cData.getTeamColor();		// 서버로부터 팀 배정
			clientNumber = cData.getTeamNum();		// 서버로부터 팀순번 배정
			
			str = String.format("Red Team : %d/3\nBlue Team: %d/3", cData.getClientRedNum(), cData.getClientBlueNum());
			JOptionPane.showMessageDialog(mp, str);
			new ClientComThread(socket, mp, this).start();
			
			//들어온 인원수를 제한한다.	블루 1/ 레드 1
			if (cData.getClientBlueNum() == 3 && cData.getClientRedNum() ==  3) {

				try {
					// 모든 팀원들이 들어와서 모두 매칭되었다. 서버로 신호를 보냄 -> 서버에서 다시 모든 클라이언트로 값을
					// 보내어 게임에 입장하도록 함.
					cData.setAllTeamOK(true);
					toServer.writeObject(cData);
					toServer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		} else if (cData.isLoginOK()) {
			str = String.format("Red Team : %d/3\nBlue Team: %d/3", cData.getClientRedNum(), cData.getClientBlueNum());
			JOptionPane.showMessageDialog(mp, str);
			loginOk = false;
		}
		return loginOk;

	}

	// Chatting sending
	public void sendMessage(String msg) {

		ClientData cData = new ClientData();
		cData.setChatMsg(msg);
		cData.setUserId(clientId);
		try {
			toServer.writeObject(cData);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 게임 데이터를 보낸다.
	public void sendGameData(GameData gData) {

		try {
			System.out.println("게임데이터 나간다. 서버쪽으로");
			
			toServer.writeObject(gData);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
