package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class SpeedBlue extends BufferedImage {

	BufferedImage img;
	public static double bluesx;
	public static double bluesy;
	public int a;
	public int b;
	public int c;
	public int d;

	public SpeedBlue() {
		super(163, 28, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("speed.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		g2d.drawImage(img, (int) bluesx, (int) bluesy, (int) bluesx + 163, (int) bluesy + 28, 0, 0, 163, 28, null);
	}
}
