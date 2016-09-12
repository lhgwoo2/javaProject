package DataBase;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class RankingTableModel extends AbstractTableModel{
	
	// AbstractTableModel �߻� ���̺� ��
	List<LoginVO> list;
	String[] colName = {"����", "�����̵�", "��", "����","����","���ӽð�","��¥"};
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
	
	/** �÷��� ���� �����Ѵ� */
	@Override
	public int getColumnCount() {

		return colName.length;
	}

	/** ���̺��� ����� �����Ѵ� */
	@Override
	public int getRowCount() {

		return data.length;
	}

	/** ���̺��� �÷����� �����Ѵ� */
	@Override
	public String getColumnName(int col) {
		return colName[col]; // �迭�� �ε���
	}

	/** �� ���� ��µ� �����͸� �����Ѵ� */
	@Override
	public Object getValueAt(int row, int col) {

		return data[row][col];
	}

	/** �信�� ������ �����͸� �� �ν��Ͻ��� �����Ѵ� */
	@Override
	public void setValueAt(Object value, int row, int col) {
		System.out.println("setValueAt(" + value + row + col + ")");
	}

	/** �� ���� ���¸� �����Ѵ�. ��)true/false�� üũ�ڽ��� ǥ���� */
	@Override
	public Class getColumnClass(int c) {
		return String.class;/*getValueAt(0, c).getClass();*/
	}

	/** ������ �������� ���θ� �����Ѵ� */
	@Override
	public boolean isCellEditable(int row, int col) {
		if (col < 5) {
			return false;
		} else {
		}
		return true;
	}

}
