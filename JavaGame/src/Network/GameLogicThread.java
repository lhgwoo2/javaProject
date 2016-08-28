package Network;

public class GameLogicThread extends Thread {

	//캐릭터별 x좌표
	public double blueP1x;
	public double blueP2x;
	public double blueP3x;
	
	public double redP1x;
	public double redP2x;
	public double redP3x;
	
	//각캐릭터 총알 좌표
	public double blueBulletX1;
	public double blueBulletY1;
	public double blueBulletX2;
	public double blueBulletY2;
	public double blueBulletX3;
	public double blueBulletY3;
	//전체 총알 속도
	public double bulletSpeed;
	
	public GameLogicThread() {
		super();
	}
	
	public static void serverSetData(GameData gData){
		
	}
	@Override
	public void run() {
		super.run();
		
		while(true){
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
