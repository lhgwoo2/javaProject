package KSH;

import java.sql.*;
import java.util.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// oracleJoin
	public Connection getConn() {

		String jdbc_driver = "oracle.jdbc.OracleDriver";
		String db_url = "jdbc:oracle:thin:@localhost:1521:xe";

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
				System.out.println("와일문들어옴");
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardScore(rs.getInt("BOARDSCORE"));
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
	//Ranking DAO
	public List<BoardVO> rankingGetList(){
		conn = getConn();
		String sql = "select ROWNUM rn, BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE from (select BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE from gameboard ORDER BY BOARDSCORE)";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("rn"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardScore(rs.getInt("BOARDSCORE"));
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
	//pageNum 1~10 page
	public List<BoardVO> getPage(int page){
		conn = getConn();
		String sql = "SELECT * FROM "
				+ "("
				+ "SELECT ROWNUM rn, BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM (SELECT BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM DESC) where BOARDREPLE = 0"
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
				BoardVO.setBoardNum(rs.getInt("rn"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardScore(rs.getInt("BOARDSCORE"));
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
		System.out.println("아이디:"+boardId);
		System.out.println("텍스트:"+text);
		conn = getConn();
		String sql ="SELECT * FROM (SELECT ROWNUM rn, BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM (SELECT BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM DESC) where BOARDREPLE = 0) WHERE "+boardId+" = ?";
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, text);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("rn"));
				BoardVO.setBoardReple(rs.getInt("BOARDREPLE"));
				BoardVO.setBoardTitle(rs.getString("BOARDTITLE"));
				BoardVO.setBoardId(rs.getString("BOARDID"));
				BoardVO.setBoardTeam(rs.getString("BOARDTEAM"));
				BoardVO.setBoardScore(rs.getInt("BOARDSCORE"));
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
		String sql = "SELECT * FROM (SELECT ROWNUM rn, BOARDREPLE,BOARDNUM,BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE FROM(SELECT BOARDREPLE,BOARDNUM,BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE FROM GAMEBOARD ORDER BY BOARDNUM DESC)where BOARDREPLE = 0) WHERE rn =?";
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
				BoardVO.setBoardScore(rs.getInt("BOARDSCORE"));
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
		String sql = "select LPAD(' ',(level-1)*4,' ') || decode(LEVEL,1,'','ㄴID:') || BOARDID as BOARDID,BOARDTITLE,BOARDHIREDATE from GAMEBOARD start with BOARDNUM = ? CONNECT BY PRIOR BOARDNUM = BOARDREPLE ORDER SIBLINGS BY BOARDNUM";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardId(rs.getString("BOARDID"));
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
		String sql = 
				"INSERT INTO gameboard VALUES(board_num_seq2.NEXTVAL,?,?,'','아이디','','',SYSDATE)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, gameboard.getBoardNum());
			pstmt.setString(2, gameboard.getBoardTitle());

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
				"INSERT INTO gameboard VALUES(board_num_seq2.NEXTVAL,0,?,?,'아이디1','team',1111,SYSDATE)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, gameboard.getBoardTitle());
			pstmt.setString(2, gameboard.getContent());


			int n = pstmt.executeUpdate();

			return n > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return false;
	}
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
