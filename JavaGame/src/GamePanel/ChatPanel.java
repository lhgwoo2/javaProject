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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Network.ClientData;
import Network.GameClient;
import Network.GameData;

public class ChatPanel extends JPanel {
	JTextArea chatField;
	JTextField ttf;
	// TransparentTextField ttf;
	GameClient gc;
	MainPanel mp;
	private Font font1 = new Font("Serif", Font.PLAIN, 15);

	//ä�� ������ Ǯ
	public List<GameData> chDataPool;
	public int cDataPoolIndex=0;
	
	public ChatPanel(GameClient gc, MainPanel mp) {
		super();
		this.gc = gc;
		this.mp = mp;
		this.setBackground(new Color(255,204,0));
		this.setLayout(null);
		this.setBounds(0, 725, 600, 175);

		chatShowText();
		chatInputTextfield();
		//ä�� ������ Ǯ ����
		chDataPool = new ArrayList<>();
		for(int i=0; i<3;i++) chDataPool.add(new GameData());
			
		
	}

	// ä�ú������� â
	public void chatShowText() {
		chatField = new JTextArea("");
		chatField.setEditable(false);
		//��ũ�ѹ� ���λ�� ���ϰ� ���θ� ���
		JScrollPane sp = new JScrollPane(chatField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatField.setFont(font1);				//��Ʈ ����
		chatField.setForeground(Color.white); // �ؽ�Ʈ �ʵ� ��Ʈ ���󺯰�
		chatField.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		chatField.setColumns(30);		//��µǴ� �� ����
		chatField.setLineWrap(true);	//�ؽ�Ʈ�ʵ� �ڵ� �ٹٲ�
		sp.setBounds(0, 0, 600, 145);
		sp.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		sp.setBorder(BorderFactory.createEmptyBorder());

		
		// sp.setOpaque(false);
		// sp.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.5f));
		// sp.setBorder(BorderFactory.createEmptyBorder());
		this.add(sp);
	}

	// ä��â�� ���ε�
	public void chatAppendMsg(GameData gdata) {
		
		chatField.append(gdata.getUserID() + " : " + gdata.getChMsg() + "\n");
		chatField.setCaretPosition(chatField.getDocument().getLength()); // ��ũ��
																		// �ڵ��̵�

	}

	// ä�� �Է��ϴ� â
	public void chatInputTextfield() {
		ttf = new JTextField("");
		// ttf = new TransparentTextField(" ");
		ttf.setBounds(0, 145, 600, 30);
		ttf.setForeground(Color.BLACK); // �ؽ�Ʈ �ʵ� ��Ʈ ���󺯰�
		ttf.setOpaque(false);
		ttf.setBackground(new Color(0.5f, 0.3f, 0.1f, 0.7f));
		ttf.setColumns(50);
		this.add(ttf);

		// �޽����� ������ ���ؼ� Ŭ������ ä��â Ȱ��ȭ
		ttf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				ttf.setEnabled(true);
			}
		});

		// ä��â�� Ű����� ������ �� ä�ø޽��� ���� - ������ ���� ��Ȱ��ȭ
		ttf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char keyCode = e.getKeyChar();
				if (keyCode == KeyEvent.VK_ENTER) {
					String sendM = ttf.getText();
					
					//ä�� ������ Ǯ�� �ε����� ó������ ������.
					if(cDataPoolIndex==chDataPool.size()-1)cDataPoolIndex=0;
					
					chDataPool.get(cDataPoolIndex).setChMsg(sendM);
					chDataPool.get(cDataPoolIndex).setTeamColor(LoginPanel.gClient.teamColor);
					gc.sendMessage(chDataPool.get(cDataPoolIndex));
					ttf.setText("");
					ttf.setEnabled(false);
					mp.setFocusable(true);			//����ȭ���� ��Ŀ���� ���߾��ش�. �����г��� ��Ŀ���� ���߾��ش�
					mp.requestFocus();				// ��Ŀ���� ��û�Ѵ�.
					mp.repaint();
					cDataPoolIndex++;
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
