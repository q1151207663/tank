package pers.tz.tank;

public class Main {
	public static void main(String[] args) throws Throwable{
		
		TankFrame tf = new TankFrame();
		
		while(true) {
			Thread.sleep(50);
			tf.repaint();
		}
		
	}
}
