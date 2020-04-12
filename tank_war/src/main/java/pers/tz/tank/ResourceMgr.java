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
	
	static {
		try {
			tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
			tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
			tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
			tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
