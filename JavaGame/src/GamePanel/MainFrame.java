package GamePanel;
import java.awt.EventQueue;

import javax.swing.JFrame;

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
		
		LoginPanel lp = new LoginPanel(mp);
		this.add(lp);
		lp.requestFocusInWindow();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
				
			}
		});

	}
}
