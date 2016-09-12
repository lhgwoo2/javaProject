package DataBase;

public class LoginVO {
	private int teamNum;
	private String userId;
	private String teamId;
	private String teamColor;
	private String victory;
	private double playTime;
	private int score;
	private java.sql.Date gameDate;
	
	public int getTeamNum() {
		return teamNum;
	}
	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamColor() {
		return teamColor;
	}
	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}
	public String getVictory() {
		return victory;
	}
	public void setVictory(String victory) {
		this.victory = victory;
	}
	public double getPlayTime() {
		return playTime;
	}
	public void setPlayTime(double playTime) {
		this.playTime = playTime;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public java.sql.Date getGameDate() {
		return gameDate;
	}
	public void setGameDate(java.sql.Date gameDate) {
		this.gameDate = gameDate;
	}
}
