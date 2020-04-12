package pers.tz.tank;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankFrame extends Frame {
	
	int x = 200 , y = 200;
	
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
	}
	
	
	/**
	 * i 键盘监视类
	 * i 设置对各种键盘的事件
	 * i 由于该类只需要给自己使用，不需要暴露给外面，所以定义成私有内部类的形式
	 * @author tangze
	 *
	 */
	private class MyKeyLisenter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
//			x += 50;
//			y += 50;
			repaint();//Frame的方法，它会调用paint
		}

		@Override
		public void keyReleased(KeyEvent e) {
			repaint();//Frame的方法，它会调用paint
		}
		
		
	}
	
	
}
