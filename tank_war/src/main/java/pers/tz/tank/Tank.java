package pers.tz.tank;

import java.awt.Graphics;

public class Tank {
	private int x ,y;
	private Dir dir = Dir.UP;//初始默认方向
	private final int SPEED = 5;
	private boolean moving = false;
	TankFrame tf;
	
	public Tank(int x, int y, Dir dir ,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
	}

	public void paint(Graphics g) {
		g.fillRect(x, y, 50, 50);
		if(moving) move();
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
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void fire() {
		tf.bullets.add( new Bullet(this.x ,this.y ,this.dir) );
	}
	
	
	
	
	
	
}
