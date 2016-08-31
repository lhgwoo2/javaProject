package Network;

public class GameLogicThread extends Thread {

	// ĳ���ͺ� x��ǥ
	// ��� ĳ���� x ��ǥ
	public static double blueP1x;
	public static double blueP2x;
	public static double blueP3x;
	// ���� ĳ���� x ��ǥ
	public static double redP1x;
	public static double redP2x;
	public static double redP3x;
	// ĳ���ͺ� x��ǥ ��

	// ĳ���� �� �¿������
	// �����
	public static boolean b1right; // ������
	public static boolean b2right;
	public static boolean b3right;
	public static boolean b1left; // ����
	public static boolean b2left;
	public static boolean b3left;

	// ������
	public static boolean r1right; // ������
	public static boolean r2right;
	public static boolean r3right;
	public static boolean r1left; // ����
	public static boolean r2left;
	public static boolean r3left;
	//ĳ���� �¿� �̵� ��
	
	
	// ��ĳ���� �Ѿ� ��ǥ
	public static double blueBulletX1;
	public static double blueBulletY1;
	public static double blueBulletX2;
	public static double blueBulletY2;
	public static double blueBulletX3;
	public static double blueBulletY3;

	// �Ѿ� ��ŸƮ!
	// ����� �Ѿ�
	public static boolean bP1BulletStart;
	public static boolean bP2BulletStart;
	public static boolean bP3BulletStart;
	// ������ �Ѿ�
	public static boolean rP1BulletStart;
	public static boolean rP2BulletStart;
	public static boolean rP3BulletStart;

	// �Ѿ� ��!

	public GameLogicThread() {
		super();
	}

	public static void serverSetData(GameData gData) {

		// ����϶�!
		if (gData.getTeamColor().equals("Blue")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					blueP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP1BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					b1right=true;					
					b1left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
					b1right=false;
					b1left=true;
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					blueP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP2BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					b2right=true;					
					b2left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
					b2right=false;
					b2left=true;
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					blueP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					bP3BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					b3right=true;					
					b3left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
					b3right=false;
					b3left=true;
				}
			}
		}

		// �����϶� !
		if (gData.getTeamColor().equals("Red")) {
			if (gData.getTeamNum() == 1) {
				if (gData.getChx() != 0) {
					redP1x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP1BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					r1right=true;					
					r1left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
					r1right=false;
					r1left=true;
				}
			}
			if (gData.getTeamNum() == 2) {
				if (gData.getChx() != 0) {
					redP2x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP2BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					r2right=true;					
					r2left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
					r2right=false;
					r2left=true;
				}
			}
			if (gData.getTeamNum() == 3) {
				if (gData.getChx() != 0) {
					redP3x += gData.getChx();
				}
				if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
					rP3BulletStart = gData.isBulletStart();
				}
				if (gData.isRightMove() == true) {// ������ �������� ����
					r3right=true;					
					r3left=false;
				}
				if(gData.isLeftMove()==true){// ���� �������� ����
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
			// �����ĳ���� x��ǥ
			gbData.setBlueP1x(blueP1x);
			gbData.setBlueP2x(blueP2x);
			gbData.setBlueP3x(blueP3x);
			// ������ĳ���� x��ǥ
			gbData.setRedP1x(redP1x);
			gbData.setRedP2x(redP2x);
			gbData.setRedP3x(redP3x);
			end= System.currentTimeMillis();
			System.out.printf("�������� ĳ������ǥ �ɸ��ð�:%d\n",end-start);
			
			start = System.currentTimeMillis();
			// ���� ��� �Ѿ��� �߻���ٸ� ���⼭ true ������ �����͸� ������ �ƴϸ� false
			gbData.setbP1bulletStart(bP1BulletStart);
			gbData.setbP2bulletStart(bP2BulletStart);
			gbData.setbP3bulletStart(bP3BulletStart);
			// ���� ���� �Ѿ��� �߻���ٸ� ���⼭ true ������ �����͸� ������ �ƴϸ� false
			gbData.setrP1bulletStart(rP1BulletStart);
			gbData.setrP2bulletStart(rP2BulletStart);
			gbData.setrP3bulletStart(rP3BulletStart);
			end= System.currentTimeMillis();
			System.out.printf("�������� �Ѿ�Ž�� �ɸ��ð�:%d\n",end-start);
			
			start = System.currentTimeMillis();
			//ĳ���� �¿� ������ ������ ����
			//���
			gbData.setB1left(b1left);
			gbData.setB1right(b1right);
			gbData.setB2left(b2left);
			gbData.setB2right(b2right);
			gbData.setB3left(b3left);
			gbData.setB3right(b3right);
			//����
			gbData.setR1left(r1left);
			gbData.setR1right(r1right);
			gbData.setR2left(r2left);
			gbData.setR2right(r2right);
			gbData.setR3left(r3left);
			gbData.setR3right(r3right);
			end= System.currentTimeMillis();
			System.out.printf("�������� �¿������ �ɸ��ð�:%d\n",end-start);
			
			
			start = System.currentTimeMillis();
			
			GameServerThread.gDatabroadCast(gbData);
			
			end= System.currentTimeMillis();
			System.out.printf("�������� �����Ȱ��������� �ɸ��ð�:%d\n",end-start);
			
			start = System.currentTimeMillis();
			// ��� ĳ���� �ӵ��ʱ�ȭ
			blueP1x = 0;
			blueP2x = 0;
			blueP3x = 0;
			// ���� ĳ���� �ӵ��ʱ�ȭ
			redP1x = 0;
			redP2x = 0;
			redP3x = 0;
			// ��� �Ѿ� �ʱ�ȭ
			bP1BulletStart = false;
			bP2BulletStart = false;
			bP3BulletStart = false;
			// ���� �Ѿ� �ʱ�ȭ
			rP1BulletStart = false;
			rP2BulletStart = false;
			rP3BulletStart = false;
			end= System.currentTimeMillis();
			System.out.printf("�������� ���ʱ�ȭ �ɸ��ð�:%d\n",end-start);
			

			try {
				Thread.sleep(30); 
				long end1 = System.currentTimeMillis();
				System.out.printf("�������� �����µ� �ɸ��ð�:%d\n",end1-start1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
