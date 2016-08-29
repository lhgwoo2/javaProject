package Network;

public class GameLogicThread extends Thread {

	//캐릭터별 x좌표
	public static double blueP1x=0.0;
	public static double blueP2x=0.0;
	public static double blueP3x=0.0;
	
	public static double redP1x=0.0;
	public static double redP2x=0.0;
	public static double redP3x=0.0;
	
	//각캐릭터 총알 좌표
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;
	//전체 총알 속도
	public static double bulletSpeed;
	public static boolean bP1BulletStart;
	public static boolean bP2BulletStart;
	
	public GameLogicThread() {
		super();
	}
	
	public static void serverSetData(GameData gData){
		
		
		if(gData.getTeamColor().equals("Blue")){
			if(gData.getTeamNum()==1){
				if(gData.getChx()!=0){
					blueP1x+=gData.getChx();
					System.out.println("bluep1x로 갑이 들어옴.");
				}
				if(gData.isBulletStart()){ //총알이 발사 됬는지 확인함!
					bP1BulletStart=gData.isBulletStart();
				}
			}else if(gData.getTeamNum()==2){
				if(gData.getChx()!=0){
					blueP2x+=gData.getChx();
				}
				if(gData.isBulletStart()){ //총알이 발사 됬는지 확인함!
					bP2BulletStart=gData.isBulletStart();
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
			gbData.setbP1bulletStart(bP1BulletStart); //만약 블루 1번이 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			gbData.setbP2bulletStart(bP2BulletStart); //만약 블루 2번이 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			
			blueP1x = 0;		//캐릭터 속도초기화
			blueP2x = 0;		//캐릭터 속도초기화
			bP1BulletStart= false; //블루1번 총알 초기화
			bP2BulletStart= false; //블루2번 총알 초기화
			GameServerThread.gDatabroadCast(gbData);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
