package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GameServer {

	static Map<String, Socket> userMap = new HashMap();
	static Map<String, Socket> blueTeam = new HashMap<>();
	static Map<String, Socket> redTeam = new HashMap<>();
	
	public static void main(String[] args) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(1234);
			Socket socket = null;
			String clientIP = null;
			
			while(true){
				socket = ss.accept();
				clientIP = socket.getInetAddress().getHostAddress();
				System.out.printf("클라이언트(%s) 접속됨...\n", clientIP);
				
				new GameLogin(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

class GameServerThread extends Thread{
	private Socket socket;
	private ObjectInputStream fromClient;
	
	
	GameServerThread(ObjectInputStream fromClient,Socket socket) {
		this.socket = socket;
		this.fromClient = fromClient;
		
	}

	@Override
	public void run() {
		try {
			DataFormat Data;

			while (true) {
				Data= (DataFormat) fromClient.readObject();
				boardCast(Data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void boardCast(DataFormat d) {
		Set<String> keys = GameServer.userMap.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String id = it.next();
			Socket soc = GameServer.userMap.get(id);

			ObjectOutputStream toClient;
			try {
				toClient = new ObjectOutputStream(soc.getOutputStream());
				toClient.writeObject(d);
				toClient.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
