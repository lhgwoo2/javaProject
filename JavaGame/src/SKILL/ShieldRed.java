package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ShieldRed extends BufferedImage {

	BufferedImage img;
	public static double redsx;
	public static double redsy;
	public int a;
	public int b;
	public int c;
	public int d;

	public ShieldRed() {
		super(150, 150, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("shield4.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		g2d.drawImage(img, (int) redsx, (int) redsy, (int) redsx + 150, (int) redsy + 150, 0, 0, 150, 150, null);
	}
}
