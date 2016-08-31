package Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import GamePanel.ChatPanel;
import GamePanel.MainPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
	
	//����� �Ѿ� ��ǥ
	public static double buxB;
	public static double buyB;
	public static double bux2B;
	public static double buy2B;
	public static double bux3B;
	public static double buy3B;
	
	//������ �Ѿ� ��ǥ
	public static double buxR;
	public static double buyR;
	public static double bux2R;
	public static double buy2R;
	public static double bux3R;
	public static double buy3R;
	
	//ĳ���� �¿� �̵� �÷���
	public boolean b1MoveFlag;				//�����
	public boolean b2MoveFlag;
	public boolean b3MoveFlag;
	public boolean r1MoveFlag;				//������
	public boolean r2MoveFlag;
	public boolean r3MoveFlag;
	
	MainPanel mp;
	ChatPanel cp;
	GameClient gc;
	// static DataFormat uData;

	ClientComThread(Socket socket, MainPanel mp, GameClient gc) {
		this.socket = socket;
		this.mp = mp;
		this.gc=gc;
	}

	@Override
	public void run() {
		cp = new ChatPanel(gc,mp);
		
		while (true) {
			try {
					fromServer = new ObjectInputStream(socket.getInputStream()); 
					Object obj = fromServer.readObject();

				// Ŭ���̾�Ʈ �α��� �� ä�õ�����
				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;
					//��� �� ���� ����ȭ�� ����.
					if (cData.isAllTeamOK()) {
						GameClient.loadthread.interrupt();			// �ε�ȭ�� ���� - ������ ����
						mp.setVisible(true);						// ����ȭ�� �ٽ� �����ֱ�
						mp.drawingPlayImage();
						mp.eventKey();		//�̺�Ʈ ó���� ���� Ű������
						mp.firstBall();
						mp.add(cp);
						mp.repaint();
					}
					if(cData.getChatMsg()!=null){
						cp.chatAppendMsg(cData);
						cp.repaint();
					}

					// ���� ������ �޴� �κ�
				} else if (obj instanceof GameBroadData) {
					long start = System.currentTimeMillis();
					GameBroadData gbData = (GameBroadData) obj;
					//ĳ���Ͱ� �̵��Ͽ� ��ǥ�����Ͽ� �ٽ� �׷��ش�.
					
					//��� ĳ����
					mp.bCharac1.chx+=gbData.getBlueP1x(); 
					mp.bCharac2.chx+=gbData.getBlueP2x();
					mp.bCharac3.chx+=gbData.getBlueP3x();
					if(gbData.getBlueP1x()!=0)	mp.bCharac1.loop();
					if(gbData.getBlueP2x()!=0)	mp.bCharac2.loop();
					if(gbData.getBlueP3x()!=0) mp.bCharac3.loop();
				
									
					//���� ĳ����
					mp.rCharac1.chx+=gbData.getRedP1x(); 
					mp.rCharac2.chx+=gbData.getRedP2x();
					mp.rCharac3.chx+=gbData.getRedP3x();
					if(gbData.getRedP1x()!=0)	mp.rCharac1.loop();
					if(gbData.getRedP2x()!=0)	mp.rCharac2.loop();
					if(gbData.getRedP3x()!=0) mp.rCharac3.loop();
				
							
					
					// ĳ���� �¿��̵� ��ȯ
					// �����
					if(!b1MoveFlag && gbData.isB1left()){mp.bCharac1.setMove(90, 180);b1MoveFlag = true;}
					else if(b1MoveFlag && gbData.isB1right()){	mp.bCharac1.setMove(0, 90);b1MoveFlag=false;	}
					if(!b2MoveFlag && gbData.isB2left()){mp.bCharac2.setMove(90, 180);b2MoveFlag = true;}
					else if(b2MoveFlag && gbData.isB2right()){	mp.bCharac2.setMove(0, 90);b2MoveFlag=false;	}
					if(!b3MoveFlag && gbData.isB3left()){mp.bCharac3.setMove(90, 180);b3MoveFlag = true;}
					else if(b3MoveFlag && gbData.isB3right()){	mp.bCharac3.setMove(0, 90);b3MoveFlag=false;	}
					
					if(!r1MoveFlag && gbData.isR1left()){mp.rCharac1.setMove(90, 180);r1MoveFlag = true;}
					else if(r1MoveFlag && gbData.isR1right()){	mp.rCharac1.setMove(0, 90);r1MoveFlag=false;	}
					if(!r2MoveFlag && gbData.isR2left()){mp.rCharac2.setMove(90, 180);r2MoveFlag = true;}
					else if(r2MoveFlag && gbData.isR2right()){	mp.rCharac2.setMove(0, 90);r2MoveFlag=false;	}
					if(!r3MoveFlag && gbData.isR3left()){mp.rCharac3.setMove(90, 180);r3MoveFlag = true;}
					else if(r3MoveFlag && gbData.isR3right()){	mp.rCharac3.setMove(0, 90);r3MoveFlag=false;	}
					
					//Ball ���� 
					for(int i =0;i<mp.fb.size();i++){
						if(!mp.fb.get(i).fbswitch)	mp.fb.get(i).loop();	
					}
					for(int i =0;i<mp.sb.size();i++){
						if(!mp.sb.get(i).sbswitch)	mp.sb.get(i).loop();	
					}
					for(int i =0;i<mp.tb.size();i++){
						if(!mp.tb.get(i).tbswitch)	mp.tb.get(i).loop();	

					}
					//Ball ���� 
					
					//�Ѿ� start
					//��� �� �Ѿ�
					if(gbData.isbP1bulletStart()) mp. bP1bullet();
					if(gbData.isbP2bulletStart()) mp. bP2bullet();
					if(gbData.isbP3bulletStart()) mp. bP3bullet();

					//���� �� �Ѿ�
					if(gbData.isrP1bulletStart()) mp. rP1bullet();
					if(gbData.isrP2bulletStart()) mp. rP2bullet();
					if(gbData.isrP3bulletStart()) mp. rP3bullet();
					//�Ѿ� end
					
					//�Ѿ� ���� ����
					//��� 1�� �Ѿ� ����
					for (int i = 0; i < mp.bullet1B.size(); i++) {
						if (mp.bullet1B.size() > 0) {
							mp.bullet1B.get(i).loop();
							buxB = mp.bullet1B.get(i).x;
							buyB = mp.bullet1B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ��� 2�� �Ѿ� ����
					for (int i = 0; i < mp.bullet2B.size(); i++) {
						if (mp.bullet2B.size() > 0) {
							mp.bullet2B.get(i).loop();
							bux2B = mp.bullet2B.get(i).x;
							buy2B = mp.bullet2B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ��� 3�� �Ѿ� ����
					for (int i = 0; i < mp.bullet3B.size(); i++) {
						if (mp.bullet3B.size() > 0) {
							mp.bullet3B.get(i).loop();
							bux3B = mp.bullet3B.get(i).x;
							buy3B = mp.bullet3B.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					
					//������ 1�� �Ѿ� ����
					for (int i = 0; i < mp.bullet1R.size(); i++) {
						if (mp.bullet1R.size() > 0) {
							mp.bullet1R.get(i).loop();
							buxR = mp.bullet1R.get(i).x;
							buyR = mp.bullet1R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ���� 2�� �Ѿ� ����
					for (int i = 0; i < mp.bullet2R.size(); i++) {
						if (mp.bullet2R.size() > 0) {
							mp.bullet2R.get(i).loop();
							bux2R = mp.bullet2R.get(i).x;
							buy2R = mp.bullet2R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
					// ���� 3�� �Ѿ� ����
					for (int i = 0; i < mp.bullet3R.size(); i++) {
						if (mp.bullet3R.size() > 0) {
							mp.bullet3R.get(i).loop();
							bux3R = mp.bullet3R.get(i).x;
							buy3R = mp.bullet3R.get(i).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
						}
					}
			
					//�Ѿ� ���� ��
				
					mp.repaint();

					long end = System.currentTimeMillis();
					System.out.printf("Ŭ���̾�Ʈ���� �޴µ� �ɸ��ð�:%d\n",end-start);
				}
				} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
