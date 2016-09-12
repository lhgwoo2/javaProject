package Network;

import java.io.ObjectInputStream;
import java.net.Socket;

import Charactor.BlueCharacter1;
import Charactor.BlueCharacter2;
import Charactor.BlueCharacter3;
import Charactor.RedCharacter1;
import Charactor.RedCharacter2;
import Charactor.RedCharacter3;
import GamePanel.ChatPanel;
import GamePanel.CountThread;
import GamePanel.GameDataPanel;
import GamePanel.GameOverPanel;
import GamePanel.LoginPanel;
import GamePanel.MainPanel;
import GamePanel.PointPanel;

public class ClientComThread extends Thread {
	private Socket socket;
	private ObjectInputStream fromServer;
	Object obj;

	// ����� �Ѿ� ��ǥ
	public static double buxB;
	public static double buyB = 1000; // 1000���� �������� ó���� �浹�� ���� ���ؼ�
	public static double bux2B;
	public static double buy2B = 1000;
	public static double bux3B;
	public static double buy3B = 1000;

	// ������ �Ѿ� ��ǥ
	public static double buxR;
	public static double buyR = 1000;
	public static double bux2R;
	public static double buy2R = 1000;
	public static double bux3R;
	public static double buy3R = 1000;

	// ĳ���� �¿� �̵� �÷���
	public boolean b1MoveFlag; // �����
	public boolean b2MoveFlag;
	public boolean b3MoveFlag;
	public boolean r1MoveFlag; // ������
	public boolean r2MoveFlag;
	public boolean r3MoveFlag;

	// ���� ��ǥ
	double fbx;
	double fby;
	double sbx;
	double sby;
	double tbx;
	double tby;
	double fthbx;
	double fthby;
	// ���� ��ǥ

	// �ɸ��ͺ��� �׾������ �ð� üũ
	public long bcd1;
	public long bcd2;
	public long bcd3;
	public long rcd1;
	public long rcd2;
	public long rcd3;
	// �ɸ��ͺ��� �׾������ �ð� üũ

	// ĳ���Ͱ� �׾��� ��� �����ϴ� ��
	public static boolean bcd1death;
	public static boolean bcd2death;
	public static boolean bcd3death;
	public static boolean rcd1death;
	public static boolean rcd2death;
	public static boolean rcd3death;

	// ĳ���Ͱ� �׾��� ��� �����ϴ� ��

	// �׾������ Cooldown time
	public long cdtb1;
	public long cdtb2;
	public long cdtb3;
	public long cdtr1;
	public long cdtr2;
	public long cdtr3;
	// �׾������ Cooldown time

	// ��ų
	public static boolean shieldblue; // ��罯�彺ų
	public static long shieldTimeblue;
	public static boolean shieldred; // ���彯�彺ų
	public static long shieldTimered;

	public static boolean speedblue; // ��� ���ǵ� ��ų
	public static long speedTimeblue;
	public static boolean speedred; // ���� ���ǵ� ��ų
	public static long speedTimered;

	public static boolean snowblue; // ��� ���� ��ų
	public static long snowTimeblue;
	public static boolean snowred; // ���� ���� ��ų
	public static long snowTimered;

	public static boolean brickblue; // ��� ���� ��ų
	public static long brickTimeblue;
	public static boolean brickred; // ���� ���� ��ų
	public static long brickTimered;
	
	
	public boolean flag;
	// ��ų

	MainPanel mp;

	GameClient gc;
	// static DataFormat uData;
	public boolean oisFlag; // ��Ʈ�� ���� �÷���
	public boolean clientFlag; // ó�� ȭ����ȯ �÷���
	long countTime; // ī��Ʈ �ð� ����
	double countdownFlag; // ī��Ʈ �÷���
	public CountThread cntThread; // ī��Ʈ ������
	public boolean countFlag; // ���� ī��Ʈ

	ChatPanel cp;
	public GameDataPanel gdp; // game ������ �г�
	public PointPanel ppl; // ���� ���ھ� ǥ�� �г�

	// ����Ʈ
	public int bluePoint;
	public int redPoint;

	ClientComThread(Socket socket, MainPanel mp, GameClient gc) {
		this.socket = socket;
		this.mp = mp;
		this.gc = gc;

	}

	@Override
	public void run() {
		cp = new ChatPanel(gc, mp);
		gdp = new GameDataPanel(mp); // ��ų, �ð�â
		while (true) {
			try {
				if (!oisFlag) {
					fromServer = new ObjectInputStream(socket.getInputStream());
					oisFlag = true;
				}
				obj = fromServer.readObject();
				
				if (obj instanceof PointData) {
					PointData pData = (PointData) obj;
					if (pData.getTeamColor().equals("BLUE")) {
						ppl.blueScore += pData.getPoint();
					} else {
						ppl.redScore += pData.getPoint();
					}
					System.out.println("����� ����Ʈ"+ppl.blueScore);
					System.out.println("������ ����Ʈ"+ppl.redScore);
					ppl.insertScore();
					ppl.repaint();
				} 

				// GameData gData = (GameData) obj;

				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;

					if (cData.isAllTeamOK()) {
						gc.loadthread.interrupt(); // �ε�ȭ�� ���� - ������ ����
						mp.setVisible(true); // ����ȭ�� �ٽ� �����ֱ�
						mp.drawingPlayImage();
						mp.setFocusable(true);
						mp.requestFocus();
						mp.firstBall();
						mp.add(cp);
						mp.add(gdp); // ��ų, �ð� �� ���� â
						mp.eventKey(); // �̺�Ʈ ó���� ���� Ű������
						ppl = new PointPanel(mp);
						mp.repaint();
						
						mp.clip.close();
						
						// ī���� ������ ����
						countTime = System.currentTimeMillis() + 5000;
						cntThread = new CountThread(mp);
						cntThread.start();
						
						mp.sound("count.wav");
						mp.mainsound("MainBGM.wav");
					}
					cData = null;
					clientFlag = true;
					//continue;

				} else {
					GameData gData = (GameData) obj;
					
					//���̵� ǥ��
					if(!flag){
					mp.label.setText("BLUE��:    "+gData.getId1());
					mp.labellabel.setText("RED ��:    "+gData.getId2());
					flag = true;
					}

					synchronized (gData) {
					

						///////////////////// // �ð� ī��Ʈ �г� ���
						if (gData.getCountdown() >= 6 && gData.getCountdown() < 66) {
							if (countdownFlag != gData.getCountdown()) {
								countdownFlag = gData.getCountdown();
								gdp.loop();
							}
							if(ppl.blueScore+ppl.redScore>=6000){						// �ð��� ������ ���� ���� �� ������ ��� ������.
								GameOverPanel gop = new GameOverPanel(mp, ppl);
								mp.add(gop);
								mp.repaint();
								System.out.println("�÷��� �ð�" + gData.getCountdown());
								gData.setPlayTime(gData.getCountdown()-6);
								gData.setCountdown(66);
								gData.setClientNum(LoginPanel.gClient.clientNumber);
								gData.setBluePoint(ppl.blueScore);
								gData.setRedPoint(ppl.redScore);
								LoginPanel.gClient.sendGameData(gData);
								mp.mainclip.close();
								break;
							}
						} else if (gData.getCountdown() == 66) { // ���� ���� �г�
							GameOverPanel gop = new GameOverPanel(mp, ppl);
							mp.add(gop);
							mp.repaint();
							gData.setPlayTime(60);							// ���� �ð� 60�� 
							gData.setClientNum(LoginPanel.gClient.clientNumber);
							gData.setBluePoint(ppl.blueScore);
							gData.setRedPoint(ppl.redScore);
							LoginPanel.gClient.sendGameData(gData);
							mp.mainclip.close();
							break; // Ŭ���̾�Ʈ ������ ����
						}
						if (gData.getChMsg() != null) {					//ä�� â
							cp.chatAppendMsg(gData);
							cp.repaint();
							mp.repaint();
						}

						if (!countFlag) {
							// ī��Ʈ�ð����� ����ð��� ������ ī���;����� ����
							if (countTime <= System.currentTimeMillis()) {
								cntThread.interrupt();
								mp.remove(cntThread.cntP);
								countFlag = true;
							}
						}

						if (gData.getTeamColor().equals("BLUE")) {
							if (gData.getTeamNum() == 1 && !bcd1death) {
								// ���ǵ� ��ų���
								if (gData.isSpeedblue()) {
									speedblue = gData.isSpeedblue();
									speedTimeblue = System.currentTimeMillis();
								}
								if (gData.getChx() != 0) {
									mp.bCharac1.chx += gData.getChx();
									mp.bCharac1.loop();
									gData.setChx(0);
									if (!b1MoveFlag && gData.isLeftMove()) {
										mp.bCharac1.setMove(90, 180);
										b1MoveFlag = true;
									} else if (b1MoveFlag && gData.isRightMove()) {
										mp.bCharac1.setMove(0, 90);
										b1MoveFlag = false;
									}
								}
								if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
									mp.bP1bullet();
								}
							}
							if (gData.getTeamNum() == 2 && !bcd2death) {
								if (gData.getChx() != 0) {
									mp.bCharac2.chx += gData.getChx();
									mp.bCharac2.loop();
									gData.setChx(0);
									if (!b2MoveFlag && gData.isLeftMove()) {
										mp.bCharac2.setMove(90, 180);
										b2MoveFlag = true;
									} else if (b2MoveFlag && gData.isRightMove()) {
										mp.bCharac2.setMove(0, 90);
										b2MoveFlag = false;
									}
								}
								if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
									mp.bP2bullet();
								}
								if (gData.isShieldblue()) { // ���� ��ų
									shieldblue = gData.isShieldblue();
									shieldTimeblue = System.currentTimeMillis();
								}
							}
							if (gData.getTeamNum() == 3 && !bcd3death && !brickblue) { // ���࿡
																						// ������
																						// �������̶��
																						// ��������
																						// ���ϵ���
																						// !brickblue
																						// ��
																						// ��
								if (gData.getChx() != 0) {
									mp.bCharac3.chx += gData.getChx();
									mp.bCharac3.loop();
									gData.setChx(0);
									if (!b3MoveFlag && gData.isLeftMove()) {
										mp.bCharac3.setMove(90, 180);
										b3MoveFlag = true;
									} else if (b3MoveFlag && gData.isRightMove()) {
										mp.bCharac3.setMove(0, 90);
										b3MoveFlag = false;
									}
								}
								// ���� ��ų ���
								if (gData.isSnowblue()) { // ��� ���� ��ų
									snowblue = true;
									snowTimeblue = System.currentTimeMillis();
								}
								// ���� ��ų ���
								if (gData.isBrickblue()) {
									brickblue = true;
									brickTimeblue = System.currentTimeMillis();
								}
							}
						}
						else if (gData.getTeamColor().equals("RED")) { // �����϶�!
							if (gData.getTeamNum() == 1 && !rcd1death) {
								// ���ǵ� ��ų ���
								if (gData.isSpeedred()) {
									speedred = gData.isSpeedred();
									speedTimered = System.currentTimeMillis();
								}
								if (gData.getChx() != 0) {
									mp.rCharac1.chx += gData.getChx();
									mp.rCharac1.loop();
									gData.setChx(0);
									if (!r1MoveFlag && gData.isLeftMove()) {
										mp.rCharac1.setMove(90, 180);
										r1MoveFlag = true;
									} else if (r1MoveFlag && gData.isRightMove()) {
										mp.rCharac1.setMove(0, 90);
										r1MoveFlag = false;
									}
								}
								if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
									mp.rP1bullet();
								}
							}
							if (gData.getTeamNum() == 2 && !rcd2death) {
								if (gData.getChx() != 0) {
									mp.rCharac2.chx += gData.getChx();
									mp.rCharac2.loop();
									gData.setChx(0);
									if (!r2MoveFlag && gData.isLeftMove()) {
										mp.rCharac2.setMove(90, 180);
										r2MoveFlag = true;
									} else if (r2MoveFlag && gData.isRightMove()) {
										mp.rCharac2.setMove(0, 90);
										r2MoveFlag = false;
									}
								}
								if (gData.isBulletStart()) { // �Ѿ��� �߻� ����� Ȯ����!
									mp.rP2bullet();
								}
								// ���� ��ų ���
								if (gData.isShieldred()) {
									shieldred = gData.isShieldred();
									shieldTimered = System.currentTimeMillis();
								}
							}
							if (gData.getTeamNum() == 3 && !rcd3death && !brickred) {// ���࿡
																						// ������
																						// �������̶��
																						// ��������
																						// ���ϵ���
																						// !brickred
																						// ��
																						// ��
								if (gData.getChx() != 0) {
									mp.rCharac3.chx += gData.getChx();
									mp.rCharac3.loop();
									gData.setChx(0);
									if (!r3MoveFlag && gData.isLeftMove()) {
										mp.rCharac3.setMove(90, 180);
										r3MoveFlag = true;
									} else if (r3MoveFlag && gData.isRightMove()) {
										mp.rCharac3.setMove(0, 90);
										r3MoveFlag = false;
									}
								}
								// ���� ��ų ���
								if (gData.isSnowred()) { // ���� ���� ��ų
									snowred = true;
									snowTimered = System.currentTimeMillis();
								}
								// ���� ��ų ���
								if (gData.isBrickred()) {
									brickred = true;
									brickTimered = System.currentTimeMillis();
								}
							}
						}
					}
					gData=null;
				}
				// �Ѿ� ���� ����
				// ��� 1�� �Ѿ� ����
				if (mp.bullet1B.size() > 0) {
					mp.bullet1B.get(0).loop();
					buxB = mp.bullet1B.get(0).x;
					buyB = mp.bullet1B.get(0).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
				}
				// ��� 2�� �Ѿ� ����
				if (mp.bullet2B.size() > 0) {
					mp.bullet2B.get(0).loop();
					bux2B = mp.bullet2B.get(0).x;
					buy2B = mp.bullet2B.get(0).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
				}

				// ������ 1�� �Ѿ� ����
				if (mp.bullet1R.size() > 0) {
					mp.bullet1R.get(0).loop();
					buxR = mp.bullet1R.get(0).x;
					buyR = mp.bullet1R.get(0).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
				}
				// ���� 2�� �Ѿ� ����
				if (mp.bullet2R.size() > 0) {
					mp.bullet2R.get(0).loop();
					bux2R = mp.bullet2R.get(0).x;
					buy2R = mp.bullet2R.get(0).y;// �Ѿ��� ��ǥ�� ��� �гο� �׸���.
				}
				// �Ѿ� ���� ��

				// Ball ����
				// ù��°��
				if (mp.sb.size() < 4) {
					for (int i = 0; i < mp.fb.size(); i++) {
						if (!mp.fb.get(i).fbswitch) {
							mp.fb.get(i).loop();
							fbx = mp.fb.get(i).x + 60;
							fby = mp.fb.get(i).y + 60;
							// ���� ĳ���� �浹
							bccollision(fbx, fby, 11025);
						}
					}
				}
				// �ι�°��
				if (mp.tb.size() < 8) {
					for (int i = 0; i < mp.sb.size(); i++) {
						if (!mp.sb.get(i).sbswitch) {
							mp.sb.get(i).loop();
							sbx = mp.sb.get(i).x + 40;
							sby = mp.sb.get(i).y + 40;
							// ���� ĳ���� �浹
							bccollision(sbx, sby, 7225);
						}
					}
				}
				// ����°��
				if (mp.fthb.size() < 16) {
					for (int i = 0; i < mp.tb.size(); i++) {
						if (!mp.tb.get(i).tbswitch) {
							mp.tb.get(i).loop();
							tbx = mp.tb.get(i).x + 20;
							tby = mp.tb.get(i).y + 20;
							// ���� ĳ���� �浹
							bccollision(tbx, tby, 4225);
						}
					}
				}
				// �׹�°��
				for (int i = 0; i < mp.fthb.size(); i++) {
					if (!mp.fthb.get(i).fthbswitch) {
						mp.fthb.get(i).loop();
						fthbx = mp.fthb.get(i).x + 10;
						fthby = mp.fthb.get(i).y + 10;
						// ���� ĳ���� �浹
						bccollision(fthbx, fthby, 3025);
					}
				}
				// Ball ����

				// �׾������ ��Ȱ�ϱ������ �ð�
				if (bcd1death)
					if (bcd1 + 5000 < System.currentTimeMillis()) {
						bcd1death = false;
						cdtb1 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}
				if (bcd2death)
					if (bcd2 + 5000 < System.currentTimeMillis()) {
						bcd2death = false;
						cdtb2 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}
				if (bcd3death)
					if (bcd3 + 5000 < System.currentTimeMillis()) {
						bcd3death = false;
						cdtb3 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}
				if (rcd1death)
					if (rcd1 + 5000 < System.currentTimeMillis()) {
						rcd1death = false;
						cdtr1 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}
				if (rcd2death)
					if (rcd2 + 5000 < System.currentTimeMillis()) {
						rcd2death = false;
						cdtr2 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}
				if (rcd3death)
					if (rcd3 + 5000 < System.currentTimeMillis()) {
						rcd3death = false;
						cdtr3 = System.currentTimeMillis(); // �׾������ Cooldown
															// time
					}

				mp.repaint();
				// gData=null;
				Thread.sleep(25);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void bccollision(double x, double y, int c) {
		if (mp.GetDistance(x, y, BlueCharacter1.x + 45, BlueCharacter1.y + 45) <= c && !bcd1death
				&& cdtb1 + 3000 < System.currentTimeMillis()) {
			bcd1 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			bcd1death = true;
		}
		if (mp.GetDistance(x, y, BlueCharacter2.x + 45, BlueCharacter2.y + 45) <= c && !bcd2death
				&& cdtb2 + 3000 < System.currentTimeMillis()) {
			bcd2 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			bcd2death = true;
		}
		if (mp.GetDistance(x, y, BlueCharacter3.x + 45, BlueCharacter3.y + 45) <= c && !bcd3death
				&& cdtb3 + 3000 < System.currentTimeMillis()) {
			bcd3 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			bcd3death = true;
		}
		if (mp.GetDistance(x, y, RedCharacter1.x + 45, RedCharacter1.y + 45) <= c && !rcd1death
				&& cdtr1 + 3000 < System.currentTimeMillis()) {
			rcd1 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			rcd1death = true;
		}
		if (mp.GetDistance(x, y, RedCharacter2.x + 45, RedCharacter2.y + 45) <= c && !rcd2death
				&& cdtr2 + 3000 < System.currentTimeMillis()) {
			rcd2 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			rcd2death = true;
		}
		if (mp.GetDistance(x, y, RedCharacter3.x + 45, RedCharacter3.y + 45) <= c && !rcd3death
				&& cdtr3 + 3000 < System.currentTimeMillis()) {
			rcd3 = System.currentTimeMillis();
			mp.sound("dead2.wav");
			rcd3death = true;
		}
	}
}
