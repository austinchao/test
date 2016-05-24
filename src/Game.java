import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener,MouseMotionListener{
	
	private int level=2; //���d
	private int[][] set; //���d�t�m
	private Brick[][] rect; 
	private Bar bar; 
	private Ball[] ball;
	private Item[] item;
	private int ballCount=0; //��e�y���ƶq
	private int ballMAX=100; 
	private int itemCount=0;
	private int itemMAX=100;
	private int checkWin=0; //�P�_�O�_�����Ҧ��j��
	private int Ai=0 ;
	private int barStartX=0;
	private int barEndX=0;
	private int beforeStage=0;
	
	private final int COL=10; //�j���ϦC��
	private final int ROW=10; //�j���Ϧ��
	private final int SPEED=5; //���q�y�t��
	private final int BarY=700; //��l��m

	private final int INVISIBLE=0; //bar����
	private final int VISIBLE=1; //bar�i��
	private int barStatic=VISIBLE;
	
	private final int NORMAL=1; //���q�y
	private final int FIRE=2; //���K�y

	private int fire_time=0; //���K�D����ɶ�
	private int unsee_time=0; //����bar����ɶ�

	private int NOW=0; //��e���A�O�_��Ĺ
	private final int LOSE=1;
	private final int WIN=2;
	
	private final int BAR_LONGER=1; //�D��s��
	private final int BAR_SHORTER=2;
	private final int BAR_UNSEE=3;
	private final int BALL_FIRE=4;
	private final int BALL_PLUS=5;
	private final int DIE=6;
	
	Random rand = new Random();
	
	public Game(){
        super();
        
        rect=new Brick[ROW][COL];
        bar=new Bar(BarY);
        ball=new Ball[ballMAX];
        item=new Item[itemMAX];
        
        addMouseListener(this);
        addMouseMotionListener(this);

        ball[ballCount++]=new Ball(600,690,0,0,0,0,NORMAL);
        
        setLevel(); //���d�t�m
        
        setRect(); //�갵�j��
        
        repaint(); //��ø
        
        gameStart(); //�}�l�C��
	}
	
	public void setLevel(){ //���d�t�m
		switch(level){ 
        case 1:   	
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,1,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}
        					,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 2:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,0,1,1,1,1,1,1,0,0}
							,{0,0,1,2,2,2,2,1,0,0}
							,{0,0,1,2,2,2,2,1,0,0}
							,{0,0,1,1,1,1,1,1,0,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 3:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,1,1,0,0,0,0}
							,{0,0,0,1,1,1,1,0,0,0}
							,{0,0,1,1,2,2,1,1,0,0}
							,{0,1,1,2,3,3,2,1,1,0}
							,{0,1,1,2,3,3,2,1,1,0}
							,{0,0,1,1,2,2,1,1,0,0}
							,{0,0,0,1,1,1,1,0,0,0}
							,{0,0,0,0,1,1,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 4:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,0,1,0,0,0,0,1,0,0}
							,{0,0,1,2,0,0,2,1,0,0}
							,{0,0,0,2,3,3,2,0,0,0}
							,{0,0,0,0,4,4,0,0,0,0}
							,{0,0,0,2,3,3,2,0,0,0}
							,{0,0,1,2,0,0,2,1,0,0}
							,{0,0,1,0,0,0,0,1,0,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 5:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,1,1,1,0,0,1,1,1,0}
							,{0,1,2,0,0,0,0,2,1,0}
							,{0,1,0,0,3,3,0,0,1,0}
							,{0,0,0,3,4,4,3,0,0,0}
							,{0,0,0,3,4,4,3,0,0,0}
							,{0,1,0,0,3,3,0,0,1,0}
							,{0,1,2,0,0,0,0,2,1,0}
							,{0,1,1,1,0,0,1,1,1,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 6:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,5,4,3,0,0,3,4,5,0}
							,{0,4,4,3,0,0,3,4,4,0}
							,{0,3,3,3,0,0,3,3,3,0}
							,{0,0,0,0,0,0,0,0,0,0}
							,{0,3,3,3,0,0,3,3,3,0}
							,{0,2,2,3,0,0,3,2,2,0}
							,{0,1,2,3,0,0,3,2,1,0}
							,{0,1,1,1,1,1,1,1,1,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        case 7:
        	set=new int[][]{ {0,0,0,0,0,0,0,0,0,0}
							,{0,0,1,0,0,1,0,1,0,0}
							,{0,0,1,0,0,1,0,1,0,0}
							,{0,0,1,0,0,1,0,0,0,0}
							,{0,0,2,3,4,5,0,2,0,0}
							,{0,0,2,3,4,5,0,2,0,0}
							,{0,0,1,0,0,1,0,1,0,0}
							,{0,0,1,0,0,1,0,1,0,0}
							,{0,0,1,0,0,1,0,1,0,0}
							,{0,0,0,0,0,0,0,0,0,0}};
        	break;
        }
	}
	
	//�갵�j��
	public void setRect(){
        for(int i=0;i<ROW;i++){
			for(int j=0;j<COL;j++){
				rect[i][j]=new Brick(set[i][j],j,i);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		bar.setX(e.getX());//�]�w���Ц�m
		for(int i=0;i<ballCount;i++){  //���y���bar����
			if(ball[i].speed==0){
				ball[i].setX(e.getX()-5);
				ball[i].setY(690);
			}
			
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i=0;i<ballCount;i++){ //�o�g�y
			if(ball[i].speed==0){
				ball[i].go(SPEED,45);
			}
		}
		
		if(beforeStage==1){ //�P�w�O�_�����d�}�l�e
			gameStart();
			beforeStage=0;
		}
		
		if(NOW==LOSE){ //�]�w���d�}�l�e��¦��
			NOW=0;
			ballCount=0;
			itemCount=0;
			ball[ballCount].setX(e.getX()-5);
			ball[ballCount].setY(690);
			ball[ballCount].speed=0;
			ball[ballCount].ballType=NORMAL;
			ballCount++;
			bar.width=120;
			barStatic=VISIBLE;
			beforeStage=1;
			setLevel();
			setRect();
		}else if(NOW==WIN){
			level++;
			NOW=0;
			ballCount=0;
			itemCount=0;
			ball[ballCount].setX(e.getX()-5);
			ball[ballCount].setY(690);
			ball[ballCount].speed=0;
			ball[ballCount].ballType=NORMAL;
			ballCount++;
			bar.width=120;
			barStatic=VISIBLE;
			beforeStage=1;
			setLevel();
			setRect();		
		}
		
		
		
		repaint();
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
	
	 public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        drawBall(g); //ø�s�y
        drawRect(g); //ø�s�j��
        drawItem(g); //ø�s�D��
        if(barStatic==VISIBLE){
        	bar.draw(g); //ø�s�O�l
        }
        if(NOW==LOSE){ //�P�_��Ĺ
        	if(Ai<255){     		
        		drawLose(g,Ai); //ø�s���Ѫ��p
                Ai++; 		
                repaint();
        	}else{
        		drawLose(g,Ai);
        	}
        }else if(NOW==WIN){
        	if(Ai<255){     		
        		drawWin(g,Ai); //ø�s��Ӫ��p
                Ai++; 		
                repaint();
        	}else{
        		drawWin(g,Ai);
        	}
        }
     }
	
	public void drawBall(Graphics g){ //ø�s�y
		for(int i=0;i<ballCount;i++){
        	ball[i].draw(g);
        }
	}
	
	public void drawRect(Graphics g){ //ø�s�j��
		for(int i=0;i<ROW;i++){
			for(int j=0;j<COL;j++){
				rect[i][j].draw(g);
			}
		}
	}
	
	public void drawItem(Graphics g){ //ø�s�D��
		for(int i=0;i<itemCount;i++){
			item[i].draw(g);
		}
	}
	
	public void drawLose(Graphics g,int i){ //ø�s���Ѫ��p
		Color color;
		if(Ai<255){
			color=new Color(0,0,0,i);
		}else{
			color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		}
		g.setColor(color);
		g.setFont(new Font("Courier New", Font.CENTER_BASELINE, 100));
	    g.drawString("YOU LOSE", 400, 200);
	    
	}
	
	public void drawWin(Graphics g,int i){ //ø�s��Ӫ��p
		Color color;
		if(Ai<255){
			color=new Color(0,0,0,i);
		}else{
			color=new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		}
		g.setColor(color);
		g.setFont(new Font("Courier New", Font.CENTER_BASELINE, 100));
	    g.drawString("YOU WIN!", 400, 200);
	    
	}
	
	public void gameStart(){
		Thread ballgame = new Thread(){
			public void run(){
				while(checkWin<100*ballCount && ball[0].y<800){ //�P�_�O�_�Ҧ��j�����Q�����άO�y�O�_���U�h
					checkWin=0;
					for(int i=0;i<ballCount;i++){
						ball[i].move();
						//System.out.println(ball[0].y);
						for(int j=0;j<ROW;j++){
							for(int k=0;k<COL;k++){ //����j���P�w
								if(set[j][k]!=0){
									if(ball[i].x+10>=190+80*k && ball[i].x<=190+80*(k+1) && ball[i].y+10>=50+30*j && ball[i].y<=50+30*(j+1)){
										if(ball[i].ballType==NORMAL){
											ball[i].reflectRect(190+80*k,50+30*j); //�y�ϼu
										}
										set[j][k]--; //�j������
										setRect(); 
										if(rand.nextInt(100)<50){ //�����j����50%���v�����D��
											if(itemCount<itemMAX){
												try{
													item[itemCount++]=new Item(190+80*k+40, 50+30*j+15, rand.nextInt(6)+1);
												}catch(NullPointerException e){
												}
											}
										}
									}
								}
								if(set[j][k]==0){
									checkWin++;
								}
								//System.out.println(checkWin);
							}
						}
						//�P�_bar���ܲy������
						if(ball[i].y>BarY-40 && ball[i].y<BarY-30){
							barStartX=bar.x;
						}else if(ball[i].y>BarY-10 && ball[i].y<BarY){
							barEndX=bar.x;
						}
						//�Q�α����l���ܲy�ϼu������
						if(ball[i].y>BarY-10 && ball[i].y<BarY && ball[i].x>bar.x-bar.width/2 && ball[i].x<bar.x+bar.width/2){
							//System.out.println(barEndX-barStartX);
							if(barEndX-barStartX>200){
								ball[i].go(SPEED, 20);
							}else if(barEndX-barStartX>100){
								ball[i].go(SPEED, 30);
							}else if(barEndX-barStartX<-200){
								ball[i].go(SPEED, 160);
							}else if(barEndX-barStartX<-100){
								ball[i].go(SPEED, 150);
							}
							else{
								ball[i].reflectBar();
							}
							
						}
						//�̫�@���y���U�h�~��
						if(ball[0].y>750 && ballCount>1){
							ball[0].x=ball[ballCount-1].x;
							ball[0].y=ball[ballCount-1].y;
							ball[0].ballType=ball[ballCount-1].ballType;
							ball[0].degree=ball[ballCount-1].degree;
							ball[0].speedX=ball[ballCount-1].speedX;
							ball[0].speedY=ball[ballCount-1].speedY;
							ballCount--;
						}
					}
					//�Y��D��P�w
					for(int i=0;i<itemCount;i++){
						item[i].move();
						if(item[i].y+35>BarY && item[i].y<BarY+10 && item[i].x+100>bar.x-bar.width/2 && item[i].x<bar.x+bar.width/2){
							//System.out.println("eat item");
							switch(item[i].type){
							case BAR_LONGER:
								bar.width=200;
								break;
								
							case BAR_SHORTER:
								bar.width=70;
								break;
								
							case BAR_UNSEE:
								barStatic=INVISIBLE;
								unsee_time=1000;
								break;
								
							case BALL_FIRE:
								for(int k=0;k<ballCount;k++){
									ball[k].ballType=FIRE;
								}
								fire_time=1000;
								break;
								
							case BALL_PLUS:
								ball[ballCount++]=new Ball(ball[0].x,ball[0].y, ball[0].speed, ball[0].speedX*(-1), ball[0].speedY*(-1),ball[0].ballType);
								break;
								
							case DIE:
								NOW=LOSE;
								ball[0].y=800;
								break;
							}
							if(i!=itemCount-1){
								for(int j=i;j<itemCount-1;j++){
									item[j] = item[j+1];
								}
							}
							itemCount--;
						}
					}	
					//���K�y�ɶ�
					if(fire_time>0){
						fire_time--;
						if(fire_time==0){
							for(int k=0;k<ballCount;k++){
								ball[k].ballType=NORMAL;
							}
						}
					}
					//����bar�ɶ�
					if(unsee_time>0){
						unsee_time--;
						if(unsee_time==0){
							barStatic=VISIBLE;
						}
					}
					System.out.println("fireTIME:"+fire_time);
					System.out.println("INVISIBLETIME:"+unsee_time);
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
				checkWin=0;
				if(ball[0].y>780){ //����
					NOW=LOSE;
				}else{ //�ӧQ
					NOW=WIN;
				}
				repaint();
			}

		};
		ballgame.start();
	}

}
