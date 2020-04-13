package pers.tz.tank;

public class Main {
	public static void main(String[] args) throws Throwable{
		
		TankFrame tf = new TankFrame();
		
		//初始化敌方坦克
		for( int i=0;i<5 ;i++ )
			tf.badTank.add(new Tank(i*80 +50 ,200 ,Dir.DOWN ,tf ,Group.BAD));
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();//Frame的方法，它会调用paint
		}
		
	}
}
