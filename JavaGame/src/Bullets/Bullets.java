package Bullets;

import java.awt.Graphics2D;

public interface Bullets {
	public void initBullet(boolean bool);;
	public boolean getBulletBool();
	public void initBulletX(double initX);
	public void initBulletY(double initY);
	public void draw(Graphics2D g2d);
	public void loop();

}


