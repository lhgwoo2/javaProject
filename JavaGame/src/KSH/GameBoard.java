package KSH;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;


public class GameBoard extends JFrame {

	JTable table;
	JTable rankingTable;
	BoardTableModel btm;
	RankingTableModel rtm;
	JButton rankingButton;
	JButton nextButton;
	JButton previousButton;
	JButton[] jbutton;
	JButton writingButton;
	JPanel mainNullPanel;
	BoardPanel panel;
	JPanel rankingPanel;
	JPanel writingPanel;
	JPanel detailPanel;
	JPanel updatePanel;
	Font Mainfont;
	Font subfont ;
	Font subfont2;
	Border buttonLine;
	int page;
	int pageAdd;
	int lastLink;
	int startLink;
	int pageTheNumber = 10;
	int getBoardNum;
	int tableNum;
	int setPreferredWidth = 750;
	int setRowHeight = 30;
	int bodyFieldLimit = 500;
	String repleText;
	MyRenderer cellRenderer;
	public GameBoard() {
		
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(20, 20, 1605, 978);
		// panelCreate 
		panel = new BoardPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1605, 978);
		panel.setBackground(Color.white);
		add(panel);
		//buttonLine value
		buttonLine = new LineBorder(Color.black,1);
		//font value
		Mainfont = new Font("나눔고딕", Font.BOLD, 70);
		subfont = new Font("나눔고딕", Font.BOLD, 20);
		subfont2 = new Font("나눔고딕", Font.PLAIN, 20);
		//panelInput
		ranking();
		boardMainPanel();
		update();
		boardName();
		currPage();
		writing();

		resetNavLink();
		setVisible(true);
	}

	// title
	public void boardName() {
		JLabel boardName = new JLabel("게시판");
		boardName.setFont(Mainfont);
		boardName.setBounds(680, 20, 300, 100);
		panel.add(boardName);
	}

	// mainBoard
	public void boardMainPanel() {
		table = new JTable();
		btm = new BoardTableModel(1);
		table.setModel(btm);
		//ScorllPane input table 
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		tableScroll.getViewport().setBackground(Color.white);
		tableScroll.setBorder(buttonLine);
		panel.add(tableScroll);
		
		
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// table num value get
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				tableNum = (Integer) table.getValueAt(row, 0);
				if(col == 1)
				{
					detail();
					//setVisible setting
					panel.setVisible(false);
					rankingPanel.setVisible(false);
					updatePanel.setVisible(false);
					detailPanel.setVisible(true);
					repaint();
				}
			}
		});
		// table size seting
		tableSizeSeting();
	}

	public void tableSizeSeting(){
		table.setOpaque(false);
		//Header colum not mouse move
		table.getTableHeader().setReorderingAllowed(false);
		//Header colum not size change
		table.getTableHeader().setResizingAllowed(false);
		//header size
		table.getTableHeader().setFont(new Font("나눔고딕", Font.BOLD, 17));
		//colum one select
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table size seting
		TableColumnModel columnModel = table.getColumnModel();
		//header back color
		table.getTableHeader().setBackground(Color.DARK_GRAY);
		//header text color
		table.getTableHeader().setForeground(Color.white);
		//focus delete
		table.setCellSelectionEnabled(false);
		// low 2 size seting
		columnModel.getColumn(1).setPreferredWidth(setPreferredWidth);
		// AllHeight 30 setting
		table.setRowHeight(setRowHeight);
		//renderer start//
		//renderer create
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		//alignment center
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		//column Alignment
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(dtcr);	
		}
		//renderer create
		DefaultTableCellRenderer dtcr2 = new DefaultTableCellRenderer();
		//alignment center
		dtcr2.setHorizontalAlignment(SwingConstants.LEFT);
		table.getColumnModel().getColumn(1).setCellRenderer(dtcr2);
		//select Color
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				 int row = table.rowAtPoint(e.getPoint());
			        if (row > -1)
			         {
			            cellRenderer.rowAtMouse = row;
			            cellRenderer.color = Color.orange;
			            table.repaint();
			         }
			}
		});	
		cellRenderer = new MyRenderer();
		table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	
	}
/*	public void resizeColumnWidth(JTable table) {
	 //max size is over Auto size setting
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}*/
public void ranking(){
	rankingButton = new JButton("랭킹");
	rankingButton.setBounds(1270, 120, 80, 40);
	rankingButton.setBackground(Color.white);
	rankingButton.setFont(subfont);
	panel.add(rankingButton);
	rankingButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			panel.setVisible(false);
			rankingPanel.setVisible(true);
		}
	});
	//ranking background 
	rankingPanel = new JPanel();
	rankingPanel.setLayout(null);
	rankingPanel.setBounds(0, 0, 1605, 978);
	rankingPanel.setBackground(Color.white);
	add(rankingPanel);
	//boardName
	JLabel boardName = new JLabel("랭킹");
	boardName.setFont(Mainfont);
	boardName.setBounds(680, 20, 300, 100);
	rankingPanel.add(boardName);
	//table
	rankingTable = new JTable();
	rtm = new RankingTableModel();
	rankingTable.setModel(rtm);
	rankingTableSizeSeting();
	//ScorllPane input table 
	JScrollPane tableScroll = new JScrollPane(rankingTable);
	tableScroll.setBounds((rankingPanel.getWidth()/2)-550, rankingPanel.getHeight()/2-320, 1100, 600);
	tableScroll.getViewport().setBackground(Color.white);
	tableScroll.setBorder(buttonLine);
	rankingPanel.add(tableScroll);
	//cancel
	JButton calcelButton = new JButton("나가기");
	calcelButton.setBounds(1250, 800, 100, 40);
	calcelButton.setBackground(Color.white);
	calcelButton.setBorder(buttonLine);
	calcelButton.setFont(subfont);
	rankingPanel.add(calcelButton);
	calcelButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int writingCancelSelect = JOptionPane.showConfirmDialog(null, "나가시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
			if (writingCancelSelect == JOptionPane.YES_OPTION) {
				//setVisible setting
				//writingPanel.setVisible(false);
				panel.setVisible(true);
				//writingButton.setVisible(true);
			} else
				return;

		}
	});

}
	public void rankingTableSizeSeting(){
	
		//table remodel
		rankingTable.setOpaque(false);
		//Header colum not mouse move
		rankingTable.getTableHeader().setReorderingAllowed(false);
		//Header colum not size change
		rankingTable.getTableHeader().setResizingAllowed(false);
		//header size
		rankingTable.getTableHeader().setFont(new Font("나눔고딕", Font.BOLD, 17));
		//colum one select
		rankingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// table size seting
		TableColumnModel columnModel = rankingTable.getColumnModel();
		//header setting
		rankingTable.getTableHeader().setBackground(Color.DARK_GRAY);
		rankingTable.getTableHeader().setForeground(Color.white);
		//columnModel.getColumn(1).setPreferredWidth(setPreferredWidth);// low 2 size seting
		rankingTable.setRowHeight(setRowHeight);// AllHeight 30 setting
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();//renderer create
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);//alignment center
		for (int i = 0; i < rankingTable.getColumnCount(); i++) {
			rankingTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);	
		}
	}
	// buttons
	public void currPage() {
		// <<버튼
		previousButton = new JButton("이전");
		previousButton.setBounds(480, 800, 80, 40);
		previousButton.setBorderPainted(false);
		previousButton.setContentAreaFilled(false);
		previousButton.setOpaque(false);
		previousButton.setFont(subfont);
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
		nextButton.setBounds(1040, 800, 80, 40);
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setOpaque(false);
		nextButton.setFont(subfont);
		nextButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String str;
				int asd = Integer.parseInt(jbutton[9].getText());
				page = asd + 1;
				resetNavLink();
				for (int i = startLink, j = 0; i <= lastLink; i++, j++) {
					str = String.valueOf(i);
					jbutton[j].setText(str);
				}
			}
		});

		// button 1~5
		jbutton = new JButton[10];
		for (int i = 0, j = 10; i < jbutton.length; i++, j += 50) {
			String str = String.valueOf(i + 1);

			jbutton[i] = new JButton(str);
			jbutton[i].setBounds( 530+j, 800, 60, 40);
			jbutton[i].setBorderPainted(false);
			jbutton[i].setContentAreaFilled(false);
			jbutton[i].setOpaque(false);
			jbutton[i].setFont(subfont2);
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
				jbt.setForeground(Color.red);
				// Data Change
				BoardDAO BoardDAO = new BoardDAO();
				List<BoardVO> list = BoardDAO.getPage(page);
				btm.setData(list);
				table.setModel(btm);

		// table size seting
		tableSizeSeting();
			}
		};
		for (int i = 0; i < jbutton.length; i++)
			jbutton[i].addActionListener(is);
		panel.add(nextButton);
		// writing button
		writingButton = new JButton("글쓰기");
		writingButton.setBounds(1250, 800, 100, 40);
		writingButton.setBackground(Color.white);
		writingButton.setBorder(BorderFactory.createLineBorder(Color.black));
		writingButton.setFont(subfont);
		panel.add(writingButton);
		writingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//setVisible setting
				writingPanel.setVisible(true);
				panel.setVisible(false);
				writingButton.setVisible(false);
				updatePanel.setVisible(false);
				rankingPanel.setVisible(false);
			}
		});
		search();
	}
	public void search(){
		JComboBox combo = new JComboBox();
		combo.setBounds(500, 850, 85, 25);
		combo.setBackground(Color.white);
		//combo.setBorder(BorderFactory.createLineBorder(Color.black));
		combo.setOpaque(false);
		combo.setEditable(false);
	    combo.addItem("아이디");
	    combo.addItem("제목");
	    combo.addItem("번호");
		panel.add(combo);
		combo.getSelectedItem();
		System.out.println(combo.getSelectedItem());
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				combo.getSelectedItem();
				System.out.println(combo.getSelectedItem());
			}
		});
		JTextArea serchText = new JTextArea();
		serchText.setBounds(600, 850,350, 25);
		serchText.setBackground(Color.white);
		serchText.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(serchText);
		
		JButton serchButton = new JButton("검색");
		serchButton.setBounds(965, 850,65, 25);
		serchButton.setBackground(Color.white);
		serchButton.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(serchButton);
	
		serchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String colName= "BOARDID";
				String colName2= "BOARDTITLE";
				String colName3= "rn";
			if(serchText.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"검색어를 입력 하세요");
			}else if (combo.getSelectedItem().equals("아이디")) {
				// data Change
				btm = new BoardTableModel(colName,serchText.getText());
				table.setModel(btm);
				tableSizeSeting();
			}else if (combo.getSelectedItem().equals("제목")) {
				// data Change
				btm = new BoardTableModel(colName2,serchText.getText());
				table.setModel(btm);
				tableSizeSeting();
			}else if (combo.getSelectedItem().equals("번호")) {
				// data Change
				btm = new BoardTableModel(colName3,serchText.getText());
				table.setModel(btm);
				tableSizeSeting();
			}
			repaint();
			}
		});
		JButton allViewButton = new JButton("전체보기");
		allViewButton.setBounds(1045, 850,75, 25);
		allViewButton.setBackground(Color.white);
		allViewButton.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(allViewButton);
		allViewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// data Change
				btm = new BoardTableModel(1);
				table.setModel(btm);
				tableSizeSeting();
				resetNavLink();
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
		int g = (p - 1) / 10 + 1;
		lastLink = g * pageTheNumber;
		startLink = lastLink - (pageTheNumber - 1);
		int link = startLink;
		int endLink = lastLink + 1;
		// 처음번호 1일경우
		// color change
		jbutton[0].setForeground(Color.red);
		// data Change
		List<BoardVO> list = boardDao.getPage(endLink - pageTheNumber);
		btm.setData(list);
		table.setModel(btm);
		// table size seting
		tableSizeSeting();

		if (link == 1) {
			previousButton.setEnabled(false);
			nextButton.setEnabled(true);
			if(boardDao.getPage(endLink).isEmpty()){
				nextButton.setEnabled(false);
			}
			// 마지박번호 +1 빈값일경우
		}else if (boardDao.getPage(endLink).isEmpty()) {
			previousButton.setEnabled(true);
			nextButton.setEnabled(false);
		}else {
			previousButton.setEnabled(true);
			nextButton.setEnabled(true);
		}

		for (int i = 0; i < jbutton.length; i++) {
			if (boardDao.getPage(startLink + i).isEmpty()) {
				jbutton[i].setEnabled(false);
				// nextButton.setEnabled(false);
			} else {
				jbutton[i].setEnabled(true);
			}
		}
		repaint();
	}

	public void writing() {

		// writingPanel
		writingPanel = new JPanel();
		writingPanel.setLayout(null);
		writingPanel.setBounds(0, 0, 1605, 978);
		writingPanel.setBackground(Color.white);
		add(writingPanel);
		writingPanel.setVisible(false);
		
		JLabel writingName = new JLabel("게시판");
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
		content.setDocument((new JTextFieldLimit(20)));
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
		body.setLineWrap(true);
		body.setDocument((new JTextFieldLimit(bodyFieldLimit)));
		body.setFont(subfont);
/*		JScrollPane ScrollBody = new JScrollPane(body);
		ScrollBody.setBounds(250, 230, 1100, 550);
		ScrollBody.setBorder(BorderFactory.createLineBorder(Color.black));
		writingPanel.add(ScrollBody);*/
		body.setBounds(250, 230, 1100, 550);
		body.setBorder(BorderFactory.createLineBorder(Color.black));
		writingPanel.add(body);

		// save button
		JButton saveButton = new JButton("저장");
		saveButton.setBounds(1130, 800, 100, 40);
		saveButton.setBackground(Color.white);
		saveButton.setBorder(buttonLine);
		saveButton.setFont(subfont);
		writingPanel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO boardDao = new BoardDAO();
				int saveSelect = JOptionPane.showConfirmDialog(null, "저장 하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (saveSelect == JOptionPane.YES_OPTION) {
					// insert value output
					BoardVO boardVo = new BoardVO();
					boardVo.setBoardTitle(content.getText());
					boardVo.setContent(body.getText());
					boardDao.insert(boardVo);

					content.setText(null);
					body.setText(null);
					//setVisible setting
				
					
					// data Change
					List<BoardVO> list = boardDao.getPage(page);
					btm.setData(list);
					table.setModel(btm);
					
					// resetNavLink
					resetNavLink();
					//visible setting
					panel.setVisible(true);
					writingPanel.setVisible(false);
					writingButton.setVisible(true);
				} else return;

			}
		});
		// cancel
		JButton calcelButton = new JButton("취소");
		calcelButton.setBounds(1250, 800, 100, 40);
		calcelButton.setBackground(Color.white);
		calcelButton.setBorder(buttonLine);
		calcelButton.setFont(subfont);
		writingPanel.add(calcelButton);
		calcelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int writingCancelSelect = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (writingCancelSelect == JOptionPane.YES_OPTION) {
					//setVisible setting
					writingPanel.setVisible(false);
					panel.setVisible(true);
					writingButton.setVisible(true);
				} else
					return;

			}
		});

	}

	public void detail() {

		// getDetail
		BoardDAO detaildao = new BoardDAO();
		BoardVO detailboardVO = detaildao.getDetail(tableNum);
		System.out.println(getBoardNum);
		getBoardNum = detailboardVO.getNumb();
		System.out.println(getBoardNum);
		//background 		
		detailPanel = new JPanel();
		detailPanel.setLayout(null);
		detailPanel.setBounds(0, 0, 1605, 978);
		detailPanel.setBackground(Color.white);
		panel.setBackground(Color.white);
		add(detailPanel);
		// detail panel create
		JPanel detailPanel2 = new JPanel();
		detailPanel2.setLayout(null);
		detailPanel2.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-350, 1100, 750);
		detailPanel2.setBackground(Color.white);
		detailPanel2.setBorder(buttonLine);
		detailPanel.add(detailPanel2);
		// reple box panel creat
		JPanel replePanel = new JPanel();
		replePanel.setLayout(null);
		replePanel.setBounds(50, 460, 1000, 190);
		replePanel.setBackground(Color.white);
		replePanel.setBorder(buttonLine);
		//detailPanel2.add(replePanel);
		//detail name
		JLabel name = new JLabel("게시판");
		name.setFont(Mainfont);
		name.setBounds(680, 20, 300, 100);
		detailPanel.add(name);
		// title
		JLabel detailName = new JLabel("Title : " + detailboardVO.getBoardTitle());
		detailName.setFont(subfont);
		detailName.setBounds(50, 10, 300, 30);
		detailPanel2.add(detailName);
		// Hiredate
		String date = String.valueOf(detailboardVO.getBoardHiredate());
		JLabel detailDate = new JLabel(date);
		detailDate.setFont(subfont);
		detailDate.setBounds(950, 10, 300, 30);
		detailPanel2.add(detailDate);
		// id
		JLabel detailId = new JLabel("ID : " + detailboardVO.getBoardId());
		detailId.setFont(subfont);
		detailId.setBounds(50, detailName.getHeight()+30, 200, 30);
		detailPanel2.add(detailId);
		// team
		JLabel detailTeam = new JLabel("Team : " + detailboardVO.getBoardTeam());
		detailTeam.setFont(subfont);
		detailTeam.setBounds(detailId.getX() + 150, detailName.getHeight()+30, 200, 30);
		detailPanel2.add(detailTeam);
		// score
		String score = String.valueOf(detailboardVO.getBoardScore());
		JLabel detailScore = new JLabel("Score : " + score);
		detailScore.setFont(subfont);
		detailScore.setBounds(detailTeam.getX() + 150, detailName.getHeight()+30, 300, 30);
		detailPanel2.add(detailScore);
		// bodyContent
		JTextArea detailContent = new JTextArea(detailboardVO.getContent());
		detailContent.setFont(subfont);
		detailContent.setEditable(false);
		detailContent.setLineWrap(true);
		JScrollPane bodyScroll = new JScrollPane(detailContent);
		bodyScroll.setBounds(50, 140, 1000, 300);
		bodyScroll.setBorder(buttonLine);
		detailPanel2.add(bodyScroll);
	/*	detailContent.setBounds(50, 140, 1000, 300);
		detailContent.setBorder(buttonLine);
		detailPanel2.add(detailContent);*/
		// 1 line
		JSeparator line1 = new JSeparator();
		line1.setBounds(50, detailName.getY()+35, 1000, 10);
		line1.setForeground(Color.black);
		detailPanel2.add(line1);
		// 2 line
		JSeparator line2 = new JSeparator();
		line2.setBounds(50, detailId.getY()+35, 1000, 10);
		line2.setForeground(Color.black);
		detailPanel2.add(line2);
		// 3 line
		JSeparator line3 = new JSeparator();
		line3.setBounds(50, 670, 1000, 10);
		line3.setForeground(Color.black);
		detailPanel2.add(line3);
		
		//reple text
		JTextArea reple = new JTextArea();
		reple.setLineWrap(true);
		reple.setDocument((new JTextFieldLimit(80)));
		reple.setFont(new Font("나눔고딕", Font.PLAIN, 20));
	    reple.setBounds(50, 690, 550, 40);
		reple.setBorder(buttonLine);
		detailPanel2.add(reple);
		
		//reple box and reple value input
		JTextArea repleBox = new JTextArea();
		repleBox.setBounds(50, 460, 1000, 190);
		repleBox.setEditable(false);
		JScrollPane repleBoxScroll = new JScrollPane(repleBox);
		repleBoxScroll.setBounds(50, 460, 1000, 190);
		repleBoxScroll.setBackground(Color.white);
		repleBoxScroll.setBorder(buttonLine);
		detailPanel2.add(repleBoxScroll);
		//value input
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list =  dao.getHierarchy(getBoardNum);
		repleText ="";
		for (int i = 0; i < list.size(); i++) {
			BoardVO BoardVO = list.get(i);
			repleText += String.format("%s    내용 : %s    날짜 : %s%n",
					BoardVO.getBoardId(),BoardVO.getBoardTitle(),BoardVO.getBoardHiredate());
			repleBox.setText(repleText);
		}
		//relple sign up button
		JButton signUpButton =new JButton("등록");
		signUpButton.setBackground(Color.white);
		signUpButton.setFont(subfont);
		signUpButton.setBounds(610, 690, 80, 40);
		signUpButton.setBorder(buttonLine);
		detailPanel2.add(signUpButton);
		signUpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(reple.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,"입력한 메세지가 없습니다");
				}else{
				BoardVO boardVo = new BoardVO();
				boardVo.setBoardNum(getBoardNum);
				boardVo.setBoardTitle(reple.getText());
				dao.repleInsert(boardVo);
				reple.setText(null);
				//data Change
				BoardDAO dao = new BoardDAO();
				List<BoardVO> list =  dao.getHierarchy(getBoardNum);
				repleText ="";
				for (int i = 0; i < list.size(); i++) {
					BoardVO BoardVO = list.get(i);
					repleText += String.format("%s    내용 : %s    날짜 : %s%n",
							BoardVO.getBoardId(),BoardVO.getBoardTitle(),BoardVO.getBoardHiredate());
					repleBox.setText(repleText);
				}
				repaint();
				}
			}
		});
 		// updateButton
		JButton updateButton = new JButton("수정");
		updateButton.setBounds(770, 690, 80, 40);
		updateButton.setBackground(Color.white);
		updateButton.setBorder(buttonLine);
		updateButton.setFont(subfont);
		detailPanel2.add(updateButton);

		updateButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int saveSelect = JOptionPane.showConfirmDialog(null, "정말 수정 하시겠습니까?", "수정",JOptionPane.YES_NO_OPTION);
		
			if (saveSelect == JOptionPane.YES_OPTION) {
						//setVisible setting
						panel.setVisible(false);
						detailPanel.setVisible(false);
						updatePanel.setVisible(true);
						repaint();
				} else return;
		}
	});
		// deleteButton
		JButton deleteButton = new JButton("삭제");
		deleteButton.setBounds(860, 690, 80, 40);
		deleteButton.setBackground(Color.white);
		deleteButton.setFont(subfont);
		deleteButton.setBorder(buttonLine);
		detailPanel2.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int saveSelect = JOptionPane.showConfirmDialog(null, "정말 삭제 하시겠습니까?", "삭제",JOptionPane.YES_NO_OPTION);
				if (saveSelect == JOptionPane.YES_OPTION) {
					//get delete
					detailboardVO.setBoardNum(getBoardNum);
					detaildao.getdelete(detailboardVO);
					// data Change
					List<BoardVO> list = detaildao.getPage(1);
					btm.setData(list);
					table.setModel(btm);
					//setVisible setting
					detailPanel.setVisible(false);
					panel.setVisible(true);
					resetNavLink();
				} else return;
			}
		});
		//out button
		JButton outButton = new JButton("나가기");
		outButton.setBounds(950, 690, 100, 40);
		outButton.setBackground(Color.white);
		outButton.setFont(subfont);
		outButton.setBorder(buttonLine);
		detailPanel2.add(outButton);
		outButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				int saveSelect = JOptionPane.showConfirmDialog(null, "나가시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (saveSelect == JOptionPane.YES_OPTION) {
					//setVisible setting
					detailPanel.setVisible(false);
					updatePanel.setVisible(false);
					panel.setVisible(true);
					repaint();
				} else return;
			}
		});
	}
	 	public void update(){ 
		//updatePanel
		updatePanel = new JPanel();
		updatePanel.setLayout(null);
		updatePanel.setBounds(0, 0, 1605, 978);
		updatePanel.setBackground(Color.white);
		add(updatePanel);
		
		JPanel updatePanel2 = new JPanel();
		updatePanel2.setLayout(null);
		updatePanel2.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		updatePanel2.setBackground(Color.ORANGE);
		updatePanel.add(updatePanel2);
		
		JLabel updateName = new JLabel("게시판");
		updateName.setFont(Mainfont);
		updateName.setBounds(680, 20, 300, 100);
		updatePanel.add(updateName);
		// title
		JLabel title = new JLabel("제목");
		title.setBounds(60, 50, 80, 30);
		title.setFont(subfont);
		updatePanel2.add(title);
		JTextArea content = new JTextArea();
		content.setDocument((new JTextFieldLimit(20)));
		content.setBounds(160, 50, 500, 30);
		content.setFont(subfont);
		content.setBorder(BorderFactory.createLineBorder(Color.black));
		content.getText();
		updatePanel2.add(content);
		// body
		JLabel bodyText = new JLabel("내용");
		bodyText.setBounds(60, 130, 80, 30);
		bodyText.setFont(subfont);
		updatePanel2.add(bodyText);
		JTextArea body = new JTextArea();
		body.setLineWrap(true); //한줄이 너무 길면 자동으로 개행할지 설정
		body.setDocument((new JTextFieldLimit(bodyFieldLimit)));
		body.setFont(subfont);
		JScrollPane ScrollBody = new JScrollPane(body);
		ScrollBody.setBounds(160, 100, 800, 400);
		ScrollBody.setBorder(BorderFactory.createLineBorder(Color.black));
		updatePanel2.add(ScrollBody);
		// saveButton
		JButton saveButton = new JButton("저장");
		saveButton.setBounds(790, 530, 80, 50);
		saveButton.setBackground(Color.white);
		saveButton.setBorder(buttonLine);
		saveButton.setFont(subfont);
		updatePanel2.add(saveButton);
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO updateDao = new BoardDAO();
				BoardVO boardVO = new BoardVO();
				int saveSelect = JOptionPane.showConfirmDialog(null, "변경된 사항 저장 하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (saveSelect == JOptionPane.YES_OPTION && !content.getText().isEmpty()) {
					boardVO.setBoardTitle(content.getText());
					boardVO.setContent(body.getText());
					boardVO.setBoardNum(getBoardNum);
					updateDao.getUpdate(boardVO);
					// data Change
					List<BoardVO> list = updateDao.getPage(1);
					btm.setData(list);
					table.setModel(btm);
					//reset
					content.setText(null);
					body.setText(null);
					
					detail();
					//setVisible setting
					panel.setVisible(false);
					detailPanel.setVisible(true);
					updatePanel.setVisible(false);
				}else if(saveSelect == JOptionPane.NO_OPTION){
					return;
				}else if(content.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "내용을 입력해 주세요");
				}
			}
		});
		// cancelButton
		JButton cancelButton = new JButton("취소");
		cancelButton.setBounds(880, 530, 80, 50);
		cancelButton.setBackground(Color.white);
		cancelButton.setBorder(buttonLine);
		cancelButton.setFont(subfont);
		updatePanel2.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int updateCancelSelect = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (updateCancelSelect == JOptionPane.YES_OPTION) {
					//setVisible setting
					panel.setVisible(false);
					detailPanel.setVisible(true);
					updatePanel.setVisible(false);
				} else return;
		
			}
		});
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
