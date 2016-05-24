import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Item {

	int x,y;
	int type;
	
	ImageIcon icon;
	Image image;
	
	private int SPEED=3;
	private final int BAR_LONGER=1;
	private final int BAR_SHORTER=2;
	private final int BAR_UNSEE=3;
	private final int BALL_FIRE=4;
	private final int BALL_PLUS=5;
	private final int DIE=6;
	
	public Item(int x,int y,int type){
		this.x=x;
		this.y=y;
		this.type=type;
	}
	
	public void move(){
		y+=SPEED;
	}
	
	public void draw(Graphics g){
		
		switch(type){
		case BAR_LONGER:
			icon = new ImageIcon( getClass().getResource( "bar_longer.png" ) );
			break;
		case BAR_SHORTER:
			icon = new ImageIcon( getClass().getResource( "bar_shorter.png" ) );
			break;
		case BAR_UNSEE:
			icon = new ImageIcon( getClass().getResource( "bar_unsee.png" ) );
			break;
		case BALL_FIRE:
			icon = new ImageIcon( getClass().getResource( "ball_fire.png" ) );
			break;
		case BALL_PLUS:
			icon = new ImageIcon( getClass().getResource( "ball_plus.png" ) );
			break;
		case DIE:
			icon = new ImageIcon( getClass().getResource( "die.png" ) );
			break;
		}
		image=icon.getImage();
		g.drawImage(image, x, y, 100, 35,null);
		
	}
	
}
