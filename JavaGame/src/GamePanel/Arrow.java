package GamePanel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Arrow extends BufferedImage {
	
	public Image img;
	public double x;
	public double y;
	

	public Arrow(){
	      super(40,40, BufferedImage.TYPE_INT_ARGB);
			InputStream is = getClass().getResourceAsStream("arrow3.png"); 
	        try { 
	        	img = ImageIO.read(is); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        }
	}
	
	
	public void draw(Graphics2D g2d){
			g2d.drawImage(img, (int)x, (int)y,(int)x+40,(int)y+40,0,0,40,40,null);
	}

}
