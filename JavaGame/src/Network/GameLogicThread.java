package Network;

public class GameLogicThread extends Thread {

	//캐릭터별 x좌표
	//블루 캐릭터 x 좌표
	public static double blueP1x;
	public static double blueP2x;
	public static double blueP3x;
	//레드 캐릭터 x 좌표
	public static double redP1x;
	public static double redP2x;
	public static double redP3x;
	//캐릭터별 x좌표 끝
	
	
	//각캐릭터 총알 좌표
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;

	
	
	//총알 스타트!
	//블루팀 총알
	public static boolean bP1BulletStart;
	public static boolean bP2BulletStart;
	public static boolean bP3BulletStart;
	//레드팀 총알
	public static boolean rP1BulletStart;
	public static boolean rP2BulletStart;
	public static boolean rP3BulletStart;
	
	//총알 끝!
	
	public GameLogicThread() {
		super();
	}
	
	public static void serverSetData(GameData gData){
		
		// 블루일때!
		if (gData.getTeamColor().equals("Blue")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					blueP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP1BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					blueP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP2BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					blueP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP3BulletStart = gData.isBulletStart();
				}
			}
		}
		
		
		//레드일때 !
		if (gData.getTeamColor().equals("Red")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					redP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP1BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					redP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP2BulletStart = gData.isBulletStart();
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					redP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP3BulletStart = gData.isBulletStart();
				}
			}
		}
	}
	@Override
	public void run() {
		super.run();
		
		while(true){
			GameBroadData gbData = new GameBroadData();

			// 블루팀캐릭터 x좌표
			gbData.setBlueP1x(blueP1x);
			gbData.setBlueP2x(blueP2x);
			gbData.setBlueP3x(blueP3x);
			// 레드팀캐릭터 x좌표
			gbData.setRedP1x(redP1x);
			gbData.setRedP2x(redP2x);
			gbData.setRedP3x(redP3x);

			// 만약 블루 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			gbData.setbP1bulletStart(bP1BulletStart);
			gbData.setbP2bulletStart(bP2BulletStart);
			gbData.setbP3bulletStart(bP3BulletStart);
			// 만약 레드 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			gbData.setrP1bulletStart(rP1BulletStart);
			gbData.setrP2bulletStart(rP2BulletStart);
			gbData.setrP3bulletStart(rP3BulletStart);

			// 블루 캐릭터 속도초기화
			blueP1x = 0;
			blueP2x = 0;
			blueP3x = 0;
			// 레드 캐릭터 속도초기화
			redP1x = 0;
			redP2x = 0;
			redP3x = 0;

			// 블루 총알 초기화
			bP1BulletStart = false;
			bP2BulletStart = false;
			bP3BulletStart = false;
			// 레드 총알 초기화
			rP1BulletStart = false;
			rP2BulletStart = false;
			rP3BulletStart = false;
			
			
			GameServerThread.gDatabroadCast(gbData);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
