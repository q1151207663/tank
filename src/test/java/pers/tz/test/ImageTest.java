package pers.tz.test;


import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.junit.Assert.assertNotNull;


class ImageTest {


	@Test
	public void test() {
		try {
//			BufferedImage image = ImageIO.read(new File("D:/学习相关/tank war静态资源/资源/images/BadTank2.png"));
			BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/BadTank2.png"));
			assertNotNull(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
