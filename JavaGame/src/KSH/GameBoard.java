package KSH;

import java.awt.*;

import java.awt.event.*;
import java.sql.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

public class GameBoard extends JFrame {
	JTable table;
	BoardTableModel btm;
	JButton nextButton;
	JButton previousButton;
	JButton[] jbutton;
	JButton writingButton;
	JPanel panel;
	JPanel writingPanel;
	JPanel detailPanel;
	Font Mainfont = new Font("나눔고딕", Font.BOLD, 70);
	Font subfont = new Font("나눔고딕", Font.BOLD, 20);
	int page;
	int pageAdd;
	int lastLink;
	int startLink;
	int getBoardNum;
	public GameBoard() {
	
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(20, 20, 1605, 978);
		// panelCreate
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1605, 978);
		panel.setBackground(Color.cyan);
		add(panel);
		// panelInput
		boardName(panel);
		boardMainPanel(panel);
		currPage(panel);
		writing();
		
		
		resetNavLink();
		setVisible(true);

	}

	// title
	public void boardName(JPanel panel) {

		JLabel boardName = new JLabel("게시판");
		boardName.setFont(Mainfont);
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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//table num value get
				if (e.getValueIsAdjusting() || table.getSelectedRow() == -1)
					return;
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				getBoardNum = (Integer) table.getValueAt(row, 0);
				System.out.println(getBoardNum);
				detail();
				panel.setVisible(false);
				detailPanel.setVisible(true);
				
			}
		});
		
		// table size seting
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(1).setPreferredWidth(750);// 2번쨰 행의 넓이 조종
		table.setRowHeight(30);// AllHeight 30 setting
	}

	// buttons
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
				page = asd - 1;
				resetNavLink();
				repaint();
				for (int i = startLink, j = 0; i <= lastLink; i++, j++) {
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
				page = asd + 1;
				resetNavLink();
				for (int i = startLink, j = 0; i <= lastLink; i++, j++) {
					str = String.valueOf(i);
					jbutton[j].setText(str);
				}
			}
		});

		// button 1~5
		jbutton = new JButton[5];
		for (int i = 0, j = 10; i < jbutton.length; i++, j += 150) {
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
		// writing button
		writingButton = new JButton("글쓰기");
		writingButton.setBounds(1000, 860, 80, 50);
		panel.add(writingButton);
		writingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writingPanel.setVisible(true);
				panel.setVisible(false);
				writingButton.setVisible(false);
			}
		});
	}
	

	// NavLink
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
		//처음번호 1일경우
		//color change
		jbutton[0].setForeground(Color.yellow);
		//data Change
		List<BoardVO> list = boardDao.getPage(endLink-5);
		System.out.println(endLink-5);
		btm.setData(list);
		table.setModel(btm);
		repaint();
		if (link == 1) {
			previousButton.setEnabled(false);
			nextButton.setEnabled(true);
			//마지박번호 +1 빈값일경우
		} else if (boardDao.getPage(endLink).isEmpty()) {
			previousButton.setEnabled(true);
			nextButton.setEnabled(false);
		} else {
			previousButton.setEnabled(true);
			nextButton.setEnabled(true);
		}

		for (int i = 0; i < jbutton.length; i++) {
			if (boardDao.getPage(startLink + i).isEmpty()) {
				jbutton[i].setEnabled(false);
				//nextButton.setEnabled(false);
			} else {
				jbutton[i].setEnabled(true);
			}
		}

	}

	public void writing() {


		//writingPanel
		writingPanel = new JPanel();
		writingPanel.setLayout(null);
		writingPanel.setBounds(0, 0, 1605, 978);
		writingPanel.setBackground(Color.ORANGE);
		add(writingPanel);
		writingPanel.setVisible(false);
		JLabel writingName = new JLabel("글쓰기");
		writingName.setFont(Mainfont);
		writingName.setBounds(680, 20, 300, 100);
		writingPanel.add(writingName);
		
		// title
		JLabel title = new JLabel("제목");
		title.setBounds(150, 150, 80, 30);
		title.setFont(subfont);
		writingPanel.add(title);
		JTextArea content = new JTextArea();
		content.setBounds(250, 150, 500, 30);
		content.setFont(subfont);
		content.setBorder(BorderFactory.createLineBorder(Color.black));
		content.getText();
		writingPanel.add(content);
		
		// body
		JLabel bodyText = new JLabel("내용");
		bodyText.setBounds(150, 230, 80, 30);
		bodyText.setFont(subfont);
		writingPanel.add(bodyText);
		JTextArea body = new JTextArea();
		body.setFont(subfont);
		JScrollPane ScrollBody = new JScrollPane(body);
		ScrollBody.setBounds(250, 230, 1100, 600);
		ScrollBody.setBorder(BorderFactory.createLineBorder(Color.black));
		writingPanel.add(ScrollBody);
		
		// save button
		JButton saveButton = new JButton("저장");
		saveButton.setBounds(1100, 860, 80, 50);
		writingPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO boardDao = new BoardDAO();
				int saveSelect = JOptionPane.showConfirmDialog(null, "저장 하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if(saveSelect == JOptionPane.YES_OPTION){
					//insert value output
					BoardVO boardVo = new BoardVO();
					boardVo.setBoardTitle(content.getText());
					boardVo.setContent(body.getText());
					boardDao.insert(boardVo);
					
					content.setText(null);
					body.setText(null);

					writingPanel.setVisible(false);
					panel.setVisible(true);
					writingButton.setVisible(true);
					//data Change
					List<BoardVO> list = boardDao.getPage(1);
					btm.setData(list);
					table.setModel(btm);
					//resetNavLink
					resetNavLink();
				}else return;
				
			}
		});
		// cancel
		JButton calcelButton = new JButton("취소");
		calcelButton.setBounds(1220, 860, 80, 50);
		writingPanel.add(calcelButton);
		calcelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int cancelSelect = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (cancelSelect == JOptionPane.YES_OPTION) {
					writingPanel.setVisible(false);
					panel.setVisible(true);
					writingButton.setVisible(true);
				}else return;
				
			}
		});

	}
	public void detail(){
		//detail panel create
		detailPanel = new JPanel();
		detailPanel.setLayout(null);
		detailPanel.setBounds(250, 150, 1100, 600);
		detailPanel.setBackground(Color.white);
		add(detailPanel);
	
		BoardDAO detaildao2 = new BoardDAO();
		BoardVO detailboardVO = detaildao2.getDetail(getBoardNum);
		//title
		JLabel detailName = new JLabel("Title : "+detailboardVO.getBoardTitle());
		detailName.setFont(subfont);
		detailName.setBounds(50, 20, 300, 100);
		detailPanel.add(detailName);
		//Hiredate
		String date =String.valueOf(detailboardVO.getBoardHiredate());
		JLabel detailDate = new JLabel(date);
		detailDate.setFont(subfont);
		detailDate.setBounds(950, 20, 300, 100);
		detailPanel.add(detailDate);
		//id
		JLabel detailId = new JLabel("ID : "+detailboardVO.getBoardId());
		detailId.setFont(subfont);
		detailId.setBounds(50, 100, 200, 100);
		detailPanel.add(detailId);
		//team
		JLabel detailTeam = new JLabel("Team : "+detailboardVO.getBoardTeam());
		detailTeam.setFont(subfont);
		detailTeam.setBounds(detailId.getX()+150, 100, 200, 100);
		detailPanel.add(detailTeam);
		//score
		String score =  String.valueOf(detailboardVO.getBoardScore());
		JLabel detailScore = new JLabel("Score : "+score);
		detailScore.setFont(subfont);
		detailScore.setBounds(detailTeam.getX()+150, 100, 300, 100);
		detailPanel.add(detailScore);
		//bodyContent
		JLabel detailContent = new JLabel(detailboardVO.getContent());
		detailContent.setFont(subfont);
		detailContent.setBounds(50, 200, 1000, 300);
		detailContent.setBorder(BorderFactory.createTitledBorder("상세 내용"));	
		detailPanel.add(detailContent);
		//1 line
		JSeparator line1 = new JSeparator();
		line1.setBounds(50,100, 1000, 10);
		detailPanel.add(line1);
		//2 line
		JSeparator line2 = new JSeparator();
		line2.setBounds(50,180, 1000, 10);
		detailPanel.add(line2);
		//3 line
		JSeparator line3 = new JSeparator();
		line3.setBounds(50,520, 1000, 10);
		detailPanel.add(line3);
		
		//out button
		JButton outButton =new JButton("나가기");
		outButton.setBounds(970, 530, 80, 50);
		detailPanel.add(outButton);
		outButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int saveSelect = JOptionPane.showConfirmDialog(null, "나가시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if(saveSelect == JOptionPane.YES_OPTION){
					detailPanel.setVisible(false);
					panel.setVisible(true);
				}else return;
			}
		});
		
		
		detailPanel.setVisible(false);
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
