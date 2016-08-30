package Network;

import java.io.Serializable;

public class GameData implements Serializable {

	private int teamNum;				// 캐릭터 번호
	private String teamColor;			// 캐릭터 팀
	private double chx;					// 캐릭터좌표
	private boolean bulletStart;		// 캐릭터 총알 시작
	private boolean leftMove;			//캐릭터 왼쪽 움직임
	private boolean rightMove;		//캐릭터 오른쪽 움직임
	
	public boolean isLeftMove() {
		return leftMove;
	}

	public void setLeftMove(boolean leftMove) {
		this.leftMove = leftMove;
	}

	public boolean isRightMove() {
		return rightMove;
	}

	public void setRightMove(boolean rightMove) {
		this.rightMove = rightMove;
	}

	public boolean isBulletStart() {
		return bulletStart;
	}

	public void setBulletStart(boolean bulletStart) {
		this.bulletStart = bulletStart;
	}

	
	
	public int getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}

	public String getTeamColor() {
		return teamColor;
	}

	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}

	public double getChx() {
		return chx;
	}

	public void setChx(double chx) {
		this.chx = chx;
	}
	
	
}
