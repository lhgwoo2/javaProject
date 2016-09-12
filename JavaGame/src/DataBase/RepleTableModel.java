package DataBase;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import GamePanel.LoginPanel;

public class RepleTableModel extends AbstractTableModel{
	// AbstractTableModel 추상 테이블 모델
		List<BoardVO> list;
		String[] colName = {"","","",""};
		Object[][] data;
		BoardDAO boardDao;
		String clinetId;
		public RepleTableModel(int boardNum){
			boardDao = new BoardDAO();
			list = boardDao.getHierarchy(boardNum);
			//clientID input
			clinetId =LoginPanel.gClient.clientId;
			setData(list);
		}
			public void setData (List<BoardVO> list2){
				data = new Object[list2.size()][colName.length];
				for (int i = 0; i < list2.size(); i++) {
					BoardVO board = list2.get(i);
					data[i][0] = board.getBoardNum();
					data[i][1] = " 아이디 : "+board.getBoardId();
					data[i][2] = "  |  날짜 :"+board.getBoardHiredate()+"  |  내용 : "+board.getBoardTitle();
					if (clinetId.equals(board.getBoardId())) {
						data[i][3] = "삭제";
					}else data[i][3] = "";
				
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
				return String.class;/*getValueAt(0, c).getClass();*/
			}

			/** 각셀의 편집가능 여부를 결정한다 */
			@Override
			public boolean isCellEditable(int row, int col) {
				if (col < 6) {
					return false;
				} else {
				}
				return true;
			}
		}
