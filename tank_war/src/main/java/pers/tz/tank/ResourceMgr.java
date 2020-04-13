package pers.tz.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * i 静态资源管理者
 * i 负责加载静态资源，并存储
 * @author tangze
 *
 */
public class ResourceMgr {
	public static BufferedImage goodTankL ,goodTankR ,goodTankU ,goodTankD;
	public static BufferedImage badTankL ,badTankR ,badTankU ,badTankD;
	public static BufferedImage bulletL ,bulletR ,bulletU ,bulletD;
	public static BufferedImage[] exlodes = new BufferedImage[16];
	
	static {
		try {
			goodTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			goodTankL = ImageUtil.rotateImage(goodTankU, -90) ;
			goodTankR = ImageUtil.rotateImage(goodTankU, 90) ;
			goodTankD = ImageUtil.rotateImage(goodTankU, 180) ;
			
			badTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			badTankL = ImageUtil.rotateImage(badTankU, -90) ;
			badTankR = ImageUtil.rotateImage(badTankU, 90) ;
			badTankD = ImageUtil.rotateImage(badTankU, 180) ;
			
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletL = ImageUtil.rotateImage(bulletU, -90);
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletU, 180);
			
			for( int i=1 ;i<=exlodes.length ;i++ )
				exlodes[i-1] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e"+ i +".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
