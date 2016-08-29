package KSH;

import java.util.List;

import javax.swing.table.AbstractTableModel;


public class BoardTableModel extends AbstractTableModel{
	
	// AbstractTableModel 추상 테이블 모델
	List<BoardVO> list;
	String[] colName = { "게시물 번호", "제목", "아이디", "팀", "점수","날짜"};
	Object[][] data ;
	BoardDAO boardDao;
		public BoardTableModel(int i){
			
			boardDao = new BoardDAO();
			//list =boardDao.getList();
			list = boardDao.getPage(i);
			setData(list);
			
		}
		public void setData (List<BoardVO> list2){
			data = new Object[list2.size()][colName.length];
			for (int i = 0; i < list2.size(); i++) {
				BoardVO board = list2.get(i);
				data[i][0] = new Integer(board.getBoardNum());
				data[i][1] = board.getBoardTitle();
				data[i][2] = board.getBoardId();
				data[i][3] = board.getBoardTeam();
				data[i][4] = new Integer(board.getBoardScore());
				data[i][5] = board.getBoardHiredate();
			}
		}


		/** 컬럼의 수를 결정한다 */
		@Override
		public int getColumnCount() {

			return colName.length;
		}

		/** 테이블의 행수를 결정한다 */
		@Override
		public int getRowCount() {

			return data.length;
		}

		/** 테이블의 컬럼명을 결정한다 */
		@Override
		public String getColumnName(int col) {
			return colName[col]; // 배열의 인덱스
		}

		/** 각 셀에 출력될 데이터를 결정한다 */
		@Override
		public Object getValueAt(int row, int col) {

			return data[row][col];
		}

		/** 뷰에서 편집된 데이터를 모델 인스턴스에 전달한다 */
		@Override
		public void setValueAt(Object value, int row, int col) {
			System.out.println("setValueAt(" + value + row + col + ")");
		}

		/** 각 셀의 형태를 결정한다. 예)true/false를 체크박스로 표현함 */
		@Override
		public Class getColumnClass(int c) {
			return /*String.class;*/getValueAt(0, c).getClass();
		}

		/** 각셀의 편집가능 여부를 결정한다 */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 2) {
				return false;
			} else {
			}
			return true;
		}
	}
