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
	static GameClient gClient;			// ���� Ŭ���̾�Ʈ ��ü

	public LoginPanel(MainPanel mp/* ,LoadingPanel ldp */) {
		super();
		this.setBounds(0, 0, 1600, 50);
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		this.mp = mp;
		//this.ldp = ldp;		//�ε� �г�
		
		// sever entrance - ID
		JLabel ja = new JLabel("User ID : ");
		ja.setBounds(200, 10, 50, 30);
		this.add(ja);

		JLabel ja2 = new JLabel("Team Selection : ");
		ja2.setBounds(500, 10, 100, 30);
		this.add(ja2);

		// login text field
		jt = new JTextField("�������̵� �Է�");
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
				
				//mp.add(ldp.loadingPanel());		// �ε� �̹��� 
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
		while (enums.hasMoreElements()) { // hasMoreElements() Enum�� �� ���� ��ü��
											// �ִ��� üũ�Ѵ�. ������ false ��ȯ
			AbstractButton ab = enums.nextElement(); // ���׸����� AbstractButton �̴ϱ�
														// �翬�� AbstractButton����
														// �޾ƾ���
			JRadioButton jb = (JRadioButton) ab; // ����ȯ. ���� ���ٰ� ������ ���ļ� �ٷ� ����ȯ
													// �ؼ� �޾Ƶ� �ȴ�.

			if (jb.isSelected()) // �޾Ƴ� ������ư�� üũ ���¸� Ȯ���Ѵ�. üũ�Ǿ������ true ��ȯ.
				teams = jb.getText().trim(); // getText() �޼ҵ�� ���ڿ� �޾Ƴ���.
		}
		
		// Connect Team - network
		gClient.connect();
		gClient.streamOpen();
		ClientData cData = new ClientData();
		cData.setTeamName(teams);
		cData.setUserId(jt.getText());
		if(gClient.loginSend(cData))
		{
			JOptionPane.showMessageDialog(mp, "���Ӽ���");
			// Connect Team - �α����гο��� ǥ��
			JLabel team = new JLabel(teams);
			team.setBounds(650, 10, 100, 30);
			this.add(team);

			this.remove(bjb);
			this.remove(bjb2);
			this.remove(jt);
			this.remove(entB);
			
		}
		else{
			JOptionPane.showMessageDialog(mp, "���ӽ���");
		}
		
		
		

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
