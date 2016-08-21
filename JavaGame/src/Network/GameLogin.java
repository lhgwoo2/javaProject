package Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameLogin extends Thread {

	BufferedReader br;

	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	Socket socket;

	public GameLogin(Socket socket) {
		super();
		this.socket = socket;
		try {
			fromClient = new ObjectInputStream(socket.getInputStream());
			toClient = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		super.run();
		ClientData cData = new ClientData();
		while (true) {
			try {
				cData = (ClientData) fromClient.readObject();

				if (loginMatch(cData)) {
					System.out.println("로그인 성공");
					//cData.setMsg("로그인 성공");
					cData.setLoginOK(true);
					toClient.writeObject(cData);
					toClient.flush();
					GameServer.userMap.put(cData.getUserId(), socket);
					new GameServerThread(fromClient, socket).start();
					break;

				} else {
					System.out.println("로그인 실패");
					//cData.setMsg("로그인 실패");
					cData.setLoginOK(false);
					toClient.writeObject(cData);
					toClient.flush();
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

	}

	public boolean loginMatch(ClientData cData) {
		FileReader fr;
		String line;
		PrintWriter pw;
		
		try {
			pw = new PrintWriter(new FileWriter("E:/test/test.txt"));
			fr = new FileReader("E:/test/test.txt");
			br = new BufferedReader(fr);
			String[] token = null;
			
			while ((line = br.readLine()) != null) {
				token = line.split("\\|");
				if (token[0].equals(cData.getUserId())) { // id비교
					return false;			// 등록된 아이디가 있으면 실패
				}
			}
			if(teamMatch(cData))
			{
				pw.println(cData.getUserId());		// userID input
				cData.setTeamOK(true);
			}else{
				cData.setTeamOK(false);
				return false;
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}
	
	// Team select Ok
	public boolean teamMatch(ClientData cData){
		String team = cData.getTeamName();
		if(team.equals("Blue"))
		{
			if(GameServer.blueTeam.size()<3){
				GameServer.blueTeam.put(cData.getUserId(), socket);
				cData.setTeamName("Blue");
				return true;
			}
			else{
				return false;
			}
		}
		else if(team.equals("Red")){
			if(GameServer.redTeam.size()<3){
				GameServer.redTeam.put(cData.getUserId(), socket);
				cData.setTeamName("Red");
				return true;
			}
			else{
				return false;
			}

		}
		else{
			return false;
		}
	}

}
