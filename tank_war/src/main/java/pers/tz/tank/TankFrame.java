package pers.tz.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
	
	int x = 200 , y = 200;
	Dir dir = Dir.UP;//初始默认方向
	private final int DISTANCE = 10;
	
	public TankFrame(){
		setSize(800 ,600);
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
		g.fillRect(x, y, 50, 50);
		switch(dir) {
		case LEFT:
			x -= DISTANCE;
			break;
		case RIGHT:
			x += DISTANCE;
			break;
		case UP:
			y -= DISTANCE;
			break;
		case DOWN:
			y += DISTANCE;
			break;
		}
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
			}
			setGoodTankDir();//改变自己tank的方向
		}
		
		
		private void setGoodTankDir() {
			if(bL) dir = Dir.LEFT;
			if(bR) dir = Dir.RIGHT;
			if(bU) dir = Dir.UP;
			if(bD) dir = Dir.DOWN;
			
		}
		
	}
	
	
}
