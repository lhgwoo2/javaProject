package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// oracleJoin
	public Connection getConn() {

		String jdbc_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@192.168.2.25:1521:xe";
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(db_url, "scott", "TIGER");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		}
		return null;
	}
	//listGet
	public List<BoardVO> getList() {
		conn = getConn();
		String sql = "select * from gameboard";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardHiredate(rs.getDate("BOARDHIREDATE"));
				list.add(BoardVO);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
	}
	//Ranking DAO -> LoginVO
	public List<LoginVO> rankingGetList(){
		conn = getConn();
		String sql = "select rownum rn,teamid,TEAMCOLOR,score,victory,playtime,gameDate from(select distinct teamid,TEAMCOLOR,score,victory,playtime,gameDate from TEAM ORDER BY score desc,PLAYTIME)where VICTORY ='½Â'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<LoginVO> list = new ArrayList<>();
			while (rs.next()) {
				LoginVO loginVo = new LoginVO();
				loginVo.setTeamNum(rs.getInt("rn"));
				loginVo.setTeamId(rs.getString("TEAMID"));
				loginVo.setTeamColor(rs.getString("TEAMCOLOR"));
				loginVo.setScore(rs.getInt("SCORE"));
				loginVo.setVictory(rs.getString("VICTORY"));
				loginVo.setPlayTime(rs.getDouble("PLAYTIME"));
				loginVo.setGameDate(rs.getDate("GAMEDATE"));
				list.add(loginVo);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
	}
	//pageNum 1~10 page
	public List<BoardVO> getPage(int page){
		conn = getConn();
		String sql = "SELECT * FROM "
				+ "("
				+ "SELECT ROWNUM rn, BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM,BOARDHIREDATE FROM (SELECT BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM desc) where BOARDREPLE = 0"
				+ ")"
				+ "WHERE rn between ? AND ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 20*page-19);
			pstmt.setInt(2, page*20);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardHiredate(rs.getDate("BOARDHIREDATE"));
				list.add(BoardVO);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
		
	}
	public List<BoardVO> getSerch(String boardId, String text){
		conn = getConn();
		String sql ="SELECT * FROM (SELECT ROWNUM rn, BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDHIREDATE FROM (SELECT BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM DESC) where BOARDREPLE = 0)  where "+boardId+" like '%"+text+"%'";
		try {
			pstmt = conn.prepareStatement(sql);
			//pstmt.setString(1, text);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardHiredate(rs.getDate("BOARDHIREDATE"));
				list.add(BoardVO);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
		
	}
	public BoardVO getDetail(int detailNum){
		conn = getConn();
		String sql = "SELECT * FROM (SELECT ROWNUM rn, BOARDREPLE,BOARDNUM,BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDHIREDATE FROM(SELECT BOARDREPLE,BOARDNUM,BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM DESC)where BOARDREPLE = 0) WHERE BOARDNUM =?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detailNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setNumb(rs.getInt("BOARDNUM"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setContent(rs.getString("BOARDCONTENT"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardHiredate(rs.getDate("BOARDHIREDATE"));
				return BoardVO;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
	}
	//hierarchy
	public List<BoardVO> getHierarchy(int boardNum){
		conn = getConn();
		String sql = "select BOARDID,BOARDNUM,BOARDTITLE,BOARDHIREDATE from GAMEBOARD WHERE BOARDREPLE = ? start with BOARDNUM = ? CONNECT BY PRIOR BOARDNUM = BOARDREPLE ORDER SIBLINGS BY BOARDNUM";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.setInt(2, boardNum);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardHiredate(rs.getDate("BOARDHIREDATE"));
				list.add(BoardVO);
			}  
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
	}
	//reple insert
	public boolean repleInsert(BoardVO gameboard){
		conn = getConn();
		String sql = "INSERT INTO gameboard VALUES(board_num_seq2.NEXTVAL,?,?,'',?,'',SYSDATE)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gameboard.getBoardNum());
			pstmt.setString(2, gameboard.getBoardTitle());
			pstmt.setString(3, gameboard.getBoardId());
			int n = pstmt.executeUpdate();
			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	//reple Delete
	public boolean repleDelete(BoardVO boardVo){
		conn = getConn();
		String sql = "DELETE from gameboard where boardNum= ?";
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardVo.getBoardNum());

			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	//writing
	public boolean insert(BoardVO gameboard){
		conn = getConn();
		String sql = 
				"INSERT INTO gameboard VALUES(board_num_seq2.NEXTVAL,0,?,?,?,?,SYSDATE)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, gameboard.getBoardTitle());
			pstmt.setString(2, gameboard.getContent());
			pstmt.setString(3, gameboard.getBoardId());
			pstmt.setString(4, gameboard.getBoardTeam());
			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	//login insert
	public boolean loginInsert(LoginVO loginVo){
		conn = getConn();
		String sql = "INSERT into TEAM (num,USERID,teamcolor,gamedate) VALUES (SEQUENCE1.NEXTVAL,?,?,sysdate)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, loginVo.getUserId());
			pstmt.setString(2, loginVo.getTeamColor());
			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	//login ID Check
	public List<LoginVO> loginIdCheck(){
		conn = getConn();
		String sql = "SELECT USERID FROM TEAM where VICTORY is null";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<LoginVO> list = new ArrayList<>();
			while (rs.next()) {
				LoginVO loginVo = new LoginVO();
				loginVo.setUserId(rs.getString("USERID"));
				list.add(loginVo);
			}  
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return null;
		
	}
	//login update
	public boolean loginUpdate(String teamId, String teamColor){
		conn = getConn();
		String sql = "UPDATE TEAM SET TEAMID = ? where TEAMCOLOR= ? and VICTORY is null";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teamId);
			pstmt.setString(2, teamColor);
			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	 //teamUpdate update
	   public boolean teamUpdate(int score,String victory, double playTime, String teamId){
	      conn = getConn();
	      String sql = "UPDATE TEAM SET score = ?, victory = ?,playtime = ?,gamedate = sysdate where TEAMID= ?";

	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, score);
	         pstmt.setString(2, victory);
	         pstmt.setDouble(3, playTime);
	         pstmt.setString(4, teamId);
	         int n = pstmt.executeUpdate();

	         return n > 0 ? true : false;
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         closeAll();
	      }
	      return false;
	   }
	//delete
	public boolean getdelete(BoardVO boardVo){
		conn = getConn();
		String sql = "DELETE from gameboard where boardNum=?";
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardVo.getBoardNum());

			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	public boolean getUpdate(BoardVO updateInfo){
		conn = getConn();
		String sql = "UPDATE gameboard SET boardTitle = ?, boardContent = ? where BOARDNUM = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateInfo.getBoardTitle());
			pstmt.setString(2, updateInfo.getContent());
			pstmt.setInt(3, updateInfo.getBoardNum());
			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
	
	//clsoeCode
	public void closeAll() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
