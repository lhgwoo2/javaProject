package DataBase;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.*;

import GamePanel.LoginPanel;


public class GameBoard extends JFrame {
	JTable table;
	JTable rankingTable;
	JTable repleTable;
	BoardTableModel btm;
	RankingTableModel rtm;
	RepleTableModel rptm;
	JButton rankingButton;
	JButton nextButton;
	JButton previousButton;
	JButton[] jbutton;
	JButton writingButton;
	JTextField serchText;
	JPanel mainNullPanel;
	JPanel userNevipanel;
	BoardPanel panel;
	BoardPanel rankingPanel;
	BoardPanel writingPanel;
	BoardPanel detailPanel;
	BoardPanel updatePanel;
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
	String tableId;
	int repleTableNum;
	String repleTableDelete;
	int setPreferredWidth = 750;
	int setRowHeight = 30;
	int bodyFieldLimit = 500;
	String repleText;
	String clinetId;
	String teamColor;
	MyRenderer cellRenderer;
	MyRendererReple cellRenderReple;
	Color commonColor;
	
	public GameBoard() {
		clinetId =LoginPanel.gClient.clientId;
		teamColor = LoginPanel.gClient.teamColor;
		
		setTitle("게시판");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setBounds(20, 20, 1605, 978);
		// panelCreate 
		panel = new BoardPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1605, 978);
		add(panel);
		//buttonLine value
		buttonLine = new LineBorder(Color.black,0);
		//value input
		Mainfont = new Font("나눔고딕", Font.BOLD, 70);
		subfont = new Font("나눔고딕", Font.BOLD, 20);
		subfont2 = new Font("나눔고딕", Font.PLAIN, 20);
		commonColor = new Color(255,255,255);
		//panelInput
		uesrNevi();
		ranking();
		boardMainPanel();
		update();
		boardName();
		currPage();
		writing();
		resetNavLink();
		
		setVisible(true);
	}
		public void resizeColumnWidth(JTable table) {
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
		table.setBackground(commonColor);
		table.setModel(btm);
		//ScorllPane input table 
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		tableScroll.getViewport().setBackground(commonColor);
		tableScroll.setBorder(buttonLine);
		panel.add(tableScroll);
	
		table.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// table num value get
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				tableNum = (Integer) table.getValueAt(row, 0);
				tableId = (String) table.getValueAt(row, 2);
				getBoardNum = tableNum;
				if(col == 1)
				{
					detail();
					//setVisible setting
					serchText.setText("검색어를 입력하세요");
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
		table.setFocusable(false);
		table.setCellSelectionEnabled(false);
		// low size seting
		columnModel.getColumn(0).setPreferredWidth(70);
		columnModel.getColumn(1).setPreferredWidth(setPreferredWidth);
		columnModel.getColumn(2).setPreferredWidth(130);
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
				 int col = table.columnAtPoint(e.getPoint());
			        if (row > -1 && col ==1)
			         {
			            cellRenderer.rowAtMouse = row;
			            cellRenderer.colAtMouse = col;
			            cellRenderer.color = new Color(255,204,051);
			            table.repaint();
			         }else {
			        	 cellRenderer.rowAtMouse = row;
			        	 cellRenderer.colAtMouse = col;
			        	 cellRenderer.color = new Color(255,255,255);
			        	 table.repaint();
			         }
			}
		});	
		cellRenderer = new MyRenderer();
		table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		
	}
	public void uesrNevi(){

		//userNevi Create
		userNevipanel = new JPanel();
		userNevipanel.setLayout(null);
		userNevipanel.setBackground(commonColor);
		userNevipanel.setOpaque(false);
		userNevipanel.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-365, 1000, 40);
		panel.add(userNevipanel);
		//Uesr ID
		JLabel userId = new JLabel("팀 : "+teamColor+"      아이디 : "+clinetId);
		userId.setBounds(10,5,1000,30);
		userId.setBackground(commonColor);
		userId.setFont(new Font("나눔고딕", Font.BOLD, 16));
		userNevipanel.add(userId);	
	}
public void ranking(){
	rankingButton = new JButton("랭킹");
	rankingButton.setBounds(1270, 120, 80, 40);
	rankingButton.setBackground(commonColor);
	rankingButton.setFont(subfont);
	panel.add(rankingButton);
	rankingButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			writingPanel.setVisible(false);
			panel.setVisible(false);
			rankingPanel.setVisible(true);
		}
	});
	//ranking background 
	rankingPanel = new BoardPanel();
	rankingPanel.setLayout(null);
	rankingPanel.setBounds(0, 0, 1605, 978);
	rankingPanel.setBackground(Color.white);
	add(rankingPanel);
	//boardName
	JLabel boardName = new JLabel("랭킹");
	boardName.setFont(Mainfont);
	boardName.setBounds(720, 20, 300, 100);
	rankingPanel.add(boardName);
	//table
	rankingTable = new JTable();
	rtm = new RankingTableModel();
	rankingTable.setBackground(commonColor);
	rankingTable.setModel(rtm);
	rankingTableSizeSeting();
	//ScorllPane input table 
	JScrollPane tableScroll = new JScrollPane(rankingTable);
	tableScroll.setBounds((rankingPanel.getWidth()/2)-550, rankingPanel.getHeight()/2-320, 1100, 600);
	tableScroll.getViewport().setBackground(commonColor);
	tableScroll.setBorder(buttonLine);
	rankingPanel.add(tableScroll);
	//cancel
	JButton calcelButton = new JButton("나가기");
	calcelButton.setBounds(1250, 800, 100, 40);
	calcelButton.setBackground(commonColor);
	calcelButton.setBorder(buttonLine);
	calcelButton.setFont(subfont);
	rankingPanel.add(calcelButton);
	calcelButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int writingCancelSelect = JOptionPane.showConfirmDialog(null, "나가시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
			if (writingCancelSelect == JOptionPane.YES_OPTION) {
				rankingPanel.setVisible(false);
				panel.setVisible(true);
				resetNavLink();
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
		//focus
		rankingTable.setFocusable(false);
		rankingTable.setCellSelectionEnabled(false);
		// table size seting
		rankingTable.getColumnModel().getColumn(0).setWidth(70);
		rankingTable.getColumnModel().getColumn(0).setMinWidth(0);
		rankingTable.getColumnModel().getColumn(0).setMaxWidth(70);
		//header setting
		rankingTable.getTableHeader().setBackground(Color.DARK_GRAY);
		rankingTable.getTableHeader().setForeground(Color.white);
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
		writingButton.setBackground(commonColor);
		writingButton.setBorder(buttonLine);
		writingButton.setFont(subfont);
		panel.add(writingButton);
		writingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//setVisible setting
				serchText.setText("검색어를 입력하세요");
				writingPanel.setVisible(true);
				panel.setVisible(false);
				writingButton.setVisible(false);
				updatePanel.setVisible(false);
				rankingPanel.setVisible(false);
				resetNavLink();
			}
		});
		search();
	}
	public void search(){
		JComboBox combo = new JComboBox();
		combo.setBounds(500, 850, 85, 25);
		combo.setBackground(commonColor);
		combo.setBorder(buttonLine);
		combo.setOpaque(false);
		combo.setEditable(false);
	    combo.addItem("아이디");
	    combo.addItem("제목");
	    combo.addItem("번호");
		panel.add(combo);
		combo.getSelectedItem();
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				combo.getSelectedItem();
			}
		});
		//serch Text
		serchText = new JTextField();
		serchText.setBounds(600, 850,350, 25);
		serchText.setBackground(commonColor);
		serchText.setBorder(buttonLine);
		panel.add(serchText);
		serchText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				serchText.setText(null);
			}
		});
		//enter search
		serchText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String colName= "BOARDID";
					String colName2= "BOARDTITLE";
					String colName3= "BOARDNUM";
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
					}
			}
		});
		
		//search Button
		JButton serchButton = new JButton("검색");
		serchButton.setBounds(965, 850,65, 25);
		serchButton.setBackground(commonColor);
		serchButton.setBorder(buttonLine);
		panel.add(serchButton);
		//button search
		serchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String colName= "BOARDID";
				String colName2= "BOARDTITLE";
				String colName3= "BOARDNUM";
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
			}
		});
		//all view button
		JButton allViewButton = new JButton("전체보기");
		allViewButton.setBounds(1045, 850,75, 25);
		allViewButton.setBackground(commonColor);
		allViewButton.setBorder(buttonLine);
		panel.add(allViewButton);
		allViewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// data Change
				serchText.setText("검색어를 입력하세요");
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
		writingPanel = new BoardPanel();
		writingPanel.setLayout(null);
		writingPanel.setBounds(0, 0, 1605, 978);
		add(writingPanel);
		writingPanel.setVisible(false);
		
		JPanel writingPanel2 = new JPanel();
		writingPanel2.setLayout(null);
		writingPanel2.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		writingPanel2.setBackground(new Color(255, 0, 0, 0));
		writingPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		writingPanel.add(writingPanel2);
		
		JLabel writingName = new JLabel("글쓰기");
		writingName.setFont(Mainfont);
		writingName.setBounds(680, 20, 300, 100);
		writingPanel.add(writingName);

		// title
		JLabel title = new JLabel("제목");
		title.setBounds(60, 50, 80, 30);
		title.setFont(subfont);
		writingPanel2.add(title);
		JTextArea content = new JTextArea();
		content.setBounds(160, 50, 500, 30);
		content.setDocument((new JTextFieldLimit(20)));
		content.setFont(subfont);
		content.setBorder(buttonLine);
		content.setBackground(commonColor);
		writingPanel2.add(content);

		// body
		JLabel bodyText = new JLabel("내용");
		bodyText.setBounds(60, 100, 80, 30);
		bodyText.setFont(subfont);
		writingPanel2.add(bodyText);
		JTextArea body = new JTextArea();
		body.setLineWrap(true);
		body.setDocument((new JTextFieldLimit(bodyFieldLimit)));
		body.setFont(subfont);
		body.setBounds(160, 100, 800, 400);
		body.setBorder(buttonLine);
		body.setBackground(commonColor);
		writingPanel2.add(body);

		//writing save button
		JButton saveButton = new JButton("저장");
		saveButton.setBounds(790, 530, 80, 50);
		saveButton.setBackground(commonColor);
		saveButton.setBorder(buttonLine);
		saveButton.setFont(subfont);
		writingPanel2.add(saveButton);
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO boardDao = new BoardDAO();
				int saveSelect = JOptionPane.showConfirmDialog(null, "저장 하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
	
				if (saveSelect == JOptionPane.YES_OPTION && !content.getText().isEmpty() && !body.getText().isEmpty()) {
					// insert value output
					BoardVO boardVo = new BoardVO();
					boardVo.setBoardTitle(content.getText());
					boardVo.setContent(body.getText());
					boardVo.setBoardId(clinetId);
					boardVo.setBoardTeam(teamColor);
					boardDao.insert(boardVo);
					//Text Reset
					content.setText(null);
					body.setText(null);		
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
				} else if(saveSelect == JOptionPane.NO_OPTION){
					return;
				}else{
					JOptionPane.showMessageDialog(null, "제목과 내용을 입력해 주세요"); 
					return;
				}
			}
		});
		// cancel
		JButton calcelButton = new JButton("취소");
		calcelButton.setBounds(880, 530, 80, 50);
		calcelButton.setBackground(commonColor);
		calcelButton.setBorder(buttonLine);
		calcelButton.setFont(subfont);
		writingPanel2.add(calcelButton);
		calcelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int writingCancelSelect = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (writingCancelSelect == JOptionPane.YES_OPTION) {
					//setVisible setting
					content.setText(null);
					body.setText(null);
					writingPanel.setVisible(false);
					panel.setVisible(true);
					writingButton.setVisible(true);
				} else return;
			}
		});
	}
	public void detail() {
		// getDetail
		BoardDAO detaildao = new BoardDAO();
		BoardVO detailboardVO = detaildao.getDetail(tableNum);
		//getBoardNum = detailboardVO.getNumb();
		//background
		detailPanel = new BoardPanel();
		detailPanel.setLayout(null);
		detailPanel.setBounds(0, 0, 1605, 978);
		detailPanel.setBackground(Color.white);
		panel.setBackground(Color.white);
		add(detailPanel);
		// detail panel create
		JPanel detailPanel2 = new JPanel();
		detailPanel2.setLayout(null);
		detailPanel2.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-350, 1100, 750);
		detailPanel2.setBackground(new Color(255, 0, 0, 0));
		detailPanel2.setBorder(buttonLine);
		detailPanel.add(detailPanel2);
		//detail name
		JLabel name = new JLabel("게시판");
		name.setFont(Mainfont);
		name.setBounds(680, 20, 300, 100);
		detailPanel.add(name);
		// title
		JLabel detailName = new JLabel("제목 : " + detailboardVO.getBoardTitle());
		detailName.setFont(subfont);
		detailName.setBounds(50, 10, 300, 30);
		detailPanel2.add(detailName);
		// Hiredate
		String date = String.valueOf(detailboardVO.getBoardHiredate());
		JLabel detailDate = new JLabel(date);
		detailDate.setFont(subfont);
		detailDate.setBounds(950, 10, 300, 30);
		detailPanel2.add(detailDate);
		// id & team
		JLabel detailId = new JLabel("팀 : " + detailboardVO.getBoardTeam()+"     아이디 : " + detailboardVO.getBoardId());
		detailId.setFont(subfont);
		detailId.setBounds(50, detailName.getHeight()+30, 1000, 30);
		detailId.setBackground(Color.red);
		detailPanel2.add(detailId);
		// bodyContent
		JTextArea detailContent = new JTextArea(detailboardVO.getContent());
		detailContent.setFont(subfont);
		detailContent.setEditable(false);
		detailContent.setLineWrap(true);
		detailContent.setBackground(commonColor);
		JScrollPane bodyScroll = new JScrollPane(detailContent);
		bodyScroll.setBounds(50, 140, 1000, 300);
		bodyScroll.setBorder(buttonLine);
		detailPanel2.add(bodyScroll);
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
		reple.setDocument((new JTextFieldLimit(55)));
		reple.setFont(new Font("나눔고딕", Font.PLAIN, 20));
	    reple.setBounds(50, 690, 550, 40);
		reple.setBorder(buttonLine);
		reple.setBackground(commonColor);
		detailPanel2.add(reple);
		//reple table
		repleTable = new JTable();
		rptm = new RepleTableModel(tableNum);
		repleTable.setModel(rptm);
		JScrollPane repleBoxScroll = new JScrollPane(repleTable);
		repleBoxScroll.setBounds(50, 460, 1000, 190);
		repleBoxScroll.setBorder(buttonLine);
		repleBoxScroll.getViewport().setBackground(commonColor);
		detailPanel2.add(repleBoxScroll);
		
		repleTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// table num value get
				int row = repleTable.getSelectedRow();
				int col = repleTable.getSelectedColumn();
				repleTableNum = (Integer) repleTable.getValueAt(row, 0);
				repleTableDelete = (String) repleTable.getValueAt(row, 3);
				if(col == 3 && repleTableDelete.equals("삭제"))
				{
					int repleDeleteSelect = JOptionPane.showConfirmDialog(null, "댓글 삭제 하시겠습니까?", "댓글 삭제",JOptionPane.YES_NO_OPTION);
					if(repleDeleteSelect == JOptionPane.YES_OPTION){
						//VO -> DAO -> Delete
						detailboardVO.setBoardNum(repleTableNum);
						detaildao.repleDelete(detailboardVO);
						//data Change
						BoardDAO dao = new BoardDAO();
						List<BoardVO> list = dao.getHierarchy(getBoardNum);
						rptm.setData(list);
						repleTable.setModel(rptm);
						//setting
						repleTableSizeSetting();
						repaint();
					}else return;
					
				}else return;
			}
			
		});
		
		repleTableSizeSetting();
		
		//relple sign up button
		BoardDAO dao = new BoardDAO();
		JButton signUpButton =new JButton("등록");
		signUpButton.setBackground(commonColor);
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
				boardVo.setBoardId(clinetId);
				dao.repleInsert(boardVo);
				reple.setText(null);
				//data Change
				BoardDAO dao = new BoardDAO();
				List<BoardVO> list = dao.getHierarchy(getBoardNum);
				rptm.setData(list);
				repleTable.setModel(rptm);
				//setting
				repleText ="";
				repleTableSizeSetting();
				repaint();
				}
			}
		});
		//detail updateButton
		if(tableId.equals(clinetId)){
		JButton updateButton = new JButton("수정");
		updateButton.setBounds(770, 690, 80, 40);
		updateButton.setBackground(commonColor);
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
}
		if(tableId.equals(clinetId)){
		// deleteButton
		JButton deleteButton = new JButton("삭제");
		deleteButton.setBounds(860, 690, 80, 40);
		deleteButton.setBackground(commonColor);
		deleteButton.setFont(subfont);
		deleteButton.setBorder(buttonLine);
		detailPanel2.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(repleTable.getRowCount() == 0){
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
			}else {
				JOptionPane.showMessageDialog(null, "등록된 댓글이 있어 삭제할 수 없습니다.");
				return;
			}
			}
		});
	}
		//out button
		JButton outButton = new JButton("나가기");
		outButton.setBounds(950, 690, 100, 40);
		outButton.setBackground(commonColor);
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
	//repleTableSize
	public void repleTableSizeSetting(){
		//header Setting
		repleTable.getTableHeader().setBackground(Color.white);
		repleTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.white));
		//number
		repleTable.getColumnModel().getColumn(0).setWidth(0);
		repleTable.getColumnModel().getColumn(0).setMinWidth(0);
		repleTable.getColumnModel().getColumn(0).setMaxWidth(0);
		//ID
		repleTable.getColumnModel().getColumn(1).setWidth(100);
		repleTable.getColumnModel().getColumn(1).setMinWidth(0);
		repleTable.getColumnModel().getColumn(1).setMaxWidth(1000);
		//delete
		repleTable.getColumnModel().getColumn(3).setWidth(35);
		repleTable.getColumnModel().getColumn(3).setMinWidth(0);
		repleTable.getColumnModel().getColumn(3).setMaxWidth(35);
		//seting
		repleTable.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		repleTable.setRowHeight(25);
		repleTable.setBackground(Color.white);
		repleTable.setShowVerticalLines(false);
		repleTable.setShowHorizontalLines(false);
		repleTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		repleTable.setFocusable(false);
		repleTable.getTableHeader().setReorderingAllowed(false);
		repleTable.getTableHeader().setResizingAllowed(false);
		repleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		repleTable.setCellSelectionEnabled(false);
		resizeColumnWidth(repleTable);
		//delete Color view
		repleTable.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				 int row = repleTable.rowAtPoint(e.getPoint());
				 int col = repleTable.columnAtPoint(e.getPoint());
				 String repleTableDelete = (String) repleTable.getValueAt(row, 3);
			        if (row > -1 && repleTableDelete.equals("삭제"))
			         {
			        	cellRenderReple.rowAtMouse = row;
			        	cellRenderReple.colAtMouse = col;
			        	cellRenderReple.color = new Color(204,255,255);
			            repleTable.repaint();
			         }else {
			        	 cellRenderReple.rowAtMouse = row;
			        	 cellRenderReple.color = new Color(255,255,255);
			        	 repleTable.repaint();
			         }
			}
		});	
		cellRenderReple = new MyRendererReple();
		repleTable.getColumnModel().getColumn(3).setCellRenderer(cellRenderReple);
	}
	 	public void update(){ 
		//updatePanel
		updatePanel = new BoardPanel();
		updatePanel.setLayout(null);
		updatePanel.setBounds(0, 0, 1605, 978);
		updatePanel.setBackground(Color.white);
		add(updatePanel);
		
		JPanel updatePanel2 = new JPanel();
		updatePanel2.setLayout(null);
		updatePanel2.setBorder(buttonLine);
		updatePanel2.setBounds((panel.getWidth()/2)-550, panel.getHeight()/2-320, 1100, 600);
		updatePanel2.setBackground(new Color(255, 0, 0, 0));
		updatePanel.add(updatePanel2);
		
		JLabel updateName = new JLabel("수정");
		updateName.setFont(Mainfont);
		updateName.setBounds(720, 20, 400, 100);
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
		content.setBackground(commonColor);
		content.setBorder(buttonLine);
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
		body.setBackground(commonColor);
		body.setFont(subfont);
		body.setBounds(160, 100, 800, 400);
		body.setBorder(buttonLine);
		updatePanel2.add(body);
		// saveButton
		JButton saveButton = new JButton("저장");
		saveButton.setBounds(790, 530, 80, 50);
		saveButton.setBackground(commonColor);
		saveButton.setBorder(buttonLine);
		saveButton.setFont(subfont);
		updatePanel2.add(saveButton);
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BoardDAO updateDao = new BoardDAO();
				BoardVO boardVO = new BoardVO();
				int saveSelect = JOptionPane.showConfirmDialog(null, "변경된 사항 저장 하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (saveSelect == JOptionPane.YES_OPTION && !content.getText().isEmpty() && !body.getText().isEmpty()) {
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
				}else {
					JOptionPane.showMessageDialog(null, "제목과 내용을 입력해 주세요");
				}
			}
		});
		// cancelButton
		JButton cancelButton = new JButton("취소");
		cancelButton.setBounds(880, 530, 80, 50);
		cancelButton.setBackground(commonColor);
		cancelButton.setBorder(buttonLine);
		cancelButton.setFont(subfont);
		updatePanel2.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int updateCancelSelect = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "예/아니오", JOptionPane.YES_NO_OPTION);
				if (updateCancelSelect == JOptionPane.YES_OPTION) {
					//reset
					content.setText(null);
					body.setText(null);
					//setVisible setting
					panel.setVisible(false);
					detailPanel.setVisible(true);
					updatePanel.setVisible(false);
				} else return;
			}
		});
	}
/*	 	// mainStart
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameBoard();
			}
		});
	}*/

}