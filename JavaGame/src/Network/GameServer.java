package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DataBase.BoardDAO;

public class GameServer {

	static Map<String, Socket> userMap = new HashMap();
	static Map<String, Socket> blueTeam = new HashMap<>();
	static Map<String, Socket> redTeam = new HashMap<>();
	static List<ObjectOutputStream> oosPool = new ArrayList<>();

	public static void main(String[] args) {
		ServerSocket ss;
		try {
			ss = new ServerSocket(1234);
			Socket socket = null;
			String clientIP = null;

			while (true) {
				socket = ss.accept();
				clientIP = socket.getInetAddress().getHostAddress();
				System.out.printf("Ŭ���̾�Ʈ(%s) ���ӵ�...\n", clientIP);

				new GameLogin(socket).start(); // �α��ξ�����
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class GameServerThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromClient;
	GameLogicThread gLogicThread;
	int playerNumber;

	GameServerThread(ObjectInputStream fromClient, Socket socket, int playerNumber) {
		this.socket = socket;
		this.fromClient = fromClient;
		this.playerNumber = playerNumber;
		// �÷��̾ ��� ������ Ȯ�εǸ� ���������带 ����
		if (playerNumber == 6) {
			gLogicThread = new GameLogicThread();
			gLogicThread.start(); // ���ӷ���(��,�浹���� ��) ������ ����

			// ��Ʈ�� ����
			Set<String> keys = GameServer.userMap.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String id = it.next();
				Socket soc = GameServer.userMap.get(id);

				ObjectOutputStream toClient;
				try {
					toClient = new ObjectOutputStream(soc.getOutputStream());

					GameServer.oosPool.add(toClient); // ��Ʈ��Ǯ�� �־�α�

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void run() {
		try {
			GameData gData;

			while (true) {
				gData = (GameData) fromClient.readObject();

				if (playerNumber == 6) {
					broadCast(gData); // �α��� ó��
					playerNumber++;
				} else if (gData instanceof PointData) {
					PointData pData = (PointData) gData;
					for (int i = 0; i < GameServer.oosPool.size(); i++) {
						GameServer.oosPool.get(i).reset();
						GameServer.oosPool.get(i).writeObject(pData);
						GameServer.oosPool.get(i).flush();
					}
					/*System.out.println("���� ����� ����Ʈ" + pData.getPoint());
					System.out.println("���� ������ ����Ʈ" + pData.getPoint());*/
					pData.setPoint(0);

				}
				// ������ ����������� ����. - ä�ð� ���ӵ����� ���
				else
					GameLogicThread.serverSetData(gData);

				if (gData.getCountdown() >= 66) {

					if (gData.getClientNum() == 1) {
						String blueId = "";
						String redId = "";
						BoardDAO idSum = new BoardDAO();
						// ����� ���̵� ����
						Set<String> bluekeys = GameServer.blueTeam.keySet();
						Iterator<String> it = bluekeys.iterator();
						while (it.hasNext()) {
							blueId += it.next();
						}
						// ������ ���̵� ����
						Set<String> redkeys = GameServer.redTeam.keySet();
						Iterator<String> it2 = redkeys.iterator();
						while (it2.hasNext()) {
							redId += it2.next();
						}

						idSum.loginUpdate(blueId, "BLUE");
						idSum.loginUpdate(redId, "RED");
						if (gData.getBluePoint() > gData.getRedPoint()) {
							idSum.teamUpdate(gData.getBluePoint(), "��",gData.getPlayTime() ,blueId);
							idSum.teamUpdate(gData.getRedPoint(), "��",gData.getPlayTime() ,redId);
						} else if (gData.getBluePoint() < gData.getRedPoint()) {
							idSum.teamUpdate(gData.getBluePoint(), "��", gData.getPlayTime(), blueId);
							idSum.teamUpdate(gData.getRedPoint(), "��", gData.getPlayTime(), redId);
						} else {
							idSum.teamUpdate(gData.getBluePoint(), "��", gData.getPlayTime(), blueId);
							idSum.teamUpdate(gData.getRedPoint(), "��", gData.getPlayTime(), redId);
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void broadCast(Object d) {
		for (int i = 0; i < GameServer.oosPool.size(); i++) {
			try {
				GameServer.oosPool.get(i).reset();
				GameServer.oosPool.get(i).writeObject(d);
				GameServer.oosPool.get(i).flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
