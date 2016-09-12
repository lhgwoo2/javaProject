package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ShieldBlue extends BufferedImage {

	BufferedImage img;
	public static double bluesx;
	public static double bluesy;
	public int a;
	public int b;
	public int c;
	public int d;

	public ShieldBlue() {
		super(150, 150, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("shield4.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		g2d.drawImage(img, (int) bluesx, (int) bluesy, (int) bluesx + 150, (int) bluesy + 150, 0, 0, 150, 150, null);
	}
}
