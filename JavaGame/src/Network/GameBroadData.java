package Network;

import java.io.Serializable;

public class GameBroadData implements Serializable{
	private double blueP1x;
	private double blueP2x;
	private double blueP3x;
	
	private double redP1x;
	private double redP2x;
	private double redP3x;
	
	//각캐릭터 총알 좌표
	private double blueBulletX1;
	private double blueBulletY1;
	private double blueBulletX2;
	private double blueBulletY2;
	private double blueBulletX3;
	private double blueBulletY3;
	//전체 총알 속도
	private double bulletSpeed;
	
	// 캐릭터 총알 시작
	private boolean bP1bulletStart;	//블루 1번 총알이 발사 됬는지 확인함!
	private boolean bP2bulletStart;	//블루 2번 총알이 발사 됬는지 확인함!

	

	public boolean isbP1bulletStart() {
		return bP1bulletStart;
	}
	public void setbP1bulletStart(boolean bP1bulletStart) {
		this.bP1bulletStart = bP1bulletStart;
	}
	public boolean isbP2bulletStart() {
		return bP2bulletStart;
	}
	public void setbP2bulletStart(boolean bP2bulletStart) {
		this.bP2bulletStart = bP2bulletStart;
	}
	public double getBlueP1x() {
		return blueP1x;
	}
	public double getBlueP2x() {
		return blueP2x;
	}
	public double getBlueP3x() {
		return blueP3x;
	}
	public double getRedP1x() {
		return redP1x;
	}
	public double getRedP2x() {
		return redP2x;
	}
	public double getRedP3x() {
		return redP3x;
	}
	public double getBlueBulletX1() {
		return blueBulletX1;
	}
	public double getBlueBulletY1() {
		return blueBulletY1;
	}
	public double getBlueBulletX2() {
		return blueBulletX2;
	}
	public double getBlueBulletY2() {
		return blueBulletY2;
	}
	public double getBlueBulletX3() {
		return blueBulletX3;
	}
	public double getBlueBulletY3() {
		return blueBulletY3;
	}
	public double getBulletSpeed() {
		return bulletSpeed;
	}
	public void setBlueP1x(double blueP1x) {
		this.blueP1x = blueP1x;
	}
	public void setBlueP2x(double blueP2x) {
		this.blueP2x = blueP2x;
	}
	public void setBlueP3x(double blueP3x) {
		this.blueP3x = blueP3x;
	}
	public void setRedP1x(double redP1x) {
		this.redP1x = redP1x;
	}
	public void setRedP2x(double redP2x) {
		this.redP2x = redP2x;
	}
	public void setRedP3x(double redP3x) {
		this.redP3x = redP3x;
	}
	public void setBlueBulletX1(double blueBulletX1) {
		this.blueBulletX1 = blueBulletX1;
	}
	public void setBlueBulletY1(double blueBulletY1) {
		this.blueBulletY1 = blueBulletY1;
	}
	public void setBlueBulletX2(double blueBulletX2) {
		this.blueBulletX2 = blueBulletX2;
	}
	public void setBlueBulletY2(double blueBulletY2) {
		this.blueBulletY2 = blueBulletY2;
	}
	public void setBlueBulletX3(double blueBulletX3) {
		this.blueBulletX3 = blueBulletX3;
	}
	public void setBlueBulletY3(double blueBulletY3) {
		this.blueBulletY3 = blueBulletY3;
	}
	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	
	
	
	
}
