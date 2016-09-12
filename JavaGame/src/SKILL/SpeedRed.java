package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class SpeedRed extends BufferedImage {

	BufferedImage img;
	public static double redsx;
	public static double redsy;
	public int a;
	public int b;
	public int c;
	public int d;

	public SpeedRed() {
		super(163, 28, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("speed.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		g2d.drawImage(img, (int) redsx, (int) redsy, (int) redsx + 163, (int) redsy + 28, 0, 0, 163, 28, null);
	}
}
