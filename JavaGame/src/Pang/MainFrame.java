package Pang;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	static double bux;
	static double buy;
	static MainPanel mp;

	public MainFrame() {
		super("팡팡");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(20, 20, 1805, 978);
		getContentPane().setLayout(null);

		setVisible(true);

		mp = new MainPanel();
		this.add(mp);
		mp.requestFocusInWindow();

		new Thread() {
			@Override
			public void run() {
				while (true) {

					if (MainPanel.bullet.size() > -1) {// 총알 루프
						for (int i = 0; i < MainPanel.bullet.size(); i++) {
							MainPanel.bullet.get(i).loop();

						}
					}
					if (MainPanel.fb.size() > 0) {
						for (int i = 0; i < MainPanel.fb.size(); i++) {
							if (!MainPanel.fb.get(i).fbswitch) {
									MainPanel.fb.get(i).loop();
							}
						}
					}
					if (MainPanel.sb.size() > 0) { // 두번째볼
						for (int i = 0; i < MainPanel.sb.size(); i++) {
							if (!MainPanel.sb.get(i).sbswitch) {
									MainPanel.sb.get(i).loop();
							}
						}
					}
					if (MainPanel.tb.size() > 0) { // 세번째볼
						for (int i = 0; i < MainPanel.tb.size(); i++) {
							if (!MainPanel.tb.get(i).tbswitch) {
									MainPanel.tb.get(i).loop();
							}
						}
					}
					repaint(); // 리풰인트~~~~~~~
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
