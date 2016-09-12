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
				System.out.printf("클라이언트(%s) 접속됨...\n", clientIP);

				new GameLogin(socket).start(); // 로그인쓰레드
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
		// 플레이어가 모두 입장이 확인되면 로직쓰레드를 실행
		if (playerNumber == 6) {
			gLogicThread = new GameLogicThread();
			gLogicThread.start(); // 게임로직(볼,충돌로직 등) 쓰레드 시작

			// 스트림 생성
			Set<String> keys = GameServer.userMap.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String id = it.next();
				Socket soc = GameServer.userMap.get(id);

				ObjectOutputStream toClient;
				try {
					toClient = new ObjectOutputStream(soc.getOutputStream());

					GameServer.oosPool.add(toClient); // 스트림풀에 넣어두기

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
					broadCast(gData); // 로그인 처리
					playerNumber++;
				} else if (gData instanceof PointData) {
					PointData pData = (PointData) gData;
					for (int i = 0; i < GameServer.oosPool.size(); i++) {
						GameServer.oosPool.get(i).reset();
						GameServer.oosPool.get(i).writeObject(pData);
						GameServer.oosPool.get(i).flush();
					}
					/*System.out.println("서버 블루팀 포인트" + pData.getPoint());
					System.out.println("서버 레드팀 포인트" + pData.getPoint());*/
					pData.setPoint(0);

				}
				// 데이터 로직쓰레드로 보냄. - 채팅과 게임데이터 모두
				else
					GameLogicThread.serverSetData(gData);

				if (gData.getCountdown() >= 66) {

					if (gData.getClientNum() == 1) {
						String blueId = "";
						String redId = "";
						BoardDAO idSum = new BoardDAO();
						// 블루팀 아이디 결합
						Set<String> bluekeys = GameServer.blueTeam.keySet();
						Iterator<String> it = bluekeys.iterator();
						while (it.hasNext()) {
							blueId += it.next();
						}
						// 레드팀 아이디 결합
						Set<String> redkeys = GameServer.redTeam.keySet();
						Iterator<String> it2 = redkeys.iterator();
						while (it2.hasNext()) {
							redId += it2.next();
						}

						idSum.loginUpdate(blueId, "BLUE");
						idSum.loginUpdate(redId, "RED");
						if (gData.getBluePoint() > gData.getRedPoint()) {
							idSum.teamUpdate(gData.getBluePoint(), "승",gData.getPlayTime() ,blueId);
							idSum.teamUpdate(gData.getRedPoint(), "패",gData.getPlayTime() ,redId);
						} else if (gData.getBluePoint() < gData.getRedPoint()) {
							idSum.teamUpdate(gData.getBluePoint(), "패", gData.getPlayTime(), blueId);
							idSum.teamUpdate(gData.getRedPoint(), "승", gData.getPlayTime(), redId);
						} else {
							idSum.teamUpdate(gData.getBluePoint(), "무", gData.getPlayTime(), blueId);
							idSum.teamUpdate(gData.getRedPoint(), "무", gData.getPlayTime(), redId);
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
