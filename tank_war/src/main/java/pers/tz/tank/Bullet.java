package pers.tz.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * i 子弹类
 * @author tangze
 *
 */
public class Bullet {
	private final int SPEED = 10;
	private int x,y;
	private Dir dir;
	private final int B_WIDTH = ResourceMgr.bulletU.getWidth() ,B_HEIGHT = ResourceMgr.bulletU.getHeight();
	TankFrame tf ;
	
	private boolean living = true;
	private Group group ;
	
	public Bullet(int x, int y, Dir dir ,TankFrame tf ,Group group) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
	}
	
	public void paint(Graphics g) {
		if( !living ) tf.bullets.remove(this);
		
		switch(dir) {
			case LEFT:
				g.drawImage(ResourceMgr.bulletL, x, y, null);
				break;
			case RIGHT:
				g.drawImage(ResourceMgr.bulletR, x, y, null);
				break;
			case UP:
				g.drawImage(ResourceMgr.bulletU, x, y, null);
				break;
			case DOWN:
				g.drawImage(ResourceMgr.bulletD, x, y, null);
				break;
		}
		
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

	//碰撞检测 用一个Rectangle，new的太频繁导致gc频繁
	public void collideWith(Tank tank) {
		if( this.group==tank.getGroup() ) return ;
		//具象
		Rectangle bRect = new Rectangle(this.x ,this.y ,this.B_WIDTH ,this.B_HEIGHT);
		Rectangle tRect = new Rectangle(tank.getX() ,tank.getY() ,tank.getT_WIDTH() ,tank.getT_HEIGHT());
		if( bRect.intersects(tRect) ) {
			tank.die();
			this.die();
			
			tf.explodes.add(new Explode(x ,y ,tf));
		}
	}

	private void die() {
		this.living = false;
	}

	@Override
	public String toString() {
		return "Bullet [SPEED=" + SPEED + ", x=" + x + ", y=" + y + ", dir=" + dir + ", B_WIDTH=" + B_WIDTH
				+ ", B_HEIGHT=" + B_HEIGHT + ", living=" + living + "]";
	}
	
	
}
