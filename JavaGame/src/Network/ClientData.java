package Network;

import java.io.Serializable;

public class ClientData extends GameData implements Serializable{

	private String userId;
	private String chatMsg;
	private boolean loginOK;
	private boolean teamOK;
	private int clientBlueNum;
	private int clientRedNum;
	private boolean allTeamOK;
	private String teamColor;				// 현재들어온 이용자의 팀
	private int teamNum;					// 현재들어온 이용자의 팀의 순번
	
	
	public ClientData() {
		super();
	}
	
	public String getTeamColor() {
		return teamColor;
	}
	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}
	public int getTeamNum() {
		return teamNum;
	}
	public void setTeamNum(int teamNum) {
		this.teamNum = teamNum;
	}
	public boolean isAllTeamOK() {
		return allTeamOK;
	}
	public void setAllTeamOK(boolean allTeamOK) {
		this.allTeamOK = allTeamOK;
	}
	public int getClientBlueNum() {
		return clientBlueNum;
	}
	public void setClientBlueNum(int clientBlueNum) {
		this.clientBlueNum = clientBlueNum;
	}
	public int getClientRedNum() {
		return clientRedNum;
	}
	public void setClientRedNum(int clientRedNum) {
		this.clientRedNum = clientRedNum;
	}
	public boolean isTeamOK() {
		return teamOK;
	}
	public void setTeamOK(boolean teamOK) {
		this.teamOK = teamOK;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	private String teamName;
	
	public boolean isLoginOK() {
		return loginOK;
	}
	public void setLoginOK(boolean loginOK) {
		this.loginOK = loginOK;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChatMsg() {
		return chatMsg;
	}
	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}
	
	
	
}
