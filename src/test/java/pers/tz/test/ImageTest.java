package pers.tz.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ImageTest {

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		try {
//			BufferedImage image = ImageIO.read(new File("D:/学习相关/tank war静态资源/资源/images/BadTank2.png"));
			BufferedImage image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/BadTank2.png"));
			assertNotNull(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
