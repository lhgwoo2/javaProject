package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Network.ClientComThread;

public class SnowBlue extends BufferedImage {

	BufferedImage img;
	public static double bluesx1;
	public static double bluesy1;
	public static double bluesx2;
	public static double bluesy2;
	public static double bluesx3;
	public static double bluesy3;
	public int a;
	public int b;
	public int c;
	public int d;

	public SnowBlue() {
		super(235, 262, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("snow.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		if (!ClientComThread.bcd1death) {
			g2d.drawImage(img, (int) bluesx1, (int) bluesy1, (int) bluesx1 + 235, (int) bluesy1 + 262, 0, 0, 235, 262,null);
		}
		if (!ClientComThread.bcd2death) {
			g2d.drawImage(img, (int) bluesx2, (int) bluesy2, (int) bluesx2 + 235, (int) bluesy2 + 262, 0, 0, 235, 262,null);
		}
		if (!ClientComThread.bcd3death) {
			g2d.drawImage(img, (int) bluesx3, (int) bluesy3, (int) bluesx3 + 235, (int) bluesy3 + 262, 0, 0, 235, 262,null);
		}
	}
}
