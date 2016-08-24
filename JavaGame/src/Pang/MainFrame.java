package Pang;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	double bux;
	double buy;

	public MainFrame() {
		super("팡팡");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(20, 20, 1805, 978);
		getContentPane().setLayout(null);

		setVisible(true);

		MainPanel mp = new MainPanel();
		this.add(mp);
		mp.requestFocusInWindow();

		new Thread() {
			@Override
			public void run() {
				while (true) {
					
					if(MainPanel.bullet.size()>-1){// 총알 루프
						for (int i = 0; i < MainPanel.bullet.size(); i++) {// 총알 루프
							MainPanel.bullet.get(i).loop();
							bux=MainPanel.bullet.get(i).x+12.5;
							buy= MainPanel.bullet.get(i).y+12.5;
						}
					}

					if (MainPanel.fb.size() > 0) {
						for (int i = 0; i < MainPanel.fb.size(); i++) {
							if (!MainPanel.fb.get(i).fbswitch) {
								MainPanel.fb.get(i).loop();
								MainPanel.fbx = MainPanel.fb.get(i).x;
								MainPanel.fby = MainPanel.fb.get(i).y;
								if (MainPanel.GetDistance(bux, buy,MainPanel.fbx + 60, MainPanel.fby + 60) <= 72.5) {
									mp.secondBall();
									MainPanel.fb.get(i).fbswitch = true;
								}
							}
						}
					}

					if (MainPanel.sb.size() > 0) { //두번째볼
						for (int i = 0; i < MainPanel.sb.size(); i++) {
							if (!MainPanel.sb.get(i).sbswitch) {
								MainPanel.sb.get(i).loop();
								MainPanel.sbx = MainPanel.sb.get(i).x;
								MainPanel.sby = MainPanel.sb.get(i).y;
								if (MainPanel.GetDistance(bux, buy ,MainPanel.sbx + 40, MainPanel.sby + 40) <= 52.5) {
									mp.thirdBall();
									MainPanel.sb.get(i).sbswitch = true;
								}
							}
						}
					}
					if (MainPanel.tb.size() > 0) { //세번째볼
						for (int i = 0; i < MainPanel.tb.size(); i++) {
							if (!MainPanel.tb.get(i).tbswitch) {
								MainPanel.tb.get(i).loop();
								MainPanel.tbx = MainPanel.tb.get(i).x;
								MainPanel.tby = MainPanel.tb.get(i).y;
								if (MainPanel.GetDistance(bux, buy,MainPanel.tbx + 20, MainPanel.tby + 20) <= 32.5) {
									MainPanel.tb.get(i).tbswitch = true;
								}
							}
						}
					}
					repaint();			//리풰인트~~~~~~~		
					try {
						Thread.sleep(30);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();

			}
		});

	}
}
