package pers.tz.tank;

import java.awt.Graphics;

public class Tank {
	private int x ,y;
	private Dir dir = Dir.UP;//初始默认方向
	private final int DISTANCE = 10;
	
	public Tank(int x, int y, Dir dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

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

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	
	
	
	
	
}
