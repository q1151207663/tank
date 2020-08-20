package pers.tz.tank;

import java.awt.Graphics;

public class Explode {
	private int x ,y;
	private final int T_WIDTH = ResourceMgr.exlodes[0].getWidth() ,T_HEIGHT = ResourceMgr.exlodes[0].getHeight();
	TankFrame tf;
//	private boolean living = true ;
	
	private int step = 0 ;
	
	public Explode(int x, int y, TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
//		new Audio("audio/explode.wav").start();
	}

	public void paint(Graphics g) {
		g.drawImage(ResourceMgr.exlodes[step++] ,x ,y ,null);
		if( step>=ResourceMgr.exlodes.length ) {
			step=0;
			tf.explodes.remove(this);
		}
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


	
	
	
	
	
	
}
