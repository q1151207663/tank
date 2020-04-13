package pers.tz.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	Tank myTank = new Tank(200 ,400 ,Dir.UP ,this ,Group.GOOD);
	List<Bullet> bullets = new ArrayList<>();
	List<Tank> badTank = new ArrayList<>();
	List<Explode> explods = new ArrayList<>();
	static final int GAME_WIDTH = 1080 ,GAME_HEIGHT = 960;
	Image offScreenImage = null ;
	Explode e = new Explode(100, 100, this);
	
	public TankFrame(){
		setSize(GAME_WIDTH ,GAME_HEIGHT);
		setResizable(false);
		setTitle("tank war");
		setVisible(true);
		
		this.addKeyListener(new MyKeyLisenter());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	
	@Override
	public void paint(Graphics g) {
		//如果将tank的属性拿出来给别人画，就在一定程度上破坏了它的封装性
		g.setColor(Color.WHITE);
		g.drawString("子弹："+bullets.size(), 10, 60);
		g.drawString("敌方坦克数量："+badTank.size(), 10, 80);
		myTank.paint(g);
		for( int i=0 ;i<bullets.size() ;i++ ) 
			bullets.get(i).paint(g);
		
		for( int i=0 ;i<badTank.size();i++ ) 
			badTank.get(i).paint(g);
		
//		for( int i=0 ;i<explods.size();i++ ) 
//			explods.get(i).paint(g);
		
		//碰撞检测
		for( int i=0;i<bullets.size() ;i++ ) {
			for( int j=0 ;j<badTank.size() ;j++ ) {
				bullets.get(i).collideWith(badTank.get(j));
			}
		}
		
		e.paint(g);
		
	}
	
	
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	
	/**
	 * i 键盘监视类
	 * i 设置对各种键盘的事件
	 * i 由于该类只需要给自己使用，不需要暴露给外面，所以定义成私有内部类的形式
	 * @author tangze
	 *
	 */
	private class MyKeyLisenter extends KeyAdapter{
		boolean bL = false;//上下左右按键状态
		boolean bR = false;
		boolean bD = false;
		boolean bU = false;
		
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
				case KeyEvent.VK_LEFT:
					bL = true;
					break;
				case KeyEvent.VK_RIGHT:
					bR = true;
					break;
				case KeyEvent.VK_DOWN:
					bD = true;
					break;
				case KeyEvent.VK_UP:
					bU = true;
			}
			setGoodTankDir();//改变自己tank的方向
			setGoodTankMoving();//改变自己tank是否移动
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
				case KeyEvent.VK_LEFT:
					bL = false;
					break;
				case KeyEvent.VK_RIGHT:
					bR = false;
					break;
				case KeyEvent.VK_DOWN:
					bD = false;
					break;
				case KeyEvent.VK_UP:
					bU = false;
					break ;
				case KeyEvent.VK_CONTROL:
					myTank.fire();
					break ;
			}
			setGoodTankDir();//改变自己tank的方向
			setGoodTankMoving();
		}
		
		
		/**
		 * i 设置方向
		 */
		private void setGoodTankDir() {
			
			if(bL) myTank.setDir( Dir.LEFT );
			if(bR) myTank.setDir( Dir.RIGHT );
			if(bU) myTank.setDir( Dir.UP );
			if(bD) myTank.setDir( Dir.DOWN );
			
		}
		
		/**
		 * i 设置是否移动
		 * i 规则是：
		 * 1 只要有任何一个方向键处于被按下的状态，就一直移动
		 * 2 如果所有的方向键都没有被按下，就停止
		 */
		private void setGoodTankMoving() {
			if(bL || bR || bU || bD) myTank.setMoving(true);
			if(!bL && !bR && !bU && !bD) myTank.setMoving(false);
			
		}
		
	}
	
	
}
