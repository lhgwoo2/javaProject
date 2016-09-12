package Network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import DataBase.BoardDAO;
import DataBase.BoardVO;
import DataBase.LoginVO;

public class GameLogin extends Thread {

	BufferedReader br;

	ObjectInputStream fromClient;
	ObjectOutputStream toClient;
	Socket socket;

	BoardDAO boardDao;
	// 플레이어들어온 수 확인
	public static int playerNumber;

	public GameLogin(Socket socket) {
		super();
		this.socket = socket;
		boardDao = new BoardDAO();
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
					// cData.setMsg("로그인 성공");
					cData.setLoginOK(true);
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					System.out.println("로그 클라로 보낸다");

					toClient.writeObject(cData);
					toClient.flush();
					// toClient.reset();
					System.out.println("로그 클라로 보내기 성공");
					GameServer.userMap.put(cData.getUserId(), socket);
					new GameServerThread(fromClient, socket, playerNumber).start();
					System.out.println("서버 통신 쓰레드 생성");
					break;

				} else {
					System.out.println("로그인 실패");
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					cData.setLoginOK(false);

					toClient.writeObject(cData);
					toClient.flush();
					break;
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

	}

	public boolean loginMatch(ClientData cData) {

		System.out.println("파일에서 로그인 매치하는 곳");
		List<LoginVO> check = boardDao.loginIdCheck();
		for (int i = 0; i < check.size(); i++) {

			if (cData.getUserId().equals(check.get(i).getUserId())) {
				System.out.println(check.get(i).getUserId());
				System.out.println(cData.getUserId());
				System.out.println("로그인 매치 포인트 아이디 존재 상황");
				return false; // 등록된 아이디가 있으면 실패
			}
		}
		if (teamMatch(cData)) {
			// pw.println(cData.getUserId()); // userID input
			cData.setTeamOK(true);

		} else {
			cData.setTeamOK(false);
			return false;
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
				cData.setTeamColor("BLUE");
				cData.setTeamNum(GameServer.blueTeam.size());
				playerNumber += 1; // 입장한 플레이어 추가
				System.out.println(playerNumber);
				// 로그인 color 전달
				LoginVO loginVo = new LoginVO();
				loginVo.setUserId(cData.getUserId());
				loginVo.setTeamColor(cData.getTeamColor());
				boardDao.loginInsert(loginVo);
				System.out.println("로그인 블루팀 매치 상황");

				return true;
			} else {
				return false;
			}
		} else if (team.equals("Red")) {
			if (GameServer.redTeam.size() < 3) {

				// Red팀이 들어온 상황 확인.
				GameServer.redTeam.put(cData.getUserId(), socket);
				// 현재들어온 이용자 자신의 순번과 팀 파악을 위한 데이터 전송 - 캐릭터배정을 위한
				cData.setTeamColor("RED");
				cData.setTeamNum(GameServer.redTeam.size());
				playerNumber += 1; // 입장한 플레이어 추가
				LoginVO loginVo = new LoginVO();
				loginVo.setUserId(cData.getUserId());
				loginVo.setTeamColor(cData.getTeamColor());
				boardDao.loginInsert(loginVo);
				System.out.println("로그인 레드팀 매치 상황");
				return true;
			} else {
				return false;
			}

		} else
			return false;
	}

}
