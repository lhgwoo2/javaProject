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
		System.out.println("접속");
		conn = getConn();
		String sql = "select * from gameboard";
		System.out.println("접속확인");
		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println(sql);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				System.out.println("와일문들어옴");
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
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
	//pageNum 1~10 page
	public List<BoardVO> getPage(int page){
		conn = getConn();
		String sql = "	SELECT * FROM "
				+ "("
				+ "SELECT ROWNUM rn, BOARDNUM, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM gameboard"
				+ ")"
				+ "WHERE rn between ? AND ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 3*page-2);
			pstmt.setInt(2, page*3);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while (rs.next()) {
				BoardVO BoardVO = new BoardVO();
				BoardVO.setBoardNum(rs.getInt("BOARDNUM"));
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
	//writing
	public boolean insert(BoardVO gameboard){
		conn = getConn();
		String sql = 
				"INSERT INTO gameboard "
				+ "VALUES(board_num_seq2.NEXTVAL,?,?,'아이디1','red',1111,SYSDATE)";

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
	public BoardVO getDetail(int boardNum){
		System.out.println("접속");
		conn = getConn();
		String sql = "select BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE "
				+ "from gameboard "
				+ "where BOARDNUM = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				BoardVO BoardVO = new BoardVO();
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
			System.out.println("클로즈캐치문");
		}
	}
}
