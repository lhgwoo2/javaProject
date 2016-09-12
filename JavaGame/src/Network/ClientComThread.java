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

	// 블루팀 총알 좌표
	public static double buxB;
	public static double buyB = 1000; // 1000으로 준이유는 처음에 충돌을 막기 위해서
	public static double bux2B;
	public static double buy2B = 1000;
	public static double bux3B;
	public static double buy3B = 1000;

	// 레드팀 총알 좌표
	public static double buxR;
	public static double buyR = 1000;
	public static double bux2R;
	public static double buy2R = 1000;
	public static double bux3R;
	public static double buy3R = 1000;

	// 캐릭터 좌우 이동 플래그
	public boolean b1MoveFlag; // 블루팀
	public boolean b2MoveFlag;
	public boolean b3MoveFlag;
	public boolean r1MoveFlag; // 레드팀
	public boolean r2MoveFlag;
	public boolean r3MoveFlag;

	// 볼의 좌표
	double fbx;
	double fby;
	double sbx;
	double sby;
	double tbx;
	double tby;
	double fthbx;
	double fthby;
	// 볼의 좌표

	// 케릭터별로 죽었을경우 시간 체크
	public long bcd1;
	public long bcd2;
	public long bcd3;
	public long rcd1;
	public long rcd2;
	public long rcd3;
	// 케릭터별로 죽었을경우 시간 체크

	// 캐릭터가 죽었을 경우 판정하는 값
	public static boolean bcd1death;
	public static boolean bcd2death;
	public static boolean bcd3death;
	public static boolean rcd1death;
	public static boolean rcd2death;
	public static boolean rcd3death;

	// 캐릭터가 죽었을 경우 판정하는 값

	// 죽었을경우 Cooldown time
	public long cdtb1;
	public long cdtb2;
	public long cdtb3;
	public long cdtr1;
	public long cdtr2;
	public long cdtr3;
	// 죽었을경우 Cooldown time

	// 스킬
	public static boolean shieldblue; // 블루쉴드스킬
	public static long shieldTimeblue;
	public static boolean shieldred; // 레드쉴드스킬
	public static long shieldTimered;

	public static boolean speedblue; // 블루 스피드 스킬
	public static long speedTimeblue;
	public static boolean speedred; // 레드 스피드 스킬
	public static long speedTimered;

	public static boolean snowblue; // 블루 얼음 스킬
	public static long snowTimeblue;
	public static boolean snowred; // 레드 얼음 스킬
	public static long snowTimered;

	public static boolean brickblue; // 블루 벽돌 스킬
	public static long brickTimeblue;
	public static boolean brickred; // 레드 벽돌 스킬
	public static long brickTimered;
	
	
	public boolean flag;
	// 스킬

	MainPanel mp;

	GameClient gc;
	// static DataFormat uData;
	public boolean oisFlag; // 스트림 생성 플래그
	public boolean clientFlag; // 처음 화면전환 플래그
	long countTime; // 카운트 시간 변수
	double countdownFlag; // 카운트 플래그
	public CountThread cntThread; // 카운트 쓰레드
	public boolean countFlag; // 시작 카운트

	ChatPanel cp;
	public GameDataPanel gdp; // game 데이터 패널
	public PointPanel ppl; // 게임 스코어 표시 패널

	// 포인트
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
		gdp = new GameDataPanel(mp); // 스킬, 시간창
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
					System.out.println("블루팀 포인트"+ppl.blueScore);
					System.out.println("레드팀 포인트"+ppl.redScore);
					ppl.insertScore();
					ppl.repaint();
				} 

				// GameData gData = (GameData) obj;

				if (obj instanceof ClientData) {
					ClientData cData = (ClientData) obj;

					if (cData.isAllTeamOK()) {
						gc.loadthread.interrupt(); // 로딩화면 중지 - 쓰레드 중지
						mp.setVisible(true); // 메인화면 다시 보여주기
						mp.drawingPlayImage();
						mp.setFocusable(true);
						mp.requestFocus();
						mp.firstBall();
						mp.add(cp);
						mp.add(gdp); // 스킬, 시간 등 정보 창
						mp.eventKey(); // 이벤트 처리를 위한 키보드사용
						ppl = new PointPanel(mp);
						mp.repaint();
						
						mp.clip.close();
						
						// 카운터 쓰레드 시작
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
					
					//아이디 표시
					if(!flag){
					mp.label.setText("BLUE팀:    "+gData.getId1());
					mp.labellabel.setText("RED 팀:    "+gData.getId2());
					flag = true;
					}

					synchronized (gData) {
					

						///////////////////// // 시간 카운트 패널 출력
						if (gData.getCountdown() >= 6 && gData.getCountdown() < 66) {
							if (countdownFlag != gData.getCountdown()) {
								countdownFlag = gData.getCountdown();
								gdp.loop();
							}
							if(ppl.blueScore+ppl.redScore>=6000){						// 시간이 끝나기 전에 볼이 다 터졌을 경우 끝낸다.
								GameOverPanel gop = new GameOverPanel(mp, ppl);
								mp.add(gop);
								mp.repaint();
								System.out.println("플레이 시간" + gData.getCountdown());
								gData.setPlayTime(gData.getCountdown()-6);
								gData.setCountdown(66);
								gData.setClientNum(LoginPanel.gClient.clientNumber);
								gData.setBluePoint(ppl.blueScore);
								gData.setRedPoint(ppl.redScore);
								LoginPanel.gClient.sendGameData(gData);
								mp.mainclip.close();
								break;
							}
						} else if (gData.getCountdown() == 66) { // 게임 오버 패널
							GameOverPanel gop = new GameOverPanel(mp, ppl);
							mp.add(gop);
							mp.repaint();
							gData.setPlayTime(60);							// 게임 시간 60초 
							gData.setClientNum(LoginPanel.gClient.clientNumber);
							gData.setBluePoint(ppl.blueScore);
							gData.setRedPoint(ppl.redScore);
							LoginPanel.gClient.sendGameData(gData);
							mp.mainclip.close();
							break; // 클라이언트 쓰레드 종료
						}
						if (gData.getChMsg() != null) {					//채팅 창
							cp.chatAppendMsg(gData);
							cp.repaint();
							mp.repaint();
						}

						if (!countFlag) {
							// 카운트시간보다 현재시간이 많으면 카운터쓰레드 종료
							if (countTime <= System.currentTimeMillis()) {
								cntThread.interrupt();
								mp.remove(cntThread.cntP);
								countFlag = true;
							}
						}

						if (gData.getTeamColor().equals("BLUE")) {
							if (gData.getTeamNum() == 1 && !bcd1death) {
								// 스피드 스킬사용
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
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
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
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
									mp.bP2bullet();
								}
								if (gData.isShieldblue()) { // 쉴드 스킬
									shieldblue = gData.isShieldblue();
									shieldTimeblue = System.currentTimeMillis();
								}
							}
							if (gData.getTeamNum() == 3 && !bcd3death && !brickblue) { // 만약에
																						// 벽돌이
																						// 생성중이라면
																						// 움직이지
																						// 못하도록
																						// !brickblue
																						// 를
																						// 씀
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
								// 얼음 스킬 사용
								if (gData.isSnowblue()) { // 블루 얼음 스킬
									snowblue = true;
									snowTimeblue = System.currentTimeMillis();
								}
								// 벽돌 스킬 사용
								if (gData.isBrickblue()) {
									brickblue = true;
									brickTimeblue = System.currentTimeMillis();
								}
							}
						}
						else if (gData.getTeamColor().equals("RED")) { // 레드일때!
							if (gData.getTeamNum() == 1 && !rcd1death) {
								// 스피드 스킬 사용
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
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
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
								if (gData.isBulletStart()) { // 총알이 발사 됬는지 확인함!
									mp.rP2bullet();
								}
								// 쉴드 스킬 사용
								if (gData.isShieldred()) {
									shieldred = gData.isShieldred();
									shieldTimered = System.currentTimeMillis();
								}
							}
							if (gData.getTeamNum() == 3 && !rcd3death && !brickred) {// 만약에
																						// 벽돌이
																						// 생성중이라면
																						// 움직이지
																						// 못하도록
																						// !brickred
																						// 를
																						// 씀
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
								// 얼음 스킬 사용
								if (gData.isSnowred()) { // 레드 얼음 스킬
									snowred = true;
									snowTimered = System.currentTimeMillis();
								}
								// 벽돌 스킬 사용
								if (gData.isBrickred()) {
									brickred = true;
									brickTimered = System.currentTimeMillis();
								}
							}
						}
					}
					gData=null;
				}
				// 총알 루프 시작
				// 블루 1번 총알 루프
				if (mp.bullet1B.size() > 0) {
					mp.bullet1B.get(0).loop();
					buxB = mp.bullet1B.get(0).x;
					buyB = mp.bullet1B.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
				}
				// 블루 2번 총알 루프
				if (mp.bullet2B.size() > 0) {
					mp.bullet2B.get(0).loop();
					bux2B = mp.bullet2B.get(0).x;
					buy2B = mp.bullet2B.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
				}

				// 레드팀 1번 총알 루프
				if (mp.bullet1R.size() > 0) {
					mp.bullet1R.get(0).loop();
					buxR = mp.bullet1R.get(0).x;
					buyR = mp.bullet1R.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
				}
				// 레드 2번 총알 루프
				if (mp.bullet2R.size() > 0) {
					mp.bullet2R.get(0).loop();
					bux2R = mp.bullet2R.get(0).x;
					buy2R = mp.bullet2R.get(0).y;// 총알의 좌표를 계속 패널에 그린다.
				}
				// 총알 루프 끝

				// Ball 루프
				// 첫번째볼
				if (mp.sb.size() < 4) {
					for (int i = 0; i < mp.fb.size(); i++) {
						if (!mp.fb.get(i).fbswitch) {
							mp.fb.get(i).loop();
							fbx = mp.fb.get(i).x + 60;
							fby = mp.fb.get(i).y + 60;
							// 공과 캐릭터 충돌
							bccollision(fbx, fby, 11025);
						}
					}
				}
				// 두번째볼
				if (mp.tb.size() < 8) {
					for (int i = 0; i < mp.sb.size(); i++) {
						if (!mp.sb.get(i).sbswitch) {
							mp.sb.get(i).loop();
							sbx = mp.sb.get(i).x + 40;
							sby = mp.sb.get(i).y + 40;
							// 공과 캐릭터 충돌
							bccollision(sbx, sby, 7225);
						}
					}
				}
				// 세번째볼
				if (mp.fthb.size() < 16) {
					for (int i = 0; i < mp.tb.size(); i++) {
						if (!mp.tb.get(i).tbswitch) {
							mp.tb.get(i).loop();
							tbx = mp.tb.get(i).x + 20;
							tby = mp.tb.get(i).y + 20;
							// 공과 캐릭터 충돌
							bccollision(tbx, tby, 4225);
						}
					}
				}
				// 네번째볼
				for (int i = 0; i < mp.fthb.size(); i++) {
					if (!mp.fthb.get(i).fthbswitch) {
						mp.fthb.get(i).loop();
						fthbx = mp.fthb.get(i).x + 10;
						fthby = mp.fthb.get(i).y + 10;
						// 공과 캐릭터 충돌
						bccollision(fthbx, fthby, 3025);
					}
				}
				// Ball 루프

				// 죽었을경우 부활하기까지에 시간
				if (bcd1death)
					if (bcd1 + 5000 < System.currentTimeMillis()) {
						bcd1death = false;
						cdtb1 = System.currentTimeMillis(); // 죽었을경우 Cooldown
															// time
					}
				if (bcd2death)
					if (bcd2 + 5000 < System.currentTimeMillis()) {
						bcd2death = false;
						cdtb2 = System.currentTimeMillis(); // 죽었을경우 Cooldown
															// time
					}
				if (bcd3death)
					if (bcd3 + 5000 < System.currentTimeMillis()) {
						bcd3death = false;
						cdtb3 = System.currentTimeMillis(); // 죽었을경우 Cooldown
															// time
					}
				if (rcd1death)
					if (rcd1 + 5000 < System.currentTimeMillis()) {
						rcd1death = false;
						cdtr1 = System.currentTimeMillis(); // 죽었을경우 Cooldown
															// time
					}
				if (rcd2death)
					if (rcd2 + 5000 < System.currentTimeMillis()) {
						rcd2death = false;
						cdtr2 = System.currentTimeMillis(); // 죽었을경우 Cooldown
															// time
					}
				if (rcd3death)
					if (rcd3 + 5000 < System.currentTimeMillis()) {
						rcd3death = false;
						cdtr3 = System.currentTimeMillis(); // 죽었을경우 Cooldown
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
