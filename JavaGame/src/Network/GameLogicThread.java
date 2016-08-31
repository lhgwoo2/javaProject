package Network;

public class GameLogicThread extends Thread {

	// 캐릭터별 x좌표
	// 블루 캐릭터 x 좌표
	public static double blueP1x;
	public static double blueP2x;
	public static double blueP3x;
	// 레드 캐릭터 x 좌표
	public static double redP1x;
	public static double redP2x;
	public static double redP3x;
	// 캐릭터별 x좌표 끝

	// 캐릭터 별 좌우움직임
	// 블루팀
	public static boolean b1right; // 오른쪽
	public static boolean b2right;
	public static boolean b3right;
	public static boolean b1left; // 왼쪽
	public static boolean b2left;
	public static boolean b3left;

	// 레드팀
	public static boolean r1right; // 오른쪽
	public static boolean r2right;
	public static boolean r3right;
	public static boolean r1left; // 왼쪽
	public static boolean r2left;
	public static boolean r3left;
	//캐릭터 좌우 이동 끝
	
	
	// 각캐릭터 총알 좌표
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;

	// 총알 스타트!
	// 블루팀 총알
	public static boolean bP1BulletStart;
	public static boolean bP2BulletStart;
	public static boolean bP3BulletStart;
	// 레드팀 총알
	public static boolean rP1BulletStart;
	public static boolean rP2BulletStart;
	public static boolean rP3BulletStart;

	// 총알 끝!

	public GameLogicThread() {
		super();
	}

	public static void serverSetData(GameData gData) {

		// 블루일때!
		if (gData.getTeamColor().equals("Blue")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					blueP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP1BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					b1right=true;					
					b1left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					b1right=false;
					b1left=true;
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					blueP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP2BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					b2right=true;					
					b2left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					b2right=false;
					b2left=true;
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					blueP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					bP3BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					b3right=true;					
					b3left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					b3right=false;
					b3left=true;
				}
			}
		}

		// 레드일때 !
		if (gData.getTeamColor().equals("Red")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					redP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP1BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					r1right=true;					
					r1left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					r1right=false;
					r1left=true;
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					redP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP2BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					r2right=true;					
					r2left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					r2right=false;
					r2left=true;
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					redP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
					rP3BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// 오른쪽 움직임이 감지
					r3right=true;					
					r3left=false;
				}
				if(gData.isLeftMove()==true){// 왼쪽 움직임이 감지
					r3right=false;
					r3left=true;
				}
			}
		}
	}

	@Override
	public void run() {
		super.run();
		GameBroadData gbData = new GameBroadData();
		long start;
		long end;
		while (true) {
			long start1 = System.currentTimeMillis();
			
			start = System.currentTimeMillis();
			// 블루팀캐릭터 x좌표
			gbData.setBlueP1x(blueP1x);
			gbData.setBlueP2x(blueP2x);
			gbData.setBlueP3x(blueP3x);
			// 레드팀캐릭터 x좌표
			gbData.setRedP1x(redP1x);
			gbData.setRedP2x(redP2x);
			gbData.setRedP3x(redP3x);
			end= System.currentTimeMillis();
			System.out.printf("서버에서 캐릭터좌표 걸린시간:%d\n",end-start);
			
			start = System.currentTimeMillis();
			// 만약 블루 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			gbData.setbP1bulletStart(bP1BulletStart);
			gbData.setbP2bulletStart(bP2BulletStart);
			gbData.setbP3bulletStart(bP3BulletStart);
			// 만약 레드 총알이 발생됬다면 여기서 true 값으로 데이터를 보내고 아니면 false
			gbData.setrP1bulletStart(rP1BulletStart);
			gbData.setrP2bulletStart(rP2BulletStart);
			gbData.setrP3bulletStart(rP3BulletStart);
			end= System.currentTimeMillis();
			System.out.printf("서버에서 총알탐지 걸린시간:%d\n",end-start);
			
			start = System.currentTimeMillis();
			//캐릭터 좌우 움직임 데이터 전송
			//블루
			gbData.setB1left(b1left);
			gbData.setB1right(b1right);
			gbData.setB2left(b2left);
			gbData.setB2right(b2right);
			gbData.setB3left(b3left);
			gbData.setB3right(b3right);
			//레드
			gbData.setR1left(r1left);
			gbData.setR1right(r1right);
			gbData.setR2left(r2left);
			gbData.setR2right(r2right);
			gbData.setR3left(r3left);
			gbData.setR3right(r3right);
			end= System.currentTimeMillis();
			System.out.printf("서버에서 좌우움직임 걸린시간:%d\n",end-start);
			
			
			start = System.currentTimeMillis();
			
			GameServerThread.gDatabroadCast(gbData);
			
			end= System.currentTimeMillis();
			System.out.printf("서버에서 보정된값을보낸다 걸린시간:%d\n",end-start);
			
			start = System.currentTimeMillis();
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
			end= System.currentTimeMillis();
			System.out.printf("서버에서 값초기화 걸린시간:%d\n",end-start);
			

			try {
				Thread.sleep(30); 
				long end1 = System.currentTimeMillis();
				System.out.printf("서버에서 보내는데 걸린시간:%d\n",end1-start1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
