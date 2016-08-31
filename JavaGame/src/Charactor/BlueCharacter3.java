package Charactor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO; 
import javax.swing.JPanel;

public class BlueCharacter3 extends BufferedImage  {
	
	//1
	Image img;
	int a =0;
	int b =0;
	int c = 90;
	int d = 90;
	JPanel j;
	boolean bool ;
	public double chx;	//캐릭터의 x좌표
	
	
	public BlueCharacter3(JPanel j){
	    super(40, 40, BufferedImage.TYPE_INT_ARGB);
		this.j = j;
		InputStream is = getClass().getResourceAsStream("/imagePack/3rd_BLUE.png"); //내가 불러온 이미지를 InputStream 형으로 받아온다.
	        try { 
	        	img = ImageIO.read(is); //프로젝트에 포함된 이미지 파일을 BufferedImage로 로드한다.
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	        
	    Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
	}
	
	public void loop(){
		
		a+=90;
		c+=90;
		if(a==450){
			a=0;
			c=90;				
		}	
	}
	
	//캐릭터 좌우 이동을 위한 좌표값 변경
	public void setMove(int b, int d){
		this.b=b;
		this.d=d;
	}
	
	public void draw(Graphics2D g2d) { //캐릭터가 패널 밖으로 나가는순간 어떻게해야 할지 모르겠음.
		AffineTransform old = g2d.getTransform(); //유사 변환 행렬 변환에 대한정보를 가지고 있는 객체이다. 붓의 초기 위치 값을 가지고 있다 
		g2d.translate(j.getWidth()/2+chx, j.getHeight()-290);
		double x = j.getWidth() / 2 + chx;

		if (x >= j.getWidth() - 80) {
			chx = 710;
			g2d.translate(chx, j.getHeight() -270);
		} else if (x <= 0) {
			chx = -j.getWidth() / 2 + 10;
			g2d.translate(chx, j.getHeight() -270);//-80
		}

		g2d.drawImage(img, 0, 0,90,90,a,b,c,d,null);
		g2d.setTransform(old); //붓을 원래위치로 ㅊ초기화
	}


}//클래스 end
