package GamePanel;

import java.awt.Image;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel{
	
	//Image splashImage;
	
	public LoadingPanel() {
		super();
	    BoxLayout layoutMgr = new BoxLayout(this, BoxLayout.PAGE_AXIS);
	    this.setLayout(layoutMgr);

	    URL loadingImage = this.getClass().getResource("/ImagePack/ripple.gif");
		/*try {
			bimg = ImageIO.read(is);
			System.out.println("메인화면 로드 성공");
		} catch (IOException e) {
			System.out.println("메인화면 로드 실패");
			e.printStackTrace();
		}*/
	    ImageIcon imageIcon = new ImageIcon(loadingImage);
	    JLabel iconLabel = new JLabel();
	    iconLabel.setIcon(imageIcon);
	    imageIcon.setImageObserver(iconLabel);

	    JLabel label = new JLabel("Loading...");
	    this.add(iconLabel);
	    this.add(label);
	}
/*
	public JPanel loadingPanel() {

	    
	    return panel;
	}
*/	
	
}
