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
		g.drawImage(ResourceMgr.tankU, x, y, null);
		
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
		//如果不清楚，存在内存泄漏的风险
		//java中的内存泄漏经常是和容器有关系的，因为它可以无限扩容
		//因此在使用容器时，需要尤为小心
		tf.bullets.add( new Bullet(this.x ,this.y ,this.dir ,this.tf) );
	}
	
	
	
	
	
	
}
