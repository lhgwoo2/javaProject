package Network;

public class GameLogicThread extends Thread {

	//Ä³¸¯ÅÍº° xÁÂÇ¥
	public static double blueP1x;
	public static double blueP2x;
	public static double blueP3x;
	
	public static double redP1x;
	public static double redP2x;
	public static double redP3x;
	
	//°¢Ä³¸¯ÅÍ ÃÑ¾Ë ÁÂÇ¥
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;
	//ÀüÃ¼ ÃÑ¾Ë ¼Óµµ
	public static double bulletSpeed;
	
	public GameLogicThread() {
		super();
	}
	
	public static void serverSetData(GameData gData){
		if(gData.getTeamColor().equals("Blue")){
			if(gData.getTeamNum()==1){
				if(gData.getChx()!=0){
					blueP1x+=gData.getChx();
					System.out.println("bluep1x·Î °©ÀÌ µé¾î¿È.");
				}
			}else if(gData.getTeamNum()==2){
				if(gData.getChx()!=0){
					blueP2x+=gData.getChx();
				}
			}
		}
	}
	@Override
	public void run() {
		super.run();
		
		while(true){
			GameBroadData gbData = new GameBroadData();
			gbData.setBlueP1x(blueP1x);
			gbData.setBlueP2x(blueP2x);
			GameServerThread.gDatabroadCast(gbData);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
