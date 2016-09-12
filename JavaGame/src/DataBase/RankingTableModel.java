package DataBase;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class RankingTableModel extends AbstractTableModel{
	
	// AbstractTableModel 추상 테이블 모델
	List<LoginVO> list;
	String[] colName = {"순위", "팀아이디", "팀", "점수","승패","게임시간","날짜"};
	Object[][] data ;
	BoardDAO boardDao;
		public RankingTableModel(){
			
			boardDao = new BoardDAO();
			list = boardDao.rankingGetList();
			setData(list);
			
		}
		public void setData (List<LoginVO> list2){
			data = new Object[list2.size()][colName.length];
			for (int i = 0; i < list2.size(); i++) {
				LoginVO login = list2.get(i);
				data[i][0] = new Integer(login.getTeamNum());
				data[i][1] = login.getTeamId();
				data[i][2] = login.getTeamColor();
				data[i][3] = new Integer(login.getScore());
				data[i][4] = login.getVictory();
				data[i][5] = login.getPlayTime();
				data[i][6] = login.getGameDate();
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
		if (col < 5) {
			return false;
		} else {
		}
		return true;
	}

}
