package Network;

public class ClientData implements DataFormat{

	private String userId;
	private String chatMsg;
	private boolean loginOK;
	private boolean teamOK;
	
	
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
