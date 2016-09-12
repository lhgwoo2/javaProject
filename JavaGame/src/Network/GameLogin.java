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
	// �÷��̾���� �� Ȯ��
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
					System.out.println("�α��� ����");
					// cData.setMsg("�α��� ����");
					cData.setLoginOK(true);
					cData.setClientBlueNum(GameServer.blueTeam.size());
					cData.setClientRedNum(GameServer.redTeam.size());
					System.out.println("�α� Ŭ��� ������");

					toClient.writeObject(cData);
					toClient.flush();
					// toClient.reset();
					System.out.println("�α� Ŭ��� ������ ����");
					GameServer.userMap.put(cData.getUserId(), socket);
					new GameServerThread(fromClient, socket, playerNumber).start();
					System.out.println("���� ��� ������ ����");
					break;

				} else {
					System.out.println("�α��� ����");
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

		System.out.println("���Ͽ��� �α��� ��ġ�ϴ� ��");
		List<LoginVO> check = boardDao.loginIdCheck();
		for (int i = 0; i < check.size(); i++) {

			if (cData.getUserId().equals(check.get(i).getUserId())) {
				System.out.println(check.get(i).getUserId());
				System.out.println(cData.getUserId());
				System.out.println("�α��� ��ġ ����Ʈ ���̵� ���� ��Ȳ");
				return false; // ��ϵ� ���̵� ������ ����
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

				// ����� ���� ��Ȳ Ȯ��.
				GameServer.blueTeam.put(cData.getUserId(), socket);
				// ������� �̿��� �ڽ��� ������ �� �ľ��� ���� ������ ���� - ĳ���͹����� ����
				cData.setTeamColor("BLUE");
				cData.setTeamNum(GameServer.blueTeam.size());
				playerNumber += 1; // ������ �÷��̾� �߰�
				System.out.println(playerNumber);
				// �α��� color ����
				LoginVO loginVo = new LoginVO();
				loginVo.setUserId(cData.getUserId());
				loginVo.setTeamColor(cData.getTeamColor());
				boardDao.loginInsert(loginVo);
				System.out.println("�α��� ����� ��ġ ��Ȳ");

				return true;
			} else {
				return false;
			}
		} else if (team.equals("Red")) {
			if (GameServer.redTeam.size() < 3) {

				// Red���� ���� ��Ȳ Ȯ��.
				GameServer.redTeam.put(cData.getUserId(), socket);
				// ������� �̿��� �ڽ��� ������ �� �ľ��� ���� ������ ���� - ĳ���͹����� ����
				cData.setTeamColor("RED");
				cData.setTeamNum(GameServer.redTeam.size());
				playerNumber += 1; // ������ �÷��̾� �߰�
				LoginVO loginVo = new LoginVO();
				loginVo.setUserId(cData.getUserId());
				loginVo.setTeamColor(cData.getTeamColor());
				boardDao.loginInsert(loginVo);
				System.out.println("�α��� ������ ��ġ ��Ȳ");
				return true;
			} else {
				return false;
			}

		} else
			return false;
	}

}
