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
	
	private int level=2; //關卡
	private int[][] set; //關卡配置
	private Brick[][] rect; 
	private Bar bar; 
	private Ball[] ball;
	private Item[] item;
	private int ballCount=0; //當前球的數量
	private int ballMAX=100; 
	private int itemCount=0;
	private int itemMAX=100;
	private int checkWin=0; //判斷是否打完所有磚塊
	private int Ai=0 ;
	private int barStartX=0;
	private int barEndX=0;
	private int beforeStage=0;
	
	private final int COL=10; //磚塊區列數
	private final int ROW=10; //磚塊區行數
	private final int SPEED=5; //普通球速度
	private final int BarY=700; //桿子位置

	private final int INVISIBLE=0; //bar隱形
	private final int VISIBLE=1; //bar可見
	private int barStatic=VISIBLE;
	
	private final int NORMAL=1; //普通球
	private final int FIRE=2; //火焰球

	private int fire_time=0; //火焰求持續時間
	private int unsee_time=0; //隱形bar持續時間

	private int NOW=0; //當前狀態是否輸贏
	private final int LOSE=1;
	private final int WIN=2;
	
	private final int BAR_LONGER=1; //道具編號
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
        
        setLevel(); //關卡配置
        
        setRect(); //實做磚塊
        
        repaint(); //重繪
        
        gameStart(); //開始遊戲
	}
	
	public void setLevel(){ //關卡配置
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
	
	//實做磚塊
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
		bar.setX(e.getX());//設定鼠標位置
		for(int i=0;i<ballCount;i++){  //讓球跟著bar移動
			if(ball[i].speed==0){
				ball[i].setX(e.getX()-5);
				ball[i].setY(690);
			}
			
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for(int i=0;i<ballCount;i++){ //發射球
			if(ball[i].speed==0){
				ball[i].go(SPEED,45);
			}
		}
		
		if(beforeStage==1){ //判定是否為關卡開始前
			gameStart();
			beforeStage=0;
		}
		
		if(NOW==LOSE){ //設定關卡開始前基礎值
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
        
        drawBall(g); //繪製球
        drawRect(g); //繪製磚塊
        drawItem(g); //繪製道具
        if(barStatic==VISIBLE){
        	bar.draw(g); //繪製板子
        }
        if(NOW==LOSE){ //判斷輸贏
        	if(Ai<255){     		
        		drawLose(g,Ai); //繪製失敗狀況
                Ai++; 		
                repaint();
        	}else{
        		drawLose(g,Ai);
        	}
        }else if(NOW==WIN){
        	if(Ai<255){     		
        		drawWin(g,Ai); //繪製獲勝狀況
                Ai++; 		
                repaint();
        	}else{
        		drawWin(g,Ai);
        	}
        }
     }
	
	public void drawBall(Graphics g){ //繪製球
		for(int i=0;i<ballCount;i++){
        	ball[i].draw(g);
        }
	}
	
	public void drawRect(Graphics g){ //繪製磚塊
		for(int i=0;i<ROW;i++){
			for(int j=0;j<COL;j++){
				rect[i][j].draw(g);
			}
		}
	}
	
	public void drawItem(Graphics g){ //繪製道具
		for(int i=0;i<itemCount;i++){
			item[i].draw(g);
		}
	}
	
	public void drawLose(Graphics g,int i){ //繪製失敗狀況
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
	
	public void drawWin(Graphics g,int i){ //繪製獲勝狀況
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
				while(checkWin<100*ballCount && ball[0].y<800){ //判斷是否所有磚塊都被打掉或是球是否掉下去
					checkWin=0;
					for(int i=0;i<ballCount;i++){
						ball[i].move();
						//System.out.println(ball[0].y);
						for(int j=0;j<ROW;j++){
							for(int k=0;k<COL;k++){ //撞到磚塊判定
								if(set[j][k]!=0){
									if(ball[i].x+10>=190+80*k && ball[i].x<=190+80*(k+1) && ball[i].y+10>=50+30*j && ball[i].y<=50+30*(j+1)){
										if(ball[i].ballType==NORMAL){
											ball[i].reflectRect(190+80*k,50+30*j); //球反彈
										}
										set[j][k]--; //磚塊消失
										setRect(); 
										if(rand.nextInt(100)<50){ //打掉磚塊後50%機率掉落道具
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
						//判斷bar改變球的角度
						if(ball[i].y>BarY-40 && ball[i].y<BarY-30){
							barStartX=bar.x;
						}else if(ball[i].y>BarY-10 && ball[i].y<BarY){
							barEndX=bar.x;
						}
						//利用控制桿子改變球反彈的角度
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
						//最後一顆球掉下去才輸
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
					//吃到道具判定
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
					//火焰球時間
					if(fire_time>0){
						fire_time--;
						if(fire_time==0){
							for(int k=0;k<ballCount;k++){
								ball[k].ballType=NORMAL;
							}
						}
					}
					//隱形bar時間
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
				if(ball[0].y>780){ //失敗
					NOW=LOSE;
				}else{ //勝利
					NOW=WIN;
				}
				repaint();
			}

		};
		ballgame.start();
	}

}
