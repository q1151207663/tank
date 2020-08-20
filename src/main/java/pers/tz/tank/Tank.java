package pers.tz.tank;

import pers.tz.tank.net.ObjectMsg;
import pers.tz.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.*;

public class Tank {
	private int x ,y;
	private Dir dir = Dir.UP;//初始默认方向
	private final int SPEED = 2;
	private boolean moving = false;
	private final int T_WIDTH = ResourceMgr.goodTankU.getWidth() ,T_HEIGHT = ResourceMgr.goodTankU.getHeight();
	TankFrame tf;
	private boolean living = true ;
	private Random random = new Random();
	private Group group ;
	public Rectangle tRect = new Rectangle();

	public UUID id = UUID.randomUUID();

	
	public Tank(int x, int y, Dir dir ,TankFrame tf ,Group group) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
		this.group = group;
		
		tRect.x = x;
		tRect.y = y;
		tRect.width = T_WIDTH ;
		tRect.height = T_HEIGHT;
	}


	public Tank(TankJoinMsg msg) {
		this.x = msg.x;
		this.y = msg.y;
		this.dir = msg.dir;
		this.group = msg.group;

		this.id = msg.id;//巨！坑！

		tRect.x = x;
		tRect.y = y;
		tRect.width = T_WIDTH ;
		tRect.height = T_HEIGHT;
	}



	public void paint(Graphics g) {
//		if( !living ) tf.badTank.remove(this);

		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("uuid："+this.id.toString(), this.x, this.y - 10);
		g.setColor(c);

		switch(dir) {
			case LEFT:
				g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankL:ResourceMgr.badTankL, x, y, null);
				break;
			case RIGHT:
				g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankR:ResourceMgr.badTankR, x, y, null);
				break;
			case UP:
				g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankU:ResourceMgr.badTankU, x, y, null);
				break;
			case DOWN:
				g.drawImage(this.group==Group.GOOD?ResourceMgr.goodTankD:ResourceMgr.badTankD, x, y, null);
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
		
		randomFire();
		
		randomDir();
		
		boundsCheck();
		
		updateRect();
	}

	private void updateRect() {
		tRect.x = x;
		tRect.y = y;		
	}

	private void boundsCheck() {
		if( x<0 ) x = 0;
		if( y<30 ) y = 30;
		if( x>TankFrame.GAME_WIDTH-T_WIDTH ) x = TankFrame.GAME_WIDTH-T_WIDTH;
		if( y>TankFrame.GAME_HEIGHT-T_HEIGHT ) y = TankFrame.GAME_HEIGHT-T_HEIGHT;
	}

	private void randomFire() {
		if( random.nextInt(10)>8 && group==Group.BAD )
			this.fire();
	}

	private void randomDir() {
		if( random.nextInt(100)>97 && group==Group.BAD )
			this.dir = Dir.values()[random.nextInt(4)];
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
		tf.bullets.add( new Bullet(bX ,bY ,this.dir ,this.tf ,this.group) );
	}

	
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tank [x=" + x + ", y=" + y + ", dir=" + dir + ", SPEED=" + SPEED + ", moving=" + moving + ", T_WIDTH="
				+ T_WIDTH + ", T_HEIGHT=" + T_HEIGHT + ", living=" + living + "]";
	}




	
}
