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
				+ "SELECT ROWNUM rn, BOARDNUM, BOARDREPLE, BOARDTITLE, BOARDID, BOARDTEAM, BOARDSCORE,BOARDHIREDATE FROM gameboard where BOARDREPLE = 0"
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
		System.out.println(gameboard.getBoardNum());
		System.out.println(gameboard.getBoardTitle());
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
	public BoardVO getDetail(int detailNum){
		conn = getConn();
		String sql = "select BOARDTITLE,BOARDCONTENT,BOARDID,BOARDTEAM,BOARDSCORE,BOARDHIREDATE "
				+ "from gameboard "
				+ "where BOARDNUM = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detailNum);
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
		System.out.println(updateInfo.getBoardTitle());
		System.out.println(updateInfo.getContent());
		System.out.println(updateInfo.getBoardNum());
		conn = getConn();
		String sql = "UPDATE gameboard SET boardTitle = ?, boardContent = ? where BOARDNUM = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateInfo.getBoardTitle());
			pstmt.setString(2, updateInfo.getContent());
			pstmt.setInt(3, updateInfo.getBoardNum());
			int n = pstmt.executeUpdate();
			System.out.println(n);

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
			System.out.println("클로즈캐치문");
		}
	}
}
