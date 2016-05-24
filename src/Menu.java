import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JPanel implements MouseMotionListener,MouseListener{
	
	Color color;
	Color color2=color.BLACK;
	static JFrame frame;
	Game game;
	
	AudioClip sound;
	
	public Menu(){
        super();
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        sound=Applet.newAudioClip(getClass().getResource("welcome.wav"));
        
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX()<700 && e.getX()>400 && e.getY()<210 && e.getY()>130){//START
			color=color.BLUE;
			sound.play();
		}else if(e.getX()<675 && e.getX()>420 && e.getY()<405 && e.getY()>330){//EXIT
			color2=color.BLUE;
		}else{
			color=color.BLACK;
			color2=color.BLACK;
		}
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getX()<700 && e.getX()>400 && e.getY()<210 && e.getY()>130){//START
			frame.setContentPane(new Game()); 
			frame.setVisible(true);
		}else if(e.getX()<675 && e.getX()>420 && e.getY()<405 && e.getY()>330){//EXIT
			frame.dispose();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void paintComponent(Graphics g) {
       super.paintComponent(g);
       ImageIcon icon = new ImageIcon( getClass().getResource( "menu.jpg" ) );
       Image image=icon.getImage();
       g.drawImage(image, 0, 0, 1200, 800, this);
       g.setColor(color);
       g.setFont(new Font("Courier New", Font.CENTER_BASELINE, 100));
       g.drawString("START", 400, 200);
       g.setColor(color2);
       g.drawString("EXIT", 430, 400);
	}
	
	public static void main(String[] args) {
		frame = new JFrame("Breakout Clone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Menu()); 
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setVisible(true);
	}
	
}

