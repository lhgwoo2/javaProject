package KSH;

public class BoardVO {
	private int boardNum;
	private int boardReple;
	private String boardTitle;
	private String content;
	private String boardId;
	private String boardTeam;
	private int boardScore;
	private java.sql.Date boardHiredate;

	public int getBoardScore() {
		return boardScore;
	}

	public void setBoardScore(int boardScore) {
		this.boardScore = boardScore;
	}

	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public int getBoardReple() {
		return boardReple;
	}

	public void setBoardReple(int boardReple) {
		this.boardReple = boardReple;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardTeam() {
		return boardTeam;
	}

	public void setBoardTeam(String boardTeam) {
		this.boardTeam = boardTeam;
	}

	public java.sql.Date getBoardHiredate() {
		return boardHiredate;
	}

	public void setBoardHiredate(java.sql.Date boardHiredate) {
		this.boardHiredate = boardHiredate;
	}
}
