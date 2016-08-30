package GamePanel;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	public MainFrame() {
		super("ÆÎÆÎ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(20, 20, 1605, 978);
		getContentPane().setLayout(null);
		setVisible(true);

		MainPanel mp = new MainPanel(this);
		this.add(mp);
		mp.requestFocusInWindow();
		
		LoginPanel lp = new LoginPanel(mp/*,ldp*/);
		this.add(lp);
		lp.requestFocusInWindow();
		
		
		/*new Thread() {
			@Override
			public void run() {
				super.run();nn
				while (true) {
				repaint();
					
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();*/
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
				
			}
		});

	}
}
