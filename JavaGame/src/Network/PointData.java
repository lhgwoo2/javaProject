package Network;

public class PointData extends GameData{
	private int Point;
	private String teamColor;

	public String getTeamColor() {
		return teamColor;
	}

	public void setTeamColor(String teamColor) {
		this.teamColor = teamColor;
	}

	public int getPoint() {
		return Point;
	}

	public void setPoint(int point) {
		Point = point;
	}
	
}
