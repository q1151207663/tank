package pers.tz.tank;

import java.awt.Color;
import java.awt.Graphics;

/**
 * i 子弹类
 * @author tangze
 *
 */
public class Bullet {
	private final int SPEED = 5;
	private int x,y;
	private Dir dir;
	TankFrame tf ;
	
	private boolean living = true;
	
	public Bullet(int x, int y, Dir dir ,TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
	}
	
	public void paint(Graphics g) {
		if( !living ) tf.bullets.remove(this);
		
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		move();
	}

	private void move() {
		switch(dir) {
		case LEFT:
			x -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}
		
		checkLiving();
	}

	//子弹超出边界
	private void checkLiving() {
		if( x<0 || y<0 || x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT ) living = false;
	}
	
	
}
