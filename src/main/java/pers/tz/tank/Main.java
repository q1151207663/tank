package pers.tz.tank;


import pers.tz.tank.net.Client;

import java.util.Objects;

public class Main {
	public static void main(String[] args) throws Throwable{
		
		TankFrame tf = TankFrame.INSTANCE;
		int initBadTankCount = Integer.parseInt(Objects.requireNonNull(PropertyMgr.get("initBadTankCount")));
		
		if( initBadTankCount>15 ) System.exit(0);
		
		//初始化敌方坦克
//		for( int i=0;i<initBadTankCount ;i++ )
//			tf.badTank.add(new Tank(i*80 +50 ,200 ,Dir.DOWN ,tf ,Group.BAD));
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();

		new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(25);
					tf.repaint();//Frame的方法，它会调用paint
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		Client.getInstance().connect();

	}
}
