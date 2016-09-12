package Network;

import java.io.Serializable;

public class GameData implements Serializable {

	private int teamNum;				// 캐릭터 번호
	private String teamColor;			// 캐릭터 팀
	private double chx;					// 캐릭터좌표
	private boolean bulletStart;		// 캐릭터 총알 시작
	private boolean leftMove;			//캐릭터 왼쪽 움직임
	private boolean rightMove;		//캐릭터 오른쪽 움직임
	private String chMsg;			//채팅 메시지
	private String userID;
	private double countdown;
	//스킬
	private boolean shieldblue;
	private boolean shieldred; 
	private boolean speedblue;
	private boolean speedred; 
	private boolean snowblue;
	private boolean snowred;
	private boolean brickblue;
	private boolean brickred;
	private int bluePoint;
	private int redPoint;
	private int clientNum;
	private double playTime;
	private String Id1;
	private String Id2;
	
	public String getId1() {
		return Id1;
	}

	public void setId1(String id1) {
		Id1 = id1;
	}

	public String getId2() {
		return Id2;
	}

	public void setId2(String id2) {
		Id2 = id2;
	}

	

	

	public double getPlayTime() {
		return playTime;
	}

	public void setPlayTime(double playTime) {
		this.playTime = playTime;
	}

	public int getClientNum() {
		return clientNum;
	}

	public void setClientNum(int clientNum) {
		this.clientNum = clientNum;
	}

	public int getBluePoint() {
		return bluePoint;
	}

	public void setBluePoint(int bluePoint) {
		this.bluePoint = bluePoint;
	}

	public int getRedPoint() {
		return redPoint;
	}

	public void setRedPoint(int redPoint) {
		this.redPoint = redPoint;
	}

	public boolean isSnowblue() {
		return snowblue;
	}

	public void setSnowblue(boolean snowblue) {
		this.snowblue = snowblue;
	}

	public boolean isSnowred() {
		return snowred;
	}

	public void setSnowred(boolean snowred) {
		this.snowred = snowred;
	}

	public boolean isBrickblue() {
		return brickblue;
	}

	public void setBrickblue(boolean brickblue) {
		this.brickblue = brickblue;
	}

	public boolean isBrickred() {
		return brickred;
	}

	public void setBrickred(boolean brickred) {
		this.brickred = brickred;
	}

	public boolean isShieldblue() {
		return shieldblue;
	}

	public void setShieldblue(boolean shieldblue) {
		this.shieldblue = shieldblue;
	}

	public boolean isShieldred() {
		return shieldred;
	}

	public void setShieldred(boolean shieldred) {
		this.shieldred = shieldred;
	}

	public boolean isSpeedblue() {
		return speedblue;
	}

	public void setSpeedblue(boolean speedblue) {
		this.speedblue = speedblue;
	}

	public boolean isSpeedred() {
		return speedred;
	}

	public void setSpeedred(boolean speedred) {
		this.speedred = speedred;
	}

	public double getCountdown() {
		return countdown;
	}

	public void setCountdown(double countdown) {
		this.countdown = countdown;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getChMsg() {
		return chMsg;
	}

	public void setChMsg(String chMsg) {
		this.chMsg = chMsg;
	}

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
