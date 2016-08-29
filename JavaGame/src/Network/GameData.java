package Network;

import java.io.Serializable;

public class GameData implements Serializable {

	private int teamNum;				// 캐릭터 번호
	private String teamColor;			// 캐릭터 팀
	private double chx;					// 캐릭터좌표
	private boolean bulletStart;		// 캐릭터 총알 시작
	
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
