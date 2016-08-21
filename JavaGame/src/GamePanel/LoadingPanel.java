package GamePanel;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {

	Image image;

	// Image splashImage;

	public LoadingPanel() {
		super();
	}

	public JPanel loadingPanel() {
		
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
	    image = imageIcon.getImage();
	    JLabel iconLabel = new JLabel();
	    iconLabel.setIcon(imageIcon);
	    imageIcon.setImageObserver(iconLabel);
	    JLabel label = new JLabel("Loading...");
	    this.add(iconLabel);
	    this.add(label);

	    return this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		AlphaComposite alpha;
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		g2d.setComposite(alpha);
		g2d.drawImage(image,0,0,200,200,this);
	
		
	}
}
