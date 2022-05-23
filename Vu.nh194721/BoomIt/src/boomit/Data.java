package boomit;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Data {
	public static BufferedImage sprite;
	
	public static Image imageChar_DOWN_1;
	public static Image imageChar_DOWN_2;
	public static Image imageChar_DOWN_3;
	public static Image imageChar_UP_1;
	public static Image imageChar_UP_2;
	public static Image imageChar_UP_3;
	public static Image imageChar_LEFT_1;
	public static Image imageChar_LEFT_2;
	public static Image imageChar_LEFT_3;
	public static Image imageChar_RIGHT_1;
	public static Image imageChar_RIGHT_2;
	public static Image imageChar_RIGHT_3;
	
	public static void loadImage() {
		try {
			sprite = ImageIO.read(new File("res/sprite.png"));
			
			imageChar_DOWN_1 = sprite.getSubimage(20, 13, 15, 22);
			imageChar_DOWN_2 = sprite.getSubimage(3, 13, 15, 22);
			imageChar_DOWN_3 = sprite.getSubimage(36, 13, 15, 22);
			
			imageChar_UP_1 = sprite.getSubimage(68, 13, 15, 22);
			imageChar_UP_2 = sprite.getSubimage(52, 13, 15, 22);
			imageChar_UP_3 = sprite.getSubimage(84, 13, 15, 22);
			
			imageChar_LEFT_1 = sprite.getSubimage(19, 37, 16, 22);
			imageChar_LEFT_2 = sprite.getSubimage(3, 37, 15, 22);
			imageChar_LEFT_3 = sprite.getSubimage(35, 37, 15, 22);
			
			imageChar_RIGHT_1 = sprite.getSubimage(67, 37, 16, 22);
			imageChar_RIGHT_2 = sprite.getSubimage(52, 37, 15, 22);
			imageChar_RIGHT_3 = sprite.getSubimage(84, 37, 15, 22);
		}catch(Exception e) {}
	}
}
