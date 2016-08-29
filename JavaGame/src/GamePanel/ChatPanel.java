package GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Network.ClientData;
import Network.GameClient;

public class ChatPanel extends JPanel {
	JTextArea chatField;
	JTextField ttf;
	// TransparentTextField ttf;
	GameClient gc;
	MainPanel mp;
	private Font font1 = new Font("Serif", Font.PLAIN, 15);

	public ChatPanel(GameClient gc, MainPanel mp) {
		super();
		this.gc = gc;
		this.mp = mp;
		this.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		this.setLayout(null);
		this.setBounds(0, 700, 600, 200);

		chatShowText();
		chatInputTextfield();

	}

	// 채팅보여지는 창
	public void chatShowText() {
		chatField = new JTextArea("");
		chatField.setEditable(false);
		//스크롤바 가로사용 안하고 세로만 사용
		JScrollPane sp = new JScrollPane(chatField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatField.setFont(font1);				//폰트 변경
		chatField.setForeground(Color.white); // 텍스트 필드 폰트 색상변경
		chatField.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		chatField.setColumns(30);		//출력되는 열 제한
		chatField.setLineWrap(true);	//텍스트필드 자동 줄바꿈
		sp.setBounds(0, 0, 600, 170);
		sp.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		sp.setBorder(BorderFactory.createEmptyBorder());

		
		// sp.setOpaque(false);
		// sp.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		// sp.setBorder(BorderFactory.createEmptyBorder());
		this.add(sp);
	}

	// 채팅창에 업로드
	public void chatAppendMsg(ClientData cdata) {
		
		chatField.append(cdata.getUserId() + " : " + cdata.getChatMsg() + "\n");
		chatField.setCaretPosition(chatField.getDocument().getLength()); // 스크롤
																		// 자동이동

	}

	// 채팅 입력하는 창
	public void chatInputTextfield() {
		ttf = new JTextField("");
		// ttf = new TransparentTextField(" ");
		ttf.setBounds(0, 170, 600, 30);
		ttf.setForeground(Color.white); // 텍스트 필드 폰트 색상변경
		ttf.setOpaque(false);
		ttf.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.7f));
		ttf.setColumns(50);
		this.add(ttf);

		// 메시지를 보내기 위해서 클릭으로 채팅창 활성화
		ttf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ttf.setEnabled(true);
			}
		});

		// 채팅창에 키보드로 눌렀을 때 채팅메시지 보냄 - 보내고 난후 비활성화
		ttf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					String sendM = ttf.getText();
					gc.sendMessage(sendM);
					ttf.setText("");
					ttf.setEnabled(false);
					// ttf.requestFocus(); //
					
					//게임화면의 포커스를 맞추어준다.
					mp.setFocusable(true);			// 메인패널의 포커스를 맟추어준다
					mp.requestFocus();				// 포커스를 요청한다.
					
				}
			}

		});

	}

}

class TransparentTextField extends JTextField {

	public TransparentTextField() {
		super();
		init();
	}

	public TransparentTextField(String text) {
		super(text);
		init();
	}

	public TransparentTextField(int columns) {
		super(columns);
		init();
	}

	public TransparentTextField(String text, int columns) {
		super(text, columns);
		init();
	}

	protected void init() {
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
		super.paint(g2d);
		g2d.dispose();
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g2d);
		g2d.dispose();
	}

}
