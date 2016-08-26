package KSH;

import java.util.List;

public class OracleJDBCTest {

	public static void main(String[] args) {
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list = dao.getList();
		System.out.println("asdasdasd");
		for (int i = 0; i < list.size(); i++) {
			BoardVO emp = list.get(i);
			System.out.printf("%d %s, %s, %s, %d,%s %n",
			emp.getBoardNum(),emp.getBoardTitle(),emp.getBoardId(),emp.getBoardTeam(),emp.getBoardScore(),emp.getBoardHiredate());
			
			System.out.println(emp.getBoardNum());
		}
	}

}
