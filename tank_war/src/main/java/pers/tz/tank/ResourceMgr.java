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
	public static BufferedImage tankL ,tankR ,tankU ,tankD;
	public static BufferedImage bulletL ,bulletR ,bulletU ,bulletD;
	public static BufferedImage[] exlodes = new BufferedImage[16];
	
	static {
		try {
			tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			tankL = ImageUtil.rotateImage(tankU, -90) ;
			tankR = ImageUtil.rotateImage(tankU, 90) ;
			tankD = ImageUtil.rotateImage(tankU, 180) ;
			
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
