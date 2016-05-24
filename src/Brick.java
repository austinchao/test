
import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

public class Brick{
	int x=190;
	int y=50;
	int count;
	int width=80;
	int height=30;
	int row;
	int col;
	Color color = Color.BLUE;
	public Brick(int count,int row,int col){
		this.count = count;
		this.row = row;
		this.col = col;		
	}
	
	public void getNum(int count,int row,int col){
		this.count = count;
		this.row = row;
		this.col = col;		
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d=(Graphics2D)g;
		g2d.setStroke(new BasicStroke(5.0f));
		g.setColor(color);
		if(count==0){
			
		}else if(count==1){
			g.setColor(Color.red);
			g.fillRect(x+row*width , y+col*height , width , height );
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x+row*width , y+col*height , width , height );
		}else if(count==2){
			g.setColor(Color.orange);
			g.fillRect(x+row*width , y+col*height , width , height );
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x+row*width , y+col*height , width , height );
		}else if(count==3){
			g.setColor(Color.YELLOW);
			g.fillRect(x+row*width , y+col*height , width , height );
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x+row*width , y+col*height , width , height );
		}else if(count==4){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x+row*width , y+col*height , width , height );
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x+row*width , y+col*height , width , height );
		}else if(count==5){
			g.setColor(Color.MAGENTA);
			g.fillRect(x+row*width , y+col*height , width , height );
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x+row*width , y+col*height , width , height );
		}
	}
}
