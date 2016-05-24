
import java.awt.Color;
import java.awt.Graphics;

public class Bar {
    int x;
    int y;
	int height=5;
	int width=120;
	Color color = Color.BLUE;
	
	public void setX(int x){
		this.x=x;
	}
	
	public Bar(int y){
		this.y=y;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x-width/2,y,width,height);
	}
}
