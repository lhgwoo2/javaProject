package GamePanel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPanel extends JPanel {

	public ChatPanel() {
		super();

		this.setBackground(new Color(237, 200, 100, 100));
		this.setBounds(0, 700, 600, 200);
		this.add(chatShowText());
		this.add(chatInputTextfield());
	}

	// 채팅보여지는 창
	public JScrollPane chatShowText() {
		JTextArea chatField = new JTextArea("채팅");
		JScrollPane sp = new JScrollPane(chatField);
		sp.setBounds(0, 0, 600, 170);
		sp.setOpaque(false);
		sp.setBorder(BorderFactory.createEmptyBorder());
		return sp;

	}

	// 채팅 입력하는 창
	public TransparentTextField chatInputTextfield() {
		TransparentTextField ttf = new TransparentTextField(" ");
		ttf.setBounds(0, 170, 600, 30);
		
		return ttf;

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

