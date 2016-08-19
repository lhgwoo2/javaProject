package HKM;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;



public class EventDemo extends JFrame {

	
	public EventDemo(){
		super("이벤트 테스트");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0,0,650,540);
		getContentPane().setLayout(null);
		

		
		GamePanel gp = new GamePanel();
		

		add(gp);
		setVisible(true);
		
		//포커스를 게임패널에 맞춰줘요!! 포커스가 있어야지만 작업을 싱핼 할 수 있다.
		
		new Thread(){
			@Override
			public void run(){
				while(true){
					gp.requestFocusInWindow();
					gp.repaint();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		
	}
	
	public static void main(String[] args){
		
		EventQueue.invokeLater(new Runnable() { //이벤트를 저장할수 있는 공간. 시스템 스레드한테 준다.
			@Override
			public void run() {
				new EventDemo();
			}
			});
	}

}
