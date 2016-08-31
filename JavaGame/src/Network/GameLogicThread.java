package Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

public class GameLogicThread extends Thread {
	public static GameData gData;
	
	public GameLogicThread() {
		super();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		gData = new GameData();
		gData.setTeamColor("Blue");
		gData.setTeamNum(1);
	}

	public static void serverSetData(GameData gData) {
		GameLogicThread.gData=gData;
	}

	@Override
	public void run() {
		super.run();
		while (true) {

			Set<String> keys = GameServer.userMap.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String id = it.next();
				Socket soc = GameServer.userMap.get(id);
				ObjectOutputStream toClient;
				try {
					toClient = new ObjectOutputStream(soc.getOutputStream());
					toClient.writeObject(gData);
					toClient.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(gData!=null){
				gData.setChx(0);
				gData.setBulletStart(false);
			}

		}
	}

}
