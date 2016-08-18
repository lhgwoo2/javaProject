package GamePanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
	MainPanel mp;
	JTextField jt;
	ButtonGroup teamBg; // team
	JRadioButton bjb;
	JRadioButton bjb2;

	public LoginPanel(MainPanel mp) {
		super();
		this.setBounds(0, 0, 1800, 50);
		this.setLayout(null);
		this.setBackground(Color.GREEN);
		this.mp = mp;

		// sever entrance - ID
		JLabel ja = new JLabel("User ID : ");
		ja.setBounds(200, 20, 50, 30);
		this.add(ja);

		JLabel ja2 = new JLabel("Team Selection : ");
		ja2.setBounds(500, 20, 100, 30);
		this.add(ja2);

		// login text field
		jt = new JTextField("유저아이디 입력");
		jt.setBounds(300, 20, 150, 30);
		this.add(jt);

		// Team selection button
		// Blue team
		bjb = new JRadioButton("Blue");
		bjb.setBounds(600, 20, 100, 30);
		bjb.setBackground(Color.CYAN);
		this.add(bjb);
		// Red team
		bjb2 = new JRadioButton("Red");
		bjb2.setBounds(700, 20, 100, 30);
		bjb2.setBackground(Color.red);
		this.add(bjb2);

		// Team button group
		teamBg = new ButtonGroup();
		teamBg.add(bjb);
		teamBg.add(bjb2);

		// Entrance button
		JButton entB = new JButton("Entrance");
		entB.setBounds(900, 20, 100, 30);
		this.add(entB);

		entB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mp.drawingPlayImage();
				mp.repaint();
				
				// Login change
					loginAfter();
					repaint();
			}
		});
	}

	public void loginAfter() {

		// Connect ID
		JLabel ja = new JLabel(this.jt.getText());
		ja.setBounds(300, 20, 100, 30);
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

		// Connect Team
		JLabel team = new JLabel(teams);
		team.setBounds(650, 20, 100, 30);
		this.add(team);

		this.remove(bjb);
		this.remove(bjb2);
		this.remove(jt);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
