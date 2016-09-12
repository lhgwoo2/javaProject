package GamePanel;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import DataBase.GameBoard;

public class GameOverPanel extends JPanel {

   Image overImg,boardgo;
   int sx1, sy1, sx2, sy2;
   int row, col;
   MainPanel mp;
   PointPanel ppl;
   public GameOverPanel(MainPanel mp, PointPanel ppl){
      this.mp = mp;
      this.ppl = ppl;
	  mp.sound("gameover.wav");
      InputStream redwin = getClass().getResourceAsStream("RedTeamWin.png");
      InputStream bluewin = getClass().getResourceAsStream("BlueTeamWin.png");
      InputStream draw = getClass().getResourceAsStream("draw.png");
      InputStream board = getClass().getResourceAsStream("boardGo.png");
      setLayout(null);
      setBounds(0,0,1600,900);
      setOpaque(false);
      

     /* System.out.println(ppl.blueScore);
      System.out.println(ppl.redScore);*/
      
      
      try {
         if (ppl.blueScore > ppl.redScore)overImg = ImageIO.read(bluewin);
         else if(ppl.blueScore < ppl.redScore)overImg = ImageIO.read(redwin);
         else if(ppl.blueScore == ppl.redScore) overImg = ImageIO.read(draw);
         boardgo = ImageIO.read(board);
         
      } catch (IOException e) {
         e.printStackTrace();
      }
      mp.add(this);
      
      JButton boardStartButton = new JButton("게시판 들어가기");
      boardStartButton = new JButton(new ImageIcon(boardgo));
      boardStartButton.setBounds(790,500,200,100);
      mp.add(boardStartButton);
      repaint();
      
      boardStartButton.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            mp.f.setVisible(false);
            
            EventQueue.invokeLater(new Runnable() {
               @Override
               public void run() {
                  new GameBoard();
               }
            });
            
         }
      });
      
   }
   /*
   public void loop() {
      sx1 = col++ * 286;
      sy1 = row * 286;
      sx2 = sx1 + 286;
      sy2 = sy1 + 286;

      if (col == 5) {
         row++;
         col = 0;
      }
      if (row == 1) {
         row = 0;
      }
   }*/
   
   
   @Override
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(overImg, 590, 300, 1156, 544, 0, 0, 592, 311, null);
      
   }

   

}