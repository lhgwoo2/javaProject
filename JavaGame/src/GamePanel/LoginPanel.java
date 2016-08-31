package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Network.ClientData;
import Network.GameClient;

public class LoginPanel extends JPanel {
	MainPanel mp;
	LoadingPanel ldp;
	
	JTextField jt;
	ButtonGroup teamBg = null; // team
	JRadioButton bjb;
	JRadioButton bjb2;
	JButton entB;
	static GameClient gClient;			// 게임 클라이언트 객체

	public LoginPanel(MainPanel mp/* ,LoadingPanel ldp */) {
		super();
		this.setBounds(0, 0, 1600, 50);
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		this.mp = mp;
		//this.ldp = ldp;		//로딩 패널
		
		// sever entrance - ID
		JLabel ja = new JLabel("User ID : ");
		ja.setBounds(200, 10, 50, 30);
		this.add(ja);

		JLabel ja2 = new JLabel("Team Selection : ");
		ja2.setBounds(500, 10, 100, 30);
		this.add(ja2);

		// login text field
		jt = new JTextField("유저아이디 입력");
		jt.setBounds(300, 10, 150, 30);
		this.add(jt);

		// Team selection button
		// Blue team
		bjb = new JRadioButton("Blue");
		bjb.setBounds(600, 10, 100, 30);
		bjb.setBackground(Color.CYAN);
		this.add(bjb);
		
		// Red team
		bjb2 = new JRadioButton("Red");
		bjb2.setBounds(700, 10, 100, 30);
		bjb2.setBackground(Color.red);
		this.add(bjb2);

		// Team button group
		teamBg = new ButtonGroup();
		teamBg.add(bjb);
		teamBg.add(bjb2);

		// Entrance button
		// button image
		InputStream is = getClass().getResourceAsStream("/ImagePack/game_start3.png");
		Image img=null;
		try {
			img=ImageIO.read(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		entB = new JButton(new ImageIcon(img));
		entB.setBounds(900, 5, 130, 43);
		this.add(entB);

		entB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gClient = new GameClient(mp);
				loginAfter();
				repaint();
				
				//ldp.loadingPanel();
				//ldp.setBounds(500, 500, 200, 200);
				//ldp.setBackground(new Color(255,0,0,0));
				
				//mp.add(ldp.loadingPanel());		// 로딩 이미지 
				//ldp.repaint();
				// Login change
					
			}
		});
	}

	public void loginAfter() {

		// Connect ID
		JLabel ja = new JLabel(this.jt.getText());
		ja.setBounds(300, 10, 100, 30);
		this.add(ja);

		// Radiobutton value extraction
		Enumeration<AbstractButton> enums = teamBg.getElements();
		String teams = null;
		while (enums.hasMoreElements()) { // hasMoreElements() Enum에 더 꺼낼 개체가
											// 있는지 체크한다. 없으며 false 반환
			AbstractButton ab = enums.nextElement(); // 제네릭스가 AbstractButton 이니까
														// 당연히 AbstractButton으로
														// 받아야함
			JRadioButton jb = (JRadioButton) ab; // 형변환. 물론 윗줄과 이줄을 합쳐서 바로 형변환
													// 해서 받아도 된다.

			if (jb.isSelected()) // 받아낸 라디오버튼의 체크 상태를 확인한다. 체크되었을경우 true 반환.
				teams = jb.getText().trim(); // getText() 메소드로 문자열 받아낸다.
		}
		
		// Connect Team - network
		gClient.connect();
		gClient.streamOpen();
		ClientData cData = new ClientData();
		cData.setTeamName(teams);
		cData.setUserId(jt.getText());
		if(gClient.loginSend(cData))
		{
			JOptionPane.showMessageDialog(mp, "접속성공");
			// Connect Team - 로그인패널에서 표시
			JLabel team = new JLabel(teams);
			team.setBounds(650, 10, 100, 30);
			this.add(team);

			this.remove(bjb);
			this.remove(bjb2);
			this.remove(jt);
			this.remove(entB);
			
		}
		else{
			JOptionPane.showMessageDialog(mp, "접속실패");
		}
		
		
		

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
