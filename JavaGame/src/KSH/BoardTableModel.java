package KSH;

import java.util.List;

import javax.swing.table.AbstractTableModel;


public class BoardTableModel extends AbstractTableModel{
	
	// AbstractTableModel �߻� ���̺� ��
	List<BoardVO> list;
	String[] colName = { "��ȣ", "����", "���̵�", "��", "����","��¥"};
	Object[][] data ;
	BoardDAO boardDao;
		public BoardTableModel(int i){
			
			boardDao = new BoardDAO();
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
			return /*String.class;*/getValueAt(0, c).getClass();
		}

		/** ������ �������� ���θ� �����Ѵ� */
		@Override
		public boolean isCellEditable(int row, int col) {
			if (col < 6) {
				return false;
			} else {
			}
			return true;
		}
	}
