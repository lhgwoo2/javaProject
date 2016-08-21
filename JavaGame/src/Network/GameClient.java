package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.kdea.java.Swing.DrawingPanel;
import org.kdea.java.Swing.WinMain;

public class GameClient {

	ObjectOutputStream toServer;
	ObjectInputStream fromServer;
	Socket socket;
	String clientId;

	public GameClient() {
	}

	// 한개에 프로그램에 접속하는 것으로 고정아이피, 고정포트넘버사용
	public boolean connect() {
		try {
			socket = new Socket("127.0.0.1", 1234);
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
		try {
			toServer.writeObject(cData);
			toServer.flush();

			cData = (ClientData) fromServer.readObject();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (cData.isLoginOK()) {
			loginOk = true;
			clientId = cData.getUserId();
			new ClientComThread(socket).start();
		} else if (cData.isLoginOK())
			loginOk = false;

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

}

class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
	//static DataFormat uData;

	ClientComThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		while (true) {
			try {
				fromServer = new ObjectInputStream(socket.getInputStream());
				Object obj =  fromServer.readObject();
				
				if(obj instanceof ClientData){
					ClientData cData = (ClientData) obj;
					
				}

				
				/*
				if (uData.getFileDate() != null) {
					// Client.fileSave(uData);
					WinMain.dp = new DrawingPanel(uData.getFileDate());
					WinMain.dp.setBounds(400, 100, 600, 480);
					WinMain.f.add(WinMain.dp);
					WinMain.dp.repaint();
					WinMain.ta.append(uData.getUserId() + " : " + uData.getFileName() + "\n");
				} else
					WinMain.ta.append(uData.getUserId() + " : " + uData.getMsg() + "\n");

*/			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
