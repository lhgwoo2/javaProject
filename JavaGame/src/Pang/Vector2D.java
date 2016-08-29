package Pang;

public class Vector2D {
	double x;
	double y;
	
	public Vector2D(double x,double y){
		this.x = x;
		this.y = y;
	}
	public Vector2D add(Vector2D v1){
		return new Vector2D(this.x+ v1.x, this.y+v1.y);
	}
	
	public Vector2D sub(Vector2D v1){
		return new Vector2D(this.x- v1.x, this.y-v1.y);
	} 
	
	public Vector2D mul(double val){
		return new Vector2D(this.x*val,this.y*val);
	}
	
	public Vector2D div(double val){
		return new Vector2D(this.x/val,this.y/val);
	}
	
	public double length(){
		return Math.sqrt(x*x+y*y);
	}
	
	public double dot(Vector2D v1) {
		return this.x*v1.x + this.y*v1.y;
	}
	
	public Vector2D inverse(){
		return new Vector2D(-x,-y);
	}
	public Vector2D getunit(){
		return new Vector2D(x/length(),y/length());
	}
	public static Vector2D getVertical(double deg){  //멤버변수와 상관없는 함수이므로 static을 선언
		double rad = Math.toRadians(deg-90);
		return new Vector2D(Math.cos(rad),Math.sin(rad));
	}
	
}
