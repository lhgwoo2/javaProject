package Vector2D;

public class Vector2D 
{
    double x,y;
      
    public Vector2D() {}
      
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }
      
    public Vector2D add(Vector2D v){
        return new Vector2D(this.x+v.x, this.y+v.y);
    }
      
    public Vector2D sub(Vector2D v){
        return new Vector2D(this.x-v.x, this.y-v.y);
    }
      
    public Vector2D mul(double val){
        return new Vector2D(this.x*val, this.y*val);
    }
      
    public Vector2D div(double val){
        return new Vector2D(this.x/val, this.y/val);
    }
      
    public double length(){
        return Math.sqrt(x*x + y*y);
    }
      
    public double dot(Vector2D v){
        return this.x*v.x + this.y*v.y;
    }
      
    public Vector2D inv() {
        return new Vector2D(this.x*-1, this.y*-1);
    }
     
    public Vector2D unit() {
        return div( length() );
    }
     
    /** 파라미터로 전달된 일반각(Deg)의 방향에 대한 수직벡터를 단위벡터로 리턴한다 */
    public static Vector2D getVertical(double deg){
        double rad = Math.toRadians(deg-90);
        double sinVal = Math.sin(rad);
        double cosVal = Math.cos(rad);
        return new Vector2D(cosVal, sinVal);
      }
}