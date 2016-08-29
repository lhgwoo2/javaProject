package KSH;

import java.awt.*;

import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import javax.swing.table.TableColumnModel;

public class GameBoard extends JFrame {
	JTable table;
	BoardTableModel btm;
	JButton nextButton;
	JButton previousButton;
	JButton[] jbutton;
	int page;
	int pageAdd;
	int lastLink;
	int startLink;

	public GameBoard() {
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(20, 20, 1605, 978);
		// panelCreate
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1605, 978);
		panel.setBackground(Color.cyan);
		add(panel);
		// panelInput
		boardName(panel);
		boardMainPanel(panel);
		currPage(panel);
		resetNavLink();
		setVisible(true);
	}

	// title
	public void boardName(JPanel panel) {
		Font font = new Font("맑은고딕", Font.BOLD, 70);
		JLabel boardName = new JLabel("게시판");
		boardName.setFont(font);
		boardName.setBounds(panel.getWidth() / 2 - 100, 20, 300, 100);
		panel.add(boardName);
	}

	// mainBoard
	public void boardMainPanel(JPanel panel) {
		table = new JTable();
		btm = new BoardTableModel(1);
		table.setModel(btm);
		table.setBackground(Color.MAGENTA);
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBounds(250, 150, 1100, 600);
		panel.add(tableScroll);
		// table size seting
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(1).setPreferredWidth(750);// 2번쨰 행의 넓이 조종
		table.setRowHeight(30);// 전체 행의 높이를 30으로
		// table.setRowHeight(5, 80);//6번째 행의 높이를 80으로 설정
	}
	//buttons
	public void currPage(JPanel panel) {

		// <<버튼
		previousButton = new JButton("이전");
		previousButton.setBounds(300, 800, 60, 50);
		panel.add(previousButton);
		previousButton.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				String str;
				int asd = Integer.parseInt(jbutton[0].getText());
				page = asd-1;
				resetNavLink();
				System.out.println(startLink);
				System.out.println(lastLink);
				for (int i = startLink,j=0; i <= lastLink; i++,j++) {
					
					str = String.valueOf(i);
					jbutton[j].setText(str);
				}
			}
		});

		// >>버튼
		nextButton = new JButton("다음");
		nextButton.setBounds(1150, 800, 60, 50);
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String str;
				int asd = Integer.parseInt(jbutton[4].getText());
				page = asd+1;
				resetNavLink();
				System.out.println(startLink);
				System.out.println(lastLink);
				for (int i = startLink,j=0; i <= lastLink; i++,j++) {
					
					str = String.valueOf(i);
					jbutton[j].setText(str);
				}
			}
		});

		// button 1~5
		jbutton = new JButton[5];
		for (int i = 0, j = 10; i < jbutton.length; i++, j += 150) {
			System.out.println(i);
			String str = String.valueOf(i + 1);

			jbutton[i] = new JButton(str);
			jbutton[i].setBackground(Color.green);
			jbutton[i].setBounds(400 + j, 800, 60, 50);
			panel.add(jbutton[i]);

		}
		ActionListener is = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				JButton jbt = (JButton) obj;
				page = Integer.parseInt(jbt.getText());
				for (int j = 0; j < jbutton.length; j++) {
					jbutton[j].setForeground(Color.black);
				}

				jbt.setForeground(Color.yellow);
				// Data Change
				BoardDAO BoardDAO = new BoardDAO();
				List<BoardVO> list = BoardDAO.getPage(page);
				btm.setData(list);
				table.setModel(btm);

				// table size seting
				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(1).setPreferredWidth(750);// 2번쨰 행의 넓이 조종
				table.setRowHeight(30);// 전체 행의 높이를 30으로
			}
		};
		for (int i = 0; i < 5; i++)
			jbutton[i].addActionListener(is);
		panel.add(nextButton);

	}

	public void resetNavLink() {
		for (int j = 0; j < jbutton.length; j++) {
			jbutton[j].setForeground(Color.black);
		}
		BoardDAO boardDao = new BoardDAO();

		int p = page;
		int g = (p - 1) / 5 + 1;
		lastLink = g * 5;
		startLink = lastLink - (5 - 1);
		int link = startLink;
		int endLink = lastLink + 1;
		System.out.println("startlink" + startLink);
		System.out.println("endlink" + endLink);

		if (link == 1) {
			previousButton.setEnabled(false);
			previousButton.setVisible(false);
			nextButton.setEnabled(true);
		} else if (boardDao.getPage(endLink).isEmpty()) {
			nextButton.setEnabled(false);
			previousButton.setEnabled(true);
			previousButton.setVisible(true);
		} else {
			nextButton.setEnabled(true);
			previousButton.setEnabled(true);
			previousButton.setVisible(true);
		}

		for (int i = 0; i < jbutton.length; i++) {
			if (boardDao.getPage(startLink + i).isEmpty()) {
				jbutton[i].setEnabled(false);
			} else {
				jbutton[i].setEnabled(true);
			}
		}

	}

	// mainStart
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameBoard();
			}
		});
	}

}
