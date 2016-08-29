package GamePanel;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class LoadingMain extends JFrame {
	////fff
	public LoadingMain(){
		setTitle("Loading..");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(100, 50, 1600, 1000);
		
		LoadingPanel panel = new LoadingPanel();
		panel.setLayout(null);
		panel.setBounds(10, 10, 1600, 1000);
		add(panel);
		setVisible(true);
		
		new Thread(){
			public void run(){
				while(true){
					panel.loop();
					repaint();
					try {
						Thread.sleep(70);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new LoadingMain();
			
			}
		});
	}
	
		
	}
