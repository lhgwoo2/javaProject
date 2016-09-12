package SKILL;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import Network.ClientComThread;

public class SnowRed extends BufferedImage {

	BufferedImage img;
	public static double redsx1;
	public static double redsy1;
	public static double redsx2;
	public static double redsy2;
	public static double redsx3;
	public static double redsy3;
	public int a;
	public int b;
	public int c;
	public int d;

	public SnowRed() {
		super(235, 262, BufferedImage.TYPE_INT_ARGB);
		InputStream in = getClass().getResourceAsStream("snow.png");
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void paintDraw(Graphics g2d) {
		if(!ClientComThread.rcd1death){
			g2d.drawImage(img, (int) redsx1, (int) redsy1, (int) redsx1 + 235, (int) redsy1 + 262, 0, 0, 235, 262, null);
		}
		if(!ClientComThread.rcd2death){
			g2d.drawImage(img, (int) redsx2, (int) redsy2, (int) redsx2 + 235, (int) redsy2 + 262, 0, 0, 235, 262, null);
		}
		if(!ClientComThread.rcd3death){
			g2d.drawImage(img, (int) redsx3, (int) redsy3, (int) redsx3 + 235, (int) redsy3 + 262, 0, 0, 235, 262, null);
		}
	}
}