package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import GamePanel.LoadingThread;
import GamePanel.MainPanel;

public class GameClient {

	public ObjectOutputStream toServer;
	public ObjectInputStream fromServer;
	public Socket socket;
	public String clientId;
	public MainPanel mp;
	public int clientNumber;		// Ŭ���̾�Ʈ ĳ���� ��ȣ
	public String teamColor="";		// �� Į��
	public LoadingThread loadthread;

	
	public GameClient(MainPanel mp) {
		this.mp = mp;
	}

	// �Ѱ��� ���α׷��� �����ϴ� ������ ����������, ������Ʈ�ѹ����
	public boolean connect() {
		try {
			socket = new Socket("192.168.2.4", 1234);
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
			toServer.reset();
			toServer.writeObject(cData);
			toServer.flush();

			cData = (ClientData) fromServer.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// team �ο��� �ľ� �� �α��� ����
		if (cData.isLoginOK()) {
			loginOk = true;
			clientId = cData.getUserId();
			teamColor = cData.getTeamColor();		// �����κ��� �� ����
			clientNumber = cData.getTeamNum();		// �����κ��� ������ ����
			
	//Ŭ���̾�Ʈ ȭ���� �ε�ȭ������ ��ȯ�Ѵ�. - ������ Ȱ��
			loadthread = new LoadingThread(mp);
			loadthread.start();
			
			
			str = String.format("Red Team : %d/3\nBlue Team: %d/3", cData.getClientRedNum(), cData.getClientBlueNum());
			JOptionPane.showMessageDialog(mp, str);
			new ClientComThread(socket, mp, this).start();
			System.out.println("Ŭ���̾�Ʈ�޾Ұ� Ŭ�� ������ ����");
			//���� �ο����� �����Ѵ�.	��� 1/ ���� 1
			if (cData.getClientBlueNum() == 3 && cData.getClientRedNum() == 3) {

				try {
					// ��� �������� ���ͼ� ��� ��Ī�Ǿ���. ������ ��ȣ�� ���� -> �������� �ٽ� ��� Ŭ���̾�Ʈ�� ����
					// ������ ���ӿ� �����ϵ��� ��.
					cData.setAllTeamOK(true);
					toServer.reset();
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
	public void sendMessage(GameData gData) {

		gData.setUserID(clientId);
		try {
			toServer.reset(); 
			toServer.writeObject(gData);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ���� �����͸� ������.
	public void sendGameData(GameData gData) {
		try {
			toServer.reset();
			toServer.writeObject(gData);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void sendGamePointData(PointData gData) {
		try {
			toServer.reset();
			toServer.writeObject(gData);
			toServer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	

}
