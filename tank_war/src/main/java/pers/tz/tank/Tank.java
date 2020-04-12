package pers.tz.tank;

import java.awt.Graphics;

public class Tank {
	private int x ,y;
	private Dir dir = Dir.UP;//初始默认方向
	private final int SPEED = 5;
	private boolean moving = false;
	private final int T_WIDTH = ResourceMgr.tankU.getWidth() ,T_HEIGHT = ResourceMgr.tankU.getHeight();
	TankFrame tf;
	private boolean living = true ;
	
	public Tank(int x, int y, Dir dir ,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
	}

	public void paint(Graphics g) {
		if( !living ) return;
		
		switch(dir) {
			case LEFT:
				g.drawImage(ResourceMgr.tankL, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.tankR, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.tankU, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.tankD, x, y, null);
				break;
		}
		
		
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
		int bX = this.x+this.T_WIDTH/2-ResourceMgr.bulletU.getWidth()/2;
		int bY = this.y+this.T_HEIGHT/2-ResourceMgr.bulletU.getHeight()/2;
		tf.bullets.add( new Bullet(bX ,bY ,this.dir ,this.tf) );
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getT_WIDTH() {
		return T_WIDTH;
	}

	public int getT_HEIGHT() {
		return T_HEIGHT;
	}

	public void die() {
		this.living = false ;
	}

	@Override
	public String toString() {
		return "Tank [x=" + x + ", y=" + y + ", dir=" + dir + ", SPEED=" + SPEED + ", moving=" + moving + ", T_WIDTH="
				+ T_WIDTH + ", T_HEIGHT=" + T_HEIGHT + ", living=" + living + "]";
	}
	
	
	
	
	
	
}
