import java.awt.Color;
import java.awt.Graphics;

public class Ball {

	int diameter=10;
	int x;
	int y;
	int speed;
	int speedX;
	int speedY;
	float degree;
	int ballType;
	Color color;
	Color colorB=color.blue;
	Color colorF=color.RED;
	
	private final int NORMAL=1;
	private final int FIRE=2;
	
	public Ball(int x,int y,int speed,int speedX,int speedY,float degree,int ballType){
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.speedX=speedX;
		this.speedY=speedY;
		this.degree=degree;
		this.ballType=ballType;
	}
	
	public Ball(int x,int y,int speed,int speedX,int speedY,int ballType){
		this.x=x;
		this.y=y;
		this.speed=speed;
		this.speedX=speedX;
		this.speedY=speedY;
		this.ballType=ballType;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public void setY(int y){
		this.y=y;
	}
	
	public void go(int speed,float degree){
		this.speed = speed;
		this.degree = degree;
		this.speedX = (int)(Math.cos(degree*Math.PI/180)*speed);
		this.speedY = -(int)(Math.sin(degree*Math.PI/180)*speed);
	}
	
	public void move(){
		if(ballType==NORMAL){
			x += speedX;
			y += speedY;
		}else{
			x += speedX*2;
			y += speedY*2;
		}
		
		
		if(x<=0){
			speedX = -speedX;
		}else if(x>=1190){
			speedX = -speedX;
		}
		
		if(y<=0){
			speedY = -speedY;
		}
	}
	
	public void reflectRect(int x,int y){
		if(this.x<=x+5){//判定打到左右
			speedX = -speedX;
		}else if(this.x>=x+75){
			speedX = -speedX;
		}
		
		if(this.y<=y+5){//判定打到上下
			speedY = -speedY;
		}else if(this.y>=y+25){
			speedY = -speedY;
		}
	}
	
	public void reflectBar(){
		speedY=-speedY;
	}
	
	public void draw(Graphics g){
		switch(ballType){
		case 1:	
			g.setColor(colorB);
			g.fillOval(x, y, diameter, diameter);
			break;
		case 2:
			g.setColor(colorF);
			g.fillOval(x, y, diameter, diameter);
			break;
		}
	}
	
}
