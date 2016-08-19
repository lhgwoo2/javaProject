package GamePanel;

import java.awt.Color;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestMain {

	public static void main(String[] args) {
		JFrame jf = new JFrame("Å×½ºÆ®");
		jf.setBounds(10, 10, 500, 500);
		jf.setVisible(true);
		JPanel jp = new JPanel();
		jp.setLayout(null);
		jp.setBounds(0, 0, 490, 480);
		jp.setBackground(Color.cyan);
		jf.add(jp);
		
		LoadingPanel lp = new LoadingPanel();
		//jp.add(lp.loadingPanel());
		
	}

}
