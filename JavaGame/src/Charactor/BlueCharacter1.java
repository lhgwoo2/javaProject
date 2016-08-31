package Charactor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Ball.*;
import GamePanel.MainPanel;

public class BlueCharacter1 extends BufferedImage  {
	
	//1
	Image img;
	int a =0;
	int b =0;
	int c = 90;
	int d = 90;
	JPanel j;
	public static double x ;
	public static int  y;
	boolean bool ;
	public double chx;	//캐릭터의 x좌표
	
	
	public BlueCharacter1(JPanel j){
		super(90,90, BufferedImage.TYPE_INT_ARGB);
		this.j = j;
		InputStream is = getClass().getResourceAsStream("/imagePack/attackBLUE.png"); //내가 불러온 이미지를 InputStream 형으로 받아온다.
	        try { 
	        	img = ImageIO.read(is); //프로젝트에 포함된 이미지 파일을 BufferedImage로 로드한다.
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	      
	}
	
	public void loop(){
		
		a+=90;
		c+=90;
		if(a==450){
			a=0;
			c=90;				
		}
	}
	
	public void setMove(int b, int d){
		this.b=b;
		this.d=d;
	}
	
	
	public void draw(Graphics2D g2d) { //캐릭터가 패널 밖으로 나가는순간 어떻게해야 할지 모르겠음.
		 x = j.getWidth() / 2 + chx;
		  y =  j.getHeight()-290;
		if (x >= j.getWidth() - 80) {
			chx = 710;
			g2d.translate(chx, j.getHeight() - 270);
		} else if (x <= 0) {
			chx = -j.getWidth() / 2 + 10;
			g2d.translate(chx, j.getHeight() - 270);// -80
		}

		g2d.drawImage(img, (int) x, y, (int) (x + 90), y + 90, a, b, c, d, null);
		g2d.fillOval((int) x, y, 90, 90);
	}


}//클래스 end
