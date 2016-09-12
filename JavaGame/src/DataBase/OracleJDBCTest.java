package DataBase;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

public class OracleJDBCTest extends JFrame {
		JPanel panel;
	  JScrollPane scrollPane;
	    ImageIcon icon;
	    BoardTableModel btm;
	    JTable table;
	    Border buttonLine;
	    MyRenderer cellRenderer;
	public  OracleJDBCTest(){
		setTitle("°Ô½ÃÆÇ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(20, 20, 1605, 978);
		// panelCreate
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1605, 978);
		panel.setBackground(Color.white);
		add(panel);
		ScrollTest();
		setVisible(true);
	}
	public void ScrollTest(){
		table = new JTable();
		btm = new BoardTableModel(1);
		table.setModel(btm);
		//ScorllPane input table 
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		tableScroll.getViewport().setBackground(Color.white);
		tableScroll.setBorder(buttonLine);
		panel.add(tableScroll);
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				 int row = table.rowAtPoint(e.getPoint());
			        if (row > -1)
			         {
			            cellRenderer.rowAtMouse = row;
			            cellRenderer.color = Color.GREEN;
			            table.repaint();
			         }
			}
		});
		cellRenderer = new MyRenderer();
		table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer); 	
		
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new OracleJDBCTest();
			}
		});
		//select * from emp
/*		BoardDAO dao = new BoardDAO();
		List<BoardVO> list =  dao.getList();
		for (int i = 0; i < list.size(); i++) {
			BoardVO emp = list.get(i);
			System.out.printf("%d,%d, %s, %s, %s, %s %n",
			emp.getBoardNum(),emp.getBoardReple(),emp.getBoardId(),emp.getBoardTitle(),emp.getContent(),emp.getBoardHiredate());
		
		}*/
		
	/*	BoardDAO dao = new BoardDAO();
		List<BoardVO> list =  dao.getSerch("asd","asd");
		for (int i = 0; i < list.size(); i++) {
			BoardVO emp = list.get(i);
			System.out.printf("%d,%d, %s, %s, %s, %s %n",
					emp.getBoardNum(),emp.getBoardReple(),emp.getBoardId(),emp.getBoardTitle(),emp.getContent(),emp.getBoardHiredate());
				
		}*/
	
	}

}
