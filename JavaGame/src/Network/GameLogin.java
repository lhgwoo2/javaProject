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
	//플레이어들어온 수 확인
	int playerNumber;

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
		ClientData cData;
		while (true) {
			try {
				cData = (ClientData) fromClient.readObject();

				if (loginMatch(cData)) {
					System.out.println("로그인 성공");
					// cData.setMsg("로그인 성공");
					cData.setLoginOK(true);
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					toClient.writeObject(cData);
					toClient.flush();
					GameServer.userMap.put(cData.getUserId(), socket);
					new GameServerThread(fromClient, socket,playerNumber).start();
					break;

				} else {
					System.out.println("로그인 실패");
					// cData.setMsg("로그인 실패");
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
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
			System.out.println("파일에서 로그인 매치하는 곳");
			pw = new PrintWriter(new FileWriter("E:/test/test.txt", true));
			fr = new FileReader("E:/test/test.txt");
			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				if (line.equals(cData.getUserId())) { // id비교
					return false; // 등록된 아이디가 있으면 실패
				}
			}
			if (teamMatch(cData)) {
				pw.println(cData.getUserId()); // userID input
				cData.setTeamOK(true);
			} else {
				cData.setTeamOK(false);
				return false;
			}
			pw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}

	// Team select Ok
	public boolean teamMatch(ClientData cData) {
		String team = cData.getTeamName();
		if (team.equals("Blue")) {
			if (GameServer.blueTeam.size() < 3) {

				// 블루팀 들어온 상황 확인.
				GameServer.blueTeam.put(cData.getUserId(), socket);
				// 현재들어온 이용자 자신의 순번과 팀 파악을 위한 데이터 전송 - 캐릭터배정을 위한
				cData.setTeamColor("Blue");
				cData.setTeamNum(GameServer.blueTeam.size());
				playerNumber+=1;		//입장한 플레이어 추가
				return true;
			} else {
				return false;
			}
		} else if (team.equals("Red")) {
			if (GameServer.redTeam.size() < 3) {

				// Red팀이 들어온 상황 확인.
				GameServer.redTeam.put(cData.getUserId(), socket);
				// 현재들어온 이용자 자신의 순번과 팀 파악을 위한 데이터 전송 - 캐릭터배정을 위한
				cData.setTeamColor("Red");
				cData.setTeamNum(GameServer.redTeam.size());
				playerNumber+=1;		//입장한 플레이어 추가
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

}
