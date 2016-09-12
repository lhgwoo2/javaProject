package Network;

import java.util.Iterator;
import java.util.Set;

public class GameLogicThread extends Thread {
	public static GameData gData;
	
	public double countdownS=0;				// 1�ʿ� ������ �� ī��Ʈ�ٿ�
	public double countdownM=0;					// ����Ǵ� �ð� ī��Ʈ �ٿ�
	
	
	String blueId;
	String redId;

	public GameLogicThread() {
		super();

		gData = new GameData();
		gData.setTeamColor("BLUE");
		gData.setTeamNum(1);
	}

	public static void serverSetData(GameData Data) {

		GameLogicThread.gData = Data;

	}

	@Override
	public void run() {
		super.run();
		
		blueId = "";
		redId = "";
		Set<String> bluekeys = GameServer.blueTeam.keySet();
		Iterator<String> it = bluekeys.iterator();
		while (it.hasNext()) {
			blueId += it.next()+"    ";

		}
		// ������ ���̵� ����
		Set<String> redkeys = GameServer.redTeam.keySet();
		Iterator<String> it2 = redkeys.iterator();
		while (it2.hasNext()) {
			redId += it2.next()+"    ";
		}
		gData.setId1(blueId);
		gData.setId2(redId);

		while (true) {
			
			
			try {
				synchronized (gData) {
				
					if(countdownM>=66 || gData.getCountdown()>=66){ 
						break;
					}
					countdownS++;
					if(countdownS==34){
						countdownM++;
						countdownS=0;
					}
					gData.setCountdown(countdownM);
					for (int i = 0; i < GameServer.oosPool.size(); i++) {
						GameServer.oosPool.get(i).reset();
						GameServer.oosPool.get(i).writeObject(gData);
						GameServer.oosPool.get(i).flush();
					}
					gData.setChx(0);
					gData.setBulletStart(false);
					gData.setChMsg(null);
				}
				//���� ���� ī��Ʈ
				
					
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
