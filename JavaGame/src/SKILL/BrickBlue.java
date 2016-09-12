package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BrickBlue extends BufferedImage {

	BufferedImage img;
	public static double bluebx;
	public static double blueby;
	public int a;
	public int b;
	public int c;
	public int d;

	public BrickBlue() {
		super(96, 762, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("brick.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		g2d.drawImage(img, (int) bluebx, (int) blueby, (int) bluebx + 96, (int) blueby + 762, 0, 0, 96, 762, null);
	}
}